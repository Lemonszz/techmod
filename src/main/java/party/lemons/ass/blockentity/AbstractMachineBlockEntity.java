package party.lemons.ass.blockentity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.ArrayUtils;
import party.lemons.ass.block.HorizontalBlock;
import party.lemons.ass.blockentity.util.*;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.util.InventoryUtil;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements PropertyDelegateHolder, Tickable, Inventory, SidedInventory, Upgradeable, Machine, NamedScreenHandlerFactory
{
	protected MachineTier machineTier = MachineTier.BASE;
	protected Worker worker;
	protected SlotIO insertSlotIO;
	protected SlotIO extractSlotIO;
	protected AssPropertyDelegate propertyDelegate;
	private boolean firstTick = true;
	private CompoundTag workerTags = null;

	public AbstractMachineBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		this.insertSlotIO = createInsertSlotIO();
		this.extractSlotIO = createExtractSlotIO();
		propertyDelegate = new AssPropertyDelegate();
	}

	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);

		//Tier
		MachineTier loadTier = MachineTier.fromName(tag.getString("MachineTier"));
		if(loadTier == null)
			loadTier = MachineTier.BASE;
		this.machineTier = loadTier;

		//Inventory
		if(tag.contains("Inventory") && getInventoryOptional().isPresent())
		{
			ListTag list = tag.getList("Inventory", NbtType.COMPOUND);
			InventoryUtil.readTag(getInventoryOptional().get(), list);
		}

		//Worker
		if(tag.contains("Worker"))
		{
			workerTags = tag.getCompound("Worker");
		}
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);
		tag.putString("MachineTier", machineTier.getName());

		if(getInventoryOptional().isPresent())
		{
			tag.put("Inventory", InventoryUtil.toTag(getInventoryOptional().get()));
		}

		if(worker != null)
		{
			tag.put("Worker", worker.toTag());
		}

		return tag;
	}

	public void init()
	{
		this.worker = createWorker();
		if(workerTags != null)
			this.worker.fromTag(workerTags);
	}

	@Override
	public void tick()
	{
		if(firstTick)
		{
			init();
			firstTick = false;
		}

		worker.update();
	}

	public abstract Worker createWorker();
	public abstract SlotIO createInsertSlotIO();
	public abstract SlotIO createExtractSlotIO();

	public Optional<Inventory> getInventoryOptional()
	{
		if(this instanceof InventorySource)
		{
			if(((InventorySource) this).getInventory() == null)
				return Optional.empty();

			return Optional.of(((InventorySource) this).getInventory());
		}

		return Optional.empty();
	}

	@Override
	public int[] getAvailableSlots(Direction side)
	{
		BlockSide bs =  HorizontalBlock.getSide(getCachedState(), side);
		return ArrayUtils.addAll(insertSlotIO.getAvailableSlots(bs), extractSlotIO.getAvailableSlots(bs));
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir)
	{
		return insertSlotIO.canInsert(slot, HorizontalBlock.getSide(getCachedState(), dir));
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir)
	{
		return extractSlotIO.canExtract(slot,  HorizontalBlock.getSide(getCachedState(), dir));
	}

	@Override
	public int size()
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().size();
		}

		return 0;
	}

	@Override
	public boolean isEmpty()
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().isEmpty();
		}
		return true;
	}

	@Override
	public ItemStack getStack(int slot)
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().getStack(slot);
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().removeStack(slot, amount);
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStack(int slot)
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().removeStack(slot);
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setStack(int slot, ItemStack stack)
	{
		if(getInventoryOptional().isPresent())
		{
			getInventoryOptional().get().setStack(slot, stack);
		}
	}


	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		if(getInventoryOptional().isPresent())
		{
			return getInventoryOptional().get().canPlayerUse(player);
		}
		return false;
	}

	@Override
	public void clear()
	{
		if(getInventoryOptional().isPresent())
		{
			getInventoryOptional().get().clear();
		}
	}

	@Override
	public float getWorkSpeedMultiplier()
	{
		return machineTier.workSpeedMultiplier;
	}

	@Override
	public float getPowerUseMultiplier()
	{
		return machineTier.powerUseMultiplier;
	}

	@Override
	public MachineTier getCurrentTier()
	{
		return machineTier;
	}

	@Override
	public boolean canAcceptTier(MachineTier tier)
	{
		return true;
	}

	@Override
	public void upgrade(MachineTier tier)
	{
		machineTier = tier;
	}

	@Override
	public PropertyDelegate getPropertyDelegate()
	{
		return propertyDelegate;
	}
}

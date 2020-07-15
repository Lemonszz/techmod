package party.lemons.ass.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.ArrayUtils;
import party.lemons.ass.block.HorizontalBlock;
import party.lemons.ass.blockentity.util.*;
import party.lemons.ass.blockentity.worker.Worker;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements Tickable, Inventory, SidedInventory, Upgradeable, Machine, NamedScreenHandlerFactory
{
	protected MachineTier machineTier = MachineTier.BASE;
	protected Worker worker;
	protected SlotIO insertSlotIO;
	protected SlotIO extractSlotIO;
	private boolean firstTick = true;

	public AbstractMachineBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		this.insertSlotIO = createInsertSlotIO();
		this.extractSlotIO = createExtractSlotIO();
	}

	public void init()
	{
		this.worker = createWorker();
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
}

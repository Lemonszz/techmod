package party.lemons.ass.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.screen.FurnaceGeneratorGuiDescription;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.MachineTier;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.FueledFurnaceWorker;
import party.lemons.ass.blockentity.worker.FurnaceGeneratorWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;

import javax.annotation.Nullable;

public class FurnaceGeneratorBlockEntity extends GeneratorBlockEntity implements InventorySource
{
	private Inventory inventory = new SimpleInventory(1);

	public FurnaceGeneratorBlockEntity()
	{
		super(AssBlockEntities.FURNACE_GENERATOR);
		propertyDelegate.addProperty(2, ()->(int) ((FurnaceGeneratorWorker)worker).burnTime, (v)->((FurnaceGeneratorWorker)worker).burnTime = v);
		propertyDelegate.addProperty(3, ()->(int) ((FurnaceGeneratorWorker)worker).fuelTime, (v)->((FurnaceGeneratorWorker)worker).fuelTime = v);
	}

	@Override
	public Worker createWorker()
	{
		return new FurnaceGeneratorWorker(this);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO().addSide(BlockSide.values()).slot(0).build();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO().addSide(BlockSide.values()).slot(0).build();
	}

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText(AssBlocks.FURNACE_GENERATOR.getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new FurnaceGeneratorGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}

	@Override
	public float getWorkSpeedMultiplier()
	{
		return 1.0F;
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}
}

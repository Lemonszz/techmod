package party.lemons.ass.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.screen.BlockPlacerGuiDescription;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.BlockPlacerWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;

import javax.annotation.Nullable;

public class BlockPlacerBlockEntity extends AbstractPoweredMachineBlockEntity implements InventorySource
{
	private Inventory inventory;

	public BlockPlacerBlockEntity()
	{
		super(AssBlockEntities.BLOCK_PLACER);
		inventory = new SimpleInventory(9);
	}

	@Override
	public Worker createWorker()
	{
		return new BlockPlacerWorker(this);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.values())
				.range(0, 8).build();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.values())
					.range(0, 8).build();
	}

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText(AssBlocks.BLOCK_PLACER.getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new BlockPlacerGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}
}

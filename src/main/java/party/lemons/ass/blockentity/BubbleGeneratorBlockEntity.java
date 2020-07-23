package party.lemons.ass.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.screen.PowerOnlyGuiDescription;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.BubbleGeneratorWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;

import javax.annotation.Nullable;

public class BubbleGeneratorBlockEntity extends GeneratorBlockEntity
{
	public BubbleGeneratorBlockEntity()
	{
		super(AssBlockEntities.BUBBLE_GENERATOR);
	}

	@Override
	public Worker createWorker()
	{
		return new BubbleGeneratorWorker(this);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO();
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new PowerOnlyGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}
}

package party.lemons.ass.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.EnderMagnetWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;


public class EnderMagnetBlockEntity extends AbstractPoweredMachineBlockEntity
{
	public EnderMagnetBlockEntity()
	{
		super(AssBlockEntities.ENDER_MAGNET);
	}

	@Override
	public Worker createWorker()
	{
		return new EnderMagnetWorker(this);
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

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText(AssBlocks.ENDER_MAGNET.getTranslationKey());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return null;
	}
}

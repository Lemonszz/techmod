package party.lemons.ass.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.screen.BlockBreakerGuiDescription;
import party.lemons.ass.blockentity.screen.PowerOnlyGuiDescription;
import party.lemons.ass.blockentity.util.MachineTier;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.EnderMagnetWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;

import javax.annotation.Nullable;


public class EnderMagnetBlockEntity extends AbstractPoweredMachineBlockEntity
{
	public EnderMagnetBlockEntity()
	{
		super(AssBlockEntities.ENDER_MAGNET);
	}

	@Override
	public void init()
	{
		super.init();
		machineTier = MachineTier.DIAMOND;
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

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new PowerOnlyGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}
}

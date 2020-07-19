package party.lemons.ass.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.util.MachineTier;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.init.AssBlocks;
import team.reborn.energy.EnergySide;

import javax.annotation.Nullable;

public class CreativeGeneratorBlockEntity extends GeneratorBlockEntity
{
	public CreativeGeneratorBlockEntity()
	{
		super(AssBlockEntities.CREATIVE_GENERATOR);
		machineTier = MachineTier.CREATIVE;
	}

	@Override
	public double getStored(EnergySide energySide)
	{
		return Double.MAX_VALUE;
	}

	@Override
	public Worker createWorker()
	{
		return null;
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
		return new TranslatableText(AssBlocks.CREATIVE_GENERATOR.getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return null;
	}
}

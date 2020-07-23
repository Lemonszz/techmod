package party.lemons.ass.blockentity.worker;

import net.minecraft.nbt.CompoundTag;
import party.lemons.ass.block.BubbleGeneratorBlock;
import party.lemons.ass.blockentity.BubbleGeneratorBlockEntity;

public class BubbleGeneratorWorker extends GeneratorWorker<BubbleGeneratorBlockEntity>
{
	public BubbleGeneratorWorker(BubbleGeneratorBlockEntity machine)
	{
		super(machine);
	}

	@Override
	public double getGeneratedPower()
	{
		return 1;
	}

	@Override
	public boolean canGeneratePower()
	{
		return machine.getCachedState().get(BubbleGeneratorBlock.ENABLED);
	}

	@Override
	public CompoundTag toTag()
	{
		return new CompoundTag();
	}

	@Override
	public void fromTag(CompoundTag tag)
	{

	}
}

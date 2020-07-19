package party.lemons.ass.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.tuple.Pair;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergySide;

import java.util.Collections;
import java.util.List;

public abstract class GeneratorBlockEntity extends AbstractPoweredMachineBlockEntity
{
	public GeneratorBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	@Override
	public void tick()
	{
		super.tick();
		transferEnergyOut();
	}

	protected void transferEnergyOut()
	{
		List<Pair<Direction, BlockEntity>> outputs = Lists.newArrayList();
		for(Direction dir : Direction.values())
		{
			BlockEntity be = world.getBlockEntity(pos.offset(dir));

			if(be != null && Energy.valid(be))
			{
				if(Energy.of(be).side(dir.getOpposite()).getMaxInput() > 0)
				{
					outputs.add(Pair.of(dir, be));
				}
			}
		}

		if(!outputs.isEmpty())
		{
			Collections.shuffle(outputs);
			for(Pair<Direction, BlockEntity> output : outputs)
			{
				Energy.of(this).into(Energy.of(output.getRight()).side(output.getLeft().getOpposite())).move();
			}
		}
	}

	@Override
	public double getMaxInput(EnergySide side)
	{
		return 0;
	}
}

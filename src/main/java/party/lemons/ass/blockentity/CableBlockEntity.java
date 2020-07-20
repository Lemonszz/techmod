package party.lemons.ass.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.tuple.Pair;
import party.lemons.ass.block.CableBlock;
import party.lemons.ass.init.AssBlockEntities;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

import java.util.Collections;
import java.util.List;

public class CableBlockEntity extends BlockEntity implements Tickable, EnergyStorage
{
	private double energy = 0;

	public CableBlockEntity()
	{
		super(AssBlockEntities.CABLE);
	}

	@Override
	public void tick()
	{
		if(energy == 0 || world.isClient())
			return;

		if(energy < 0.00005D)
		{
			energy = 0;
			return;
		}

		//TODO: Could cache connected cables/machines? Refresh on neighbour update?
		List<CableBlockEntity> connectedCables = Lists.newArrayList();
		List<Pair<Direction, BlockEntity>> connectedMachines = Lists.newArrayList();
		for(Direction dir : Direction.values())
		{
			if(getCachedState().get(CableBlock.FACING_PROPERTIES.get(dir)))
			{
				BlockEntity be = world.getBlockEntity(getPos().offset(dir));
				if(be != null && Energy.valid(be))
				{
					if(be instanceof CableBlockEntity)
					{
						CableBlockEntity otherCable = (CableBlockEntity) be;
						if(otherCable.energy < energy)
						{
							connectedCables.add(otherCable);
						}
					}
					else if(Energy.of(be).side(dir.getOpposite()).getMaxInput() > 0)
					{
						connectedMachines.add(Pair.of(dir, be));
					}
				}
			}
		}

		if(!connectedMachines.isEmpty())
		{
			Collections.shuffle(connectedMachines);
			for(Pair<Direction, BlockEntity> output : connectedMachines)
			{
				Energy.of(this).into(Energy.of(output.getRight()).side(output.getLeft().getOpposite())).move();
			}
		}

		if(!connectedCables.isEmpty())
		{
			connectedCables.add(this);
			double energyTotal = connectedCables.stream().mapToDouble(c->c.energy).sum();
			double energyShare = energyTotal / (double)connectedCables.size();

			connectedCables.forEach(c->c.setStored(energyShare));
		}
	}

	public boolean canAcceptEnergy(EnergySide direction) {
		return getMaxStoredPower() != energy;
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		tag.putDouble("Energy", energy);
		return super.toTag(tag);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);

		energy = tag.getDouble("Energy");
	}

	@Override
	public double getStored(EnergySide energySide)
	{
		return energy;
	}

	@Override
	public void setStored(double v)
	{
		this.energy = v;
	}

	@Override
	public double getMaxStoredPower()
	{
		return 512;
	}

	@Override
	public EnergyTier getTier()
	{
		return EnergyTier.MEDIUM;
	}

	@Override
	public double getMaxInput(EnergySide side)
	{
		if(!canAcceptEnergy(side))
			return 0;

		return 128;
	}

	@Override
	public double getMaxOutput(EnergySide side)
	{
		return 128;
	}
}

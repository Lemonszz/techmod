package party.lemons.ass.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import party.lemons.ass.blockentity.util.MachineTier;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

public abstract class AbstractPoweredMachineBlockEntity extends AbstractMachineBlockEntity implements EnergyStorage
{
	private double energy;

	public AbstractPoweredMachineBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		machineTier = MachineTier.GOLD;

		if(hasEnergyDelegate())
		{
			propertyDelegate.addProperty(0, ()->(int) energy, (v)->energy = v);
			propertyDelegate.addProperty(1, ()->(int) getMaxStoredPower(), (v)->{});
		}
	}

	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);

		energy = tag.getDouble("Energy");
	}

	@Override
	public double getMaxStoredPower()
	{
		return machineTier.maxEnergy;
	}

	@Override
	public double getMaxInput(EnergySide side)
	{
		return machineTier.energyTier.getMaxInput();
	}

	@Override
	public double getMaxOutput(EnergySide side)
	{
		return machineTier.energyTier.getMaxOutput();
	}

	@Override
	public EnergyTier getTier()
	{
		return machineTier.energyTier;
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

	public boolean hasEnergyDelegate()
	{
		return true;
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);

		tag.putDouble("Energy", energy);

		return tag;
	}
}

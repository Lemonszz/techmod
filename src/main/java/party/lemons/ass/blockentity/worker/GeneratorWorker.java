package party.lemons.ass.blockentity.worker;

import party.lemons.ass.blockentity.AbstractMachineBlockEntity;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;

public abstract class GeneratorWorker<T extends AbstractMachineBlockEntity & EnergyStorage> extends Worker<T>
{
	public GeneratorWorker(T machine)
	{
		super(machine);
	}

	@Override
	public void update()
	{
		if(canGeneratePower())
		{
			if(machine.getMaxStoredPower() == machine.getStored(EnergySide.UNKNOWN))
				return;

			if(machine.getStored(EnergySide.UNKNOWN) + getGeneratedPower() <= machine.getMaxStoredPower())
				machine.setStored(machine.getStored(EnergySide.UNKNOWN) + getGeneratedPower());
			else
				machine.setStored(machine.getMaxStoredPower());
		}
	}

	public abstract double getGeneratedPower();
	public abstract boolean canGeneratePower();
}

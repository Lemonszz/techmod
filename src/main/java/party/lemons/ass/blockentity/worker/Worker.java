package party.lemons.ass.blockentity.worker;

import net.minecraft.nbt.CompoundTag;
import party.lemons.ass.blockentity.AbstractMachineBlockEntity;

public abstract class Worker<T extends AbstractMachineBlockEntity>
{
	protected final T machine;

	public Worker(T machine)
	{
		this.machine = machine;
	}

	public abstract void update();

	public abstract CompoundTag toTag();
	public abstract void fromTag(CompoundTag tag);
}

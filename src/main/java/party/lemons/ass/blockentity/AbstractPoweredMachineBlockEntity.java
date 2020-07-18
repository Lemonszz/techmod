package party.lemons.ass.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public abstract class AbstractPoweredMachineBlockEntity extends AbstractMachineBlockEntity
{
	public AbstractPoweredMachineBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);


		//TODO:
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);


		//TODO:
		return tag;
	}
}

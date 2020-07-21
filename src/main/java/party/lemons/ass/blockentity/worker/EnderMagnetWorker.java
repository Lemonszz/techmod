package party.lemons.ass.blockentity.worker;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import party.lemons.ass.block.DirectionalBlock;
import party.lemons.ass.blockentity.EnderMagnetBlockEntity;
import party.lemons.ass.entity.FlyingBlockEntity;

public class EnderMagnetWorker extends Worker<EnderMagnetBlockEntity>
{
	private static final int range = 12;
	private final BlockPos finalPos;
	private final Direction facing;
	private int workTime;


	public EnderMagnetWorker(EnderMagnetBlockEntity machine)
	{
		super(machine);
		this.facing = machine.getCachedState().get(DirectionalBlock.FACING);
		this.finalPos = machine.getPos().offset(facing);
		this.workTime = 100;
	}

	@Override
	public void update()
	{
		if(machine.getWorld().isClient())
			return;

		workTime--;
		if(workTime <= 0)
		{
			workTime = 100;
			if(machine.getWorld().getBlockState(finalPos).isAir())
			{
				BlockPos targetPos = getTargetPos();
				if(targetPos == null)
					return;

				BlockState state = machine.getWorld().getBlockState(targetPos);
				if(state.getBlock().getPistonBehavior(state) == PistonBehavior.DESTROY)
				{
					BlockEntity blockEntity = state.getBlock().hasBlockEntity() ? machine.getWorld().getBlockEntity(targetPos) : null;
					PistonBlock.dropStacks(state, machine.getWorld(), targetPos, blockEntity);
				}
				else
				{
					FlyingBlockEntity flying = new FlyingBlockEntity(machine.getWorld(), finalPos, state);
					flying.setPos(targetPos.getX() + 0.5F, targetPos.getY(), targetPos.getZ() + 0.5F);
					machine.getWorld().spawnEntity(flying);
				}
				machine.getWorld().setBlockState(targetPos, Blocks.AIR.getDefaultState());
			}
		}
	}

	public BlockPos getTargetPos()
	{
		BlockPos.Mutable pos = new BlockPos.Mutable(finalPos.getX(), finalPos.getY(), finalPos.getZ());
		for(int i = 1; i < range; i++)
		{
			pos.move(facing);
			BlockState state = machine.getWorld().getBlockState(pos);
			if(state.isAir() || state.getMaterial().isReplaceable() || state.getMaterial().isLiquid())
				continue;

			if(!isMovable(state, machine.getWorld(), pos))
				return null;

			if(!state.canPlaceAt(machine.getWorld(), finalPos))
				return null;

			return pos;
		}
		return null;
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

	public static boolean isMovable(BlockState state, World world, BlockPos pos) {
		if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR))
		{
			if (!world.getWorldBorder().contains(pos))
			{
				return false;
			}
			else
			{
				if(!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON))
				{
					if (state.getHardness(world, pos) == -1.0F) {
						return false;
					}

					switch(state.getPistonBehavior()) {
						case BLOCK:
							return false;
						case DESTROY:
						case PUSH_ONLY:
							return true;
					}
				}
				else
				{
					if(!state.get(PistonBlock.EXTENDED))
					{
						return !state.getBlock().hasBlockEntity();
					}
					return false;
				}
				return !state.getBlock().hasBlockEntity();
			}
		} else {
			return false;
		}
	}
}

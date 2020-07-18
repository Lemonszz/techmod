package party.lemons.ass.block.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface RedstoneToggleable
{
	boolean isEnabled(BlockState state, World world, BlockPos pos);
}

package party.lemons.ass.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.EnderMagnetBlockEntity;

import javax.annotation.Nullable;

public class EnderMagnetBlock extends DirectionalBlock implements BlockEntityProvider, RedstoneToggleable
{
	public EnderMagnetBlock(Settings settings)
	{
		super(settings, PlacementMode.PLAYER_FACE_OPPOSITE);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new EnderMagnetBlockEntity();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}
}

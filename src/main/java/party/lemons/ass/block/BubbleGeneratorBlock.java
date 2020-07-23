package party.lemons.ass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.BubbleGeneratorBlockEntity;

import javax.annotation.Nullable;
import java.util.Random;

public class BubbleGeneratorBlock extends AssBlock implements BlockEntityProvider, RedstoneToggleable
{
	public static BooleanProperty ENABLED = Properties.ENABLED;

	public BubbleGeneratorBlock(Settings settings)
	{
		super(settings);

		this.setDefaultState(this.getStateManager().getDefaultState().with(ENABLED, false));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(state.get(ENABLED))
		{
			double x = pos.getX() + 0.5D;
			double y = pos.getY();
			double z = pos.getZ() + 0.5D;

			for(int i = 0; i < 3; i++)
			{
				world.addParticle(ParticleTypes.BUBBLE, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
				if(random.nextDouble() < 0.1D)
					world.addParticle(ParticleTypes.BUBBLE_POP, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
				if(random.nextDouble() < 0.2D)
					world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
	{
		if(direction == Direction.DOWN)
		{
			return state.with(ENABLED, newState.isOf(Blocks.BUBBLE_COLUMN));
		}
		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new BubbleGeneratorBlockEntity();
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(ENABLED);
	}
}

package party.lemons.ass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.SpoutBlockEntity;
import party.lemons.ass.init.AssBlockEntities;

import javax.annotation.Nullable;

public class SpoutBlock extends DirectionalBlock implements BlockEntityProvider
{
	public static final BooleanProperty ENABLED = Properties.ENABLED;

	private static VoxelShape SHAPE_NORTH = Block.createCuboidShape(4.5, 4.5, 0.0, 11.5, 11.5, 10F);
	private static VoxelShape SHAPE_SOUTH = Block.createCuboidShape(4.5, 4.5, 6.0, 11.5, 11.5, 16F);
	private static VoxelShape SHAPE_WEST = Block.createCuboidShape(0.0, 4.5, 4.5, 10, 11.5, 11.5F);
	private static VoxelShape SHAPE_EAST = Block.createCuboidShape(6, 4.5, 4.5, 16, 11.5, 11.5F);
	private static VoxelShape SHAPE_UP = Block.createCuboidShape(4.5, 6.0, 4.5, 11.5, 16.0, 11.5F);
	private static VoxelShape SHAPE_DOWN = Block.createCuboidShape(4.5, 0, 4.5, 11.5, 10.0, 11.5F);

	public SpoutBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(ENABLED, true));

	}

	private VoxelShape getShape(BlockState state)
	{
		switch(state.get(FACING))
		{
			case DOWN:
				return SHAPE_DOWN;
			case UP:
				return SHAPE_UP;
			case NORTH:
				return SHAPE_NORTH;
			case SOUTH:
				return SHAPE_SOUTH;
			case WEST:
				return SHAPE_WEST;
			case EAST:
				return SHAPE_EAST;
		}
		return SHAPE_NORTH;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return getShape(state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return getShape(state);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new SpoutBlockEntity(AssBlockEntities.SPOUT);
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) {
			this.updateEnabled(world, pos, state);
		}
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		this.updateEnabled(world, pos, state);
	}

	private void updateEnabled(World world, BlockPos pos, BlockState state) {
		boolean isPowered = !world.isReceivingRedstonePower(pos);
		if (isPowered != state.get(ENABLED)) {
			world.setBlockState(pos, state.with(ENABLED, isPowered), 4);
		}
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(ENABLED);
	}

}

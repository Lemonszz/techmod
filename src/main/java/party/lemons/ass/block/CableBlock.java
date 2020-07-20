package party.lemons.ass.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import party.lemons.ass.blockentity.CableBlockEntity;
import team.reborn.energy.Energy;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CableBlock extends AssBlock implements BlockEntityProvider, Waterloggable
{
	public CableBlock(Settings settings)
	{
		super(settings);

		this.setDefaultState(getStateManager().getDefaultState()
				.with(NORTH, false)
				.with(EAST, false)
				.with(SOUTH, false)
				.with(WEST, false)
				.with(UP, false)
				.with(DOWN, false)
				.with(WATERLOGGED, false));
	}


	@Override
	public BlockState getPlacementState(ItemPlacementContext context)
	{
		boolean down = canConnect(context.getWorld(), context.getBlockPos().down());
		boolean up = canConnect(context.getWorld(), context.getBlockPos().up());
		boolean north = canConnect(context.getWorld(), context.getBlockPos().north());
		boolean south = canConnect(context.getWorld(), context.getBlockPos().south());
		boolean east = canConnect(context.getWorld(), context.getBlockPos().east());
		boolean west = canConnect(context.getWorld(), context.getBlockPos().west());

		return getDefaultState().with(DOWN, down).with(UP, up).with(NORTH, north).with(SOUTH, south).with(EAST, east).with(WEST, west)
				.with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid() == Fluids.WATER);
	}

	public boolean canConnect(WorldAccess world, BlockPos pos)
	{
		BlockEntity be = world.getBlockEntity(pos);
		return be != null && (be instanceof CableBlockEntity || Energy.valid(be));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
	{
		if(state.get(WATERLOGGED))
		{
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return state.with(FACING_PROPERTIES.get(direction), canConnect(world, posFrom));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return getShape(state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(EAST, WEST, NORTH, SOUTH, UP, DOWN, WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new CableBlockEntity();
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	///Shape shit TODO: split into own class for other blocks
	//Based off Tech Reborn (MIT) coz im lazy xd
	private final HashMap<BlockState, VoxelShape> shapes = Util.make(Maps.newHashMap(), m->getStateManager().getStates().forEach(st->m.put(st, getShapeForState(st))));

	private VoxelShape getShapeForState(BlockState state)
	{
		double size = 6;
		VoxelShape base = Block.createCuboidShape(size, size, size, 16 - size, 16 - size, 16 - size);

		List<VoxelShape> connections = Lists.newArrayList();
		for(Direction dir : Direction.values())
		{
			if(state.get(FACING_PROPERTIES.get(dir)))
			{
				double x= dir == Direction.WEST ? 0 : dir == Direction.EAST ? 16D : size;
				double y= dir == Direction.DOWN ? 0 : dir == Direction.UP ? 16D : size;
				double z= dir == Direction.NORTH ? 0 : dir == Direction.SOUTH ? 16D : size;

				VoxelShape sh = Block.createCuboidShape(x, y, z, 16 - size, 16 - size, 16 - size);
				connections.add(sh);
			}
		}

		return VoxelShapes.union(base, connections.toArray(new VoxelShape[]{}));
	}

	public VoxelShape getShape(BlockState state)
	{
		return shapes.get(state);
	}

	public static final BooleanProperty NORTH = Properties.NORTH;
	public static final BooleanProperty EAST = Properties.EAST;
	public static final BooleanProperty SOUTH = Properties.SOUTH;
	public static final BooleanProperty WEST = Properties.WEST;
	public static final BooleanProperty UP = Properties.UP;
	public static final BooleanProperty DOWN = Properties.DOWN;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
		enumMap.put(Direction.NORTH, NORTH);
		enumMap.put(Direction.EAST, EAST);
		enumMap.put(Direction.SOUTH, SOUTH);
		enumMap.put(Direction.WEST, WEST);
		enumMap.put(Direction.UP, UP);
		enumMap.put(Direction.DOWN, DOWN);
	});
}

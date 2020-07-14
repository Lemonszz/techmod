package party.lemons.ass.block;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.util.registry.BlockWithItem;

public class HorizontalBlock extends Block implements BlockWithItem
{
	public static DirectionProperty FACING = AbstractFurnaceBlock.FACING;

	public HorizontalBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	public static BlockSide getSide(BlockState state, Direction hit)
	{
		if(!(state.getBlock() instanceof HorizontalBlock))
		{
			return BlockSide.fromDirection(hit);
		}

		if(hit.getAxis().isVertical() || state.get(FACING) == Direction.NORTH)
			return BlockSide.fromDirection(hit);

		switch(state.get(FACING))
		{
			case SOUTH:
				hit = hit.rotateYCounterclockwise();
				hit = hit.rotateYCounterclockwise();
				break;
			case WEST:
				hit = hit.rotateYClockwise();
				break;
			case EAST:
				hit = hit.rotateYCounterclockwise();
				break;
		}

		return BlockSide.fromDirection(hit);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

}

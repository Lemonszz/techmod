package party.lemons.ass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class DirectionalBlock extends AssBlock
{
	public enum PlacementMode{
		BLOCK_FACE,
		BLOCK_FACE_OPPOSITE,
		PLAYER_FACE,
		PLAYER_FACE_OPPOSITE
	}

	public static DirectionProperty FACING = Properties.FACING;
	private final PlacementMode placementMode;

	public DirectionalBlock(Settings settings, PlacementMode placementMode)
	{
		super(settings);
		this.placementMode = placementMode;
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		switch(placementMode)
		{
			case BLOCK_FACE:
				return getDefaultState().with(FACING, ctx.getSide());
			case BLOCK_FACE_OPPOSITE:
				return getDefaultState().with(FACING, ctx.getSide().getOpposite());
			case PLAYER_FACE:
				return getDefaultState().with(FACING, ctx.getPlayerLookDirection());
			case PLAYER_FACE_OPPOSITE:
				return getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
		}
		return getDefaultState();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
}

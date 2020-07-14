package party.lemons.ass.blockentity.util;

import com.google.common.collect.Lists;
import net.minecraft.util.math.Direction;

import java.util.List;

public enum BlockSide
{
	FRONT(Direction.NORTH),
	BACK(Direction.SOUTH),
	LEFT(Direction.EAST),
	RIGHT(Direction.WEST),
	TOP(Direction.UP),
	BOTTOM(Direction.DOWN);

	public final Direction defaultDirection;

	BlockSide(Direction defaultDirection)
	{
		this.defaultDirection = defaultDirection;
	}

	public static BlockSide fromDirection(Direction side)
	{
		for(BlockSide bs : values())
		{
			if(bs.defaultDirection == side)
				return bs;
		}

		return null;
	}

	public static BlockSide[] HORIZONTALS = {
		LEFT,RIGHT,FRONT,BACK
	};
	public static BlockSide[] VERTICALS = {
			TOP,BOTTOM
	};
}

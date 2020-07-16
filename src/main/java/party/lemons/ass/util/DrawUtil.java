package party.lemons.ass.util;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.minecraft.util.Identifier;

public final class DrawUtil
{
	public static void texturedRect(int x, int y, int width, int height, int x1, int y1, int x2, int y2, Identifier tex)
	{
		ScreenDrawing.texturedRect(x, y, width, height, tex, x1 / 256F, y1 / 256F, x2 / 256F, y2 / 256F, 0xFFFFFFFF);
	}

	private DrawUtil(){}
}

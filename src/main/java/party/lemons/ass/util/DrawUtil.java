package party.lemons.ass.util;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import party.lemons.ass.Ass;

public final class DrawUtil
{
	public static final Identifier ICON_TEXTURE = Ass.ID("textures/gui/icons.png");

	public static void texturedRect(int x, int y, int width, int height, int x1, int y1, int x2, int y2, Identifier tex)
	{
		ScreenDrawing.texturedRect(x, y, width, height, tex, x1 / 256F, y1 / 256F, x2 / 256F, y2 / 256F, 0xFFFFFFFF);
	}

	public static void powerIcon(int x, int y)
	{
		texturedRect(x, y, 8, 8, 0, 143, 8, 151, ICON_TEXTURE);
	}

	public static void fillGradient(MatrixStack matrices, int xStart, int yStart, int xEnd, int yEnd, int colorStart, int colorEnd) {
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
		fillGradient(matrices.peek().getModel(), bufferBuilder, xStart, yStart, xEnd, yEnd, 0, colorStart, colorEnd);
		tessellator.draw();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}

	protected static void fillGradient(Matrix4f matrix4f, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int i, int j, int k) {
		float f = (float)(j >> 24 & 255) / 255.0F;
		float g = (float)(j >> 16 & 255) / 255.0F;
		float h = (float)(j >> 8 & 255) / 255.0F;
		float l = (float)(j & 255) / 255.0F;
		float m = (float)(k >> 24 & 255) / 255.0F;
		float n = (float)(k >> 16 & 255) / 255.0F;
		float o = (float)(k >> 8 & 255) / 255.0F;
		float p = (float)(k & 255) / 255.0F;
		bufferBuilder.vertex(matrix4f, (float)xEnd, (float)yStart, (float)i).color(g, h, l, f).next();
		bufferBuilder.vertex(matrix4f, (float)xStart, (float)yStart, (float)i).color(g, h, l, f).next();
		bufferBuilder.vertex(matrix4f, (float)xStart, (float)yEnd, (float)i).color(n, o, p, m).next();
		bufferBuilder.vertex(matrix4f, (float)xEnd, (float)yEnd, (float)i).color(n, o, p, m).next();
	}

	private DrawUtil(){}
}

package party.lemons.ass.blockentity.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.util.Identifier;
import party.lemons.ass.Ass;
import party.lemons.ass.util.DrawUtil;

import java.util.List;
import java.util.function.IntSupplier;

public class HorizontalEnergyBarWidget extends WWidget
{

	private final IntSupplier maxEnergyGetter;
	private final IntSupplier energyGetter;

	public HorizontalEnergyBarWidget(IntSupplier maxEnergyGetter, IntSupplier energyGetter)
	{
		this.maxEnergyGetter = maxEnergyGetter;
		this.energyGetter = energyGetter;
		this.width = 54;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY)
	{
		super.paint(matrices, x, y, mouseX, mouseY);
		int dX = x;
		int dY = y + 4;

		DrawUtil.texturedRect(dX, dY, 54, 10, 0, 125, 54, 135, DrawUtil.ICON_TEXTURE);
		DrawUtil.powerIcon(dX - 9, dY + 1);

		if(energyGetter.getAsInt() > 0)
		{
			float perc =  (float)energyGetter.getAsInt() / (float)maxEnergyGetter.getAsInt();
			int width = (int)(52F * perc);
			DrawUtil.texturedRect(dX + 1, dY + 1, width, 8, 0, 135, width, 143, DrawUtil.ICON_TEXTURE);
		}

		if(isWithinBounds(mouseX, mouseY))
		{
			RenderSystem.disableDepthTest();
			int highlightX = dX + 1;
			int highlightY = dY + 1;
			RenderSystem.colorMask(true, true, true, false);
			DrawUtil.fillGradient(matrices, highlightX, highlightY, highlightX + 52, highlightY + 8, -2130706433, -2130706433);
			RenderSystem.colorMask(true, true, true, true);
			RenderSystem.enableDepthTest();
		}
	}

	@Override
	public void addTooltip(List<StringRenderable> tooltip)
	{
		super.addTooltip(tooltip);
		tooltip.add(new LiteralText(energyGetter.getAsInt() + "/" + maxEnergyGetter.getAsInt()));
	}
}

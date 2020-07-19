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

public class EnergyBarWidget extends WWidget
{
	private static final Identifier TEX = Ass.ID("textures/gui/icons.png");

	private final IntSupplier maxEnergyGetter;
	private final IntSupplier energyGetter;

	public EnergyBarWidget(IntSupplier maxEnergyGetter, IntSupplier energyGetter)
	{
		this.maxEnergyGetter = maxEnergyGetter;
		this.energyGetter = energyGetter;
		this.height = 54;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY)
	{
		super.paint(matrices, x, y, mouseX, mouseY);
		int dX = x + 4;
		int dY = y;

		DrawUtil.texturedRect(dX, dY, 10, 54, 0, 71, 10, 125, TEX);
		DrawUtil.powerIcon(dX + 1, dY - 9);

		if(energyGetter.getAsInt() > 0)
		{
			int amt =  energyGetter.getAsInt() * 52 / maxEnergyGetter.getAsInt();
			int diff = 52 - amt;

			DrawUtil.texturedRect(dX + 1, dY + 1 + diff, 8, 52 - diff, 10, 71, 18, 123, TEX);
		}

		if(isWithinBounds(mouseX, mouseY))
		{
			RenderSystem.disableDepthTest();
			int highlightX = dX + 1;
			int highlightY = dY + 1;
			RenderSystem.colorMask(true, true, true, false);
			DrawUtil.fillGradient(matrices, highlightX, highlightY, highlightX + 8, highlightY + 52, -2130706433, -2130706433);
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

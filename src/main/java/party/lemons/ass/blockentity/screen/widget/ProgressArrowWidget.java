package party.lemons.ass.blockentity.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import party.lemons.ass.Ass;
import party.lemons.ass.util.DrawUtil;

import java.util.List;
import java.util.function.IntSupplier;

public class ProgressArrowWidget extends WWidget
{
	private static final Identifier TEX = Ass.ID("textures/gui/icons.png");
	private final IntSupplier progress;
	private final IntSupplier progressMax;

	public ProgressArrowWidget(IntSupplier progress, IntSupplier progressMax)
	{
		this.progress = progress;
		this.progressMax = progressMax;
		width = 36;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY)
	{
		super.paint(matrices, x, y, mouseX, mouseY);

		DrawUtil.texturedRect(x + 7, y + 1, 22, 15, 0, 0, 22, 15, TEX);
		if(isWithinBounds(mouseX, mouseY))
		{
			DrawUtil.texturedRect(x + 7, y + 1, 22, 15, 22, 0, 44, 15, TEX);
		}

		float percent = (float)progress.getAsInt() / (float)progressMax.getAsInt();
		int width = (int)(22F * percent);
		if(width > 0)
			DrawUtil.texturedRect(x + 7, y + 1, width, 15, 0, 15, width, 31, TEX);
	}

	@Override
	public void addTooltip(List<StringRenderable> tooltip)
	{
		super.addTooltip(tooltip);

		int percent = (int)((float)progress.getAsInt() / (float)progressMax.getAsInt() * 100F);
		tooltip.add(new TranslatableText("info.progress.percent", percent));
	}
}

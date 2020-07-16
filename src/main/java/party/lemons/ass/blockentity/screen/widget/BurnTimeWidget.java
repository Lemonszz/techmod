package party.lemons.ass.blockentity.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Overwrite;
import party.lemons.ass.Ass;
import party.lemons.ass.util.DrawUtil;

import java.util.List;
import java.util.function.IntSupplier;

public class BurnTimeWidget extends WWidget
{
	private static final Identifier TEX = Ass.ID("textures/gui/icons.png");
	private final IntSupplier burnTime;
	private final IntSupplier burnTimeMax;

	public BurnTimeWidget(IntSupplier burnTime, IntSupplier burnTimeMax)
	{
		this.burnTime = burnTime;
		this.burnTimeMax = burnTimeMax;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY)
	{
		DrawUtil.texturedRect(x + 2, y + 2, 13, 13, 0, 31, 13, 44, TEX);

		int p = getFuelProgress();
		if(p > 0)
		{
			int diff = 12 - p;
			DrawUtil.texturedRect(x + 2, y + 2 + diff, 14, 14 - diff, 0, 44 + diff, 14, 57 + 1, TEX);
		}

		if(isWithinBounds(mouseX, mouseY))
		{
			DrawUtil.texturedRect(x+2, y+2, 13, 13, 0, 58, 13, 71, TEX);
		}

		super.paint(matrices, x, y, mouseX, mouseY);
	}

	@Override
	public void addTooltip(List<StringRenderable> tooltip)
	{
		super.addTooltip(tooltip);

		int seconds = burnTime.getAsInt() / 20;
		tooltip.add(new TranslatableText("info.burn.seconds", seconds));
	}

	public int getFuelProgress() {
		int i = burnTimeMax.getAsInt();
		if (i == 0) {
			i = 200;
		}

		return burnTime.getAsInt() * 13 / i;
	}

}

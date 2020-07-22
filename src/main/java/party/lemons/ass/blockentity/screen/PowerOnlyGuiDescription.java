package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.blockentity.screen.widget.HorizontalEnergyBarWidget;
import party.lemons.ass.init.AssScreens;

public class PowerOnlyGuiDescription extends SyncedGuiDescription
{
	public PowerOnlyGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.POWER_DISPLAY, syncId, playerInventory, getBlockInventory(ctx, 0), getBlockPropertyDelegate(ctx, 2));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);
		HorizontalEnergyBarWidget bar = new HorizontalEnergyBarWidget(()->getPropertyDelegate().get(1), ()->getPropertyDelegate().get(0));
		root.add(bar, 0, 1);
		bar.setLocation(bar.getX() + 4, bar.getY());

		root.validate(this);
	}

	@Override
	public boolean canUse(PlayerEntity entity)
	{
		return true;
	}
}

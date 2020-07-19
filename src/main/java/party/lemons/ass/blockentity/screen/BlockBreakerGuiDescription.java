package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.blockentity.screen.widget.EnergyBarWidget;
import party.lemons.ass.blockentity.screen.widget.HorizontalEnergyBarWidget;
import party.lemons.ass.init.AssScreens;

public class BlockBreakerGuiDescription extends SyncedGuiDescription
{
	public BlockBreakerGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.BLOCK_BREAKER, syncId, playerInventory, getBlockInventory(ctx, 1), getBlockPropertyDelegate(ctx, 2));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);

		root.add(new HorizontalEnergyBarWidget(()->propertyDelegate.get(1), ()->propertyDelegate.get(0)), 3, 2, 3, 1);

		WItemSlot toolSlot = WItemSlot.of(blockInventory, 0);
		root.add(toolSlot, 4, 1);

		root.add(createPlayerInventoryPanel(), 0, 3);

		root.validate(this);
	}
}

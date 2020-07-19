package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.blockentity.screen.widget.BurnTimeWidget;
import party.lemons.ass.blockentity.screen.widget.HorizontalEnergyBarWidget;
import party.lemons.ass.init.AssScreens;

public class FurnaceGeneratorGuiDescription extends SyncedGuiDescription
{
	public FurnaceGeneratorGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.FURNACE_GENERATOR, syncId, playerInventory, getBlockInventory(ctx, 1), getBlockPropertyDelegate(ctx, 4));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);

		root.add(new HorizontalEnergyBarWidget(()->propertyDelegate.get(1), ()->propertyDelegate.get(0)), 3, 3, 3, 1);
		root.add(new BurnTimeWidget(()->propertyDelegate.get(2), ()->propertyDelegate.get(3)), 4, 1);

		WItemSlot toolSlot = WItemSlot.of(blockInventory, 0);
		root.add(toolSlot, 4, 2);

		root.add(createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}
}
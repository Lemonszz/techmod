package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.blockentity.screen.widget.BurnTimeWidget;
import party.lemons.ass.blockentity.screen.widget.HorizontalEnergyBarWidget;
import party.lemons.ass.blockentity.screen.widget.ProgressArrowWidget;
import party.lemons.ass.init.AssScreens;

public class PoweredFurnaceGuiDescription extends SyncedGuiDescription
{

	public PoweredFurnaceGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.POWERED_FURNACE, syncId, playerInventory, getBlockInventory(ctx, 2), getBlockPropertyDelegate(ctx, 4));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);

		WItemSlot input = WItemSlot.of(blockInventory, 0);
		WItemSlot output = WItemSlot.of(blockInventory, 1).setInsertingAllowed(false);
		HorizontalEnergyBarWidget bar = new HorizontalEnergyBarWidget(()->getPropertyDelegate().get(1), ()->getPropertyDelegate().get(0));
		ProgressArrowWidget progress = new ProgressArrowWidget(()->getPropertyDelegate().get(2), ()->getPropertyDelegate().get(3));

		root.add(input, 3, 1);
		root.add(output, 6, 1);
		root.add(bar, 3, 2);
		root.add(progress, 4, 1, 1, 2);

		input.setLocation(input.getX() - 8, input.getY());
		output.setLocation(output.getX() - 8, output.getY());
		progress.setLocation(progress.getX() - 8, progress.getY());

		root.add(createPlayerInventoryPanel(), 0, 3);

		root.validate(this);
	}
}

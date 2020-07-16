package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.blockentity.screen.widget.BurnTimeWidget;
import party.lemons.ass.blockentity.screen.widget.ProgressArrowWidget;
import party.lemons.ass.init.AssScreens;

public class FueledFurnaceGuiDescription extends SyncedGuiDescription
{

	public FueledFurnaceGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.FUELED_FURNACE, syncId, playerInventory, getBlockInventory(ctx, 3), getBlockPropertyDelegate(ctx, 4));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);

		root.add( WItemSlot.of(blockInventory, 1).setFilter((is)->AbstractFurnaceBlockEntity.canUseAsFuel(is) || is.getItem() == Items.BUCKET), 3, 3);
		root.add(WItemSlot.of(blockInventory, 0), 3, 1);
		root.add(WItemSlot.outputOf(blockInventory, 2).setInsertingAllowed(false), 6, 2);

		root.add(new BurnTimeWidget(()->getPropertyDelegate().get(0), ()->getPropertyDelegate().get(1)), 3, 2);
		root.add(new ProgressArrowWidget(()->getPropertyDelegate().get(2), ()->getPropertyDelegate().get(3)), 4, 2, 1, 2);

		root.add(createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}
}

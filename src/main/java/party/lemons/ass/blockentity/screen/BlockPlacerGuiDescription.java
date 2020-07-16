package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import party.lemons.ass.init.AssScreens;

public class BlockPlacerGuiDescription extends SyncedGuiDescription
{
	public BlockPlacerGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx)
	{
		super(AssScreens.BLOCK_PLACER, syncId, playerInventory, getBlockInventory(ctx, 9), getBlockPropertyDelegate(ctx));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(0, 0);

		int ind = 0;
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				root.add(WItemSlot.of(blockInventory, ind), 3 + x, 1 + y);
				ind++;
			}
		}

		root.add(createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}
}

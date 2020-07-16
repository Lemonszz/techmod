package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class FueledFurnaceScreen extends CottonInventoryScreen<FueledFurnaceGuiDescription>
{
	public FueledFurnaceScreen(FueledFurnaceGuiDescription description, PlayerEntity player, Text title)
	{
		super(description, player, title);
	}
}

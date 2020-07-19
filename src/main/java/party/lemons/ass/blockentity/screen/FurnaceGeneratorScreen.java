package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class FurnaceGeneratorScreen extends CottonInventoryScreen<FurnaceGeneratorGuiDescription>
{
	public FurnaceGeneratorScreen(FurnaceGeneratorGuiDescription description, PlayerEntity player, Text title)
	{
		super(description, player, title);
	}
}

package party.lemons.ass.blockentity.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class GenericMachineScreen<T extends SyncedGuiDescription> extends CottonInventoryScreen<T>
{
	public GenericMachineScreen(T description, PlayerEntity player)
	{
		super(description, player);
	}

	public GenericMachineScreen(T description, PlayerEntity player, Text title)
	{
		super(description, player, title);
	}
}

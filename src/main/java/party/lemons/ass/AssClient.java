package party.lemons.ass;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import party.lemons.ass.blockentity.screen.*;
import party.lemons.ass.init.AssScreens;

public class AssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		//TODO: this could probably to automated
		register(AssScreens.BLOCK_BREAKER);
		register(AssScreens.FUELED_FURNACE);
		register(AssScreens.BLOCK_PLACER);
		register(AssScreens.FURNACE_GENERATOR);
		register(AssScreens.POWERED_FURNACE);
	}

	public static <H extends SyncedGuiDescription> void register(ScreenHandlerType<? extends H> type)
	{
		ScreenRegistry.register(type, (ScreenRegistry.Factory<H, CottonInventoryScreen<H>>) (h, playerInventory, text)->new GenericMachineScreen<H>(h, playerInventory.player, text));
	}
}

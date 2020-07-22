package party.lemons.ass;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import party.lemons.ass.blockentity.screen.*;
import party.lemons.ass.entity.render.FlyingBlockRenderer;
import party.lemons.ass.init.AssEntities;
import party.lemons.ass.init.AssNetwork;
import party.lemons.ass.init.AssScreens;
import party.lemons.ass.network.S2C_SpawnEntityCustom;

public class AssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		//TODO: this could probably to automated
		//TODO: also move it
		register(AssScreens.BLOCK_BREAKER);
		register(AssScreens.FUELED_FURNACE);
		register(AssScreens.BLOCK_PLACER);
		register(AssScreens.FURNACE_GENERATOR);
		register(AssScreens.POWERED_FURNACE);
		register(AssScreens.POWER_DISPLAY);

		//TODO: move this
		ClientSidePacketRegistry.INSTANCE.register(AssNetwork.SPAWN_ENTITY_CUSTOM, new S2C_SpawnEntityCustom());

		//TODO: and this
		EntityRendererRegistry.INSTANCE.register(AssEntities.FLYING_BLOCK, (r,c)->new FlyingBlockRenderer(r));
	}

	public static <H extends SyncedGuiDescription> void register(ScreenHandlerType<? extends H> type)
	{
		ScreenRegistry.register(type, (ScreenRegistry.Factory<H, CottonInventoryScreen<H>>) (h, playerInventory, text)->new GenericMachineScreen<>(h, playerInventory.player, text));
	}
}

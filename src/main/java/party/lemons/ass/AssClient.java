package party.lemons.ass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import party.lemons.ass.blockentity.screen.*;
import party.lemons.ass.init.AssScreens;

public class AssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ScreenRegistry.<BlockBreakerGuiDescription, BlockBreakerScreen>register(AssScreens.BLOCK_BREAKER, (g, i, t)->new BlockBreakerScreen(g,i.player, t));
		ScreenRegistry.<FueledFurnaceGuiDescription, FueledFurnaceScreen>register(AssScreens.FUELED_FURNACE, (g, i, t)->new FueledFurnaceScreen(g,i.player, t));
		ScreenRegistry.<BlockPlacerGuiDescription, BlockPlacerScreen>register(AssScreens.BLOCK_PLACER, (g, i, t)->new BlockPlacerScreen(g,i.player, t));
		ScreenRegistry.<FurnaceGeneratorGuiDescription, FurnaceGeneratorScreen>register(AssScreens.FURNACE_GENERATOR, (g, i, t)->new FurnaceGeneratorScreen(g,i.player, t));
		ScreenRegistry.<PoweredFurnaceGuiDescription, PoweredFurnaceScreen>register(AssScreens.POWERED_FURNACE, (g, i, t)->new PoweredFurnaceScreen(g,i.player, t));
	}
}

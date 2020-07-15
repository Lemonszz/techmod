package party.lemons.ass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import party.lemons.ass.blockentity.screen.BlockBreakerGuiDescription;
import party.lemons.ass.blockentity.screen.BlockBreakerScreen;
import party.lemons.ass.init.AssScreens;

public class AssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ScreenRegistry.<BlockBreakerGuiDescription, BlockBreakerScreen>register(AssScreens.BLOCK_BREAKER, (g, i, t)->new BlockBreakerScreen(g,i.player, t));
	}
}

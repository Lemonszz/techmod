package party.lemons.ass.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import party.lemons.ass.Ass;
import party.lemons.ass.blockentity.screen.BlockBreakerGuiDescription;

public class AssScreens
{
	public static ScreenHandlerType<BlockBreakerGuiDescription> BLOCK_BREAKER;

	public static void init()
	{
		BLOCK_BREAKER = ScreenHandlerRegistry.registerSimple(Ass.ID("block_breaker"), (id, inv)-> new BlockBreakerGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
	}
}

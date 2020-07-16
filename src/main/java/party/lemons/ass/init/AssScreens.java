package party.lemons.ass.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import party.lemons.ass.Ass;
import party.lemons.ass.blockentity.screen.BlockBreakerGuiDescription;
import party.lemons.ass.blockentity.screen.FueledFurnaceGuiDescription;

public class AssScreens
{
	public static ScreenHandlerType<BlockBreakerGuiDescription> BLOCK_BREAKER;
	public static ScreenHandlerType<FueledFurnaceGuiDescription> FUELED_FURNACE;

	public static void init()
	{
		BLOCK_BREAKER = ScreenHandlerRegistry.registerSimple(Ass.ID("block_breaker"), (id, inv)-> new BlockBreakerGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
		FUELED_FURNACE = ScreenHandlerRegistry.registerSimple(Ass.ID("fueled_furnace"), (id, inv)-> new FueledFurnaceGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
	}
}

package party.lemons.ass.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.world.gen.placer.BlockPlacer;
import party.lemons.ass.Ass;
import party.lemons.ass.blockentity.screen.BlockBreakerGuiDescription;
import party.lemons.ass.blockentity.screen.BlockPlacerGuiDescription;
import party.lemons.ass.blockentity.screen.FueledFurnaceGuiDescription;

public class AssScreens
{
	public static ScreenHandlerType<BlockBreakerGuiDescription> BLOCK_BREAKER;
	public static ScreenHandlerType<FueledFurnaceGuiDescription> FUELED_FURNACE;
	public static ScreenHandlerType<BlockPlacerGuiDescription> BLOCK_PLACER;

	public static void init()
	{
		BLOCK_BREAKER = ScreenHandlerRegistry.registerSimple(Ass.ID("block_breaker"), (id, inv)-> new BlockBreakerGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
		FUELED_FURNACE = ScreenHandlerRegistry.registerSimple(Ass.ID("fueled_furnace"), (id, inv)-> new FueledFurnaceGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
		BLOCK_PLACER = ScreenHandlerRegistry.registerSimple(Ass.ID("block_placer"), (id, inv)-> new BlockPlacerGuiDescription(id, inv, ScreenHandlerContext.EMPTY));
	}
}

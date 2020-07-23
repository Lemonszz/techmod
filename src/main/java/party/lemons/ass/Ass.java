package party.lemons.ass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import party.lemons.ass.init.AssBlocks;
import party.lemons.ass.init.AssScreens;
import party.lemons.ass.util.registry.RegistryLoader;

public class Ass implements ModInitializer
{
	public static final String MODID = "ass";

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(ID(MODID), ()->new ItemStack(AssBlocks.SCORCHED_FURNACE));

	@Override
	public void onInitialize()
	{
		RegistryLoader.registerPackage("party.lemons.ass");
		RegistryLoader.init();

		AssScreens.init();
	}

	public static Identifier ID(String path)
	{
		return new Identifier(MODID, path);
	}
}

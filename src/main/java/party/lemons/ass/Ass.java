package party.lemons.ass;

import net.fabricmc.api.ModInitializer;
import party.lemons.ass.util.registry.RegistryLoader;

public class Ass implements ModInitializer
{
	public static final String MODID = "ass";

	@Override
	public void onInitialize()
	{
		RegistryLoader.registerPackage("party.lemons.ass");
		RegistryLoader.init();
	}
}

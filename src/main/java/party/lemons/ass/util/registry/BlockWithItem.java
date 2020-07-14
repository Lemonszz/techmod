package party.lemons.ass.util.registry;

import net.minecraft.item.Item;
import party.lemons.ass.init.AssItems;

public interface BlockWithItem
{
	default boolean hasItem()
	{
		return true;
	}

	default Item.Settings makeItemSettings(){
		return AssItems.settings();
	}
}
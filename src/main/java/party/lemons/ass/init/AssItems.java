package party.lemons.ass.init;

import net.minecraft.item.Item;
import party.lemons.ass.Ass;

public class AssItems
{
	public static Item.Settings settings()
	{
		return new Item.Settings().group(Ass.ITEM_GROUP);
	}
}

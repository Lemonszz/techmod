package party.lemons.ass.init;

import net.minecraft.block.TorchBlock;
import net.minecraft.item.Item;
import party.lemons.ass.Ass;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(type = Item.class, registry = "item")
public class AssItems
{
	public static Item.Settings settings()
	{
		return new Item.Settings().group(Ass.ITEM_GROUP);
	}
}

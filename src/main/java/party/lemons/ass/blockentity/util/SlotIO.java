package party.lemons.ass.blockentity.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Map;

public class SlotIO
{
	private Map<BlockSide, List<Integer>> io = Maps.newHashMap();

	public SlotIO()
	{
	}

	public int[] getAvailableSlots(BlockSide side)
	{
		if(io.containsKey(side))
		{
			List<Integer> inventorySlots = io.get(side);
			int[] slots = new int[inventorySlots.size()];

			for(int i = 0; i < inventorySlots.size(); i++)
			{
				slots[i] = inventorySlots.get(i);
			}
			return slots;
		}

		return new int[0];
	}

	public boolean canInsert(int index, BlockSide side)
	{
		return contains(side, index);
	}

	public boolean canExtract(int index, BlockSide side)
	{
		return contains(side, index);
	}

	private boolean contains(BlockSide side, int index)
	{
		if(io.containsKey(side))
		{
			return io.get(side).contains(index);
		}
		return false;
	}

	public SideBuilder addSide(BlockSide... side)
	{
		return new SideBuilder(this, side);
	}

	public class SideBuilder
	{
		private BlockSide[] sides;
		private SlotIO parent;
		private List<Integer> slots;

		private SideBuilder(SlotIO parent, BlockSide... sides)
		{
			this.sides = sides;
			this.parent = parent;
			slots = Lists.newArrayList();
		}

		public SideBuilder slot(int index)
		{
			slots.add(index);
			return this;
		}

		public SideBuilder range(int min, int max)
		{
			for(int i = min; i < max; i++)
			{
				slot(i);
			}

			return this;
		}

		public SideBuilder all(Inventory inventory)
		{
			for(int i = 0; i < inventory.size(); i++)
			{
				slot(i);
			}
			return this;
		}

		public SlotIO build()
		{
			for(BlockSide side : sides)
			{
				if(parent.io.containsKey(side))
				{
					parent.io.get(side).addAll(slots);
				}else
				{
					parent.io.put(side, slots);
				}
			}
			return parent;
		}
	}
}

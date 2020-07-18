package party.lemons.ass.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public final class InventoryUtil
{
	public static ListTag toTag(Inventory inventory)
	{
		ListTag listTag = new ListTag();

		for(int i = 0; i < inventory.size(); ++i) {
			ItemStack itemStack = inventory.getStack(i);
			listTag.add(itemStack.toTag(new CompoundTag()));
		}

		return listTag;
	}

	public static void readTag(Inventory inventory, ListTag tags)
	{
		for(int i = 0; i < tags.size(); ++i) {
			ItemStack itemStack = ItemStack.fromTag(tags.getCompound(i));
			inventory.setStack(i, itemStack);
		}
	}

	@Nullable
	public static Inventory getInventoryAt(World world, BlockPos blockPos)
	{
		Inventory inventory = null;
		BlockState blockState = world.getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (block instanceof InventoryProvider) {
			inventory = ((InventoryProvider)block).getInventory(blockState, world, blockPos);
		} else if (block.hasBlockEntity()) {
			BlockEntity blockEntity = world.getBlockEntity(blockPos);
			if (blockEntity instanceof Inventory) {
				inventory = (Inventory)blockEntity;
				if (inventory instanceof ChestBlockEntity && block instanceof ChestBlock) {
					inventory = ChestBlock.getInventory((ChestBlock)block, blockState, world, blockPos, true);
				}
			}
		}

		return inventory;
	}

	private InventoryUtil(){}
}

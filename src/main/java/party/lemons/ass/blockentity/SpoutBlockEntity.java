package party.lemons.ass.blockentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import party.lemons.ass.block.SpoutBlock;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class SpoutBlockEntity extends BlockEntity implements Tickable
{
	private int dropTime = 20;

	public SpoutBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	@Override
	public void tick()
	{
		if(!getCachedState().get(SpoutBlock.ENABLED))
			return;

		dropTime--;
		if(dropTime <= 0)
		{
			dropTime = 20;
			BlockState thisState = world.getBlockState(getPos());
			Direction dir = thisState.get(SpoutBlock.FACING);
			BlockPos pos = getPos().offset(dir);

			Inventory inv = getInventoryAt(world, pos);
			if(inv != null)
			{
				Direction extractDir = dir.getOpposite();

				if(!isInventoryEmpty(inv, extractDir))
				{
					for(int i : getAvailableSlots(inv, extractDir).toArray())
					{
						if(extract(inv, i, extractDir, world, getPos()))
						{
							break;
						}
					}
				}
			}
		}
	}

	private static boolean extract(Inventory inventory, int slot, Direction side, World world, BlockPos spoutPos) {
		ItemStack itemStack = inventory.getStack(slot);
		if (!itemStack.isEmpty() && canExtract(inventory, itemStack, slot, side)) {
			ItemStack itemStack3 = inventory.removeStack(slot, 1);
			if (itemStack3.isEmpty()) {
				inventory.markDirty();
				return true;
			}
			else
			{
				float x = spoutPos.getX() + 0.5F + (side.getOffsetX() / 4F);
				float y = spoutPos.getY() + 0.3F + (side == Direction.UP ? 0.6F : side == Direction.DOWN ? -0.6F : 0);
				float z = spoutPos.getZ() + 0.5F + (side.getOffsetZ() / 4F);

				ItemEntity item = new ItemEntity(world,x, y, z,itemStack3);
				item.setVelocity(Vec3d.ZERO);
				world.spawnEntity(item);
				return true;
			}
		}

		return false;
	}

	private static boolean canExtract(Inventory inv, ItemStack stack, int slot, Direction facing) {
		return !(inv instanceof SidedInventory) || ((SidedInventory)inv).canExtract(slot, stack, facing);
	}

	private static boolean isInventoryEmpty(Inventory inv, Direction facing) {
		return getAvailableSlots(inv, facing).allMatch((i) ->inv.getStack(i).isEmpty());
	}

	private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
		return inventory instanceof SidedInventory ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(side)) : IntStream.range(0, inventory.size());
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
}

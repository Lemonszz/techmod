package party.lemons.ass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.AbstractMachineBlockEntity;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.util.registry.BlockWithItem;

import javax.annotation.Nullable;

public class AssBlock extends Block implements BlockWithItem
{
	public AssBlock(Settings settings)
	{
		super(settings);
	}

	@Nullable
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}

	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof InventorySource) {
				ItemScatterer.spawn(world, pos, ((InventorySource) blockEntity).getInventory());
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}
}

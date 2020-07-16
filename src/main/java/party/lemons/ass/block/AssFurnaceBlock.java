package party.lemons.ass.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.AbstractMachineBlockEntity;

import javax.annotation.Nullable;

public abstract class AssFurnaceBlock extends HorizontalBlock implements BlockEntityProvider
{
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

	public AssFurnaceBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}

	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof AbstractMachineBlockEntity) {
				ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
	}

	@Nullable
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(LIT);
	}

}

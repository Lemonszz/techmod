package party.lemons.ass.block;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.FurnaceGeneratorBlockEntity;

import javax.annotation.Nullable;

public class FurnaceGeneratorBlock extends HorizontalBlock implements BlockEntityProvider, RedstoneToggleable
{
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

	public FurnaceGeneratorBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));

	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new FurnaceGeneratorBlockEntity();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(LIT);
	}
}

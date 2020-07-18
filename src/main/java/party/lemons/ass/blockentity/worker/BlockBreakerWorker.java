package party.lemons.ass.blockentity.worker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import party.lemons.ass.block.HorizontalBlock;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.BlockBreakerBlockEntity;
import party.lemons.ass.util.fakeplayer.FakePlayer;

public class BlockBreakerWorker extends Worker<BlockBreakerBlockEntity>
{
	private static FakePlayer fakePlayer;

	private float progress = 0;
	private BlockState breakingState = Blocks.AIR.getDefaultState();
	private ItemStack usingStack = ItemStack.EMPTY;
	private float breakDelta;
	private int fakeEntityID;

	public BlockBreakerWorker(BlockBreakerBlockEntity machine)
	{
		super(machine);

		fakeEntityID = -machine.getWorld().random.nextInt(Integer.MAX_VALUE);

		if(fakePlayer == null && !machine.getWorld().isClient)
		{
			fakePlayer = FakePlayer.createInvisibleFake("block_breaker", machine.getWorld().getServer(), (ServerWorld) machine.getWorld());
			fakePlayer.inventory.selectedSlot = 0;
		}
	}

	@Override
	public void update()
	{
		//TODO: power
		if(!machine.getWorld().isClient && isEnabled() && hasBlock())
		{
			BlockState checkState = getBreakingState();
			BlockPos breakPos = getBreakingPosition();

			if(breakingState.isAir() || breakingState != checkState || getStack() != usingStack)
			{
				usingStack = getStack();
				breakingState = checkState;
				updateStateInformation();
			}

			progress += breakDelta;
			machine.getWorld().setBlockBreakingInfo(fakeEntityID, breakPos, (int)(progress * 10F));

			if(progress >= 1)
			{
				Block block = checkState.getBlock();

				block.onBreak(machine.getWorld(), breakPos, breakingState, fakePlayer);
				boolean wasRemoved = machine.getWorld().removeBlock(breakPos, false);
				if (wasRemoved) {
					block.onBroken(machine.getWorld(), breakPos, checkState);
				}

				fakePlayer.inventory.setStack(0, getStack());
				ItemStack useStack = fakePlayer.getMainHandStack();
				ItemStack originalStack = useStack.copy();
				boolean isCorrectTool = fakePlayer.isUsingEffectiveTool(breakingState);

				useStack.postMine(machine.getWorld(), breakingState, getBreakingPosition(), fakePlayer);
				if (wasRemoved && isCorrectTool) {
					block.afterBreak(machine.getWorld(), fakePlayer, breakPos, breakingState, machine, originalStack);
				}

				progress = 0;
				breakingState = Blocks.AIR.getDefaultState();
			}
		}
	}

	private boolean isEnabled()
	{
		Block bl = machine.getCachedState().getBlock();
		if(bl instanceof RedstoneToggleable)
			return ((RedstoneToggleable)bl).isEnabled(machine.getCachedState(), machine.getWorld(), machine.getPos());

		return true;
	}

	private void updateStateInformation()
	{
		progress = 0;
		float hardness = breakingState.getHardness(machine.getWorld(), getBreakingPosition());
		if (hardness == -1.0F)
		{
			breakDelta = 0;
		}
		else
		{
			fakePlayer.inventory.setStack(0, getStack());
			int div = fakePlayer.isUsingEffectiveTool(breakingState) ? 30 : 100;

			breakDelta = fakePlayer.getBlockBreakingSpeed(breakingState) / hardness / (float)div;
		}
	}

	public boolean hasBlock()
	{
		return !machine.getWorld().isAir(getBreakingPosition());
	}

	public BlockPos getBreakingPosition()
	{
		Direction facing = machine.getCachedState().get(HorizontalBlock.FACING);
		BlockPos pos = machine.getPos().offset(facing);

		return pos;
	}

	public BlockState getBreakingState()
	{
		return machine.getWorld().getBlockState(getBreakingPosition());
	}

	@Override
	public CompoundTag toTag()
	{
		//TODO:
		return new CompoundTag();
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		//TODO:
	}

	public ItemStack getStack()
	{
		return machine.getInventory().getStack(0);
	}
}

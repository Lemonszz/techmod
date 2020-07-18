package party.lemons.ass.blockentity.worker;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import party.lemons.ass.block.DirectionalBlock;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.BlockPlacerBlockEntity;
import party.lemons.ass.util.fakeplayer.FakePlayer;

import java.util.Objects;
import java.util.Random;

public class BlockPlacerWorker extends Worker<BlockPlacerBlockEntity>
{
	private static FakePlayer fakePlayer;

	private static final Random RANDOM = new Random();
	private float placeTime = 60;
	private float placeTimeMax = 60;

	public BlockPlacerWorker(BlockPlacerBlockEntity machine)
	{
		super(machine);

		if(fakePlayer == null && machine.getWorld() != null && !machine.getWorld().isClient)
		{
			fakePlayer = FakePlayer.createInvisibleFake("block_breaker", machine.getWorld().getServer(), (ServerWorld) machine.getWorld());
		}
	}

	private boolean isEnabled()
	{
		Block bl = machine.getCachedState().getBlock();
		if(bl instanceof RedstoneToggleable)
			return ((RedstoneToggleable)bl).isEnabled(machine.getCachedState(), machine.getWorld(), machine.getPos());

		return true;
	}

	@Override
	public void update()
	{
		if(!Objects.requireNonNull(machine.getWorld()).isClient() && hasBlock() && isEnabled())
		{
			//TODO: power
			placeTime -= machine.getWorkSpeedMultiplier();

			if(placeTime <= 0)
			{
				placeTime = placeTimeMax;
				placeBlock();
			}
		}
	}

	public void placeBlock()
	{
		ItemStack selectStack = selectItemStack();

		if(selectStack.isEmpty())
			Objects.requireNonNull(machine.getWorld()).syncWorldEvent(1001, machine.getPos(), 0);

		BlockItem item = (BlockItem) selectStack.getItem();
		Direction dir = machine.getCachedState().get(DirectionalBlock.FACING);

		BlockPos placePos = machine.getPos().offset(dir);
		fakePlayer.setPos(machine.getPos().getX(), machine.getPos().getY(), machine.getPos().getZ());
		fakePlayer.yaw = dir.asRotation();
		fakePlayer.headYaw = dir.asRotation();
		if(dir.getAxis().isVertical())
			fakePlayer.pitch = dir.asRotation();
		else
			fakePlayer.pitch = 0;

		BlockPos offsetPlacePos = placePos.offset(dir);
		ItemPlacementContext ctx = new ItemPlacementContext(fakePlayer, Hand.MAIN_HAND, selectStack, new BlockHitResult(new Vec3d(offsetPlacePos.getX(), offsetPlacePos.getY(), offsetPlacePos.getZ()), dir.getOpposite(), placePos, false));
		item.useOnBlock(ctx);
	}

	private ItemStack selectItemStack()
	{
		Inventory inv = machine;
		if(inv.isEmpty())
			return ItemStack.EMPTY;

		int slot = -1;
		int increasingChance = 1;

		for(int i = 0; i < inv.size(); ++i)
		{
			ItemStack st = inv.getStack(i);
			if (!st.isEmpty() && st.getItem() instanceof BlockItem && RANDOM.nextInt(increasingChance++) == 0)
			{
				slot = i;
			}
		}

		return slot < 0 ? ItemStack.EMPTY : inv.getStack(slot);
	}

	private boolean hasBlock()
	{
		Inventory inv = machine;
		if(inv.isEmpty())
			return false;

		for(int i = 0; i < inv.size(); i++)
		{
			ItemStack checkStack = inv.getStack(i);
			if(!checkStack.isEmpty() && checkStack.getItem() instanceof BlockItem)
				return true;
		}

		return false;
	}

	@Override
	public CompoundTag toTag()
	{
		return null;
	}

	@Override
	public void fromTag(CompoundTag tag)
	{

	}
}

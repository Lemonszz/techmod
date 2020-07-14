package party.lemons.ass.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import party.lemons.ass.blockentity.BlockBreakerBlockEntity;

import javax.annotation.Nullable;

public class BlockBreakerBlock extends HorizontalBlock implements BlockEntityProvider
{
	public BlockBreakerBlock(Settings settings)
	{
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new BlockBreakerBlockEntity();
	}
}

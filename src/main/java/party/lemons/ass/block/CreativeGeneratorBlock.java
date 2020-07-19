package party.lemons.ass.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.CreativeGeneratorBlockEntity;

import javax.annotation.Nullable;

public class CreativeGeneratorBlock extends AssBlock implements BlockEntityProvider, RedstoneToggleable
{
	public CreativeGeneratorBlock(Settings settings)
	{
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new CreativeGeneratorBlockEntity();
	}
}

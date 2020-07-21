package party.lemons.ass.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.blockentity.EnderMagnetBlockEntity;

import javax.annotation.Nullable;

public class EnderMagnetBlock extends DirectionalBlock implements BlockEntityProvider, RedstoneToggleable
{
	public EnderMagnetBlock(Settings settings)
	{
		super(settings, PlacementMode.PLAYER_FACE_OPPOSITE);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new EnderMagnetBlockEntity();
	}
}

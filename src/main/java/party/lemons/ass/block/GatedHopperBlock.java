package party.lemons.ass.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.ass.block.util.RedstoneToggleable;
import party.lemons.ass.util.registry.BlockWithItem;

public class GatedHopperBlock extends HopperBlock implements BlockWithItem, RedstoneToggleable
{
	public GatedHopperBlock(Settings settings)
	{
		super(settings);

		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof HopperBlockEntity) {
			((HopperBlockEntity)blockEntity).setCustomName(new TranslatableText("gui.gated_hopper"));
		}
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	public boolean isEnabled(BlockState state, World world, BlockPos pos)
	{
		return state.get(ENABLED);
	}
}

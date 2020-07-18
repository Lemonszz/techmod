package party.lemons.ass.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.block.util.ExperienceSucker;
import party.lemons.ass.blockentity.ExperienceTorchBlockEntity;

import javax.annotation.Nullable;

public class ExperienceTorchBlock extends DirectionalBlock implements ExperienceSucker, BlockEntityProvider
{
	private static VoxelShape SHAPE_NORTH = Block.createCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 10F);
	private static VoxelShape SHAPE_SOUTH = Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 10.0, 16F);
	private static VoxelShape SHAPE_WEST = Block.createCuboidShape(0.0, 6.0, 6.0, 10, 10.0, 10.0F);
	private static VoxelShape SHAPE_EAST = Block.createCuboidShape(6, 6.0, 6.0, 16, 10.0, 10.0F);
	private static VoxelShape SHAPE_DOWN = Block.createCuboidShape(6.0, 0, 6.0, 10.0, 10.0, 10.0F);
	private static VoxelShape SHAPE_UP = Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 16.0, 10.0F);

	public ExperienceTorchBlock(Settings settings)
	{
		super(settings, PlacementMode.BLOCK_FACE_OPPOSITE);
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if(entity instanceof ExperienceOrbEntity)
		{
			BlockEntity be = world.getBlockEntity(pos);
			if(be instanceof ExperienceTorchBlockEntity)
				((ExperienceTorchBlockEntity)be).pickup((ExperienceOrbEntity) entity);
		}

		super.onEntityCollision(state, world, pos, entity);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch(state.get(FACING))
		{
			case DOWN:
				return SHAPE_DOWN;
			case UP:
				return SHAPE_UP;
			case NORTH:
				return SHAPE_NORTH;
			case SOUTH:
				return SHAPE_SOUTH;
			case WEST:
				return SHAPE_WEST;
			case EAST:
				return SHAPE_EAST;
		}
		return SHAPE_NORTH;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new ExperienceTorchBlockEntity();
	}
}

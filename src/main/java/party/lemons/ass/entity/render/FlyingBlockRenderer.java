package party.lemons.ass.entity.render;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FlyingBlockRenderer extends EntityRenderer<FallingBlockEntity>
{
	public FlyingBlockRenderer(EntityRenderDispatcher r)
	{
		super(r);
	}

	public void render(FallingBlockEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		BlockState blockState = fallingBlockEntity.getBlockState();
		if (blockState.getRenderType() == BlockRenderType.MODEL) {
			World world = fallingBlockEntity.getWorldClient();
			if (blockState != world.getBlockState(fallingBlockEntity.getBlockPos()) && blockState.getRenderType() != BlockRenderType.INVISIBLE) {
				matrixStack.push();
				BlockPos blockPos = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
				matrixStack.translate(-0.5D, 0.0D, -0.5D);
				BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
				blockRenderManager.getModelRenderer().render(world, blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.method_29359(blockState)), false, new Random(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
				matrixStack.pop();
				super.render(fallingBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
			}
		}
	}

	public Identifier getTexture(FallingBlockEntity fallingBlockEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEX;
	}
}

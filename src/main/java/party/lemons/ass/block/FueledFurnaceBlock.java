package party.lemons.ass.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.SmokerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.FueledFurnaceBlockEntity;
import party.lemons.ass.blockentity.util.MachineTier;

import javax.annotation.Nullable;
import java.util.Random;

public class FueledFurnaceBlock extends AssFurnaceBlock
{
	private final RecipeType<? extends AbstractCookingRecipe> recipeType;
	private final Lazy<BlockEntityType<?>> type;
	private final MachineTier tier;

	public FueledFurnaceBlock(RecipeType<? extends AbstractCookingRecipe> recipeType, Lazy<BlockEntityType<?>> type, MachineTier tier, FabricBlockSettings settings)
	{
		super(settings);

		this.recipeType = recipeType;
		this.type = type;
		this.tier = tier;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(!state.get(LIT))
			return;

		double x = pos.getX() + 0.5D;
		double y = pos.getY();
		double z = pos.getZ() + 0.5D;
		Direction direction = state.get(FACING);
		Direction.Axis axis = direction.getAxis();
		SoundEvent sound = null;

		if(recipeType == RecipeType.SMELTING) //Furnace
		{
			sound = SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE;
			double offset = 0.52D;

			for(int i = 0; i < 3; i++)
			{
				double altOffset = random.nextDouble() * 0.6D - 0.3D;
				double xOffset = axis == Direction.Axis.X ? (double) direction.getOffsetX() * offset : altOffset;
				double yOffset = random.nextDouble() * 6.0D / 16.0D;
				double zOffset = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * offset : altOffset;
				world.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
			}
		}
		else if(recipeType == RecipeType.BLASTING)
		{
			sound = SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE;
			double offset = 0.52D;
			for(int i = 0; i < 3; i++)
			{
				double altOffset = random.nextDouble() * 0.6D - 0.3D;
				double xOffset = axis == Direction.Axis.X ? (double) direction.getOffsetX() * offset : altOffset;
				double yOffset = random.nextDouble() * 6.0D / 16.0D;
				double zOffset = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * offset : altOffset;
				world.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
			}
		}
		else if(recipeType == RecipeType.SMOKING)
		{
			sound = SoundEvents.BLOCK_SMOKER_SMOKE;
			for(int i = 0; i < 3; i++)
			{
				world.addParticle(ParticleTypes.SMOKE, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
				if(random.nextDouble() < 0.1D)
					world.addParticle(ParticleTypes.FLAME, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
			}
		}

		if(sound != null && random.nextDouble() < 0.1D)
		{
			world.playSound(x, y, z, sound, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		FueledFurnaceBlockEntity be = new FueledFurnaceBlockEntity(recipeType, type.get());
		be.upgrade(tier);
		return be;
	}
}

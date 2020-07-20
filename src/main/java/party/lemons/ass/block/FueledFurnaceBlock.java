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
	public FueledFurnaceBlock(RecipeType<? extends AbstractCookingRecipe> recipeType, Lazy<BlockEntityType<?>> type, FabricBlockSettings settings)
	{
		super(recipeType, type, settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		FueledFurnaceBlockEntity be = new FueledFurnaceBlockEntity(recipeType, type.get());
		return be;
	}
}

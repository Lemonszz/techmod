package party.lemons.ass.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.FueledFurnaceBlockEntity;
import party.lemons.ass.init.AssBlockEntities;

import javax.annotation.Nullable;

public class FueledFurnaceBlock extends AssFurnaceBlock
{
	private final RecipeType<? extends AbstractCookingRecipe> recipeType;
	private final Lazy<BlockEntityType<?>> type;

	public FueledFurnaceBlock(RecipeType<? extends AbstractCookingRecipe> recipeType, Lazy<BlockEntityType<?>> type, FabricBlockSettings settings)
	{
		super(settings);

		this.recipeType = recipeType;
		this.type = type;
	}

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player)
	{

	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new FueledFurnaceBlockEntity(recipeType, type.get());
	}
}

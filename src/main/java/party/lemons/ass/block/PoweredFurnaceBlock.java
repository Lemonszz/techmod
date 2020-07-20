package party.lemons.ass.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Lazy;
import net.minecraft.world.BlockView;
import party.lemons.ass.blockentity.PoweredFurnaceBlockEntity;

import javax.annotation.Nullable;

public class PoweredFurnaceBlock extends AssFurnaceBlock
{
	public PoweredFurnaceBlock(RecipeType<? extends AbstractCookingRecipe> recipeType, Lazy<BlockEntityType<?>> type, FabricBlockSettings settings)
	{
		super(recipeType, type, settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		PoweredFurnaceBlockEntity be = new PoweredFurnaceBlockEntity(recipeType, type.get());
		return be;
	}
}

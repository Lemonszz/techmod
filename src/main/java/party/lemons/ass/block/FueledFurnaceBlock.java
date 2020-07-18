package party.lemons.ass.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Lazy;
import net.minecraft.world.BlockView;
import party.lemons.ass.blockentity.FueledFurnaceBlockEntity;
import party.lemons.ass.blockentity.util.MachineTier;

import javax.annotation.Nullable;

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

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		FueledFurnaceBlockEntity be = new FueledFurnaceBlockEntity(recipeType, type.get());
		be.upgrade(tier);
		return be;
	}
}

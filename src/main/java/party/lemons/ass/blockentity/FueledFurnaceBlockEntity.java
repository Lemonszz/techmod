package party.lemons.ass.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.FueledFurnaceWorker;
import party.lemons.ass.blockentity.worker.Worker;

public class FueledFurnaceBlockEntity extends AbstractMachineBlockEntity implements InventorySource
{
	private Inventory inventory;
	private RecipeType<? extends AbstractCookingRecipe> recipeType;

	public FueledFurnaceBlockEntity(RecipeType<? extends AbstractCookingRecipe> recipeType, BlockEntityType<?> type)
	{
		super(type);
		this.recipeType = recipeType;
		this.inventory = new SimpleInventory(3);
	}

	@Override
	public Worker createWorker()
	{
		return new FueledFurnaceWorker(this, recipeType);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.TOP).slot(0).build()
				.addSide(BlockSide.HORIZONTALS).slot(1).build();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.BOTTOM).slot(2).build();
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}
}

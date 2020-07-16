package party.lemons.ass.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import party.lemons.ass.blockentity.screen.FueledFurnaceGuiDescription;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.FueledFurnaceWorker;
import party.lemons.ass.blockentity.worker.Worker;

import javax.annotation.Nullable;

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
	public void init()
	{
		super.init();

		propertyDelegate.addProperty(0, ()->(int) ((FueledFurnaceWorker)worker).burnTime, (v)->((FueledFurnaceWorker)worker).burnTime = v);
		propertyDelegate.addProperty(1, ()->(int) ((FueledFurnaceWorker)worker).fuelTime, (v)->((FueledFurnaceWorker)worker).fuelTime = v);
		propertyDelegate.addProperty(2, ()->(int) ((FueledFurnaceWorker)worker).cookTime, (v)->((FueledFurnaceWorker)worker).cookTime = v);
		propertyDelegate.addProperty(3, ()->(int) ((FueledFurnaceWorker)worker).cookTimeTotal, (v)->((FueledFurnaceWorker)worker).cookTimeTotal = v);
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

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText("gui." + getCachedState().getBlock().getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new FueledFurnaceGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}
}

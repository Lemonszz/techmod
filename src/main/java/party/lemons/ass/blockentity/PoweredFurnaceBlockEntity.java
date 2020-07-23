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
import party.lemons.ass.blockentity.screen.PoweredFurnaceGuiDescription;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.FueledFurnaceWorker;
import party.lemons.ass.blockentity.worker.FurnaceWorker;
import party.lemons.ass.blockentity.worker.PoweredFurnaceWorker;
import party.lemons.ass.blockentity.worker.Worker;
import team.reborn.energy.EnergySide;

import javax.annotation.Nullable;

public class PoweredFurnaceBlockEntity extends AbstractPoweredMachineBlockEntity implements InventorySource
{
	private Inventory inventory;
	private RecipeType<? extends AbstractCookingRecipe> recipeType;

	public PoweredFurnaceBlockEntity(RecipeType<? extends AbstractCookingRecipe> recipeType, BlockEntityType<?> type)
	{
		super(type);

		this.recipeType = recipeType;
		this.inventory = new SimpleInventory(2);
	}

	@Override
	public void init()
	{
		super.init();

		propertyDelegate.addProperty(0, ()->(int)getStored(EnergySide.UNKNOWN), (v)->setStored(v));
		propertyDelegate.addProperty(1, ()->(int)getMaxStoredPower(), (v)->setStored(v));
		propertyDelegate.addProperty(2, ()->(int) ((FurnaceWorker)worker).cookTime, (v)->((FurnaceWorker)worker).cookTime = v);
		propertyDelegate.addProperty(3, ()->(int) ((FurnaceWorker)worker).cookTimeTotal, (v)->((FurnaceWorker)worker).cookTimeTotal = v);
	}

	@Override
	public Worker createWorker()
	{
		return new PoweredFurnaceWorker(this, recipeType);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.BACK, BlockSide.FRONT, BlockSide.LEFT, BlockSide.RIGHT, BlockSide.TOP).slot(0).build();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO()
				.addSide(BlockSide.BACK, BlockSide.FRONT, BlockSide.LEFT, BlockSide.RIGHT, BlockSide.TOP).slot(0).build()
				.addSide(BlockSide.BOTTOM).slot(2).build();
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new PoweredFurnaceGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
	}
}

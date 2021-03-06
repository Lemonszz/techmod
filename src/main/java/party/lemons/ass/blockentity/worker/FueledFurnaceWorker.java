package party.lemons.ass.blockentity.worker;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import party.lemons.ass.blockentity.FueledFurnaceBlockEntity;

public class FueledFurnaceWorker extends FurnaceWorker<FueledFurnaceBlockEntity>
{
	public float fuelTime = 0;

	public FueledFurnaceWorker(FueledFurnaceBlockEntity machine, RecipeType<? extends AbstractCookingRecipe> recipeType)
	{
		super(machine, recipeType);
	}

	@Override
	public CompoundTag toTag()
	{
		CompoundTag tag = super.toTag();
		tag.putFloat("FuelTime", fuelTime);

		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		super.fromTag(tag);
		fuelTime = tag.getFloat("FuelTime");
	}

	@Override
	protected void takeFuel()
	{
		ItemStack st = inventory.getStack(getFuelSlot());
		this.burnTime = this.getFuelTime(st);
		this.fuelTime = this.burnTime;
		if (this.isBurning()) {
			if (!st.isEmpty()) {
				Item item = st.getItem();
				st.decrement(1);
				if (st.isEmpty()) {
					Item item2 = item.getRecipeRemainder();
					this.inventory.setStack(getFuelSlot(), item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
				}
			}
		}
	}

	@Override
	protected int getInputSlot()
	{
		return 0;
	}

	@Override
	protected int getOutputSlot()
	{
		return 2;
	}

	public int getFuelSlot()
	{
		return 1;
	}

	@Override
	protected boolean hasFuel()
	{
		return !inventory.getStack(getFuelSlot()).isEmpty();
	}

	@Override
	protected int getRequiredInventorySize()
	{
		return 3;
	}

	protected float getFuelTime(ItemStack fuel)
	{
		if (fuel.isEmpty()) {
			return 0;
		} else {
			Item item = fuel.getItem();
			return ((float)AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0) * machine.getPowerUseMultiplier());
		}
	}

}

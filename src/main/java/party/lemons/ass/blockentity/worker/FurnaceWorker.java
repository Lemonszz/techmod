package party.lemons.ass.blockentity.worker;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.AbstractMachineBlockEntity;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class FurnaceWorker<T extends AbstractMachineBlockEntity> extends Worker<T>
{
	private final RecipeType<? extends AbstractCookingRecipe> recipeType;
	protected final Inventory inventory;
	protected final T machine;
	private final World world;

	public float burnTime;
	public float cookTime;
	public float cookTimeTotal;
	private final Object2IntOpenHashMap<Identifier> recipesUsed;

	public FurnaceWorker(T machine, RecipeType<? extends AbstractCookingRecipe> recipeType)
	{
		super(machine);
		this.machine = machine;
		Optional<Inventory> inventoryOptional = machine.getInventoryOptional();
		if(!inventoryOptional.isPresent() || inventoryOptional.get().size() < getRequiredInventorySize())
			throw new IllegalArgumentException("Inventory doesn't have enough slots for this worker");

		inventory = inventoryOptional.get();
		this.recipeType = recipeType;
		this.world = machine.getWorld();
		this.recipesUsed = new Object2IntOpenHashMap<>();
	}


	@Override
	public void update()
	{
		boolean originallyBurning = isBurning();
		boolean hasChanged = false;

		if(originallyBurning)
			burnTime--;

		if(!world.isClient)
		{
			if(!isBurning() && (!hasFuel() || !hasInput()))
			{
				if(shouldDecreaseCookTimeOnIdle() && !isBurning() && this.cookTime > 0)
				{
					//Decrease cook time
					this.cookTime = MathHelper.clamp(this.cookTime - 2, 0 , this.cookTimeTotal);
				}
			}
			else
			{
				Optional<? extends AbstractCookingRecipe> recipe = world.getRecipeManager().getFirstMatch(recipeType, machine, world);
				if(recipe.isPresent() && !isBurning() && canAcceptRecipeOutput(recipe.get()))
				{
					takeFuel();
					cookTimeTotal = getCookTime();
				}

				if(recipe.isPresent() && isBurning() && canAcceptRecipeOutput(recipe.get()))
				{
					cookTime += machine.getWorkSpeedMultiplier();
					if(cookTime >= cookTimeTotal)
					{
						cookTime = 0;
						cookTimeTotal = getCookTime();
						craftRecipe(recipe.get());
						hasChanged = true;
					}
				}
				else{
					cookTime = 0;
				}
			}

			if(originallyBurning != isBurning())
			{
				hasChanged = true;
				world.setBlockState(machine.getPos(), this.world.getBlockState(machine.getPos()).with(AbstractFurnaceBlock.LIT, isBurning()));
			}

			if(hasChanged)
			{
				machine.markDirty();
			}
		}
	}

	protected float getCookTime()
	{
		return this.world.getRecipeManager().getFirstMatch(this.recipeType, machine, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
	}

	/***
	 * Takes from fuel (either power or itemstack)
	 * Set burnTime & fuelTime equal to fuel time
	 */
	protected abstract void takeFuel();

	@Override
	public CompoundTag toTag()
	{
		CompoundTag tag = new CompoundTag();
		tag.putFloat("BurnTime", burnTime);
		tag.putFloat("CookTime", cookTime);
		tag.putFloat("CookTimeTotal", cookTimeTotal);

		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		burnTime = tag.getFloat("BurnTime");
		cookTime = tag.getFloat("CookTime");
		cookTimeTotal = tag.getFloat("CookTimeTotal");
	}

	protected boolean isBurning()
	{
		return burnTime > 0;
	}

	protected abstract int getInputSlot();
	protected abstract int getOutputSlot();
	protected abstract boolean hasFuel();
	protected abstract int getRequiredInventorySize();

	public boolean hasInput()
	{
		return !inventory.getStack(getInputSlot()).isEmpty();
	}

	protected boolean shouldDecreaseCookTimeOnIdle()
	{
		return true;
	}

	protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe)
	{
		if (!this.inventory.getStack(getInputSlot()).isEmpty() && recipe != null)
		{
			ItemStack outputStack = recipe.getOutput();
			if (outputStack.isEmpty())
				return false;
			else
			{
				ItemStack currentOutputStack = this.inventory.getStack(getOutputSlot());
				if (currentOutputStack.isEmpty())
					return true;
				else if (!currentOutputStack.isItemEqualIgnoreDamage(outputStack))
					return false;
				else if (currentOutputStack.getCount() < machine.getMaxCountPerStack() && currentOutputStack.getCount() < currentOutputStack.getMaxCount())
					return true;
				else
					return currentOutputStack.getCount() < outputStack.getMaxCount();
			}
		} else
			return false;
	}

	protected void craftRecipe(@Nullable Recipe<?> recipe)
	{
		if (recipe != null && this.canAcceptRecipeOutput(recipe)) {
			ItemStack inputStack = this.inventory.getStack(getInputSlot());
			ItemStack recipeOutput = recipe.getOutput();
			ItemStack outputStack = this.inventory.getStack(getOutputSlot());
			if (outputStack.isEmpty())
				this.inventory.setStack(getOutputSlot(), recipeOutput.copy());
			else if (outputStack.getItem() == recipeOutput.getItem())
				outputStack.increment(1);

			if (!this.world.isClient)
				this.setLastRecipe(recipe);

			inputStack.decrement(1);
		}
	}

	public void setLastRecipe(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			Identifier identifier = recipe.getId();
			this.recipesUsed.addTo(identifier, 1);
		}
	}
}

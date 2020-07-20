package party.lemons.ass.blockentity.worker;

import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import party.lemons.ass.blockentity.PoweredFurnaceBlockEntity;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergySide;

public class PoweredFurnaceWorker extends FurnaceWorker<PoweredFurnaceBlockEntity>
{
	public PoweredFurnaceWorker(PoweredFurnaceBlockEntity machine, RecipeType<? extends AbstractCookingRecipe> recipeType)
	{
		super(machine, recipeType);
	}

	public double getEnergyUse()
	{
		return 10 * machine.getPowerUseMultiplier();
	}

	@Override
	protected void takeFuel()
	{
		double powerUse = Energy.of(machine).extract(getEnergyUse());
		this.burnTime = (int)(powerUse / 2D);
	}

	@Override
	protected int getInputSlot()
	{
		return 0;
	}

	@Override
	protected int getOutputSlot()
	{
		return 1;
	}

	@Override
	protected boolean hasFuel()
	{
		return machine.getStored(EnergySide.UNKNOWN) > 0;
	}

	@Override
	protected int getRequiredInventorySize()
	{
		return 2;
	}
}

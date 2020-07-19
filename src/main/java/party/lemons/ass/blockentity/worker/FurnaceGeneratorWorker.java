package party.lemons.ass.blockentity.worker;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.FurnaceGeneratorBlockEntity;

import java.util.Optional;

public class FurnaceGeneratorWorker extends GeneratorWorker<FurnaceGeneratorBlockEntity>
{
	public float fuelTime = 0;
	public float burnTime;
	private Inventory inventory;

	public FurnaceGeneratorWorker(FurnaceGeneratorBlockEntity machine)
	{
		super(machine);
		inventory = machine.getInventory();
	}

	@Override
	public double getGeneratedPower()
	{
		return 10.0D;
	}

	@Override
	public void update()
	{
		boolean originallyBurning = isBurning();
		boolean hasChanged = false;
		World world = machine.getWorld();

		if(originallyBurning)
			burnTime--;

		if(!world.isClient)
		{
			if(!isBurning() && hasFuel())
			{
				takeFuel();
			}

			if(originallyBurning != isBurning())
			{
				hasChanged = true;
				world.setBlockState(machine.getPos(), world.getBlockState(machine.getPos()).with(AbstractFurnaceBlock.LIT, isBurning()));
			}

			if(hasChanged)
			{
				machine.markDirty();
			}
		}
		super.update();
	}

	@Override
	public boolean canGeneratePower()
	{
		return isBurning();
	}

	protected boolean isBurning()
	{
		return burnTime > 0;
	}

	protected boolean hasFuel()
	{
		return !inventory.getStack(0).isEmpty();
	}

	protected boolean takeFuel()
	{
		ItemStack st = inventory.getStack(0);
		this.burnTime = this.getFuelTime(st);
		this.fuelTime = this.burnTime;
		if (this.isBurning()) {
			if (!st.isEmpty()) {
				Item item = st.getItem();
				st.decrement(1);
				if (st.isEmpty()) {
					Item item2 = item.getRecipeRemainder();
					inventory.setStack(0, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
				}
			}

			return true;
		}

		return false;
	}

	protected float getFuelTime(ItemStack fuel)
	{
		if (fuel.isEmpty()) {
			return 0;
		} else {
			Item item = fuel.getItem();
			return (((float) AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0) * machine.getWorkSpeedMultiplier())) / 4F;
		}
	}

	@Override
	public CompoundTag toTag()
	{
		CompoundTag tag = new CompoundTag();
		tag.putFloat("FuelTime", fuelTime);
		tag.putFloat("BurnTime", burnTime);

		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		fuelTime = tag.getFloat("FuelTime");
		burnTime = tag.getFloat("BurnTime");

	}
}

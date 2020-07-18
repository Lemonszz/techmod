package party.lemons.ass.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import party.lemons.ass.block.ExperienceTorchBlock;
import party.lemons.ass.init.AssBlockEntities;
import party.lemons.ass.util.InventoryUtil;
import party.lemons.ass.util.access.ExperienceOrbEntityAccess;

public class ExperienceTorchBlockEntity extends BlockEntity implements Tickable
{
	public int experiencePickupDelay;
	public int experienceLevel;
	public int totalExperience;
	public float experienceProgress;

	public ExperienceTorchBlockEntity()
	{
		super(AssBlockEntities.EXPERIENCE_TORCH);
	}

	@Override
	public void tick()
	{
		if(experiencePickupDelay > 0)
			experiencePickupDelay--;
	}

	public void addExperience(int experience) {
		this.experienceProgress += (float)experience / (float)this.getNextLevelExperience();
		this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);

		while(this.experienceProgress < 0.0F) {
			float f = this.experienceProgress * (float)this.getNextLevelExperience();
			if (this.experienceLevel > 0) {
				this.addExperienceLevels(-1);
				this.experienceProgress = 1.0F + f / (float)this.getNextLevelExperience();
			} else {
				this.addExperienceLevels(-1);
				this.experienceProgress = 0.0F;
			}
		}

		while(this.experienceProgress >= 1.0F) {
			this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getNextLevelExperience();
			this.addExperienceLevels(1);
			this.experienceProgress /= (float)this.getNextLevelExperience();
		}

	}

	public void addExperienceLevels(int levels) {
		this.experienceLevel += levels;
		if (this.experienceLevel < 0) {
			this.experienceLevel = 0;
			this.experienceProgress = 0.0F;
			this.totalExperience = 0;
		}
	}

	public Inventory getAttachedInventory()
	{
		BlockPos offset = getPos().offset(getCachedState().get(ExperienceTorchBlock.FACING));
		return InventoryUtil.getInventoryAt(world, offset);
	}

	public void pickup(ExperienceOrbEntity orb)
	{
		if (!this.world.isClient) {
			if (orb.pickupDelay == 0 && experiencePickupDelay == 0)
			{
				experiencePickupDelay = 2;
				Inventory inventory = getAttachedInventory();
				if(inventory != null)
				{
					for(int i = 0; i < inventory.size(); i++)
					{
						ItemStack stack = inventory.getStack(i);
						if(!stack.isEmpty() && EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0 && stack.isDamaged())
						{
							int repairAmt = orb.getExperienceAmount();  //On player mending is this value * 2

							((ExperienceOrbEntityAccess)orb).reduceAmount(repairAmt);
							stack.setDamage(stack.getDamage() - 1);
							break;
						}
					}
				}
				if (orb.getExperienceAmount() > 0) {
					addExperience(orb.getExperienceAmount());
				}

				orb.remove();
			}

		}
	}

	public int getNextLevelExperience() {
		if (this.experienceLevel >= 30) {
			return 112 + (this.experienceLevel - 30) * 9;
		} else {
			return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);

		tag.putInt("PickupDelay", experiencePickupDelay);
		tag.putInt("XPLevel", experienceLevel);
		tag.putInt("XPTotal", totalExperience);
		tag.putFloat("XPProgress", experienceProgress);

		return tag;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);

		experiencePickupDelay = tag.getInt("PickupDelay");
		experienceLevel = tag.getInt("XPLevel");
		totalExperience = tag.getInt("XPTotal");
		experienceProgress = tag.getFloat("XPProgress");
	}
}

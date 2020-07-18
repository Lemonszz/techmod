package party.lemons.ass.mixin;

import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.ass.init.AssBlocks;

import javax.annotation.Nullable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends LootableContainerBlockEntity
{
	public HopperBlockEntityMixin(BlockEntityType<?> type)
	{
		super(type);
	}

	@Shadow @Nullable protected abstract Inventory getOutputInventory();

	@Shadow protected abstract boolean isInventoryFull(Inventory inv, Direction direction);

	@Shadow private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side){
		return ItemStack.EMPTY;
	}

	@Inject(at = @At("HEAD"), method = "insert", cancellable = true)
	public void insert(CallbackInfoReturnable<Boolean> cbi)
	{
		if(this.getCachedState().getBlock() == AssBlocks.GATED_HOPPER)
		{
			Inventory inventory = this.getOutputInventory();
			if(inventory == null)
			{
				cbi.setReturnValue(false);
			}
			else
			{
				Direction direction = this.getCachedState().get(HopperBlock.FACING).getOpposite();
				if(this.isInventoryFull(inventory, direction))
				{
					cbi.setReturnValue(false);
				}
				else
				{
					for(int i = 0; i < this.size(); ++i)
					{
						if(!this.getStack(i).isEmpty() && this.getStack(i).getCount() > 1)
						{
							ItemStack itemStack = this.getStack(i).copy();
							ItemStack itemStack2 = transfer(this, inventory, this.removeStack(i, 1), direction);
							if(itemStack2.isEmpty())
							{
								inventory.markDirty();
								cbi.setReturnValue(true);
								return;
							}

							this.setStack(i, itemStack);
						}
					}

					cbi.setReturnValue(false);
				}
			}
		}
	}
}

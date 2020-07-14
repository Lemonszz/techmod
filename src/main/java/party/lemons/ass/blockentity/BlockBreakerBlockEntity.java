package party.lemons.ass.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import party.lemons.ass.blockentity.util.BlockSide;
import party.lemons.ass.blockentity.util.InventorySource;
import party.lemons.ass.blockentity.util.SlotIO;
import party.lemons.ass.blockentity.worker.BlockBreakerWorker;
import party.lemons.ass.blockentity.worker.Worker;
import party.lemons.ass.init.AssBlockEntities;

public class BlockBreakerBlockEntity extends AbstractMachineBlockEntity implements InventorySource
{
	private Inventory inventory;

	public BlockBreakerBlockEntity()
	{
		super(AssBlockEntities.BLOCK_BREAKER);
		this.inventory = new SimpleInventory(1);

	}

	@Override
	public Worker createWorker()
	{
		return new BlockBreakerWorker(this);
	}

	@Override
	public SlotIO createInsertSlotIO()
	{
		return new SlotIO().addSide(BlockSide.values()).slot(0).build();
	}

	@Override
	public SlotIO createExtractSlotIO()
	{
		return new SlotIO().addSide(BlockSide.values()).slot(0).build();
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}
}

package party.lemons.ass.init;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeType;
import party.lemons.ass.blockentity.BlockBreakerBlockEntity;
import party.lemons.ass.blockentity.FueledFurnaceBlockEntity;
import party.lemons.ass.blockentity.SpoutBlockEntity;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(registry = "block_entity_type", type = BlockEntityType.class, priority = 101)
public final class AssBlockEntities
{
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_SMELTING_FURNACE = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.SMELTING, AssBlockEntities.FUELED_SMELTING_FURNACE), AssBlocks.GILDED_FURNACE));
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_BLAST_FURNACE = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.BLASTING, AssBlockEntities.FUELED_BLAST_FURNACE), AssBlocks.GILDED_BLAST_FURNACE));
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_SMOKER = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.SMOKING, AssBlockEntities.FUELED_SMOKER), AssBlocks.GILDED_SMOKER));
	public static final BlockEntityType<SpoutBlockEntity> SPOUT = create(BlockEntityType.Builder.create(()->new SpoutBlockEntity(AssBlockEntities.SPOUT), AssBlocks.SPOUT));
	public static final BlockEntityType<BlockBreakerBlockEntity> BLOCK_BREAKER = create(BlockEntityType.Builder.create(BlockBreakerBlockEntity::new, AssBlocks.BLOCK_BREAKER));

	private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.Builder<T> builder) {
		return builder.build(null);
	}

	private AssBlockEntities(){}
}

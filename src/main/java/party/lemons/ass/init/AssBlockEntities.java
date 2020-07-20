package party.lemons.ass.init;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeType;
import party.lemons.ass.blockentity.*;
import party.lemons.ass.util.access.BlockEntityTypeAccess;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(registry = "block_entity_type", type = BlockEntityType.class, priority = 101)
public final class AssBlockEntities
{
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_SMELTING_FURNACE = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.SMELTING, AssBlockEntities.FUELED_SMELTING_FURNACE), AssBlocks.SCORCHED_FURNACE));
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_BLAST_FURNACE = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.BLASTING, AssBlockEntities.FUELED_BLAST_FURNACE), AssBlocks.SCORCHED_BLAST_FURNACE));
	public static final BlockEntityType<FueledFurnaceBlockEntity> FUELED_SMOKER = create(BlockEntityType.Builder.create(()->new FueledFurnaceBlockEntity(RecipeType.SMOKING, AssBlockEntities.FUELED_SMOKER), AssBlocks.SCORCHED_SMOKER));
	public static final BlockEntityType<SpoutBlockEntity> SPOUT = create(BlockEntityType.Builder.create(()->new SpoutBlockEntity(AssBlockEntities.SPOUT), AssBlocks.SPOUT));
	public static final BlockEntityType<BlockBreakerBlockEntity> BLOCK_BREAKER = create(BlockEntityType.Builder.create(BlockBreakerBlockEntity::new, AssBlocks.BLOCK_BREAKER));
	public static final BlockEntityType<BlockPlacerBlockEntity> BLOCK_PLACER = create(BlockEntityType.Builder.create(BlockPlacerBlockEntity::new, AssBlocks.BLOCK_PLACER));
	public static final BlockEntityType<ExperienceTorchBlockEntity> EXPERIENCE_TORCH = create(BlockEntityType.Builder.create(ExperienceTorchBlockEntity::new, AssBlocks.EXPERIENCE_TORCH));
	public static final BlockEntityType<CreativeGeneratorBlockEntity> CREATIVE_GENERATOR = create(BlockEntityType.Builder.create(CreativeGeneratorBlockEntity::new, AssBlocks.CREATIVE_GENERATOR));
	public static final BlockEntityType<FurnaceGeneratorBlockEntity> FURNACE_GENERATOR = create(BlockEntityType.Builder.create(FurnaceGeneratorBlockEntity::new, AssBlocks.FURNACE_GENERATOR));
	public static final BlockEntityType<CableBlockEntity> CABLE = create(BlockEntityType.Builder.create(CableBlockEntity::new, AssBlocks.CABLE));

	static {
		((BlockEntityTypeAccess)BlockEntityType.HOPPER).addSupportedBlock(AssBlocks.GATED_HOPPER);
	}

	private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.Builder<T> builder) {
		return builder.build(null);
	}

	private AssBlockEntities(){}
}

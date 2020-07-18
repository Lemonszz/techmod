package party.lemons.ass.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Lazy;
import party.lemons.ass.block.*;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(registry = "block", type = Block.class)
public class AssBlocks
{
	public static final FueledFurnaceBlock GILDED_FURNACE = new FueledFurnaceBlock(RecipeType.SMELTING, new Lazy<>(()->AssBlockEntities.FUELED_SMELTING_FURNACE), FabricBlockSettings.of(Material.METAL).strength(1.5F, 1.5F));
	public static final FueledFurnaceBlock GILDED_SMOKER = new FueledFurnaceBlock(RecipeType.SMOKING, new Lazy<>(()->AssBlockEntities.FUELED_SMOKER), FabricBlockSettings.of(Material.METAL).strength(1.5F, 1.5F));
	public static final FueledFurnaceBlock GILDED_BLAST_FURNACE = new FueledFurnaceBlock(RecipeType.BLASTING, new Lazy<>(()->AssBlockEntities.FUELED_BLAST_FURNACE), FabricBlockSettings.of(Material.METAL).strength(1.5F, 1.5F));
	public static final SpoutBlock SPOUT = new SpoutBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(1.5F, 1.5F));
	public static final BlockBreakerBlock BLOCK_BREAKER = new BlockBreakerBlock(FabricBlockSettings.of(Material.METAL).strength(1.5F, 1.5F));
	public static final BlockPlacerBlock BLOCK_PLACER = new BlockPlacerBlock(FabricBlockSettings.of(Material.METAL).strength(1.5F, 1.5F));
	public static final GatedHopperBlock GATED_HOPPER = new GatedHopperBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(1.5F, 1.5F));
}

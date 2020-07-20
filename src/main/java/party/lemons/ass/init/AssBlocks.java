package party.lemons.ass.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Lazy;
import party.lemons.ass.block.*;
import party.lemons.ass.blockentity.util.MachineTier;
import party.lemons.ass.util.Util;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(registry = "block", type = Block.class)
public class AssBlocks
{
	public static final FueledFurnaceBlock SCORCHED_FURNACE = new FueledFurnaceBlock(RecipeType.SMELTING, Util.lazy(()->AssBlockEntities.FUELED_SMELTING_FURNACE), MachineTier.IRON, settings(Material.METAL));
	public static final FueledFurnaceBlock SCORCHED_SMOKER = new FueledFurnaceBlock(RecipeType.SMOKING, Util.lazy(()->AssBlockEntities.FUELED_SMOKER), MachineTier.IRON, settings(Material.METAL));
	public static final FueledFurnaceBlock SCORCHED_BLAST_FURNACE = new FueledFurnaceBlock(RecipeType.BLASTING, Util.lazy(()->AssBlockEntities.FUELED_BLAST_FURNACE), MachineTier.IRON, settings(Material.METAL));
	public static final SpoutBlock SPOUT = new SpoutBlock(settings(Material.METAL).nonOpaque());
	public static final BlockBreakerBlock BLOCK_BREAKER = new BlockBreakerBlock(settings(Material.METAL));
	public static final BlockPlacerBlock BLOCK_PLACER = new BlockPlacerBlock(settings(Material.METAL));
	public static final GatedHopperBlock GATED_HOPPER = new GatedHopperBlock(settings(Material.METAL));
	public static final ExperienceTorchBlock EXPERIENCE_TORCH = new ExperienceTorchBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().breakInstantly());
	public static final CreativeGeneratorBlock CREATIVE_GENERATOR = new CreativeGeneratorBlock(FabricBlockSettings.of(Material.METAL).dropsNothing().strength(-1));
	public static final FurnaceGeneratorBlock FURNACE_GENERATOR = new FurnaceGeneratorBlock(settings(Material.METAL));
	public static final CableBlock CABLE = new CableBlock(settings(Material.METAL));

	public static FabricBlockSettings settings(Material material, float hardness)
	{
		return FabricBlockSettings.of(material).strength(hardness);
	}

	public static FabricBlockSettings settings(Material material)
	{
		return settings(material, 1.5F);
	}
}

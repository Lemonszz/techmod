package party.lemons.ass.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.Lazy;
import party.lemons.ass.block.*;
import party.lemons.ass.blockentity.util.MachineTier;
import party.lemons.ass.util.Util;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(registry = "block", type = Block.class)
public class AssBlocks
{
	public static final FueledFurnaceBlock SCORCHED_FURNACE = new FueledFurnaceBlock(RecipeType.SMELTING, Util.lazy(()->AssBlockEntities.FUELED_SMELTING_FURNACE), settings(Material.STONE));
	public static final FueledFurnaceBlock SCORCHED_SMOKER = new FueledFurnaceBlock(RecipeType.SMOKING, Util.lazy(()->AssBlockEntities.FUELED_SMOKER), settings(Material.STONE));
	public static final FueledFurnaceBlock SCORCHED_BLAST_FURNACE = new FueledFurnaceBlock(RecipeType.BLASTING, Util.lazy(()->AssBlockEntities.FUELED_BLAST_FURNACE), settings(Material.STONE));
	public static final SpoutBlock SPOUT = new SpoutBlock(settings(Material.METAL, 3.5F).nonOpaque());
	public static final BlockBreakerBlock BLOCK_BREAKER = new BlockBreakerBlock(settings(Material.STONE));
	public static final BlockPlacerBlock BLOCK_PLACER = new BlockPlacerBlock(settings(Material.STONE));
	public static final GatedHopperBlock GATED_HOPPER = new GatedHopperBlock(settings(Material.METAL, 5.0F));
	public static final ExperienceTorchBlock EXPERIENCE_TORCH = new ExperienceTorchBlock(settings(Material.METAL, 1.5F).noCollision());
	public static final CreativeGeneratorBlock CREATIVE_GENERATOR = new CreativeGeneratorBlock(FabricBlockSettings.of(Material.METAL).dropsNothing().strength(-1));
	public static final FurnaceGeneratorBlock FURNACE_GENERATOR = new FurnaceGeneratorBlock(settings(Material.STONE));
	public static final CableBlock CABLE = new CableBlock(settings(Material.PISTON, 0.5F));
	public static final PoweredFurnaceBlock POWERED_SCORCHED_FURNACE = new PoweredFurnaceBlock(RecipeType.SMELTING, Util.lazy(()->AssBlockEntities.POWERED_SMELTING_FURNACE), settings(Material.STONE));
	public static final PoweredFurnaceBlock POWERED_SCORCHED_SMOKER = new PoweredFurnaceBlock(RecipeType.SMOKING, Util.lazy(()->AssBlockEntities.POWERED_SMOKER), settings(Material.STONE));
	public static final PoweredFurnaceBlock POWERED_SCORCHED_BLAST_FURNACE = new PoweredFurnaceBlock(RecipeType.BLASTING, Util.lazy(()->AssBlockEntities.POWERED_BLAST_FURNACE), settings(Material.STONE));
	public static final EnderMagnetBlock ENDER_MAGNET = new EnderMagnetBlock(settings(Material.STONE));
	public static final BubbleGeneratorBlock BUBBLE_GENERATOR = new BubbleGeneratorBlock(settings(Material.STONE));

	public static FabricBlockSettings settings(Material material, float hardness)
	{
		return FabricBlockSettings.of(material).strength(hardness);
	}

	public static FabricBlockSettings settings(Material material)
	{
		return settings(material, 3.5F);
	}
}

package party.lemons.ass.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.ass.util.access.BlockEntityTypeAccess;

import java.util.HashSet;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityTypeAccess
{
	@Shadow @Final @Mutable private Set<Block> blocks;

	@Override
	public void addSupportedBlock(Block block)
	{
		blocks = new HashSet<>(blocks);
		blocks.add(block);
	}
}

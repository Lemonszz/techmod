package party.lemons.ass.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.ass.util.access.FallingBlockEntityAccess;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin implements FallingBlockEntityAccess
{
	@Shadow private BlockState block;

	@Override
	public void setState(BlockState state)
	{
		this.block = state;
	}
}

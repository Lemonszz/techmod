package party.lemons.ass.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.ass.init.AssBlocks;
import party.lemons.ass.util.access.ExperienceOrbEntityAccess;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity implements ExperienceOrbEntityAccess
{
	@Shadow private int lastTargetUpdateTick;
	@Shadow public int renderTicks;
	@Shadow private PlayerEntity target;
	@Shadow public int orbAge;
	@Shadow private int amount;
	private Vec3d targetPos = null;

	private boolean isValidBlock(BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() == AssBlocks.EXPERIENCE_TORCH && squaredDistanceTo(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) < 64.0D;
	}

	private boolean hasTarget()
	{
		return !((this.target == null || this.target.squaredDistanceTo(this) > 64.0D) && (this.targetPos == null || squaredDistanceTo(targetPos) > 64.0D));
	}

	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ExperienceOrbEntity;lastTargetUpdateTick:I"), method = "tick", cancellable = true)
	public void onTick(CallbackInfo cbi)
	{
		if (this.lastTargetUpdateTick < this.renderTicks - 20 + this.getEntityId() % 100)
		{
			PlayerEntity closestPlayer = this.world.getClosestPlayer(this, 8.0D);
			if (!hasTarget() || (closestPlayer != null && closestPlayer.squaredDistanceTo(this) < 5F))
			{
				this.target = closestPlayer;
				if(target == null)
				{
					Vec3d best = null;
					double bestDistance = Double.MAX_VALUE;

					BlockPos.Mutable pos = new BlockPos.Mutable(0,0,0);
					for(int x = (int)getX() -4; x < (int)getX() + 4; x++)
					{
						for(int z = (int)getZ() -4; z < (int)getZ() + 4; z++)
						{
							for(int y = (int)getY()-4; y < (int)getY() + 4; y++)
							{
								pos.set(x, y, z);
								if(isValidBlock(pos))
								{
									Vec3d test = new Vec3d(x + 0.5F, y + 0.5F, z + 0.5F);
									double dis = squaredDistanceTo(test);
									if(dis < bestDistance)
									{
										bestDistance = dis;
										best = test;
									}
								}
							}
						}
					}
					targetPos = best;
				}
			}
			this.lastTargetUpdateTick = this.renderTicks;
		}

		if (this.target != null || targetPos != null)
		{
			Vec3d movePos = target != null
					? new Vec3d(this.target.getX() - this.getX(), this.target.getY() + (double)this.target.getStandingEyeHeight() / 2.0D - this.getY(), this.target.getZ() - this.getZ())
					: new Vec3d(targetPos.getX() - this.getX(), targetPos.getY() - this.getY(), targetPos.getZ() - this.getZ());
			double e = movePos.lengthSquared();
			if (e < 64.0D) {
				double f = 1.0D - Math.sqrt(e) / 8.0D;
				this.setVelocity(this.getVelocity().add(movePos.normalize().multiply(f * f * 0.1D)));
			}
		}

		this.move(MovementType.SELF, this.getVelocity());
		float g = 0.98F;
		if (this.onGround) {
			g = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.98F;
		}

		this.setVelocity(this.getVelocity().multiply((double)g, 0.98D, (double)g));
		if (this.onGround) {
			this.setVelocity(this.getVelocity().multiply(1.0D, -0.9D, 1.0D));
		}

		++this.renderTicks;
		++this.orbAge;
		if (this.orbAge >= 6000) {
			this.remove();
		}

		cbi.cancel();
	}

	@Override
	public void reduceAmount(int amt)
	{
		amount -= amt;
	}

	public ExperienceOrbEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}
}

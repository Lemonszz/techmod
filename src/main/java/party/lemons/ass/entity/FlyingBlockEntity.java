package party.lemons.ass.entity;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import party.lemons.ass.init.AssEntities;
import party.lemons.ass.init.AssNetwork;
import party.lemons.ass.util.EntityUtil;

import java.util.Optional;

public class FlyingBlockEntity extends FallingBlockEntity
{
	private BlockPos targetPos;
	private double sX, sY, sZ;
	private float progress;
	private static final TrackedData<Optional<BlockState>> STATE = DataTracker.registerData(FlyingBlockEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);

	public FlyingBlockEntity(World world)
	{
		super(AssEntities.FLYING_BLOCK, world);
		dataTracker.set(STATE, Optional.of(Blocks.AIR.getDefaultState()));
	}

	public FlyingBlockEntity(World world, BlockPos targetPos, BlockState state)
	{
		this(world);
		this.targetPos = targetPos;
		dataTracker.set(STATE, Optional.of(state));
		setNoGravity(true);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(STATE, Optional.empty());
	}

	@Override
	public BlockState getBlockState()
	{
		return dataTracker.get(STATE).orElse(null);
	}

	public void tick()
	{
		if (this.world.isClient) {
			this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D, this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
		}

		BlockState state = getBlockState();
		if(state == null && !world.isClient())
		{
			remove();
			return;
		}

		if (state.isAir())
		{
			this.remove();
		}
		else
		{
			if(this.timeFalling++ == 0)
			{
				sX = getX();
				sY = getY();
				sZ = getZ();
			}

			if (!this.hasNoGravity()) {
				this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
			}

			if (!this.world.isClient)
			{
				if(!world.getBlockState(targetPos).isAir())
				{
					FallingBlockEntity falling = new FallingBlockEntity(world, getX(), getY(), getZ(), state);
					falling.timeFalling++;
					world.spawnEntity(falling);
					remove();
				}

				if(!getBlockPos().equals(targetPos))
				{
					Vec3d t = new Vec3d(1,1,1);
					setPos(
							lerp(sX, targetPos.getX() + 0.5F, progress),
							lerp(sY, targetPos.getY(), progress),
							lerp(sZ, targetPos.getZ() + 0.5F, progress)
					);
				}
				else
				{
					remove();
					world.setBlockState(targetPos, state);
					world.getBlockTickScheduler().schedule(targetPos, state.getBlock(), 0);
				}
				progress += 0.1F;
			}
		}
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(AssNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}

	private double lerp(double st, double t, double p)
	{
		return st + p * (t - st);
	}
}

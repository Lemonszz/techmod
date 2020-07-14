package party.lemons.ass.util.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

/***
  Shamelessly stolen from carpet

 */
public class FakePlayer extends ServerPlayerEntity
{
	public Runnable fixStartingPosition = () -> {};
	public boolean isAShadow;

	public static FakePlayer createInvisibleFake(String name, MinecraftServer server, ServerWorld world)
	{
		ServerPlayerInteractionManager interactionManagerIn = new ServerPlayerInteractionManager(world);
		GameProfile gameprofile = server.getUserCache().findByName(name);
		if (gameprofile == null)
		{
			return null;
		}
		FakePlayer instance = new FakePlayer(server, world, gameprofile, interactionManagerIn, false);
		return instance;
	}

	public static FakePlayer createFake(String username, MinecraftServer server, double d0, double d1, double d2, double yaw, double pitch, RegistryKey<World> dimensionId, GameMode gamemode)
	{
		//prolly half of that crap is not necessary, but it works
		ServerWorld worldIn = server.getWorld(dimensionId);
		ServerPlayerInteractionManager interactionManagerIn = new ServerPlayerInteractionManager(worldIn);
		GameProfile gameprofile = server.getUserCache().findByName(username);
		if (gameprofile == null)
		{
			return null;
		}
		if (gameprofile.getProperties().containsKey("textures"))
		{
			gameprofile = SkullBlockEntity.loadProperties(gameprofile);
		}
		FakePlayer instance = new FakePlayer(server, worldIn, gameprofile, interactionManagerIn, false);
		instance.fixStartingPosition = () -> instance.refreshPositionAndAngles(d0, d1, d2, (float) yaw, (float) pitch);
		server.getPlayerManager().onPlayerConnect(new FakeNetworkManager(NetworkSide.SERVERBOUND), instance);
		instance.teleport(worldIn, d0, d1, d2, (float)yaw, (float)pitch);
		instance.setHealth(20.0F);
		instance.removed = false;
		instance.stepHeight = 0.6F;
		interactionManagerIn.method_30118(gamemode); // setGameMode
		server.getPlayerManager().sendToDimension(new EntitySetHeadYawS2CPacket(instance, (byte) (instance.headYaw * 256 / 360)), dimensionId);//instance.dimension);
		server.getPlayerManager().sendToDimension(new EntityPositionS2CPacket(instance), dimensionId);//instance.dimension);
		instance.getServerWorld().getChunkManager().updateCameraPosition(instance);
		instance.dataTracker.set(PLAYER_MODEL_PARTS, (byte) 0x7f); // show all model layers (incl. capes)
		return instance;
	}

	public static FakePlayer createShadow(MinecraftServer server, ServerPlayerEntity player)
	{
		player.getServer().getPlayerManager().remove(player);
		player.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.duplicate_login"));
		ServerWorld worldIn = player.getServerWorld();//.getWorld(player.dimension);
		ServerPlayerInteractionManager interactionManagerIn = new ServerPlayerInteractionManager(worldIn);
		GameProfile gameprofile = player.getGameProfile();
		FakePlayer playerShadow = new FakePlayer(server, worldIn, gameprofile, interactionManagerIn, true);
		server.getPlayerManager().onPlayerConnect(new FakeNetworkManager(NetworkSide.SERVERBOUND), playerShadow);

		playerShadow.setHealth(player.getHealth());
		playerShadow.networkHandler.requestTeleport(player.getX(), player.getY(), player.getZ(), player.yaw, player.pitch);
		interactionManagerIn.method_30118(player.interactionManager.getGameMode()); // setGameMode
		playerShadow.stepHeight = 0.6F;
		playerShadow.dataTracker.set(PLAYER_MODEL_PARTS, player.getDataTracker().get(PLAYER_MODEL_PARTS));


		server.getPlayerManager().sendToDimension(new EntitySetHeadYawS2CPacket(playerShadow, (byte) (player.headYaw * 256 / 360)), playerShadow.world.getRegistryKey());
		server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, playerShadow));
		player.getServerWorld().getChunkManager().updateCameraPosition(playerShadow);
		return playerShadow;
	}

	private FakePlayer(MinecraftServer server, ServerWorld worldIn, GameProfile profile, ServerPlayerInteractionManager interactionManagerIn, boolean shadow)
	{
		super(server, worldIn, profile, interactionManagerIn);
		isAShadow = shadow;
	}


	@Override
	public void kill()
	{
		this.server.send(new ServerTask(this.server.getTicks(), () ->this.networkHandler.onDisconnected(new LiteralText("AHH"))));
	}

	@Override
	public void tick()
	{
		if (this.getServer().getTicks() % 10 == 0)
		{
			this.networkHandler.syncWithPlayerPosition();
			this.getServerWorld().getChunkManager().updateCameraPosition(this);
		}
		super.tick();
		this.playerTick();
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		setHealth(20);
		this.hungerManager = new HungerManager();
		kill();
	}
}
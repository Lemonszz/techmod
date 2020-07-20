package party.lemons.ass.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Lazy;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import party.lemons.ass.blockentity.AbstractMachineBlockEntity;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class AssFurnaceBlock extends HorizontalBlock implements BlockEntityProvider
{
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;
	protected final RecipeType<? extends AbstractCookingRecipe> recipeType;
	protected final Lazy<BlockEntityType<?>> type;

	public AssFurnaceBlock(RecipeType<? extends AbstractCookingRecipe> recipeType, Lazy<BlockEntityType<?>> type, Settings settings)
	{
		super(settings);
		this.recipeType = recipeType;
		this.type = type;
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(!state.get(LIT))
			return;

		double x = pos.getX() + 0.5D;
		double y = pos.getY();
		double z = pos.getZ() + 0.5D;
		Direction direction = state.get(FACING);
		Direction.Axis axis = direction.getAxis();
		SoundEvent sound = null;

		if(recipeType == RecipeType.SMELTING) //Furnace
		{
			sound = SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE;
			double offset = 0.52D;

			for(int i = 0; i < 3; i++)
			{
				double altOffset = random.nextDouble() * 0.6D - 0.3D;
				double xOffset = axis == Direction.Axis.X ? (double) direction.getOffsetX() * offset : altOffset;
				double yOffset = random.nextDouble() * 6.0D / 16.0D;
				double zOffset = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * offset : altOffset;
				world.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
			}
		}
		else if(recipeType == RecipeType.BLASTING)
		{
			sound = SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE;
			double offset = 0.52D;
			for(int i = 0; i < 3; i++)
			{
				double altOffset = random.nextDouble() * 0.6D - 0.3D;
				double xOffset = axis == Direction.Axis.X ? (double) direction.getOffsetX() * offset : altOffset;
				double yOffset = random.nextDouble() * 6.0D / 16.0D;
				double zOffset = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * offset : altOffset;
				world.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
			}
		}
		else if(recipeType == RecipeType.SMOKING)
		{
			sound = SoundEvents.BLOCK_SMOKER_SMOKE;
			for(int i = 0; i < 3; i++)
			{
				world.addParticle(ParticleTypes.SMOKE, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
				if(random.nextDouble() < 0.1D)
					world.addParticle(ParticleTypes.FLAME, x, y + 1.1D, z, 0.0D, 0.0D, 0.0D);
			}
		}

		if(sound != null && random.nextDouble() < 0.1D)
		{
			world.playSound(x, y, z, sound, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}

	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(LIT);
	}

}

package net.jadenxgamer.netherexp.core.block;

import java.awt.Color;
import net.jadenxgamer.elysium_api.api.extensions.IElysiumBlockExtension;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class MagmaCreamBlock extends HalfTransparentBlock implements IElysiumBlockExtension {
   public static final BooleanProperty NORTH = BooleanProperty.create("north");
   public static final BooleanProperty EAST = BooleanProperty.create("east");
   public static final BooleanProperty SOUTH = BooleanProperty.create("south");
   public static final BooleanProperty WEST = BooleanProperty.create("west");
   public static final BooleanProperty UP = BooleanProperty.create("up");
   public static final BooleanProperty DOWN = BooleanProperty.create("down");
   private static final double PARTICLE_SPEED_THRESHOLD = 0.4;

   public MagmaCreamBlock(Properties properties) {
      super(properties);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      Direction face = hitResult.getDirection();
      BooleanProperty property = getPropertyForFace(face);
      Boolean isFaceSlick = (Boolean)state.getValue(property);
      if (stack.canPerformAction(ItemAbilities.AXE_SCRAPE) && isFaceSlick) {
         level.setBlock(pos, (BlockState)state.setValue(property, false), 3);
         level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
         }

         if (level.isClientSide()) {
            MagmaCreamBlock.Client.faceParticle(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), face);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else if (stack.canPerformAction(ItemAbilities.FIRESTARTER_LIGHT) && !isFaceSlick) {
         level.setBlock(pos, (BlockState)state.setValue(property, true), 3);
         level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
         }

         if (level.isClientSide()) {
            MagmaCreamBlock.Client.faceParticle(level, pos, ParticleTypes.SMALL_FLAME, face);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
      return state.getValue(UP) ? 1.098901F : super.getFriction(state, level, pos, entity);
   }

   public boolean canStickToFace(BlockState selfState, BlockState otherState, Direction selfFace, Direction otherFace) {
      return selfState.getBlock() == this && otherState.getBlock() == this ? true : !(Boolean)selfState.getValue(getPropertyForFace(selfFace));
   }

   public boolean isStickyBlock(BlockState state) {
      return true;
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      if (!(Boolean)state.getValue(UP) && !entity.isSuppressingBounce()) {
         entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
      } else {
         super.fallOn(level, state, pos, entity, fallDistance);
      }
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      super.stepOn(level, pos, state, entity);
      if ((Boolean)state.getValue(UP)) {
         double horizontalSpeed = Math.sqrt(
            entity.getDeltaMovement().x * entity.getDeltaMovement().x + entity.getDeltaMovement().z * entity.getDeltaMovement().z
         );
         if (level.isClientSide() && horizontalSpeed >= 0.4) {
            MagmaCreamBlock.Client.particle(level, level.random, entity.getRandomX(0.5), entity.blockPosition().getY(), entity.getRandomZ(0.5));

            for (int i = 0; i < 2; i++) {
               level.addParticle(
                  ParticleTypes.SMALL_FLAME, entity.getRandomX(0.5), entity.blockPosition().getY() + 0.2, entity.getRandomZ(0.5), 0.0, 0.08F, 0.0
               );
            }
         }
      } else {
         double d0 = Math.abs(entity.getDeltaMovement().y);
         if (d0 < 0.1 && !entity.isSteppingCarefully()) {
            double d1 = 0.4 + d0 * 0.2;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(d1, 1.0, d1));
         }
      }
   }

   public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
      return (Boolean)state.getValue(UP);
   }

   public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
      return (Boolean)state1.getValue(UP);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{NORTH, EAST, SOUTH, WEST, UP, DOWN});
   }

   private static BooleanProperty getPropertyForFace(Direction face) {
      return switch (face) {
         case NORTH -> NORTH;
         case EAST -> EAST;
         case SOUTH -> SOUTH;
         case WEST -> WEST;
         case UP -> UP;
         case DOWN -> DOWN;
         default -> throw new MatchException(null, null);
      };
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void faceParticle(Level level, BlockPos pos, BlockParticleOption particle, Direction direction) {
         RandomSource random = level.random;
         BlockPos relativePos = pos.relative(direction);

         for (int i = 0; i < 16; i++) {
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
               Axis axis = direction.getAxis();
               double x = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
               double y = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
               double z = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
               level.addParticle(particle, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0, 0.0, 0.0);
            }
         }
      }

      public static void faceParticle(Level level, BlockPos pos, SimpleParticleType particle, Direction direction) {
         RandomSource random = level.random;
         BlockPos relativePos = pos.relative(direction);

         for (int i = 0; i < 10; i++) {
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
               Axis axis = direction.getAxis();
               double x = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
               double y = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
               double z = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
               level.addParticle(particle, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0, 0.0, 0.0);
            }
         }
      }

      public static void particle(Level level, RandomSource random, double x, double y, double z) {
         Color start = new Color(13655584);
         Color end = new Color(15721548);
         LodestoneWorldParticleType particle = CommonParticles.SMOKE_VARIANTS[random.nextInt(CommonParticles.SMOKE_VARIANTS.length)];

         for (int i = 0; i < 4; i++) {
            WorldParticleBuilder.create(particle)
               .setNaturalLighting()
               .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.43F, 0.6F), 0.95F).build())
               .setTransparencyData(GenericParticleData.create(0.25F, 0.5F, 0.0F).setEasing(Easing.BOUNCE_OUT).build())
               .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
               .setColorData(ColorParticleData.create(start, end).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.5F).build())
               .setSpritePicker(ParticleSpritePicker.WITH_AGE)
               .setLifetime(Mth.randomBetweenInclusive(random, 20, 30))
               .disableNoClip()
               .addMotion(0.0 + random.nextDouble() / 24.0, 0.09, 0.0 + random.nextDouble() / 24.0)
               .setGravity(0.3F)
               .setLifeDelay(i * 3)
               .spawn(level, x, y + 0.2, z);
         }
      }
   }
}

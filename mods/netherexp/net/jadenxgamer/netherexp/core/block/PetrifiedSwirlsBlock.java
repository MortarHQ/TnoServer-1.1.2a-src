package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import java.awt.Color;
import net.jadenxgamer.netherexp.core.block.entity.PetrifiedSwirlsBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class PetrifiedSwirlsBlock extends BaseEntityBlock {
   public static final MapCodec<PetrifiedSwirlsBlock> CODEC = simpleCodec(PetrifiedSwirlsBlock::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty SALTED = BooleanProperty.create("salted");
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   protected final VoxelShape northAabb;
   protected final VoxelShape southAabb;
   protected final VoxelShape eastAabb;
   protected final VoxelShape westAabb;
   protected final VoxelShape upAabb;
   protected final VoxelShape downAabb;

   public PetrifiedSwirlsBlock(Properties properties) {
      super(properties);
      float height = 7.0F;
      float aabbOffset = 3.0F;
      this.upAabb = Block.box(aabbOffset, 0.0, aabbOffset, 16.0F - aabbOffset, height, 16.0F - aabbOffset);
      this.downAabb = Block.box(aabbOffset, 16.0F - height, aabbOffset, 16.0F - aabbOffset, 16.0, 16.0F - aabbOffset);
      this.northAabb = Block.box(aabbOffset, aabbOffset, 16.0F - height, 16.0F - aabbOffset, 16.0F - aabbOffset, 16.0);
      this.southAabb = Block.box(aabbOffset, aabbOffset, 0.0, 16.0F - aabbOffset, 16.0F - aabbOffset, height);
      this.eastAabb = Block.box(0.0, aabbOffset, aabbOffset, height, 16.0F - aabbOffset, 16.0F - aabbOffset);
      this.westAabb = Block.box(16.0F - height, aabbOffset, aabbOffset, 16.0, 16.0F - aabbOffset, 16.0F - aabbOffset);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false)).setValue(FACING, Direction.UP)).setValue(SALTED, true)
      );
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance + 2.0F, 1.0F, entity.damageSources().stalagmite());
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity
         && entity.getType() != JNEEntityType.ECTO_SLAB.get()
         && !level.isClientSide
         && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
         double xChange = Math.abs(entity.getX() - entity.xOld);
         double zChange = Math.abs(entity.getZ() - entity.zOld);
         if (xChange >= 0.003F || zChange >= 0.003F) {
            entity.hurt(level.damageSources().cactus(), 1.0F);
         }
      }
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PetrifiedSwirlsBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return (lvl, pos, st, blockEntity) -> {
         if (!(Boolean)state.getValue(SALTED) && blockEntity instanceof PetrifiedSwirlsBlockEntity swirls) {
            swirls.tick();
         }
      };
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Direction direction = (Direction)state.getValue(FACING);

      return switch (direction) {
         case NORTH -> this.northAabb;
         case SOUTH -> this.southAabb;
         case EAST -> this.eastAabb;
         case WEST -> this.westAabb;
         case DOWN -> this.downAabb;
         default -> this.upAabb;
      };
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockPos blockpos = pos.relative(direction.getOpposite());
      return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, direction);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return direction == ((Direction)state.getValue(FACING)).getOpposite() && !state.canSurvive(level, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      LevelAccessor level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      return (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER))
         .setValue(FACING, context.getClickedFace());
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED, FACING, SALTED});
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!level.isClientSide
         && !(Boolean)state.getValue(SALTED)
         && level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity be
         && be.getPetrifier() != null) {
         level.blockEvent(pos, this, 1, 2);
      }

      return InteractionResult.SUCCESS;
   }

   public void attack(BlockState state, Level level, BlockPos pos, Player player) {
      if (!level.isClientSide
         && !(Boolean)state.getValue(SALTED)
         && level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity be
         && be.getPetrifier() != null) {
         level.blockEvent(pos, this, 1, 2);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void unpetrifyParticle(Level level, RandomSource random, BlockPos pos) {
         for (Direction direction : Direction.values()) {
            BlockPos relativePos = pos.relative(direction);
            double motionX = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
            double motionY = random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
            double motionZ = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
               WorldParticleBuilder.create(JNEParticleTypes.ECTO_SHARD.get())
                  .setNaturalLighting()
                  .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0F, 1.0F).setCoefficient(0.0F).setEasing(Easing.SINE_IN).build())
                  .setScaleData(GenericParticleData.create(0.13F).build())
                  .setTransparencyData(GenericParticleData.create(1.0F).build())
                  .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                  .setSpritePicker(ParticleSpritePicker.RANDOM_SPRITE)
                  .setLifetime(random.nextInt(80, 120))
                  .disableNoClip()
                  .addMotion(motionX, motionY, motionZ)
                  .setGravity(0.75F)
                  .spawn(level, relativePos.getX(), relativePos.getY(), relativePos.getZ());
            }
         }
      }

      public static void petrificationParticle(Level level, RandomSource random, BlockPos pos) {
         for (Direction direction : Direction.values()) {
            BlockPos relativePos = pos.relative(direction);
            Color color = new Color(937550);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
               WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
                  .setFullBrightLighting()
                  .setColorData(ColorParticleData.create(color).build())
                  .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 2.0F).setCoefficient(1.0F).setEasing(Easing.SINE_IN).build())
                  .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
                  .setTransparencyData(GenericParticleData.create(0.5F).build())
                  .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                  .setSpritePicker(ParticleSpritePicker.WITH_AGE)
                  .setLifetime(random.nextInt(10, 25))
                  .enableNoClip()
                  .setGravity(0.0F)
                  .setLifeDelay(random.nextInt(0, 10))
                  .setMotion(0.0, -0.04, 0.0)
                  .spawn(level, relativePos.getX(), relativePos.getY() + 1.0, relativePos.getZ());
            }
         }
      }

      public static void attractToPetrifierParticle(Level level, RandomSource random, BlockPos pos) {
         if (level.isClientSide) {
            Color start = new Color(2013882);
            Color end = new Color(937550);
            if (level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity entity) {
               BlockPos targetPos = entity.getPetrifierPos();
               if (targetPos == null) {
                  return;
               }

               double dx = targetPos.getX() + 0.5 - (pos.getX() + 0.5);
               double dy = targetPos.getY() + 0.5 - (pos.getY() + 0.5);
               double dz = targetPos.getZ() + 0.5 - (pos.getZ() + 0.5);
               double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
               if (distance < 0.01) {
                  return;
               }

               double speed = 0.2;
               double vx = dx / distance * speed;
               double vy = dy / distance * speed;
               double vz = dz / distance * speed;
               int lifetime = (int)(distance / speed) + random.nextInt(10, 20);
               lifetime = Math.min(lifetime, 100);

               for (int i = 0; i < random.nextInt(4, 8); i++) {
                  double velOffset = (random.nextDouble() - 0.5) * 0.01;
                  double posOffset = random.nextDouble() / 1.6 * (random.nextBoolean() ? 1 : -1);
                  WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                     .setFullBrightLighting()
                     .setColorData(ColorParticleData.create(start, end).setEasing(Easing.SINE_IN_OUT).setCoefficient(4.0F).build())
                     .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.5F).setEasing(Easing.SINE_IN_OUT).build())
                     .setScaleData(GenericParticleData.create(0.14F, 0.23F, 0.0F).setCoefficient(1.4F).build())
                     .setTransparencyData(GenericParticleData.create(0.7F, 0.3F, 0.0F).setCoefficient(0.5F).build())
                     .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                     .setSpritePicker(ParticleSpritePicker.WITH_AGE)
                     .setLifetime(lifetime)
                     .enableNoClip()
                     .setGravity(0.0F)
                     .setLifeDelay(i * 2)
                     .setMotion(vx + velOffset, vy + velOffset, vz + velOffset)
                     .spawn(level, pos.getX() + 0.5 + posOffset, pos.getY() + 0.5 + posOffset, pos.getZ() + 0.5 + posOffset);
               }
            }
         }
      }
   }
}

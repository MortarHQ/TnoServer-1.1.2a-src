package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractJNECandleBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private final Supplier<SimpleParticleType> fireParticle;
   private final Supplier<SoundEvent> ambientSound;

   public AbstractJNECandleBlock(Supplier<SimpleParticleType> fireParticle, Supplier<SoundEvent> ambientSound, Properties properties) {
      super(properties);
      this.fireParticle = fireParticle;
      this.ambientSound = ambientSound;
   }

   protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
      if (!level.isClientSide()) {
         if (this.canBeLit(state) && projectile.isOnFire()) {
            this.lightCandle(level, state, hit.getBlockPos());
         }
      }
   }

   protected abstract Iterable<Vec3> getParticleOffsets(BlockState var1);

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(LIT)) {
         this.getParticleOffsets(state).forEach(vectorPos -> this.addParticlesAndSound(level, vectorPos.add(pos.getX(), pos.getY(), pos.getZ()), random));
      }
   }

   private void addParticlesAndSound(Level level, Vec3 vectorPos, RandomSource random) {
      float chance = random.nextFloat();
      if (chance < 0.3F) {
         level.addParticle(ParticleTypes.SMOKE, vectorPos.x, vectorPos.y, vectorPos.z, 0.0, 0.0, 0.0);
         if (chance < 0.17F) {
            level.playLocalSound(
               vectorPos.x + 0.5,
               vectorPos.y + 0.5,
               vectorPos.z + 0.5,
               this.ambientSound.get(),
               SoundSource.BLOCKS,
               1.0F + random.nextFloat(),
               random.nextFloat() * 0.7F + 0.3F,
               false
            );
         }
      }

      level.addParticle((ParticleOptions)this.fireParticle.get(), vectorPos.x, vectorPos.y, vectorPos.z, 0.0, 0.0, 0.0);
   }

   protected boolean canBeLit(BlockState state) {
      return !(Boolean)state.getValue(LIT) && !(Boolean)state.getValue(WATERLOGGED);
   }

   protected void lightCandle(LevelAccessor level, BlockState state, BlockPos pos) {
      level.setBlock(pos, (BlockState)state.setValue(LIT, true), 3);
   }

   protected void extinguishCandle(LevelAccessor level, BlockState state, BlockPos pos) {
      level.setBlock(pos, (BlockState)state.setValue(LIT, false), 3);
      level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
      this.getParticleOffsets(state)
         .forEach(
            vectorPos -> level.addParticle(
               ParticleTypes.SMOKE, pos.getX() + vectorPos.x(), pos.getY() + vectorPos.y(), pos.getZ() + vectorPos.z(), 0.0, 0.1F, 0.0
            )
         );
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
      return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluid.is(Fluids.WATER));
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluid) {
      if (!(Boolean)state.getValue(WATERLOGGED) && fluid.is(Fluids.WATER)) {
         if ((Boolean)state.getValue(LIT)) {
            this.extinguishCandle(level, state, pos);
         } else {
            level.setBlock(pos, (BlockState)state.setValue(WATERLOGGED, true), 3);
         }

         level.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(level));
         return true;
      } else {
         return false;
      }
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return Block.canSupportCenter(level, pos.below(), Direction.UP);
   }
}

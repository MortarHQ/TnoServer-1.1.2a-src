package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlackIceBlock extends Block {
   public BlackIceBlock(Properties properties) {
      super(properties);
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
      if (!level.isClientSide && (Boolean)JNEConfigs.BLACK_ICE_TAINTING.get()) {
         if (level.getBlockState(neighborPos).is(Blocks.NETHERRACK)) {
            level.setBlock(neighborPos, JNEBlocks.PALE_SOUL_SLATE.get().defaultBlockState(), 3);
            level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      if (!level.isClientSide && (Boolean)JNEConfigs.BLACK_ICE_TAINTING.get()) {
         Direction[] directions = Direction.values();

         for (Direction direction : directions) {
            BlockPos relativePos = pos.relative(direction);
            if (level.getBlockState(relativePos).is(Blocks.NETHERRACK)) {
               level.setBlock(relativePos, JNEBlocks.PALE_SOUL_SLATE.get().defaultBlockState(), 3);
            }
         }
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Boolean)JNEConfigs.BLACK_ICE_PARTICLES.get() && random.nextInt(10) == 0) {
         BlockPos belowPos = pos.below();
         BlockState belowState = level.getBlockState(belowPos);
         if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
            ParticleUtils.spawnParticleBelow(level, pos, random, (ParticleOptions)JNEParticleTypes.BLACK_FLAKE.get());
         }
      }
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(random.nextDouble() > (Double)JNEConfigs.BLACK_ICE_FROSTS_WATER_CHANCE.get())) {
         Direction freezeDirection = Plane.HORIZONTAL.getRandomDirection(random);
         BlockPos freezePos = pos.relative(freezeDirection);
         if (level.getFluidState(freezePos).is(JNETags.Fluids.TURNS_TO_BLACK_ICE)) {
            level.setBlock(freezePos, JNEBlocks.THIN_BLACK_ICE.get().defaultBlockState(), 3);
            level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }
}

package com.benji.netherman.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;

public class PotentMagmaBlock extends Block {
   public PotentMagmaBlock(Properties properties) {
      super(properties);
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!level.isClientSide()) {
         level.scheduleTick(pos, this, 2);
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
         level.scheduleTick(pos, this, 2);
      }
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      long hash = pos.getX() * 3129871L ^ pos.getZ() * 116129781L ^ pos.getY() * 4963211L;
      long cycleLength = 200L + Math.abs(hash % 1001L);
      long eruptionDuration = 40L + Math.abs((hash >> 2) % 21L);
      long time = level.getGameTime() + Math.abs(hash % cycleLength);
      long currentProgress = time % cycleLength;
      boolean hasLava = false;

      for (Direction dir : Direction.values()) {
         if (level.getBlockState(pos.relative(dir)).is(Blocks.LAVA)) {
            hasLava = true;
            break;
         }
      }

      if (!hasLava) {
         long ticksUntilNextEruption = cycleLength - currentProgress;
         level.scheduleTick(pos, this, (int)Math.max(2L, ticksUntilNextEruption));
      } else {
         if (currentProgress < eruptionDuration) {
            if (currentProgress == 0L || currentProgress == 1L) {
               for (int i = 1; i <= 10; i++) {
                  BlockPos checkPos = pos.above(i);
                  BlockState checkState = level.getBlockState(checkPos);
                  if (checkState.getBlock() instanceof CrimsonWebBlock webBlock) {
                     webBlock.triggerChainReaction(checkState, level, checkPos);
                     break;
                  }
               }
            }

            int height = 5 + (int)Math.abs((hash >> 4) % 6L);
            AABB launchBox = new AABB(pos.getX(), pos.getY() + 1.0, pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0 + height, pos.getZ() + 1.0)
               .inflate(0.25, 0.0, 0.25);

            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, launchBox)) {
               entity.hurt(level.damageSources().inFire(), 3.0F);
               entity.igniteForSeconds(5.0F);
               entity.setDeltaMovement(entity.getDeltaMovement().x, 0.65, entity.getDeltaMovement().z);
               entity.hurtMarked = true;
            }

            if (currentProgress % 8L == 0L) {
               level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.5F, 0.5F + random.nextFloat() * 0.15F);
            }

            if (currentProgress % 12L == 0L) {
               level.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.2F, 0.7F + random.nextFloat() * 0.3F);
            }

            level.scheduleTick(pos, this, 2);
         } else {
            long ticksUntilNextEruption = cycleLength - currentProgress;
            level.scheduleTick(pos, this, (int)Math.max(2L, ticksUntilNextEruption));
         }
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      long hash = pos.getX() * 3129871L ^ pos.getZ() * 116129781L ^ pos.getY() * 4963211L;
      long cycleLength = 200L + Math.abs(hash % 1001L);
      long eruptionDuration = 40L + Math.abs((hash >> 2) % 21L);
      long time = level.getGameTime() + Math.abs(hash % cycleLength);
      long currentProgress = time % cycleLength;
      if (currentProgress < eruptionDuration) {
         int height = 5 + (int)Math.abs((hash >> 4) % 6L);
         double speedY = 0.4 + random.nextDouble() * 0.25;

         for (int i = 0; i < 25; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.45;
            double y = pos.getY() + 1.0 + random.nextDouble() * height;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.45;
            level.addParticle(random.nextBoolean() ? ParticleTypes.CAMPFIRE_COSY_SMOKE : ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, speedY, 0.0);
         }

         if (random.nextInt(2) == 0) {
            level.addParticle(
               ParticleTypes.LAVA,
               pos.getX() + 0.5,
               pos.getY() + 1.1,
               pos.getZ() + 0.5,
               (random.nextDouble() - 0.5) * 0.2,
               0.35,
               (random.nextDouble() - 0.5) * 0.2
            );
         }

         double capY = pos.getY() + 1.0 + height + 0.3;

         for (int i = 0; i < 16; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.7;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.7;
            double speedX = (x - (pos.getX() + 0.5)) * 0.35;
            double speedZ = (z - (pos.getZ() + 0.5)) * 0.35;
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, capY, z, speedX, speedY, speedZ);
         }
      } else if (random.nextInt(15) == 0) {
         level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.02, 0.0);
      }
   }
}

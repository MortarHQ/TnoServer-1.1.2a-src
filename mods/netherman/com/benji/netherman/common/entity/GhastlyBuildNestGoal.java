package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModBlocks;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GhastlyBuildNestGoal extends Goal {
   private final GhastlyEntity ghastly;
   private BlockPos targetStem = null;
   private BlockPos targetAirPos = null;

   public GhastlyBuildNestGoal(GhastlyEntity ghastly) {
      this.ghastly = ghastly;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   private boolean isNestNearby(Level level, BlockPos center) {
      for (BlockPos pos : BlockPos.betweenClosed(center.offset(-30, -10, -30), center.offset(30, 10, 30))) {
         if (level.getBlockState(pos).is((Block)ModBlocks.GHASTLY_NEST.get())) {
            return true;
         }
      }

      return false;
   }

   public boolean canUse() {
      if (!this.ghastly.isTame() && this.ghastly.eatTicks <= 0) {
         if (this.ghastly.tickCount % 100 != 0) {
            return false;
         }

         Level level = this.ghastly.level();
         BlockPos mobPos = this.ghastly.blockPosition();
         if (this.isNestNearby(level, mobPos)) {
            return false;
         }

         for (BlockPos pos : BlockPos.betweenClosed(mobPos.offset(-20, -10, -20), mobPos.offset(20, 10, 20))) {
            if (level.getBlockState(pos).is(Blocks.CRIMSON_STEM)) {
               for (Direction dir : Direction.values()) {
                  BlockPos airCheck = pos.relative(dir);
                  if (level.isEmptyBlock(airCheck)) {
                     this.targetStem = pos.immutable();
                     this.targetAirPos = airCheck.immutable();
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void start() {
      if (this.targetAirPos != null) {
         this.ghastly.getNavigation().moveTo(this.targetAirPos.getX() + 0.5, this.targetAirPos.getY() + 0.5, this.targetAirPos.getZ() + 0.5, 1.2);
      }
   }

   public void tick() {
      if (this.targetStem != null && this.targetAirPos != null) {
         this.ghastly.getNavigation().moveTo(this.targetAirPos.getX() + 0.5, this.targetAirPos.getY() + 0.5, this.targetAirPos.getZ() + 0.5, 1.2);
         double dist = this.ghastly.distanceToSqr(this.targetAirPos.getX() + 0.5, this.targetAirPos.getY() + 0.5, this.targetAirPos.getZ() + 0.5);
         if (dist < 2.5) {
            this.ghastly.getNavigation().stop();
            Level level = this.ghastly.level();
            if (level.getBlockState(this.targetStem).is(Blocks.CRIMSON_STEM) && !this.isNestNearby(level, this.targetStem)) {
               level.setBlockAndUpdate(this.targetStem, ((Block)ModBlocks.GHASTLY_NEST.get()).defaultBlockState());
               level.playSound(null, this.targetStem, SoundEvents.STEM_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F);
               level.playSound(null, this.targetStem, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 0.6F);
               if (level instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(
                     ParticleTypes.CRIMSON_SPORE,
                     this.targetStem.getX() + 0.5,
                     this.targetStem.getY() + 0.5,
                     this.targetStem.getZ() + 0.5,
                     40,
                     0.6,
                     0.6,
                     0.6,
                     0.1
                  );
                  serverLevel.sendParticles(
                     ParticleTypes.LARGE_SMOKE,
                     this.targetStem.getX() + 0.5,
                     this.targetStem.getY() + 0.5,
                     this.targetStem.getZ() + 0.5,
                     10,
                     0.3,
                     0.3,
                     0.3,
                     0.05
                  );
               }
            }

            this.stop();
         }
      }
   }

   public boolean canContinueToUse() {
      return this.targetStem != null && this.targetAirPos != null && this.ghastly.level().getBlockState(this.targetStem).is(Blocks.CRIMSON_STEM);
   }

   public void stop() {
      this.targetStem = null;
      this.targetAirPos = null;
      this.ghastly.getNavigation().stop();
   }
}

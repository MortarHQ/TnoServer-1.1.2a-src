package com.benji.netherman.common.entity;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GhastlyPollinateGoal extends Goal {
   private final GhastlyEntity ghastly;
   private BlockPos targetPos = null;
   private int hoverTicks = 0;
   private int cooldown = 0;

   public GhastlyPollinateGoal(GhastlyEntity ghastly) {
      this.ghastly = ghastly;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.ghastly.isTame() || (Boolean)this.ghastly.getEntityData().get(GhastlyEntity.SHOW_HINT)) {
         return false;
      }

      if (this.cooldown > 0) {
         this.cooldown--;
         return false;
      }

      if (this.ghastly.getRandom().nextInt(20) == 0) {
         BlockPos mobPos = this.ghastly.blockPosition();

         for (int x = -8; x <= 8; x++) {
            for (int y = -4; y <= 4; y++) {
               for (int z = -8; z <= 8; z++) {
                  BlockPos checkPos = mobPos.offset(x, y, z);
                  Block block = this.ghastly.level().getBlockState(checkPos).getBlock();
                  if (block == Blocks.CRIMSON_ROOTS || block == Blocks.WARPED_ROOTS) {
                     this.targetPos = checkPos;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean canContinueToUse() {
      if (this.ghastly.isTame() || (Boolean)this.ghastly.getEntityData().get(GhastlyEntity.SHOW_HINT)) {
         return false;
      }

      if (this.targetPos == null) {
         return false;
      }

      Block block = this.ghastly.level().getBlockState(this.targetPos).getBlock();
      return block == Blocks.CRIMSON_ROOTS || block == Blocks.WARPED_ROOTS;
   }

   public void start() {
      this.hoverTicks = 0;
   }

   public void tick() {
      if (this.targetPos != null) {
         double dist = this.ghastly.distanceToSqr(this.targetPos.getX() + 0.5, this.targetPos.getY() + 1.0, this.targetPos.getZ() + 0.5);
         if (dist > 2.0) {
            this.ghastly.getNavigation().moveTo(this.targetPos.getX() + 0.5, this.targetPos.getY() + 1.2, this.targetPos.getZ() + 0.5, 1.0);
         } else {
            this.ghastly.getNavigation().stop();
            this.ghastly.setDeltaMovement(this.ghastly.getDeltaMovement().multiply(0.5, 0.5, 0.5));
            this.hoverTicks++;
            if (this.hoverTicks >= 40) {
               this.performAction();
               this.targetPos = null;
               this.cooldown = 200;
            }
         }
      }
   }

   public void stop() {
      this.targetPos = null;
      this.hoverTicks = 0;
      this.ghastly.getNavigation().stop();
   }

   private void performAction() {
      Block block = this.ghastly.level().getBlockState(this.targetPos).getBlock();
      boolean isEat = this.ghastly.getRandom().nextBoolean();
      if (isEat) {
         this.ghastly.getEntityData().set(GhastlyEntity.IS_EATING, true);
         this.ghastly.eatTicks = 60;
         this.ghastly.level().destroyBlock(this.targetPos, false);
      } else if (this.ghastly.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            ParticleTypes.CRIMSON_SPORE, this.targetPos.getX() + 0.5, this.targetPos.getY() + 0.5, this.targetPos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.0
         );
         int toPlant = 2 + this.ghastly.getRandom().nextInt(2);

         for (int i = 0; i < 10 && toPlant > 0; i++) {
            BlockPos p = this.targetPos
               .offset(this.ghastly.getRandom().nextInt(5) - 2, this.ghastly.getRandom().nextInt(3) - 1, this.ghastly.getRandom().nextInt(5) - 2);
            if (this.ghastly.level().isEmptyBlock(p) && this.ghastly.level().getBlockState(p.below()).isSolidRender(this.ghastly.level(), p.below())) {
               this.ghastly.level().setBlockAndUpdate(p, block.defaultBlockState());
               toPlant--;
            }
         }
      }
   }
}

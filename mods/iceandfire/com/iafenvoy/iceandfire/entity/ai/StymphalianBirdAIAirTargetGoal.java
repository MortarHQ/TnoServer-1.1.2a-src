package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.StymphalianBirdEntity;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class StymphalianBirdAIAirTargetGoal extends Goal {
   private final StymphalianBirdEntity bird;

   public StymphalianBirdAIAirTargetGoal(StymphalianBirdEntity bird) {
      this.bird = bird;
   }

   public static BlockPos getNearbyAirTarget(StymphalianBirdEntity bird) {
      if (bird.getTarget() == null) {
         BlockPos pos = DragonUtils.getBlockInViewStymphalian(bird);
         if (pos != null && bird.level().getBlockState(pos).isAir()) {
            return pos;
         }

         if (bird.flock != null && bird.flock.isLeader(bird)) {
            bird.flock.setTarget(bird.airTarget);
         }

         return bird.blockPosition();
      } else {
         return BlockPos.containing(bird.getTarget().getBlockX(), bird.getTarget().getY() + bird.getTarget().getEyeHeight(), bird.getTarget().getBlockZ());
      }
   }

   public boolean canUse() {
      if (this.bird != null) {
         if (!this.bird.isFlying()) {
            return false;
         }

         if (!this.bird.isBaby() && !this.bird.doesWantToLand()) {
            if (this.bird.airTarget != null && this.bird.isTargetBlocked(Vec3.atCenterOf(this.bird.airTarget))) {
               this.bird.airTarget = null;
            }

            if (this.bird.airTarget != null) {
               return false;
            }

            Vec3 vec = this.findAirTarget();
            if (vec == null) {
               return false;
            }

            this.bird.airTarget = BlockPos.containing(vec);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      if (!this.bird.isFlying()) {
         return false;
      } else {
         return this.bird.isBaby() ? false : this.bird.airTarget != null;
      }
   }

   public Vec3 findAirTarget() {
      return Vec3.atCenterOf(getNearbyAirTarget(this.bird));
   }
}

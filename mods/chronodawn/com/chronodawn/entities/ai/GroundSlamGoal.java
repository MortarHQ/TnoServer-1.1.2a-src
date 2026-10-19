package com.chronodawn.entities.ai;

import com.chronodawn.entities.bosses.ChronosWardenEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class GroundSlamGoal extends Goal {
   private final ChronosWardenEntity warden;
   private final double minDistanceToTarget;
   private final double maxDistanceToTarget;

   public GroundSlamGoal(ChronosWardenEntity warden, double minDistance, double maxDistance) {
      this.warden = warden;
      this.minDistanceToTarget = minDistance * minDistance;
      this.maxDistanceToTarget = maxDistance * maxDistance;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      LivingEntity target = this.warden.getTarget();
      if (target == null || !target.isAlive()) {
         return false;
      }

      if (this.warden.isInStoneStance()) {
         return false;
      }

      if (!this.warden.canGroundSlam()) {
         return false;
      }

      double distanceSquared = this.warden.distanceToSqr(target);
      return distanceSquared >= this.minDistanceToTarget && distanceSquared <= this.maxDistanceToTarget;
   }

   public boolean canContinueToUse() {
      return this.canUse();
   }

   public void start() {
      LivingEntity target = this.warden.getTarget();
      if (target != null) {
         this.warden.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      this.warden.performGroundSlam();
   }

   public void stop() {
   }
}

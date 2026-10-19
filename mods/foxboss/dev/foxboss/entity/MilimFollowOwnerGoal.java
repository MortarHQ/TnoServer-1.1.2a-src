package dev.foxboss.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

final class MilimFollowOwnerGoal extends FollowOwnerGoal {
   private final MilimEntity milim;

   MilimFollowOwnerGoal(MilimEntity milim) {
      super(milim, 1.15, 2.0F, 2.0F);
      this.milim = milim;
   }

   private boolean canFollow() {
      return this.milim.action() == 0
         && !this.milim.isDebugAnimation()
         && !this.milim.isDemo()
         && !this.milim.isWandering()
         && !this.milim.hasCombatTarget()
         && !this.milim.isNoAi()
         && !this.milim.unableToMoveToOwner();
   }

   public boolean canUse() {
      return this.canFollow() && this.milim.needsOwnerFollowing() && super.canUse();
   }

   public boolean canContinueToUse() {
      return this.canFollow() && super.canContinueToUse();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void start() {
      super.start();
      this.milim.setSprinting(false);
   }

   public void tick() {
      LivingEntity owner = this.milim.getOwner();
      if (this.canFollow() && owner != null && owner.isAlive()) {
         super.tick();
         double distance = this.milim.distanceToSqr(owner);
         boolean ownerRunning = owner.isSprinting() || owner.getDeltaMovement().horizontalDistanceSqr() > 0.04;
         boolean running = distance > 9.0 && (this.milim.isSprinting() || distance >= 49.0 || ownerRunning && distance > 16.0);
         this.milim.setSprinting(running);
         this.milim.getNavigation().setSpeedModifier(running ? 1.45 : 1.15);
      } else {
         this.milim.setSprinting(false);
         this.milim.getNavigation().stop();
      }
   }

   public void stop() {
      super.stop();
      this.milim.setSprinting(false);
   }
}

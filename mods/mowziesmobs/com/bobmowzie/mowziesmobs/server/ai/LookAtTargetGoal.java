package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class LookAtTargetGoal extends Goal {
   protected final Mob mob;
   @Nullable
   protected Entity lookAt;
   protected final float lookDistance;
   private final boolean onlyHorizontal;

   public LookAtTargetGoal(Mob mob, float distance) {
      this(mob, distance, false);
   }

   public LookAtTargetGoal(Mob mob, float distance, boolean onlyHorizontal) {
      this.mob = mob;
      this.lookDistance = distance;
      this.onlyHorizontal = onlyHorizontal;
      this.setFlags(EnumSet.of(Flag.LOOK));
   }

   public boolean canUse() {
      if (this.mob.getTarget() != null) {
         this.lookAt = this.mob.getTarget();
      }

      return this.lookAt != null;
   }

   public boolean canContinueToUse() {
      if (this.lookAt == null) {
         return false;
      } else if (this.lookAt != this.mob.getTarget()) {
         return false;
      } else {
         return !this.lookAt.isAlive() ? false : !(this.mob.distanceToSqr(this.lookAt) > this.lookDistance * this.lookDistance);
      }
   }

   public void stop() {
      this.lookAt = null;
   }

   public void tick() {
      if (this.lookAt.isAlive()) {
         double d0 = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
         this.mob.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
      }
   }
}

package com.notunanancyowen.spears.goals;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.KineticWeapon;
import java.util.EnumSet;
import java.util.Optional;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;

public class UseSpearGoal<T extends Monster> extends Goal {
   private final T mob;
   double speedModifierWhenCharging;
   double speedModifierWhenRepositioning;
   float approachDistanceSq;
   float targetInRangeRadiusSq;
   int engageTime = -1;
   int fleeingTime = -1;
   Vec3 awayPos;

   public UseSpearGoal(T mob, double d, double e, float f, float g) {
      this.mob = mob;
      this.speedModifierWhenCharging = d;
      this.speedModifierWhenRepositioning = e;
      this.approachDistanceSq = f * f;
      this.targetInRangeRadiusSq = g * g;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.canContinueToUse() && !this.mob.isUsingItem();
   }

   public boolean canContinueToUse() {
      return this.mob.getTarget() != null && this.mob.getMainHandItem().has(Spears.KINETIC_WEAPON);
   }

   public void start() {
      super.start();
      this.mob.setAggressive(true);
   }

   public void stop() {
      super.stop();
      this.mob.getNavigation().stop();
      this.mob.setAggressive(false);
      this.mob.releaseUsingItem();
   }

   public void tick() {
      if (this.mob.getTarget() instanceof LivingEntity target) {
         double d = this.mob.distanceToSqr(target);
         Entity entity = this.mob.getRootVehicle();
         float f = 1.0F;
         if (entity instanceof ZombieHorse) {
            f = 1.4F;
         }

         if (entity instanceof Camel) {
            f = 4.0F;
         }

         int i = this.mob.isPassenger() ? 2 : 0;
         this.mob.lookAt(target, 30.0F, 30.0F);
         this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
         if (this.engageTime < 0) {
            if (d > this.approachDistanceSq) {
               this.mob.getNavigation().moveTo(target, f * this.speedModifierWhenRepositioning);
               return;
            }

            this.engageTime = reducedTickDelay(
               Optional.ofNullable((KineticWeapon)this.mob.getMainHandItem().get(Spears.KINETIC_WEAPON)).map(KineticWeapon::getUseTicks).orElse(0)
            );
            this.mob.startUsingItem(InteractionHand.MAIN_HAND);
         }

         if (this.engageTime > 0) {
            if (--this.engageTime == 0) {
               this.mob.releaseUsingItem();
               double e = Math.sqrt(d);
               this.awayPos = DefaultRandomPos.getPosAway(this.mob, (int)Math.max(1.0, 11 + i - e), 7, target.position());
               this.fleeingTime = 1;
            } else {
               this.mob.getNavigation().moveTo(target, f * this.speedModifierWhenCharging);
            }
         }

         if (this.fleeingTime > 0 && ++this.fleeingTime <= reducedTickDelay(100)) {
            if (this.awayPos != null) {
               this.mob.getNavigation().moveTo(this.awayPos.x, this.awayPos.y, this.awayPos.z, f * this.speedModifierWhenRepositioning);
               if (this.mob.getNavigation().isDone()) {
                  if (this.fleeingTime > 0) {
                     return;
                  }

                  this.awayPos = null;
               }
            } else {
               this.mob.getNavigation().moveTo(target, f * this.speedModifierWhenCharging);
               if (d < this.targetInRangeRadiusSq || this.mob.getNavigation().isDone()) {
                  this.awayPos = DefaultRandomPos.getPosAway(this.mob, 7 + i, 7, target.position());
               }
            }
         }
      }
   }
}

package net.pixeldreamstudios.mythika.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class TimedMeleeAttackGoal<T extends PathfinderMob & TimedAttacker> extends MeleeAttackGoal {
   private final T attacker;

   public TimedMeleeAttackGoal(T mob, double speedModifier) {
      super(mob, speedModifier, true);
      this.attacker = mob;
   }

   public void tick() {
      if (this.attacker.isAttackLocked()) {
         this.attacker.getNavigation().stop();
         this.attacker.setZza(0.0F);
         this.attacker.setXxa(0.0F);
         this.attacker.setDeltaMovement(0.0, this.attacker.getDeltaMovement().y, 0.0);
         LivingEntity target = this.attacker.getTarget();
         if (target != null) {
            this.attacker.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      } else {
         super.tick();
      }
   }

   protected void checkAndPerformAttack(LivingEntity target) {
      if (this.attacker.canStartAttack() && this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) {
         this.resetAttackCooldown();
         this.attacker.startAttack();
      }
   }
}

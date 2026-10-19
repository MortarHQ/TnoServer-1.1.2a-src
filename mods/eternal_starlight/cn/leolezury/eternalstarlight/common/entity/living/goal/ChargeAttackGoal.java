package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class ChargeAttackGoal extends Goal {
   private final PathfinderMob charger;
   private LivingEntity target;
   private Vec3 targetPos = Vec3.ZERO;
   private final boolean extendTarget;
   private final float speed;
   private final int randomFrequency;
   private final int chargeTime;
   private final float attackRange;
   private int chargeTicks;
   private boolean ended;

   public ChargeAttackGoal(PathfinderMob mob, boolean extendTarget, float speed, int randomFrequency, int chargeTime, float attackRange) {
      this.charger = mob;
      this.extendTarget = extendTarget;
      this.speed = speed;
      this.randomFrequency = randomFrequency;
      this.chargeTime = chargeTime;
      this.attackRange = attackRange;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      this.target = this.charger.getTarget();
      if (this.target == null) {
         return false;
      }

      Vec3 chargerPos = this.charger.position();
      Vec3 chargeTargetPos = this.target.position();
      if (this.target.distanceTo(this.charger) > 20.0F) {
         return false;
      }

      if (this.charger.getSensing().hasLineOfSight(this.target)) {
         if (this.extendTarget) {
            this.targetPos = chargerPos.add(chargeTargetPos.subtract(chargerPos).scale(2.0));
         } else {
            this.targetPos = chargeTargetPos;
         }

         return this.charger.getRandom().nextInt(this.randomFrequency) == 0;
      } else {
         return false;
      }
   }

   public void start() {
      super.start();
      this.chargeTicks = this.chargeTime;
   }

   public boolean canContinueToUse() {
      return this.chargeTicks > 0 || !this.charger.getNavigation().isDone();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      super.tick();
      this.charger.getLookControl().setLookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z, 10.0F, this.charger.getMaxHeadXRot());
      if (this.chargeTicks > 0) {
         this.chargeTicks--;
         if (this.chargeTicks <= 0) {
            this.charger.getNavigation().moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.speed);
         } else {
            this.charger.walkAnimation.setSpeed(this.charger.walkAnimation.speed() + 0.5F);
            if (this.charger instanceof Charger chargingMob) {
               chargingMob.setCharging(true);
            }
         }
      }

      for (LivingEntity living : this.charger.level().getEntitiesOfClass(LivingEntity.class, this.charger.getBoundingBox().inflate(this.attackRange))) {
         if (!this.ended && living == this.target) {
            this.charger.doHurtTarget(this.target);
            this.ended = true;
         }
      }
   }

   public void stop() {
      super.stop();
      this.chargeTicks = 0;
      this.target = null;
      this.ended = false;
      if (this.charger instanceof Charger chargingMob) {
         chargingMob.setCharging(false);
      }
   }
}

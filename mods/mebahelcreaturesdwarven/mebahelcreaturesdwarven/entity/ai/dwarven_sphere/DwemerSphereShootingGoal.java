package mebahelcreaturesdwarven.entity.ai.dwarven_sphere;

import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import mebahelcreaturesdwarven.entity.projectile.DwarvenArrowEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.mebahelsapi.util.MovementUtil;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public class DwemerSphereShootingGoal extends class_1352 {
   private final DwemerSphereEntity actor;
   private final MovementUtil movementUtil;
   private static final double STRAFE_DISTANCE = 6.0;
   private static final int ANIM_TICKS = 40;
   private static final int CYCLE_TICKS = 40;
   private static final int SHOOT_TICK_IN_ANIM = 28;
   private static final int SHOOT_CD = 12;
   private static final int DRAW_TICK_IN_ANIM = 12;
   private static final int RELEASE_TICK_IN_ANIM = 28;
   private static final int DRAW_CD = 28;
   private static final int RELEASE_CD = 12;
   private static final double LEFT_OFFSET = -0.55;
   private static final double FORWARD_OFFSET = 0.25;
   private static final double Y_OFFSET = 1.55;
   private static final int EXIT_TO_MELEE_PAUSE_TICKS = 20;
   private boolean pendingExitToMelee = false;
   private int exitToMeleePauseTicks = 0;

   public DwemerSphereShootingGoal(DwemerSphereEntity actor) {
      this.actor = actor;
      this.movementUtil = new MovementUtil(this.actor, 6.0);
      this.movementUtil.setTolerance(3.0);
   }

   public boolean method_6264() {
      if (!this.actor.isRangedMode()) {
         return false;
      }

      class_1309 livingEntity = this.actor.method_5968();
      return !(livingEntity instanceof class_1657 playerEntity && (playerEntity.method_7337() || playerEntity.method_7325()))
         ? livingEntity != null && livingEntity.method_5805() && !this.actor.isTransforming()
         : false;
   }

   public boolean method_6266() {
      class_1309 livingEntity = this.actor.method_5968();
      if (livingEntity instanceof class_1657 playerEntity && (playerEntity.method_7337() || playerEntity.method_7325())) {
         return false;
      } else if (livingEntity == null || !livingEntity.method_5805() || this.actor.isTransforming()) {
         return false;
      } else {
         return this.pendingExitToMelee ? true : this.actor.isRangedMode();
      }
   }

   public void method_6269() {
      this.pendingExitToMelee = false;
      this.exitToMeleePauseTicks = 0;
      this.beginNewCycle();
   }

   public void method_6270() {
      this.actor.setCooldown(40.0F);
      this.actor.setShooting(false);
      this.pendingExitToMelee = false;
      this.exitToMeleePauseTicks = 0;
      this.hardStop(null);
   }

   public boolean method_38846() {
      return true;
   }

   private void beginNewCycle() {
      this.actor.setCooldown(40.0F);
      this.actor.setShooting(true);
      this.actor.bumpAttackBowSeq();
      this.actor.triggerAnim("attacking", "attack_bow");
   }

   private void hardStop(class_1309 target) {
      this.actor.method_5942().method_6340();
      this.actor.method_5962().method_6243(0.0F, 0.0F);
      class_243 v = this.actor.method_18798();
      this.actor.method_18800(0.0, v.field_1351, 0.0);
      this.actor.field_6007 = true;
      if (target != null) {
         this.actor.method_5988().method_6226(target, 30.0F, 30.0F);
      }
   }

   public void method_6268() {
      class_1309 target = this.actor.method_5968();
      if (target == null || !target.method_5805()) {
         this.method_6270();
      } else if (!this.pendingExitToMelee) {
         this.movementUtil.maintainRangedPosition(target);
         class_1937 world = this.actor.method_37908();
         int cd = (int)this.actor.getCooldown();
         if (cd <= 0) {
            this.actor.rollModeSwitchAfterAttack();
            if (!this.actor.isRangedMode()) {
               this.actor.setShooting(false);
               this.actor.setCooldown(40.0F);
               this.pendingExitToMelee = true;
               this.exitToMeleePauseTicks = 20;
               this.hardStop(target);
            } else {
               this.beginNewCycle();
            }
         } else {
            this.actor.setShooting(true);
            cd = Math.max(cd - 1, 0);
            this.actor.setCooldown(cd);
            if (cd == 28) {
               world.method_43128(
                  null,
                  this.actor.method_23317(),
                  this.actor.method_23318(),
                  this.actor.method_23321(),
                  ModSounds.DWARVEN_SPHERE_BOW_DRAW,
                  this.actor.method_5634(),
                  0.75F + this.actor.method_59922().method_43057() * 0.2F,
                  0.8F + this.actor.method_59922().method_43057() * 0.4F
               );
            }

            if (cd == 12) {
               world.method_43128(
                  null,
                  this.actor.method_23317(),
                  this.actor.method_23318(),
                  this.actor.method_23321(),
                  ModSounds.DWARVEN_SPHERE_BOW_RELEASE,
                  this.actor.method_5634(),
                  0.75F + this.actor.method_59922().method_43057() * 0.2F,
                  0.8F + this.actor.method_59922().method_43057() * 0.4F
               );
            }

            if (cd == 12) {
               DwarvenArrowEntity arrow = new DwarvenArrowEntity(ModEntities.DWARVEN_ARROW, world, this.actor);
               float yawRad = (float)Math.toRadians(this.actor.method_43078());
               double fx = -class_3532.method_15374(yawRad);
               double fz = class_3532.method_15362(yawRad);
               double lx = -fz;
               double lz = fx;
               double xProjectile = this.actor.method_23317() + fx * 0.25 + lx * -0.55;
               double yProjectile = this.actor.method_23323(0.0) + 1.55;
               double zProjectile = this.actor.method_23321() + fz * 0.25 + lz * -0.55;
               arrow.method_5814(xProjectile, yProjectile, zProjectile);
               double a = target.method_23320() - 1.1F;
               double e = target.method_23317() - xProjectile;
               double f = a - arrow.method_23318();
               double g = target.method_23321() - zProjectile;
               double h = Math.sqrt(e * e + g * g) * 0.2F;
               float distance;
               float speed;
               if (this.actor.method_5739(target) > 25.0F) {
                  distance = 0.85F;
                  speed = 2.1F;
               } else if (this.actor.method_5739(target) >= 12.0F && this.actor.method_5739(target) <= 17.0F) {
                  distance = 0.8F;
                  speed = 2.05F;
               } else {
                  distance = 0.75F;
                  speed = 2.0F;
               }

               arrow.method_7485(e, f + h * distance, g, speed, 0.0F);
               world.method_8649(arrow);
            }
         }
      } else if (this.exitToMeleePauseTicks > 0) {
         this.exitToMeleePauseTicks--;
         this.actor.setShooting(false);
         this.hardStop(target);
      } else {
         this.pendingExitToMelee = false;
         this.exitToMeleePauseTicks = 0;
         this.actor.setShooting(false);
      }
   }
}

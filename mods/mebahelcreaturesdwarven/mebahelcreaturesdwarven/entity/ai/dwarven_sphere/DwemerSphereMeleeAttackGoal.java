package mebahelcreaturesdwarven.entity.ai.dwarven_sphere;

import java.util.EnumSet;
import java.util.Objects;
import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1309;
import net.minecraft.class_1324;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_5134;
import net.minecraft.class_1352.class_4134;

public class DwemerSphereMeleeAttackGoal extends class_1352 {
   protected final DwemerSphereEntity mob;
   private final double speed;
   private static final double ORIGINAL_SPEED = 0.3;
   private final double attackRangeSq;
   private final double hitRangeSq;
   private final double stopMovingRangeSq;
   private static final int RECOVERY_TICKS = 6;
   private static final float TURN_SPEED = 12.0F;
   private int cooldown = 0;
   private int attackTicks = 0;
   private boolean hit1Done = false;
   private boolean hit2Done = false;
   private boolean sound1Done = false;
   private boolean sound2Done = false;
   private DwemerSphereMeleeAttackGoal.AttackVariant currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.NONE;
   private static final int ATTACK1_DURATION = 30;
   private static final int ATTACK2_DURATION = 30;
   private static final int ATTACK_DOUBLE_DURATION = 40;
   private static final int ATTACK1_HIT = 14;
   private static final int ATTACK2_HIT = 13;
   private static final int DOUBLE_HIT_1 = 16;
   private static final int DOUBLE_HIT_2 = 26;
   private static final int SOUND_BEFORE_HIT_TICKS = 4;

   public DwemerSphereMeleeAttackGoal(DwemerSphereEntity mob, double speed, int attackDistance, double attackRange) {
      this.mob = mob;
      this.speed = speed;
      this.attackRangeSq = attackRange * attackRange;
      double hitRange = attackRange + attackDistance;
      this.hitRangeSq = hitRange * hitRange;
      double stopMovingRange = Math.max(1.0, attackRange - 0.75);
      this.stopMovingRangeSq = stopMovingRange * stopMovingRange;
      this.method_6265(EnumSet.of(class_4134.field_18405, class_4134.field_18406));
   }

   public boolean method_6264() {
      if (this.mob.isRangedMode()) {
         return false;
      }

      if (this.mob.isTransforming()) {
         return false;
      }

      class_1309 target = this.mob.method_5968();
      return !(target instanceof class_1657 p && (p.method_7337() || p.method_7325())) ? target != null && target.method_5805() : false;
   }

   public boolean method_6266() {
      if (this.mob.isRangedMode()) {
         return false;
      }

      if (this.mob.isTransforming()) {
         return false;
      }

      class_1309 target = this.mob.method_5968();
      return !(target instanceof class_1657 p && (p.method_7337() || p.method_7325())) ? target != null && target.method_5805() : false;
   }

   public void method_6269() {
      this.mob.method_19540(true);
      this.mob.setSwinging(false);
      this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.cooldown = 0;
      class_1309 target = this.mob.method_5968();
      if (target != null) {
         this.mob.method_5942().method_6335(target, this.speed);
      }
   }

   public void method_6270() {
      this.mob.method_19540(false);
      this.mob.setSwinging(false);
      this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.restoreMovementSpeed();
      this.stopMovementHard();
   }

   public boolean method_38846() {
      return true;
   }

   public void method_6268() {
      class_1309 target = this.mob.method_5968();
      if (target != null && target.method_5805()) {
         this.mob.method_5988().method_6226(target, 30.0F, 30.0F);
         this.attack(target);
      } else {
         this.method_6270();
      }
   }

   private void attack(class_1309 target) {
      double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
      if (!this.mob.isSwinging() && this.cooldown > 0) {
         this.cooldown = Math.max(0, this.cooldown - 1);
      }

      if (!this.mob.isSwinging()) {
         this.restoreMovementSpeedIfNeeded();
         if (distSq <= this.stopMovingRangeSq) {
            this.stopMovingInMeleeRange();
         } else {
            this.mob.method_5942().method_6335(target, this.speed);
         }
      }

      if (this.mob.isSwinging()) {
         this.turnTowardTargetSmooth(target);
         this.freezeMovement();
      }

      if (!this.mob.isSwinging() && this.cooldown == 0 && distSq <= this.attackRangeSq) {
         this.startNewAttack();
      }

      if (this.mob.isSwinging()) {
         this.attackTicks++;
         switch (this.currentAttack) {
            case NONE:
               this.endAttack(target);
               break;
            case ATTACK1:
               this.handleAttack1(target);
               break;
            case ATTACK2:
               this.handleAttack2(target);
               break;
            case ATTACK_DOUBLE:
               this.handleAttackDouble(target);
         }
      }
   }

   private void startNewAttack() {
      this.mob.setSwinging(true);
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.freezeMovement();
      double r = Math.random();
      if (r < 0.34) {
         this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.ATTACK1;
         this.mob.setAttackName("attack1");
      } else if (r < 0.67) {
         this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.ATTACK2;
         this.mob.setAttackName("attack2");
      } else {
         this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.ATTACK_DOUBLE;
         this.mob.setAttackName("attack_double");
      }

      this.mob.triggerAnim("attacking", this.mob.getAttackName());
   }

   private void handleAttack1(class_1309 target) {
      if (!this.sound1Done && this.attackTicks == 10) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 14) {
         this.doHitOnce(target, false);
         this.hit1Done = true;
      }

      if (this.attackTicks >= 30) {
         this.endAttack(target);
      }
   }

   private void handleAttack2(class_1309 target) {
      if (!this.sound1Done && this.attackTicks == 9) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 13) {
         this.doHitOnce(target, false);
         this.hit1Done = true;
      }

      if (this.attackTicks >= 30) {
         this.endAttack(target);
      }
   }

   private void handleAttackDouble(class_1309 target) {
      if (!this.sound1Done && this.attackTicks == 12) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 16) {
         this.doHitOnce(target, false);
         this.hit1Done = true;
      }

      if (!this.sound2Done && this.attackTicks == 22) {
         this.playAttackSound();
         this.sound2Done = true;
      }

      if (!this.hit2Done && this.attackTicks == 26) {
         this.doHitOnce(target, true);
         this.hit2Done = true;
      }

      if (this.attackTicks >= 40) {
         this.endAttack(target);
      }
   }

   private void doHitOnce(class_1309 target, boolean bypassInvulnForThisHit) {
      double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
      if (!(distSq > this.hitRangeSq)) {
         if (bypassInvulnForThisHit) {
            target.field_6008 = 0;
            target.field_6235 = 0;
         }

         this.mob.method_6121(target);
      }
   }

   private void endAttack(class_1309 target) {
      this.mob.setSwinging(false);
      this.currentAttack = DwemerSphereMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.restoreMovementSpeed();
      if (target != null && target.method_5805()) {
         double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
         if (distSq <= this.stopMovingRangeSq) {
            this.stopMovingInMeleeRange();
         }
      }

      this.cooldown = 6;
      this.mob.rollModeSwitchAfterAttack();
   }

   private void freezeMovement() {
      Objects.requireNonNull(this.mob.method_5996(class_5134.field_23719)).method_6192(0.0);
      this.stopMovementHard();
   }

   private void restoreMovementSpeed() {
      Objects.requireNonNull(this.mob.method_5996(class_5134.field_23719)).method_6192(0.3);
   }

   private void restoreMovementSpeedIfNeeded() {
      class_1324 attr = Objects.requireNonNull(this.mob.method_5996(class_5134.field_23719));
      if (attr.method_6201() == 0.0) {
         attr.method_6192(0.3);
      }
   }

   private void stopMovingInMeleeRange() {
      this.mob.method_5942().method_6340();
      this.mob.method_5962().method_6243(0.0F, 0.0F);
      this.mob.method_5930(0.0F);
      this.mob.method_5938(0.0F);
      this.mob.method_6125(0.0F);
      class_243 velocity = this.mob.method_18798();
      this.mob.method_18800(velocity.field_1352 * 0.2, velocity.field_1351, velocity.field_1350 * 0.2);
      this.mob.field_6007 = true;
   }

   private void stopMovementHard() {
      this.mob.method_5942().method_6340();
      this.mob.method_5962().method_6243(0.0F, 0.0F);
      this.mob.method_5930(0.0F);
      this.mob.method_5938(0.0F);
      this.mob.method_6125(0.0F);
      this.mob.method_18800(0.0, this.mob.method_18798().field_1351, 0.0);
      this.mob.field_6007 = true;
   }

   private void playAttackSound() {
      switch (this.mob.getAttackName()) {
         case "attack1":
         case "attack2":
            if (this.mob.method_59922().method_43056()) {
               this.mob
                  .method_37908()
                  .method_43128(
                     null,
                     this.mob.method_23317(),
                     this.mob.method_23318(),
                     this.mob.method_23321(),
                     ModSounds.DWARVEN_SPHERE_ATTACK_1,
                     this.mob.method_5634(),
                     0.75F + this.mob.method_59922().method_43057() * 0.2F,
                     0.8F + this.mob.method_59922().method_43057() * 0.4F
                  );
            } else {
               this.mob
                  .method_37908()
                  .method_43128(
                     null,
                     this.mob.method_23317(),
                     this.mob.method_23318(),
                     this.mob.method_23321(),
                     ModSounds.DWARVEN_SPHERE_ATTACK_2,
                     this.mob.method_5634(),
                     0.75F + this.mob.method_59922().method_43057() * 0.2F,
                     0.8F + this.mob.method_59922().method_43057() * 0.4F
                  );
            }
            break;
         case "attack_double":
            this.mob
               .method_37908()
               .method_43128(
                  null,
                  this.mob.method_23317(),
                  this.mob.method_23318(),
                  this.mob.method_23321(),
                  ModSounds.DWARVEN_SPHERE_ATTACK_3,
                  this.mob.method_5634(),
                  0.75F + this.mob.method_59922().method_43057() * 0.2F,
                  0.8F + this.mob.method_59922().method_43057() * 0.4F
               );
      }
   }

   private void turnTowardTargetSmooth(class_1309 target) {
      double dx = target.method_23317() - this.mob.method_23317();
      double dz = target.method_23321() - this.mob.method_23321();
      if (!(dx * dx + dz * dz < 1.0E-6)) {
         float desiredYaw = (float)(class_3532.method_15349(dz, dx) * (180.0 / Math.PI)) - 90.0F;
         float newYaw = class_3532.method_15388(this.mob.method_36454(), desiredYaw, 12.0F);
         this.mob.method_36456(newYaw);
         float headNew = class_3532.method_15388(this.mob.method_5791(), desiredYaw, 18.0F);
         this.mob.method_5847(headNew);
      }
   }

   private enum AttackVariant {
      NONE,
      ATTACK1,
      ATTACK2,
      ATTACK_DOUBLE;
   }
}

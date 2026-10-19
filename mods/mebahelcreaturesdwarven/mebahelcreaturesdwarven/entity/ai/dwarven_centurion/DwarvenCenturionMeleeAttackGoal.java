package mebahelcreaturesdwarven.entity.ai.dwarven_centurion;

import java.util.EnumSet;
import java.util.Objects;
import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1309;
import net.minecraft.class_1324;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_5134;
import net.minecraft.class_1352.class_4134;

public class DwarvenCenturionMeleeAttackGoal extends class_1352 {
   protected final DwemerCenturionEntity mob;
   private final double speed;
   private final int attackDistance;
   private static final double ORIGINAL_SPEED = 0.3;
   private static final double ATTACK_RANGE = 4.0;
   private static final double ATTACK_RANGE_SQ = 16.0;
   private static final double STOP_MOVING_RANGE = 3.0;
   private static final double STOP_MOVING_RANGE_SQ = 9.0;
   private final double hitRangeSq;
   private static final int RECOVERY_TICKS = 8;
   private static final float TURN_SPEED = 14.0F;
   private int cooldown = 0;
   private int attackTicks = 0;
   private boolean hit1Done = false;
   private boolean hit2Done = false;
   private boolean hasDashed = false;
   private boolean sound1Done = false;
   private boolean sound2Done = false;
   private DwarvenCenturionMeleeAttackGoal.AttackVariant currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.NONE;
   private static final int ATTACK1_DURATION = 40;
   private static final int ATTACK2_DURATION = 40;
   private static final int ATTACK_DOUBLE_DURATION = 45;
   private static final int ATTACK_DUAL_DURATION = 40;
   private static final int ATTACK1_HIT = 15;
   private static final int ATTACK2_HIT = 18;
   private static final int DOUBLE_HIT_1 = 15;
   private static final int DOUBLE_HIT_2 = 30;
   private static final int DUAL_HIT = 16;
   private static final int DASH_BEFORE_HIT_TICKS = 4;
   private static final int SOUND_BEFORE_HIT_TICKS = 4;
   private static final double BASE_DASH_POWER = 0.75;

   public DwarvenCenturionMeleeAttackGoal(DwemerCenturionEntity mob, double speed, int attackDistance) {
      this.mob = mob;
      this.speed = speed;
      this.attackDistance = attackDistance;
      double hitRange = 4.0 + attackDistance;
      this.hitRangeSq = hitRange * hitRange;
      this.method_6265(EnumSet.of(class_4134.field_18405, class_4134.field_18406));
   }

   public boolean method_6264() {
      class_1309 target = this.mob.method_5968();
      return !(target instanceof class_1657 p && (p.method_7337() || p.method_7325()))
         ? target != null && target.method_5805() && !this.mob.isShooting()
         : false;
   }

   public boolean method_6266() {
      class_1309 target = this.mob.method_5968();
      return !(target instanceof class_1657 p && (p.method_7337() || p.method_7325()))
         ? target != null && target.method_5805() && !this.mob.isShooting()
         : false;
   }

   public void method_6269() {
      this.mob.method_19540(true);
      this.mob.setSwinging(false);
      this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.hasDashed = false;
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
      this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.hasDashed = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.restoreMovementSpeed();
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
         if (distSq <= 9.0) {
            this.stopMovingInMeleeRange();
         } else {
            this.mob.method_5942().method_6335(target, this.speed);
         }
      }

      if (this.mob.isSwinging()) {
         this.turnTowardTarget(target);
         this.mob.method_5942().method_6340();
      }

      if (!this.mob.isSwinging() && this.cooldown == 0 && distSq <= 16.0) {
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
               break;
            case ATTACK_DUAL:
               this.handleAttackDual(target);
         }
      }
   }

   private void startNewAttack() {
      this.mob.setSwinging(true);
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.hasDashed = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.freezeMovement();
      double r = Math.random();
      if (r < 0.25) {
         this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.ATTACK1;
         this.mob.setAttackName("attack1");
      } else if (r < 0.5) {
         this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.ATTACK2;
         this.mob.setAttackName("attack2");
      } else if (r < 0.75) {
         this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.ATTACK_DOUBLE;
         this.mob.setAttackName("attack_double");
      } else {
         this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.ATTACK_DUAL;
         this.mob.setAttackName("attack_dual");
      }

      this.mob.triggerAnim("attacking", this.mob.getAttackName());
   }

   private void handleAttack1(class_1309 target) {
      if (this.attackTicks == 11 && !this.hasDashed) {
         this.dashToward(target, 0.75);
         this.hasDashed = true;
      }

      if (!this.sound1Done && this.attackTicks == 11) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 15) {
         this.doHitOnce(target, 4.0F, false);
         this.hit1Done = true;
      }

      if (this.attackTicks >= 40) {
         this.endAttack(target);
      }
   }

   private void handleAttack2(class_1309 target) {
      if (this.attackTicks == 14 && !this.hasDashed) {
         this.dashToward(target, 0.75);
         this.hasDashed = true;
      }

      if (!this.sound1Done && this.attackTicks == 14) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 18) {
         this.doHitOnce(target, 4.0F, false);
         this.hit1Done = true;
      }

      if (this.attackTicks >= 40) {
         this.endAttack(target);
      }
   }

   private void handleAttackDouble(class_1309 target) {
      if (this.attackTicks == 11) {
         this.dashToward(target, 0.75);
      }

      if (this.attackTicks == 26) {
         this.dashToward(target, 0.75);
      }

      if (!this.sound1Done && this.attackTicks == 11) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.sound2Done && this.attackTicks == 26) {
         this.playAttackSound();
         this.sound2Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 15) {
         this.doHitOnce(target, 4.0F, false);
         this.hit1Done = true;
      }

      if (!this.hit2Done && this.attackTicks == 30) {
         this.doHitOnce(target, 4.0F, false);
         this.hit2Done = true;
      }

      if (this.attackTicks >= 45) {
         this.endAttack(target);
      }
   }

   private void handleAttackDual(class_1309 target) {
      if (this.attackTicks == 12 && !this.hasDashed) {
         this.dashToward(target, 0.8624999999999999);
         this.hasDashed = true;
      }

      if (!this.sound1Done && this.attackTicks == 12) {
         this.playAttackSound();
         this.sound1Done = true;
      }

      if (!this.hit1Done && this.attackTicks == 16) {
         this.doHitOnce(target, 8.0F, true);
         this.hit1Done = true;
      }

      if (this.attackTicks >= 40) {
         this.endAttack(target);
      }
   }

   private void doHitOnce(class_1309 target, float areaDamage, boolean breakShield) {
      double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
      if (!(distSq > this.hitRangeSq)) {
         this.mob.method_6121(target);
         this.dealAreaDamage(target, areaDamage);
         if (breakShield) {
            this.breakShield(target);
         }
      }
   }

   private void endAttack(class_1309 target) {
      this.mob.setSwinging(false);
      this.currentAttack = DwarvenCenturionMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hit1Done = false;
      this.hit2Done = false;
      this.hasDashed = false;
      this.sound1Done = false;
      this.sound2Done = false;
      this.restoreMovementSpeed();
      if (target != null && target.method_5805()) {
         double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
         if (distSq <= 9.0) {
            this.stopMovingInMeleeRange();
         }
      }

      this.cooldown = 8;
   }

   private void freezeMovement() {
      Objects.requireNonNull(this.mob.method_5996(class_5134.field_23719)).method_6192(0.0);
      this.mob.method_5942().method_6340();
      this.mob.method_5962().method_6243(0.0F, 0.0F);
      this.mob.method_5930(0.0F);
      this.mob.method_5938(0.0F);
      this.mob.method_6125(0.0F);
      this.mob.method_18800(0.0, this.mob.method_18798().field_1351, 0.0);
      this.mob.field_6007 = true;
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

   private void turnTowardTarget(class_1309 target) {
      class_243 dir = target.method_19538().method_1020(this.mob.method_19538());
      if (!(dir.method_1027() < 1.0E-6)) {
         dir = dir.method_1029();
         float yaw = (float)(class_3532.method_15349(dir.field_1350, dir.field_1352) * (180.0 / Math.PI)) - 90.0F;
         this.mob.method_36456(yaw);
         this.mob.method_5847(yaw);
         this.mob.method_5636(yaw);
         this.mob.field_5982 = yaw;
         this.mob.field_6220 = yaw;
      }
   }

   private void dashToward(class_1309 target, double basePower) {
      class_243 rawDir = target.method_19538().method_1020(this.mob.method_19538());
      class_243 flatDir = new class_243(rawDir.field_1352, 0.0, rawDir.field_1350);
      if (!(flatDir.method_1027() < 1.0E-6)) {
         class_243 dir = flatDir.method_1029();
         float yaw = (float)(class_3532.method_15349(dir.field_1350, dir.field_1352) * (180.0 / Math.PI)) - 90.0F;
         this.mob.method_36456(yaw);
         this.mob.method_5847(yaw);
         this.mob.method_5636(yaw);
         this.mob.field_5982 = yaw;
         this.mob.field_6220 = yaw;
         double dist = Math.sqrt(flatDir.method_1027());
         double minDist = 2.0;
         double maxDist = 10.0;
         double minPower = basePower * 0.55;
         double maxPower = basePower * 1.75;
         double clamped = class_3532.method_15350((dist - minDist) / (maxDist - minDist), 0.0, 1.0);
         double scaledPower = class_3532.method_16436(clamped, minPower, maxPower);
         class_243 dash = dir.method_1021(scaledPower).method_1031(0.0, 0.05, 0.0);
         this.mob.method_5762(dash.field_1352, dash.field_1351, dash.field_1350);
         this.clampHorizontalVelocity(1.2);
         this.mob.field_6007 = true;
      }
   }

   private void clampHorizontalVelocity(double maxHorizontalSpeed) {
      class_243 velocity = this.mob.method_18798();
      class_243 horizontal = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
      if (horizontal.method_1027() > maxHorizontalSpeed * maxHorizontalSpeed) {
         class_243 clamped = horizontal.method_1029().method_1021(maxHorizontalSpeed);
         this.mob.method_18800(clamped.field_1352, velocity.field_1351, clamped.field_1350);
         this.mob.field_6007 = true;
      }
   }

   private void playAttackSound() {
      switch (this.mob.getAttackName()) {
         case "attack1":
         case "attack2":
         case "attack_dual":
            this.mob
               .method_37908()
               .method_43128(
                  null,
                  this.mob.method_23317(),
                  this.mob.method_23318(),
                  this.mob.method_23321(),
                  ModSounds.DWARVEN_CENTURION_ATTACK_1,
                  this.mob.method_5634(),
                  0.75F + this.mob.method_59922().method_43057() * 0.2F,
                  0.8F + this.mob.method_59922().method_43057() * 0.4F
               );
            break;
         case "attack_double":
            this.mob
               .method_37908()
               .method_43128(
                  null,
                  this.mob.method_23317(),
                  this.mob.method_23318(),
                  this.mob.method_23321(),
                  ModSounds.DWARVEN_CENTURION_ATTACK_2,
                  this.mob.method_5634(),
                  0.75F + this.mob.method_59922().method_43057() * 0.2F,
                  0.8F + this.mob.method_59922().method_43057() * 0.4F
               );
      }
   }

   private void breakShield(class_1309 target) {
      if (target instanceof class_1657 player && player.method_6039()) {
         class_1799 activeItem = player.method_6030();
         if (activeItem.method_7909() instanceof class_1819) {
            player.method_7357().method_7906(activeItem.method_7909(), 100);
            player.method_37908().method_8421(player, (byte)30);
            player.method_6021();
         }
      }
   }

   private void dealAreaDamage(class_1309 primaryTarget, float areaDamage) {
      this.mob
         .method_37908()
         .method_8333(
            this.mob,
            primaryTarget.method_5829().method_1014(1.0),
            e -> e instanceof class_1309 entity
               && !(entity instanceof DwemerEntity)
               && entity != primaryTarget
               && entity.method_5805()
               && this.mob.method_6057(entity)
         )
         .forEach(entity -> {
            entity.field_6008 = 0;
            entity.method_5643(this.mob.method_48923().method_48812(this.mob), areaDamage);
         });
   }

   private enum AttackVariant {
      NONE,
      ATTACK1,
      ATTACK2,
      ATTACK_DOUBLE,
      ATTACK_DUAL;
   }
}

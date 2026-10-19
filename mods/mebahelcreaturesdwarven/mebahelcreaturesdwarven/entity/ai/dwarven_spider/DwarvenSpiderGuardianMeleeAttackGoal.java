package mebahelcreaturesdwarven.entity.ai.dwarven_spider;

import java.util.EnumSet;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderGuardianEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_11;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_3532;
import net.minecraft.class_1352.class_4134;

public class DwarvenSpiderGuardianMeleeAttackGoal extends class_1352 {
   protected final DwemerSpiderGuardianEntity mob;
   private final double speed;
   private final int attackDistance;
   private static final int RECOVERY_TICKS = 6;
   private static final double ATTACK_RANGE_SQ = 8.0;
   private static final float TURN_SPEED = 10.0F;
   private long lastUpdateTime;
   private int cooldown = 0;
   private int attackTicks = 0;
   private boolean hitADone = false;
   private boolean hitBDone = false;
   private boolean soundDone = false;
   private DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.NONE;
   private static final int ATTACK_DURATION = 15;
   private static final int ATTACK1_DURATION = 30;
   private static final int ATTACK_DOUBLE_DURATION = 40;
   private static final int ATTACK_DELAY_DURATION = 46;
   private static final int ATTACK_HIT = 8;
   private static final int ATTACK1_HIT = 14;
   private static final int DOUBLE_HIT_1 = 14;
   private static final int DOUBLE_HIT_2 = 26;
   private static final int ATTACK_DELAY_HIT = 32;
   private static final int SOUND_ATTACK1_TICK = 4;
   private static final int SOUND_DOUBLE_TICK = 4;
   private static final int SOUND_DELAY_TICK = 8;

   public DwarvenSpiderGuardianMeleeAttackGoal(DwemerSpiderGuardianEntity mob, double speed, int attackDistance) {
      this.mob = mob;
      this.speed = speed;
      this.attackDistance = attackDistance;
      this.method_6265(EnumSet.of(class_4134.field_18405, class_4134.field_18406));
   }

   public boolean method_6264() {
      long t = this.mob.method_37908().method_8510();
      if (t - this.lastUpdateTime < 20L) {
         return false;
      } else {
         this.lastUpdateTime = t;
         class_1309 target = this.mob.method_5968();
         if (target instanceof class_1657 p && (p.method_7337() || p.method_7325())) {
            return false;
         } else if (target != null && target.method_5805()) {
            class_11 path = this.mob.method_5942().method_6349(target, 0);
            return path != null
               ? true
               : this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321()) <= 8.0 && !this.mob.isShooting();
         } else {
            return false;
         }
      }
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
      this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hitADone = false;
      this.hitBDone = false;
      this.soundDone = false;
      this.cooldown = 0;
   }

   public void method_6270() {
      this.mob.method_19540(false);
      this.mob.setSwinging(false);
      this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hitADone = false;
      this.hitBDone = false;
      this.soundDone = false;
   }

   public boolean method_38846() {
      return true;
   }

   public void method_6268() {
      class_1309 target = this.mob.method_5968();
      if (target != null && target.method_5805()) {
         this.mob.method_5988().method_6226(target, 30.0F, 30.0F);
         if (!this.mob.isSwinging() && this.cooldown > 0) {
            this.cooldown = Math.max(0, this.cooldown - 1);
         }

         double distSq = this.mob.method_5649(target.method_23317(), target.method_23318(), target.method_23321());
         if (!this.mob.isSwinging()) {
            class_11 path = this.mob.method_5942().method_6349(target, this.attackDistance);
            if (path != null) {
               this.mob.method_5942().method_6334(path, this.speed);
            }
         } else {
            this.mob.method_5942().method_6340();
         }

         if (!this.mob.isSwinging() && this.cooldown == 0 && distSq <= 8.0) {
            this.startNewAttack();
         }

         if (this.mob.isSwinging()) {
            this.turnTowardTargetSmooth(target);
            this.attackTicks++;
            this.handleAttackSoundTimeline();
            switch (this.currentAttack) {
               case NONE:
                  this.endAttack();
                  break;
               case ATTACK1:
                  this.handleAttackSingleHit(target, distSq, 14, 30);
                  break;
               case ATTACK_DOUBLE:
                  this.handleAttackDoubleHit(target, distSq);
                  break;
               case ATTACK_DELAY:
                  this.handleAttackSingleHit(target, distSq, 32, 46);
            }
         }
      } else {
         this.method_6270();
      }
   }

   private void startNewAttack() {
      this.mob.setSwinging(true);
      this.attackTicks = 0;
      this.hitADone = false;
      this.hitBDone = false;
      this.soundDone = false;
      double r = Math.random();
      if (r < 0.33) {
         this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.ATTACK1;
         this.mob.setAttackName("attack1");
      } else if (r < 0.66) {
         this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.ATTACK_DOUBLE;
         this.mob.setAttackName("attack_double");
      } else {
         this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.ATTACK_DELAY;
         this.mob.setAttackName("attack_delay");
      }

      this.mob.triggerAnim("attacking", this.mob.getAttackName());
   }

   private void handleAttackSoundTimeline() {
      if (!this.soundDone) {
         int soundTick = switch (this.currentAttack) {
            case ATTACK1 -> 4;
            case ATTACK_DOUBLE -> 4;
            case ATTACK_DELAY -> 8;
            default -> -1;
         };
         if (soundTick > 0 && this.attackTicks == soundTick) {
            switch (this.currentAttack) {
               case ATTACK1:
                  this.mob
                     .method_37908()
                     .method_43128(
                        null,
                        this.mob.method_23317(),
                        this.mob.method_23318(),
                        this.mob.method_23321(),
                        ModSounds.DWARVEN_SPIDER_ATTACK_2,
                        this.mob.method_5634(),
                        0.85F,
                        0.9F + this.mob.method_59922().method_43057() * 0.2F
                     );
                  break;
               case ATTACK_DOUBLE:
                  this.mob
                     .method_37908()
                     .method_43128(
                        null,
                        this.mob.method_23317(),
                        this.mob.method_23318(),
                        this.mob.method_23321(),
                        ModSounds.DWARVEN_SPIDER_ATTACK_DOUBLE_1,
                        this.mob.method_5634(),
                        0.9F,
                        0.9F + this.mob.method_59922().method_43057() * 0.2F
                     );
                  break;
               case ATTACK_DELAY:
                  this.mob
                     .method_37908()
                     .method_43128(
                        null,
                        this.mob.method_23317(),
                        this.mob.method_23318(),
                        this.mob.method_23321(),
                        ModSounds.DWARVEN_SPIDER_ATTACK_DELAY_1,
                        this.mob.method_5634(),
                        0.85F,
                        0.9F + this.mob.method_59922().method_43057() * 0.2F
                     );
            }

            this.soundDone = true;
         }
      }
   }

   private void handleAttackSingleHit(class_1309 target, double distSq, int hitTick, int durationTicks) {
      if (!this.hitADone && this.attackTicks == hitTick) {
         this.doHitOnce(target, distSq);
         this.hitADone = true;
      }

      if (this.attackTicks >= durationTicks) {
         this.endAttack();
      }
   }

   private void handleAttackDoubleHit(class_1309 target, double distSq) {
      if (!this.hitADone && this.attackTicks == 14) {
         this.doHitOnce(target, distSq);
         this.hitADone = true;
      }

      if (!this.hitBDone && this.attackTicks == 26) {
         this.doHitOnce(target, distSq);
         this.hitBDone = true;
      }

      if (this.attackTicks >= 40) {
         this.endAttack();
      }
   }

   private void doHitOnce(class_1309 target, double distSq) {
      if (distSq <= 9.0) {
         this.mob.method_6121(target);
      }
   }

   private void endAttack() {
      this.mob.setSwinging(false);
      this.currentAttack = DwarvenSpiderGuardianMeleeAttackGoal.AttackVariant.NONE;
      this.attackTicks = 0;
      this.hitADone = false;
      this.hitBDone = false;
      this.soundDone = false;
      this.cooldown = 6;
   }

   private void turnTowardTargetSmooth(class_1309 target) {
      double dx = target.method_23317() - this.mob.method_23317();
      double dz = target.method_23321() - this.mob.method_23321();
      if (!(dx * dx + dz * dz < 1.0E-6)) {
         float desiredYaw = (float)(class_3532.method_15349(dz, dx) * (180.0 / Math.PI)) - 90.0F;
         float newYaw = class_3532.method_15388(this.mob.method_36454(), desiredYaw, 10.0F);
         this.mob.method_36456(newYaw);
         float headNew = class_3532.method_15388(this.mob.method_5791(), desiredYaw, 15.0F);
         this.mob.method_5847(headNew);
      }
   }

   private enum AttackVariant {
      NONE,
      ATTACK1,
      ATTACK_DOUBLE,
      ATTACK_DELAY;
   }
}

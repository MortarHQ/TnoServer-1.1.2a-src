package mebahelcreaturesdwarven.entity.ai.dwarven_spider;

import mebahelcreaturesdwarven.entity.custom.DwemerSpiderGuardianEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_243;

public class DwarvenSpiderGuardianJumpAttackGoal extends class_1352 {
   private final DwemerSpiderGuardianEntity guardian;
   private boolean hasDealtDamage = false;
   private static final int MAX_COOLDOWN = 280;
   private static final double MIN_DISTANCE = 5.0;
   private static final double MAX_DISTANCE = 12.0;
   private static final int ANIM_TICK = 50;
   private static final int JUMP_TICK = 41;
   private static final int DAMAGE_TICK = 29;

   public DwarvenSpiderGuardianJumpAttackGoal(DwemerSpiderGuardianEntity guardian) {
      this.guardian = guardian;
   }

   public boolean method_6264() {
      class_1309 t = this.guardian.method_5968();
      if (t != null && t.method_5805()) {
         return !(t instanceof class_1657 p && (p.method_7337() || p.method_7325())) ? this.guardian.getCooldown() <= 0.0F : false;
      } else {
         return false;
      }
   }

   public boolean method_6266() {
      class_1309 t = this.guardian.method_5968();
      if (t != null && t.method_5805()) {
         return !(t instanceof class_1657 p && (p.method_7337() || p.method_7325())) ? this.guardian.getCooldown() > 0.0F : false;
      } else {
         return false;
      }
   }

   public void method_6269() {
      this.guardian.setCooldown(101.0F);
      this.hasDealtDamage = false;
      this.guardian.setShooting(false);
   }

   public void method_6270() {
      this.hasDealtDamage = false;
      this.guardian.setShooting(false);
      this.guardian.setCooldown(280.0F);
   }

   public void method_6268() {
      class_1309 livingEntity = this.guardian.method_5968();
      if (livingEntity != null && livingEntity.method_5805()) {
         if (this.guardian.isShooting()) {
            this.guardian.method_5942().method_6340();
         }

         this.guardian.setCooldown(Math.max(this.guardian.getCooldown() - 1.0F, 0.0F));
         if (!(this.guardian.getCooldown() > 101.0F)) {
            double dist = this.guardian.method_5739(livingEntity);
            boolean inRange = dist < 10.0 && dist >= 5.0 && dist <= 12.0;
            if (inRange) {
               if (this.guardian.isShooting()
                  && this.guardian.method_5739(livingEntity) <= 2.0F
                  && !this.hasDealtDamage
                  && this.guardian.getCooldown() == 29.0F) {
                  livingEntity.method_5643(this.guardian.method_48923().method_48812(this.guardian), 10.0F);
                  this.guardian.method_5783(ModSounds.SWING, 0.8F, 1.2F);
                  livingEntity.method_6092(new class_1293(class_1294.field_5909, 60, 2, false, false));
                  this.hasDealtDamage = true;
               }

               if (this.guardian.getCooldown() == 41.0F) {
                  class_243 direction = livingEntity.method_19538().method_1020(this.guardian.method_19538()).method_1029();
                  class_243 jumpVelocity;
                  if (dist < 10.0 && dist > 4.0) {
                     jumpVelocity = new class_243(direction.field_1352 * 1.2, 0.6, direction.field_1350 * 1.1);
                  } else {
                     jumpVelocity = new class_243(direction.field_1352 * 0.7, 0.5, direction.field_1350 * 0.55);
                  }

                  this.guardian.method_18799(jumpVelocity);
                  this.guardian.field_6037 = true;
               } else if (this.guardian.getCooldown() == 50.0F) {
                  this.guardian.triggerAnim("attacking", "jump_attack");
                  this.guardian.setShooting(true);
               } else if (this.guardian.getCooldown() == 48.0F) {
                  this.guardian.method_5783(ModSounds.DWARVEN_SPIDER_JUMP_ATTACK, 0.6F, 0.85F);
               } else if (this.guardian.getCooldown() == 0.0F) {
                  this.hasDealtDamage = false;
                  this.guardian.setShooting(false);
                  this.method_6270();
               } else if (this.guardian.getCooldown() <= 100.0F && this.guardian.getCooldown() > 24.0F) {
                  this.hasDealtDamage = false;
                  this.guardian.setShooting(false);
               }
            } else {
               this.hasDealtDamage = false;
               this.guardian.setShooting(false);
               if (this.guardian.getCooldown() <= 101.0F) {
                  this.guardian.setCooldown(101.0F);
               }
            }
         }
      } else {
         this.method_6270();
      }
   }
}

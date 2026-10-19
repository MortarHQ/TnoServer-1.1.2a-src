package mebahelcreaturesdwarven.entity.ai.dwarven_centurion;

import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import mebahelcreaturesdwarven.util.ProjectileUtil;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_243;

public class DwarvenCenturionShootingGoal extends class_1352 {
   private final DwemerCenturionEntity centurion;
   private static final int COOLDOWN_TICKS = 360;
   private static final int SHOOT_START = 60;
   private static final int SHOOT_END = 30;
   private static final int SHOOT_INTERVAL = 2;
   private final ProjectileUtil projectileUtil;
   private class_243 lockedTargetPos;
   private int shotsFired = 0;
   public float damage;

   public DwarvenCenturionShootingGoal(DwemerCenturionEntity centurion, float damage) {
      this.centurion = centurion;
      this.projectileUtil = new ProjectileUtil();
      this.damage = damage;
   }

   public boolean method_6264() {
      class_1309 target = this.centurion.method_5968();
      return target != null && target.method_5805() && !(target instanceof class_1657 player && (player.method_7337() || player.method_7325()));
   }

   public void method_6269() {
      this.centurion.setCooldown(360.0F);
      this.lockedTargetPos = null;
      this.shotsFired = 0;
   }

   public boolean method_6266() {
      class_1309 target = this.centurion.method_5968();
      return target != null && target.method_5805();
   }

   public void method_6270() {
      this.centurion.setShooting(false);
      this.centurion.setCooldown(360.0F);
      this.lockedTargetPos = null;
      this.shotsFired = 0;
   }

   public boolean method_38846() {
      return true;
   }

   public void method_6268() {
      class_1309 target = this.centurion.method_5968();
      if (target != null && target.method_5805() && this.centurion.method_6057(target)) {
         float cooldown = this.centurion.getCooldown();
         this.centurion.setCooldown(Math.max(cooldown - 1.0F, 0.0F));
         if (this.centurion.isShooting()) {
            this.centurion.method_5942().method_6340();
         }

         if (cooldown == 60.0F) {
            this.centurion.setShooting(true);
            this.centurion.triggerAnim("attacking", "shooting");
            class_243 toTarget = target.method_19538()
               .method_1031(0.0, target.method_17682() * 0.75, 0.0)
               .method_1020(this.centurion.method_19538())
               .method_1029();
            class_243 leftOffset = toTarget.method_1036(new class_243(0.0, 1.0, 0.0)).method_1029().method_1021(1.5);
            this.lockedTargetPos = target.method_19538().method_1019(leftOffset).method_1031(0.0, target.method_17682() * 0.7, 0.0);
            this.shotsFired = 0;
            class_243 diff = this.lockedTargetPos.method_1020(this.centurion.method_19538());
            double yaw = Math.toDegrees(Math.atan2(diff.field_1350, diff.field_1352)) - 90.0;
            this.centurion.method_36456((float)yaw);
            this.centurion.field_5982 = (float)yaw;
            this.centurion.field_6241 = (float)yaw;
            this.centurion.method_5636((float)yaw);
            this.centurion
               .method_37908()
               .method_43128(
                  null,
                  this.centurion.method_23317(),
                  this.centurion.method_23318(),
                  this.centurion.method_23321(),
                  ModSounds.DWARVEN_CENTURION_SHOOTING,
                  this.centurion.method_5634(),
                  1.0F,
                  1.0F
               );
         }

         if (this.centurion.isShooting() && this.lockedTargetPos != null) {
            class_243 diff = this.lockedTargetPos.method_1020(this.centurion.method_19538());
            double yaw = Math.toDegrees(Math.atan2(diff.field_1350, diff.field_1352)) - 90.0;
            this.centurion.method_36456((float)yaw);
            this.centurion.field_5982 = (float)yaw;
            this.centurion.field_6241 = (float)yaw;
            this.centurion.method_5636((float)yaw);
         }

         if (cooldown <= 48.0F && cooldown >= 30.0F && cooldown % 2.0F == 0.0F && this.lockedTargetPos != null) {
            class_243 start = this.centurion.method_19538().method_1031(0.0, 4.0, 0.0);
            class_243 dirToTarget = this.lockedTargetPos.method_1031(0.0, target.method_17682() * 0.6, 0.0).method_1020(start).method_1029();
            class_243 perpendicular = dirToTarget.method_1036(new class_243(0.0, 1.0, 0.0)).method_1029().method_1021(0.8);
            float progress = (60.0F - cooldown) / 30.0F;
            float angle = -(progress - 0.5F) * 2.0F;
            class_243 shootDir = dirToTarget.method_1019(perpendicular.method_1021(angle)).method_1029();
            this.projectileUtil.shootSteamProjectile(this.centurion.method_37908(), this.centurion, shootDir, this.damage);
            this.shotsFired++;
         }

         if (cooldown == 0.0F) {
            this.centurion.setShooting(false);
            this.centurion.setCooldown(360.0F);
            this.shotsFired = 0;
            this.lockedTargetPos = null;
         }
      } else {
         this.method_6270();
      }
   }
}

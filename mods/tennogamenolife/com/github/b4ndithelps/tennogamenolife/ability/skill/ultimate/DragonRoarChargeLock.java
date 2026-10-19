package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class DragonRoarChargeLock {
   private static final double MIN_AIM_DISTANCE_SQUARED = 1.0E-8;

   private DragonRoarChargeLock() {
   }

   public static void apply(Player player, Vec3 target) {
      stopHorizontalMovement(player);
      applyAim(player, target);
   }

   public static void stopHorizontalMovement(Player player) {
      Vec3 movement = player.getDeltaMovement();
      player.setDeltaMovement(0.0, movement.y, 0.0);
      player.xxa = 0.0F;
      player.zza = 0.0F;
      player.setSprinting(false);
   }

   public static void applyAim(Player player, Vec3 target) {
      DragonRoarChargeLock.Aim aim = calculateAim(player, target);
      player.setYRot(aim.yaw());
      player.setXRot(aim.pitch());
      player.yRotO = aim.yaw();
      player.xRotO = aim.pitch();
      player.setYHeadRot(aim.yaw());
      player.yHeadRotO = aim.yaw();
      player.setYBodyRot(aim.yaw());
      player.yBodyRotO = aim.yaw();
   }

   public static DragonRoarChargeLock.Aim calculateAim(Player player, Vec3 target) {
      Vec3 offset = target.subtract(player.getEyePosition());
      if (offset.lengthSqr() < 1.0E-8) {
         return new DragonRoarChargeLock.Aim(player.getYRot(), player.getXRot());
      }

      double horizontalDistance = Math.sqrt(offset.x * offset.x + offset.z * offset.z);
      float yaw = (float)(Math.toDegrees(Math.atan2(offset.z, offset.x)) - 90.0);
      float pitch = (float)(-Math.toDegrees(Math.atan2(offset.y, horizontalDistance)));
      return new DragonRoarChargeLock.Aim(yaw, pitch);
   }

   public record Aim(float yaw, float pitch) {
   }
}

package dev.foxboss.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MilimMeleeContact {
   private static final Vec3 FIST = new Vec3(-0.42462028, 1.29127591, -0.58234913);
   private static final Vec3 HEAVY_FIST = new Vec3(0.11358353, 1.324866, -1.0730912);
   private static final Vec3 BOOT = new Vec3(0.62699451, 0.69726691, 0.1943587);

   private MilimMeleeContact() {
   }

   private static Vec3 localCenter(int action) {
      return action == 9 ? BOOT : (action == 16 ? HEAVY_FIST : FIST);
   }

   private static double radius(int action) {
      return action == 9 ? 0.22 : 0.14;
   }

   public static float yawOffset(int action) {
      Vec3 point = localCenter(action);
      return (float)Math.toDegrees(Math.atan2(-point.x, -point.z));
   }

   public static double reach(int action) {
      return localCenter(action).horizontalDistance() + radius(action);
   }

   public static Vec3 center(MilimEntity milim, int action) {
      return center(milim, action, milim.yBodyRot);
   }

   private static Vec3 center(MilimEntity milim, int action, float bodyYaw) {
      Vec3 point = localCenter(action).scale(milim.getScale());
      double angle = Math.toRadians(180.0F - bodyYaw);
      return milim.position().add(Math.cos(angle) * point.x + Math.sin(angle) * point.z, point.y, -Math.sin(angle) * point.x + Math.cos(angle) * point.z);
   }

   public static boolean hits(MilimEntity milim, LivingEntity target, int action) {
      return target != null && target.isAlive() && touches(center(milim, action), target.getBoundingBox(), radius(action) * milim.getScale());
   }

   public static boolean canReach(MilimEntity milim, LivingEntity target, int action) {
      if (target != null && target.isAlive()) {
         float targetYaw = (float)(Mth.atan2(target.getZ() - milim.getZ(), target.getX() - milim.getX()) * 180.0 / Math.PI) - 90.0F;
         return touches(center(milim, action, targetYaw + yawOffset(action)), target.getBoundingBox(), radius(action) * milim.getScale());
      } else {
         return false;
      }
   }

   private static boolean touches(Vec3 center, AABB box, double radius) {
      double dx = Math.max(box.minX - center.x, Math.max(0.0, center.x - box.maxX));
      double dy = Math.max(box.minY - center.y, Math.max(0.0, center.y - box.maxY));
      double dz = Math.max(box.minZ - center.z, Math.max(0.0, center.z - box.maxZ));
      return dx * dx + dy * dy + dz * dz <= radius * radius;
   }
}

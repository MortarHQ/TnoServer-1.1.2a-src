package com.foxablazeultimate.client;

import java.util.List;
import net.minecraft.world.phys.Vec3;

public final class AzathothWeaponGeometry {
   private AzathothWeaponGeometry() {
   }

   public static AzathothWeaponGeometry.Axis swordBlade(List<Vec3> opaquePixels) {
      return swordBlade(opaquePixels, 0.46875, 0.53125);
   }

   public static AzathothWeaponGeometry.Axis swordBlade(List<Vec3> opaquePixels, double minDepth, double maxDepth) {
      double middleDepth = (minDepth + maxDepth) / 2.0;
      if (opaquePixels.isEmpty()) {
         return new AzathothWeaponGeometry.Axis(new Vec3(0.4, 0.4, middleDepth), new Vec3(0.96, 0.96, middleDepth), 0.16);
      }

      Vec3 center = Vec3.ZERO;

      for (Vec3 point : opaquePixels) {
         center = center.add(point);
      }

      center = center.scale(1.0 / opaquePixels.size());
      Vec3 direction = new Vec3(0.731, 1.0, 0.219).normalize();

      for (int iteration = 0; iteration < 24; iteration++) {
         Vec3 next = Vec3.ZERO;

         for (Vec3 point : opaquePixels) {
            Vec3 offset = point.subtract(center);
            next = next.add(offset.scale(offset.dot(direction)));
         }

         if (next.lengthSqr() < 1.0E-12) {
            break;
         }

         direction = next.normalize();
      }

      if (direction.y < 0.0) {
         direction = direction.scale(-1.0);
      }

      double min = Double.POSITIVE_INFINITY;
      double max = Double.NEGATIVE_INFINITY;

      for (Vec3 point : opaquePixels) {
         double projection = point.subtract(center).dot(direction);
         min = Math.min(min, projection);
         max = Math.max(max, projection);
      }

      Vec3 hilt = center.add(direction.scale(min));
      Vec3 tip = center.add(direction.scale(max));
      hilt = new Vec3(hilt.x, hilt.y, middleDepth);
      tip = new Vec3(tip.x, tip.y, middleDepth);
      Vec3 start = hilt.lerp(tip, 0.38);
      double radius = Math.max(0.07, (maxDepth - minDepth) / 2.0);

      for (Vec3 pixel : opaquePixels) {
         Vec3 offset = pixel.subtract(start);
         double along = offset.dot(direction);
         if (!(along < 0.0)) {
            radius = Math.max(radius, offset.subtract(direction.scale(along)).length());
         }
      }

      return new AzathothWeaponGeometry.Axis(start, tip, radius + 0.04);
   }

   public record Axis(Vec3 start, Vec3 end, double radius) {
   }
}

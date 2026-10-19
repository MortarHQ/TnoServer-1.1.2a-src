package com.tensura_tno.client.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MoreSkillsAlbisVinaVfxOptimizer {
   private static final double NEAR_DISTANCE_SQUARED = 1024.0;
   private static final double MID_DISTANCE_SQUARED = 6400.0;

   private MoreSkillsAlbisVinaVfxOptimizer() {
   }

   public static boolean isImpactVisible(Vec3 origin, float radius) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && origin != null) {
         Vec3 camera = minecraft.gameRenderer.getMainCamera().getPosition();
         double renderDistance = Math.max(160.0, ((Integer)minecraft.options.renderDistance().get()).intValue() * 16.0 + 48.0);
         double extent = Math.max(3.0, Math.abs(radius) + 3.0);
         return distanceSquaredToAabb(camera, new AABB(origin, origin).inflate(extent, 5.0, extent)) <= renderDistance * renderDistance;
      } else {
         return true;
      }
   }

   public static boolean isBeamVisible(Vec3 start, Vec3 end, float radius) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && start != null && end != null) {
         double padding = Math.max(2.0, Math.abs(radius) * 2.0);
         AABB bounds = new AABB(
               Math.min(start.x, end.x),
               Math.min(start.y, end.y),
               Math.min(start.z, end.z),
               Math.max(start.x, end.x),
               Math.max(start.y, end.y),
               Math.max(start.z, end.z)
            )
            .inflate(padding);
         Vec3 camera = minecraft.gameRenderer.getMainCamera().getPosition();
         double renderDistance = Math.max(160.0, ((Integer)minecraft.options.renderDistance().get()).intValue() * 16.0 + 48.0);
         return distanceSquaredToAabb(camera, bounds) <= renderDistance * renderDistance;
      } else {
         return true;
      }
   }

   public static int count(int original, Vec3 origin) {
      return Math.min(original, Math.max(1, (int)Math.ceil(original * factor(origin))));
   }

   public static int rings(int original, Vec3 origin) {
      return Math.max(5, count(original, origin));
   }

   public static int segments(int original, Vec3 origin) {
      return Math.max(48, count(original, origin));
   }

   private static double factor(Vec3 origin) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && origin != null) {
         double distanceSquared = minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr(origin);
         int tier = distanceSquared <= 1024.0 ? 0 : (distanceSquared <= 6400.0 ? 1 : 2);
         ParticleStatus particles = (ParticleStatus)minecraft.options.particles().get();
         if (particles == ParticleStatus.DECREASED) {
            tier++;
         } else if (particles == ParticleStatus.MINIMAL) {
            tier += 2;
         }
         return switch (Math.min(2, tier)) {
            case 0 -> 0.5;
            case 1 -> 0.25;
            default -> 0.1;
         };
      } else {
         return 1.0;
      }
   }

   private static double distanceSquaredToAabb(Vec3 point, AABB bounds) {
      double dx = Math.max(Math.max(bounds.minX - point.x, 0.0), point.x - bounds.maxX);
      double dy = Math.max(Math.max(bounds.minY - point.y, 0.0), point.y - bounds.maxY);
      double dz = Math.max(Math.max(bounds.minZ - point.z, 0.0), point.z - bounds.maxZ);
      return dx * dx + dy * dy + dz * dz;
   }
}

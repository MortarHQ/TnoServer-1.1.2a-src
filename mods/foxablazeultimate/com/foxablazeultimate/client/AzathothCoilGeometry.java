package com.foxablazeultimate.client;

import net.minecraft.world.phys.Vec3;

public final class AzathothCoilGeometry {
   public static final int SEGMENTS = 40;
   public static final int SIDES = 4;

   private AzathothCoilGeometry() {
   }

   public static AzathothCoilGeometry.Section section(Vec3 start, Vec3 end, double radius, double t, double phase) {
      Vec3 axis = end.subtract(start).normalize();
      Vec3 side = axis.cross(Math.abs(axis.z) < 0.9 ? new Vec3(0.0, 0.0, 1.0) : new Vec3(0.0, 1.0, 0.0)).normalize();
      Vec3 normal = axis.cross(side).normalize();
      double angle = t * Math.PI * 4.0 + phase;
      Vec3 radial = side.scale(Math.cos(angle)).add(normal.scale(Math.sin(angle)));
      Vec3 center = start.lerp(end, t).add(radial.scale(radius));
      Vec3 tangent = end.subtract(start)
         .add(side.scale(-Math.sin(angle) * radius * Math.PI * 4.0))
         .add(normal.scale(Math.cos(angle) * radius * Math.PI * 4.0))
         .normalize();
      return new AzathothCoilGeometry.Section(center, radial, tangent.cross(radial).normalize());
   }

   public record Section(Vec3 center, Vec3 radial, Vec3 binormal) {
      public Vec3 vertex(int side, double thickness) {
         double angle = side * Math.PI * 2.0 / 4.0;
         return this.center.add(this.radial.scale(Math.cos(angle) * thickness)).add(this.binormal.scale(Math.sin(angle) * thickness));
      }
   }
}

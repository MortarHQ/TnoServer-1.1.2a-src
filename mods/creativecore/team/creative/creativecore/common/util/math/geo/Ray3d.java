package team.creative.creativecore.common.util.math.geo;

import org.joml.Vector3d;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class Ray3d {
   public final Vec3d origin;
   public final Vec3d direction;

   public Ray3d(Vec3d start, Vec3d end) {
      this.origin = start;
      this.direction = new Vec3d(end);
      this.direction.sub(this.origin);
      this.direction.normalize();
   }

   public Ray3d(Vec3d start, Vec3d end, boolean normalize) {
      this.origin = start;
      this.direction = new Vec3d(end);
      this.direction.sub(this.origin);
      if (normalize) {
         this.direction.normalize();
      }
   }

   public Ray3d(Vec3d origin, Facing facing) {
      this.origin = origin;
      this.direction = new Vec3d();
      this.direction.set(facing.axis, facing.offset());
   }

   public void set(double x, double y, double z, double x2, double y2, double z2) {
      this.origin.set(x, y, z);
      this.direction.set(x2, y2, z2);
      this.direction.sub(this.origin);
   }

   public double getT(Axis axis, double value) {
      return (value - this.origin.get(axis)) / this.direction.get(axis);
   }

   public Vector3d get(float t) {
      return new Vector3d(this.origin.x + this.direction.x * t, this.origin.y + this.direction.y * t, this.origin.z + this.direction.z * t);
   }
}

package team.creative.creativecore.common.util.math.box;

import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class BoxPlane {
   public final BoxFace face;
   public final Vec3d normal;
   public final Vec3d origin;

   public BoxPlane(Vec3d[] corners, BoxFace face) {
      this.face = face;
      this.origin = face.first(corners);
      this.normal = face.normal(corners);
   }

   public double getIntersectingScale(Vec3d rayOrigin, Vec3d ray) {
      Double result = linePlaneIntersection(ray, rayOrigin, this.normal, this.origin);
      return result != null && !(result < 0.0) ? result : Double.MAX_VALUE;
   }

   public static Double linePlaneIntersection(Vec3d ray, Vec3d rayOrigin, Vec3d normal, Vec3d origin) {
      double d = normal.dot(origin);
      return normal.dot(ray) == 0.0 ? null : (d - normal.dot(rayOrigin)) / normal.dot(ray);
   }

   public static BoxPlane createPlane(Axis axis, Vec3d direction, Vec3d[] corners) {
      double value = direction.get(axis);
      return value == 0.0 ? null : new BoxPlane(corners, BoxFace.get(axis, value > 0.0));
   }

   public static BoxPlane createOppositePlane(Axis axis, Vec3d direction, Vec3d[] corners) {
      double value = direction.get(axis);
      return value == 0.0 ? null : new BoxPlane(corners, BoxFace.get(axis, value < 0.0));
   }
}

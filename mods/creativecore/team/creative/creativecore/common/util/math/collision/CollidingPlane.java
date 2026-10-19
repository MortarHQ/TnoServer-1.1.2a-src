package team.creative.creativecore.common.util.math.collision;

import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.box.BoxCorner;
import team.creative.creativecore.common.util.math.matrix.Matrix4;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class CollidingPlane {
   public static final int accuracySteps = 10;
   public final AABB bb;
   public final Facing facing;
   public final PlaneCache cache;
   protected final Vec3d origin;
   protected final Vec3d normal;

   public CollidingPlane(AABB bb, Facing facing, PlaneCache cache, Vec3d[] corners, BoxCorner[] planeCorners) {
      this.bb = bb;
      this.facing = facing;
      this.cache = cache;
      this.origin = corners[planeCorners[0].ordinal()];
      Vec3d first = corners[planeCorners[1].ordinal()];
      Vec3d second = corners[planeCorners[2].ordinal()];
      this.normal = new Vec3d(
         (first.y - this.origin.y) * (second.z - this.origin.z) - (first.z - this.origin.z) * (second.y - this.origin.y),
         (first.z - this.origin.z) * (second.x - this.origin.x) - (first.x - this.origin.x) * (second.z - this.origin.z),
         (first.x - this.origin.x) * (second.y - this.origin.y) - (first.y - this.origin.y) * (second.x - this.origin.x)
      );
   }

   public Boolean isInFront(Vec3d vec) {
      double scalar = (vec.x - this.origin.x) * this.normal.x + (vec.y - this.origin.y) * this.normal.y + (vec.z - this.origin.z) * this.normal.z;
      if (scalar > 0.0) {
         return true;
      } else {
         return scalar < 0.0 ? false : null;
      }
   }

   public Double binarySearch(@Nullable Double value, AABB toCheck, double checkRadiusSquared, Vec3d center, CollisionCoordinator coordinator) {
      if (coordinator.isSimple) {
         Double t = this.searchBetweenSimple(value, center, new Vec3d(center), new Vec3d(), 0.0, 1.0, coordinator, 0);
         return t != null && this.intersects(toCheck, checkRadiusSquared, center, t, coordinator) ? t : null;
      }

      if (coordinator.hasOneRotation && !coordinator.hasTranslation) {
         int halfRotations = coordinator.getNumberOfHalfRotations();
         double halfRotationSize = 1.0 / halfRotations;
         Vec3d temp = new Vec3d();
         Vec3d start = new Vec3d(center);
         Double t = this.searchBetweenSimple(value, center, start, temp, 0.0, halfRotationSize, coordinator, 0);
         if (t != null && this.intersects(toCheck, checkRadiusSquared, center, t, coordinator)) {
            return t;
         }

         start.set(center);
         coordinator.transformInverted(start, halfRotationSize);
         t = this.searchBetweenSimple(value, center, new Vec3d(center), temp, halfRotationSize, halfRotationSize * 2.0, coordinator, 0);
         return t != null && this.intersects(toCheck, checkRadiusSquared, center, t, coordinator) ? t : null;
      } else {
         Vec3d start = new Vec3d(center);
         Vec3d temp = new Vec3d();
         int halfRotations = coordinator.getNumberOfHalfRotations();
         double halfRotationSize = 1.0 / halfRotations;

         for (int i = 0; i < halfRotations; i++) {
            double startT = halfRotationSize * i;
            double endT = halfRotationSize * (i + 1);
            if (startT != 0.0) {
               start.set(center);
               coordinator.transformInverted(start, startT);
            }

            if (value != null && value <= startT) {
               return null;
            }

            Double t = this.searchBetweenSimple(value, center, start, temp, startT, endT, coordinator, 0);
            if (t != null && this.intersects(toCheck, checkRadiusSquared, center, t, coordinator)) {
               return t;
            }
         }

         return null;
      }
   }

   protected Double searchBetweenSimple(
      @Nullable Double value, Vec3d center, Vec3d start, Vec3d temp, double startT, double endT, CollisionCoordinator coordinator, int steps
   ) {
      if (value != null && value < startT) {
         return null;
      }

      Boolean beforeFront = this.isInFront(start);
      if (beforeFront == null) {
         return startT;
      }

      temp.set(center);
      coordinator.transformInverted(temp, endT);
      Boolean afterFront = this.isInFront(temp);
      if (afterFront == null) {
         return value != null ? Math.min(value, endT) : endT;
      }

      if (beforeFront != afterFront) {
         if (steps < 10) {
            steps++;
            double halfT = (startT + endT) * 0.5;
            temp.set(center);
            coordinator.transformInverted(temp, halfT);
            Boolean halfFront = this.isInFront(temp);
            if (halfFront == null) {
               return value != null ? Math.min(value, halfT) : halfT;
            } else {
               return beforeFront != halfFront
                  ? this.searchBetweenSimple(value, center, start, temp, startT, halfT, coordinator, steps)
                  : this.searchBetweenSimple(value, center, temp, start, halfT, endT, coordinator, steps);
            }
         } else {
            return startT;
         }
      } else {
         return null;
      }
   }

   public boolean intersects(AABB toCheck, double checkRadiusSquared, Vec3d center, double t, CollisionCoordinator coordinator) {
      Vec3d cachedCenter = new Vec3d(this.cache.center);
      coordinator.original().transformPointToWorld(cachedCenter);
      coordinator.transform(cachedCenter, t);
      cachedCenter.sub(center);
      if (cachedCenter.lengthSquared() >= checkRadiusSquared + this.cache.radiusSquared) {
         return false;
      }

      Matrix4 matrix = coordinator.get(t);
      double minX = Double.POSITIVE_INFINITY;
      double minY = Double.POSITIVE_INFINITY;
      double minZ = Double.POSITIVE_INFINITY;
      double maxX = Double.NEGATIVE_INFINITY;
      double maxY = Double.NEGATIVE_INFINITY;
      double maxZ = Double.NEGATIVE_INFINITY;
      Vec3d corner = new Vec3d();

      for (int i = 0; i < BoxCorner.values().length; i++) {
         BoxCorner.values()[i].set(this.bb, corner);
         coordinator.original().transformPointToWorld(corner);
         coordinator.transform(matrix, corner);
         if (toCheck.contains(corner.x, corner.y, corner.z)) {
            return true;
         }

         minX = Math.min(minX, corner.x);
         minY = Math.min(minY, corner.y);
         minZ = Math.min(minZ, corner.z);
         maxX = Math.max(maxX, corner.x);
         maxY = Math.max(maxY, corner.y);
         maxZ = Math.max(maxZ, corner.z);
      }

      return toCheck.intersects(minX, minY, minZ, maxX, maxY, maxZ);
   }

   public static Facing getDirection(CollisionCoordinator coordinator, PlaneCache cache, Vec3d center) {
      double x = (center.x - cache.center.x) / (cache.bb.maxX - cache.bb.minX);
      double y = (center.y - cache.center.y) / (cache.bb.maxY - cache.bb.minY);
      double z = (center.z - cache.center.z) / (cache.bb.maxZ - cache.bb.minZ);
      boolean xy = Math.abs(x) > Math.abs(y);
      boolean xz = Math.abs(x) > Math.abs(z);
      boolean yz = Math.abs(y) > Math.abs(z);
      if (xy && xz) {
         return x > 0.0 ? Facing.EAST : Facing.WEST;
      } else if (!xz && !yz) {
         return z > 0.0 ? Facing.SOUTH : Facing.NORTH;
      } else {
         return y > 0.0 ? Facing.UP : Facing.DOWN;
      }
   }
}

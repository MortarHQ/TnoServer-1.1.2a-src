package team.creative.creativecore.common.util.math.vec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3f;
import team.creative.creativecore.common.util.math.base.Axis;

public class Vec3d extends VecNd<Vec3d> {
   public double x;
   public double y;
   public double z;

   public Vec3d() {
   }

   public Vec3d(Vec3i vec) {
      this(vec.getX(), vec.getY(), vec.getZ());
   }

   public Vec3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3d(Vec3d vec) {
      super(vec);
   }

   public Vec3d(Vec3f vec) {
      this(vec.x, vec.y, vec.z);
   }

   public Vec3d(Vector3d vec) {
      this(vec.x, vec.y, vec.z);
   }

   public Vec3d(Vec3 vec) {
      this(vec.x, vec.y, vec.z);
   }

   public Vec3d(Vector3f step) {
      this(step.x(), step.y(), step.z());
   }

   public Vec3 toVanilla() {
      return new Vec3(this.x, this.y, this.z);
   }

   public BlockPos toBlockPos() {
      return BlockPos.containing(this.x, this.y, this.z);
   }

   public void set(Vec3d vec) {
      this.x = vec.x;
      this.y = vec.y;
      this.z = vec.z;
   }

   public void set(Vec3 vec) {
      this.x = vec.x;
      this.y = vec.y;
      this.z = vec.z;
   }

   @Override
   public double get(int dim) {
      return switch (dim) {
         case 0 -> this.x;
         case 1 -> this.y;
         case 2 -> this.z;
         default -> 0.0;
      };
   }

   @Override
   public double get(Axis axis) {
      return switch (axis) {
         case X -> this.x;
         case Y -> this.y;
         case Z -> this.z;
         default -> 0.0;
      };
   }

   public void set(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @Override
   public void set(Axis axis, double value) {
      switch (axis) {
         case X:
            this.x = value;
            break;
         case Y:
            this.y = value;
            break;
         case Z:
            this.z = value;
      }
   }

   @Override
   public void set(int dim, double value) {
      switch (dim) {
         case 0:
            this.x = value;
            break;
         case 1:
            this.y = value;
            break;
         case 2:
            this.z = value;
      }
   }

   @Override
   public int dimensions() {
      return 3;
   }

   public Vec3d copy() {
      return new Vec3d(this.x, this.y, this.z);
   }

   public void add(Vec3d vec) {
      this.x = this.x + vec.x;
      this.y = this.y + vec.y;
      this.z = this.z + vec.z;
   }

   public void sub(Vec3d vec) {
      this.x = this.x - vec.x;
      this.y = this.y - vec.y;
      this.z = this.z - vec.z;
   }

   @Override
   public void scale(double scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof Vec3d) ? false : ((Vec3d)obj).x == this.x && ((Vec3d)obj).y == this.y && ((Vec3d)obj).z == this.z;
   }

   public boolean epsilonEquals(Vec3d var1, double var2) {
      double var3 = this.x - var1.x;
      if (Double.isNaN(var3)) {
         return false;
      }

      if ((var3 < 0.0 ? -var3 : var3) > var2) {
         return false;
      }

      var3 = this.y - var1.y;
      if (Double.isNaN(var3)) {
         return false;
      }

      if ((var3 < 0.0 ? -var3 : var3) > var2) {
         return false;
      }

      var3 = this.z - var1.z;
      return Double.isNaN(var3) ? false : (var3 < 0.0 ? -var3 : var3) <= var2;
   }

   public double distance(Vec3 vec) {
      return this.distance(vec.x, vec.y, vec.z);
   }

   public double distance(Vec3d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      double z = this.z - vec.z;
      return Math.sqrt(x * x + y * y + z * z);
   }

   public double distanceSqr(Vec3d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      double z = this.z - vec.z;
      return x * x + y * y + z * z;
   }

   public double distance(double x, double y, double z) {
      double posX = this.x - x;
      double posY = this.y - y;
      double posZ = this.z - z;
      return Math.sqrt(posX * posX + posY * posY + posZ * posZ);
   }

   public double distanceSqr(double x, double y, double z) {
      double posX = this.x - x;
      double posY = this.y - y;
      double posZ = this.z - z;
      return posX * posX + posY * posY + posZ * posZ;
   }

   @Override
   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   @Override
   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public double angle(Vec3d vec) {
      double vDot = this.dot(vec) / (this.length() * vec.length());
      if (vDot < -1.0) {
         vDot = -1.0;
      }

      if (vDot > 1.0) {
         vDot = 1.0;
      }

      return Math.acos(vDot);
   }

   public void cross(Vec3d vec1, Vec3d vec2) {
      this.x = vec1.y * vec2.z - vec1.z * vec2.y;
      this.y = vec2.x * vec1.z - vec2.z * vec1.x;
      this.z = vec1.x * vec2.y - vec1.y * vec2.x;
   }

   public double dot(Vec3d vec) {
      return this.x * vec.x + this.y * vec.y + this.z * vec.z;
   }
}

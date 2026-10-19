package team.creative.creativecore.common.util.math.vec;

import team.creative.creativecore.common.util.math.base.Axis;

public class Vec4d extends VecNd<Vec4d> {
   public double x;
   public double y;
   public double z;
   public double w;

   public Vec4d() {
   }

   public Vec4d(double x, double y, double z, double w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public Vec4d(Vec3d vec, double w) {
      this(vec.x, vec.y, vec.z, w);
   }

   public Vec4d(Vec4d vec) {
      super(vec);
   }

   public void set(Vec4d vec) {
      this.x = vec.x;
      this.y = vec.y;
      this.z = vec.z;
      this.w = vec.w;
   }

   @Override
   public double get(int dim) {
      return switch (dim) {
         case 0 -> this.x;
         case 1 -> this.y;
         case 2 -> this.z;
         case 3 -> this.w;
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

   public void set(double x, double y, double z, double w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
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
            break;
         case 3:
            this.w = value;
      }
   }

   @Override
   public int dimensions() {
      return 4;
   }

   public Vec4d copy() {
      return new Vec4d(this.x, this.y, this.z, this.w);
   }

   public void add(Vec4d vec) {
      this.x = this.x + vec.x;
      this.y = this.y + vec.y;
      this.z = this.z + vec.z;
      this.w = this.w + vec.w;
   }

   public void sub(Vec4d vec) {
      this.x = this.x - vec.x;
      this.y = this.y - vec.y;
      this.z = this.z - vec.z;
      this.w = this.w - vec.w;
   }

   @Override
   public void scale(double scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
      this.w *= scale;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof Vec3d) ? false : ((Vec3d)obj).x == this.x && ((Vec3d)obj).y == this.y && ((Vec3d)obj).z == this.z;
   }

   public boolean epsilonEquals(Vec4d var1, double var2) {
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
      if (Double.isNaN(var3)) {
         return false;
      }

      if ((var3 < 0.0 ? -var3 : var3) > var2) {
         return false;
      }

      var3 = this.w - var1.w;
      return Double.isNaN(var3) ? false : (var3 < 0.0 ? -var3 : var3) <= var2;
   }

   public double distance(Vec4d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      double z = this.z - vec.z;
      double w = this.w - vec.w;
      return Math.sqrt(x * x + y * y + z * z + w * w);
   }

   public double distanceSqr(Vec4d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      double z = this.z - vec.z;
      double w = this.w - vec.w;
      return x * x + y * y + z * z + w * w;
   }

   @Override
   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
   }

   @Override
   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
   }

   public double angle(Vec4d vec) {
      double var2 = this.dot(vec) / (this.length() * vec.length());
      if (var2 < -1.0) {
         var2 = -1.0;
      }

      if (var2 > 1.0) {
         var2 = 1.0;
      }

      return Math.acos(var2);
   }

   public double dot(Vec4d vec) {
      return this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w * vec.w;
   }
}

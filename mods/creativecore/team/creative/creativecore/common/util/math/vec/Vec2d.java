package team.creative.creativecore.common.util.math.vec;

public class Vec2d extends VecNd<Vec2d> {
   public double x;
   public double y;

   public Vec2d() {
   }

   public Vec2d(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public Vec2d(Vec2d vec) {
      super(vec);
   }

   public void set(Vec2d vec) {
      this.x = vec.x;
      this.y = vec.y;
   }

   @Override
   public double get(int dim) {
      if (dim == 0) {
         return this.x;
      } else {
         return dim == 1 ? this.y : 0.0;
      }
   }

   public void set(double x, double y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void set(int dim, double value) {
      if (dim == 0) {
         this.x = value;
      } else if (dim == 1) {
         this.y = value;
      }
   }

   @Override
   public int dimensions() {
      return 2;
   }

   public Vec2d copy() {
      return new Vec2d(this.x, this.y);
   }

   public void add(double x, double y) {
      this.x += x;
      this.y += y;
   }

   public void add(Vec2d vec) {
      this.x = this.x + vec.x;
      this.y = this.y + vec.y;
   }

   public void sub(double x, double y) {
      this.x -= x;
      this.y -= y;
   }

   public void sub(Vec2d vec) {
      this.x = this.x - vec.x;
      this.y = this.y - vec.y;
   }

   @Override
   public void scale(double scale) {
      this.x *= scale;
      this.y *= scale;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof Vec2d) ? false : ((Vec2d)obj).x == this.x && ((Vec2d)obj).y == this.y;
   }

   public boolean epsilonEquals(Vec2d var1, double var2) {
      double var3 = this.x - var1.x;
      if (Double.isNaN(var3)) {
         return false;
      }

      if ((var3 < 0.0 ? -var3 : var3) > var2) {
         return false;
      }

      var3 = this.y - var1.y;
      return Double.isNaN(var3) ? false : (var3 < 0.0 ? -var3 : var3) <= var2;
   }

   public double distance(Vec2d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      return Math.sqrt(x * x + y * y);
   }

   public double distanceSqr(Vec2d vec) {
      double x = this.x - vec.x;
      double y = this.y - vec.y;
      return x * x + y * y;
   }

   @Override
   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y);
   }

   @Override
   public double lengthSquared() {
      return this.x * this.x + this.y * this.y;
   }

   public double angle(Vec2d vec) {
      double vDot = this.dot(vec) / (this.length() * vec.length());
      if (vDot < -1.0) {
         vDot = -1.0;
      }

      if (vDot > 1.0) {
         vDot = 1.0;
      }

      return Math.acos(vDot);
   }

   public double dot(Vec2d vec) {
      return this.x * vec.x + this.y * vec.y;
   }
}

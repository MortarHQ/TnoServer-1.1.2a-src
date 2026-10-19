package team.creative.creativecore.common.util.math.vec;

public class Vec1d extends VecNd<Vec1d> {
   public double x;

   public Vec1d() {
   }

   public Vec1d(double x) {
      this.x = x;
   }

   public Vec1d(Vec1d vec) {
      super(vec);
   }

   public void set(Vec1d vec) {
      this.x = vec.x;
   }

   @Override
   public double get(int dim) {
      return dim == 0 ? this.x : 0.0;
   }

   @Override
   public void set(int dim, double value) {
      if (dim == 0) {
         this.x = value;
      }
   }

   @Override
   public int dimensions() {
      return 1;
   }

   public Vec1d copy() {
      return new Vec1d(this.x);
   }

   public void add(Vec1d vec) {
      this.x = this.x + vec.x;
   }

   public void sub(Vec1d vec) {
      this.x = this.x - vec.x;
   }

   @Override
   public void scale(double scale) {
      this.x *= scale;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Vec1d ? ((Vec1d)obj).x == this.x : false;
   }

   public boolean epsilonEquals(Vec1d var1, double var2) {
      double var3 = this.x - var1.x;
      return Double.isNaN(var3) ? false : (var3 < 0.0 ? -var3 : var3) <= var2;
   }

   public double distance(Vec1d vec) {
      return Math.abs(this.x - vec.x);
   }

   public double distanceSqr(Vec1d vec) {
      double x = this.x - vec.x;
      return x * x;
   }

   @Override
   public double length() {
      return Math.abs(this.x);
   }

   @Override
   public double lengthSquared() {
      return this.x * this.x;
   }

   public double angle(Vec1d vec) {
      return 0.0;
   }

   public double dot(Vec1d vec) {
      return 0.0;
   }
}

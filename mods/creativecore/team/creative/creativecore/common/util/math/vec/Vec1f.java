package team.creative.creativecore.common.util.math.vec;

public class Vec1f extends VecNf<Vec1f> {
   public float x;

   public Vec1f() {
   }

   public Vec1f(float x) {
      this.x = x;
   }

   public Vec1f(Vec1f vec) {
      super(vec);
   }

   public void set(Vec1f vec) {
      this.x = vec.x;
   }

   @Override
   public float get(int dim) {
      return dim == 0 ? this.x : 0.0F;
   }

   @Override
   public void set(int dim, float value) {
      if (dim == 0) {
         this.x = value;
      }
   }

   @Override
   public int dimensions() {
      return 1;
   }

   public Vec1f copy() {
      return new Vec1f(this.x);
   }

   public void add(Vec1f vec) {
      this.x = this.x + vec.x;
   }

   public void sub(Vec1f vec) {
      this.x = this.x - vec.x;
   }

   @Override
   public void scale(double scale) {
      this.x *= (float)scale;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Vec1f ? ((Vec1f)obj).x == this.x : false;
   }

   public boolean epsilonEquals(Vec1f var1, float var2) {
      float var3 = this.x - var1.x;
      return Float.isNaN(var3) ? false : (var3 < 0.0F ? -var3 : var3) <= var2;
   }

   public double distance(Vec1f vec) {
      return Math.abs(this.x - vec.x);
   }

   public double distanceSqr(Vec1f vec) {
      float x = this.x - vec.x;
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

   public double angle(Vec1f vec) {
      return 0.0;
   }

   public float dot(Vec1f vec) {
      return this.x * vec.x;
   }
}

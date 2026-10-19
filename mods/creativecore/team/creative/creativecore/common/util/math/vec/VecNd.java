package team.creative.creativecore.common.util.math.vec;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.util.math.base.Axis;

public abstract class VecNd<T extends VecNd> {
   public VecNd() {
   }

   public VecNd(T vec) {
      this.set(vec);
   }

   public abstract void set(T var1);

   public double get(Axis axis) {
      return this.get(axis.ordinal());
   }

   public void set(Axis axis, double value) {
      this.set(axis.ordinal(), value);
   }

   public abstract double get(int var1);

   public abstract void set(int var1, double var2);

   public abstract int dimensions();

   public abstract T copy();

   public abstract void add(T var1);

   public void add(T origin, T vec) {
      this.set(origin);
      this.add(vec);
   }

   public abstract void sub(T var1);

   public void sub(T origin, T vec) {
      this.set(origin);
      this.sub(vec);
   }

   public abstract void scale(double var1);

   public void invert() {
      this.scale(-1.0);
   }

   @Override
   public abstract boolean equals(Object var1);

   public abstract boolean epsilonEquals(T var1, double var2);

   public boolean epsilonEquals(T vec) {
      return this.epsilonEquals(vec, 1.0E-4F);
   }

   public abstract double distance(T var1);

   public abstract double distanceSqr(T var1);

   public abstract double length();

   public abstract double lengthSquared();

   public void normalize() {
      this.scale(1.0 / this.length());
   }

   public abstract double angle(T var1);

   public abstract double dot(T var1);

   public long[] toLong() {
      long[] array = new long[this.dimensions()];

      for (int i = 0; i < array.length; i++) {
         array[i] = Double.doubleToRawLongBits(this.get(i));
      }

      return array;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("(");

      for (int i = 0; i < this.dimensions(); i++) {
         if (i > 0) {
            builder.append(",");
         }

         builder.append(this.get(i));
      }

      builder.append(")");
      return builder.toString();
   }

   public static VecNd load(long[] array) {
      if (array.length == 1) {
         return new Vec1d(Double.longBitsToDouble(array[0]));
      } else if (array.length == 2) {
         return new Vec2d(Double.longBitsToDouble(array[0]), Double.longBitsToDouble(array[1]));
      } else if (array.length == 3) {
         return new Vec3d(Double.longBitsToDouble(array[0]), Double.longBitsToDouble(array[1]), Double.longBitsToDouble(array[2]));
      } else {
         throw new IllegalArgumentException("Invalid long array for vector: " + Arrays.toString(array));
      }
   }

   public static <T extends VecNd> T createEmptyVec(Class<T> className) {
      try {
         return className.getConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
         CreativeCore.LOGGER.error(e);
         throw new IllegalArgumentException();
      }
   }
}

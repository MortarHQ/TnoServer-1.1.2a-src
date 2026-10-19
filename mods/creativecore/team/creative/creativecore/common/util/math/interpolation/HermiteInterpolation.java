package team.creative.creativecore.common.util.math.interpolation;

import java.util.List;
import team.creative.creativecore.common.util.math.vec.VecNd;

public class HermiteInterpolation<T extends VecNd> extends CubicInterpolation<T> {
   public HermiteInterpolation.Tension tension;
   public double bias;

   public HermiteInterpolation(double[] times, T[] points, double bias, HermiteInterpolation.Tension tension) {
      super(times, points);
      this.bias = bias;
      this.tension = tension;
   }

   public HermiteInterpolation(double[] times, T[] points, HermiteInterpolation.Tension tension) {
      this(times, points, 0.0, tension);
   }

   public HermiteInterpolation(double[] times, T[] points) {
      this(times, points, HermiteInterpolation.Tension.Normal);
   }

   public HermiteInterpolation(double bias, HermiteInterpolation.Tension tension, T... points) {
      super(points);
      this.bias = bias;
      this.tension = tension;
   }

   public HermiteInterpolation(double bias, HermiteInterpolation.Tension tension, List<T> points) {
      super(points);
      this.bias = bias;
      this.tension = tension;
   }

   public HermiteInterpolation(double bias, HermiteInterpolation.Tension tension, T before, List<T> points, T after) {
      super(before, points, after);
      this.bias = bias;
      this.tension = tension;
   }

   public HermiteInterpolation(double[] times, double bias, HermiteInterpolation.Tension tension, T before, List<T> points, T after) {
      super(times, before, points, after);
      this.bias = bias;
      this.tension = tension;
   }

   public HermiteInterpolation(HermiteInterpolation.Tension tension, T... points) {
      this(0.0, tension, points);
   }

   public HermiteInterpolation(HermiteInterpolation.Tension tension, List<T> points) {
      this(0.0, tension, points);
   }

   public HermiteInterpolation(T... points) {
      this(HermiteInterpolation.Tension.Normal, points);
   }

   public HermiteInterpolation(List<T> points) {
      this(HermiteInterpolation.Tension.Normal, points);
   }

   public HermiteInterpolation(T before, List<T> points, T after) {
      this(0.0, HermiteInterpolation.Tension.Normal, before, points, after);
   }

   public HermiteInterpolation(double[] times, T before, List<T> points, T after) {
      this(times, 0.0, HermiteInterpolation.Tension.Normal, before, points, after);
   }

   @Override
   public double valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
      double v0 = this.getValue(pointIndex - 1, dim);
      double v1 = this.getValue(pointIndex, dim);
      double v2 = this.getValue(pointIndexNext, dim);
      double v3 = this.getValue(pointIndexNext + 1, dim);
      double mu2 = mu * mu;
      double mu3 = mu2 * mu;
      double m0 = (v1 - v0) * (1.0 + this.bias) * (1 - this.tension.value) / 2.0;
      m0 += (v2 - v1) * (1.0 - this.bias) * (1 - this.tension.value) / 2.0;
      double m1 = (v2 - v1) * (1.0 + this.bias) * (1 - this.tension.value) / 2.0;
      m1 += (v3 - v2) * (1.0 - this.bias) * (1 - this.tension.value) / 2.0;
      double a0 = 2.0 * mu3 - 3.0 * mu2 + 1.0;
      double a1 = mu3 - 2.0 * mu2 + mu;
      double a2 = mu3 - mu2;
      double a3 = -2.0 * mu3 + 3.0 * mu2;
      return a0 * v1 + a1 * m0 + a2 * m1 + a3 * v2;
   }

   public enum Tension {
      High(1),
      Normal(0),
      Low(-1);

      public final int value;

      Tension(int value) {
         this.value = value;
      }
   }
}

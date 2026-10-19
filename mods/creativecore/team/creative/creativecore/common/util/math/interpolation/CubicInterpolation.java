package team.creative.creativecore.common.util.math.interpolation;

import java.util.List;
import team.creative.creativecore.common.util.math.vec.VecNd;

public class CubicInterpolation<T extends VecNd> extends Interpolation<T> {
   public T beginVec;
   public T endVec;

   public CubicInterpolation(double[] times, T[] points) {
      super(times, points);
      this.beginVec = (T)points[0].copy();
      this.beginVec.sub(points[1]);
      this.beginVec.add(points[0]);
      this.endVec = (T)points[points.length - 1].copy();
      this.endVec.sub(points[points.length - 2]);
      this.endVec.add(points[points.length - 1]);
   }

   public CubicInterpolation(List<T> points) {
      this(null, points, null);
   }

   public CubicInterpolation(T before, List<T> points, T after) {
      super(points);
      if (before != null) {
         this.beginVec = before;
      } else {
         this.beginVec = (T)points.get(0).copy();
         this.beginVec.sub(points.get(1));
         this.beginVec.add(points.get(0));
      }

      if (after != null) {
         this.endVec = after;
      } else {
         this.endVec = (T)points.get(points.size() - 1).copy();
         this.endVec.sub(points.get(points.size() - 2));
         this.endVec.add(points.get(points.size() - 1));
      }
   }

   public CubicInterpolation(double[] times, T before, List<T> points, T after) {
      super(times, points);
      if (before != null) {
         this.beginVec = before;
      } else {
         this.beginVec = (T)points.get(0).copy();
         this.beginVec.sub(points.get(1));
         this.beginVec.add(points.get(0));
      }

      if (after != null) {
         this.endVec = after;
      } else {
         this.endVec = (T)points.get(points.size() - 1).copy();
         this.endVec.sub(points.get(points.size() - 2));
         this.endVec.add(points.get(points.size() - 1));
      }
   }

   public CubicInterpolation(T... points) {
      super(points);
      this.beginVec = (T)points[0].copy();
      this.beginVec.sub(points[1]);
      this.beginVec.add(points[0]);
      this.endVec = (T)points[points.length - 1].copy();
      this.endVec.sub(points[points.length - 2]);
      this.endVec.add(points[points.length - 1]);
   }

   @Override
   protected double getValue(int index, int dim) {
      if (index < 0) {
         return this.beginVec.get(dim);
      } else {
         return index >= this.points.size() ? this.endVec.get(dim) : ((VecNd)this.points.get(index).value).get(dim);
      }
   }

   @Override
   public double[] estimateDistance() {
      double[] data = new double[this.points.size()];
      double startTime = (Double)this.points.getFirst().key;
      double endTime = (Double)this.points.getLast().key;
      double pointDuration = (endTime - startTime) / (this.points.size() - 1);
      int steps = 3;
      double stepDuration = 1.0F / (steps + 1.0F);

      for (int i = 1; i < this.points.size(); i++) {
         double distance = 0.0;
         T last = (T)this.points.get(i - 1).value;

         for (int j = 1; j <= steps; j++) {
            T vec = this.valueAt((i - 1 + steps * stepDuration) * pointDuration);
            distance += vec.distance(last);
            last = vec;
         }

         distance += ((VecNd)this.points.get(i).value).distance(last);
         data[0] += distance;
         data[i] = distance;
      }

      return data;
   }

   @Override
   public double valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
      double v0 = this.getValue(pointIndex - 1, dim);
      double v1 = this.getValue(pointIndex, dim);
      double v2 = this.getValue(pointIndexNext, dim);
      double v3 = this.getValue(pointIndexNext + 1, dim);
      double mu2 = mu * mu;
      double a0 = v3 - v2 - v0 + v1;
      double a1 = v0 - v1 - a0;
      double a2 = v2 - v0;
      double a3 = v1;
      return a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3;
   }
}

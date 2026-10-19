package team.creative.creativecore.common.util.math.interpolation;

import java.util.List;
import team.creative.creativecore.common.util.math.vec.VecNd;

public class CosineInterpolation<T extends VecNd> extends Interpolation<T> {
   public CosineInterpolation(T... points) {
      super(points);
   }

   public CosineInterpolation(double[] times, T... points) {
      super(times, points);
   }

   public CosineInterpolation(List<T> points) {
      super(points);
   }

   public CosineInterpolation(double[] times, List<T> points) {
      super(times, points);
   }

   @Override
   public double[] estimateDistance() {
      double[] data = new double[this.points.size()];

      for (int i = 1; i < this.points.size(); i++) {
         double distance = ((VecNd)this.points.get(i).value).distance((T)this.points.get(i - 1).value);
         data[0] += distance;
         data[i] = distance;
      }

      return data;
   }

   @Override
   public double valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
      double mu2 = (1.0 - Math.cos(mu * Math.PI)) / 2.0;
      return this.getValue(pointIndex, dim) * (1.0 - mu2) + this.getValue(pointIndexNext, dim) * mu2;
   }
}

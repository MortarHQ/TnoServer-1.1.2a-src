package team.creative.creativecore.common.util.math.interpolation;

import java.util.List;
import team.creative.creativecore.common.util.math.vec.VecNd;
import team.creative.creativecore.common.util.type.list.TupleList;

public class LinearInterpolation<T extends VecNd> extends Interpolation<T> {
   public LinearInterpolation(double[] times, T[] points) {
      super(times, points);
   }

   public LinearInterpolation(T... points) {
      super(points);
   }

   public LinearInterpolation(double[] times, List<T> points) {
      super(times, points);
   }

   public LinearInterpolation(List<T> points) {
      super(points);
   }

   public LinearInterpolation(TupleList<Double, T> points) {
      super(points);
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
      return (this.getValue(pointIndexNext, dim) - this.getValue(pointIndex, dim)) * mu + this.getValue(pointIndex, dim);
   }
}

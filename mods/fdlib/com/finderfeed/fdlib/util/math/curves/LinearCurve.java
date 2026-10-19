package com.finderfeed.fdlib.util.math.curves;

import java.util.Collection;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class LinearCurve extends MultipointCurve {
   public LinearCurve(Collection<Vector3f> points) {
      super(points);
   }

   @Override
   public Vector3f getLocalCurvePoint(int pointIndex, float percent) {
      Vector3f point = this.points.get(pointIndex);
      Vector3f next = this.points.get(pointIndex + 1);
      return new Vector3f(Mth.lerp(percent, point.x, next.x), Mth.lerp(percent, point.y, next.y), Mth.lerp(percent, point.z, next.z));
   }
}

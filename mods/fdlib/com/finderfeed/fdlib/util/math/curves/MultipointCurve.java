package com.finderfeed.fdlib.util.math.curves;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Vector3f;

public abstract class MultipointCurve implements Curve {
   protected List<Vector3f> points;

   public MultipointCurve(Collection<Vector3f> points) {
      if (points.size() < 2) {
         throw new RuntimeException("Curve should consist of two or more points!");
      }

      this.points = new ArrayList<>();

      for (Vector3f p : points) {
         this.points.add(new Vector3f(p));
      }
   }

   @Override
   public void addPoint(int index, Vector3f point) {
      this.points.add(index, point);
   }

   @Override
   public Vector3f getPoint(int index) {
      if (index < 0) {
         return null;
      } else {
         return index >= this.points.size() ? null : this.points.get(index);
      }
   }

   @Override
   public Vector3f getCurvePoint(float percent) {
      Pair<Integer, Float> pair = this.globalPercentToPointAndLocalPercent(percent);
      return this.getLocalCurvePoint((Integer)pair.getFirst(), (Float)pair.getSecond());
   }

   @Override
   public int size() {
      return this.points.size();
   }
}

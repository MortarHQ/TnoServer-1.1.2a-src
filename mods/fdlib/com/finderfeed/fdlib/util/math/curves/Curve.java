package com.finderfeed.fdlib.util.math.curves;

import com.mojang.datafixers.util.Pair;
import org.joml.Vector3f;

public interface Curve {
   Vector3f getCurvePoint(float var1);

   Vector3f getLocalCurvePoint(int var1, float var2);

   int size();

   void addPoint(int var1, Vector3f var2);

   Vector3f getPoint(int var1);

   default Pair<Integer, Float> globalPercentToPointAndLocalPercent(float globalPercent) {
      int size = this.size();
      float val = globalPercent * size;
      if (val >= size) {
         return new Pair(size - 2, 1.0F);
      }

      int index = (int)val;
      return new Pair(index, val - index);
   }
}

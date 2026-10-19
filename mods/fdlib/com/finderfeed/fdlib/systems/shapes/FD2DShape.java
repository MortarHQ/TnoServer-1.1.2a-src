package com.finderfeed.fdlib.systems.shapes;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;

public class FD2DShape {
   private List<Vector3f> points = new ArrayList<>();

   public FD2DShape(List<Vector3f> points) {
      this.points = points;
   }

   public List<Vector3f> getPoints() {
      return this.points;
   }

   public static FD2DShape createSimpleCircleNVertexShape(float radius, int vertexAmount) {
      if (vertexAmount <= 1) {
         throw new RuntimeException("Cannot create shapes out of less than 1 vertex. Like, it won't be a shape at all...");
      }

      Vector3f base = new Vector3f(radius, 0.0F, 0.0F);
      List<Vector3f> points = new ArrayList<>();
      float angle = (float) (Math.PI * 2) / vertexAmount;
      points.add(base);

      for (int i = 1; i < vertexAmount; i++) {
         Vector3f b = base.rotateY(angle * i, new Vector3f());
         points.add(b);
      }

      return new FD2DShape(points);
   }
}

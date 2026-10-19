package com.finderfeed.fdlib.util.math.curves;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Vector3f;

public class CatmullRomCurve extends MultipointCurve {
   private List<Pair<Vector3f, Vector3f>> neighbors = new ArrayList<>();

   public CatmullRomCurve(Collection<Vector3f> points) {
      super(points);

      for (int i = 0; i < this.points.size(); i++) {
         this.neighbors.add(this.generateNeighbors(this.getPoint(i - 1), this.getPoint(i), this.getPoint(i + 1)));
      }
   }

   @Override
   public void addPoint(int index, Vector3f point) {
      if (index == 0) {
         Pair<Vector3f, Vector3f> nn = this.generateNeighbors(point, this.points.get(0), this.points.get(1));
         Pair<Vector3f, Vector3f> nc = this.generateNeighbors(null, point, this.points.get(0));
         this.neighbors.set(0, nn);
         this.neighbors.add(0, nc);
      } else if (index == this.points.size()) {
         Pair<Vector3f, Vector3f> nn = this.generateNeighbors(this.points.get(this.points.size() - 2), this.points.get(this.points.size() - 1), point);
         Pair<Vector3f, Vector3f> nc = this.generateNeighbors(this.points.get(this.points.size() - 1), point, null);
         this.neighbors.set(this.neighbors.size() - 1, nn);
         this.neighbors.add(nc);
      } else {
         Pair<Vector3f, Vector3f> np = this.generateNeighbors(this.getPoint(index - 2), this.getPoint(index - 1), point);
         Pair<Vector3f, Vector3f> nc = this.generateNeighbors(this.getPoint(index - 1), point, this.getPoint(index));
         Pair<Vector3f, Vector3f> nn = this.generateNeighbors(point, this.getPoint(index), this.getPoint(index + 1));
         this.neighbors.set(index - 1, np);
         this.neighbors.set(index, nn);
         this.neighbors.add(index, nc);
      }

      this.points.add(point);
   }

   @Override
   public Vector3f getLocalCurvePoint(int pointIndex, float percent) {
      Pair<Vector3f, Vector3f> ns1 = this.neighbors.get(pointIndex);
      Pair<Vector3f, Vector3f> ns2 = this.neighbors.get(pointIndex + 1);
      Vector3f n1 = this.points.get(pointIndex);
      Vector3f n2 = (Vector3f)ns1.getSecond();
      Vector3f n3 = (Vector3f)ns2.getFirst();
      Vector3f n4 = this.points.get(pointIndex + 1);
      return new Vector3f(
         this.bernstein(n1.x, n2.x, n3.x, n4.x, percent), this.bernstein(n1.y, n2.y, n3.y, n4.y, percent), this.bernstein(n1.z, n2.z, n3.z, n4.z, percent)
      );
   }

   private float bernstein(float x1, float x2, float x3, float x4, float t) {
      float t3 = (float)Math.pow(t, 3.0);
      float t2 = (float)Math.pow(t, 2.0);
      return x1 * (-t3 + 3.0F * t2 - 3.0F * t + 1.0F) + x2 * (3.0F * t3 - 6.0F * t2 + 3.0F * t) + x3 * (-3.0F * t3 + 3.0F * t2) + x4 * t3;
   }

   private Pair<Vector3f, Vector3f> generateNeighbors(Vector3f previous, Vector3f current, Vector3f next) {
      if (previous == null) {
         Vector3f b = next.sub(current, new Vector3f()).mul(0.6666667F);
         Vector3f n2 = current.add(b, new Vector3f());
         Vector3f n1 = current.add(b.mul(-1.0F, new Vector3f()), new Vector3f());
         return new Pair(n1, n2);
      } else if (next == null) {
         Vector3f b = current.sub(previous, new Vector3f()).mul(0.6666667F);
         Vector3f n2 = current.add(b, new Vector3f());
         Vector3f n1 = current.add(b.mul(-1.0F, new Vector3f()), new Vector3f());
         return new Pair(n1, n2);
      } else {
         Vector3f b = next.sub(previous, new Vector3f()).mul(0.33333334F);
         Vector3f n2 = current.add(b, new Vector3f());
         Vector3f n1 = current.add(b.mul(-1.0F, new Vector3f()), new Vector3f());
         return new Pair(n1, n2);
      }
   }
}

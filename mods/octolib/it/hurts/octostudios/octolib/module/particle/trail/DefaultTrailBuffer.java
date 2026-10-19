package it.hurts.octostudios.octolib.module.particle.trail;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DefaultTrailBuffer<T> implements TrailBuffer {
   private Deque<Vec3> points = new ArrayDeque<>();
   private final int maxSize;

   public DefaultTrailBuffer(int maxSize) {
      this.maxSize = maxSize;
   }

   @Override
   public void write(Vec3 vec3) {
      if (this.size() >= this.maxSize) {
         this.remove();
      }

      this.points.push(vec3);
   }

   @Override
   public int size() {
      return this.points.size();
   }

   @Override
   public void remove() {
      this.points.removeLast();
   }

   @NotNull
   @Override
   public Iterator<Vec3> iterator() {
      return this.points.iterator();
   }

   public Deque<Vec3> getPoints() {
      return this.points;
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public void setPoints(Deque<Vec3> points) {
      this.points = points;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DefaultTrailBuffer<?> other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getMaxSize() != other.getMaxSize()) {
            return false;
         }

         Object this$points = this.getPoints();
         Object other$points = other.getPoints();
         return this$points == null ? other$points == null : this$points.equals(other$points);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof DefaultTrailBuffer;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMaxSize();
      Object $points = this.getPoints();
      return result * 59 + ($points == null ? 43 : $points.hashCode());
   }

   @Override
   public String toString() {
      return "DefaultTrailBuffer(points=" + this.getPoints() + ", maxSize=" + this.getMaxSize() + ")";
   }
}

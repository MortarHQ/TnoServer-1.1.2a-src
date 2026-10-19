package net.pixeldreamstudios.mythika.entity;

import org.jetbrains.annotations.Nullable;

public final class Footfalls {
   private final int blendTicks;
   @Nullable
   private Footfalls.Gait gait;
   private double tick;
   private double cycleStart;
   private int next;

   public Footfalls(int blendTicks) {
      this.blendTicks = blendTicks;
   }

   public boolean tick(@Nullable Footfalls.Gait gait, double rate) {
      if (gait != this.gait) {
         this.gait = gait;
         this.tick = -this.blendTicks;
         this.cycleStart = 0.0;
         this.next = 0;
      }

      if (gait == null) {
         return false;
      }

      this.tick += rate;

      boolean landed;
      for (landed = false; this.tick >= this.cycleStart + gait.plants()[this.next] * gait.cycleTicks(); landed = true) {
         if (++this.next == gait.plants().length) {
            this.next = 0;
            this.cycleStart = this.cycleStart + gait.cycleTicks();
         }
      }

      return landed;
   }

   public record Gait(double cycleTicks, double... plants) {
   }
}

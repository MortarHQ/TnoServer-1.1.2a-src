package com.cerbon.bosses_of_mass_destruction.animation;

import java.util.function.Supplier;

public class PauseAnimationTimer implements IAnimationTimer {
   private final Supplier<Double> sysTimeProvider;
   private final Supplier<Boolean> isPaused;
   private double pauseTime = 0.0;
   private double pauseStart = 0.0;

   public PauseAnimationTimer(Supplier<Double> sysTimeProvider, Supplier<Boolean> isPaused) {
      this.sysTimeProvider = sysTimeProvider;
      this.isPaused = isPaused;
   }

   @Override
   public double getCurrentTick() {
      double sysTime = this.sysTimeProvider.get();
      if (this.isPaused.get()) {
         if (this.pauseStart == 0.0) {
            this.pauseStart = sysTime;
         }

         return this.pauseStart - this.pauseTime;
      } else {
         if (this.pauseStart != 0.0) {
            double timeElapsed = sysTime - this.pauseStart;
            this.pauseTime += timeElapsed;
            this.pauseStart = 0.0;
         }

         return sysTime - this.pauseTime;
      }
   }
}

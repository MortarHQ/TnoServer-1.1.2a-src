package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.animation.IAnimationTimer;

public class FrameLimiter {
   private final double minimumFrameDelta;
   private final IAnimationTimer timer;
   private double previousTime = 0.0;

   public FrameLimiter(float framesPerUnit, IAnimationTimer timer) {
      this.minimumFrameDelta = 1.0F / framesPerUnit;
      this.timer = timer;
   }

   public boolean canDoFrame() {
      double currentTick = this.timer.getCurrentTick();
      double frameDelta = currentTick - this.previousTime;
      if (frameDelta >= this.minimumFrameDelta) {
         this.previousTime = currentTick;
         return true;
      } else {
         return false;
      }
   }
}

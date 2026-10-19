package com.finderfeed.fdlib.systems.screen.screen_effect;

import net.minecraft.util.Mth;

public class ScreenEffectInstance {
   public ScreenEffect effect;
   public int currentTime;

   public ScreenEffectInstance(ScreenEffect base) {
      this.effect = base;
   }

   public void tick() {
      this.currentTime = Mth.clamp(this.currentTime + 1, 0, this.effect.getLifetime());
   }

   public boolean isFinished() {
      return this.currentTime >= this.effect.getOutTime() + this.effect.getInTime() + this.effect.getStayTime();
   }

   public int getCurrentTime() {
      return this.currentTime;
   }
}

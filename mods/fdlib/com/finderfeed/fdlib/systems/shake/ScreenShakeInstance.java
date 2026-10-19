package com.finderfeed.fdlib.systems.shake;

public class ScreenShakeInstance {
   public int currentTime = 0;
   public ScreenShake shake;

   public ScreenShakeInstance(ScreenShake shake) {
      this.shake = shake;
   }

   public void tick() {
      this.currentTime++;
   }

   public boolean hasEnded() {
      return this.shake.hasEnded(this.currentTime);
   }
}

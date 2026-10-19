package com.finderfeed.fdlib.systems.shake;

public abstract class FDScreenShake implements ScreenShake {
   private FDShakeData data;

   public FDScreenShake(FDShakeData data) {
      this.data = data;
   }

   @Override
   public boolean hasEnded(int elapsedTime) {
      return elapsedTime > this.data.duration();
   }

   public FDShakeData getData() {
      return this.data;
   }
}

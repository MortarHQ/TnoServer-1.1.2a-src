package com.ilexiconn.llibrary.server.world;

import com.bobmowzie.mowziesmobs.MMCommon;

public enum TickRateHandler {
   INSTANCE;

   public static final float DEFAULT_TPS = 20.0F;
   private float tps = 20.0F;

   public float getTPS() {
      return this.tps;
   }

   public void setTPS(float tps) {
      if (this.tps != tps) {
         MMCommon.PROXY.setTPS(tps);
      }

      this.tps = tps;
   }

   public void resetTPS() {
      this.setTPS(20.0F);
   }

   public long getTickRate() {
      return (long)(this.tps / 20.0F * 50.0F);
   }
}

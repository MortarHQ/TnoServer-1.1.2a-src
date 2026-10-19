package me.decce.gnetum;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;

public class PassManager {
   public static final long NANOS_IN_A_SECOND = 1000000000L;
   private final int SAVED_DURATIONS = 30;
   private String[] PASS_TEXT;
   private long[][] durations;
   private long currentPassDuration;
   private int index;
   private long passBeginNanos;
   public int current = 1;

   public String getPassText() {
      if (this.PASS_TEXT == null || this.PASS_TEXT.length != Gnetum.config.numberOfPasses + 1) {
         this.PASS_TEXT = new String[Gnetum.config.numberOfPasses + 1];
         this.PASS_TEXT[0] = "sleep";

         for (int i = 1; i <= Gnetum.config.numberOfPasses; i++) {
            this.PASS_TEXT[i] = "pass" + i;
         }
      }

      return this.PASS_TEXT[this.current];
   }

   public void begin() {
      if (this.current > Gnetum.config.numberOfPasses) {
         this.current = 1;
      }

      if (this.current > 0) {
         this.passBeginNanos = Util.getNanos();
      }

      Minecraft.getInstance().getProfiler().push(this.getPassText());
   }

   public void end() {
      Minecraft.getInstance().getProfiler().pop();
      if (this.current > 0) {
         this.currentPassDuration = this.currentPassDuration + (Util.getNanos() - this.passBeginNanos);
      }
   }

   public void nextPass() {
      long nanos = Util.getNanos();
      if (this.current == 0) {
         if (Gnetum.config.getMaxFps() == 125 || nanos <= Gnetum.lastSwapNanos || nanos - Gnetum.lastSwapNanos >= 1000000000L / Gnetum.config.getMaxFps()) {
            this.current = 1;
            HudDeltaTracker.reset();
            FramebufferManager.getInstance().swapFramebuffers();
            Gnetum.lastSwapNanos = nanos;
         }
      } else {
         if (this.durations == null || this.durations.length != Gnetum.config.numberOfPasses + 1) {
            this.durations = new long[Gnetum.config.numberOfPasses + 1][30];
         }

         this.durations[this.current][this.index] = this.currentPassDuration;
         this.currentPassDuration = 0L;
         if (this.current == Gnetum.config.numberOfPasses) {
            this.index++;
         }

         if (this.index == 30) {
            this.index = 0;
         }

         if (this.current++ == Gnetum.config.numberOfPasses) {
            if (Gnetum.config.getMaxFps() != 125 && nanos > Gnetum.lastSwapNanos && nanos - Gnetum.lastSwapNanos < 1000000000L / Gnetum.config.getMaxFps()) {
               this.current = 0;
            } else {
               this.current = 1;
               HudDeltaTracker.reset();
               FramebufferManager.getInstance().swapFramebuffers();
               Gnetum.lastSwapNanos = nanos;
            }
         }
      }
   }

   public long[] getDurations() {
      if (this.durations == null) {
         return null;
      }

      long[] ret = new long[this.durations.length];

      for (int i = 1; i < this.durations.length; i++) {
         long avg = 0L;
         int t = 1;

         for (int j = 0; j < 30; j++) {
            if (this.durations[i][j] > 0L) {
               avg += (this.durations[i][j] - avg) / t++;
            }
         }

         ret[i] = avg;
      }

      return ret;
   }

   public boolean shouldRender(String vanillaOverlay) {
      if (Gnetum.renderingCanceled) {
         return false;
      }

      if (Gnetum.uncachedElements.has(vanillaOverlay)) {
         return false;
      }

      CacheSetting cacheSetting = Gnetum.getCacheSetting(vanillaOverlay);
      return cacheSetting.enabled.get() && this.current == cacheSetting.pass;
   }

   public boolean cachingDisabled(String vanillaOverlay) {
      return Gnetum.uncachedElements.has(vanillaOverlay) ? true : !Gnetum.getCacheSetting(vanillaOverlay).enabled.get();
   }

   public boolean shouldRender(String moddedOverlay, ElementType type) {
      if (Gnetum.uncachedElements.has(moddedOverlay, type)) {
         return false;
      }

      CacheSetting cacheSetting = Gnetum.getCacheSetting(moddedOverlay, type);
      return cacheSetting.enabled.get() && this.current == cacheSetting.pass;
   }

   public boolean cachingDisabled(String moddedOverlay, ElementType type) {
      return Gnetum.uncachedElements.has(moddedOverlay, type) ? true : !Gnetum.getCacheSetting(moddedOverlay, type).enabled.get();
   }
}

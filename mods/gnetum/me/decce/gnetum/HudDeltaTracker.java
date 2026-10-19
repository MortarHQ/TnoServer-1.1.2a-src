package me.decce.gnetum;

import java.util.Arrays;
import net.minecraft.client.DeltaTracker.Timer;

public class HudDeltaTracker {
   private static float lastRealtimeDeltaTicks = Float.NaN;
   private static float[] realtimeDeltaTicks;
   private static boolean logOnce;

   public static void update(Timer timer) {
      int len = Gnetum.config.numberOfPasses + 1;
      int curr = Gnetum.passManager.current;
      if (realtimeDeltaTicks == null || realtimeDeltaTicks.length != len) {
         reset();
      }

      realtimeDeltaTicks[curr] = realtimeDeltaTicks[curr] + timer.getRealtimeDeltaTicks();
   }

   public static void reset() {
      int len = Gnetum.config.numberOfPasses + 1;
      if (realtimeDeltaTicks != null && realtimeDeltaTicks.length == len) {
         store();
      } else {
         realtimeDeltaTicks = new float[len];
      }

      Arrays.fill(realtimeDeltaTicks, 0.0F);
   }

   private static void store() {
      lastRealtimeDeltaTicks = 0.0F;

      for (int i = 0; i <= Gnetum.config.numberOfPasses; i++) {
         if (i < realtimeDeltaTicks.length) {
            lastRealtimeDeltaTicks = lastRealtimeDeltaTicks + realtimeDeltaTicks[i];
         }
      }
   }

   public static void disable() {
      if (!logOnce) {
         logOnce = true;
         Gnetum.LOGGER.warn("Incompatible mod detected, disabling HUD delta tracker.");
         Gnetum.LOGGER.warn("Consider reporting to Gnetum developer.");
      }

      lastRealtimeDeltaTicks = Float.NaN;
   }

   public static boolean isReady() {
      return !Float.isNaN(lastRealtimeDeltaTicks);
   }

   public static float getRealtimeDeltaTicks() {
      return lastRealtimeDeltaTicks;
   }
}

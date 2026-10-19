package com.chronodawn.client.shield;

import com.chronodawn.network.S2CShieldCooldownPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public final class ClientShieldCooldowns {
   private static long speedStartTick = 0L;
   private static long speedEndTick = 0L;
   private static long echoStartTick = 0L;
   private static long echoEndTick = 0L;

   private ClientShieldCooldowns() {
   }

   public static void updateSpeed(long nowTick, int durationTicks) {
      speedStartTick = nowTick;
      speedEndTick = nowTick + durationTicks;
   }

   public static void updateEcho(long nowTick, int durationTicks) {
      echoStartTick = nowTick;
      echoEndTick = nowTick + durationTicks;
   }

   public static void applyFromPayload(S2CShieldCooldownPayload payload) {
      ClientLevel level = Minecraft.getInstance().level;
      long now = level == null ? 0L : level.getGameTime();
      if (payload.kind() == 0) {
         updateSpeed(now, payload.durationTicks());
      } else if (payload.kind() == 1) {
         updateEcho(now, payload.durationTicks());
      }
   }

   public static float getSpeedProgress(long nowTick, float partialTick) {
      return compute(speedStartTick, speedEndTick, nowTick, partialTick);
   }

   public static float getEchoProgress(long nowTick, float partialTick) {
      return compute(echoStartTick, echoEndTick, nowTick, partialTick);
   }

   private static float compute(long startTick, long endTick, long nowTick, float partialTick) {
      long total = endTick - startTick;
      if (total <= 0L) {
         return 0.0F;
      }

      float elapsed = (float)(nowTick - startTick) + partialTick;
      float remaining = (float)total - elapsed;
      if (remaining <= 0.0F) {
         return 0.0F;
      }

      float ratio = remaining / (float)total;
      return Math.min(1.0F, Math.max(0.0F, ratio));
   }
}

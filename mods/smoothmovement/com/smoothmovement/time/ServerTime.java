package com.smoothmovement.time;

import java.util.Optional;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;

public class ServerTime {
   public static final String SCOREBOARD_TPS = "smoothmovementtps";
   public static float slownessFactor = 1.0F;
   public static float averageSlownessFactor = 1.0F;
   public static int extraTicks = 0;
   private static double extraTickTotal = 0.0;
   public static int artificialLagTPS = -1;
   public static int artificialLagBaseMS = 50;
   private static int packetCounter = 0;

   public static boolean hasLag() {
      return slownessFactor > 1.0F;
   }

   public static void onTick(MinecraftServer server, long[] tickTimes, int tickCount) {
      double lastTickMs = tickTimes[tickCount % 100] * 1.0E-6;
      if (lastTickMs > 50.0 && tickCount > 200) {
         slownessFactor = (float)Mth.clamp(lastTickMs / 50.0, 1.0, 10.0);
         averageSlownessFactor = averageSlownessFactor * 0.8F + slownessFactor * 0.2F;
         extraTickTotal = extraTickTotal + (slownessFactor - 1.0F);
         extraTicks = (int)extraTickTotal;
         extraTickTotal = extraTickTotal - extraTicks;
      } else {
         slownessFactor = 1.0F;
         averageSlownessFactor = 1.0F;
         extraTickTotal = 0.0;
         extraTicks = 0;
      }

      if (packetCounter++ == 20) {
         server.getPlayerList()
            .broadcastAll(
               new ClientboundSetScorePacket(
                  "smoothmovement", "smoothmovementtps", Math.round(20.0F / averageSlownessFactor), Optional.empty(), Optional.empty()
               )
            );
         packetCounter = 0;
      }
   }
}

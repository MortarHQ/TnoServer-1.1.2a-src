package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.Minecraft;

public final class PainSixPathsVfxClient {
   private static boolean initialized;

   private PainSixPathsVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         PainPlanetaryDevastationClient.init();
      }
   }

   public static void spawn(double x, double y, double z, float radius, int kind, float power, long seed) {
      init();
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 313600.0)) {
            if (kind == 4) {
               PainPlanetaryDevastationClient.planet(seed, x, y, z, radius, power);
            } else if (kind == 5) {
               PainPlanetaryDevastationClient.stop(seed);
            } else if (kind == 6) {
               PainPlanetaryDevastationClient.push(seed, x, y, z, radius, power);
            } else {
               if (kind == 7) {
                  PainPlanetaryDevastationClient.pull(seed, x, y, z, radius, power);
               }
            }
         }
      }
   }
}

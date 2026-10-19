package com.corosus.coroutil.util;

import net.minecraft.world.level.Level;

public class CoroUtilWorldTime {
   public static int getDayLength() {
      return 24000;
   }

   public static boolean isNight(Level world) {
      long timeMod = world.getGameTime() % getDayLength();
      return timeMod >= getNightFirstTick() && timeMod <= getDayFirstTick();
   }

   public static boolean isNightPadded(Level world) {
      return isNightPadded(world, 5);
   }

   public static boolean isNightPadded(Level world, int padding) {
      long timeMod = world.getGameTime() % getDayLength();
      return timeMod >= getNightFirstTick() + padding && timeMod <= getDayFirstTick() - padding;
   }

   public static int getNightFirstTick() {
      return 12542;
   }

   public static int getDayFirstTick() {
      return 23460;
   }
}

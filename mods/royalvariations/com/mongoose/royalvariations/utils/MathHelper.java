package com.mongoose.royalvariations.utils;

public class MathHelper {
   public static int secondsToTicks(int pSeconds) {
      return pSeconds * 20;
   }

   public static int secondsToTicks(float pSeconds) {
      return (int)(pSeconds * 20.0F);
   }

   public static int minutesToTicks(int pMinutes) {
      return secondsToTicks(pMinutes * 60);
   }

   public static float minutesToTicks(float pMinutes) {
      return secondsToTicks(pMinutes * 60.0F);
   }

   public static int minecraftDayToTicks(int pDay) {
      return pDay * 24000;
   }

   public static float minecraftDayToTicks(float pDay) {
      return pDay * 24000.0F;
   }

   public static float modelDegrees(float degree) {
      return (float)(degree * Math.PI / 180.0);
   }
}

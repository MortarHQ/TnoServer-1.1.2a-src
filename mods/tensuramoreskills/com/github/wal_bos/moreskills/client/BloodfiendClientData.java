package com.github.wal_bos.moreskills.client;

public final class BloodfiendClientData {
   private static boolean bloodfiend;
   private static int blood;
   private static int maxBlood;
   private static int generation;
   private static int totalBloodDrained;
   private static int totalThrallsTurned;

   private BloodfiendClientData() {
   }

   public static void update(boolean isBloodfiend, int value, int max, int gen, int drained, int thralls) {
      bloodfiend = isBloodfiend;
      blood = Math.max(0, value);
      maxBlood = Math.max(0, max);
      generation = Math.max(0, gen);
      totalBloodDrained = Math.max(0, drained);
      totalThrallsTurned = Math.max(0, thralls);
   }

   public static boolean isBloodfiend() {
      return bloodfiend;
   }

   public static int getBlood() {
      return blood;
   }

   public static int getMaxBlood() {
      return maxBlood;
   }

   public static int getGeneration() {
      return generation;
   }

   public static int getTotalBloodDrained() {
      return totalBloodDrained;
   }

   public static int getTotalThrallsTurned() {
      return totalThrallsTurned;
   }
}

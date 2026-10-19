package io.github.Memoires.mysticism.client.ability;

public final class CaptivatorClientState {
   private static boolean lieMode;

   private CaptivatorClientState() {
   }

   public static boolean isLieMode() {
      return lieMode;
   }

   public static void setLieMode(boolean value) {
      lieMode = value;
   }
}

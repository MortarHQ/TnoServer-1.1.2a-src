package org.crypticdev.stextras.storage.player.client;

public final class ClientBossKills {
   private static int kills;

   public static void set(int value) {
      kills = value;
   }

   public static int get() {
      return kills;
   }
}

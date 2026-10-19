package de.melanx.simplebackups.compat;

import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;

public class Mc2DiscordCompat {
   public static void announce(Component text) {
   }

   public static boolean isLoaded() {
      return ModList.get().isLoaded("mc2discord");
   }
}

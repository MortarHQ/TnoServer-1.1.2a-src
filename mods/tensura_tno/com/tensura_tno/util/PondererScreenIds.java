package com.tensura_tno.util;

import net.minecraft.client.gui.screens.Screen;

public final class PondererScreenIds {
   private static final String PONDER_PACKAGE_PREFIX = "net.createmod.ponder.";
   private static final String PONDERER_PACKAGE_PREFIX = "com.nododiiiii.ponderer.";

   private PondererScreenIds() {
   }

   public static boolean isPonderUI(String className) {
      return className != null && (className.startsWith("net.createmod.ponder.") || className.startsWith("com.nododiiiii.ponderer."));
   }

   public static boolean isPonderUI(Screen screen) {
      return screen != null && isPonderUI(screen.getClass().getName());
   }
}

package com.tensura_tno.compat.ponder_jei;

import com.tensura_tno.util.PondererScreenIds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class PonderJeiEmiSwitch {
   private static volatile Object realIngredientListOverlay;
   private static volatile Object realBookmarkOverlay;

   private PonderJeiEmiSwitch() {
   }

   public static void setRealIngredientListOverlay(Object overlay) {
      if (overlay != null) {
         String name = overlay.getClass().getName();
         if (name.equals("mezz.jei.gui.overlay.IngredientListOverlay")) {
            realIngredientListOverlay = overlay;
         }
      }
   }

   public static void setRealBookmarkOverlay(Object overlay) {
      if (overlay != null) {
         String name = overlay.getClass().getName();
         if (name.equals("mezz.jei.gui.overlay.bookmarks.BookmarkOverlay")) {
            realBookmarkOverlay = overlay;
         }
      }
   }

   public static boolean isPonderMainScreen(Screen screen) {
      return PondererScreenIds.isPonderUI(screen);
   }

   public static boolean inPonderGuiContext() {
      Minecraft mc = Minecraft.getInstance();
      return isPonderMainScreen(mc.screen);
   }

   public static Object getRealIngredientListOverlay() {
      return realIngredientListOverlay;
   }

   public static Object getRealBookmarkOverlay() {
      return realBookmarkOverlay;
   }
}

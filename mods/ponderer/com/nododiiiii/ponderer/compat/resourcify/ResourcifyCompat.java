package com.nododiiiii.ponderer.compat.resourcify;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public final class ResourcifyCompat {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static Boolean loaded = null;

   private ResourcifyCompat() {
   }

   public static boolean isLoaded() {
      if (loaded == null) {
         try {
            Class.forName("dev.dediamondpro.resourcify.gui.browsepage.BrowseScreen");
            loaded = true;
         } catch (ClassNotFoundException e) {
            loaded = false;
         }
      }

      return loaded;
   }

   public static void openBrowseScreen(String initialQuery) {
      if (!isLoaded()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null) {
            player.displayClientMessage(Component.translatable("ponderer.ui.resourcify_not_installed"), false);
         }
      } else {
         try {
            ResourcifyBrowseHelper.open(initialQuery);
         } catch (Exception e) {
            LOGGER.error("Failed to open Resourcify browse screen", e);
         }
      }
   }
}

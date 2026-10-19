package org.crypticdev.stextras.client.loadingScreen;

import net.minecraft.resources.ResourceLocation;

public final class loadingScreen {
   public static final String MOD_ID = "stextras";
   public static final String BASE_PATH = "textures/loadingscreen/";
   public static final ResourceLocation MOJANGSTUDIOS = tex("mojangstudios.png");
   public static final ResourceLocation MOJANK = tex("mojank.png");
   public static final ResourceLocation PROGRESSBAR = tex("progressbar.png");
   public static final ResourceLocation EMPTY = tex("empty.png");
   public static final ResourceLocation LOGO_LIGHT = tex("logo_light.png");
   public static final ResourceLocation IMAGE = tex("image.png");
   public static final ResourceLocation IMAGE_ANIMATED = tex("image.gif");
   public static final ResourceLocation PROGRESSBAR_BACKGROUND = tex("progressbar_background.png");

   private loadingScreen() {
   }

   public static ResourceLocation tex(String fileName) {
      return ResourceLocation.fromNamespaceAndPath("stextras", "textures/loadingscreen/" + fileName);
   }
}

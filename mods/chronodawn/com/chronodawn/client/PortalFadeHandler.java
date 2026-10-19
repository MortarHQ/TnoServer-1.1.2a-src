package com.chronodawn.client;

public class PortalFadeHandler {
   private static int fadeInTimer = 0;
   private static final int FADE_DURATION = 20;

   public static void tick() {
      if (fadeInTimer > 0) {
         fadeInTimer--;
      }
   }

   public static void triggerFadeIn() {
      fadeInTimer = 20;
   }

   public static float getFadeAlpha() {
      return fadeInTimer == 0 ? 0.0F : fadeInTimer / 20.0F;
   }
}

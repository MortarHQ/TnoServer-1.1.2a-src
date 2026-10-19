package com.foxablazeultimate.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class WisdomCrystalEasterEggOverlay {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/gui/wisdom_crystal_easter_egg.png");
   private static final long DURATION_NANOS = 2000000000L;
   private static final long FADE_NANOS = 500000000L;
   private static final float DISPLAY_SCALE = 0.65F;
   private static final int TEXTURE_SIZE = 512;
   private static volatile long startedAtNanos = Long.MIN_VALUE;

   private WisdomCrystalEasterEggOverlay() {
   }

   public static void trigger() {
      startedAtNanos = System.nanoTime();
   }

   public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
      long started = startedAtNanos;
      if (started != Long.MIN_VALUE) {
         if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            long elapsed = System.nanoTime() - started;
            if (elapsed >= 2000000000L) {
               startedAtNanos = Long.MIN_VALUE;
            } else {
               float alpha = alphaAt(elapsed);
               if (!(alpha <= 0.0F)) {
                  int width = graphics.guiWidth();
                  int height = graphics.guiHeight();
                  int size = Math.max(1, Math.round(Math.min(width, height) * 0.65F));
                  int x = (width - size) / 2;
                  int y = Math.max(0, height - size);
                  RenderSystem.enableBlend();
                  RenderSystem.defaultBlendFunc();
                  graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
                  graphics.blit(TEXTURE, x, y, size, size, 0.0F, 0.0F, 512, 512, 512, 512);
                  graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                  RenderSystem.disableBlend();
               }
            }
         } else {
            startedAtNanos = Long.MIN_VALUE;
         }
      }
   }

   private static float alphaAt(long elapsedNanos) {
      if (elapsedNanos < 500000000L) {
         return smoothStep((float)elapsedNanos / 5.0E8F);
      }

      long fadeOutStart = 1500000000L;
      return elapsedNanos > fadeOutStart ? smoothStep((float)(2000000000L - elapsedNanos) / 5.0E8F) : 1.0F;
   }

   private static float smoothStep(float value) {
      float clamped = Math.max(0.0F, Math.min(1.0F, value));
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }
}

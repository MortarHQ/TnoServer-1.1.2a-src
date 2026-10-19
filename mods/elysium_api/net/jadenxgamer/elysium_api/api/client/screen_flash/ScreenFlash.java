package net.jadenxgamer.elysium_api.api.client.screen_flash;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.jadenxgamer.elysium_api.impl.networking.to_client.ScreenFlashPayload;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class ScreenFlash {
   private static final ResourceLocation TEXTURE = ElysiumAPI.elysiumPath("textures/misc/screen_flash.png");
   private static int fadeInDuration = 0;
   private static int holdDuration = 0;
   private static int fadeOutDuration = 0;
   private static boolean playing = false;
   private static float startTime = -1.0F;
   private static boolean firstPersonOnly = true;
   private static int flashColor = -1;

   public static void triggerScreenFlash(int fadeIn, int hold, int fadeOut, int color, boolean firstPersonOnly, boolean force) {
      if (!playing || force) {
         if (force) {
            stopScreenFlash();
         }

         fadeInDuration = fadeIn;
         holdDuration = hold;
         fadeOutDuration = fadeOut;
         flashColor = color;
         playing = true;
         ScreenFlash.firstPersonOnly = firstPersonOnly;
         startTime = -1.0F;
      }
   }

   public static void triggerScreenFlash(ServerPlayer player, int fadeIn, int hold, int fadeOut, int color, boolean firstPersonOnly, boolean force) {
      PacketDistributor.sendToPlayer(player, new ScreenFlashPayload(fadeIn, hold, fadeOut, color, firstPersonOnly, force), new CustomPacketPayload[0]);
   }

   public static void stopScreenFlash() {
      playing = false;
      fadeInDuration = 0;
      holdDuration = 0;
      fadeOutDuration = 0;
      startTime = -1.0F;
      firstPersonOnly = true;
      flashColor = -1;
   }

   public static void handle(GuiGraphics guiGraphics, float partialTick) {
      if (playing) {
         Minecraft client = Minecraft.getInstance();
         if (client.level != null && client.player != null) {
            if (startTime < 0.0F) {
               startTime = (float)client.level.getGameTime() + partialTick;
            }

            float currentTime = (float)client.level.getGameTime() + partialTick;
            float elapsed = currentTime - startTime;
            float totalDuration = fadeInDuration + holdDuration + fadeOutDuration;
            if (client.options.getCameraType() != CameraType.FIRST_PERSON && firstPersonOnly) {
               if (elapsed >= totalDuration) {
                  stopScreenFlash();
               }
            } else if (elapsed >= totalDuration) {
               stopScreenFlash();
            } else {
               float opacity;
               if (elapsed < fadeInDuration) {
                  opacity = elapsed / fadeInDuration;
               } else if (elapsed < fadeInDuration + holdDuration) {
                  opacity = 1.0F;
               } else {
                  float fadeOutStart = fadeInDuration + holdDuration;
                  opacity = 1.0F - (elapsed - fadeOutStart) / fadeOutDuration;
               }

               renderScreenFlash(guiGraphics, client, opacity);
            }
         } else {
            stopScreenFlash();
         }
      }
   }

   private static void renderScreenFlash(GuiGraphics guiGraphics, Minecraft client, float opacity) {
      float screenEffectIntensity = ((Double)client.options.screenEffectScale().get()).floatValue();
      float actualOpacity = opacity * screenEffectIntensity;
      if (!(actualOpacity <= 0.001F)) {
         float alpha = (flashColor >> 24 & 0xFF) / 255.0F;
         float red = (flashColor >> 16 & 0xFF) / 255.0F;
         float green = (flashColor >> 8 & 0xFF) / 255.0F;
         float blue = (flashColor & 0xFF) / 255.0F;
         float finalAlpha = alpha * actualOpacity;
         int width = client.getWindow().getGuiScaledWidth();
         int height = client.getWindow().getGuiScaledHeight();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         guiGraphics.setColor(red, green, blue, finalAlpha);
         guiGraphics.blit(TEXTURE, 0, 0, width, height, 0.0F, 0.0F, 2, 2, 2, 2);
         RenderSystem.disableBlend();
         guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}

package com.tensura.naturesaura.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tensura.naturesaura.mixin.ClientEventsAccessor;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = "tensura_naturesaura", value = Dist.CLIENT)
public final class AuraHudHandler {
   private static final int BAR_LENGTH = 50;
   private static final int EYE_OVERFLOW_LENGTH = 25;
   private static final int EYE_OVERFLOW_GAP = 1;
   private static final int CACHE_TEXTURE_LENGTH = 80;
   private static final int BAR_THICKNESS = 6;
   private static final int BAR_GAP = 4;
   private static final int RIGHT_MARGIN = 8;
   private static final int TOP_OFFSET = 20;

   private AuraHudHandler() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onRenderGuiLayer(Post event) {
      if (VanillaGuiLayers.HOTBAR.equals(event.getName())) {
         Minecraft minecraft = Minecraft.getInstance();
         if (!minecraft.options.hideGui && minecraft.player != null && minecraft.level != null) {
            ItemStack heldCache = ClientEventsAccessor.tensuraNaturesAura$getHeldCache();
            ItemStack heldEye = ClientEventsAccessor.tensuraNaturesAura$getHeldEye();
            ItemStack heldOcular = ClientEventsAccessor.tensuraNaturesAura$getHeldOcular();
            IAuraContainer cacheContainer = heldCache.isEmpty()
               ? null
               : (IAuraContainer)heldCache.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, null);
            boolean showCache = cacheContainer != null;
            boolean showEye = !minecraft.getDebugOverlay().showDebugScreen() && (!heldEye.isEmpty() || !heldOcular.isEmpty());
            int barCount = (showEye ? 1 : 0) + (showCache ? 1 : 0);
            if (barCount != 0) {
               int x = minecraft.getWindow().getGuiScaledWidth() - 8 - barCount * 6 - (barCount - 1) * 4;
               GuiGraphics graphics = event.getGuiGraphics();
               graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

               try {
                  if (showEye) {
                     int aura = IAuraChunk.triangulateAuraInArea(minecraft.level, minecraft.player.blockPosition(), 35);
                     int maxAura = 2000000;
                     int color = IAuraType.forLevel(minecraft.level).getColor();
                     drawEyeBar(graphics, x, 20, aura, maxAura, color, !heldOcular.isEmpty());
                     x += 10;
                  }

                  if (showCache) {
                     drawCacheBar(graphics, x, 20, cacheContainer.getStoredAura(), cacheContainer.getMaxAura(), cacheContainer.getAuraColor());
                  }
               } finally {
                  graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   private static void drawEyeBar(GuiGraphics graphics, int x, int y, int amount, int maximum, int color, boolean showOverflow) {
      int filledHeight = getFilledLength(amount, maximum, 50);
      setColor(graphics, color);
      int overflowHeight = showOverflow ? Mth.ceil(Mth.clamp(((float)amount / maximum - 1.0F) * 2.0F, 0.0F, 1.0F) * 25.0F) : 0;
      if (overflowHeight > 0) {
         if (overflowHeight < 25) {
            graphics.blit(ClientEvents.OVERLAYS, x, y, 18.0F, 12.0F, 6, 25 - overflowHeight, 256, 256);
         }

         graphics.blit(ClientEvents.OVERLAYS, x, y + 25 - overflowHeight, 12.0F, 37 - overflowHeight, 6, overflowHeight, 256, 256);
         y += 26;
      }

      if (filledHeight < 50) {
         graphics.blit(ClientEvents.OVERLAYS, x, y, 6.0F, 12.0F, 6, 50 - filledHeight, 256, 256);
      }

      if (filledHeight > 0) {
         graphics.blit(ClientEvents.OVERLAYS, x, y + 50 - filledHeight, 0.0F, 62 - filledHeight, 6, filledHeight, 256, 256);
      }
   }

   private static void drawCacheBar(GuiGraphics graphics, int x, int y, int amount, int maximum, int color) {
      int filledHeight = getFilledLength(amount, maximum, 50);
      int sourceFilledWidth = getFilledLength(amount, maximum, 80);
      setColor(graphics, color);
      PoseStack pose = graphics.pose();
      pose.pushPose();
      pose.translate(x, y + 50, 0.0F);
      pose.mulPose(Axis.ZP.rotationDegrees(-90.0F));

      try {
         if (filledHeight < 50) {
            graphics.blit(ClientEvents.OVERLAYS, filledHeight, 0, 50 - filledHeight, 6, sourceFilledWidth, 0.0F, 80 - sourceFilledWidth, 6, 256, 256);
         }

         if (filledHeight > 0) {
            graphics.blit(ClientEvents.OVERLAYS, 0, 0, filledHeight, 6, 0.0F, 6.0F, sourceFilledWidth, 6, 256, 256);
         }
      } finally {
         pose.popPose();
      }
   }

   private static int getFilledLength(int amount, int maximum, int length) {
      return maximum <= 0 ? 0 : Mth.clamp(Mth.ceil((float)amount / maximum * length), 0, length);
   }

   private static void setColor(GuiGraphics graphics, int color) {
      graphics.setColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
   }
}

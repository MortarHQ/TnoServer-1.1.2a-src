package dev.foxboss.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.foxboss.network.MilimBossBars;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;

@EventBusSubscriber(modid = "foxboss", value = Dist.CLIENT)
public final class MilimBossBarOverlay {
   private static final ResourceLocation FRAME = texture("milim_frame");
   private static final ResourceLocation BACKGROUND = texture("milim_background");
   private static final ResourceLocation PROGRESS = texture("milim_progress");
   private static final int BAR_WIDTH = 182;
   private static final int BAR_HEIGHT = 5;

   @SubscribeEvent
   public static void render(BossEventProgress event) {
      if (MilimBossBars.contains(event.getBossEvent().getId())) {
         event.setCanceled(true);
         event.setIncrement(36);
         GuiGraphics graphics = event.getGuiGraphics();
         int x = event.getX();
         int y = event.getY() + 6;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         graphics.blit(BACKGROUND, x, y, 0.0F, 0.0F, 182, 5, 182, 5);
         float progress = event.getBossEvent().getProgress();
         int filled = Float.isFinite(progress) ? Mth.clamp((int)(progress * 182.0F), 0, 182) : 0;
         if (filled > 0) {
            graphics.blit(PROGRESS, x, y, 0.0F, 0.0F, filled, 5, 182, 5);
         }

         graphics.blit(FRAME, x - 37, y - 12, 0.0F, 0.0F, 256, 32, 256, 32);
         RenderSystem.disableBlend();
      }
   }

   @SubscribeEvent
   public static void disconnect(LoggingOut event) {
      MilimBossBars.clear();
   }

   private static ResourceLocation texture(String name) {
      return ResourceLocation.fromNamespaceAndPath("foxboss", "textures/gui/boss_bar/" + name + ".png");
   }

   private MilimBossBarOverlay() {
   }
}

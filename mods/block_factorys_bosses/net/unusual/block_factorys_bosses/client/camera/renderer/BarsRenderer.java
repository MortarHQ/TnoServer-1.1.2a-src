package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Pre;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(Dist.CLIENT)
public class BarsRenderer implements CameraHandler {
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void eventHandler(Pre event) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BARS)) {
            renderBars(event);
         }
      }
   }

   @SubscribeEvent
   public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
      if (event.getOverlayType() == OverlayType.FIRE) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BARS)) {
            event.setCanceled(true);
         }
      }
   }

   private static void renderBars(Pre event) {
      int w = event.getGuiGraphics().guiWidth();
      int h = event.getGuiGraphics().guiHeight();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      event.getGuiGraphics().blit(BossesRise.prefix("textures/screens/cinematic_bars.png"), 0, 0, 0.0F, 0.0F, w, h, w, h);
      RenderSystem.depthMask(true);
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}

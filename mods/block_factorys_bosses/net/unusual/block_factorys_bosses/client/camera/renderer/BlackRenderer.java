package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Pre;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(Dist.CLIENT)
public class BlackRenderer implements CameraHandler {
   @SubscribeEvent(receiveCanceled = true)
   public static void computeCameraAngles(Pre event) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BLACK)) {
            onRenderGui(event.getGuiGraphics());
         }
      }
   }

   @SubscribeEvent
   public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
      if (event.getOverlayType() == OverlayType.FIRE) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BLACK)) {
            event.setCanceled(true);
         }
      }
   }

   public static void onRenderGui(GuiGraphics graphics) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
      int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
      int color = -16777216;
      graphics.fill(0, 0, screenWidth, screenHeight, color);
      RenderSystem.disableBlend();
   }
}

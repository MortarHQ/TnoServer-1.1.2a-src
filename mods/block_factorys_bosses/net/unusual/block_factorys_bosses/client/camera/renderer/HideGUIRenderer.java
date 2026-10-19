package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

public class HideGUIRenderer implements CameraHandler {
   private static Boolean wasHideGui = null;

   @SubscribeEvent
   public static void onTickPre(ComputeFov event) {
      if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.HIDE_GUI)) {
         if (wasHideGui == null) {
            wasHideGui = Minecraft.getInstance().options.hideGui;
         }

         Minecraft.getInstance().options.hideGui = true;
      }
   }

   @Override
   public void cleanup() {
      Minecraft.getInstance().options.hideGui = wasHideGui != null && wasHideGui;
      wasHideGui = null;
   }
}

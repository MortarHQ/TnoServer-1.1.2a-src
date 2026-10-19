package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(Dist.CLIENT)
public class FovRenderer implements CameraHandler {
   @SubscribeEvent
   public static void computeFOV(ComputeFov event) {
      if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.FOV)) {
         event.setFOV(event.getFOV() + 30.0);
      }
   }
}

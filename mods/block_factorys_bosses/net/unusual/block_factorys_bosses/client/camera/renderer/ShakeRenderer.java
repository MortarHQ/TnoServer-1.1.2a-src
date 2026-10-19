package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(Dist.CLIENT)
public class ShakeRenderer implements CameraHandler {
   private static CameraInject camera;

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void computeCameraAngles(ComputeCameraAngles event) {
      if (!Minecraft.getInstance().isPaused()) {
         ClientLevel level = Minecraft.getInstance().level;
         camera = (CameraInject)event.getCamera();
         if (level != null) {
            if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.SHAKE)) {
               float yaw = event.getYaw() + ((float)Math.random() - 0.5F);
               float pitch = event.getPitch() + ((float)Math.random() - 0.5F);
               event.setYaw(yaw);
               event.setPitch(pitch);
            }
         }
      }
   }

   @Override
   public void cleanup() {
      camera.setCinematic(false);
   }
}

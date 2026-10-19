package com.benji.netherman.client;

import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

@EventBusSubscriber(modid = "netherman", value = Dist.CLIENT)
public class ClientFogHandler {
   public static boolean isInsideMansion = false;
   private static float fogProgress = 0.0F;

   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (isInsideMansion && fogProgress < 1.0F) {
         fogProgress += 0.01F;
      } else if (!isInsideMansion && fogProgress > 0.0F) {
         fogProgress -= 0.01F;
      }

      fogProgress = Mth.clamp(fogProgress, 0.0F, 1.0F);
      isInsideMansion = false;
   }

   @SubscribeEvent
   public static void onFogColor(ComputeFogColor event) {
      if (fogProgress > 0.0F) {
         float targetRed = 0.6F;
         float targetGreen = 0.0F;
         float targetBlue = 0.02F;
         event.setRed(Mth.lerp(fogProgress, event.getRed(), targetRed));
         event.setGreen(Mth.lerp(fogProgress, event.getGreen(), targetGreen));
         event.setBlue(Mth.lerp(fogProgress, event.getBlue(), targetBlue));
      }
   }

   @SubscribeEvent
   public static void onRenderFog(RenderFog event) {
      if (fogProgress > 0.0F) {
         float targetFarPlane = 48.0F;
         float targetNearPlane = 12.0F;
         float currentFar = event.getFarPlaneDistance();
         float currentNear = event.getNearPlaneDistance();
         event.setNearPlaneDistance(Mth.lerp(fogProgress, currentNear, targetNearPlane));
         event.setFarPlaneDistance(Mth.lerp(fogProgress, currentFar, targetFarPlane));
         event.setCanceled(true);
      }
   }
}

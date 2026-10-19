package com.mortal_cultivation.event;

import com.mortal_cultivation.dimension.MindscapeDimension;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class MindscapeFogRenderer {
   @SubscribeEvent
   public static void onRenderFog(RenderFog event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         if (mc.level.dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            event.setNearPlaneDistance(2.0F);
            event.setFarPlaneDistance(30.0F);
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onComputeFogColor(ComputeFogColor event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         if (mc.level.dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            event.setRed(0.15F);
            event.setGreen(0.15F);
            event.setBlue(0.18F);
         }
      }
   }
}

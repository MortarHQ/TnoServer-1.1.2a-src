package com.mortal_cultivation.client;

import com.mortal_cultivation.client.renderer.ZhaoYingZhuItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class ZhaoYingZhuClientEvents {
   private ZhaoYingZhuClientEvents() {
   }

   @SubscribeEvent
   public static void registerAdditionalModels(RegisterAdditional event) {
      event.register(ZhaoYingZhuItemRenderer.ICON_MODEL);
      event.register(ZhaoYingZhuItemRenderer.STORED_ICON_MODEL);
   }
}

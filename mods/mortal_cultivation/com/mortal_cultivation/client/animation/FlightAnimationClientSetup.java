package com.mortal_cultivation.client.animation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class FlightAnimationClientSetup {
   private FlightAnimationClientSetup() {
   }

   @SubscribeEvent
   public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(FlightAnimationLibrary::reload);
   }
}

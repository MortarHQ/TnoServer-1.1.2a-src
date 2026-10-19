package com.github.b4ndithelps.tennogamenolife.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
public class ClientModEvents {
   @SubscribeEvent
   public static void onRegisterEntityRenderers(RegisterRenderers event) {
   }

   @SubscribeEvent
   public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event) {
   }
}

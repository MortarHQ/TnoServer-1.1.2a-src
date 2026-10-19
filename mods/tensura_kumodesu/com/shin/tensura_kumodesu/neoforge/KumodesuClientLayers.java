package com.shin.tensura_kumodesu.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;

@EventBusSubscriber(modid = "tensura_kumodesu", value = Dist.CLIENT)
public class KumodesuClientLayers {
   @SubscribeEvent
   public static void addLayers(AddLayers event) {
   }
}

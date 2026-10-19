package com.shin.tensura_kumodesu.neoforge;

import com.shin.tensura_kumodesu.entity.model.WrathOni;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

@EventBusSubscriber(Dist.CLIENT)
public class KumodesuModelsRegistry {
   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(WrathOni.LAYER_LOCATION, WrathOni::createBodyLayer);
   }
}

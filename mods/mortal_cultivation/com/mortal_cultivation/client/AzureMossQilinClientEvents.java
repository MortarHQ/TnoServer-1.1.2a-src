package com.mortal_cultivation.client;

import com.mortal_cultivation.client.renderer.AzureMossQilinRenderer;
import com.mortal_cultivation.registry.CultivationEntities;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class AzureMossQilinClientEvents {
   private AzureMossQilinClientEvents() {
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)CultivationEntities.AZURE_MOSS_QILIN.get(), AzureMossQilinRenderer::new);
   }
}

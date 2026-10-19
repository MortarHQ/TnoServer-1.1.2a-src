package com.craisinlord.apple_cows.neoforge.client;

import com.craisinlord.apple_cows.content.client.renderer.AppleCowRenderer;
import com.craisinlord.apple_cows.neoforge.registry.AppleCowsNeoforgeEntities;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "apple_cows", bus = Bus.MOD, value = Dist.CLIENT)
public class AppleCowsNeoForgeClient {
   @SubscribeEvent
   public static void onRegisterEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)AppleCowsNeoforgeEntities.APPLE_COW.get(), AppleCowRenderer::new);
      event.registerEntityRenderer((EntityType)AppleCowsNeoforgeEntities.GOLDEN_APPLE_COW.get(), AppleCowRenderer::new);
      event.registerEntityRenderer((EntityType)AppleCowsNeoforgeEntities.ENCHANTED_GOLDEN_APPLE_COW.get(), AppleCowRenderer::new);
   }
}

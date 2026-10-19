package dev.foxboss.client;

import dev.foxboss.registry.FoxEntities;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "foxboss", bus = Bus.MOD, value = Dist.CLIENT)
public final class FoxBossClient {
   @SubscribeEvent
   public static void renderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)FoxEntities.MILIM.get(), MilimRenderer::new);
      event.registerEntityRenderer((EntityType)FoxEntities.DRAGON_STAR.get(), DragonStarRenderer::new);
      event.registerEntityRenderer((EntityType)FoxEntities.THROWN_BLOCK.get(), ThrownBlockRenderer::new);
   }

   private FoxBossClient() {
   }
}

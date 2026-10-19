package net.tslat.smartbrainlib.example.boilerplate;

import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.tslat.smartbrainlib.SBLConstants;
import net.tslat.smartbrainlib.SBLNeoForge;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
final class SBLClient {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers ev) {
      if (SBLConstants.SBL_LOADER.isDevEnv()) {
         ev.registerEntityRenderer((EntityType)SBLNeoForge.SBL_SKELETON.get(), SkeletonRenderer::new);
      }
   }
}

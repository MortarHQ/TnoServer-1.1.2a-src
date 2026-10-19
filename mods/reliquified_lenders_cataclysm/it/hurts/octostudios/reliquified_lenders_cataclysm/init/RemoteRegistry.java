package it.hurts.octostudios.reliquified_lenders_cataclysm.init;

import it.hurts.octostudios.reliquified_lenders_cataclysm.client.renderer.VoidVortexModifiedRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "reliquified_lenders_cataclysm", bus = Bus.MOD, value = Dist.CLIENT)
public class RemoteRegistry {
   @SubscribeEvent
   public static void entityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)EntityRegistry.VOID_VORTEX_MODIFIED.get(), VoidVortexModifiedRenderer::new);
   }
}

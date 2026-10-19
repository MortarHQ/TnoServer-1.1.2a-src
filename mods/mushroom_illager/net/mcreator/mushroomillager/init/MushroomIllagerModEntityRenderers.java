package net.mcreator.mushroomillager.init;

import net.mcreator.mushroomillager.client.renderer.MushroomIllagerRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class MushroomIllagerModEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)MushroomIllagerModEntities.MUSHROOM_ILLAGER.get(), MushroomIllagerRenderer::new);
   }
}

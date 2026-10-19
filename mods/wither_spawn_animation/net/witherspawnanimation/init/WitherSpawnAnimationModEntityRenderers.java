package net.witherspawnanimation.init;

import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.witherspawnanimation.client.renderer.AnimatedAltarBygoneMinXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarBygoneMinZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarBygoneXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarBygoneZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarMinXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarMinZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarSoilMinXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarSoilMinZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarSoilXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarSoilZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormMinXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormMinZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormSoilMinXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormSoilMinZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormSoilXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormSoilZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarStormZRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarXRenderer;
import net.witherspawnanimation.client.renderer.AnimatedAltarZRenderer;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class WitherSpawnAnimationModEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_Z.get(), AnimatedAltarZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_MIN_Z.get(), AnimatedAltarMinZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_X.get(), AnimatedAltarXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_MIN_X.get(), AnimatedAltarMinXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_Z.get(), AnimatedAltarSoilZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_MIN_Z.get(), AnimatedAltarSoilMinZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_X.get(), AnimatedAltarSoilXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_MIN_X.get(), AnimatedAltarSoilMinXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_Z.get(), AnimatedAltarStormZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_MIN_Z.get(), AnimatedAltarStormMinZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_X.get(), AnimatedAltarStormXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_MIN_X.get(), AnimatedAltarStormMinXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_Z.get(), AnimatedAltarBygoneZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_MIN_Z.get(), AnimatedAltarBygoneMinZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_X.get(), AnimatedAltarBygoneXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_MIN_X.get(), AnimatedAltarBygoneMinXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_Z.get(), AnimatedAltarStormSoilZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_MIN_Z.get(), AnimatedAltarStormSoilMinZRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_X.get(), AnimatedAltarStormSoilXRenderer::new);
      event.registerEntityRenderer((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_MIN_X.get(), AnimatedAltarStormSoilMinXRenderer::new);
   }
}

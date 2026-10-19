package net.regions_unexplored.client;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.regions_unexplored.client.particle.RUParticleProviders;

@EventBusSubscriber(value = Dist.CLIENT, modid = "regions_unexplored")
public class ParticleRegistration {
   public static final Map<ParticleType, SpriteParticleRegistration> spriteParticleRegistrationMap = new HashMap<>();

   @SubscribeEvent
   public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      RUParticleProviders.init();
      spriteParticleRegistrationMap.forEach(event::registerSpriteSet);
   }
}

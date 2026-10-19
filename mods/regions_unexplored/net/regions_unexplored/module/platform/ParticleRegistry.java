package net.regions_unexplored.module.platform;

import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.regions_unexplored.client.ParticleRegistration;

public class ParticleRegistry {
   public static <T extends ParticleOptions> void register(ParticleType<T> type, SpriteParticleRegistration<T> factory) {
      ParticleRegistration.spriteParticleRegistrationMap.computeIfAbsent(type, k -> factory);
   }
}

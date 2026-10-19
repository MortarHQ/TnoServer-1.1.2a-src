package net.regions_unexplored.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.regions_unexplored.client.particle.spore.FallingSporeParticle;
import net.regions_unexplored.client.particle.spore.FloatingSporeParticle;
import net.regions_unexplored.client.particle.spore.GroundSporeParticle;
import net.regions_unexplored.module.platform.ParticleRegistry;
import net.regions_unexplored.registry.RUParticleTypes;

public interface RUParticleProviders {
   static void init() {
      ParticleRegistry.register(RUParticleTypes.SMALL_LEAVES.get(), RULeavesParticle.Provider::flowing);
      ParticleRegistry.register(RUParticleTypes.STANDARD_LEAVES.get(), RULeavesParticle.Provider::swirling);
      ParticleRegistry.register(RUParticleTypes.LARGE_LEAVES.get(), RULeavesParticle.Provider::large);
      ParticleRegistry.register(RUParticleTypes.PINE_LEAVES.get(), RULeavesParticle.Provider::pine);
      ParticleRegistry.register(RUParticleTypes.MAGNOLIA_LEAVES.get(), RULeavesParticle.Provider::magnolia);
      ParticleRegistry.register((ParticleType)RUParticleTypes.PINK_MAGNOLIA_LEAVES.get(), RULeavesParticle.Provider::magnolia);
      ParticleRegistry.register((ParticleType)RUParticleTypes.WHITE_MAGNOLIA_LEAVES.get(), RULeavesParticle.Provider::magnolia);
      ParticleRegistry.register((ParticleType)RUParticleTypes.BLUE_MAGNOLIA_LEAVES.get(), RULeavesParticle.Provider::magnolia);
      ParticleRegistry.register((ParticleType)RUParticleTypes.GROUND_SPORE.get(), GroundSporeParticle.Provider::new);
      ParticleRegistry.register(RUParticleTypes.FALLING_SPORE.get(), FallingSporeParticle.Provider::new);
      ParticleRegistry.register(RUParticleTypes.FLOATING_SPORE.get(), FloatingSporeParticle.Provider::new);
      ParticleRegistry.register(RUParticleTypes.FLOATING_HYACINTH.get(), FloatingHyacinthParticle.Provider::new);
      ParticleRegistry.register((ParticleType)RUParticleTypes.PRISMARITE_SPARKLE.get(), PrismariteSparkleParticle.Provider::new);
      ParticleRegistry.register((ParticleType)RUParticleTypes.MYCOTOXIC_SPORE.get(), MycotoxicSporeParticle.MycotoxicSporeParticleProvider::new);
   }
}

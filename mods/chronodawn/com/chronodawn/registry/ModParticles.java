package com.chronodawn.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ModParticles {
   public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create("chronodawn", Registries.PARTICLE_TYPE);
   public static final RegistrySupplier<SimpleParticleType> CHRONO_DAWN_PORTAL = PARTICLE_TYPES.register(
      "chrono_dawn_portal", ModParticles::createSimpleParticleType
   );
   public static final RegistrySupplier<SimpleParticleType> CHRONO_SHIELD_ECHO = PARTICLE_TYPES.register(
      "chrono_shield_echo", ModParticles::createSimpleParticleType
   );

   private static SimpleParticleType createSimpleParticleType() {
      return new SimpleParticleType(false) {};
   }

   public static void register() {
      PARTICLE_TYPES.register();
   }
}

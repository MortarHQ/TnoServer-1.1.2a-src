package com.github.wal_bos.moreskills.registry.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModParticles {
   public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, "tensuramoreskills");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DEATHSHINE = PARTICLES.register("deathshine", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DEATH_ENERGY_LIGHTNING = PARTICLES.register(
      "death_energy_lightning", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> JUDGEMENT_SMOKE = PARTICLES.register(
      "judgement_smoke", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HOLY_SMOKE_BEAM = PARTICLES.register(
      "holy_smoke_beam", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SUN = PARTICLES.register("sun", () -> new SimpleParticleType(false));

   private ModParticles() {
   }

   public static void register(IEventBus eventBus) {
      PARTICLES.register(eventBus);
   }
}

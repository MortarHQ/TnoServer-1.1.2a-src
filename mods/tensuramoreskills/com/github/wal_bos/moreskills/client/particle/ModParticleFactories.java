package com.github.wal_bos.moreskills.client.particle;

import com.github.wal_bos.moreskills.registry.particle.ModParticles;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public final class ModParticleFactories {
   private ModParticleFactories() {
   }

   public static void register(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)ModParticles.DEATHSHINE.get(), DeathshineParticle.Provider::new);
      event.registerSpriteSet((ParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), DeathEnergyLightningParticle.Provider::new);
      event.registerSpriteSet((ParticleType)ModParticles.JUDGEMENT_SMOKE.get(), JudgementSmokeParticle.Provider::new);
      event.registerSpriteSet((ParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), HolySmokeBeamParticle.Provider::new);
      event.registerSpriteSet((ParticleType)ModParticles.SUN.get(), SunParticle.Provider::new);
   }
}

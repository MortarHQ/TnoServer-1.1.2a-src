package com.mongoose.royalvariations.init;

import com.mongoose.royalvariations.client.particles.AuraOfProtectionParticle;
import com.mongoose.royalvariations.client.particles.DazedStarParticle;
import com.mongoose.royalvariations.client.particles.DetonationTimerParticle;
import com.mongoose.royalvariations.client.particles.EndPoofParticle;
import com.mongoose.royalvariations.client.particles.EndSplashParticle;
import com.mongoose.royalvariations.client.particles.GoldenTrailParticle;
import com.mongoose.royalvariations.client.particles.HolyParticle;
import com.mongoose.royalvariations.client.particles.MarkParticle;
import com.mongoose.royalvariations.client.particles.RATraceParticle;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.client.particles.RoyalExplosionParticle;
import com.mongoose.royalvariations.client.particles.RoyalGlowParticle;
import com.mongoose.royalvariations.client.particles.RoyalSpiritParticle;
import com.mongoose.royalvariations.client.particles.ScrapsNetParticle;
import com.mongoose.royalvariations.client.particles.ShineParticle;
import com.mongoose.royalvariations.client.particles.SparkleParticle;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class ClientSideInit extends SidedInit {
   @Override
   public void init(IEventBus modEventBus) {
      modEventBus.addListener(this::setupParticles);
   }

   public void setupParticles(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)RVParticleTypes.AURA_OF_PROTECTION.get(), AuraOfProtectionParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.SHINE.get(), ShineParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.SPARKLE.get(), SparkleParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.GOLDEN_TRAIL.get(), GoldenTrailParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.DAZED_STAR.get(), DazedStarParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.SCRAPS_NET.get(), ScrapsNetParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.HOLY.get(), HolyParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.ROYAL_SPIRIT.get(), RoyalSpiritParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.ROYAL_GLOW.get(), RoyalGlowParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.END_SPLASH.get(), EndSplashParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.ROYAL_ARROW_TRACE.get(), RATraceParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.END_POOF.get(), EndPoofParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.ROYAL_EXPLOSION.get(), RoyalExplosionParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.MARK.get(), MarkParticle.Provider::new);
      event.registerSpriteSet((ParticleType)RVParticleTypes.DETONATION_TIMER.get(), DetonationTimerParticle.Provider::new);
   }
}

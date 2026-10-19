package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.Function;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public record RegisterParticlesEvent(RegisterParticlesEvent.Registrar registrar) {
   public static final EventHandler<RegisterParticlesEvent> EVENT = new EventHandler<>();

   public <T extends ParticleOptions> void register(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> registration) {
      this.registrar.register(type, registration);
   }

   @FunctionalInterface
   public interface Registrar {
      <T extends ParticleOptions> void register(ParticleType<T> var1, Function<SpriteSet, ParticleProvider<T>> var2);
   }
}

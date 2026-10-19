package net.darkhax.bookshelf.common.api.registry.register;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record RegisterParticleProviders(String owner, BiConsumer<SimpleParticleType, RegisterParticleProviders.SimpleParticleProviderBuilder> registerSimple) {
   public void add(Supplier<SimpleParticleType> type, RegisterParticleProviders.SimpleParticleProviderBuilder provider) {
      this.add(type.get(), provider);
   }

   public void add(String id, RegisterParticleProviders.SimpleParticleProviderBuilder provider) {
      this.add(ResourceLocation.tryBuild(this.owner, id), provider);
   }

   public void add(ResourceLocation id, RegisterParticleProviders.SimpleParticleProviderBuilder provider) {
      if (BuiltInRegistries.PARTICLE_TYPE.containsKey(id)) {
         ParticleType<?> type = (ParticleType<?>)BuiltInRegistries.PARTICLE_TYPE.get(id);
         if (type instanceof SimpleParticleType simpleType) {
            this.add(simpleType, provider);
         } else {
            throw new IllegalStateException("Particle type '" + id + "' is not a SimpleParticleType! type=" + type);
         }
      } else {
         throw new IllegalStateException("Particle type '" + id + "' has not been registered.");
      }
   }

   public void add(SimpleParticleType type, RegisterParticleProviders.SimpleParticleProviderBuilder provider) {
      this.registerSimple.accept(type, provider);
   }

   public interface SimpleParticleProviderBuilder {
      <T extends SpriteSet> ParticleProvider<SimpleParticleType> build(T var1);
   }
}

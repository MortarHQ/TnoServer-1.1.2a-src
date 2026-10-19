package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.common.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FriendsAndFoesParticleTypes {
   public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, "friendsandfoes");
   public static final RegistryEntry<SimpleParticleType> TOTEM_OF_FREEZING = PARTICLE_TYPES.register("totem_of_freezing", () -> new SimpleParticleType(false));
   public static final RegistryEntry<SimpleParticleType> TOTEM_OF_ILLUSION = PARTICLE_TYPES.register("totem_of_illusion", () -> new SimpleParticleType(false));

   public static void registerParticlesEvent(RegisterParticlesEvent event) {
      event.register((ParticleType)TOTEM_OF_FREEZING.get(), FreezingTotemParticle.Factory::new);
      event.register((ParticleType)TOTEM_OF_ILLUSION.get(), IllusionTotemParticle.Factory::new);
   }

   private FriendsAndFoesParticleTypes() {
   }
}

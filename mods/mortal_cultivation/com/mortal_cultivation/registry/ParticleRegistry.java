package com.mortal_cultivation.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ParticleRegistry {
   private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, "mortal_cultivation");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPIRIT_MIST = PARTICLES.register("spirit_mist", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_WOOD_ARC = PARTICLES.register(
      "lightning_wood_arc", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> JIUYOU_MIST = PARTICLES.register("jiuyou_mist", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> JIUYOU_WISP = PARTICLES.register("jiuyou_wisp", () -> new SimpleParticleType(false));

   private ParticleRegistry() {
   }

   public static void register(IEventBus bus) {
      PARTICLES.register(bus);
   }
}

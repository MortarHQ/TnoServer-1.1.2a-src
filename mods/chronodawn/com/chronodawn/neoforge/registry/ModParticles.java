package com.chronodawn.neoforge.registry;

import com.chronodawn.ChronoDawn;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
   public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, "chronodawn");
   public static final Supplier<SimpleParticleType> CHRONO_DAWN_PORTAL = PARTICLE_TYPES.register("chronodawn_portal", () -> new SimpleParticleType(false));

   public static void register(IEventBus eventBus) {
      PARTICLE_TYPES.register(eventBus);
      ChronoDawn.LOGGER.debug("Registered particle types for NeoForge");
   }
}

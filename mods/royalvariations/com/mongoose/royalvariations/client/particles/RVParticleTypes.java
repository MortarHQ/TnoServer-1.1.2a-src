package com.mongoose.royalvariations.client.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVParticleTypes {
   public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, "royalvariations");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AURA_OF_PROTECTION = PARTICLE_TYPES.register(
      "aura_of_protection", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHINE = PARTICLE_TYPES.register("shine", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKLE = PARTICLE_TYPES.register("sparkle", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_TRAIL = PARTICLE_TYPES.register(
      "golden_trail", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DAZED_STAR = PARTICLE_TYPES.register(
      "dazed_star", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SCRAPS_NET = PARTICLE_TYPES.register(
      "scraps_net", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HOLY = PARTICLE_TYPES.register("holy", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ROYAL_SPIRIT = PARTICLE_TYPES.register(
      "royal_spirit", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ROYAL_ARROW_TRACE = PARTICLE_TYPES.register(
      "royal_arrow_trace", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> END_POOF = PARTICLE_TYPES.register("end_poof", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, ParticleType<MarkParticleOption>> MARK = PARTICLE_TYPES.register(
      "mark", () -> new ParticleType<MarkParticleOption>(false) {
         public MapCodec<MarkParticleOption> codec() {
            return MarkParticleOption.CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, MarkParticleOption> streamCodec() {
            return MarkParticleOption.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<DTParticleOption>> DETONATION_TIMER = PARTICLE_TYPES.register(
      "detonation_timer", () -> new ParticleType<DTParticleOption>(false) {
         public MapCodec<DTParticleOption> codec() {
            return DTParticleOption.CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, DTParticleOption> streamCodec() {
            return DTParticleOption.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<RoyalGlowParticleOption>> ROYAL_GLOW = PARTICLE_TYPES.register(
      "royal_glow", () -> new ParticleType<RoyalGlowParticleOption>(false) {
         public MapCodec<RoyalGlowParticleOption> codec() {
            return RoyalGlowParticleOption.CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, RoyalGlowParticleOption> streamCodec() {
            return RoyalGlowParticleOption.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<EndSplashParticleOption>> END_SPLASH = PARTICLE_TYPES.register(
      "end_splash", () -> new ParticleType<EndSplashParticleOption>(false) {
         public MapCodec<EndSplashParticleOption> codec() {
            return EndSplashParticleOption.CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, EndSplashParticleOption> streamCodec() {
            return EndSplashParticleOption.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<RoyalExplosionParticleOption>> ROYAL_EXPLOSION = PARTICLE_TYPES.register(
      "royal_explosion", () -> new ParticleType<RoyalExplosionParticleOption>(false) {
         public MapCodec<RoyalExplosionParticleOption> codec() {
            return RoyalExplosionParticleOption.CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, RoyalExplosionParticleOption> streamCodec() {
            return RoyalExplosionParticleOption.STREAM_CODEC;
         }
      }
   );
}

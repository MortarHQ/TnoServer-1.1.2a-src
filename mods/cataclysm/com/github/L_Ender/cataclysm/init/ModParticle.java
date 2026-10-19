package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.client.particle.Options.AfterImageParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.CircleLightningParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.Circle_ParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.Cursed_MarkParticleOption;
import com.github.L_Ender.cataclysm.client.particle.Options.CustomPoofParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.GatheringWaterParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.IgnisSoulSwingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.IgnisSwingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningStormParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningZapParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.NotSpinTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.ParryParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RainFogParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.Rising_Trail_Options;
import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.ScyllaSwingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.SparkTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.SpinTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.StormParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.TrackLightningParticleOptions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticle {
   public static final DeferredRegister<ParticleType<?>> PARTICLE = DeferredRegister.create(Registries.PARTICLE_TYPE, "cataclysm");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARK = PARTICLE.register("spark", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_LAVA = PARTICLE.register("soul_lava", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RAIN_CLOUD = PARTICLE.register("rain_cloud", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, ParticleType<LightningParticleOptions>> LIGHTNING = PARTICLE.register(
      "lightning", () -> new ParticleType<LightningParticleOptions>(false) {
         public MapCodec<LightningParticleOptions> codec() {
            return LightningParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, LightningParticleOptions> streamCodec() {
            return LightningParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<SparkTrailParticleOptions>> SPARK_TRAIL = PARTICLE.register(
      "spark_trail", () -> new ParticleType<SparkTrailParticleOptions>(false) {
         public MapCodec<SparkTrailParticleOptions> codec() {
            return SparkTrailParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, SparkTrailParticleOptions> streamCodec() {
            return SparkTrailParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<StormParticleOptions>> STORM = PARTICLE.register(
      "storm", () -> new ParticleType<StormParticleOptions>(false) {
         public MapCodec<StormParticleOptions> codec() {
            return StormParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, StormParticleOptions> streamCodec() {
            return StormParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<Rising_Trail_Options>> RISING_TRAIL = PARTICLE.register(
      "rising_trail", () -> new ParticleType<Rising_Trail_Options>(false) {
         public MapCodec<Rising_Trail_Options> codec() {
            return Rising_Trail_Options.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, Rising_Trail_Options> streamCodec() {
            return Rising_Trail_Options.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<LightTrailParticleOptions>> LIGHT_TRAIL = PARTICLE.register(
      "light_trail", () -> new ParticleType<LightTrailParticleOptions>(false) {
         public MapCodec<LightTrailParticleOptions> codec() {
            return LightTrailParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, LightTrailParticleOptions> streamCodec() {
            return LightTrailParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<TrackLightningParticleOptions>> TRACK_LIGHTNING = PARTICLE.register(
      "track_lightning", () -> new ParticleType<TrackLightningParticleOptions>(false) {
         public MapCodec<TrackLightningParticleOptions> codec() {
            return TrackLightningParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, TrackLightningParticleOptions> streamCodec() {
            return TrackLightningParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<CircleLightningParticleOptions>> CIRCLE_LIGHTNING = PARTICLE.register(
      "circle_lightning", () -> new ParticleType<CircleLightningParticleOptions>(false) {
         public MapCodec<CircleLightningParticleOptions> codec() {
            return CircleLightningParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, CircleLightningParticleOptions> streamCodec() {
            return CircleLightningParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<RingParticleOptions>> RING = PARTICLE.register(
      "ring_0", () -> new ParticleType<RingParticleOptions>(false) {
         public MapCodec<RingParticleOptions> codec() {
            return RingParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, RingParticleOptions> streamCodec() {
            return RingParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<RoarParticleOptions>> ROAR = PARTICLE.register(
      "roar", () -> new ParticleType<RoarParticleOptions>(false) {
         public MapCodec<RoarParticleOptions> codec() {
            return RoarParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, RoarParticleOptions> streamCodec() {
            return RoarParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParryParticleOptions>> PARRY = PARTICLE.register(
      "parry", () -> new ParticleType<ParryParticleOptions>(false) {
         public MapCodec<ParryParticleOptions> codec() {
            return ParryParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, ParryParticleOptions> streamCodec() {
            return ParryParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ScyllaSwingParticleOptions>> SCYLLA_SWING = PARTICLE.register(
      "scylla_swing", () -> new ParticleType<ScyllaSwingParticleOptions>(false) {
         public MapCodec<ScyllaSwingParticleOptions> codec() {
            return ScyllaSwingParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, ScyllaSwingParticleOptions> streamCodec() {
            return ScyllaSwingParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<IgnisSwingParticleOptions>> IGNIS_SWING = PARTICLE.register(
      "ignis_swing", () -> new ParticleType<IgnisSwingParticleOptions>(false) {
         public MapCodec<IgnisSwingParticleOptions> codec() {
            return IgnisSwingParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, IgnisSwingParticleOptions> streamCodec() {
            return IgnisSwingParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<IgnisSoulSwingParticleOptions>> IGNIS_SOUL_SWING = PARTICLE.register(
      "ignis_soul_swing", () -> new ParticleType<IgnisSoulSwingParticleOptions>(false) {
         public MapCodec<IgnisSoulSwingParticleOptions> codec() {
            return IgnisSoulSwingParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, IgnisSoulSwingParticleOptions> streamCodec() {
            return IgnisSoulSwingParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<CustomPoofParticleOptions>> CUSTOM_POOF = PARTICLE.register(
      "custom_poof", () -> new ParticleType<CustomPoofParticleOptions>(false) {
         public MapCodec<CustomPoofParticleOptions> codec() {
            return CustomPoofParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, CustomPoofParticleOptions> streamCodec() {
            return CustomPoofParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<LightningZapParticleOptions>> LIGHTNING_ZAP = PARTICLE.register(
      "lightning_zap", () -> new ParticleType<LightningZapParticleOptions>(false) {
         public MapCodec<LightningZapParticleOptions> codec() {
            return LightningZapParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, LightningZapParticleOptions> streamCodec() {
            return LightningZapParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<LightningStormParticleOptions>> LIGHTNING_STORM = PARTICLE.register(
      "lightning_storm", () -> new ParticleType<LightningStormParticleOptions>(false) {
         public MapCodec<LightningStormParticleOptions> codec() {
            return LightningStormParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, LightningStormParticleOptions> streamCodec() {
            return LightningStormParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<RainFogParticleOptions>> RAIN_FOG = PARTICLE.register(
      "rain_fog", () -> new ParticleType<RainFogParticleOptions>(false) {
         public MapCodec<RainFogParticleOptions> codec() {
            return RainFogParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, RainFogParticleOptions> streamCodec() {
            return RainFogParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<GatheringWaterParticleOptions>> GATHERING_WATER = PARTICLE.register(
      "gathering_water", () -> new ParticleType<GatheringWaterParticleOptions>(false) {
         public MapCodec<GatheringWaterParticleOptions> codec() {
            return GatheringWaterParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, GatheringWaterParticleOptions> streamCodec() {
            return GatheringWaterParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<SpinTrailParticleOptions>> SPIN_TRAIL_PARTICLE = PARTICLE.register(
      "spin_trail", () -> new ParticleType<SpinTrailParticleOptions>(false) {
         public MapCodec<SpinTrailParticleOptions> codec() {
            return SpinTrailParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, SpinTrailParticleOptions> streamCodec() {
            return SpinTrailParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<NotSpinTrailParticleOptions>> NOT_SPIN_PARTICLE = PARTICLE.register(
      "not_spin_particle", () -> new ParticleType<NotSpinTrailParticleOptions>(false) {
         public MapCodec<NotSpinTrailParticleOptions> codec() {
            return NotSpinTrailParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, NotSpinTrailParticleOptions> streamCodec() {
            return NotSpinTrailParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CURSED_FLAME = PARTICLE.register("cursed_flame", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_CURSED_FLAME = PARTICLE.register(
      "small_cursed_flame", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHANTOM_WING_FLAME = PARTICLE.register(
      "phantom_wing_flame", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EM_PULSE = PARTICLE.register("em_pulse", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHOCK_WAVE = PARTICLE.register("shock_wave", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TRAP_FLAME = PARTICLE.register("trap_flame", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FLAME_JET = PARTICLE.register("flame_jet", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FLARE_EXPLODE = PARTICLE.register(
      "flare_explode", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_EXPLODE = PARTICLE.register(
      "lightning_explode", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IGNIS_EXPLODE = PARTICLE.register(
      "ignis_explode", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IGNIS_ABYSS_EXPLODE = PARTICLE.register(
      "ignis_abyss_explode", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IGNIS_SOUL_EXPLODE = PARTICLE.register(
      "ignis_soul_explode", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DESERT_GLYPH = PARTICLE.register("desert_glyph", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DUST_BLAST = PARTICLE.register("dust_blast", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHANTOM_EMITTER = PARTICLE.register(
      "phantom_emitter", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMETHYST_CRASH = PARTICLE.register(
      "amethyst_crash", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<Cursed_MarkParticleOption>> CURSED_MARK = PARTICLE.register(
      "cursed_mark", () -> new ParticleType<Cursed_MarkParticleOption>(false) {
         public MapCodec<Cursed_MarkParticleOption> codec() {
            return Cursed_MarkParticleOption.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, Cursed_MarkParticleOption> streamCodec() {
            return Cursed_MarkParticleOption.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CURSED_ALGIZ = PARTICLE.register("cursed_algiz", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, ParticleType<Circle_ParticleOptions>> CIRCLE = PARTICLE.register(
      "circle", () -> new ParticleType<Circle_ParticleOptions>(false) {
         public MapCodec<Circle_ParticleOptions> codec() {
            return Circle_ParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, Circle_ParticleOptions> streamCodec() {
            return Circle_ParticleOptions.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<AfterImageParticleOptions>> AFTER_IMAGE = PARTICLE.register(
      "after_image", () -> new ParticleType<AfterImageParticleOptions>(false) {
         public MapCodec<AfterImageParticleOptions> codec() {
            return AfterImageParticleOptions.MAP_CODEC;
         }

         public StreamCodec<? super RegistryFriendlyByteBuf, AfterImageParticleOptions> streamCodec() {
            return AfterImageParticleOptions.STREAM_CODEC;
         }
      }
   );
}

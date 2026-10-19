package net.unusual.block_factorys_bosses.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;

public class BossesRiseParticleTypes {
   public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, "block_factorys_bosses");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOW_CLOUD = REGISTRY.register("snow_cloud", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOW_FLAKE = REGISTRY.register("snow_flake", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_DUST = REGISTRY.register("falling_dust", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_FLAME = REGISTRY.register(
      "blazing_flame", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FANCY_SMOKE = REGISTRY.register("fancy_smoke", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_DUST = REGISTRY.register("blazing_dust", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_CLOUD = REGISTRY.register(
      "blazing_cloud", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_SMOKE = REGISTRY.register("soul_smoke", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGICAL_DOT = REGISTRY.register("magical_dot", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_CLOUD = REGISTRY.register("soul_cloud", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_FALLING_DUST = REGISTRY.register(
      "black_falling_dust", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DUST_CLOUD = REGISTRY.register("dust_cloud", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GROUND_DUST = REGISTRY.register("ground_dust", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SAND_ROCK = REGISTRY.register("sand_rock", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> POISON_SPIT = REGISTRY.register("poison_spit", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INVUL_WISP = REGISTRY.register("invul_wisp", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INVUL_WISP_INACTIVE = REGISTRY.register(
      "invul_wisp_inactive", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ColorParticleOption>> PIXEL = REGISTRY.register(
      "pixel", () -> register(false, ColorParticleOption::codec, ColorParticleOption::streamCodec)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT = REGISTRY.register("rift", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT_OPEN = REGISTRY.register("rift_open", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT_CLOSE = REGISTRY.register("rift_close", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL = REGISTRY.register("soul", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_FLIP = REGISTRY.register("soul_flip", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BIG_STAR = REGISTRY.register("big_star", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STAR = REGISTRY.register("star", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WEIRD_SMOKE = REGISTRY.register("weird_smoke", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXPLOSION = REGISTRY.register("explosion", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BIG_EXPLOSION = REGISTRY.register(
      "big_explosion", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLINT = REGISTRY.register("glint", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMOKE = REGISTRY.register("smoke", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_SMOKE = REGISTRY.register("red_smoke", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_SPARK = REGISTRY.register("red_spark", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_1 = REGISTRY.register(
      "ice_whirlpool_1", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_2 = REGISTRY.register(
      "ice_whirlpool_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_3 = REGISTRY.register(
      "ice_whirlpool_3", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_4 = REGISTRY.register(
      "ice_whirlpool_4", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_5 = REGISTRY.register(
      "ice_whirlpool_5", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_SMOKE_IMPACT_1 = REGISTRY.register(
      "yeti_smoke_impact_1", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_SMOKE_IMPACT_2 = REGISTRY.register(
      "yeti_smoke_impact_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SOLID = REGISTRY.register(
      "yeti_death_solid", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SOFT = REGISTRY.register(
      "yeti_death_soft", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SHOCKWAVE = REGISTRY.register(
      "yeti_death_shockwave", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_LIGHT = REGISTRY.register(
      "yeti_death_light", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_FROST = REGISTRY.register(
      "yeti_death_frost", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_ICE_CHUNK = REGISTRY.register(
      "yeti_ice_chunk", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_AOE = REGISTRY.register("fire_aoe", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_CLOUD_SMOKE = REGISTRY.register(
      "fire_cloud_smoke", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_CLOUD = REGISTRY.register("fire_cloud", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RING = REGISTRY.register("ring", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_EVENT = REGISTRY.register(
      "introduction_attack_event", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_2 = REGISTRY.register(
      "introduction_attack_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_3 = REGISTRY.register(
      "introduction_attack_3", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_DUST = REGISTRY.register(
      "introduction_attack_dust", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOUL_ONCE = REGISTRY.register(
      "introduction_attack_soul_once", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS = REGISTRY.register(
      "introduction_attack_souls", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_2 = REGISTRY.register(
      "introduction_attack_souls_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_3 = REGISTRY.register(
      "introduction_attack_souls_3", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_3_DIED = REGISTRY.register(
      "introduction_attack_souls_3_died", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_DIED_2 = REGISTRY.register(
      "introduction_attack_souls_died_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_SMOKE_IMPACT_1 = REGISTRY.register(
      "knight_smoke_impact_1", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_SMOKE_IMPACT_2 = REGISTRY.register(
      "knight_smoke_impact_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_TRAIL = REGISTRY.register("soul_trail", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT = REGISTRY.register("mark_glint", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_2 = REGISTRY.register("mark_glint_2", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_3 = REGISTRY.register("mark_glint_3", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_EXP = REGISTRY.register(
      "mark_glint_exp", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_EXP_2 = REGISTRY.register(
      "mark_glint_exp_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_1 = REGISTRY.register(
      "phase_transition_1", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_2 = REGISTRY.register(
      "phase_transition_2", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_4 = REGISTRY.register(
      "phase_transition_4", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_6 = REGISTRY.register(
      "phase_transition_6", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_6_LOW = REGISTRY.register(
      "phase_transition_6_low", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_7 = REGISTRY.register(
      "phase_transition_7", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_8 = REGISTRY.register(
      "phase_transition_8", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_9 = REGISTRY.register(
      "phase_transition_9", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_10 = REGISTRY.register(
      "phase_transition_10", () -> new SimpleParticleType(false)
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> AREA_BUBBLING = registerBedrockParticle("area_bubbling");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> BITE_DEBRIS = registerBedrockParticle("bite_debris");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> BITE_DUST = registerBedrockParticle("bite_dust");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_DEBRIS = registerBedrockParticle("crate_debris");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_IMPACT_DUST = registerBedrockParticle(
      "crate_impact_dust"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_THROW_DUST = registerBedrockParticle(
      "crate_throw_dust"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM = registerBedrockParticle(
      "emergence_water_foam"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_BIG = registerBedrockParticle(
      "emergence_water_foam_big"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_GIANT = registerBedrockParticle(
      "emergence_water_foam_giant"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_MEDIUM = registerBedrockParticle(
      "emergence_water_foam_medium"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_GIANT = registerBedrockParticle(
      "emergence_water_giant"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> INK_FOG = registerBedrockParticle("ink_fog");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_HEAD_STARS = registerBedrockParticle(
      "kraken_head_stars"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_PIRATE_SUMMON = registerBedrockParticle(
      "kraken_pirate_summon"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_SPIT = registerBedrockParticle("kraken_spit");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DEBRIS = registerBedrockParticle("mast_debris");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DEBRIS_BIG = registerBedrockParticle("mast_debris_big");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DUST = registerBedrockParticle("mast_dust");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DUST_BIG = registerBedrockParticle("mast_dust_big");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MIST_CLOUD = registerBedrockParticle("mist_cloud");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> STRONG_SLAM_DEBRIS = registerBedrockParticle(
      "strong_slam_debris"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> STRONG_SLAM_DUST = registerBedrockParticle(
      "strong_slam_dust"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_EMERGENCE_SPLASH = registerBedrockParticle(
      "tentacle_emergence_splash"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TOWERING_MIST_CLOUD = registerBedrockParticle(
      "towering_mist_cloud"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_SPLASH_BIG = registerBedrockParticle(
      "transition_emergence_splash_big"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM = registerBedrockParticle(
      "transition_emergence_water_foam"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM_LONG = registerBedrockParticle(
      "transition_emergence_water_foam_long"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM_TENTACLE_LONG = registerBedrockParticle(
      "transition_emergence_water_foam_tentacle_long"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_MAST_DEBRIS = registerBedrockParticle(
      "transition_mast_debris"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_MAST_DUST = registerBedrockParticle(
      "transition_mast_dust"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_TENTACLE_EMERGENCE_SPLASH = registerBedrockParticle(
      "transition_tentacle_emergence_splash"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_WATER_BUBBLE = registerBedrockParticle(
      "transition_water_bubble"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_WATER_BUBBLE_FOAM = registerBedrockParticle(
      "transition_water_bubble_foam"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_BUBBLE_FOAM = registerBedrockParticle(
      "water_bubble_foam"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_BURST = registerBedrockParticle("water_burst");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_FOAM = registerBedrockParticle("water_foam");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_SPLASH = registerBedrockParticle("water_splash");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WOOD_DEBRIS = registerBedrockParticle("wood_debris");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WOOD_DUST = registerBedrockParticle("wood_dust");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_AOE = registerBedrockParticle("poison_aoe");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_BUBBLE = registerBedrockParticle("poison_bubble");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_FOG = registerBedrockParticle("poison_fog");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_FOG_PUDDLE = registerBedrockParticle(
      "poison_fog_puddle"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_BUBBLES_LOOP = registerBedrockParticle(
      "slam_bubbles_loop"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_DROPS_LOOP = registerBedrockParticle("slam_drops_loop");
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SPLASH_LOOP = registerBedrockParticle(
      "slam_splash_loop"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_WHIRLPOOL_LOOP = registerBedrockParticle(
      "slam_whirlpool_loop"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_BUBBLES_RELEASE = registerBedrockParticle(
      "slam_bubbles_release"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_DROPS_RELEASE = registerBedrockParticle(
      "slam_drops_release"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SMALL_SPLASH_RELEASE = registerBedrockParticle(
      "slam_small_splash_release"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SPLASH_RELEASE = registerBedrockParticle(
      "slam_splash_release"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_WHIRLPOOL_RELEASE = registerBedrockParticle(
      "slam_whirlpool_release"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SHOCKWAVE_ACTIVATION = registerBedrockParticle(
      "tentacle_slam_shockwave_activation"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SMALL_WATER_ACTIVATION = registerBedrockParticle(
      "tentacle_slam_small_water_activation"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SPLASH_ACTIVATION_MAIN = registerBedrockParticle(
      "tentacle_slam_splash_activation_main"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_TALL_WATER_ACTIVATION = registerBedrockParticle(
      "tentacle_slam_tall_water_activation"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_WHITE_WATER_ACTIVATION = registerBedrockParticle(
      "tentacle_slam_white_water_activation"
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> FOG_1 = registerBedrockParticle("fog_1");
   public static final int PIXEL_COLOR_DEFAULT = 62969;
   public static final int PIXEL_COLOR_INACTIVE = 16740689;

   private static <T extends ParticleOptions> ParticleType<T> register(
      boolean overrideLimiter,
      final Function<ParticleType<T>, MapCodec<T>> codecGetter,
      final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecGetter
   ) {
      return new ParticleType<T>(overrideLimiter) {
         @Nonnull
         public MapCodec<T> codec() {
            return codecGetter.apply(this);
         }

         @Nonnull
         public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
            return streamCodecGetter.apply(this);
         }
      };
   }

   private static DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> registerBedrockParticle(String name) {
      return REGISTRY.register(name, () -> register(false, BedrockEmitterParticleOptions::codec, BedrockEmitterParticleOptions::streamCodec));
   }
}

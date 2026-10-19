package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.particle.types.AdvancedParticleType;
import com.bobmowzie.mowziesmobs.client.particle.types.DecalParticleType;
import com.bobmowzie.mowziesmobs.client.particle.types.RibbonParticleType;
import com.bobmowzie.mowziesmobs.client.particle.types.TerrainParticleType;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ParticleHandler {
   public static final DeferredRegister<ParticleType<?>> REG = DeferredRegister.create(Registries.PARTICLE_TYPE, "mowziesmobs");
   public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKLE = REG.register("sparkle", () -> new SimpleParticleType(false));
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParticleVanillaCloudExtended.Data>> VANILLA_CLOUD_EXTENDED = REG.register(
      "vanilla_cloud_extended", () -> new ParticleType<ParticleVanillaCloudExtended.Data>(false) {
         @NotNull
         public MapCodec<ParticleVanillaCloudExtended.Data> codec() {
            return ParticleVanillaCloudExtended.Data.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, ParticleVanillaCloudExtended.Data> streamCodec() {
            return ParticleVanillaCloudExtended.Data.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParticleSnowFlake.Data>> SNOWFLAKE = REG.register(
      "snowflake", () -> new ParticleType<ParticleSnowFlake.Data>(false) {
         @NotNull
         public MapCodec<ParticleSnowFlake.Data> codec() {
            return ParticleSnowFlake.Data.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, ParticleSnowFlake.Data> streamCodec() {
            return ParticleSnowFlake.Data.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParticleCloud.Data>> CLOUD = REG.register(
      "cloud_soft", () -> new ParticleType<ParticleCloud.Data>(false) {
         @NotNull
         public MapCodec<ParticleCloud.Data> codec() {
            return ParticleCloud.Data.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, ParticleCloud.Data> streamCodec() {
            return ParticleCloud.Data.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParticleOrb.Data>> ORB = REG.register(
      "orb_0", () -> new ParticleType<ParticleOrb.Data>(false) {
         @NotNull
         public MapCodec<ParticleOrb.Data> codec() {
            return ParticleOrb.Data.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, ParticleOrb.Data> streamCodec() {
            return ParticleOrb.Data.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<ParticleRing.Data>> RING = REG.register(
      "ring_0", () -> new ParticleType<ParticleRing.Data>(false) {
         @NotNull
         public MapCodec<ParticleRing.Data> codec() {
            return ParticleRing.Data.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, ParticleRing.Data> streamCodec() {
            return ParticleRing.Data.STREAM_CODEC;
         }
      }
   );
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> RING2 = registerAdvanced("ring");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> RING_BIG = registerAdvanced("ring_big");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> PIXEL = registerAdvanced("pixel");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> ORB2 = registerAdvanced("orb");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> EYE = registerAdvanced("eye");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> BUBBLE = registerAdvanced("bubble");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> SUN = registerAdvanced("sun");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> SUN_NOVA = registerAdvanced("sun_nova");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> FLARE = registerAdvanced("flare");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> FLARE_RADIAL = registerAdvanced("flare_radial");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> BURST_IN = registerAdvanced("ring1");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> BURST_MESSY = registerAdvanced("burst_messy");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> RING_SPARKS = registerAdvanced("sparks_ring");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> BURST_OUT = registerAdvanced("ring2");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> GLOW = registerAdvanced("glow");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> ARROW_HEAD = registerAdvanced("arrow_head");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> LEAF = registerAdvanced("leaf");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> MOON_FULL = registerAdvanced("moon_full");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> MOON_GIBBOUS = registerAdvanced("moon_gibbous");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> MOON_HALF = registerAdvanced("moon_half");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> MOON_CRESCENT = registerAdvanced("moon_crescent");
   public static final DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> MOON_NEW = registerAdvanced("moon_new");
   public static final DeferredHolder<ParticleType<?>, ParticleType<TerrainParticleType>> TERRAIN = registerTerrain("terrain");
   public static final DeferredHolder<ParticleType<?>, ParticleType<DecalParticleType>> PLAYER_FOOTPRINT = registerDecal("player_footprint");
   public static final DeferredHolder<ParticleType<?>, ParticleType<DecalParticleType>> STRIX_FOOTPRINT = registerDecal("strix_footprint");
   public static final DeferredHolder<ParticleType<?>, ParticleType<DecalParticleType>> GROUND_CRACK = registerDecal("crack");
   public static final DeferredHolder<ParticleType<?>, ParticleType<RibbonParticleType>> RIBBON_FLAT = registerRibbon("ribbon_flat");
   public static final DeferredHolder<ParticleType<?>, ParticleType<RibbonParticleType>> RIBBON_STREAKS = registerRibbon("ribbon_streaks");
   public static final DeferredHolder<ParticleType<?>, ParticleType<RibbonParticleType>> RIBBON_GLOW = registerRibbon("ribbon_glow");
   public static final DeferredHolder<ParticleType<?>, ParticleType<RibbonParticleType>> RIBBON_SQUIGGLE = registerRibbon("ribbon_squiggle");

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)SPARKLE.get(), ParticleSparkle.Provider::new);
      event.registerSpriteSet((ParticleType)VANILLA_CLOUD_EXTENDED.get(), ParticleVanillaCloudExtended.Provider::new);
      event.registerSpriteSet((ParticleType)SNOWFLAKE.get(), ParticleSnowFlake.Provider::new);
      event.registerSpriteSet((ParticleType)CLOUD.get(), ParticleCloud.Provider::new);
      event.registerSpriteSet((ParticleType)ORB.get(), ParticleOrb.Provider::new);
      event.registerSpriteSet((ParticleType)RING.get(), ParticleRing.Provider::new);
      event.registerSpriteSet((ParticleType)RING2.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)RING_BIG.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)PIXEL.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)ORB2.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)EYE.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)BUBBLE.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)SUN.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)SUN_NOVA.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)FLARE.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)FLARE_RADIAL.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)BURST_IN.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)BURST_MESSY.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)RING_SPARKS.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)BURST_OUT.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)GLOW.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)ARROW_HEAD.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)LEAF.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)TERRAIN.get(), AdvancedTerrainParticle.Factory::new);
      event.registerSpriteSet((ParticleType)MOON_FULL.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)MOON_GIBBOUS.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)MOON_HALF.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)MOON_CRESCENT.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)MOON_NEW.get(), AdvancedParticleBase.Factory::new);
      event.registerSpriteSet((ParticleType)STRIX_FOOTPRINT.get(), ParticleDecal.Provider::new);
      event.registerSpriteSet((ParticleType)GROUND_CRACK.get(), ParticleDecal.Provider::new);
      event.registerSpriteSet((ParticleType)PLAYER_FOOTPRINT.get(), ParticleDecal.Provider::new);
      event.registerSpriteSet((ParticleType)RIBBON_FLAT.get(), ParticleRibbon.Provider::new);
      event.registerSpriteSet((ParticleType)RIBBON_STREAKS.get(), ParticleRibbon.Provider::new);
      event.registerSpriteSet((ParticleType)RIBBON_GLOW.get(), ParticleRibbon.Provider::new);
      event.registerSpriteSet((ParticleType)RIBBON_SQUIGGLE.get(), ParticleRibbon.Provider::new);
   }

   private static DeferredHolder<ParticleType<?>, ParticleType<AdvancedParticleType>> registerAdvanced(String key) {
      return REG.register(key, location -> new ParticleType<AdvancedParticleType>(false) {
         @NotNull
         public MapCodec<AdvancedParticleType> codec() {
            return AdvancedParticleType.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, AdvancedParticleType> streamCodec() {
            return AdvancedParticleType.STREAM_CODEC;
         }
      });
   }

   private static DeferredHolder<ParticleType<?>, ParticleType<DecalParticleType>> registerDecal(String key) {
      return REG.register(key, location -> new ParticleType<DecalParticleType>(false) {
         @NotNull
         public MapCodec<DecalParticleType> codec() {
            return DecalParticleType.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, DecalParticleType> streamCodec() {
            return DecalParticleType.STREAM_CODEC;
         }
      });
   }

   private static DeferredHolder<ParticleType<?>, ParticleType<RibbonParticleType>> registerRibbon(String key) {
      return REG.register(key, location -> new ParticleType<RibbonParticleType>(false) {
         @NotNull
         public MapCodec<RibbonParticleType> codec() {
            return RibbonParticleType.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, RibbonParticleType> streamCodec() {
            return RibbonParticleType.STREAM_CODEC;
         }
      });
   }

   private static DeferredHolder<ParticleType<?>, ParticleType<TerrainParticleType>> registerTerrain(String key) {
      return REG.register(key, location -> new ParticleType<TerrainParticleType>(false) {
         @NotNull
         public MapCodec<TerrainParticleType> codec() {
            return TerrainParticleType.CODEC;
         }

         @NotNull
         public StreamCodec<? super RegistryFriendlyByteBuf, TerrainParticleType> streamCodec() {
            return TerrainParticleType.STREAM_CODEC;
         }
      });
   }
}

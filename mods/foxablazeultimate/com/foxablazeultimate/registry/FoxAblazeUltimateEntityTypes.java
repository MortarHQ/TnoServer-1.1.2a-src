package com.foxablazeultimate.registry;

import com.foxablazeultimate.entity.HellSniperBeamEntity;
import com.foxablazeultimate.entity.HellSniperCircleEntity;
import com.foxablazeultimate.entity.VeldoraBlackMistProjectile;
import com.foxablazeultimate.entity.YogTimeStopWaveEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FoxAblazeUltimateEntityTypes {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "foxablazeultimate");
   public static final DeferredHolder<EntityType<?>, EntityType<VeldoraBlackMistProjectile>> VELDORA_BLACK_MIST = ENTITY_TYPES.register(
      "veldora_black_mist",
      () -> Builder.of(VeldoraBlackMistProjectile::new, MobCategory.MISC)
         .sized(15.0F, 15.0F)
         .clientTrackingRange(64)
         .build(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "veldora_black_mist").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HellSniperCircleEntity>> HELL_SNIPER_CIRCLE = ENTITY_TYPES.register(
      "hell_sniper_circle",
      () -> Builder.of(HellSniperCircleEntity::new, MobCategory.MISC)
         .sized(0.1F, 0.1F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .build(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "hell_sniper_circle").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<HellSniperBeamEntity>> HELL_SNIPER_BEAM = ENTITY_TYPES.register(
      "hell_sniper_beam",
      () -> Builder.of(HellSniperBeamEntity::new, MobCategory.MISC)
         .sized(0.08F, 0.08F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .build(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "hell_sniper_beam").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<YogTimeStopWaveEntity>> YOG_TIME_STOP_WAVE = ENTITY_TYPES.register(
      "yog_time_stop_wave",
      () -> Builder.of(YogTimeStopWaveEntity::new, MobCategory.MISC)
         .sized(0.1F, 0.1F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .build(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "yog_time_stop_wave").toString())
   );

   private FoxAblazeUltimateEntityTypes() {
   }

   public static void register(IEventBus modBus) {
      ENTITY_TYPES.register(modBus);
   }
}

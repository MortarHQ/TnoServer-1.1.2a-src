package com.github.wal_bos.moreskills.registry.entity;

import com.github.wal_bos.moreskills.entity.michael.MichaelMeteorEntity;
import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonKnightEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonNovariTransitionEntity;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAngelEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayEntity;
import com.github.wal_bos.moreskills.world.entity.MichaelManasEntity;
import com.github.wal_bos.moreskills.world.entity.TheCoordinateEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "tensuramoreskills");
   public static final DeferredHolder<EntityType<?>, EntityType<FeldwayEntity>> FELDWAY = ENTITIES.register(
      "feldway",
      () -> Builder.of(FeldwayEntity::new, MobCategory.MONSTER)
         .sized(0.9F, 2.9F)
         .clientTrackingRange(96)
         .updateInterval(2)
         .fireImmune()
         .build("tensuramoreskills:feldway")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AvalonKnightEntity>> AVALON_KNIGHT = ENTITIES.register(
      "avalon_knight",
      () -> Builder.of(AvalonKnightEntity::new, MobCategory.CREATURE)
         .sized(0.6F, 1.8F)
         .clientTrackingRange(128)
         .updateInterval(2)
         .fireImmune()
         .build("tensuramoreskills:avalon_knight")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MichaelMeteorEntity>> MICHAEL_METEOR = ENTITIES.register(
      "michael_meteor",
      () -> Builder.of(MichaelMeteorEntity::new, MobCategory.MISC)
         .sized(1.4F, 1.4F)
         .clientTrackingRange(512)
         .updateInterval(1)
         .build("tensuramoreskills:michael_meteor")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ArsWeissCloneEntity>> ARS_WEISS_CLONE = ENTITIES.register(
      "ars_weiss_clone",
      () -> Builder.of(ArsWeissCloneEntity::new, MobCategory.MISC)
         .sized(0.6F, 1.8F)
         .clientTrackingRange(64)
         .updateInterval(2)
         .build("tensuramoreskills:ars_weiss_clone")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FeldwayAngelEntity>> FELDWAY_ANGEL = ENTITIES.register(
      "feldway_angel",
      () -> Builder.of(FeldwayAngelEntity::new, MobCategory.MONSTER)
         .sized(0.7F, 2.1F)
         .clientTrackingRange(96)
         .updateInterval(2)
         .fireImmune()
         .build("tensuramoreskills:feldway_angel")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MichaelManasEntity>> MICHAEL_MANAS = ENTITIES.register(
      "michael_manas",
      () -> Builder.of(MichaelManasEntity::new, MobCategory.MONSTER)
         .sized(0.8F, 2.4F)
         .clientTrackingRange(96)
         .updateInterval(2)
         .fireImmune()
         .build("tensuramoreskills:michael_manas")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FeldwayAwakeningEntity>> FELDWAY_AWAKENING = ENTITIES.register(
      "feldway_awakening",
      () -> Builder.of(FeldwayAwakeningEntity::new, MobCategory.MONSTER)
         .sized(0.9F, 2.9F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .fireImmune()
         .build("tensuramoreskills:feldway_awakening")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FeldwayAwakenedEntity>> FELDWAY_AWAKENED = ENTITIES.register(
      "feldway_awakened",
      () -> Builder.of(FeldwayAwakenedEntity::new, MobCategory.MONSTER)
         .sized(1.05F, 3.2F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .fireImmune()
         .build("tensuramoreskills:feldway_awakened")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TheCoordinateEntity>> THE_COORDINATE = ENTITIES.register(
      "the_coordinate",
      () -> Builder.of(TheCoordinateEntity::new, MobCategory.MISC)
         .sized(0.8F, 3.0F)
         .clientTrackingRange(512)
         .updateInterval(1)
         .build("tensuramoreskills:the_coordinate")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AvalonNovariEntity>> AVALON_NOVARI = ENTITIES.register(
      "avalon_novari",
      () -> Builder.of(AvalonNovariEntity::new, MobCategory.MONSTER)
         .sized(0.8F, 2.5F)
         .clientTrackingRange(160)
         .updateInterval(1)
         .fireImmune()
         .build("tensuramoreskills:avalon_novari")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AvalonNovariTransitionEntity>> AVALON_NOVARI_TRANSITION = ENTITIES.register(
      "avalon_novari_transition",
      () -> Builder.of(AvalonNovariTransitionEntity::new, MobCategory.MONSTER)
         .sized(0.8F, 2.5F)
         .clientTrackingRange(192)
         .updateInterval(1)
         .fireImmune()
         .build("tensuramoreskills:avalon_novari_transition")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BerettaEntity>> BERETTA = ENTITIES.register(
      "beretta",
      () -> Builder.of(BerettaEntity::new, MobCategory.MONSTER)
         .sized(1.65F, 4.35F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .fireImmune()
         .build("tensuramoreskills:beretta")
   );

   public static void init(IEventBus modBus) {
      ENTITIES.register(modBus);
   }
}

package net.witherspawnanimation.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneXEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneZEntity;
import net.witherspawnanimation.entity.AnimatedAltarMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilXEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormZEntity;
import net.witherspawnanimation.entity.AnimatedAltarXEntity;
import net.witherspawnanimation.entity.AnimatedAltarZEntity;

@EventBusSubscriber(bus = Bus.MOD)
public class WitherSpawnAnimationModEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, "wither_spawn_animation");
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarZEntity>> ANIMATED_ALTAR_Z = register(
      "animated_altar_z",
      Builder.of(AnimatedAltarZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarMinZEntity>> ANIMATED_ALTAR_MIN_Z = register(
      "animated_altar_min_z",
      Builder.of(AnimatedAltarMinZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarXEntity>> ANIMATED_ALTAR_X = register(
      "animated_altar_x",
      Builder.of(AnimatedAltarXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarMinXEntity>> ANIMATED_ALTAR_MIN_X = register(
      "animated_altar_min_x",
      Builder.of(AnimatedAltarMinXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarSoilZEntity>> ANIMATED_ALTAR_SOIL_Z = register(
      "animated_altar_soil_z",
      Builder.of(AnimatedAltarSoilZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarSoilMinZEntity>> ANIMATED_ALTAR_SOIL_MIN_Z = register(
      "animated_altar_soil_min_z",
      Builder.of(AnimatedAltarSoilMinZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarSoilXEntity>> ANIMATED_ALTAR_SOIL_X = register(
      "animated_altar_soil_x",
      Builder.of(AnimatedAltarSoilXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarSoilMinXEntity>> ANIMATED_ALTAR_SOIL_MIN_X = register(
      "animated_altar_soil_min_x",
      Builder.of(AnimatedAltarSoilMinXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormZEntity>> ANIMATED_ALTAR_STORM_Z = register(
      "animated_altar_storm_z",
      Builder.of(AnimatedAltarStormZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormMinZEntity>> ANIMATED_ALTAR_STORM_MIN_Z = register(
      "animated_altar_storm_min_z",
      Builder.of(AnimatedAltarStormMinZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormXEntity>> ANIMATED_ALTAR_STORM_X = register(
      "animated_altar_storm_x",
      Builder.of(AnimatedAltarStormXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormMinXEntity>> ANIMATED_ALTAR_STORM_MIN_X = register(
      "animated_altar_storm_min_x",
      Builder.of(AnimatedAltarStormMinXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarBygoneZEntity>> ANIMATED_ALTAR_BYGONE_Z = register(
      "animated_altar_bygone_z",
      Builder.of(AnimatedAltarBygoneZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarBygoneMinZEntity>> ANIMATED_ALTAR_BYGONE_MIN_Z = register(
      "animated_altar_bygone_min_z",
      Builder.of(AnimatedAltarBygoneMinZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarBygoneXEntity>> ANIMATED_ALTAR_BYGONE_X = register(
      "animated_altar_bygone_x",
      Builder.of(AnimatedAltarBygoneXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarBygoneMinXEntity>> ANIMATED_ALTAR_BYGONE_MIN_X = register(
      "animated_altar_bygone_min_x",
      Builder.of(AnimatedAltarBygoneMinXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormSoilZEntity>> ANIMATED_ALTAR_STORM_SOIL_Z = register(
      "animated_altar_storm_soil_z",
      Builder.of(AnimatedAltarStormSoilZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormSoilMinZEntity>> ANIMATED_ALTAR_STORM_SOIL_MIN_Z = register(
      "animated_altar_storm_soil_min_z",
      Builder.of(AnimatedAltarStormSoilMinZEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormSoilXEntity>> ANIMATED_ALTAR_STORM_SOIL_X = register(
      "animated_altar_storm_soil_x",
      Builder.of(AnimatedAltarStormSoilXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AnimatedAltarStormSoilMinXEntity>> ANIMATED_ALTAR_STORM_SOIL_MIN_X = register(
      "animated_altar_storm_soil_min_x",
      Builder.of(AnimatedAltarStormSoilMinXEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .fireImmune()
         .sized(3.0F, 4.0F)
   );

   private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
   }

   @SubscribeEvent
   public static void init(RegisterSpawnPlacementsEvent event) {
      AnimatedAltarZEntity.init(event);
      AnimatedAltarMinZEntity.init(event);
      AnimatedAltarXEntity.init(event);
      AnimatedAltarMinXEntity.init(event);
      AnimatedAltarSoilZEntity.init(event);
      AnimatedAltarSoilMinZEntity.init(event);
      AnimatedAltarSoilXEntity.init(event);
      AnimatedAltarSoilMinXEntity.init(event);
      AnimatedAltarStormZEntity.init(event);
      AnimatedAltarStormMinZEntity.init(event);
      AnimatedAltarStormXEntity.init(event);
      AnimatedAltarStormMinXEntity.init(event);
      AnimatedAltarBygoneZEntity.init(event);
      AnimatedAltarBygoneMinZEntity.init(event);
      AnimatedAltarBygoneXEntity.init(event);
      AnimatedAltarBygoneMinXEntity.init(event);
      AnimatedAltarStormSoilZEntity.init(event);
      AnimatedAltarStormSoilMinZEntity.init(event);
      AnimatedAltarStormSoilXEntity.init(event);
      AnimatedAltarStormSoilMinXEntity.init(event);
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)ANIMATED_ALTAR_Z.get(), AnimatedAltarZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_MIN_Z.get(), AnimatedAltarMinZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_X.get(), AnimatedAltarXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_MIN_X.get(), AnimatedAltarMinXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_SOIL_Z.get(), AnimatedAltarSoilZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_SOIL_MIN_Z.get(), AnimatedAltarSoilMinZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_SOIL_X.get(), AnimatedAltarSoilXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_SOIL_MIN_X.get(), AnimatedAltarSoilMinXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_Z.get(), AnimatedAltarStormZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_MIN_Z.get(), AnimatedAltarStormMinZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_X.get(), AnimatedAltarStormXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_MIN_X.get(), AnimatedAltarStormMinXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_BYGONE_Z.get(), AnimatedAltarBygoneZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_BYGONE_MIN_Z.get(), AnimatedAltarBygoneMinZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_BYGONE_X.get(), AnimatedAltarBygoneXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_BYGONE_MIN_X.get(), AnimatedAltarBygoneMinXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_SOIL_Z.get(), AnimatedAltarStormSoilZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_SOIL_MIN_Z.get(), AnimatedAltarStormSoilMinZEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_SOIL_X.get(), AnimatedAltarStormSoilXEntity.createAttributes().build());
      event.put((EntityType)ANIMATED_ALTAR_STORM_SOIL_MIN_X.get(), AnimatedAltarStormSoilMinXEntity.createAttributes().build());
   }
}

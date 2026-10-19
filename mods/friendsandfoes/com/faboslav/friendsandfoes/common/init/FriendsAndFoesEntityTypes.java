package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.common.entity.IllusionerEntity;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityTypeResourceId;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class FriendsAndFoesEntityTypes {
   public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, "friendsandfoes");
   public static boolean previousUseChoiceTypeRegistrations = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
   public static final RegistryEntry<EntityType<CopperGolemEntity>> COPPER_GOLEM = ENTITY_TYPES.register(
      "copper_golem",
      () -> Builder.of(CopperGolemEntity::new, MobCategory.MISC)
         .sized(0.75F, 1.375F)
         .eyeHeight(0.75F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("copper_golem"))
   );
   public static final RegistryEntry<EntityType<CrabEntity>> CRAB = ENTITY_TYPES.register(
      "crab",
      () -> Builder.of(CrabEntity::new, MobCategory.CREATURE)
         .sized(0.875F, 0.5625F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("crab"))
   );
   public static final RegistryEntry<EntityType<GlareEntity>> GLARE = ENTITY_TYPES.register(
      "glare",
      () -> Builder.of(GlareEntity::new, CustomSpawnGroup.getGlaresCategory())
         .sized(0.875F, 1.1875F)
         .clientTrackingRange(8)
         .updateInterval(2)
         .build(VersionedEntityTypeResourceId.create("glare"))
   );
   public static final RegistryEntry<EntityType<IceologerEntity>> ICEOLOGER = ENTITY_TYPES.register(
      "iceologer",
      () -> Builder.of(IceologerEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("iceologer"))
   );
   public static final RegistryEntry<EntityType<IllusionerEntity>> ILLUSIONER = ENTITY_TYPES.register(
      "illusioner",
      () -> Builder.of(IllusionerEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .passengerAttachments(new float[]{2.0F})
         .ridingOffset(-0.6F)
         .clientTrackingRange(8)
         .build(VersionedEntityTypeResourceId.create("illusioner"))
   );
   public static final RegistryEntry<EntityType<IceologerIceChunkEntity>> ICE_CHUNK = ENTITY_TYPES.register(
      "ice_chunk",
      () -> Builder.of(IceologerIceChunkEntity::new, MobCategory.MISC)
         .fireImmune()
         .sized(2.5F, 1.0F)
         .clientTrackingRange(6)
         .build(VersionedEntityTypeResourceId.create("ice_chunk"))
   );
   public static final RegistryEntry<EntityType<MaulerEntity>> MAULER = ENTITY_TYPES.register(
      "mauler",
      () -> Builder.of(MaulerEntity::new, MobCategory.CREATURE)
         .sized(0.5625F, 0.5625F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("mauler"))
   );
   public static final RegistryEntry<EntityType<MoobloomEntity>> MOOBLOOM = ENTITY_TYPES.register(
      "moobloom",
      () -> Builder.of(MoobloomEntity::new, MobCategory.CREATURE)
         .sized(0.9F, 1.4F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("moobloom"))
   );
   public static final RegistryEntry<EntityType<RascalEntity>> RASCAL = ENTITY_TYPES.register(
      "rascal",
      () -> Builder.of(RascalEntity::new, CustomSpawnGroup.getRascalsCategory())
         .sized(0.9F, 1.25F)
         .clientTrackingRange(10)
         .canSpawnFarFromPlayer()
         .build(VersionedEntityTypeResourceId.create("rascal"))
   );
   public static final RegistryEntry<EntityType<TuffGolemEntity>> TUFF_GOLEM = ENTITY_TYPES.register(
      "tuff_golem",
      () -> Builder.of(TuffGolemEntity::new, MobCategory.MISC)
         .sized(0.75F, 1.0625F)
         .eyeHeight(0.8F)
         .clientTrackingRange(10)
         .build(VersionedEntityTypeResourceId.create("tuff_golem"))
   );
   public static final RegistryEntry<EntityType<WildfireEntity>> WILDFIRE = ENTITY_TYPES.register(
      "wildfire",
      () -> Builder.of(WildfireEntity::new, MobCategory.MONSTER)
         .sized(1.05F, 2.8125F)
         .clientTrackingRange(10)
         .fireImmune()
         .build(VersionedEntityTypeResourceId.create("wildfire"))
   );
   public static final RegistryEntry<EntityType<PlayerIllusionEntity>> PLAYER_ILLUSION = ENTITY_TYPES.register(
      "player_illusion",
      () -> Builder.of(PlayerIllusionEntity::new, MobCategory.MISC)
         .sized(0.7F, 1.875F)
         .clientTrackingRange(10)
         .fireImmune()
         .build(VersionedEntityTypeResourceId.create("player_illusion"))
   );

   public static void registerEntityAttributes(RegisterEntityAttributesEvent event) {
      event.register((EntityType<? extends LivingEntity>)COPPER_GOLEM.get(), CopperGolemEntity.createCopperGolemAttributes());
      event.register((EntityType<? extends LivingEntity>)CRAB.get(), CrabEntity.createCrabAttributes());
      event.register((EntityType<? extends LivingEntity>)GLARE.get(), GlareEntity.createGlareAttributes());
      event.register((EntityType<? extends LivingEntity>)ICEOLOGER.get(), IceologerEntity.createIceologerAttributes());
      event.register((EntityType<? extends LivingEntity>)ILLUSIONER.get(), IllusionerEntity.createAttributes());
      event.register((EntityType<? extends LivingEntity>)MAULER.get(), MaulerEntity.createMaulerAttributes());
      event.register((EntityType<? extends LivingEntity>)MOOBLOOM.get(), MoobloomEntity.createAttributes());
      event.register((EntityType<? extends LivingEntity>)RASCAL.get(), RascalEntity.createRascalAttributes());
      event.register((EntityType<? extends LivingEntity>)TUFF_GOLEM.get(), TuffGolemEntity.createTuffGolemAttributes());
      event.register((EntityType<? extends LivingEntity>)WILDFIRE.get(), WildfireEntity.createWildfireAttributes());
      event.register((EntityType<? extends LivingEntity>)PLAYER_ILLUSION.get(), PlayerIllusionEntity.createPlayerIllusionAttributes());
   }

   public static void registerEntitySpawnRestrictions(RegisterEntitySpawnRestrictionsEvent event) {
      event.register((EntityType)CRAB.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
      event.register((EntityType)GLARE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
      event.register(
         (EntityType)ICEOLOGER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules
      );
      event.register(
         (EntityType)ILLUSIONER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules
      );
      event.register((EntityType)MAULER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
      event.register((EntityType)MOOBLOOM.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
      event.register((EntityType)RASCAL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, RascalEntity::canSpawn);
   }

   public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
      FriendsAndFoesConfig config = FriendsAndFoes.getConfig();
      if (config.enableCrab && config.enableCrabSpawn) {
         event.add(
            FriendsAndFoesTags.HAS_CRAB,
            MobCategory.CREATURE,
            (EntityType<?>)CRAB.get(),
            config.crabSpawnWeight,
            config.crabSpawnMinGroupSize,
            config.crabSpawnMaxGroupSize
         );
      }

      if (config.enableGlare && config.enableGlareSpawn) {
         event.add(
            FriendsAndFoesTags.HAS_GLARE,
            CustomSpawnGroup.getGlaresCategory(),
            (EntityType<?>)GLARE.get(),
            config.glareSpawnWeight,
            config.glareSpawnMinGroupSize,
            config.glareSpawnMaxGroupSize
         );
      }

      if (config.enableMauler && config.enableMaulerSpawn) {
         if (config.enableMaulerSpawnInBadlands) {
            event.add(
               FriendsAndFoesTags.HAS_BADLANDS_MAULER,
               MobCategory.CREATURE,
               (EntityType<?>)MAULER.get(),
               config.maulerBadlandsSpawnWeight,
               config.maulerBadlandsSpawnMinGroupSize,
               config.maulerBadlandsSpawnMaxGroupSize
            );
         }

         if (config.enableMaulerSpawnInDesert) {
            event.add(
               FriendsAndFoesTags.HAS_DESERT_MAULER,
               MobCategory.CREATURE,
               (EntityType<?>)MAULER.get(),
               config.maulerDesertSpawnWeight,
               config.maulerDesertSpawnMinGroupSize,
               config.maulerDesertSpawnMaxGroupSize
            );
         }

         if (config.enableMaulerSpawnInSavanna) {
            event.add(
               FriendsAndFoesTags.HAS_SAVANNA_MAULER,
               MobCategory.CREATURE,
               (EntityType<?>)MAULER.get(),
               config.maulerSavannaSpawnWeight,
               config.maulerSavannaSpawnMinGroupSize,
               config.maulerSavannaSpawnMaxGroupSize
            );
         }
      }

      if (config.enableMoobloom && config.enableMoobloomSpawn) {
         event.add(
            FriendsAndFoesTags.HAS_MOOBLOOMS,
            MobCategory.CREATURE,
            (EntityType<?>)MOOBLOOM.get(),
            config.moobloomSpawnWeight,
            config.moobloomSpawnMinGroupSize,
            config.moobloomSpawnMaxGroupSize
         );
      }

      if (config.enableRascal && config.enableRascalSpawn) {
         event.add(
            FriendsAndFoesTags.HAS_RASCAL,
            CustomSpawnGroup.getRascalsCategory(),
            (EntityType<?>)RASCAL.get(),
            config.rascalSpawnWeight,
            config.rascalSpawnMinGroupSize,
            config.rascalSpawnMaxGroupSize
         );
      }
   }

   private FriendsAndFoesEntityTypes() {
   }

   static {
      SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
      SharedConstants.CHECK_DATA_FIXER_SCHEMA = previousUseChoiceTypeRegistrations;
   }
}

package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.AmphithereArrowEntity;
import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.entity.ChainTieEntity;
import com.iafenvoy.iceandfire.entity.CockatriceEggEntity;
import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import com.iafenvoy.iceandfire.entity.CyclopsEntity;
import com.iafenvoy.iceandfire.entity.CyclopsEyeEntity;
import com.iafenvoy.iceandfire.entity.DeathWormEggEntity;
import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import com.iafenvoy.iceandfire.entity.DragonArrowEntity;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.DragonEggEntity;
import com.iafenvoy.iceandfire.entity.DragonPartEntity;
import com.iafenvoy.iceandfire.entity.DragonSkullEntity;
import com.iafenvoy.iceandfire.entity.DreadBeastEntity;
import com.iafenvoy.iceandfire.entity.DreadGhoulEntity;
import com.iafenvoy.iceandfire.entity.DreadHorseEntity;
import com.iafenvoy.iceandfire.entity.DreadKnightEntity;
import com.iafenvoy.iceandfire.entity.DreadLichEntity;
import com.iafenvoy.iceandfire.entity.DreadLichSkullEntity;
import com.iafenvoy.iceandfire.entity.DreadScuttlerEntity;
import com.iafenvoy.iceandfire.entity.DreadThrallEntity;
import com.iafenvoy.iceandfire.entity.FireDragonChargeEntity;
import com.iafenvoy.iceandfire.entity.FireDragonEntity;
import com.iafenvoy.iceandfire.entity.GhostEntity;
import com.iafenvoy.iceandfire.entity.GhostSwordEntity;
import com.iafenvoy.iceandfire.entity.GorgonEntity;
import com.iafenvoy.iceandfire.entity.HippocampusEntity;
import com.iafenvoy.iceandfire.entity.HippogryphEggEntity;
import com.iafenvoy.iceandfire.entity.HippogryphEntity;
import com.iafenvoy.iceandfire.entity.HydraArrowEntity;
import com.iafenvoy.iceandfire.entity.HydraBreathEntity;
import com.iafenvoy.iceandfire.entity.HydraEntity;
import com.iafenvoy.iceandfire.entity.HydraHeadEntity;
import com.iafenvoy.iceandfire.entity.IceDragonChargeEntity;
import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import com.iafenvoy.iceandfire.entity.LightningDragonChargeEntity;
import com.iafenvoy.iceandfire.entity.LightningDragonEntity;
import com.iafenvoy.iceandfire.entity.MobSkullEntity;
import com.iafenvoy.iceandfire.entity.PixieChargeEntity;
import com.iafenvoy.iceandfire.entity.PixieEntity;
import com.iafenvoy.iceandfire.entity.SeaSerpentArrowEntity;
import com.iafenvoy.iceandfire.entity.SeaSerpentBubblesEntity;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.entity.SirenEntity;
import com.iafenvoy.iceandfire.entity.SlowPartEntity;
import com.iafenvoy.iceandfire.entity.StoneStatueEntity;
import com.iafenvoy.iceandfire.entity.StymphalianArrowEntity;
import com.iafenvoy.iceandfire.entity.StymphalianBirdEntity;
import com.iafenvoy.iceandfire.entity.StymphalianFeatherEntity;
import com.iafenvoy.iceandfire.entity.TideTridentEntity;
import com.iafenvoy.iceandfire.entity.TrollEntity;
import com.iafenvoy.iceandfire.registry.tag.IafBiomeTags;
import dev.architectury.platform.Platform;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class IafEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create("iceandfire", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<DragonPartEntity>> DRAGON_MULTIPART = build(
      "dragon_multipart", DragonPartEntity::new, MobCategory.MISC, true, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<SlowPartEntity>> SLOW_MULTIPART = build("multipart", SlowPartEntity::new, MobCategory.MISC, true, 0.5F, 0.5F);
   public static final RegistrySupplier<EntityType<HydraHeadEntity>> HYDRA_MULTIPART = build(
      "hydra_multipart", HydraHeadEntity::new, MobCategory.MISC, true, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<CyclopsEyeEntity>> CYCLOPS_MULTIPART = build(
      "cylcops_multipart", CyclopsEyeEntity::new, MobCategory.MISC, true, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<DragonEggEntity>> DRAGON_EGG = build(
      "dragon_egg", DragonEggEntity::new, MobCategory.MISC, true, 0.45F, 0.55F
   );
   public static final RegistrySupplier<EntityType<DragonArrowEntity>> DRAGON_ARROW = build(
      "dragon_arrow", DragonArrowEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<DragonSkullEntity>> DRAGON_SKULL = build(
      "dragon_skull", DragonSkullEntity::new, MobCategory.MISC, false, 0.9F, 0.65F
   );
   public static final RegistrySupplier<EntityType<FireDragonEntity>> FIRE_DRAGON = build(
      "fire_dragon", FireDragonEntity::new, MobCategory.CREATURE, true, 0.78F, 1.2F, 256
   );
   public static final RegistrySupplier<EntityType<IceDragonEntity>> ICE_DRAGON = build(
      "ice_dragon", IceDragonEntity::new, MobCategory.CREATURE, false, 0.78F, 1.2F, 256
   );
   public static final RegistrySupplier<EntityType<LightningDragonEntity>> LIGHTNING_DRAGON = build(
      "lightning_dragon", LightningDragonEntity::new, MobCategory.CREATURE, false, 0.78F, 1.2F, 256
   );
   public static final RegistrySupplier<EntityType<FireDragonChargeEntity>> FIRE_DRAGON_CHARGE = build(
      "fire_dragon_charge", FireDragonChargeEntity::new, MobCategory.MISC, false, 0.9F, 0.9F
   );
   public static final RegistrySupplier<EntityType<IceDragonChargeEntity>> ICE_DRAGON_CHARGE = build(
      "ice_dragon_charge", IceDragonChargeEntity::new, MobCategory.MISC, false, 0.9F, 0.9F
   );
   public static final RegistrySupplier<EntityType<LightningDragonChargeEntity>> LIGHTNING_DRAGON_CHARGE = build(
      "lightning_dragon_charge", LightningDragonChargeEntity::new, MobCategory.MISC, false, 0.9F, 0.9F
   );
   public static final RegistrySupplier<EntityType<HippogryphEggEntity>> HIPPOGRYPH_EGG = build(
      "hippogryph_egg", HippogryphEggEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<HippogryphEntity>> HIPPOGRYPH = build(
      "hippogryph", HippogryphEntity::new, MobCategory.CREATURE, false, 1.7F, 1.6F, 128
   );
   public static final RegistrySupplier<EntityType<StoneStatueEntity>> STONE_STATUE = build(
      "stone_statue", StoneStatueEntity::new, MobCategory.CREATURE, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<GorgonEntity>> GORGON = build("gorgon", GorgonEntity::new, MobCategory.CREATURE, false, 0.8F, 1.99F);
   public static final RegistrySupplier<EntityType<PixieEntity>> PIXIE = build("pixie", PixieEntity::new, MobCategory.CREATURE, false, 0.4F, 0.8F);
   public static final RegistrySupplier<EntityType<CyclopsEntity>> CYCLOPS = build("cyclops", CyclopsEntity::new, MobCategory.CREATURE, false, 1.95F, 7.4F);
   public static final RegistrySupplier<EntityType<SirenEntity>> SIREN = build("siren", SirenEntity::new, MobCategory.CREATURE, false, 1.6F, 0.9F);
   public static final RegistrySupplier<EntityType<HippocampusEntity>> HIPPOCAMPUS = build(
      "hippocampus", HippocampusEntity::new, MobCategory.WATER_CREATURE, false, 1.95F, 0.95F
   );
   public static final RegistrySupplier<EntityType<DeathWormEntity>> DEATH_WORM = build(
      "deathworm", DeathWormEntity::new, MobCategory.CREATURE, false, 0.8F, 0.8F, 128
   );
   public static final RegistrySupplier<EntityType<DeathWormEggEntity>> DEATH_WORM_EGG = build(
      "deathworm_egg", DeathWormEggEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<CockatriceEntity>> COCKATRICE = build(
      "cockatrice", CockatriceEntity::new, MobCategory.CREATURE, false, 1.1F, 1.0F
   );
   public static final RegistrySupplier<EntityType<CockatriceEggEntity>> COCKATRICE_EGG = build(
      "cockatrice_egg", CockatriceEggEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<StymphalianBirdEntity>> STYMPHALIAN_BIRD = build(
      "stymphalian_bird", StymphalianBirdEntity::new, MobCategory.CREATURE, false, 1.3F, 1.2F, 128
   );
   public static final RegistrySupplier<EntityType<StymphalianFeatherEntity>> STYMPHALIAN_FEATHER = build(
      "stymphalian_feather", StymphalianFeatherEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<StymphalianArrowEntity>> STYMPHALIAN_ARROW = build(
      "stymphalian_arrow", StymphalianArrowEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<TrollEntity>> TROLL = build("troll", TrollEntity::new, MobCategory.MONSTER, false, 1.2F, 3.5F);
   public static final RegistrySupplier<EntityType<AmphithereEntity>> AMPHITHERE = build(
      "amphithere", AmphithereEntity::new, MobCategory.CREATURE, false, 2.5F, 1.25F, 128
   );
   public static final RegistrySupplier<EntityType<AmphithereArrowEntity>> AMPHITHERE_ARROW = build(
      "amphithere_arrow", AmphithereArrowEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<SeaSerpentEntity>> SEA_SERPENT = build(
      "sea_serpent", SeaSerpentEntity::new, MobCategory.CREATURE, false, 0.5F, 0.5F, 256
   );
   public static final RegistrySupplier<EntityType<SeaSerpentBubblesEntity>> SEA_SERPENT_BUBBLES = build(
      "sea_serpent_bubbles", SeaSerpentBubblesEntity::new, MobCategory.MISC, false, 0.9F, 0.9F
   );
   public static final RegistrySupplier<EntityType<SeaSerpentArrowEntity>> SEA_SERPENT_ARROW = build(
      "sea_serpent_arrow", SeaSerpentArrowEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<ChainTieEntity>> CHAIN_TIE = build("chain_tie", ChainTieEntity::new, MobCategory.MISC, false, 0.8F, 0.9F);
   public static final RegistrySupplier<EntityType<PixieChargeEntity>> PIXIE_CHARGE = build(
      "pixie_charge", PixieChargeEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<TideTridentEntity>> TIDE_TRIDENT = build(
      "tide_trident", TideTridentEntity::new, MobCategory.MISC, false, 0.85F, 0.5F
   );
   public static final RegistrySupplier<EntityType<MobSkullEntity>> MOB_SKULL = build("mob_skull", MobSkullEntity::new, MobCategory.MISC, false, 0.85F, 0.85F);
   public static final RegistrySupplier<EntityType<DreadThrallEntity>> DREAD_THRALL = build(
      "dread_thrall", DreadThrallEntity::new, MobCategory.MONSTER, false, 0.6F, 1.8F
   );
   public static final RegistrySupplier<EntityType<DreadGhoulEntity>> DREAD_GHOUL = build(
      "dread_ghoul", DreadGhoulEntity::new, MobCategory.MONSTER, false, 0.6F, 1.8F
   );
   public static final RegistrySupplier<EntityType<DreadBeastEntity>> DREAD_BEAST = build(
      "dread_beast", DreadBeastEntity::new, MobCategory.MONSTER, false, 1.2F, 0.9F
   );
   public static final RegistrySupplier<EntityType<DreadScuttlerEntity>> DREAD_SCUTTLER = build(
      "dread_scuttler", DreadScuttlerEntity::new, MobCategory.MONSTER, false, 1.5F, 1.3F
   );
   public static final RegistrySupplier<EntityType<DreadLichEntity>> DREAD_LICH = build(
      "dread_lich", DreadLichEntity::new, MobCategory.MONSTER, false, 0.6F, 1.8F
   );
   public static final RegistrySupplier<EntityType<DreadLichSkullEntity>> DREAD_LICH_SKULL = build(
      "dread_lich_skull", DreadLichSkullEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<DreadKnightEntity>> DREAD_KNIGHT = build(
      "dread_knight", DreadKnightEntity::new, MobCategory.MONSTER, false, 0.6F, 1.8F
   );
   public static final RegistrySupplier<EntityType<DreadHorseEntity>> DREAD_HORSE = build(
      "dread_horse", DreadHorseEntity::new, MobCategory.MONSTER, false, 1.3964844F, 1.6F
   );
   public static final RegistrySupplier<EntityType<HydraEntity>> HYDRA = build("hydra", HydraEntity::new, MobCategory.CREATURE, false, 2.8F, 1.39F);
   public static final RegistrySupplier<EntityType<HydraBreathEntity>> HYDRA_BREATH = build(
      "hydra_breath", HydraBreathEntity::new, MobCategory.MISC, false, 0.9F, 0.9F
   );
   public static final RegistrySupplier<EntityType<HydraArrowEntity>> HYDRA_ARROW = build(
      "hydra_arrow", HydraArrowEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );
   public static final RegistrySupplier<EntityType<GhostEntity>> GHOST = build("ghost", GhostEntity::new, MobCategory.MONSTER, true, 0.8F, 1.9F);
   public static final RegistrySupplier<EntityType<GhostSwordEntity>> GHOST_SWORD = build(
      "ghost_sword", GhostSwordEntity::new, MobCategory.MISC, false, 0.5F, 0.5F
   );

   private static <T extends Entity> RegistrySupplier<EntityType<T>> build(
      String entityName, EntityFactory<T> constructor, MobCategory category, boolean fireImmune, float sizeX, float sizeY
   ) {
      Builder<T> builder = Builder.of(constructor, category).sized(sizeX, sizeY);
      if (fireImmune) {
         builder.fireImmune();
      }

      return register(entityName, () -> builder.build(entityName));
   }

   private static <T extends Entity> RegistrySupplier<EntityType<T>> build(
      String entityName, EntityFactory<T> constructor, MobCategory category, boolean fireImmune, float sizeX, float sizeY, int trackingRange
   ) {
      Builder<T> builder = Builder.of(constructor, category).sized(sizeX, sizeY).clientTrackingRange(trackingRange);
      if (fireImmune) {
         builder.fireImmune();
      }

      return register(entityName, () -> builder.build(entityName));
   }

   private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String entityName, Supplier<EntityType<T>> builder) {
      return REGISTRY.register(entityName, builder);
   }

   public static void init() {
      addSpawners();
      commonSetup();
   }

   public static void registerAttributes() {
      EntityAttributeRegistry.register(DRAGON_EGG, DragonEggEntity::bakeAttributes);
      EntityAttributeRegistry.register(DRAGON_SKULL, DragonSkullEntity::bakeAttributes);
      EntityAttributeRegistry.register(FIRE_DRAGON, DragonBaseEntity::bakeAttributes);
      EntityAttributeRegistry.register(ICE_DRAGON, DragonBaseEntity::bakeAttributes);
      EntityAttributeRegistry.register(LIGHTNING_DRAGON, DragonBaseEntity::bakeAttributes);
      EntityAttributeRegistry.register(HIPPOGRYPH, HippogryphEntity::bakeAttributes);
      EntityAttributeRegistry.register(GORGON, GorgonEntity::bakeAttributes);
      EntityAttributeRegistry.register(STONE_STATUE, StoneStatueEntity::bakeAttributes);
      EntityAttributeRegistry.register(PIXIE, PixieEntity::bakeAttributes);
      EntityAttributeRegistry.register(CYCLOPS, CyclopsEntity::bakeAttributes);
      EntityAttributeRegistry.register(SIREN, SirenEntity::bakeAttributes);
      EntityAttributeRegistry.register(HIPPOCAMPUS, HippocampusEntity::bakeAttributes);
      EntityAttributeRegistry.register(DEATH_WORM, DeathWormEntity::bakeAttributes);
      EntityAttributeRegistry.register(COCKATRICE, CockatriceEntity::bakeAttributes);
      EntityAttributeRegistry.register(STYMPHALIAN_BIRD, StymphalianBirdEntity::bakeAttributes);
      EntityAttributeRegistry.register(TROLL, TrollEntity::bakeAttributes);
      EntityAttributeRegistry.register(AMPHITHERE, AmphithereEntity::bakeAttributes);
      EntityAttributeRegistry.register(SEA_SERPENT, SeaSerpentEntity::bakeAttributes);
      EntityAttributeRegistry.register(MOB_SKULL, MobSkullEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_THRALL, DreadThrallEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_LICH, DreadLichEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_BEAST, DreadBeastEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_HORSE, DreadHorseEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_GHOUL, DreadGhoulEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_KNIGHT, DreadKnightEntity::bakeAttributes);
      EntityAttributeRegistry.register(DREAD_SCUTTLER, DreadScuttlerEntity::bakeAttributes);
      EntityAttributeRegistry.register(HYDRA, HydraEntity::bakeAttributes);
      EntityAttributeRegistry.register(GHOST, GhostEntity::bakeAttributes);
   }

   public static void commonSetup() {
      SpawnPlacements.register((EntityType)HIPPOGRYPH.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
      SpawnPlacements.register((EntityType)TROLL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, TrollEntity::canTrollSpawnOn);
      SpawnPlacements.register((EntityType)DREAD_LICH.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, DreadLichEntity::canLichSpawnOn);
      SpawnPlacements.register(
         (EntityType)COCKATRICE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, CockatriceEntity::canCockatriceSpawn
      );
      SpawnPlacements.register((EntityType)AMPHITHERE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Types.MOTION_BLOCKING, AmphithereEntity::canAmphithereSpawnOn);
   }

   public static void addSpawners() {
      if (!Platform.isNeoForge()) {
         if ((Boolean)IafCommonConfig.INSTANCE.hippogryphs.spawn.getValue()) {
            BiomeModifications.addProperties(
               context -> context.hasTag(IafBiomeTags.HIPPOGRYPH),
               (context, mutable) -> mutable.getSpawnProperties()
                  .addSpawn(
                     MobCategory.CREATURE,
                     new SpawnerData((EntityType)HIPPOGRYPH.get(), (Integer)IafCommonConfig.INSTANCE.hippogryphs.spawnWeight.getValue(), 1, 1)
                  )
            );
         }

         if ((Boolean)IafCommonConfig.INSTANCE.lich.spawn.getValue()) {
            BiomeModifications.addProperties(
               context -> context.hasTag(IafBiomeTags.MAUSOLEUM),
               (context, mutable) -> mutable.getSpawnProperties()
                  .addSpawn(
                     MobCategory.MONSTER, new SpawnerData((EntityType)DREAD_LICH.get(), (Integer)IafCommonConfig.INSTANCE.lich.spawnWeight.getValue(), 1, 1)
                  )
            );
         }

         if ((Boolean)IafCommonConfig.INSTANCE.cockatrice.spawn.getValue()) {
            BiomeModifications.addProperties(
               context -> context.hasTag(IafBiomeTags.COCKATRICE),
               (context, mutable) -> mutable.getSpawnProperties()
                  .addSpawn(
                     MobCategory.CREATURE,
                     new SpawnerData((EntityType)COCKATRICE.get(), (Integer)IafCommonConfig.INSTANCE.cockatrice.spawnWeight.getValue(), 1, 2)
                  )
            );
         }

         if ((Boolean)IafCommonConfig.INSTANCE.amphithere.spawn.getValue()) {
            BiomeModifications.addProperties(
               context -> context.hasTag(IafBiomeTags.AMPHITHERE),
               (context, mutable) -> mutable.getSpawnProperties()
                  .addSpawn(
                     MobCategory.CREATURE,
                     new SpawnerData((EntityType)AMPHITHERE.get(), (Integer)IafCommonConfig.INSTANCE.amphithere.spawnWeight.getValue(), 1, 3)
                  )
            );
         }

         if ((Boolean)IafCommonConfig.INSTANCE.troll.spawn.getValue()) {
            BiomeModifications.addProperties(
               context -> context.hasTag(IafBiomeTags.TROLL),
               (context, mutable) -> mutable.getSpawnProperties()
                  .addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)TROLL.get(), (Integer)IafCommonConfig.INSTANCE.troll.spawnWeight.getValue(), 1, 3))
            );
         }
      }
   }
}

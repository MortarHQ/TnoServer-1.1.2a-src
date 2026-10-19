package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.entities.boats.ChronoDawnBoat;
import com.chronodawn.entities.boats.ChronoDawnChestBoat;
import com.chronodawn.entities.bosses.ChronosWardenEntity;
import com.chronodawn.entities.bosses.ClockworkColossusEntity;
import com.chronodawn.entities.bosses.EntropyKeeperEntity;
import com.chronodawn.entities.bosses.TemporalPhantomEntity;
import com.chronodawn.entities.bosses.TimeGuardianEntity;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.entities.mobs.ChronalLeechEntity;
import com.chronodawn.entities.mobs.ChronoBovineEntity;
import com.chronodawn.entities.mobs.ChronoTurtleEntity;
import com.chronodawn.entities.mobs.ChronoUrsidEntity;
import com.chronodawn.entities.mobs.ClockworkSentinelEntity;
import com.chronodawn.entities.mobs.EpochHuskEntity;
import com.chronodawn.entities.mobs.FloqEntity;
import com.chronodawn.entities.mobs.ForgottenMinuteEntity;
import com.chronodawn.entities.mobs.GlideFishEntity;
import com.chronodawn.entities.mobs.HourglassGolemEntity;
import com.chronodawn.entities.mobs.MomentCreeperEntity;
import com.chronodawn.entities.mobs.ParadoxCrawlerEntity;
import com.chronodawn.entities.mobs.PulseHogEntity;
import com.chronodawn.entities.mobs.SecondhandArcherEntity;
import com.chronodawn.entities.mobs.SecondwingFowlEntity;
import com.chronodawn.entities.mobs.TemporalCapridEntity;
import com.chronodawn.entities.mobs.TemporalWraithEntity;
import com.chronodawn.entities.mobs.TickingSheepEntity;
import com.chronodawn.entities.mobs.TimeKeeperEntity;
import com.chronodawn.entities.mobs.TimeboundRabbitEntity;
import com.chronodawn.entities.mobs.TimelineStriderEntity;
import com.chronodawn.entities.projectiles.GearProjectileEntity;
import com.chronodawn.entities.projectiles.TimeArrowEntity;
import com.chronodawn.entities.projectiles.TimeBlastEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class ModEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create("chronodawn", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<TimeGuardianEntity>> TIME_GUARDIAN = ENTITIES.register(
      ModEntityId.TIME_GUARDIAN.id(),
      () -> Builder.of(TimeGuardianEntity::new, MobCategory.MONSTER).sized(1.0F, 2.8F).clientTrackingRange(10).updateInterval(3).build("time_guardian")
   );
   public static final RegistrySupplier<EntityType<ChronosWardenEntity>> CHRONOS_WARDEN = ENTITIES.register(
      ModEntityId.CHRONOS_WARDEN.id(),
      () -> Builder.of(ChronosWardenEntity::new, MobCategory.MONSTER).sized(1.0F, 2.5F).clientTrackingRange(10).updateInterval(3).build("chronos_warden")
   );
   public static final RegistrySupplier<EntityType<ClockworkColossusEntity>> CLOCKWORK_COLOSSUS = ENTITIES.register(
      ModEntityId.CLOCKWORK_COLOSSUS.id(),
      () -> Builder.of(ClockworkColossusEntity::new, MobCategory.MONSTER)
         .sized(1.0F, 2.5F)
         .clientTrackingRange(10)
         .updateInterval(3)
         .build("clockwork_colossus")
   );
   public static final RegistrySupplier<EntityType<GearProjectileEntity>> GEAR_PROJECTILE = ENTITIES.register(
      ModEntityId.GEAR_PROJECTILE.id(),
      () -> Builder.of(GearProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("gear_projectile")
   );
   public static final RegistrySupplier<EntityType<TemporalWraithEntity>> TEMPORAL_WRAITH = ENTITIES.register(
      ModEntityId.TEMPORAL_WRAITH.id(),
      () -> Builder.of(TemporalWraithEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(8).updateInterval(3).build("temporal_wraith")
   );
   public static final RegistrySupplier<EntityType<ClockworkSentinelEntity>> CLOCKWORK_SENTINEL = ENTITIES.register(
      ModEntityId.CLOCKWORK_SENTINEL.id(),
      () -> Builder.of(ClockworkSentinelEntity::new, MobCategory.MONSTER)
         .sized(0.9F, 2.0F)
         .clientTrackingRange(10)
         .updateInterval(3)
         .build("clockwork_sentinel")
   );
   public static final RegistrySupplier<EntityType<EpochHuskEntity>> EPOCH_HUSK = ENTITIES.register(
      ModEntityId.EPOCH_HUSK.id(),
      () -> Builder.of(EpochHuskEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).updateInterval(3).build("epoch_husk")
   );
   public static final RegistrySupplier<EntityType<ForgottenMinuteEntity>> FORGOTTEN_MINUTE = ENTITIES.register(
      ModEntityId.FORGOTTEN_MINUTE.id(),
      () -> Builder.of(ForgottenMinuteEntity::new, MobCategory.MONSTER).sized(0.4F, 0.8F).clientTrackingRange(8).updateInterval(3).build("forgotten_minute")
   );
   public static final RegistrySupplier<EntityType<ChronalLeechEntity>> CHRONAL_LEECH = ENTITIES.register(
      ModEntityId.CHRONAL_LEECH.id(),
      () -> Builder.of(ChronalLeechEntity::new, MobCategory.MONSTER).sized(0.4F, 0.3F).clientTrackingRange(8).updateInterval(3).build("chronal_leech")
   );
   public static final RegistrySupplier<EntityType<MomentCreeperEntity>> MOMENT_CREEPER = ENTITIES.register(
      ModEntityId.MOMENT_CREEPER.id(),
      () -> Builder.of(MomentCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8).updateInterval(3).build("moment_creeper")
   );
   public static final RegistrySupplier<EntityType<TimeKeeperEntity>> TIME_KEEPER = ENTITIES.register(
      ModEntityId.TIME_KEEPER.id(),
      () -> Builder.of(TimeKeeperEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).clientTrackingRange(10).updateInterval(3).build("time_keeper")
   );
   public static final RegistrySupplier<EntityType<FloqEntity>> FLOQ = ENTITIES.register(
      ModEntityId.FLOQ.id(), () -> Builder.of(FloqEntity::new, MobCategory.MONSTER).sized(0.6F, 0.6F).clientTrackingRange(8).updateInterval(3).build("floq")
   );
   public static final RegistrySupplier<EntityType<TimeTyrantEntity>> TIME_TYRANT = ENTITIES.register(
      ModEntityId.TIME_TYRANT.id(),
      () -> Builder.of(TimeTyrantEntity::new, MobCategory.MONSTER).sized(1.5F, 4.0F).clientTrackingRange(10).updateInterval(3).build("time_tyrant")
   );
   public static final RegistrySupplier<EntityType<TimeArrowEntity>> TIME_ARROW = ENTITIES.register(
      ModEntityId.TIME_ARROW.id(),
      () -> Builder.of(TimeArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("time_arrow")
   );
   public static final RegistrySupplier<EntityType<TimeBlastEntity>> TIME_BLAST = ENTITIES.register(
      ModEntityId.TIME_BLAST.id(),
      () -> Builder.of(TimeBlastEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("time_blast")
   );
   public static final RegistrySupplier<EntityType<TemporalPhantomEntity>> TEMPORAL_PHANTOM = ENTITIES.register(
      ModEntityId.TEMPORAL_PHANTOM.id(),
      () -> Builder.of(TemporalPhantomEntity::new, MobCategory.MONSTER).sized(0.8F, 2.0F).clientTrackingRange(10).updateInterval(3).build("temporal_phantom")
   );
   public static final RegistrySupplier<EntityType<EntropyKeeperEntity>> ENTROPY_KEEPER = ENTITIES.register(
      ModEntityId.ENTROPY_KEEPER.id(),
      () -> Builder.of(EntropyKeeperEntity::new, MobCategory.MONSTER).sized(0.9F, 2.2F).clientTrackingRange(10).updateInterval(3).build("entropy_keeper")
   );
   public static final RegistrySupplier<EntityType<GlideFishEntity>> GLIDE_FISH = ENTITIES.register(
      ModEntityId.GLIDE_FISH.id(),
      () -> Builder.of(GlideFishEntity::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.3F).clientTrackingRange(8).updateInterval(3).build("glide_fish")
   );
   public static final RegistrySupplier<EntityType<TimelineStriderEntity>> TIMELINE_STRIDER = ENTITIES.register(
      ModEntityId.TIMELINE_STRIDER.id(),
      () -> Builder.of(TimelineStriderEntity::new, MobCategory.MONSTER).sized(0.6F, 2.9F).clientTrackingRange(8).updateInterval(3).build("timeline_strider")
   );
   public static final RegistrySupplier<EntityType<HourglassGolemEntity>> HOURGLASS_GOLEM = ENTITIES.register(
      ModEntityId.HOURGLASS_GOLEM.id(),
      () -> Builder.of(HourglassGolemEntity::new, MobCategory.MONSTER).sized(1.4F, 2.7F).clientTrackingRange(8).updateInterval(3).build("hourglass_golem")
   );
   public static final RegistrySupplier<EntityType<ParadoxCrawlerEntity>> PARADOX_CRAWLER = ENTITIES.register(
      ModEntityId.PARADOX_CRAWLER.id(),
      () -> Builder.of(ParadoxCrawlerEntity::new, MobCategory.MONSTER).sized(1.4F, 0.9F).clientTrackingRange(8).updateInterval(3).build("paradox_crawler")
   );
   public static final RegistrySupplier<EntityType<SecondhandArcherEntity>> SECONDHAND_ARCHER = ENTITIES.register(
      ModEntityId.SECONDHAND_ARCHER.id(),
      () -> Builder.of(SecondhandArcherEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).updateInterval(3).build("secondhand_archer")
   );
   public static final RegistrySupplier<EntityType<ChronoTurtleEntity>> CHRONO_TURTLE = ENTITIES.register(
      ModEntityId.CHRONO_TURTLE.id(),
      () -> Builder.of(ChronoTurtleEntity::new, MobCategory.WATER_CREATURE).sized(1.2F, 0.4F).clientTrackingRange(8).updateInterval(3).build("chrono_turtle")
   );
   public static final RegistrySupplier<EntityType<TimeboundRabbitEntity>> TIMEBOUND_RABBIT = ENTITIES.register(
      ModEntityId.TIMEBOUND_RABBIT.id(),
      () -> Builder.of(TimeboundRabbitEntity::new, MobCategory.CREATURE).sized(0.4F, 0.5F).clientTrackingRange(8).updateInterval(3).build("timebound_rabbit")
   );
   public static final RegistrySupplier<EntityType<PulseHogEntity>> PULSE_HOG = ENTITIES.register(
      ModEntityId.PULSE_HOG.id(),
      () -> Builder.of(PulseHogEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(3).build("pulse_hog")
   );
   public static final RegistrySupplier<EntityType<SecondwingFowlEntity>> SECONDWING_FOWL = ENTITIES.register(
      ModEntityId.SECONDWING_FOWL.id(),
      () -> Builder.of(SecondwingFowlEntity::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10).updateInterval(3).build("secondwing_fowl")
   );
   public static final RegistrySupplier<EntityType<TickingSheepEntity>> TICKING_SHEEP = ENTITIES.register(
      ModEntityId.TICKING_SHEEP.id(),
      () -> Builder.of(TickingSheepEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).updateInterval(3).build("ticking_sheep")
   );
   public static final RegistrySupplier<EntityType<ChronoBovineEntity>> CHRONO_BOVINE = ENTITIES.register(
      ModEntityId.CHRONO_BOVINE.id(),
      () -> Builder.of(ChronoBovineEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).updateInterval(3).build("chrono_bovine")
   );
   public static final RegistrySupplier<EntityType<TemporalCapridEntity>> TEMPORAL_CAPRID = ENTITIES.register(
      ModEntityId.TEMPORAL_CAPRID.id(),
      () -> Builder.of(TemporalCapridEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).updateInterval(3).build("temporal_caprid")
   );
   public static final RegistrySupplier<EntityType<ChronoUrsidEntity>> CHRONO_URSID = ENTITIES.register(
      ModEntityId.CHRONO_URSID.id(),
      () -> Builder.of(ChronoUrsidEntity::new, MobCategory.CREATURE).sized(1.4F, 1.4F).clientTrackingRange(10).updateInterval(3).build("chrono_ursid")
   );
   public static final RegistrySupplier<EntityType<ChronoDawnBoat>> CHRONO_DAWN_BOAT = ENTITIES.register(
      ModEntityId.CHRONO_DAWN_BOAT.id(),
      () -> Builder.of(ChronoDawnBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("chronodawn_boat")
   );
   public static final RegistrySupplier<EntityType<ChronoDawnChestBoat>> CHRONO_DAWN_CHEST_BOAT = ENTITIES.register(
      ModEntityId.CHRONO_DAWN_CHEST_BOAT.id(),
      () -> Builder.of(ChronoDawnChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("chronodawn_chest_boat")
   );

   public static void register() {
      ENTITIES.register();
      ChronoDawn.LOGGER.debug("Registered ModEntities");
   }
}

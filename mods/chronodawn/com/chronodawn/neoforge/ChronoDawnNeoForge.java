package com.chronodawn.neoforge;

import com.chronodawn.ChronoDawn;
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
import com.chronodawn.neoforge.event.OverlayPackFinder;
import com.chronodawn.neoforge.registry.ModFluidTypes;
import com.chronodawn.neoforge.registry.ModLootModifiers;
import com.chronodawn.neoforge.registry.ModParticles;
import com.chronodawn.network.ModNetworking;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import com.chronodawn.worldgen.processors.BossRoomProtectionProcessor;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@Mod("chronodawn")
public class ChronoDawnNeoForge {
   private int tickCounter = 0;

   public ChronoDawnNeoForge(IEventBus modEventBus) {
      ModFluidTypes.register(modEventBus);
      ChronoDawn.init();
      ModNetworking.register();
      ModParticles.register(modEventBus);
      ModLootModifiers.register(modEventBus);
      modEventBus.addListener(this::registerEntityAttributes);
      modEventBus.addListener(this::registerSpawnPlacements);
      modEventBus.addListener(this::commonSetup);
      modEventBus.addListener(event -> OverlayPackFinder.onAddPackFinders(event));
      NeoForge.EVENT_BUS.addListener(this::onServerTick);
      NeoForge.EVENT_BUS.addListener(this::onBlockBreak);
      NeoForge.EVENT_BUS.addListener(this::onBlockPlace);
      ChronoDawn.LOGGER.info("ChronoDawn Mod (NeoForge) initialized");
   }

   private void onServerTick(Post event) {
      VersionSpecificServerHelper.processPendingTeleports(event.getServer());
      this.tickCounter++;
      if (this.tickCounter >= 100) {
         this.tickCounter = 0;
         event.getServer().getAllLevels().forEach(level -> BossRoomProtectionProcessor.registerPendingProtections(level));
      }
   }

   private void onBlockBreak(BreakEvent event) {
      Player player = event.getPlayer();
      if (!player.isCreative()) {
         if (event.getLevel() instanceof Level level && BlockProtectionHandler.isProtected(level, event.getPos())) {
            player.displayClientMessage(Component.translatable("message.chronodawn.boss_room_protected"), true);
            event.setCanceled(true);
         }
      }
   }

   private void onBlockPlace(EntityPlaceEvent event) {
      if (event.getEntity() instanceof Player player) {
         if (!player.isCreative()) {
            if (event.getLevel() instanceof Level level && BlockProtectionHandler.isProtected(level, event.getPos())) {
               player.displayClientMessage(Component.translatable("message.chronodawn.boss_room_no_placement"), true);
               event.setCanceled(true);
            }
         }
      }
   }

   private void registerEntityAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)ModEntities.TIME_GUARDIAN.get(), TimeGuardianEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CHRONOS_WARDEN.get(), ChronosWardenEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CLOCKWORK_COLOSSUS.get(), ClockworkColossusEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TIME_TYRANT.get(), TimeTyrantEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TEMPORAL_PHANTOM.get(), TemporalPhantomEntity.createAttributes().build());
      event.put((EntityType)ModEntities.ENTROPY_KEEPER.get(), EntropyKeeperEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TEMPORAL_WRAITH.get(), TemporalWraithEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CLOCKWORK_SENTINEL.get(), ClockworkSentinelEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TIME_KEEPER.get(), TimeKeeperEntity.createAttributes().build());
      event.put((EntityType)ModEntities.FLOQ.get(), FloqEntity.createAttributes().build());
      event.put((EntityType)ModEntities.EPOCH_HUSK.get(), EpochHuskEntity.createAttributes().build());
      event.put((EntityType)ModEntities.FORGOTTEN_MINUTE.get(), ForgottenMinuteEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CHRONAL_LEECH.get(), ChronalLeechEntity.createAttributes().build());
      event.put((EntityType)ModEntities.MOMENT_CREEPER.get(), MomentCreeperEntity.createAttributes().build());
      event.put((EntityType)ModEntities.GLIDE_FISH.get(), GlideFishEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TIMELINE_STRIDER.get(), TimelineStriderEntity.createAttributes().build());
      event.put((EntityType)ModEntities.HOURGLASS_GOLEM.get(), HourglassGolemEntity.createAttributes().build());
      event.put((EntityType)ModEntities.SECONDHAND_ARCHER.get(), SecondhandArcherEntity.createAttributes().build());
      event.put((EntityType)ModEntities.PARADOX_CRAWLER.get(), ParadoxCrawlerEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CHRONO_TURTLE.get(), ChronoTurtleEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TIMEBOUND_RABBIT.get(), TimeboundRabbitEntity.createAttributes().build());
      event.put((EntityType)ModEntities.PULSE_HOG.get(), PulseHogEntity.createAttributes().build());
      event.put((EntityType)ModEntities.SECONDWING_FOWL.get(), SecondwingFowlEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TICKING_SHEEP.get(), TickingSheepEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CHRONO_BOVINE.get(), ChronoBovineEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TEMPORAL_CAPRID.get(), TemporalCapridEntity.createAttributes().build());
      event.put((EntityType)ModEntities.CHRONO_URSID.get(), ChronoUrsidEntity.createAttributes().build());
      ChronoDawn.LOGGER.debug("Registered entity attributes for NeoForge");
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         ModItems.initializeSpawnEggs();
         ChronoDawn.LOGGER.debug("Initialized spawn eggs for NeoForge");
      });
   }

   private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)ModEntities.TEMPORAL_WRAITH.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TemporalWraithEntity::checkTemporalWraithSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.CLOCKWORK_SENTINEL.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ClockworkSentinelEntity::checkClockworkSentinelSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.TIME_KEEPER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TimeKeeperEntity::checkTimeKeeperSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.FLOQ.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, FloqEntity::checkFloqSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.EPOCH_HUSK.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         EpochHuskEntity::checkEpochHuskSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.FORGOTTEN_MINUTE.get(),
         SpawnPlacementTypes.NO_RESTRICTIONS,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ForgottenMinuteEntity::checkForgottenMinuteSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.CHRONAL_LEECH.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ChronalLeechEntity::checkChronalLeechSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.MOMENT_CREEPER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         MomentCreeperEntity::checkMomentCreeperSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.GLIDE_FISH.get(),
         SpawnPlacementTypes.IN_WATER,
         Types.OCEAN_FLOOR,
         WaterAnimal::checkSurfaceWaterAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.TIMELINE_STRIDER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TimelineStriderEntity::checkTimelineStriderSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.HOURGLASS_GOLEM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         HourglassGolemEntity::checkHourglassGolemSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.SECONDHAND_ARCHER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         SecondhandArcherEntity::checkSecondhandArcherSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.PARADOX_CRAWLER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ParadoxCrawlerEntity::checkParadoxCrawlerSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.CHRONO_TURTLE.get(),
         SpawnPlacementTypes.IN_WATER,
         Types.OCEAN_FLOOR,
         WaterAnimal::checkSurfaceWaterAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.TIMEBOUND_RABBIT.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TimeboundRabbitEntity::checkTimeboundRabbitSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.PULSE_HOG.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         PulseHogEntity::checkPulseHogSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.SECONDWING_FOWL.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         SecondwingFowlEntity::checkSecondwingFowlSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.TICKING_SHEEP.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TickingSheepEntity::checkTickingSheepSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.CHRONO_BOVINE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ChronoBovineEntity::checkChronoBovineSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.TEMPORAL_CAPRID.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TemporalCapridEntity::checkTemporalCapridSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)ModEntities.CHRONO_URSID.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         ChronoUrsidEntity::checkChronoUrsidSpawnRules,
         Operation.REPLACE
      );
      ChronoDawn.LOGGER.debug("Registered spawn placements for custom mobs");
   }
}

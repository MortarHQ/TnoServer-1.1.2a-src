package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.TemporalPhantomEntity;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEntities;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerLevelState;
import dev.architectury.event.events.common.TickEvent.Server;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TemporalPhantomSpawner {
   private static final ResourceLocation PHANTOM_CATACOMBS_ID = CompatResourceLocation.create("chronodawn", "phantom_catacombs");
   private static final Map<ResourceLocation, Set<BlockPos>> bossRoomPositions = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Set<BlockPos>> spawnedBossRooms = new ConcurrentHashMap<>();
   private static final int CHECK_INTERVAL = 20;
   private static final Map<ResourceLocation, Integer> tickCounters = new ConcurrentHashMap<>();

   public static void registerBossRoom(ServerLevel level, BlockPos bossRoomCenter) {
      ResourceLocation dimensionId = level.dimension().location();
      bossRoomPositions.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
      bossRoomPositions.get(dimensionId).add(bossRoomCenter.immutable());
      ChronoDawn.LOGGER.debug("Registered Phantom Catacombs boss_room at {} in dimension {} for Temporal Phantom spawning", bossRoomCenter, dimensionId);
   }

   public static void register() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Temporal Phantom Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerLevel level : server.getAllLevels()) {
            checkAndSpawnPhantom(level);
         }
      });
      ChronoDawn.LOGGER.debug("Registered TemporalPhantomSpawner");
   }

   public static void checkAndSpawnPhantom(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ResourceLocation dimensionId = level.dimension().location();
         tickCounters.putIfAbsent(dimensionId, 0);
         int tickCounter = tickCounters.compute(dimensionId, (k, v) -> (v == null ? 0 : v) + 1);
         if (tickCounter >= 20) {
            tickCounters.put(dimensionId, 0);
            if (bossRoomPositions.containsKey(dimensionId)) {
               if (!level.players().isEmpty()) {
                  spawnedBossRooms.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
                  Set<BlockPos> spawned = spawnedBossRooms.get(dimensionId);
                  Set<BlockPos> bossRooms = bossRoomPositions.get(dimensionId);
                  ChronoDawn.LOGGER
                     .debug(
                        "Checking Temporal Phantom spawn conditions in dimension {}: {} boss_rooms registered, {} players present",
                        new Object[]{dimensionId, bossRooms.size(), level.players().size()}
                     );

                  for (BlockPos bossRoomCenter : bossRooms) {
                     if (!spawned.contains(bossRoomCenter)) {
                        AABB bossRoomBounds = new AABB(bossRoomCenter).inflate(12.0, 6.0, 12.0);
                        boolean playerNearby = false;
                        ServerPlayer nearestPlayer = null;

                        for (ServerPlayer player : level.players()) {
                           if (bossRoomBounds.contains(player.position())) {
                              playerNearby = true;
                              nearestPlayer = player;
                              break;
                           }
                        }

                        if (playerNearby) {
                           ChronoDawn.LOGGER
                              .debug(
                                 "Player {} entered boss_room at {} (dimension: {}), spawning Temporal Phantom",
                                 new Object[]{nearestPlayer.getName().getString(), bossRoomCenter, dimensionId}
                              );
                           spawnTemporalPhantom(level, bossRoomCenter);
                           spawned.add(bossRoomCenter);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void spawnTemporalPhantom(ServerLevel level, BlockPos bossRoomCenter) {
      BlockPos spawnPos = findGroundLevel(level, bossRoomCenter);
      ChronoDawn.LOGGER
         .debug(
            "Spawning Temporal Phantom in Phantom Catacombs boss_room at {} (center: {}, dimension: {})",
            new Object[]{spawnPos, bossRoomCenter, level.dimension().location()}
         );
      TemporalPhantomEntity phantom = (TemporalPhantomEntity)((EntityType)ModEntities.TEMPORAL_PHANTOM.get()).create(level);
      if (phantom != null) {
         phantom.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0F, 0.0F);
         phantom.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.STRUCTURE, null);
         boolean addedToWorld = level.addFreshEntity(phantom);
         if (addedToWorld) {
            ChronoDawn.LOGGER.debug("Successfully spawned Temporal Phantom at {} (UUID: {})", spawnPos, phantom.getUUID());
         } else {
            ChronoDawn.LOGGER.error("Failed to add Temporal Phantom entity to world at {} (entity created but addFreshEntity returned false)", spawnPos);
         }
      } else {
         ChronoDawn.LOGGER.error("Failed to create Temporal Phantom entity at {} (ModEntities.TEMPORAL_PHANTOM.get().create() returned null)", spawnPos);
      }
   }

   private static BlockPos findGroundLevel(ServerLevel level, BlockPos center) {
      ChronoDawn.LOGGER.debug("Searching for ground level from center position: {}", center);

      for (int y = center.getY(); y >= center.getY() - 10; y--) {
         BlockPos checkPos = new BlockPos(center.getX(), y, center.getZ());
         BlockPos belowPos = checkPos.below();
         BlockState belowState = level.getBlockState(belowPos);
         BlockState checkState = level.getBlockState(checkPos);
         if (!belowState.isAir() && checkState.isAir()) {
            ChronoDawn.LOGGER
               .debug(
                  "Found ground level at Y={} (below: {}, current: {})",
                  new Object[]{y, belowState.getBlock().getName().getString(), checkState.getBlock().getName().getString()}
               );
            return checkPos;
         }
      }

      ChronoDawn.LOGGER.warn("No ground level found within 10 blocks below center {}, using center position as fallback", center);
      return center;
   }

   public static void clearDimension(ResourceLocation dimensionId) {
      bossRoomPositions.remove(dimensionId);
      spawnedBossRooms.remove(dimensionId);
      tickCounters.remove(dimensionId);
   }

   public static void reset() {
      bossRoomPositions.clear();
      spawnedBossRooms.clear();
      tickCounters.clear();
   }
}

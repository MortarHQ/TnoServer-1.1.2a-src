package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.data.BossSpawnData;
import com.chronodawn.entities.bosses.ClockworkColossusEntity;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEntities;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerLevelState;
import dev.architectury.event.events.common.TickEvent.Server;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;

public class ClockworkColossusSpawner {
   private static final ResourceLocation CLOCKWORK_DEPTHS_ID = CompatResourceLocation.create("chronodawn", "clockwork_depths");
   private static final Map<ResourceLocation, Set<BoundingBox>> engineRooms = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Map<BlockPos, List<BlockPos>>> cachedMarkers = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Map<ChunkPos, Long>> searchedChunks = new ConcurrentHashMap<>();
   private static final long SEARCH_CACHE_DURATION_MS = 300000L;
   private static final int CHECK_INTERVAL = 200;
   private static int tickCounter = 0;
   private static final double SPAWN_DISTANCE = 20.0;

   public static void register() {
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerLevel level : server.getAllLevels()) {
            checkAndSpawnColossus(level);
         }
      });
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Clockwork Colossus Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      ChronoDawn.LOGGER.debug("Registered ClockworkColossusSpawner");
   }

   public static void checkAndSpawnColossus(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ResourceLocation dimensionId = level.dimension().location();
         BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
         tickCounter++;
         if (tickCounter >= 200) {
            tickCounter = 0;
            if (!level.players().isEmpty()) {
               Set<BoundingBox> rooms = engineRooms.get(dimensionId);
               if (rooms != null && !rooms.isEmpty()) {
                  for (ServerPlayer player : level.players()) {
                     BlockPos playerPos = player.blockPosition();

                     for (BoundingBox room : rooms) {
                        if (room.isInside(playerPos)) {
                           BlockPos roomCenter = new BlockPos((room.minX() + room.maxX()) / 2, (room.minY() + room.maxY()) / 2, (room.minZ() + room.maxZ()) / 2);
                           if (!data.hasClockworkColossusStructureSpawned(roomCenter)) {
                              List<BlockPos> markerPositions = findEngineRoomMarkersInBoundingBox(level, room);
                              if (!markerPositions.isEmpty()) {
                                 boolean playerNearby = false;

                                 for (BlockPos marker : markerPositions) {
                                    double distance = player.position().distanceTo(marker.getCenter());
                                    if (distance <= 20.0) {
                                       playerNearby = true;
                                       break;
                                    }
                                 }

                                 if (playerNearby) {
                                    data.markClockworkColossusStructureSpawned(roomCenter);
                                    ChronoDawn.LOGGER.debug("Spawning Clockwork Colossus in engine room at {}", roomCenter);
                                    spawnClockworkColossusAtEngineRoom(level, markerPositions);
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static List<BlockPos> findEngineRoomMarkersInBoundingBox(ServerLevel level, BoundingBox room) {
      List<BlockPos> markers = new ArrayList<>();

      for (BlockPos pos : BlockPos.betweenClosed(room.minX(), room.minY(), room.minZ(), room.maxX(), room.maxY(), room.maxZ())) {
         if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            boolean isDangerSign = false;
            SignText frontText = sign.getFrontText();

            for (int i = 0; i < 4; i++) {
               String lineText = frontText.getMessage(i, false).getString();
               if (lineText.contains("DANGER!!")) {
                  isDangerSign = true;
                  break;
               }
            }

            if (isDangerSign) {
               markers.add(pos.immutable());
            }
         }
      }

      return markers;
   }

   private static boolean hasClockworkDepths(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(CLOCKWORK_DEPTHS_ID)) {
            return true;
         }
      }

      return false;
   }

   private static List<BlockPos> findEngineRoomMarkers(ServerLevel level, ChunkPos chunkPos) {
      long startTime = System.nanoTime();
      List<BlockPos> markers = new ArrayList<>();
      ResourceLocation dimensionId = level.dimension().location();
      Set<BoundingBox> rooms = engineRooms.get(dimensionId);
      if (rooms != null && !rooms.isEmpty()) {
         int chunkMinX = chunkPos.getMinBlockX();
         int chunkMaxX = chunkPos.getMaxBlockX();
         int chunkMinZ = chunkPos.getMinBlockZ();
         int chunkMaxZ = chunkPos.getMaxBlockZ();

         for (BoundingBox room : rooms) {
            if (room.maxX() >= chunkMinX && room.minX() <= chunkMaxX && room.maxZ() >= chunkMinZ && room.minZ() <= chunkMaxZ) {
               for (BlockPos pos : BlockPos.betweenClosed(room.minX(), room.minY(), room.minZ(), room.maxX(), room.maxY(), room.maxZ())) {
                  if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
                     boolean isDangerSign = false;
                     SignText frontText = sign.getFrontText();

                     for (int i = 0; i < 4; i++) {
                        String lineText = frontText.getMessage(i, false).getString();
                        if (lineText.contains("DANGER!!")) {
                           isDangerSign = true;
                           break;
                        }
                     }

                     if (isDangerSign) {
                        markers.add(pos.immutable());
                     }
                  }
               }
            }
         }

         long endTime = System.nanoTime();
         long durationMs = (endTime - startTime) / 1000000L;
         if (durationMs > 100L) {
            ChronoDawn.LOGGER
               .warn("Clockwork Depths marker search took {}ms at chunk {} - found {} markers", new Object[]{durationMs, chunkPos, markers.size()});
         } else {
            ChronoDawn.LOGGER
               .debug("Clockwork Depths marker search took {}ms at chunk {} - found {} markers", new Object[]{durationMs, chunkPos, markers.size()});
         }

         return markers;
      } else {
         return markers;
      }
   }

   private static boolean isPlayerNearby(ServerLevel level, BlockPos pos, double distance) {
      for (ServerPlayer player : level.players()) {
         if (player.blockPosition().distSqr(pos) <= distance * distance) {
            return true;
         }
      }

      return false;
   }

   private static void spawnClockworkColossusAtEngineRoom(ServerLevel level, List<BlockPos> markerPositions) {
      if (markerPositions.isEmpty()) {
         ChronoDawn.LOGGER.warn("No spawn markers found. Cannot spawn Clockwork Colossus.");
      } else {
         BlockPos selectedMarker = markerPositions.get(level.random.nextInt(markerPositions.size()));
         if (!isColossusNearby(level, selectedMarker)) {
            BlockState signBlock = level.getBlockState(selectedMarker);
            BlockPos spawnPos = selectedMarker;
            if (signBlock.getBlock() instanceof WallSignBlock) {
               Direction facing = (Direction)signBlock.getValue(WallSignBlock.FACING);
               spawnPos = selectedMarker.relative(facing);
            }

            BlockState blockAtSpawn = level.getBlockState(spawnPos);
            if (!blockAtSpawn.isAir()) {
               spawnPos = findValidSpawnPosition(level, spawnPos);
            }

            ClockworkColossusEntity colossus = (ClockworkColossusEntity)((EntityType)ModEntities.CLOCKWORK_COLOSSUS.get()).create(level);
            if (colossus != null) {
               colossus.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);
               colossus.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.STRUCTURE, null);
               level.addFreshEntity(colossus);
               ChronoDawn.LOGGER.debug("Clockwork Colossus spawned at [{}, {}, {}]", new Object[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});
            } else {
               ChronoDawn.LOGGER.error("Failed to create Clockwork Colossus entity");
            }
         }
      }
   }

   private static boolean isColossusNearby(ServerLevel level, BlockPos pos) {
      double searchRadius = 50.0;
      List<ClockworkColossusEntity> nearbyColossuses = level.getEntitiesOfClass(ClockworkColossusEntity.class, new AABB(pos).inflate(searchRadius));
      return !nearbyColossuses.isEmpty();
   }

   private static BlockPos findValidSpawnPosition(ServerLevel level, BlockPos startPos) {
      for (int i = 0; i < 10; i++) {
         BlockPos checkPos = startPos.below(i);
         if (level.getBlockState(checkPos).isAir() && !level.getBlockState(checkPos.below()).isAir()) {
            return checkPos;
         }
      }

      return startPos;
   }

   private static void cacheMarkers(ResourceLocation dimensionId, List<BlockPos> markers) {
      if (!markers.isEmpty()) {
         cachedMarkers.putIfAbsent(dimensionId, new ConcurrentHashMap<>());
         Map<BlockPos, List<BlockPos>> dimensionCache = cachedMarkers.get(dimensionId);
         BlockPos key = markers.stream().min((a, b) -> {
            int cmp = Integer.compare(a.getX(), b.getX());
            if (cmp != 0) {
               return cmp;
            }

            cmp = Integer.compare(a.getY(), b.getY());
            return cmp != 0 ? cmp : Integer.compare(a.getZ(), b.getZ());
         }).orElse(markers.get(0));
         dimensionCache.put(key, new ArrayList<>(markers));
      }
   }

   private static List<BlockPos> getCachedMarkersForChunk(ResourceLocation dimensionId, ChunkPos chunkPos) {
      Map<BlockPos, List<BlockPos>> dimensionCache = cachedMarkers.get(dimensionId);
      if (dimensionCache == null) {
         return null;
      }

      for (List<BlockPos> markers : dimensionCache.values()) {
         for (BlockPos marker : markers) {
            int markerChunkX = marker.getX() >> 4;
            int markerChunkZ = marker.getZ() >> 4;
            if (markerChunkX == chunkPos.x && markerChunkZ == chunkPos.z) {
               return markers;
            }
         }
      }

      return null;
   }

   public static void registerEngineRoom(ServerLevel level, BoundingBox boundingBox) {
      ResourceLocation dimensionId = level.dimension().location();
      engineRooms.putIfAbsent(dimensionId, ConcurrentHashMap.newKeySet());
      Set<BoundingBox> rooms = engineRooms.get(dimensionId);
      rooms.add(boundingBox);
      ChronoDawn.LOGGER.debug("Registered Clockwork Depths engine room in dimension {}: {}", dimensionId, boundingBox);
   }

   public static void reset(ServerLevel level) {
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      data.resetClockworkColossus();
      tickCounter = 0;
      ResourceLocation dimensionId = level.dimension().location();
      engineRooms.remove(dimensionId);
      cachedMarkers.remove(dimensionId);
      searchedChunks.remove(dimensionId);
      ChronoDawn.LOGGER.debug("Clockwork Colossus Spawner reset for dimension: {}", dimensionId);
   }
}

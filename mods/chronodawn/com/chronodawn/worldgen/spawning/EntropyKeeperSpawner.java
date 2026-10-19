package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.data.BossSpawnData;
import com.chronodawn.entities.bosses.EntropyKeeperEntity;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEntities;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerLevelState;
import dev.architectury.event.events.common.TickEvent.Server;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;

public class EntropyKeeperSpawner {
   private static final ResourceLocation ENTROPY_CRYPT_ID = CompatResourceLocation.create("chronodawn", "entropy_crypt");
   private static final Map<ResourceLocation, Map<BlockPos, BlockPos>> cachedMarkers = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Map<ChunkPos, Long>> searchedChunks = new ConcurrentHashMap<>();
   private static final long SEARCH_CACHE_DURATION_MS = 300000L;
   private static final int CHECK_INTERVAL = 40;
   private static int tickCounter = 0;

   public static void register() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Entropy Keeper Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerLevel level : server.getAllLevels()) {
            checkAndSpawnKeeper(level);
         }
      });
      ChronoDawn.LOGGER.debug("Registered EntropyKeeperSpawner");
   }

   public static void checkAndSpawnKeeper(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         tickCounter++;
         if (tickCounter >= 40) {
            tickCounter = 0;
            if (!level.players().isEmpty()) {
               BossSpawnData data = CompatSavedData.computeIfAbsent(
                  level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName()
               );

               for (ServerPlayer player : level.players()) {
                  ChunkPos playerChunkPos = new ChunkPos(player.blockPosition());

                  for (int x = -5; x <= 5; x++) {
                     for (int z = -5; z <= 5; z++) {
                        ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + x, playerChunkPos.z + z);
                        if (hasEntropyCrypt(level, chunkPos)) {
                           BlockPos structurePos = chunkPos.getWorldPosition();
                           if (!data.isEntropyKeeperStructureProcessed(structurePos)) {
                              data.markEntropyKeeperStructureProcessed(structurePos);
                              ChronoDawn.LOGGER.debug("Found Entropy Crypt structure at chunk {} (block pos: {})", chunkPos, structurePos);
                              BlockPos markerPos = findBossChamberMarker(level, chunkPos, structurePos);
                              if (markerPos != null && !data.hasEntropyKeeperMarkerSpawned(markerPos)) {
                                 double distance = player.position().distanceTo(markerPos.getCenter());
                                 if (distance <= 20.0) {
                                    ChronoDawn.LOGGER
                                       .debug(
                                          "Player {} is near Entropy Crypt boss marker at {} (distance: {}), spawning Entropy Keeper",
                                          new Object[]{player.getName().getString(), markerPos, distance}
                                       );
                                    spawnEntropyKeeper(level, markerPos);
                                    data.markEntropyKeeperMarkerSpawned(markerPos);
                                    level.setBlock(markerPos, Blocks.AIR.defaultBlockState(), 3);
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

   private static boolean hasEntropyCrypt(ServerLevel level, ChunkPos chunkPos) {
      StructureManager structureManager = level.structureManager();

      for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(chunkPos.getWorldPosition()).entrySet()) {
         Structure structure = entry.getKey();
         ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
         if (structureLocation != null && structureLocation.equals(ENTROPY_CRYPT_ID)) {
            return true;
         }
      }

      return false;
   }

   private static BlockPos findBossChamberMarker(ServerLevel level, ChunkPos chunkPos, BlockPos structurePos) {
      long startTime = System.nanoTime();
      ResourceLocation dimensionId = level.dimension().location();
      cachedMarkers.putIfAbsent(dimensionId, new ConcurrentHashMap<>());
      searchedChunks.putIfAbsent(dimensionId, new ConcurrentHashMap<>());
      Map<BlockPos, BlockPos> dimensionMarkerCache = cachedMarkers.get(dimensionId);
      Map<ChunkPos, Long> dimensionSearchCache = searchedChunks.get(dimensionId);
      BlockPos cachedMarker = dimensionMarkerCache.get(structurePos);
      if (cachedMarker != null) {
         ChronoDawn.LOGGER.debug("Using cached marker position for structure at {}: {}", structurePos, cachedMarker);
         return cachedMarker;
      }

      Long lastSearchTime = dimensionSearchCache.get(chunkPos);
      long currentTime = System.currentTimeMillis();
      if (dimensionSearchCache.size() > 100) {
         dimensionSearchCache.entrySet().removeIf(entry -> currentTime - entry.getValue() > 300000L);
      }

      if (lastSearchTime != null && currentTime - lastSearchTime < 300000L) {
         ChronoDawn.LOGGER.debug("Chunk {} recently searched, skipping", chunkPos);
         return null;
      }

      int searchRadius = 30;
      int minY = Math.max(structurePos.getY() - 20, level.getMinBuildHeight());
      int maxY = Math.min(structurePos.getY() + 20, level.getMaxBuildHeight());
      BlockPos foundMarker = null;

      for (int y = maxY; y >= minY; y--) {
         for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
               BlockPos checkPos = new BlockPos(structurePos.getX() + x, y, structurePos.getZ() + z);
               BlockState state = level.getBlockState(checkPos);
               if (state.is(Blocks.AMETHYST_BLOCK)) {
                  foundMarker = checkPos.immutable();
                  break;
               }
            }

            if (foundMarker != null) {
               break;
            }
         }

         if (foundMarker != null) {
            break;
         }
      }

      dimensionSearchCache.put(chunkPos, currentTime);
      if (foundMarker != null) {
         dimensionMarkerCache.put(structurePos, foundMarker);
      }

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1000000L;
      if (durationMs > 50L) {
         ChronoDawn.LOGGER.warn("Entropy Crypt marker search took {}ms at chunk {} - found: {}", new Object[]{durationMs, chunkPos, foundMarker != null});
      } else {
         ChronoDawn.LOGGER.debug("Entropy Crypt marker search took {}ms at chunk {} - found: {}", new Object[]{durationMs, chunkPos, foundMarker != null});
      }

      return foundMarker;
   }

   private static void spawnEntropyKeeper(ServerLevel level, BlockPos markerPos) {
      BlockPos spawnPos = findGroundLevel(level, markerPos);
      ChronoDawn.LOGGER
         .debug("Spawning Entropy Keeper in Entropy Crypt at {} (marker: {}, dimension: {})", new Object[]{spawnPos, markerPos, level.dimension().location()});
      EntropyKeeperEntity keeper = (EntropyKeeperEntity)((EntityType)ModEntities.ENTROPY_KEEPER.get()).create(level);
      if (keeper != null) {
         keeper.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0F, 0.0F);
         keeper.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.STRUCTURE, null);
         boolean addedToWorld = level.addFreshEntity(keeper);
         if (addedToWorld) {
            ChronoDawn.LOGGER.debug("Successfully spawned Entropy Keeper at {} (UUID: {})", spawnPos, keeper.getUUID());
         } else {
            ChronoDawn.LOGGER.error("Failed to add Entropy Keeper entity to world at {}", spawnPos);
         }
      } else {
         ChronoDawn.LOGGER.error("Failed to create Entropy Keeper entity at {} (ModEntities.ENTROPY_KEEPER.get().create() returned null)", spawnPos);
      }
   }

   private static BlockPos findGroundLevel(ServerLevel level, BlockPos center) {
      for (int y = center.getY(); y >= center.getY() - 10; y--) {
         BlockPos checkPos = new BlockPos(center.getX(), y, center.getZ());
         BlockPos belowPos = checkPos.below();
         BlockState belowState = level.getBlockState(belowPos);
         BlockState checkState = level.getBlockState(checkPos);
         if (!belowState.isAir() && (checkState.isAir() || !checkState.blocksMotion())) {
            return checkPos;
         }
      }

      return center.above();
   }

   public static void reset(ServerLevel level) {
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      data.resetEntropyKeeper();
      tickCounter = 0;
      ResourceLocation dimensionId = level.dimension().location();
      cachedMarkers.remove(dimensionId);
      searchedChunks.remove(dimensionId);
      ChronoDawn.LOGGER.debug("Entropy Keeper Spawner reset for dimension: {}", dimensionId);
   }
}

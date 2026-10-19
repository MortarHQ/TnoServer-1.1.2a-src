package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.data.BossSpawnData;
import com.chronodawn.entities.bosses.TimeGuardianEntity;
import com.chronodawn.registry.ModEntities;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerLevelState;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;

public class TimeGuardianSpawner {
   private static final ResourceLocation DESERT_CLOCK_TOWER_ID = CompatResourceLocation.create("chronodawn", "desert_clock_tower");
   private static final int CHECK_INTERVAL = 100;
   private static final Map<ResourceLocation, AtomicInteger> tickCounters = new ConcurrentHashMap<>();

   public static void register() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Time Guardian Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      ChronoDawn.LOGGER.debug("Registered TimeGuardianSpawner");
   }

   public static void checkAndSpawnGuardians(ServerLevel level) {
      ResourceLocation dimensionId = level.dimension().location();
      tickCounters.putIfAbsent(dimensionId, new AtomicInteger(0));
      AtomicInteger tickCounter = tickCounters.get(dimensionId);
      int currentTick = tickCounter.incrementAndGet();
      if (currentTick >= 100) {
         tickCounter.set(0);
         if (!level.players().isEmpty()) {
            BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
            StructureManager structureManager = level.structureManager();

            for (ServerPlayer player : level.players()) {
               BlockPos playerPos = player.blockPosition();
               ChunkPos playerChunkPos = new ChunkPos(playerPos);

               for (int x = -8; x <= 8; x++) {
                  for (int z = -8; z <= 8; z++) {
                     ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + x, playerChunkPos.z + z);

                     for (StructureStart structureStart : structureManager.startsForStructure(chunkPos, structure -> {
                        ResourceLocation location = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
                        return location != null && location.equals(DESERT_CLOCK_TOWER_ID);
                     })) {
                        BoundingBox boundingBox = structureStart.getBoundingBox();
                        BlockPos structureCenter = new BlockPos(
                           (boundingBox.minX() + boundingBox.maxX()) / 2,
                           (boundingBox.minY() + boundingBox.maxY()) / 2,
                           (boundingBox.minZ() + boundingBox.maxZ()) / 2
                        );
                        if (!data.hasTimeGuardianStructureSpawned(structureCenter)) {
                           double distanceSq = playerPos.distSqr(structureCenter);
                           if (!(distanceSq > 4096.0)) {
                              double distance = Math.sqrt(distanceSq);
                              ChronoDawn.LOGGER
                                 .debug("Found Desert Clock Tower near player at {} (distance: {} blocks) - attempting spawn", structureCenter, distance);
                              int structureHeight = boundingBox.maxY() - boundingBox.minY();
                              BlockPos spawnPos = new BlockPos(
                                 structureCenter.getX(), boundingBox.minY() + (int)(structureHeight * 0.8), structureCenter.getZ()
                              );
                              if (spawnTimeGuardian(level, spawnPos)) {
                                 data.markTimeGuardianStructureSpawned(structureCenter);
                                 ChronoDawn.LOGGER.debug("Successfully spawned Time Guardian at {}", spawnPos);
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

   private static boolean spawnTimeGuardian(ServerLevel level, BlockPos spawnPos) {
      if (isGuardianNearby(level, spawnPos)) {
         return true;
      } else {
         BlockPos validPos = findValidSpawnPosition(level, spawnPos);
         if (validPos == null) {
            ChronoDawn.LOGGER.warn("Could not find valid spawn position near {}", spawnPos);
            return false;
         } else {
            TimeGuardianEntity guardian = (TimeGuardianEntity)((EntityType)ModEntities.TIME_GUARDIAN.get()).create(level);
            if (guardian != null) {
               guardian.moveTo(validPos.getX() + 0.5, validPos.getY(), validPos.getZ() + 0.5, 0.0F, 0.0F);
               guardian.finalizeSpawn(level, level.getCurrentDifficultyAt(validPos), MobSpawnType.STRUCTURE, null);
               level.addFreshEntity(guardian);
               ChronoDawn.LOGGER.debug("Successfully spawned Time Guardian at {}", validPos);
               return true;
            } else {
               ChronoDawn.LOGGER.error("Failed to create Time Guardian entity at {}", validPos);
               return false;
            }
         }
      }
   }

   private static boolean isGuardianNearby(ServerLevel level, BlockPos pos) {
      double searchRadius = 30.0;
      List<TimeGuardianEntity> nearbyGuardians = level.getEntitiesOfClass(TimeGuardianEntity.class, new AABB(pos).inflate(searchRadius));
      return !nearbyGuardians.isEmpty();
   }

   private static BlockPos findValidSpawnPosition(ServerLevel level, BlockPos startPos) {
      int entityHeight = 3;

      for (int i = 0; i < 15; i++) {
         BlockPos checkPos = startPos.below(i);
         if (isValidSpawnLocation(level, checkPos, entityHeight)) {
            return checkPos;
         }
      }

      for (int dx = -2; dx <= 2; dx++) {
         for (int dz = -2; dz <= 2; dz++) {
            if (dx != 0 || dz != 0) {
               BlockPos horizontalPos = startPos.offset(dx, 0, dz);

               for (int dy = 0; dy < 15; dy++) {
                  BlockPos checkPos = horizontalPos.below(dy);
                  if (isValidSpawnLocation(level, checkPos, entityHeight)) {
                     return checkPos;
                  }
               }
            }
         }
      }

      ChronoDawn.LOGGER.warn("Could not find any valid spawn position near {} after exhaustive search", startPos);
      return null;
   }

   private static boolean isValidSpawnLocation(ServerLevel level, BlockPos pos, int entityHeight) {
      if (!level.getBlockState(pos.below()).isSolid()) {
         return false;
      }

      for (int y = 0; y < entityHeight; y++) {
         BlockPos entityBlockPos = pos.above(y);
         BlockState blockState = level.getBlockState(entityBlockPos);
         if (!blockState.isAir()) {
            return false;
         }
      }

      return true;
   }

   public static void reset(ServerLevel level) {
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      data.resetTimeGuardian();
      ResourceLocation dimensionId = level.dimension().location();
      tickCounters.remove(dimensionId);
      ChronoDawn.LOGGER.debug("Time Guardian Spawner reset for dimension: {}", dimensionId);
   }
}

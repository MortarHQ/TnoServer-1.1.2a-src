package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.core.dimension.DimensionStabilizer;
import com.chronodawn.data.BossSpawnData;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.registry.ModEntities;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerLevelState;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TimeTyrantSpawner {
   private static final int MAX_TIME_TYRANTS_PER_WORLD = 1;

   public static void register() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Time Tyrant Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      ChronoDawn.LOGGER.debug("Registered TimeTyrantSpawner");
   }

   private static boolean isTyrantNearby(ServerLevel level, BlockPos pos) {
      double searchRadius = 50.0;
      List<TimeTyrantEntity> nearbyTyrants = level.getEntitiesOfClass(TimeTyrantEntity.class, new AABB(pos).inflate(searchRadius));
      return !nearbyTyrants.isEmpty();
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

   public static void spawnOnDoorOpen(ServerLevel level, BlockPos doorPos, BlockState doorState) {
      ChronoDawn.LOGGER.debug("Boss Room Door opened at {}", doorPos);
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      if (DimensionStabilizer.isTyrantDefeated(level.getServer())) {
         ChronoDawn.LOGGER.debug("Time Tyrant already defeated - not spawning");
      } else if (data.hasTimeTyrantDoorSpawned(doorPos)) {
         ChronoDawn.LOGGER.debug("Time Tyrant already spawned from this door - not spawning again");
      } else {
         int currentCount = data.getTimeTyrantSpawnCount();
         if (currentCount >= 1) {
            ChronoDawn.LOGGER.debug("Time Tyrant spawn limit reached ({}/{}) - not spawning", currentCount, 1);
         } else {
            Direction facing = (Direction)doorState.getValue(DoorBlock.FACING);
            BlockPos spawnPos = doorPos.relative(facing, 15).above(4);
            spawnPos = findValidSpawnPosition(level, spawnPos);
            if (isTyrantNearby(level, spawnPos)) {
               ChronoDawn.LOGGER.debug("Time Tyrant already exists near {} - not spawning", spawnPos);
            } else {
               TimeTyrantEntity tyrant = (TimeTyrantEntity)((EntityType)ModEntities.TIME_TYRANT.get()).create(level);
               if (tyrant != null) {
                  tyrant.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0F, 0.0F);
                  tyrant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.TRIGGERED, null);
                  level.addFreshEntity(tyrant);
                  data.markTimeTyrantDoorSpawned(doorPos);
                  data.incrementTimeTyrantSpawnCount();
                  int newCount = data.getTimeTyrantSpawnCount();
                  ChronoDawn.LOGGER
                     .debug(
                        "Time Tyrant spawned at [{}, {}, {}] from Boss Room Door (Total: {}/{})",
                        new Object[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), newCount, 1}
                     );
               } else {
                  ChronoDawn.LOGGER.error("Failed to create Time Tyrant entity");
               }
            }
         }
      }
   }

   public static void reset(ServerLevel level) {
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      data.resetTimeTyrant();
      ChronoDawn.LOGGER.debug("Time Tyrant Spawner reset for dimension: {}", level.dimension().location());
   }
}

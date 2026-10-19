package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.data.BossSpawnData;
import com.chronodawn.entities.bosses.ChronosWardenEntity;
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

public class ChronosWardenSpawner {
   public static void register() {
      LifecycleEvent.SERVER_LEVEL_LOAD.register((ServerLevelState)level -> {
         if (level instanceof ServerLevel serverLevel) {
            ChronoDawn.LOGGER.debug("Chronos Warden Spawner initialized for dimension: {}", serverLevel.dimension().location());
         }
      });
      ChronoDawn.LOGGER.debug("Registered ChronosWardenSpawner");
   }

   private static boolean isWardenNearby(ServerLevel level, BlockPos pos) {
      double searchRadius = 50.0;
      List<ChronosWardenEntity> nearbyWardens = level.getEntitiesOfClass(ChronosWardenEntity.class, new AABB(pos).inflate(searchRadius));
      return !nearbyWardens.isEmpty();
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
      ChronoDawn.LOGGER.debug("Guardian Vault Door opened at {}", doorPos);
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      if (data.hasChronosWardenDoorSpawned(doorPos)) {
         ChronoDawn.LOGGER.debug("Chronos Warden already spawned from this door - not spawning again");
      } else {
         Direction facing = (Direction)doorState.getValue(DoorBlock.FACING);
         BlockPos spawnPos = doorPos.relative(facing, 10).relative(facing.getClockWise(), 10).above(1);
         spawnPos = findValidSpawnPosition(level, spawnPos);
         if (isWardenNearby(level, spawnPos)) {
            ChronoDawn.LOGGER.debug("Chronos Warden already exists near {} - not spawning", spawnPos);
         } else {
            ChronosWardenEntity warden = (ChronosWardenEntity)((EntityType)ModEntities.CHRONOS_WARDEN.get()).create(level);
            if (warden != null) {
               warden.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0F, 0.0F);
               warden.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.TRIGGERED, null);
               level.addFreshEntity(warden);
               data.markChronosWardenDoorSpawned(doorPos);
               ChronoDawn.LOGGER
                  .debug("Chronos Warden spawned at [{}, {}, {}] from Guardian Vault Door", new Object[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});
            } else {
               ChronoDawn.LOGGER.error("Failed to create Chronos Warden entity");
            }
         }
      }
   }

   public static void reset(ServerLevel level) {
      BossSpawnData data = CompatSavedData.computeIfAbsent(level.getDataStorage(), BossSpawnData::new, BossSpawnData::load, BossSpawnData.getDataName());
      data.resetChronosWarden();
      ChronoDawn.LOGGER.debug("Chronos Warden Spawner reset for dimension: {}", level.dimension().location());
   }
}

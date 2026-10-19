package me.wesley1808.servercore.common.utils;

import java.util.List;
import me.wesley1808.servercore.common.config.data.mob_spawning.EnforcedMobcap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.LocalMobCapCalculator.MobCounts;
import net.minecraft.world.level.NaturalSpawner.SpawnState;

public class Mobcaps {
   public static final MobCounts EMPTY_MOBCOUNTS = new MobCounts();
   public static final int MAGIC_NUMBER = (int)Math.pow(17.0, 2.0);

   public static boolean canSpawnForCategory(Level level, ChunkPos pos, MobCategory category, EnforcedMobcap config) {
      return !(level instanceof ServerLevel serverLevel && !canSpawnForCategory(serverLevel, pos, category, config));
   }

   public static boolean canSpawnForCategory(ServerLevel level, ChunkPos pos, MobCategory category, EnforcedMobcap config) {
      SpawnState state = level.getChunkSource().getLastSpawnState();
      if (state != null && category != MobCategory.MISC && config.enforcesMobcap()) {
         int capacity = category.getMaxInstancesPerChunk() + config.additionalCapacity();
         int globalCapacity = toGlobalCapacity(state, capacity);
         int globalCount = state.getMobCategoryCounts().getInt(category);
         if (globalCount >= globalCapacity) {
            return false;
         }

         for (ServerPlayer player : getPlayersNear(level, pos, state.localMobCapCalculator)) {
            MobCounts mobCounts = (MobCounts)state.localMobCapCalculator.playerMobCounts.get(player);
            if (mobCounts == null || mobCounts.counts.getOrDefault(category, 0) < capacity) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static int toGlobalCapacity(SpawnState state, int capacity) {
      return capacity * state.getSpawnableChunkCount() / MAGIC_NUMBER;
   }

   private static List<ServerPlayer> getPlayersNear(ServerLevel level, ChunkPos pos, LocalMobCapCalculator calculator) {
      return Environment.MOD_VMP ? level.getChunkSource().chunkMap.getPlayersCloseForSpawning(pos) : calculator.getPlayersNear(pos);
   }
}

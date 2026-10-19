package com.chronodawn.core.dimension;

import com.chronodawn.ChronoDawn;
import com.chronodawn.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class ChronoDawnDimension {
   public static ResourceKey<Level> getDimensionKey() {
      return ModDimensions.CHRONO_DAWN_DIMENSION;
   }

   public static boolean isChronoDawnDimension(Level level) {
      return level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION);
   }

   public static BlockPos getSpawnLocation(ServerLevel level) {
      BlockPos worldSpawn = level.getSharedSpawnPos();
      return new BlockPos(worldSpawn.getX(), 64, worldSpawn.getZ());
   }

   public static void onPlayerEnterDimension(ServerPlayer player) {
      ChronoDawn.LOGGER.debug("Player {} entered ChronoDawn dimension", player.getName().getString());
   }

   public static void onPlayerLeaveDimension(ServerPlayer player) {
      ChronoDawn.LOGGER.debug("Player {} left ChronoDawn dimension", player.getName().getString());
   }

   public static void init() {
      ChronoDawn.LOGGER.debug("ChronoDawn dimension logic initialized");
   }
}

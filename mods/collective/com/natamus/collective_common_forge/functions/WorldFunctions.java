package com.natamus.collective_common_forge.functions;

import java.io.File;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.LevelResource;

public class WorldFunctions {
   public static void setWorldTime(ServerLevel serverLevel, Integer time) {
      if (time >= 0 && time <= 24000) {
         int days = getTotalDaysPassed(serverLevel);
         serverLevel.setDayTime(time.intValue() + days * 24000L);
      }
   }

   public static int getTotalTimePassed(ServerLevel serverLevel) {
      return (int)serverLevel.getDayTime();
   }

   public static int getTotalDaysPassed(ServerLevel serverLevel) {
      int currenttime = getTotalTimePassed(serverLevel);
      return (int)Math.floor(currenttime / 24000.0);
   }

   public static int getWorldTime(ServerLevel serverLevel) {
      return getTotalTimePassed(serverLevel) - getTotalDaysPassed(serverLevel) * 24000;
   }

   public static String getWorldDimensionName(Level level) {
      return level.dimension().location().toString();
   }

   public static boolean isOverworld(Level level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("overworld");
   }

   public static boolean isNether(Level level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("nether");
   }

   public static boolean isEnd(Level level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("end");
   }

   public static Level getWorldIfInstanceOfAndNotRemote(LevelAccessor levelAccessor) {
      if (levelAccessor.isClientSide()) {
         return null;
      } else {
         return levelAccessor instanceof Level ? (Level)levelAccessor : null;
      }
   }

   public static String getWorldPath(ServerLevel serverLevel) {
      return getWorldPath(serverLevel.getServer());
   }

   public static String getWorldPath(MinecraftServer minecraftServer) {
      String worldpath = minecraftServer.getWorldPath(LevelResource.ROOT).toString();
      return worldpath.substring(0, worldpath.length() - 2);
   }

   public static String getWorldFolderName(ServerLevel serverLevel) {
      return getWorldFolderName(serverLevel.getServer());
   }

   public static String getWorldFolderName(MinecraftServer minecraftServer) {
      String worldPath = getWorldPath(minecraftServer);
      return worldPath.substring(worldPath.lastIndexOf(File.separator) + 1);
   }
}

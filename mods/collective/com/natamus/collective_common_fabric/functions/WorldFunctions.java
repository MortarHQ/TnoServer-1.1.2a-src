package com.natamus.collective_common_fabric.functions;

import java.io.File;
import net.minecraft.class_1936;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import net.minecraft.class_5218;
import net.minecraft.server.MinecraftServer;

public class WorldFunctions {
   public static void setWorldTime(class_3218 serverLevel, Integer time) {
      if (time >= 0 && time <= 24000) {
         int days = getTotalDaysPassed(serverLevel);
         serverLevel.method_29199(time.intValue() + days * 24000L);
      }
   }

   public static int getTotalTimePassed(class_3218 serverLevel) {
      return (int)serverLevel.method_8532();
   }

   public static int getTotalDaysPassed(class_3218 serverLevel) {
      int currenttime = getTotalTimePassed(serverLevel);
      return (int)Math.floor(currenttime / 24000.0);
   }

   public static int getWorldTime(class_3218 serverLevel) {
      return getTotalTimePassed(serverLevel) - getTotalDaysPassed(serverLevel) * 24000;
   }

   public static String getWorldDimensionName(class_1937 level) {
      return level.method_27983().method_29177().toString();
   }

   public static boolean isOverworld(class_1937 level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("overworld");
   }

   public static boolean isNether(class_1937 level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("nether");
   }

   public static boolean isEnd(class_1937 level) {
      return getWorldDimensionName(level).toLowerCase().endsWith("end");
   }

   public static class_1937 getWorldIfInstanceOfAndNotRemote(class_1936 levelAccessor) {
      if (levelAccessor.method_8608()) {
         return null;
      } else {
         return levelAccessor instanceof class_1937 ? (class_1937)levelAccessor : null;
      }
   }

   public static String getWorldPath(class_3218 serverLevel) {
      return getWorldPath(serverLevel.method_8503());
   }

   public static String getWorldPath(MinecraftServer minecraftServer) {
      String worldpath = minecraftServer.method_27050(class_5218.field_24188).toString();
      return worldpath.substring(0, worldpath.length() - 2);
   }

   public static String getWorldFolderName(class_3218 serverLevel) {
      return getWorldFolderName(serverLevel.method_8503());
   }

   public static String getWorldFolderName(MinecraftServer minecraftServer) {
      String worldPath = getWorldPath(minecraftServer);
      return worldPath.substring(worldPath.lastIndexOf(File.separator) + 1);
   }
}

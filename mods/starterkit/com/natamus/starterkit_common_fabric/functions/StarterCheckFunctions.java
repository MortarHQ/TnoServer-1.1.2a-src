package com.natamus.starterkit_common_fabric.functions;

import com.natamus.collective_common_fabric.functions.WorldFunctions;
import com.natamus.starterkit_common_fabric.data.Variables;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.server.MinecraftServer;

public class StarterCheckFunctions {
   public static boolean shouldPlayerReceiveStarterKit(class_1657 player) {
      return shouldPlayerReceiveStarterKit(player.method_37908(), player);
   }

   public static boolean shouldPlayerReceiveStarterKit(class_1937 level, class_1657 player) {
      if (level.field_9236) {
         return false;
      }

      MinecraftServer minecraftServer = level.method_8503();
      if (!minecraftServer.method_3816()) {
         String levelName = WorldFunctions.getWorldFolderName(minecraftServer);
         if (Variables.trackingMap.get("singleplayer").containsKey(levelName)) {
            return !Variables.trackingMap.get("singleplayer").get(levelName);
         }
      } else {
         String rawUUID = player.method_5845();
         if (Variables.trackingMap.get("multiplayer").containsKey(rawUUID)) {
            return !Variables.trackingMap.get("multiplayer").get(rawUUID);
         }
      }

      return true;
   }

   public static void addPlayerToTrackingMap(class_1657 player) {
      addPlayerToTrackingMap(player.method_37908(), player);
   }

   public static void addPlayerToTrackingMap(class_1937 level, class_1657 player) {
      if (!level.field_9236) {
         MinecraftServer minecraftServer = level.method_8503();
         if (!minecraftServer.method_3816()) {
            String levelName = WorldFunctions.getWorldFolderName(minecraftServer);
            Variables.trackingMap.get("singleplayer").put(levelName, true);
         }

         String rawUUID = player.method_5845();
         Variables.trackingMap.get("multiplayer").put(rawUUID, true);
         StarterDataFunctions.writeTrackingMapToJsonFile(minecraftServer);
      }
   }
}

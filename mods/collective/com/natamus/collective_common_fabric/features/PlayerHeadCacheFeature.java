package com.natamus.collective_common_fabric.features;

import com.mojang.authlib.GameProfile;
import com.natamus.collective_common_fabric.data.FeatureFlags;
import com.natamus.collective_common_fabric.functions.HeadFunctions;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_3218;

public class PlayerHeadCacheFeature {
   public static HashMap<String, class_1799> cachedPlayerHeadsMap = new HashMap<>();
   public static HashMap<String, UUID> cachedPlayerNamesMap = new HashMap<>();
   public static HashMap<UUID, String> cachedPlayerUUIDsMap = new HashMap<>();
   public static HashMap<UUID, GameProfile> cachedGameProfileMap = new HashMap<>();

   public static class_1799 cachePlayer(class_1657 player) {
      return cachePlayer(player.method_5477().getString(), player.method_7334());
   }

   public static class_1799 cachePlayer(class_3218 serverLevel, String playerName) {
      if (cachedPlayerHeadsMap.containsKey(playerName)) {
         return cachedPlayerHeadsMap.get(playerName).method_7972();
      }

      class_1799 headStack = HeadFunctions.getNewPlayerHead(serverLevel, playerName, 1);
      if (headStack == null) {
         return null;
      }

      cachedPlayerHeadsMap.put(playerName, headStack);
      return headStack.method_7972();
   }

   public static class_1799 cachePlayer(String playerName, GameProfile gameProfile) {
      if (cachedPlayerHeadsMap.containsKey(playerName)) {
         return cachedPlayerHeadsMap.get(playerName).method_7972();
      }

      class_1799 headStack = HeadFunctions.getNewPlayerHead(gameProfile, 1);
      if (headStack == null) {
         return null;
      }

      cachedPlayerHeadsMap.put(gameProfile.getName(), headStack);
      return headStack.method_7972();
   }

   public static class_1799 getPlayerHeadStackFromCache(class_1657 player) {
      return cachePlayer(player);
   }

   public static class_1799 getPlayerHeadStackFromCache(class_3218 serverLevel, String playerName) {
      return cachedPlayerHeadsMap.containsKey(playerName) ? cachedPlayerHeadsMap.get(playerName).method_7972() : cachePlayer(serverLevel, playerName);
   }

   public static boolean isHeadCachingEnabled() {
      return FeatureFlags.shouldCachePlayerHeads;
   }

   public static void enableHeadCaching() {
      FeatureFlags.shouldCachePlayerHeads = true;
   }

   public static boolean resetPlayerHeadCache() {
      cachedPlayerHeadsMap = new HashMap<>();
      return true;
   }
}

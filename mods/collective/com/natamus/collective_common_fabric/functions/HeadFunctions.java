package com.natamus.collective_common_fabric.functions;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.natamus.collective_common_fabric.features.PlayerHeadCacheFeature;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3312;
import net.minecraft.class_4844;
import net.minecraft.class_9296;
import net.minecraft.class_9334;
import net.minecraft.server.MinecraftServer;

public class HeadFunctions {
   public static class_1799 getNewPlayerHead(class_3218 serverLevel, String playerName, Integer amount) {
      return getNewPlayerHead(serverLevel, playerName, "", amount);
   }

   public static class_1799 getNewPlayerHead(class_3218 serverLevel, String playerName, String noteBlockSound, Integer amount) {
      GameProfile gameProfile = getGameProfileFromPlayerName(serverLevel, playerName);
      return gameProfile == null ? null : getNewPlayerHead(gameProfile, noteBlockSound, amount);
   }

   public static class_1799 getNewPlayerHead(GameProfile gameProfile, Integer amount) {
      return getNewPlayerHead(gameProfile, "", amount);
   }

   public static class_1799 getNewPlayerHead(GameProfile gameProfile, String noteBlockSound, Integer amount) {
      if (gameProfile == null) {
         return null;
      }

      String playerName = gameProfile.getName();
      PropertyMap propertyMap = gameProfile.getProperties();
      String texturePropertyValue = "";

      for (Property textureProperty : propertyMap.get("textures")) {
         if (textureProperty.name().equals("textures")) {
            texturePropertyValue = textureProperty.value();
            break;
         }
      }

      if (!texturePropertyValue.contains("cHJvZmlsZUlk")) {
         return null;
      }

      String textures = "ewogICJ0aW1lc3RhbXAiIDogMCwKICAicHJvZmlsZUlk" + texturePropertyValue.split("cHJvZmlsZUlk")[1];
      class_1799 playerHeadStack = getNewTexturedHead(playerName, textures, gameProfile.getId(), noteBlockSound, amount);
      playerHeadStack.method_57379(class_9334.field_49631, class_2561.method_43470(playerName + "'s Head"));
      return playerHeadStack;
   }

   public static class_1799 getNewTexturedHead(String entityName, String texture, String uuidString, Integer amount) {
      return getNewTexturedHead(entityName, texture, uuidString, "", amount);
   }

   public static class_1799 getNewTexturedHead(String entityName, String texture, String uuidString, String noteBlockSound, Integer amount) {
      UUID uuid = UUIDFunctions.getUUIDFromStringLenient(uuidString);
      int[] intArray = class_4844.method_26275(uuid);
      return getNewTexturedHead(entityName, texture, uuid, noteBlockSound, amount);
   }

   public static class_1799 getNewTexturedHead(String entityName, String texture, UUID uuid, Integer amount) {
      return getNewTexturedHead(entityName, texture, uuid, "", amount);
   }

   public static class_1799 getNewTexturedHead(String entityName, String texture, UUID uuid, String noteBlockSound, Integer amount) {
      if (entityName.length() > 16) {
         entityName = entityName.substring(0, 16);
      }

      class_1799 texturedHeadStack = new class_1799(class_1802.field_8575, amount);
      GameProfile gameProfile = new GameProfile(uuid, entityName.replace(" ", "_"));
      gameProfile.getProperties().put("textures", new Property("textures", texture));
      texturedHeadStack.method_57379(class_9334.field_49617, new class_9296(gameProfile));
      if (!noteBlockSound.equals("")) {
         texturedHeadStack.method_57379(class_9334.field_49618, class_2960.method_60654(noteBlockSound));
      }

      return texturedHeadStack;
   }

   public static GameProfile getGameProfileFromPlayerName(class_3218 serverLevel, String playerName) {
      MinecraftSessionService minecraftSessionService = serverLevel.method_8503().method_3844();
      MinecraftServer minecraftServer = serverLevel.method_8503();
      class_3312 gameProfileCache = minecraftServer.method_3793();
      UUID headUUID;
      if (PlayerHeadCacheFeature.cachedPlayerNamesMap.containsKey(playerName.toLowerCase())) {
         headUUID = PlayerHeadCacheFeature.cachedPlayerNamesMap.get(playerName.toLowerCase());
         playerName = PlayerHeadCacheFeature.cachedPlayerUUIDsMap.get(headUUID);
      } else {
         Optional<GameProfile> playerNameGameProfileOptional = gameProfileCache.method_14515(playerName);
         if (playerNameGameProfileOptional.isEmpty()) {
            return null;
         }

         GameProfile playerNameGameProfile = playerNameGameProfileOptional.get();
         if (playerNameGameProfile == null) {
            return null;
         }

         headUUID = playerNameGameProfile.getId();
         PlayerHeadCacheFeature.cachedPlayerNamesMap.put(playerNameGameProfile.getName().toLowerCase(), headUUID);
         PlayerHeadCacheFeature.cachedPlayerUUIDsMap.put(headUUID, playerNameGameProfile.getName());
      }

      GameProfile gameProfile;
      if (PlayerHeadCacheFeature.cachedGameProfileMap.containsKey(headUUID)) {
         gameProfile = PlayerHeadCacheFeature.cachedGameProfileMap.get(headUUID);
      } else {
         ProfileResult profileResult = minecraftSessionService.fetchProfile(headUUID, false);
         if (profileResult == null) {
            return null;
         }

         gameProfile = profileResult.profile();
         if (gameProfile == null) {
            return null;
         }

         PlayerHeadCacheFeature.cachedGameProfileMap.put(headUUID, gameProfile);
      }

      return gameProfile;
   }

   public static boolean hasStandardHead(String mobname) {
      return mobname.equals("creeper") || mobname.equals("zombie") || mobname.equals("skeleton");
   }
}

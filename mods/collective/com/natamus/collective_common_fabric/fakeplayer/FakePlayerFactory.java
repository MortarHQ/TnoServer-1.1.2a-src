package com.natamus.collective_common_fabric.fakeplayer;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_3218;

public class FakePlayerFactory {
   private static final GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77"), "[Minecraft]");
   private static final Map<FakePlayerFactory.FakePlayerKey, FakePlayer> fakePlayers = Maps.newHashMap();

   public static FakePlayer getMinecraft(class_3218 level) {
      return get(level, MINECRAFT);
   }

   public static FakePlayer get(class_3218 level, GameProfile username) {
      FakePlayerFactory.FakePlayerKey key = new FakePlayerFactory.FakePlayerKey(level, username);
      return fakePlayers.computeIfAbsent(key, k -> new FakePlayer(k.level(), k.username()));
   }

   public static void unloadLevel(class_3218 level) {
      fakePlayers.entrySet().removeIf(entry -> entry.getValue().method_37908() == level);
   }

   private record FakePlayerKey(class_3218 level, GameProfile username) {
   }
}

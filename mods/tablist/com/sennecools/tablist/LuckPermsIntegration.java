package com.sennecools.tablist;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.UUID;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.minecraft.server.level.ServerPlayer;

public final class LuckPermsIntegration {
   private static final LuckPermsIntegration.PlayerMetaData EMPTY_META_DATA = new LuckPermsIntegration.PlayerMetaData("", "", "");

   private LuckPermsIntegration() {
   }

   private static User getUser(UUID playerId) {
      try {
         return LuckPermsProvider.get().getUserManager().getUser(playerId);
      } catch (IllegalStateException exception) {
         return null;
      }
   }

   public static LuckPermsIntegration.PlayerMetaData getPlayerMetaData(ServerPlayer player) {
      return getPlayerMetaData(player.getUUID());
   }

   static LuckPermsIntegration.PlayerMetaData getPlayerMetaData(UUID playerId) {
      User user = getUser(playerId);
      if (user == null) {
         return EMPTY_META_DATA;
      }

      CachedMetaData metaData = user.getCachedData().getMetaData(user.getQueryOptions());
      return new LuckPermsIntegration.PlayerMetaData(
         Objects.requireNonNullElse(metaData.getPrefix(), ""), Objects.requireNonNullElse(metaData.getSuffix(), ""), user.getPrimaryGroup()
      );
   }

   public static int getGroupWeight(ServerPlayer player) {
      return getGroupWeight(player.getUUID());
   }

   static int getGroupWeight(UUID playerId) {
      User user = getUser(playerId);
      return user == null
         ? 0
         : user.getInheritedGroups(user.getQueryOptions())
            .stream()
            .<OptionalInt>map(Group::getWeight)
            .filter(weight -> weight.isPresent())
            .mapToInt(weight -> weight.getAsInt())
            .max()
            .orElse(0);
   }

   public record PlayerMetaData(String prefix, String suffix, String primaryGroup) {
   }
}

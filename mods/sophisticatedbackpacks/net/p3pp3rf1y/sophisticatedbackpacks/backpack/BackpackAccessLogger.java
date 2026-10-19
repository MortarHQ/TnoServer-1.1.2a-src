package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class BackpackAccessLogger {
   private static final int REFRESH_INTERVAL_SECONDS = 30;
   private static long lastCacheRefresh = 0L;
   private static final Map<String, Set<AccessLogRecord>> playerLogCache = new HashMap<>();

   private BackpackAccessLogger() {
   }

   public static void logPlayerAccess(Player player, Item backpackItem, UUID backpackUuid, String backpackName, int clothColor, int trimColor, int columnsTaken) {
      if (!player.level().isClientSide) {
         BackpackStorage.get()
            .putAccessLog(
               new AccessLogRecord(
                  BuiltInRegistries.ITEM.getKey(backpackItem),
                  backpackUuid,
                  player.getGameProfile().getName(),
                  backpackName,
                  clothColor,
                  trimColor,
                  Util.getEpochMillis(),
                  columnsTaken
               )
            );
      }
   }

   public static Set<String> getPlayerNames() {
      initPlayerBackpackCache();
      return playerLogCache.keySet();
   }

   public static Collection<AccessLogRecord> getBackpackLogsForPlayer(String playerName) {
      initPlayerBackpackCache();
      return playerLogCache.getOrDefault(playerName, new HashSet<>());
   }

   private static void initPlayerBackpackCache() {
      if (lastCacheRefresh + 30000L < Util.getEpochMillis()) {
         lastCacheRefresh = Util.getEpochMillis();
         playerLogCache.clear();
         BackpackStorage.get().getAccessLogs().values().forEach(alr -> playerLogCache.computeIfAbsent(alr.getPlayerName(), name -> new HashSet<>()).add(alr));
      }
   }

   public static Collection<AccessLogRecord> getAllBackpackLogs() {
      return BackpackStorage.get().getAccessLogs().values();
   }

   public static Set<UUID> getBackpackUuids() {
      return BackpackStorage.get().getAccessLogs().keySet();
   }

   public static Optional<AccessLogRecord> getBackpackLog(UUID backpackUuid) {
      return Optional.ofNullable(BackpackStorage.get().getAccessLogs().get(backpackUuid));
   }
}

package dev.ftb.mods.ftbchunks.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;

public final class VisibleClientPlayers {
   private static final Set<UUID> CLIENT_LIST = new HashSet<>();

   public static void updatePlayerList(List<UUID> uuids) {
      CLIENT_LIST.clear();
      CLIENT_LIST.addAll(uuids);
   }

   public static boolean isPlayerVisible(Player player) {
      return CLIENT_LIST.contains(player.getUUID());
   }
}

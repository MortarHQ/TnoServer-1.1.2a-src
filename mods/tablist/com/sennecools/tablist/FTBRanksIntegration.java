package com.sennecools.tablist;

import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import dev.ftb.mods.ftbranks.api.PermissionValue;
import dev.ftb.mods.ftbranks.api.Rank;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;

public class FTBRanksIntegration {
   public static String getPlayerRankName(ServerPlayer player) {
      List<Rank> ranks = FTBRanksAPI.manager().getRanks(player);
      return !ranks.isEmpty() ? ranks.getFirst().getName() : "";
   }

   public static int getPlayerRankPower(ServerPlayer player) {
      List<Rank> ranks = FTBRanksAPI.manager().getRanks(player);
      return !ranks.isEmpty() ? ranks.stream().mapToInt(Rank::getPower).max().orElse(0) : 0;
   }

   public static String getFormattedDisplayName(ServerPlayer player) {
      PermissionValue value = FTBRanksAPI.getPermissionValue(player, "ftbranks.name_format");
      return value.isEmpty() ? null : value.asString().map(format -> format.replace("{name}", player.getGameProfile().getName())).orElse(null);
   }
}

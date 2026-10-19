package com.tensura_guild;

import com.tensura_guild.data.GuildMemberService;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;

public final class GuildGameRules {
   public static Key<BooleanValue> SHOW_GUILD_PREFIX;
   public static Key<BooleanValue> APPLY_LOW_SUCCESS_RATE_REWARD_PENALTY;

   private GuildGameRules() {
   }

   public static void register() {
      SHOW_GUILD_PREFIX = GameRules.register("showTensuraGuildPrefix", Category.CHAT, BooleanValue.create(true, GuildGameRules::onShowGuildPrefixChanged));
      APPLY_LOW_SUCCESS_RATE_REWARD_PENALTY = GameRules.register("applyTensuraGuildLowSuccessRateRewardPenalty", Category.PLAYER, BooleanValue.create(true));
   }

   public static boolean showGuildPrefix(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(SHOW_GUILD_PREFIX);
   }

   public static boolean applyLowSuccessRateRewardPenalty(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(APPLY_LOW_SUCCESS_RATE_REWARD_PENALTY);
   }

   private static void onShowGuildPrefixChanged(MinecraftServer server, BooleanValue value) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         GuildMemberService.syncPublicData(player);
         GuildPlayerEvents.refreshNames(player);
      }
   }
}

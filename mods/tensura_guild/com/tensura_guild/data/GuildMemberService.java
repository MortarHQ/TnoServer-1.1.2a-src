package com.tensura_guild.data;

import com.tensura_guild.GuildPlayerEvents;
import com.tensura_guild.compat.GuildCompatHooks;
import com.tensura_guild.registry.GuildAttachments;
import net.minecraft.server.level.ServerPlayer;

public final class GuildMemberService {
   private GuildMemberService() {
   }

   public static GuildMemberData ensureRegistered(ServerPlayer player) {
      GuildMemberData memberData = (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER);
      if (memberData.hasJoinedGuild()) {
         syncPublicData(player, memberData);
         ensureCurrentTitleAvailable(player, memberData);
         return memberData;
      } else {
         int memberNumber = GuildWorldData.get(player.server).claimNextMemberNumber();
         String biomeId = player.serverLevel().getBiome(player.blockPosition()).unwrapKey().map(key -> key.location().toString()).orElse("minecraft:unknown");
         GuildMemberData updated = memberData.withMemberNumber(memberNumber).withOrigin(biomeId, player.getBlockX(), player.getBlockY(), player.getBlockZ());
         setMemberData(player, updated);
         GuildPlayerEvents.refreshNames(player);
         GuildCompatHooks.onJoinedGuild(player);
         return updated;
      }
   }

   public static void setMemberData(ServerPlayer player, GuildMemberData memberData) {
      player.setData(GuildAttachments.GUILD_MEMBER, memberData);
      player.syncData(GuildAttachments.GUILD_MEMBER);
      ensureCurrentTitleAvailable(player, memberData);
      syncPublicData(player, memberData);
   }

   public static void ensureCurrentTitleAvailable(ServerPlayer player) {
      ensureCurrentTitleAvailable(player, (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER));
   }

   public static void grantTitle(ServerPlayer player, String title) {
      GuildTitleData current = (GuildTitleData)player.getData(GuildAttachments.GUILD_TITLES);
      GuildTitleData updated = current.withTitle(title);
      if (!updated.equals(current)) {
         player.setData(GuildAttachments.GUILD_TITLES, updated);
      }

      player.syncData(GuildAttachments.GUILD_TITLES);
   }

   public static void syncTitleData(ServerPlayer player) {
      player.syncData(GuildAttachments.GUILD_TITLES);
   }

   public static void syncPublicData(ServerPlayer player) {
      syncPublicData(player, (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER));
   }

   public static void syncPublicData(ServerPlayer player, GuildMemberData memberData) {
      player.setData(GuildAttachments.GUILD_MEMBER_PUBLIC, GuildMemberPublicData.from(memberData, GuildPlayerEvents.shouldShowGuildPrefix(player)));
      player.syncData(GuildAttachments.GUILD_MEMBER_PUBLIC);
   }

   private static void ensureCurrentTitleAvailable(ServerPlayer player, GuildMemberData memberData) {
      memberData.title().ifPresent(title -> grantTitle(player, title));
   }
}

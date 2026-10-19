package com.tensura_guild.data;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

public final class GuildAdvancementService {
   private static final ResourceLocation GUILD_ROOKIE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_rookie");
   private static final ResourceLocation CRAFTSMANS_REQUEST = ResourceLocation.fromNamespaceAndPath("tensura_guild", "craftsmans_request");

   private GuildAdvancementService() {
   }

   public static void grantGuildRookie(ServerPlayer player) {
      grant(player, GUILD_ROOKIE);
   }

   public static void grantCraftsmansRequest(ServerPlayer player) {
      grant(player, CRAFTSMANS_REQUEST);
   }

   private static void grant(ServerPlayer player, ResourceLocation advancementId) {
      AdvancementHolder advancement = player.server.getAdvancements().get(advancementId);
      if (advancement != null) {
         PlayerAdvancements playerAdvancements = player.getAdvancements();
         AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancement);
         if (!progress.isDone()) {
            for (String criterion : progress.getRemainingCriteria()) {
               playerAdvancements.award(advancement, criterion);
            }
         }
      }
   }
}

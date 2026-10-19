package com.tensura_guild.data;

import com.tensura_guild.GuildGameRules;
import com.tensura_guild.registry.GuildAttachments;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record GuildTaskRewardPenalty(int currencyPenaltyPercent, int experiencePenaltyPercent, boolean suppressNonCurrencyItems) {
   public static final GuildTaskRewardPenalty NONE = new GuildTaskRewardPenalty(0, 0, false);
   private static final Set<ResourceLocation> CURRENCY_ITEMS = Set.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "bronze_coin"),
      ResourceLocation.fromNamespaceAndPath("tensura", "silver_coin"),
      ResourceLocation.fromNamespaceAndPath("tensura", "gold_coin"),
      ResourceLocation.fromNamespaceAndPath("tensura", "stellar_gold_coin")
   );

   public static GuildTaskRewardPenalty forPlayer(ServerPlayer player) {
      if (player != null && GuildGameRules.applyLowSuccessRateRewardPenalty(player)) {
         GuildMemberData memberData = (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER);
         int resolvedTasks = memberData.successfulTasks() + memberData.failureCount();
         if (resolvedTasks <= 0) {
            return NONE;
         } else {
            double successRate = memberData.successfulTasks() * 100.0 / resolvedTasks;
            if (successRate < 60.0) {
               return new GuildTaskRewardPenalty(20, 3, true);
            } else if (successRate < 70.0) {
               return new GuildTaskRewardPenalty(20, 3, false);
            } else if (successRate < 80.0) {
               return new GuildTaskRewardPenalty(10, 2, false);
            } else {
               return successRate < 90.0 ? new GuildTaskRewardPenalty(5, 1, false) : NONE;
            }
         }
      } else {
         return NONE;
      }
   }

   public static boolean isCurrencyItem(ResourceLocation itemId) {
      return CURRENCY_ITEMS.contains(itemId);
   }

   public int adjustItemRewardAmount(ResourceLocation itemId, int amount) {
      if (isCurrencyItem(itemId)) {
         return this.adjustCurrencyAmount(amount);
      } else {
         return this.suppressNonCurrencyItems ? 0 : Math.max(0, amount);
      }
   }

   public int adjustCurrencyAmount(int amount) {
      if (amount > 0 && this.currencyPenaltyPercent > 0) {
         int penalty = Math.max(1, (int)Math.ceil(amount * (this.currencyPenaltyPercent / 100.0)));
         return Math.max(1, amount - penalty);
      } else {
         return Math.max(0, amount);
      }
   }

   public int adjustExperienceAmount(int amount) {
      if (amount > 0 && this.experiencePenaltyPercent > 0) {
         int penalty = (int)Math.round(amount * (this.experiencePenaltyPercent / 100.0));
         return Math.max(1, amount - Math.max(0, penalty));
      } else {
         return Math.max(0, amount);
      }
   }
}

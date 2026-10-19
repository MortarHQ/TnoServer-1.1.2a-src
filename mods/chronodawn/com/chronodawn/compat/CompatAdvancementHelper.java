package com.chronodawn.compat;

import com.chronodawn.ChronoDawn;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

public class CompatAdvancementHelper {
   public static boolean grantAdvancement(MinecraftServer server, ServerPlayer player, ResourceLocation advancementId) {
      AdvancementHolder advancementHolder = server.getAdvancements().get(advancementId);
      if (advancementHolder == null) {
         ChronoDawn.LOGGER.warn("Advancement not found: {}", advancementId);
         return false;
      }

      PlayerAdvancements playerAdvancements = player.getAdvancements();
      AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancementHolder);
      if (progress.isDone()) {
         return false;
      }

      for (String criterion : progress.getRemainingCriteria()) {
         playerAdvancements.award(advancementHolder, criterion);
      }

      return true;
   }

   public static boolean advancementExists(MinecraftServer server, ResourceLocation advancementId) {
      return server.getAdvancements().get(advancementId) != null;
   }

   public static boolean hasAdvancement(MinecraftServer server, ServerPlayer player, ResourceLocation advancementId) {
      AdvancementHolder advancementHolder = server.getAdvancements().get(advancementId);
      if (advancementHolder == null) {
         return false;
      }

      PlayerAdvancements playerAdvancements = player.getAdvancements();
      AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancementHolder);
      return progress.isDone();
   }
}

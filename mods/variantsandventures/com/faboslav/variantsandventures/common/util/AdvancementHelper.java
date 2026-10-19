package com.faboslav.variantsandventures.common.util;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

public final class AdvancementHelper {
   public static void triggerMonsterHunter(Level level, DamageSource damageSource) {
      if (!level.isClientSide()) {
         MinecraftServer server = level.getServer();
         if (server != null) {
            ResourceLocation advancementId = VariantsAndVentures.makeNamespacedId("minecraft:adventure/kill_a_mob");
            AdvancementHolder advancement = server.getAdvancements().get(advancementId);
            if (advancement != null) {
               if (damageSource.getEntity() instanceof ServerPlayer player) {
                  AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
                  if (!progress.isDone()) {
                     for (String criterion : progress.getRemainingCriteria()) {
                        player.getAdvancements().award(advancement, criterion);
                     }
                  }
               }
            }
         }
      }
   }
}

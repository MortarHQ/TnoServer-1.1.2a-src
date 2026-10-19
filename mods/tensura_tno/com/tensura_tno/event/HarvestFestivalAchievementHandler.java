package com.tensura_tno.event;

import com.tensura_tno.TensuraTNOMod;
import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.EnterHarvestFestivalEvent;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;

public final class HarvestFestivalAchievementHandler {
   private static final ResourceLocation ADVANCEMENT_ID = ResourceLocation.fromNamespaceAndPath("tensura_tno", "true_demon_lord_birth");
   private static final String CRITERION_NAME = "trigger";
   private static volatile boolean registered = false;

   private HarvestFestivalAchievementHandler() {
   }

   public static void register() {
      if (!registered) {
         synchronized (HarvestFestivalAchievementHandler.class) {
            if (!registered) {
               TensuraEntityEvents.ENTER_HARVEST_FESTIVAL_EVENT.register((EnterHarvestFestivalEvent)(entity, time, soul) -> {
                  if (entity instanceof ServerPlayer player) {
                     grant(player);
                  }

                  return EventResult.pass();
               });
               registered = true;
            }
         }
      }
   }

   private static void grant(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server != null) {
         ServerAdvancementManager manager = server.getAdvancements();
         AdvancementHolder holder = manager.get(ADVANCEMENT_ID);
         if (holder == null) {
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Advancement {} not found, cannot grant 真王降诞 to {}", ADVANCEMENT_ID, player.getGameProfile().getName());
         } else {
            PlayerAdvancements progress = player.getAdvancements();
            if (!progress.getOrStartProgress(holder).isDone()) {
               progress.award(holder, "trigger");
            }
         }
      }
   }
}

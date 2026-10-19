package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.extra.MorphSkill;
import com.github.wal_bos.moreskills.net.msg.MorphSyncPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StartTracking;

public final class MorphSyncHandler {
   private MorphSyncHandler() {
   }

   public static void syncToTrackingAndSelf(LivingEntity entity, String entityId) {
      if (entity instanceof ServerPlayer morphed) {
         PlayerList players = morphed.server.getPlayerList();
         MorphSyncPayload payload = new MorphSyncPayload(morphed.getUUID(), entityId == null ? "" : entityId);

         for (ServerPlayer viewer : players.getPlayers()) {
            if (viewer == morphed || viewer.level() == morphed.level() && viewer.distanceToSqr(morphed) <= 4096.0) {
               NetworkManager.sendToPlayer(viewer, payload);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onStartTracking(StartTracking event) {
      if (event.getEntity() instanceof ServerPlayer viewer) {
         if (event.getTarget() instanceof ServerPlayer morphed) {
            SkillAPI.getSkillsFrom(morphed).getSkill((ManasSkill)AllSkills.MORPH.get()).ifPresent(instance -> {
               String morphId = MorphSkill.getMorphIdString(instance);
               if (!morphId.isBlank()) {
                  NetworkManager.sendToPlayer(viewer, new MorphSyncPayload(morphed.getUUID(), morphId));
               }
            });
         }
      }
   }

   @SubscribeEvent
   public static void onLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.MORPH.get()).ifPresent(instance -> {
            String morphId = MorphSkill.getMorphIdString(instance);
            if (!morphId.isBlank()) {
               syncToTrackingAndSelf(player, morphId);
            }
         });
      }
   }
}

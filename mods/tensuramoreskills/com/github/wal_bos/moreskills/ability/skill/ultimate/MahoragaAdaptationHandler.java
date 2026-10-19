package com.github.wal_bos.moreskills.ability.skill.ultimate;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public final class MahoragaAdaptationHandler {
   private static boolean initialized = false;

   private MahoragaAdaptationHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MahoragaAdaptationHandler::onIncomingDamage);
         NeoForge.EVENT_BUS.addListener(MahoragaAdaptationHandler::onFinalDamage);
         NeoForge.EVENT_BUS.addListener(MahoragaAdaptationHandler::onClone);
         NeoForge.EVENT_BUS.addListener(MahoragaAdaptationHandler::onLogin);
      }
   }

   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (DivineGeneralMahoragaSkill.hasMahoraga(player)) {
            DivineGeneralMahoragaSkill.handleIncomingDamage(player, event.getSource(), event.getAmount());
         }
      }
   }

   public static void onFinalDamage(Pre event) {
      if (event.getEntity() instanceof LivingEntity target) {
         if (target instanceof ServerPlayer player && DivineGeneralMahoragaSkill.hasMahoraga(player)) {
            DivineGeneralMahoragaSkill.handlePostDamageReduction(player, event.getSource(), event);
         }

         if (event.getSource().getEntity() instanceof ServerPlayer attacker && DivineGeneralMahoragaSkill.hasMahoraga(attacker) && attacker != target) {
            DivineGeneralMahoragaSkill.handleOutgoingStageEffects(attacker, target, event.getSource(), event);
            DivineGeneralMahoragaSkill.handleOutgoingDamage(attacker, target, event.getSource());
         }
      }
   }

   public static void onClone(Clone event) {
      if (event.getOriginal() instanceof ServerPlayer oldPlayer) {
         if (event.getEntity() instanceof ServerPlayer newPlayer) {
            DivineGeneralMahoragaSkill.onClone(oldPlayer, newPlayer, event.isWasDeath());
         }
      }
   }

   public static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         DivineGeneralMahoragaSkill.sanitizeOnLogin(player);
      }
   }
}

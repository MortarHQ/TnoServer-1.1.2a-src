package com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent.Pre;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.PickupXp;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.XpChange;

public final class ReplayEventInterceptors {
   private ReplayEventInterceptors() {
   }

   @SubscribeEvent
   public static void onAdvancementEarn(AdvancementEarnEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         ReplayGuard.auditBlocked("event", "advancement", player.getScoreboardName());
      }
   }

   @SubscribeEvent
   public static void onXpChange(XpChange event) {
      if (event.getEntity() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         if (event.getAmount() != 0) {
            ReplayGuard.auditBlocked("event", "xpChange(" + event.getAmount() + ")", player.getScoreboardName());
            event.setAmount(0);
         }
      }
   }

   @SubscribeEvent
   public static void onXpPickup(PickupXp event) {
      if (event.getEntity() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         event.setCanceled(true);
         ReplayGuard.auditBlocked("event", "xpPickup", player.getScoreboardName());
      }
   }

   @SubscribeEvent
   public static void onItemPickup(Pre event) {
      if (event.getPlayer() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         event.setCanPickup(TriState.FALSE);
         ReplayGuard.auditBlocked("event", "itemPickup", player.getScoreboardName());
      }
   }

   @SubscribeEvent
   public static void onItemCraft(ItemCraftedEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         ReplayGuard.auditBlocked("event", "itemCraft", player.getScoreboardName());
      }
   }

   @SubscribeEvent
   public static void onItemSmelt(ItemSmeltedEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && ReplayGuard.shouldBlockPlayer(player)) {
         ReplayGuard.auditBlocked("event", "itemSmelt", player.getScoreboardName());
      }
   }
}

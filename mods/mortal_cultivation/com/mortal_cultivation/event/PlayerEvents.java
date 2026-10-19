package com.mortal_cultivation.event;

import com.mortal_cultivation.registry.item.CultivationItems;
import com.mortal_cultivation.registry.race.CultivationRaces;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootRaceHandler;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class PlayerEvents {
   private static final String STELLAR_PILL_COOLDOWN_TAG = "StellarPillCooldown";

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      Player player = event.getEntity();
      if (player instanceof ServerPlayer) {
         CompoundTag playerData = player.getPersistentData();
         CompoundTag modData = getOrCreateModData(playerData);
         Item stellarPill = (Item)CultivationItems.STELLAR_CORE_PILL.get();
         if (player.getCooldowns().isOnCooldown(stellarPill)) {
            float remainingPercent = player.getCooldowns().getCooldownPercent(stellarPill, 0.0F);
            int totalCooldown = 12000;
            int remainingCooldown = (int)(remainingPercent * totalCooldown);
            modData.putInt("StellarPillCooldown", remainingCooldown);
         } else {
            modData.remove("StellarPillCooldown");
         }

         ensureModDataSaved(playerData, modData);
      }
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      Player player = event.getEntity();
      if (player instanceof ServerPlayer serverPlayer) {
         if (SpiritRootData.hasRoot(serverPlayer) && !SpiritRootRaceHandler.shouldKeepRoot(serverPlayer)) {
            SpiritRootData.clearRoot(serverPlayer, true);
         }

         CompoundTag playerData = player.getPersistentData();
         if (playerData.contains("PlayerPersisted")) {
            CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
            if (persistedData.contains("mortal_cultivation")) {
               CompoundTag modData = persistedData.getCompound("mortal_cultivation");
               if (modData.contains("StellarPillCooldown")) {
                  int cooldown = modData.getInt("StellarPillCooldown");
                  if (cooldown > 0) {
                     player.getCooldowns().addCooldown((Item)CultivationItems.STELLAR_CORE_PILL.get(), cooldown);
                  }
               }
            }
         }

         boolean isFoundationPlayer = RaceAPI.getRaceFrom(player)
            .getRace()
            .map(instance -> instance.getRace() == CultivationRaces.FOUNDATION_ESTABLISHMENT_RACE.get())
            .orElse(false);
         if (isFoundationPlayer && !SpiritRootData.hasRoot(serverPlayer)) {
            SpiritRootData.backfillFoundationRoot(serverPlayer);
         } else {
            SpiritRootData.syncCurrentRoot(serverPlayer);
         }
      }
   }

   private static CompoundTag getOrCreateModData(CompoundTag playerData) {
      if (!playerData.contains("PlayerPersisted")) {
         playerData.put("PlayerPersisted", new CompoundTag());
      }

      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      if (!persistedData.contains("mortal_cultivation")) {
         persistedData.put("mortal_cultivation", new CompoundTag());
      }

      return persistedData.getCompound("mortal_cultivation");
   }

   private static void ensureModDataSaved(CompoundTag playerData, CompoundTag modData) {
      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      persistedData.put("mortal_cultivation", modData);
      playerData.put("PlayerPersisted", persistedData);
   }
}

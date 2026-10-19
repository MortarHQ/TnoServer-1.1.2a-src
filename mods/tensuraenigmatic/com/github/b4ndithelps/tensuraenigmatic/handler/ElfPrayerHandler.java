package com.github.b4ndithelps.tensuraenigmatic.handler;

import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class ElfPrayerHandler {
   private static final String ENIGMATIC_MOD_ID = "enigmaticlegacyplus";
   private static final String NBT_KEY_RING_RECEIVED = "tensuraenigmatic_elf_ring_received";
   private static final String DATA_NAME = "tensuraenigmatic_ring_received";

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && player.tickCount % 20 == 0) {
         if (!hasReceivedRingOfTheElves(player) && meetsRingOfTheElvesRequirement(player)) {
            giveRingOfTheElves(player);
            markRingOfTheElvesReceived(player);
         }
      }
   }

   private static boolean meetsRingOfTheElvesRequirement(ServerPlayer player) {
      return ModList.get().isLoaded("enigmaticlegacyplus") ? TensuraEnergyHelper.hasAnySpirit(player) : TensuraEnergyHelper.hasAllCommonSpirits(player);
   }

   private static boolean hasReceivedRingOfTheElves(ServerPlayer player) {
      String playerUUID = player.getUUID().toString();
      if (player.getPersistentData().getBoolean("tensuraenigmatic_elf_ring_received_" + playerUUID)) {
         return true;
      } else {
         RingReceivedData savedData = getSavedData(player);
         if (savedData.hasPlayerReceived(playerUUID)) {
            player.getPersistentData().putBoolean("tensuraenigmatic_elf_ring_received_" + playerUUID, true);
            return true;
         } else {
            return false;
         }
      }
   }

   private static void markRingOfTheElvesReceived(ServerPlayer player) {
      String playerUUID = player.getUUID().toString();
      player.getPersistentData().putBoolean("tensuraenigmatic_elf_ring_received_" + playerUUID, true);
      getSavedData(player).addPlayer(playerUUID);
   }

   private static RingReceivedData getSavedData(ServerPlayer player) {
      Factory<RingReceivedData> factory = new Factory(RingReceivedData::new, (tag, registries) -> new RingReceivedData(tag), null);
      return (RingReceivedData)player.serverLevel().getServer().overworld().getDataStorage().computeIfAbsent(factory, "tensuraenigmatic_ring_received");
   }

   private static void giveRingOfTheElves(ServerPlayer player) {
      ItemStack ring = new ItemStack((ItemLike)TensuraEnigmaticItems.RING_OF_THE_ELVES.get());
      if (player.getInventory().add(ring)) {
         player.sendSystemMessage(Component.translatable("message.tensuraenigmatic.elf_prayer.ring_received").withStyle(ChatFormatting.GOLD));
      } else {
         ItemEntity dropRing = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), ring);
         player.level().addFreshEntity(dropRing);
         player.sendSystemMessage(Component.translatable("message.tensuraenigmatic.elf_prayer.ring_dropped").withStyle(ChatFormatting.GOLD));
      }

      player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
   }
}

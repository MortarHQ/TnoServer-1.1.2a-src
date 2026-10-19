package com.mortal_cultivation.event;

import com.mortal_cultivation.item.FastingTalismanItem;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class FastingTalismanEventHandler {
   private static final Map<UUID, Integer> playerTimers = new HashMap<>();
   private static final int MINUTE_IN_TICKS = 1200;

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && hasFastingTalisman(player)) {
         FoodData foodData = player.getFoodData();
         foodData.setFoodLevel(20);
         foodData.setSaturation(20.0F);
      }
   }

   @SubscribeEvent
   public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
      if (ServerLifecycleHooks.getCurrentServer() != null) {
         for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            UUID playerUUID = player.getUUID();
            int currentTime = playerTimers.getOrDefault(playerUUID, 0);
            if (hasFastingTalisman(player)) {
               if (++currentTime >= 1200) {
                  damageFastingTalisman(player);
                  currentTime = 0;
               }
            }

            playerTimers.put(playerUUID, currentTime);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = event.getEntity();
         playerTimers.remove(player.getUUID());
      }
   }

   private static boolean hasFastingTalisman(Player player) {
      Inventory inventory = player.getInventory();
      if (inventory.getSelected().getItem() != CultivationItems.FASTING_TALISMAN.get()
         && player.getOffhandItem().getItem() != CultivationItems.FASTING_TALISMAN.get()) {
         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == CultivationItems.FASTING_TALISMAN.get()) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static void damageFastingTalisman(Player player) {
      Inventory inventory = player.getInventory();
      if (inventory.getSelected().getItem() == CultivationItems.FASTING_TALISMAN.get()) {
         if (FastingTalismanItem.damageItem(inventory.getSelected(), 1)) {
            inventory.removeItem(inventory.getSelected());
         }
      } else if (player.getOffhandItem().getItem() == CultivationItems.FASTING_TALISMAN.get()) {
         if (FastingTalismanItem.damageItem(player.getOffhandItem(), 1)) {
            player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
         }
      } else {
         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == CultivationItems.FASTING_TALISMAN.get()) {
               if (FastingTalismanItem.damageItem(stack, 1)) {
                  inventory.removeItemNoUpdate(i);
                  inventory.setItem(i, ItemStack.EMPTY);
               }

               return;
            }
         }
      }
   }
}

package com.natamus.starterkit_common_forge.functions;

import com.natamus.collective_common_forge.functions.GearFunctions;
import com.natamus.starterkit_common_forge.data.Constants;
import com.natamus.starterkit_common_forge.data.ConstantsClient;
import com.natamus.starterkit_common_forge.data.VariablesClient;
import com.natamus.starterkit_common_forge.inventory.StarterKitInventoryScreen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class StarterClientFunctions {
   public static void showInitialChooseKitScreen() {
      showInitialChooseKitScreen(ConstantsClient.mc.player);
   }

   public static void showInitialChooseKitScreen(Player player) {
      if (!cacheLocalPlayerEquipment()) {
         Constants.logger.warn("[Starter Kit] Unable to cache the local player's prior equipment.");
      } else {
         removeLocalPlayerEquipment();
         setCachedStarterKitInventory(player, 0);
         if (VariablesClient.cachedStarterKitInventory == null) {
            Constants.logger.warn("[Starter Kit] Cannot initiate StarterKitInventoryScreen, the cached starter kit inventory is null.");
         } else {
            ConstantsClient.mc.setScreen(new StarterKitInventoryScreen(player));
         }
      }
   }

   public static void cycleChooseKitScreen(Player player, boolean next) {
      if (player == null) {
         Constants.logger.warn("[Starter Kit] Unable to cycle the choose kit screen, player is null.");
      } else {
         List<String> activeKitNames = StarterGearFunctions.getActiveKitNames(VariablesClient.cachedStarterGearEntries);
         int kitIndex = 0;

         for (String activeKitName : activeKitNames) {
            if (activeKitName.equals(VariablesClient.cachedStarterKitName)) {
               break;
            }

            kitIndex++;
         }

         if (next) {
            kitIndex++;
         } else {
            kitIndex--;
         }

         if (kitIndex < 0) {
            kitIndex = activeKitNames.size() - 1;
         } else if (kitIndex >= activeKitNames.size()) {
            kitIndex = 0;
         }

         removeLocalPlayerEquipment();
         setCachedStarterKitInventory(player, kitIndex);
         if (VariablesClient.cachedStarterKitInventory == null) {
            Constants.logger.warn("[Starter Kit] Cannot cycle StarterKitInventoryScreen, the cached starter kit inventory is null.");
         } else {
            ConstantsClient.mc.setScreen(new StarterKitInventoryScreen(player));
         }
      }
   }

   public static void setCachedStarterKitInventory(Player player, int kitIndex) {
      Inventory inventory = new Inventory(player);
      if (VariablesClient.cachedStarterGearEntries.size() == 0) {
         Constants.logger.warn("[Starter Kit] Unable to show the choose kit screen, starter gear entries are empty.");
      } else if (kitIndex >= VariablesClient.cachedStarterGearEntries.size()) {
         Constants.logger.warn("[Starter Kit] Unable to show the choose kit screen, kit index higher than the cached starter gear entries.");
      } else {
         String kitName = StarterGearFunctions.getActiveKitNames(VariablesClient.cachedStarterGearEntries).get(kitIndex);
         String gearString = VariablesClient.cachedStarterGearEntries.get(kitName);
         if (gearString == null) {
            Constants.logger
               .warn("[Starter Kit] Unable to show the choose kit screen, gearString is null from kit name '" + kitName + "' and kit index " + kitIndex + ".");
         } else {
            GearFunctions.setInventoryFromGearString(player.level(), inventory, gearString);
            VariablesClient.cachedStarterKitName = kitName;
            VariablesClient.cachedStarterKitInventory = inventory;
            VariablesClient.cachedStarterKitEffects = GearFunctions.getEffectsFromGearString(player, gearString);
         }
      }
   }

   public static boolean cacheLocalPlayerEquipment() {
      LocalPlayer localPlayer = ConstantsClient.mc.player;
      return localPlayer == null ? false : cacheLocalPlayerEquipment(localPlayer);
   }

   public static boolean cacheLocalPlayerEquipment(Player player) {
      VariablesClient.priorPlayerEquipment = new HashMap<>();
      VariablesClient.priorPlayerHotbar = new ArrayList<>();
      player.getInventory().selected = 0;

      for (EquipmentSlot equipmentSlot : Constants.equipmentSlots) {
         VariablesClient.priorPlayerEquipment.put(equipmentSlot, player.getItemBySlot(equipmentSlot).copy());
      }

      for (int i = 0; i < 9; i++) {
         VariablesClient.priorPlayerHotbar.add(player.getInventory().getItem(i).copy());
      }

      return true;
   }

   public static boolean removeLocalPlayerEquipment() {
      LocalPlayer localPlayer = ConstantsClient.mc.player;
      return localPlayer == null ? false : removeLocalPlayerEquipment(localPlayer);
   }

   public static boolean removeLocalPlayerEquipment(Player player) {
      ItemStack airStack = new ItemStack(Items.AIR, 1);

      for (EquipmentSlot equipmentSlot : Constants.equipmentSlots) {
         player.setItemSlot(equipmentSlot, airStack.copy());
      }

      for (int i = 0; i < 9; i++) {
         player.getInventory().setItem(i, airStack.copy());
      }

      return true;
   }

   public static boolean setPriorLocalPlayerEquipment() {
      LocalPlayer localPlayer = ConstantsClient.mc.player;
      return localPlayer == null ? false : setPriorLocalPlayerEquipment(localPlayer);
   }

   public static boolean setPriorLocalPlayerEquipment(Player player) {
      if (VariablesClient.priorPlayerEquipment != null && VariablesClient.priorPlayerHotbar != null) {
         player.getInventory().selected = 0;

         for (EquipmentSlot equipmentSlot : Constants.equipmentSlots) {
            ItemStack slotStack = VariablesClient.priorPlayerEquipment.get(equipmentSlot);
            if (slotStack != null) {
               player.setItemSlot(equipmentSlot, slotStack.copy());
            }
         }

         for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, VariablesClient.priorPlayerHotbar.get(i).copy());
         }

         ConstantsClient.mc.options.hideGui = false;
         return true;
      } else {
         return false;
      }
   }

   public static void clearStarterKitClientCache() {
      VariablesClient.cachedStarterKitName = "";
      VariablesClient.cachedStarterKitInventory = null;
      VariablesClient.cachedStarterKitEffects = new ArrayList<>();
      clearPriorEquipmentCache();
   }

   public static void clearPriorEquipmentCache() {
      VariablesClient.priorPlayerEquipment = null;
      VariablesClient.priorPlayerHotbar = null;
      ConstantsClient.mc.options.hideGui = false;
   }

   public static void selectFirstSlot() {
      Player player = ConstantsClient.mc.player;
      if (player != null) {
         player.getInventory().selected = 0;
      }
   }
}

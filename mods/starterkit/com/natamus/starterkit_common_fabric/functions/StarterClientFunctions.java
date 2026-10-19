package com.natamus.starterkit_common_fabric.functions;

import com.natamus.collective_common_fabric.functions.GearFunctions;
import com.natamus.starterkit_common_fabric.data.Constants;
import com.natamus.starterkit_common_fabric.data.ConstantsClient;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import com.natamus.starterkit_common_fabric.inventory.StarterKitInventoryScreen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_746;

public class StarterClientFunctions {
   public static void showInitialChooseKitScreen() {
      showInitialChooseKitScreen(ConstantsClient.mc.field_1724);
   }

   public static void showInitialChooseKitScreen(class_1657 player) {
      if (!cacheLocalPlayerEquipment()) {
         Constants.logger.warn("[Starter Kit] Unable to cache the local player's prior equipment.");
      } else {
         removeLocalPlayerEquipment();
         setCachedStarterKitInventory(player, 0);
         if (VariablesClient.cachedStarterKitInventory == null) {
            Constants.logger.warn("[Starter Kit] Cannot initiate StarterKitInventoryScreen, the cached starter kit inventory is null.");
         } else {
            ConstantsClient.mc.method_1507(new StarterKitInventoryScreen(player));
         }
      }
   }

   public static void cycleChooseKitScreen(class_1657 player, boolean next) {
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
            ConstantsClient.mc.method_1507(new StarterKitInventoryScreen(player));
         }
      }
   }

   public static void setCachedStarterKitInventory(class_1657 player, int kitIndex) {
      class_1661 inventory = new class_1661(player);
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
            GearFunctions.setInventoryFromGearString(player.method_37908(), inventory, gearString);
            VariablesClient.cachedStarterKitName = kitName;
            VariablesClient.cachedStarterKitInventory = inventory;
            VariablesClient.cachedStarterKitEffects = GearFunctions.getEffectsFromGearString(player, gearString);
         }
      }
   }

   public static boolean cacheLocalPlayerEquipment() {
      class_746 localPlayer = ConstantsClient.mc.field_1724;
      return localPlayer == null ? false : cacheLocalPlayerEquipment(localPlayer);
   }

   public static boolean cacheLocalPlayerEquipment(class_1657 player) {
      VariablesClient.priorPlayerEquipment = new HashMap<>();
      VariablesClient.priorPlayerHotbar = new ArrayList<>();
      player.method_31548().field_7545 = 0;

      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         VariablesClient.priorPlayerEquipment.put(equipmentSlot, player.method_6118(equipmentSlot).method_7972());
      }

      for (int i = 0; i < 9; i++) {
         VariablesClient.priorPlayerHotbar.add(player.method_31548().method_5438(i).method_7972());
      }

      return true;
   }

   public static boolean removeLocalPlayerEquipment() {
      class_746 localPlayer = ConstantsClient.mc.field_1724;
      return localPlayer == null ? false : removeLocalPlayerEquipment(localPlayer);
   }

   public static boolean removeLocalPlayerEquipment(class_1657 player) {
      class_1799 airStack = new class_1799(class_1802.field_8162, 1);

      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         player.method_5673(equipmentSlot, airStack.method_7972());
      }

      for (int i = 0; i < 9; i++) {
         player.method_31548().method_5447(i, airStack.method_7972());
      }

      return true;
   }

   public static boolean setPriorLocalPlayerEquipment() {
      class_746 localPlayer = ConstantsClient.mc.field_1724;
      return localPlayer == null ? false : setPriorLocalPlayerEquipment(localPlayer);
   }

   public static boolean setPriorLocalPlayerEquipment(class_1657 player) {
      if (VariablesClient.priorPlayerEquipment != null && VariablesClient.priorPlayerHotbar != null) {
         player.method_31548().field_7545 = 0;

         for (class_1304 equipmentSlot : Constants.equipmentSlots) {
            class_1799 slotStack = VariablesClient.priorPlayerEquipment.get(equipmentSlot);
            if (slotStack != null) {
               player.method_5673(equipmentSlot, slotStack.method_7972());
            }
         }

         for (int i = 0; i < 9; i++) {
            player.method_31548().method_5447(i, VariablesClient.priorPlayerHotbar.get(i).method_7972());
         }

         ConstantsClient.mc.field_1690.field_1842 = false;
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
      ConstantsClient.mc.field_1690.field_1842 = false;
   }

   public static void selectFirstSlot() {
      class_1657 player = ConstantsClient.mc.field_1724;
      if (player != null) {
         player.method_31548().field_7545 = 0;
      }
   }
}

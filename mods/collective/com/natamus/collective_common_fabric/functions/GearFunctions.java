package com.natamus.collective_common_fabric.functions;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective_common_fabric.data.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2371;
import net.minecraft.class_2378;
import net.minecraft.class_2487;
import net.minecraft.class_2522;
import net.minecraft.class_2960;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_7924;

public class GearFunctions {
   public static void setPlayerGearFromGearString(class_1657 player, String gearString) {
      setPlayerGearFromGearString(player, gearString, false);
   }

   public static void setPlayerGearFromGearString(class_1657 player, String gearString, boolean parseActiveEffects) {
      class_1937 level = player.method_37908();
      class_2378<class_1291> mobEffectRegistry = null;
      if (parseActiveEffects) {
         mobEffectRegistry = level.method_30349().method_30530(class_7924.field_41208);
         player.method_6012();
      }

      boolean emptiedInventory = false;

      for (String line : getGearStringSplit(gearString)) {
         String slotString = getSlotStringFromLine(line);
         if (slotString.equalsIgnoreCase("effects") && parseActiveEffects) {
            if (mobEffectRegistry != null) {
               for (class_1293 mobEffectInstance : getEffectsFromLine(mobEffectRegistry, line)) {
                  player.method_6092(mobEffectInstance);
               }
            }
         } else {
            class_1799 itemStack = getItemStackFromGearStringLine(level, line);
            if (itemStack != null && !itemStack.method_7960()) {
               if (!emptiedInventory) {
                  player.method_31548().method_5448();
                  emptiedInventory = true;
               }

               if (NumberFunctions.isNumeric(slotString)) {
                  player.method_31548().method_5447(Integer.parseInt(slotString), itemStack);
               } else {
                  class_1304 equipmentSlot = getEquipmentSlotFromSlotString(slotString);
                  if (equipmentSlot != null) {
                     player.method_5673(equipmentSlot, itemStack);
                  }
               }
            }
         }
      }
   }

   public static void setInventoryFromGearString(class_1937 level, class_1661 inventory, String gearString) {
      for (String line : getGearStringSplit(gearString)) {
         class_1799 itemStack = getItemStackFromGearStringLine(level, line);
         if (itemStack != null && !itemStack.method_7960()) {
            String slotString = getSlotStringFromLine(line);
            if (NumberFunctions.isNumeric(slotString)) {
               inventory.method_5447(Integer.parseInt(slotString), itemStack);
            } else {
               class_1304 equipmentSlot = getEquipmentSlotFromSlotString(slotString);
               if (equipmentSlot.equals(class_1304.field_6169)) {
                  inventory.field_7548.set(3, itemStack);
               }

               if (equipmentSlot.equals(class_1304.field_6174)) {
                  inventory.field_7548.set(2, itemStack);
               }

               if (equipmentSlot.equals(class_1304.field_6172)) {
                  inventory.field_7548.set(1, itemStack);
               }

               if (equipmentSlot.equals(class_1304.field_6166)) {
                  inventory.field_7548.set(0, itemStack);
               }

               if (equipmentSlot.equals(class_1304.field_6171)) {
                  inventory.field_7544.set(0, itemStack);
               }
            }
         }
      }
   }

   public static String getGearStringFromPlayer(class_1657 player) {
      return getGearStringFromPlayer(player, false);
   }

   public static String getGearStringFromPlayer(class_1657 player, boolean parseActiveEffects) {
      class_1937 level = player.method_37908();
      StringBuilder gearStringBuilder = new StringBuilder();

      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         if (!gearStringBuilder.isEmpty()) {
            gearStringBuilder.append("\n");
         }

         String equipmentSlotString = equipmentSlot.method_5923();
         class_1799 equipmentSlotStack = player.method_6118(equipmentSlot);
         if (!equipmentSlotStack.method_7960()) {
            String nbtString = getFormattedNBTStringFromItemStack(level, equipmentSlotStack);
            gearStringBuilder.append("'").append(equipmentSlotString).append("'").append(" : ").append("'").append(nbtString).append("',");
         } else {
            gearStringBuilder.append("'").append(equipmentSlotString).append("'").append(" : ").append("'',");
         }
      }

      class_1661 playerInventory = player.method_31548();

      for (int i = 0; i < 36; i++) {
         class_1799 slotStack = playerInventory.method_5438(i);
         if (!slotStack.method_7960()) {
            String nbtString = getFormattedNBTStringFromItemStack(level, slotStack);
            gearStringBuilder.append("\n").append(i).append(" : ").append("'").append(nbtString).append("',");
         } else {
            gearStringBuilder.append("\n").append(i).append(" : '',");
         }
      }

      if (parseActiveEffects) {
         StringBuilder effectsStringBuilder = new StringBuilder();
         Map<class_6880<class_1291>, class_1293> activeEffectsMap = player.method_6088();
         if (activeEffectsMap.size() > 0) {
            class_2378<class_1291> mobEffectRegistry = player.method_37908().method_30349().method_30530(class_7924.field_41208);
            List<String> effectResourceLocationList = new ArrayList<>();
            HashMap<String, String> effectLineData = new HashMap<>();

            for (class_6880<class_1291> mobEffectHolder : activeEffectsMap.keySet()) {
               class_1293 mobEffectInstance = activeEffectsMap.get(mobEffectHolder);
               class_2960 resourceLocation = mobEffectRegistry.method_10221((class_1291)mobEffectHolder.comp_349());
               if (resourceLocation != null) {
                  String rlString = resourceLocation.toString();
                  int amplifier = mobEffectInstance.method_5578() + 1;
                  int duration = mobEffectInstance.method_5584();
                  effectResourceLocationList.add(rlString);
                  effectLineData.put(rlString, rlString + ";lvl:" + amplifier + ";duration:" + duration);
               }
            }

            Collections.sort(effectResourceLocationList);

            for (String rlString : effectResourceLocationList) {
               String lineData = effectLineData.get(rlString);
               if (!effectsStringBuilder.toString().equals("")) {
                  effectsStringBuilder.append("|");
               }

               effectsStringBuilder.append(lineData);
            }
         }

         gearStringBuilder.append("\n'effects' : '").append(effectsStringBuilder).append("',");
      }

      return gearStringBuilder.toString();
   }

   public static String getPlayerGearStringFromHashMap(class_1937 level, HashMap<String, class_1799> gearStringHashMap) {
      StringBuilder gearStringBuilder = new StringBuilder();

      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         if (!gearStringBuilder.isEmpty()) {
            gearStringBuilder.append("\n");
         }

         String equipmentSlotString = equipmentSlot.method_5923();
         String nbtString = "";
         if (gearStringHashMap.containsKey(equipmentSlotString)) {
            nbtString = getFormattedNBTStringFromItemStack(level, gearStringHashMap.get(equipmentSlotString));
         }

         gearStringBuilder.append("'").append(equipmentSlotString).append("'").append(" : ").append("'").append(nbtString).append("',");
      }

      List<class_1799> emptyInventoryList = class_2371.method_10213(36, class_1799.field_8037);

      for (int i = 0; i < emptyInventoryList.size(); i++) {
         String nbtString = "";
         if (gearStringHashMap.containsKey(i + "")) {
            nbtString = getFormattedNBTStringFromItemStack(level, gearStringHashMap.get(i + ""));
         }

         gearStringBuilder.append("\n").append(i).append(" : '").append(nbtString).append("',");
      }

      return gearStringBuilder.toString();
   }

   public static HashMap<String, class_1799> getHashMapFromGearString(class_1937 level, String gearString) {
      HashMap<String, class_1799> gearHashMap = new HashMap<>();

      for (String line : getGearStringSplit(gearString)) {
         String slotString = getSlotStringFromLine(line);
         class_1799 itemStack = getItemStackFromGearStringLine(level, line);
         if (itemStack == null) {
            itemStack = class_1799.field_8037.method_7972();
         }

         gearHashMap.put(slotString, itemStack);
      }

      return gearHashMap;
   }

   public static List<class_1799> getItemStackListFromGearString(class_1937 level, String gearString) {
      List<class_1799> gearList = new ArrayList<>();

      for (String line : getGearStringSplit(gearString)) {
         class_1799 itemStack = getItemStackFromGearStringLine(level, line);
         if (itemStack != null) {
            gearList.add(itemStack);
         }
      }

      return gearList;
   }

   public static List<class_1293> getEffectsFromGearString(class_1657 player, String gearString) {
      return getEffectsFromGearString(player.method_37908(), gearString);
   }

   public static List<class_1293> getEffectsFromGearString(class_1937 level, String gearString) {
      return getEffectsFromGearString(level.method_30349().method_30530(class_7924.field_41208), gearString);
   }

   public static List<class_1293> getEffectsFromGearString(class_2378<class_1291> mobEffectRegistry, String gearString) {
      for (String line : getGearStringSplit(gearString)) {
         String slotString = getSlotStringFromLine(line);
         if (slotString.equalsIgnoreCase("effects")) {
            return getEffectsFromLine(mobEffectRegistry, line);
         }
      }

      return new ArrayList<>();
   }

   public static boolean areTwoGearStringsEqual(class_1937 level, String gearStringOne, String gearStringTwo) {
      List<class_1799> itemStackListOne = getItemStackListFromGearString(level, gearStringOne);
      List<class_1799> itemStackListTwo = getItemStackListFromGearString(level, gearStringTwo);

      for (class_1799 itemStackOne : itemStackListOne) {
         class_1792 itemOne = itemStackOne.method_7909();
         boolean foundMatch = false;

         for (class_1799 itemStackTwo : itemStackListTwo) {
            class_1792 itemTwo = itemStackTwo.method_7909();
            if (itemOne.equals(itemTwo) && itemStackOne.method_7947() == itemStackTwo.method_7947()) {
               foundMatch = true;
               break;
            }
         }

         if (!foundMatch) {
            return false;
         }
      }

      return true;
   }

   public static String sortGearString(String gearString) {
      StringBuilder sortedGearString = new StringBuilder();
      HashMap<String, String> gearHashMap = new HashMap<>();

      for (String line : getGearStringSplit(gearString)) {
         String slotString = getSlotStringFromLine(line);
         String data = getDataFromLine(line);
         gearHashMap.put(slotString, data);
      }

      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         String equipmentSlotName = equipmentSlot.method_5923();
         if (gearHashMap.containsKey(equipmentSlotName)) {
            sortedGearString.append("'")
               .append(equipmentSlotName)
               .append("' : '")
               .append(gearHashMap.get(equipmentSlotName))
               .append("',")
               .append(System.lineSeparator());
         }
      }

      for (String line : getGearStringSplit(gearString)) {
         String slotString = getSlotStringFromLine(line);
         String data = getDataFromLine(line);
         if (NumberFunctions.isNumeric(slotString)) {
            sortedGearString.append("'").append(slotString).append("' : '").append(data).append("',").append(System.lineSeparator());
         }
      }

      if (gearHashMap.containsKey("effects")) {
         sortedGearString.append("'effects' : '").append(gearHashMap.get("effects")).append("',").append(System.lineSeparator());
      }

      return sortedGearString.toString().strip();
   }

   private static class_1799 getItemStackFromGearStringLine(class_1937 level, String line) {
      class_1799 itemStack = class_1799.field_8037;
      String[] stringArray = getStringArrayFromLine(line);
      if (stringArray.length != 2) {
         return null;
      }

      String data = getDataFromStringArray(stringArray);

      try {
         class_2487 compoundTag = parseFormattedTag(data);
         Optional<class_1799> optionalItemStack = class_1799.method_57360(level.method_30349(), compoundTag);
         if (optionalItemStack.isPresent()) {
            itemStack = optionalItemStack.get();
         }
      } catch (CommandSyntaxException var8) {
      }

      if (itemStack.method_7960()) {
         try {
            data = extendedFormatDataString(data);
            class_2487 compoundTag = parseFormattedTag(data);
            Optional<class_1799> optionalItemStack = class_1799.method_57360(level.method_30349(), compoundTag);
            if (optionalItemStack.isPresent()) {
               itemStack = optionalItemStack.get();
            }
         } catch (CommandSyntaxException ex1) {
            return null;
         }
      }

      return itemStack.method_7972();
   }

   private static List<class_1293> getEffectsFromLine(class_1657 player, String line) {
      return getEffectsFromLine(player.method_37908(), line);
   }

   private static List<class_1293> getEffectsFromLine(class_1937 level, String line) {
      return getEffectsFromLine(level.method_30349().method_30530(class_7924.field_41208), line);
   }

   private static List<class_1293> getEffectsFromLine(class_2378<class_1291> mobEffectRegistry, String line) {
      List<class_1293> mobEffectInstanceList = new ArrayList<>();
      String effectData = getDataFromLine(line);

      for (String effectRaw : effectData.split("\\|")) {
         String[] effectSpl = effectRaw.split(";");
         if (effectSpl.length == 3) {
            String rlString = effectSpl[0];
            String lvlRaw = effectSpl[1];
            String durationRaw = effectSpl[2];
            if (lvlRaw.contains(":") && durationRaw.contains(":")) {
               String lvlString = lvlRaw.split(":")[1].strip();
               String durationString = durationRaw.split(":")[1].strip();
               if (NumberFunctions.isNumeric(lvlString) && NumberFunctions.isNumeric(durationString)) {
                  int lvl = Integer.parseInt(lvlString);
                  if (lvl != 0) {
                     int duration = Integer.parseInt(durationString);
                     class_1291 mobEffect = (class_1291)mobEffectRegistry.method_10223(class_2960.method_60654(rlString));
                     if (mobEffect != null) {
                        class_1293 mobEffectInstance = new class_1293(class_7923.field_41174.method_47983(mobEffect), duration, lvl - 1);
                        mobEffectInstanceList.add(mobEffectInstance);
                     }
                  }
               }
            }
         }
      }

      return mobEffectInstanceList;
   }

   private static String[] getGearStringSplit(String gearString) {
      return gearString.split("',[\\r\\n]+");
   }

   private static String getSlotStringFromLine(String line) {
      return getStringArrayFromLine(line)[0].replace("'", "").strip();
   }

   private static String getDataFromStringArray(String[] stringArray) {
      if (stringArray.length < 2) {
         return "";
      }

      String rawData = stringArray[1];
      if (rawData.startsWith("'")) {
         rawData = rawData.substring(1);
      }

      if (rawData.endsWith("'")) {
         rawData = rawData.substring(0, rawData.length() - 1);
      }

      return rawData.replaceAll("\r", "");
   }

   private static String[] getStringArrayFromLine(String line) {
      line = line.trim();
      if (line.endsWith(",")) {
         line = line.substring(0, line.length() - 1);
      }

      if (!line.endsWith("'")) {
         line = line + "'";
      }

      return line.split(" : ", 2);
   }

   private static String getDataFromLine(String line) {
      return getDataFromStringArray(getStringArrayFromLine(line));
   }

   private static class_1304 getEquipmentSlotFromSlotString(String slotString) {
      for (class_1304 equipmentSlot : Constants.equipmentSlots) {
         if (equipmentSlot.method_5923().equalsIgnoreCase(slotString.strip())) {
            return equipmentSlot;
         }
      }

      return null;
   }

   private static String getFormattedNBTStringFromItemStack(class_1937 level, class_1799 itemStack) {
      String nbtString = ItemFunctions.getNBTStringFromItemStack(level, itemStack);
      return nbtString.replaceAll("[\\r\\n]", "|n").replace("'", "‵");
   }

   private static class_2487 parseFormattedTag(String data) throws CommandSyntaxException {
      data = data.replace("|n", "\n").replace("‵", "'");
      return class_2522.method_10718(data);
   }

   private static String extendedFormatDataString(String rawData) {
      return rawData.replace("\",\"", "|||,|||").replace(" \"", " '").replace("\" ", "' ").replace("|||,|||", "\",\"");
   }
}

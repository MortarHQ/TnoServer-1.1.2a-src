package com.natamus.starterkit_common_fabric.functions;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import com.natamus.collective_common_fabric.functions.GearFunctions;
import com.natamus.collective_common_fabric.functions.ItemFunctions;
import com.natamus.collective_common_fabric.functions.MessageFunctions;
import com.natamus.collective_common_fabric.functions.TaskFunctions;
import com.natamus.collective_common_fabric.implementations.networking.api.Dispatcher;
import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.data.Constants;
import com.natamus.starterkit_common_fabric.data.Variables;
import com.natamus.starterkit_common_fabric.networking.packets.ToClientAskIfModIsInstalledPacket;
import com.natamus.starterkit_common_fabric.networking.packets.ToClientSelectFirstSlotPacket;
import com.natamus.starterkit_common_fabric.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2561;
import net.minecraft.class_3222;
import net.minecraft.class_5250;
import org.apache.commons.lang3.StringUtils;

public class StarterGearFunctions {
   public static void initStarterKitHandle(class_1937 level, class_1657 player, @Nullable class_2168 commandSource) {
      initStarterKitHandle(level, player, commandSource, "");
   }

   public static void initStarterKitHandle(class_1937 level, class_1657 player, @Nullable class_2168 commandSource, String kitName) {
      UUID uuid = player.method_5667();
      TaskFunctions.enqueueCollectiveTask(level.method_8503(), () -> {
         if (StarterCheckFunctions.shouldPlayerReceiveStarterKit(level, player) || commandSource != null) {
            if (ConfigHandler.usePotionEffectsInStarterKit) {
               player.method_6012();
            }

            if (!ConfigHandler.randomizeMultipleKitsToggle && Variables.starterGearEntries.size() > 1 && kitName.equals("")) {
               Dispatcher.sendToClient(new ToClientAskIfModIsInstalledPacket(), (class_3222)player);
               TaskFunctions.enqueueCollectiveTask(level.method_8503(), () -> {
                  if (!Variables.playersWithModInstalledOnClient.contains(uuid)) {
                     chooseOrGiveStarterKit(player, commandSource, kitName);
                  }
               }, 100);
            } else {
               giveStarterKit(player, commandSource, kitName);
            }
         }
      }, 10);
   }

   public static void chooseOrGiveStarterKit(class_1657 player, @Nullable class_2168 commandSource, String kitName) {
      int kitCount = Variables.starterGearEntries.size();
      if (kitCount != 0) {
         if (!ConfigHandler.randomizeMultipleKitsToggle && kitCount > 1 && kitName.equals("")) {
            chooseStarterKitViaCommands(player);
         } else {
            giveStarterKit(player, commandSource, kitName);
         }
      }
   }

   public static void chooseStarterKitViaCommands(class_1657 player) {
      class_1937 level = player.method_37908();
      if (!level.field_9236) {
         MessageFunctions.sendMessage(
            player,
            class_2561.method_43470(ConfigHandler.chooseKitText.replace("%s", player.method_5477().getString()))
               .method_27692(class_124.field_1065)
               .method_27692(class_124.field_1067),
            true
         );
         MessageFunctions.sendMessage(player, "  /sk choose <kit_name>", class_124.field_1077);
         MessageFunctions.sendMessage(player, "  /sk info <kit_name>", class_124.field_1077);
         MessageFunctions.sendMessage(player, " Available kits: " + StringUtils.join(getActiveKitNames(), ", "), class_124.field_1080, true);
      }
   }

   public static String giveStarterKit(class_1657 player, @Nullable class_2168 commandSource) {
      return giveStarterKit(player, commandSource, "");
   }

   public static String giveStarterKit(class_1657 player, @Nullable class_2168 commandSource, String kitName) {
      if (Variables.starterGearEntries.isEmpty()) {
         return null;
      }

      if (!Variables.starterGearEntries.containsKey(kitName)) {
         kitName = "";
      }

      String kitGearString = "";
      if (kitName.equals("")) {
         String[] allKitNames = Variables.starterGearEntries.keySet().toArray(new String[0]);
         String randomKitName = allKitNames[GlobalVariables.random.nextInt(allKitNames.length)];
         if (!Variables.starterGearEntries.containsKey(randomKitName)) {
            Constants.logger.warn("[Starter Kit] Unable to find a starter kit to give with the name '" + randomKitName + "'.");
            return null;
         }

         kitGearString = Variables.starterGearEntries.get(randomKitName);
         kitName = randomKitName;
      } else {
         kitGearString = Variables.starterGearEntries.get(kitName);
      }

      if (kitGearString.equals("")) {
         Constants.logger.warn("[Starter Kit] Unable to find a starter kit to give.");
         return null;
      }

      List<class_1799> toAddAfter = new ArrayList<>();
      if (ConfigHandler.addExistingItemsAfterKitSet) {
         class_1661 inv = player.method_31548();
         boolean isempty = true;

         for (int i = 0; i < 36; i++) {
            class_1799 itemStack = inv.method_5438(i);
            if (!itemStack.method_7960()) {
               toAddAfter.add(itemStack.method_7972());
            }
         }
      }

      GearFunctions.setPlayerGearFromGearString(player, kitGearString, ConfigHandler.usePotionEffectsInStarterKit);
      if (toAddAfter.size() > 0) {
         for (class_1799 itemStackToAdd : toAddAfter) {
            ItemFunctions.giveOrDropItemStack(player, itemStackToAdd);
         }
      }

      if (commandSource != null) {
         MessageFunctions.sendMessage(
            commandSource,
            player.method_5477().getString() + " has been given the '" + Util.formatKitName(kitName) + "' starter kit!",
            class_124.field_1077,
            true
         );
      }

      StarterCheckFunctions.addPlayerToTrackingMap(player);
      Dispatcher.sendToClient(new ToClientSelectFirstSlotPacket(), (class_3222)player);
      return kitName;
   }

   public static String createStarterKitFile(class_1657 player, String kitName, boolean adding) {
      String playerGearString = GearFunctions.getGearStringFromPlayer(player, ConfigHandler.usePotionEffectsInStarterKit);
      if (!adding) {
         moveAllKitsToInactive();
      }

      if (kitName.equals("")) {
         File[] files = Util.configKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));
         kitName = "Kit_" + (files.length + 1);
      }

      PrintWriter writer = null;

      try {
         writer = new PrintWriter(Util.configKitPath + File.separator + kitName + ".txt", StandardCharsets.UTF_8);
         writer.print(playerGearString);
      } catch (IOException ex) {
         Constants.logger.warn("[Starter Kit] Something went wrong while writing the new starter kit file.");
         ex.printStackTrace();
      }

      if (writer != null) {
         writer.close();
      }

      processKitFiles();
      return kitName;
   }

   public static boolean moveKitToInactive(String kitName) {
      String kitPath = Util.configKitPath + File.separator + kitName + ".txt";
      File kit = new File(kitPath);
      if (!kit.isFile()) {
         return false;
      }

      String destinationPath = Util.configInactiveKitDir.getAbsolutePath() + File.separator + kitName + ".txt";
      boolean movedCorrectly = moveKit(kitPath, destinationPath);
      processKitFiles();
      return movedCorrectly;
   }

   public static void moveAllKitsToInactive() {
      File[] files = Util.configKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String filePath = Util.configKitDir.getAbsolutePath() + File.separator + file.getName();
         String destinationPath = Util.configInactiveKitDir.getAbsolutePath() + File.separator + file.getName();
         moveKit(filePath, destinationPath);
      }

      processKitFiles();
   }

   public static boolean moveKitToActive(String kitName) {
      String kitPath = Util.configInactiveKitPath + File.separator + kitName + ".txt";
      File kit = new File(kitPath);
      if (!kit.isFile()) {
         return false;
      }

      String destinationPath = Util.configKitDir.getAbsolutePath() + File.separator + kitName + ".txt";
      boolean movedCorrectly = moveKit(kitPath, destinationPath);
      processKitFiles();
      return movedCorrectly;
   }

   public static void moveAllKitsToActive() {
      File[] files = Util.configInactiveKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String filePath = Util.configInactiveKitDir.getAbsolutePath() + File.separator + file.getName();
         String destinationPath = Util.configKitDir.getAbsolutePath() + File.separator + file.getName();
         moveKit(filePath, destinationPath);
      }

      processKitFiles();
   }

   private static boolean moveKit(String filePath, String destinationPath) {
      for (File destinationFile = new File(destinationPath); destinationFile.exists(); destinationFile = new File(destinationPath)) {
         destinationPath = destinationPath.replace(".txt", "_.txt");
      }

      try {
         Files.move(Paths.get(filePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
         return true;
      } catch (IOException ex) {
         return false;
      }
   }

   public static void processKitFiles() {
      Variables.starterGearEntries = new HashMap<>();

      try {
         File[] files = Util.configKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));
         if (files.length == 0 && !new File(Util.configInactiveKitPath + File.separator + "Default.txt").isFile()) {
            StarterDefaultKitFunctions.createDefaultKits(false);
            files = Util.configKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));
         }

         boolean renamedFiles = false;

         for (File file : files) {
            String fileName = file.getName();
            if (fileName.contains(" ")) {
               Files.move(
                  Paths.get(file.getAbsolutePath()),
                  Paths.get(file.getAbsolutePath().replace(fileName, fileName.replace(" ", "_"))),
                  StandardCopyOption.REPLACE_EXISTING
               );
               renamedFiles = true;
            }
         }

         if (renamedFiles) {
            files = Util.configKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));
         }

         for (File file : files) {
            String kitName = file.getName().replace(".txt", "");
            String gearString = Files.readString(Paths.get(file.getAbsolutePath()));
            Variables.starterGearEntries.put(kitName, gearString);
            File matchingKitDescriptionFile = new File(Util.configDescriptionPath + File.separator + kitName + ".txt");
            if (!matchingKitDescriptionFile.isFile()) {
               PrintWriter descriptionWriter = new PrintWriter(Util.configDescriptionPath + File.separator + kitName + ".txt", StandardCharsets.UTF_8);
               descriptionWriter.print("You can edit this description in ./config/starterkit/description/" + kitName + ".txt!");
               descriptionWriter.close();
            }
         }

         processKitDescriptionFiles();
      } catch (Exception ex) {
         Constants.logger.warn("[Starter Kit] Unable to process the starter kit files.");
         ex.printStackTrace();
      }
   }

   private static void processKitDescriptionFiles() throws IOException {
      Variables.starterKitDescriptions = new HashMap<>();
      File[] files = Util.configDescriptionDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String kitName = file.getName().replace(".txt", "");
         String kitDescription = Files.readString(Paths.get(file.getAbsolutePath()));
         Variables.starterKitDescriptions.put(kitName.toLowerCase(), kitDescription);
      }
   }

   public static List<String> getActiveKitNames() {
      return getActiveKitNames(Variables.starterGearEntries, false);
   }

   public static List<String> getActiveKitNames(boolean includeAll) {
      return getActiveKitNames(Variables.starterGearEntries, includeAll);
   }

   public static List<String> getActiveKitNames(HashMap<String, String> entryMap) {
      return getActiveKitNames(entryMap, false);
   }

   public static List<String> getActiveKitNames(HashMap<String, String> entryMap, boolean includeAll) {
      List<String> kitNames = new ArrayList<>(entryMap.keySet());
      if (includeAll) {
         kitNames.add("_all");
      }

      Collections.sort(kitNames);
      return kitNames;
   }

   public static List<String> getInactiveKitNames() {
      return getInactiveKitNames(false);
   }

   public static List<String> getInactiveKitNames(boolean includeAll) {
      List<String> inactiveKitNames = new ArrayList<>();
      File[] files = Util.configInactiveKitDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String inactiveKitName = file.getName().replace(".txt", "");
         inactiveKitNames.add(inactiveKitName);
      }

      if (includeAll) {
         inactiveKitNames.add("_all");
      }

      Collections.sort(inactiveKitNames);
      return inactiveKitNames;
   }

   public static int showKitInformation(class_1937 level, class_2168 targetSource, class_1657 targetPlayer, String kitName) {
      String kitDescription = "";
      if (Variables.starterKitDescriptions.containsKey(kitName.toLowerCase())) {
         kitDescription = Variables.starterKitDescriptions.get(kitName.toLowerCase());
         if (kitDescription.contains("/config/starterkit/description/")) {
            kitDescription = "N/A";
         }
      }

      class_5250 nameComponent = class_2561.method_43470("Name: ")
         .method_27692(class_124.field_1077)
         .method_10852(class_2561.method_43470(Util.formatKitName(kitName)).method_27692(class_124.field_1080));
      class_5250 descriptionComponent = class_2561.method_43470("Description: ")
         .method_27692(class_124.field_1077)
         .method_10852(class_2561.method_43470(kitDescription).method_27692(class_124.field_1080));
      if (targetSource != null) {
         MessageFunctions.sendMessage(targetSource, nameComponent, true);
         MessageFunctions.sendMessage(targetSource, descriptionComponent);
      }

      if (targetPlayer != null) {
         MessageFunctions.sendMessage(targetPlayer, nameComponent, true);
         MessageFunctions.sendMessage(targetPlayer, descriptionComponent);
      }

      StringBuilder kitItems = new StringBuilder();
      if (Variables.starterGearEntries.containsKey(kitName)) {
         for (class_1799 itemStack : GearFunctions.getItemStackListFromGearString(level, Variables.starterGearEntries.get(kitName))) {
            if (!itemStack.method_7960()) {
               if (!kitItems.toString().equals("")) {
                  kitItems.append(", ");
               }

               String count = itemStack.method_7947() + " ";
               if (count.equals("1 ")) {
                  count = "";
               }

               kitItems.append(count).append(itemStack.method_7954().getString().replace("[", "").replace("]", "").toLowerCase());
            }
         }
      }

      class_5250 itemComponent = class_2561.method_43470("Items: ")
         .method_27692(class_124.field_1077)
         .method_10852(class_2561.method_43470(kitItems.toString()).method_27692(class_124.field_1080));
      if (targetSource != null) {
         MessageFunctions.sendMessage(targetSource, itemComponent);
      }

      if (targetPlayer != null) {
         MessageFunctions.sendMessage(targetPlayer, itemComponent);
      }

      return 1;
   }
}

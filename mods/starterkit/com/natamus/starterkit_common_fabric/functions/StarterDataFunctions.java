package com.natamus.starterkit_common_fabric.functions;

import com.natamus.collective_common_fabric.functions.GearFunctions;
import com.natamus.collective_common_fabric.functions.WorldFunctions;
import com.natamus.starterkit_common_fabric.data.Constants;
import com.natamus.starterkit_common_fabric.data.Variables;
import com.natamus.starterkit_common_fabric.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.server.MinecraftServer;

public class StarterDataFunctions {
   public static void init(MinecraftServer minecraftServer) {
      setupTrackingMap();
      initDataFolders(minecraftServer);

      try {
         readTrackingJsonFile(minecraftServer);
      } catch (Exception ex) {
         Constants.logger.warn("[Starter Kit] Something went wrong while parsing the tracking json file.");
         writeTrackingMapToJsonFile(minecraftServer);
      }

      try {
         processExistingTrackingData(minecraftServer);
      } catch (NullPointerException ex) {
         Constants.logger.warn("[Starter Kit] Unable to process existing tracking data.");
      }
   }

   private static void setupTrackingMap() {
      Variables.trackingMap.put("singleplayer", new HashMap<>());
      Variables.trackingMap.put("multiplayer", new HashMap<>());
   }

   public static void initConfigFolders() {
      if (!Util.rootConfigDir.isDirectory()) {
         Util.rootConfigDir.mkdirs();
      }

      if (!Util.configKitDir.isDirectory()) {
         Util.configKitDir.mkdirs();
      }

      if (!Util.configInactiveKitDir.isDirectory()) {
         Util.configInactiveKitDir.mkdirs();
      }

      if (!Util.configDescriptionDir.isDirectory()) {
         Util.configDescriptionDir.mkdirs();
      }

      if (new File(Util.rootConfigPath + File.separator + "starterkit.txt").isFile()) {
         try {
            processRetroConfig();
         } catch (IOException ex) {
            Constants.logger.warn("[Starter Kit] Unable to convert the old config format.");
         }
      }
   }

   private static void initDataFolders(MinecraftServer minecraftServer) {
      if (!Util.getRootDataDir(minecraftServer).isDirectory()) {
         Util.getRootDataDir(minecraftServer).mkdirs();
      }
   }

   public static void readTrackingJsonFile(MinecraftServer minecraftServer) throws Exception {
      String trackingJsonFilePath = Util.getRootDataDir(minecraftServer) + File.separator + "tracking.json";
      File trackingJsonFile = new File(trackingJsonFilePath);
      if (!trackingJsonFile.isFile()) {
         writeTrackingMapToJsonFile(minecraftServer);
      } else {
         String rawJson = Files.readString(Paths.get(trackingJsonFilePath));
         Map<String, Map<String, Boolean>> jsonMap = (Map<String, Map<String, Boolean>>)Constants.gson.fromJson(rawJson, Constants.gsonTrackingMapType);

         for (String environment : jsonMap.keySet()) {
            HashMap<String, Boolean> trackingData = new HashMap<>();

            for (String key : jsonMap.get(environment).keySet()) {
               trackingData.put(key, jsonMap.get(environment).get(key));
            }

            Variables.trackingMap.put(environment, trackingData);
         }
      }
   }

   public static void processExistingTrackingData(MinecraftServer minecraftServer) throws NullPointerException {
      String playerDataFolder = WorldFunctions.getWorldPath(minecraftServer) + File.separator + "playerdata";
      File playerDataDir = new File(playerDataFolder);
      boolean updatedTracking = false;
      File[] files = playerDataDir.listFiles(pathname -> pathname.getName().endsWith(".dat"));

      for (File f : files) {
         String fileName = f.getName();
         String rawUUID = fileName.replace(".dat", "");
         if (!Variables.trackingMap.containsKey(rawUUID)) {
            Variables.trackingMap.get("multiplayer").put(rawUUID, true);
            updatedTracking = true;
         }
      }

      if (updatedTracking) {
         writeTrackingMapToJsonFile(minecraftServer);
      }
   }

   public static void resetTrackingForPlayer(class_1657 player) {
      class_1937 level = player.method_37908();
      if (!level.field_9236) {
         MinecraftServer minecraftServer = level.method_8503();
         if (!minecraftServer.method_3816()) {
            Variables.trackingMap.get("singleplayer").replaceAll((r, v) -> false);
         }

         String rawUUID = player.method_5845();
         Variables.trackingMap.get("multiplayer").put(rawUUID, false);
         writeTrackingMapToJsonFile(minecraftServer);
      }
   }

   public static void resetTrackingMap(MinecraftServer minecraftServer) {
      Variables.trackingMap.get("singleplayer").replaceAll((r, v) -> false);
      Variables.trackingMap.get("multiplayer").replaceAll((r, v) -> false);
      writeTrackingMapToJsonFile(minecraftServer);
   }

   public static void writeTrackingMapToJsonFile(MinecraftServer minecraftServer) {
      String trackingJsonFilePath = Util.getRootDataDir(minecraftServer) + File.separator + "tracking.json";
      new File(trackingJsonFilePath);
      String rawJson = Constants.gsonPretty.toJson(Variables.trackingMap);
      PrintWriter writer = null;

      try {
         writer = new PrintWriter(trackingJsonFilePath, StandardCharsets.UTF_8);
         writer.print(rawJson);
      } catch (IOException ex) {
         Constants.logger.warn("[Starter Kit] Unable to write kit tracking map to json data file.");
      }

      if (writer != null) {
         writer.close();
      }
   }

   private static void processRetroConfig() throws IOException {
      Constants.logger.info("[Starter Kit] Old file structure detected. Converting it automatically.");
      File[] files = Util.rootConfigDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String filePath = Util.rootConfigDir + File.separator + file.getName();
         String fileContent = Files.readString(Paths.get(filePath));
         if (!fileContent.contains("'effects'")) {
            fileContent = fileContent + "'effects' : ''," + System.lineSeparator();
         }

         fileContent = GearFunctions.sortGearString(fileContent);
         PrintWriter fileWriter = new PrintWriter(filePath, StandardCharsets.UTF_8);
         fileWriter.print(fileContent.strip());
         fileWriter.close();
      }

      String activeKitSourcePath = Util.rootConfigDir.getAbsolutePath() + File.separator + "starterkit.txt";
      String activeKitDestinationPath = Util.configKitDir.getAbsolutePath() + File.separator + "Default.txt";
      String defaultGearString = StarterDefaultKitFunctions.getDefaultKitGearString();
      String activeKitGearString = Files.readString(Paths.get(activeKitSourcePath));
      if (!activeKitGearString.equals("") && !defaultGearString.equals(activeKitGearString)) {
         activeKitDestinationPath = Util.configKitDir.getAbsolutePath() + File.separator + "starterkit.txt";
      }

      Files.move(Paths.get(activeKitSourcePath), Paths.get(activeKitDestinationPath), StandardCopyOption.REPLACE_EXISTING);
      files = Util.rootConfigDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));

      for (File file : files) {
         String inactiveKitSourcePath = Util.rootConfigDir.getAbsolutePath() + File.separator + file.getName();
         String inactiveKitDestinationPath = Util.configInactiveKitDir.getAbsolutePath() + File.separator + file.getName();
         Files.move(Paths.get(inactiveKitSourcePath), Paths.get(inactiveKitDestinationPath), StandardCopyOption.REPLACE_EXISTING);
      }

      StarterDefaultKitFunctions.createDefaultKits(true);
      Constants.logger.info("[Starter Kit] The old file structure has successfully been converted!");
   }
}

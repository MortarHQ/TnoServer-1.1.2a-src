package mebahelcreaturesdwarven.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModMultiplayerChest {
   private static final String CONFIG_FILE_NAME = "multiplayer_chest_config.json";
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public static boolean turnOnMultiplayerDraugrChest = true;

   public static void loadConfig(File configDir) {
      if (!configDir.exists()) {
         configDir.mkdirs();
      }

      File configFile = new File(configDir, "multiplayer_chest_config.json");
      if (configFile.exists()) {
         try (FileReader reader = new FileReader(configFile)) {
            ModMultiplayerChest.ConfigData data = (ModMultiplayerChest.ConfigData)GSON.fromJson(reader, ModMultiplayerChest.ConfigData.class);
            boolean updated = false;
            if (data.turnOnMultiplayerDraugrChest == null) {
               data.turnOnMultiplayerDraugrChest = true;
               updated = true;
            }

            turnOnMultiplayerDraugrChest = data.turnOnMultiplayerDraugrChest;
            if (updated) {
               saveConfig(configDir);
            }
         } catch (IOException e) {
            System.err.println("Failed to load config file: " + e.getMessage());
         }
      } else {
         saveConfig(configDir);
      }
   }

   public static void saveConfig(File configDir) {
      File configFile = new File(configDir, "multiplayer_chest_config.json");
      ModMultiplayerChest.ConfigData data = new ModMultiplayerChest.ConfigData(turnOnMultiplayerDraugrChest);

      try (FileWriter writer = new FileWriter(configFile)) {
         GSON.toJson(data, writer);
      } catch (IOException e) {
         System.err.println("Failed to save config file: " + e.getMessage());
      }
   }

   private static class ConfigData {
      Boolean turnOnMultiplayerDraugrChest;

      ConfigData(boolean turnOnMultiplayerDraugrChest) {
         this.turnOnMultiplayerDraugrChest = turnOnMultiplayerDraugrChest;
      }
   }
}

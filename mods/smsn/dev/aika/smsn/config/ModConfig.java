package dev.aika.smsn.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.aika.smsn.SMSN;
import dev.aika.smsn.SMSNPlatform;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ModConfig {
   private static final Logger log = SMSN.LOGGER;
   private static final Marker marker = MarkerFactory.getMarker("ModConfig");
   protected static final File CONFIG_FILE = new File(SMSNPlatform.getConfigDir().toFile(), "smsn.json");
   protected static final File OLD_CONFIG_FILE = new File(SMSNPlatform.getConfigDir().toFile(), "smsn.json5");
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

   protected static <T> T load(Class<T> clazz) {
      try (FileReader reader = new FileReader(CONFIG_FILE)) {
         return (T)GSON.fromJson(reader, clazz);
      } catch (IOException e) {
         log.error(marker, "Failed to parse config file: {}", CONFIG_FILE, e);
         return null;
      }
   }

   public void save() {
      String configStr = GSON.toJson(this);

      try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
         writer.write(configStr);
      } catch (IOException e) {
         log.error(marker, "Failed to write config file {}", CONFIG_FILE, e);
      }
   }
}

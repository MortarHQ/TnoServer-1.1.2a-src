package org.crypticdev.stextras.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import org.crypticdev.stextras.Stextras;

public final class STExtrasClientConfig {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Object LOCK = new Object();
   private static STExtrasClientConfig.ConfigData data = new STExtrasClientConfig.ConfigData();
   private static boolean loaded;

   private STExtrasClientConfig() {
   }

   public static void init(Minecraft minecraft) {
      ensureLoaded(minecraft);
   }

   public static boolean isLoadingScreenEnabled(Minecraft minecraft) {
      ensureLoaded(minecraft);
      return data.loadingScreen != null && data.loadingScreen.enabled;
   }

   public static boolean isAnimatedLoadingBackgroundEnabled(Minecraft minecraft) {
      ensureLoaded(minecraft);
      return data.loadingScreen != null && data.loadingScreen.enabled && data.loadingScreen.animatedBackground;
   }

   private static void ensureLoaded(Minecraft minecraft) {
      if (!loaded && minecraft != null) {
         synchronized (LOCK) {
            if (!loaded) {
               Path path = configPath(minecraft);
               STExtrasClientConfig.ConfigData loadedData = readConfig(path);
               if (loadedData == null) {
                  loadedData = readConfig(legacyConfigPath(minecraft));
               }

               if (loadedData != null) {
                  data = loadedData;
               }

               normalize(data);
               writeConfig(path, data);
               loaded = true;
            }
         }
      }
   }

   private static STExtrasClientConfig.ConfigData readConfig(Path path) {
      if (!Files.exists(path)) {
         return null;
      }

      try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
         STExtrasClientConfig.ConfigData fileData = (STExtrasClientConfig.ConfigData)GSON.fromJson(reader, STExtrasClientConfig.ConfigData.class);
         return fileData == null ? new STExtrasClientConfig.ConfigData() : fileData;
      } catch (IOException | JsonParseException e) {
         Stextras.LOGGER.warn("Failed to read client config at {}", path, e);
         return null;
      }
   }

   private static void writeConfig(Path path, STExtrasClientConfig.ConfigData config) {
      try {
         Files.createDirectories(path.getParent());

         try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(config, writer);
         }
      } catch (IOException e) {
         Stextras.LOGGER.warn("Failed to write client config at {}", path, e);
      }
   }

   private static Path configPath(Minecraft minecraft) {
      return minecraft.gameDirectory.toPath().resolve("config").resolve("stextras").resolve("client").resolve("general.json");
   }

   private static Path legacyConfigPath(Minecraft minecraft) {
      return minecraft.gameDirectory.toPath().resolve("config").resolve("stextras").resolve("client.json");
   }

   private static void normalize(STExtrasClientConfig.ConfigData config) {
      if (config.loadingScreen == null) {
         config.loadingScreen = new STExtrasClientConfig.LoadingScreenConfig();
      }

      if (config.enableLoadingScreen != null) {
         config.loadingScreen.enabled = config.enableLoadingScreen;
         config.enableLoadingScreen = null;
      }
   }

   private static final class ConfigData {
      private STExtrasClientConfig.LoadingScreenConfig loadingScreen = new STExtrasClientConfig.LoadingScreenConfig();
      private Boolean enableLoadingScreen;
   }

   private static final class LoadingScreenConfig {
      private boolean enabled = true;
      private boolean animatedBackground = true;
   }
}

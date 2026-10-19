package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.TensuraMoreSkills;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import net.neoforged.fml.loading.FMLPaths;

public final class ClientVfxLocalOverrides {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("tensuramoreskills-local-vfx.json");
   private static ClientVfxLocalOverrides.Data data;

   private ClientVfxLocalOverrides() {
   }

   public static Boolean getAll() {
      load();
      return data.allEffects;
   }

   public static Boolean getEffect(String id) {
      load();
      return data.effects.get(id);
   }

   public static boolean hasAnyOverride() {
      load();
      return data.allEffects != null || !data.effects.isEmpty();
   }

   public static void setAll(Boolean value) {
      load();
      data.allEffects = value;
      save();
   }

   public static void setEffect(String id, Boolean value) {
      load();
      if (value == null) {
         data.effects.remove(id);
      } else {
         data.effects.put(id, value);
      }

      save();
   }

   public static Map<String, Boolean> copyEffects() {
      load();
      return new LinkedHashMap<>(data.effects);
   }

   private static void load() {
      if (data == null) {
         data = new ClientVfxLocalOverrides.Data();
         if (Files.isRegularFile(FILE)) {
            try (BufferedReader reader = Files.newBufferedReader(FILE)) {
               ClientVfxLocalOverrides.Data loaded = (ClientVfxLocalOverrides.Data)GSON.fromJson(reader, ClientVfxLocalOverrides.Data.class);
               if (loaded != null) {
                  data = loaded;
                  if (data.effects == null) {
                     data.effects = new LinkedHashMap<>();
                  }

                  data.effects.entrySet().removeIf(entry -> !ClientVfxIds.exists(entry.getKey()));
               }
            } catch (Throwable throwable) {
               TensuraMoreSkills.LOGGER.warn("Failed to load local VFX overrides", throwable);
            }
         }
      }
   }

   private static void save() {
      try {
         Files.createDirectories(FILE.getParent());

         try (BufferedWriter writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(data, writer);
         }
      } catch (Throwable throwable) {
         TensuraMoreSkills.LOGGER.warn("Failed to save local VFX overrides", throwable);
      }
   }

   private static final class Data {
      private Boolean allEffects;
      private Map<String, Boolean> effects = new LinkedHashMap<>();
   }
}

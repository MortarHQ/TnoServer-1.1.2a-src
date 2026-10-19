package com.nododiiiii.ponderer.ponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.platform.PondererServices;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public final class PackStateStore {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final String STATE_FILE = ".pack_state.json";
   private static final String LEGACY_REGISTRY_FILE = ".ponderer_registry.json";
   private static final Map<String, PackStateStore.ImportedPackState> IMPORTS = new LinkedHashMap<>();
   private static final Path STATE_PATH = PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve(".pack_state.json");
   private static final Path LEGACY_REGISTRY_PATH = PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve(".ponderer_registry.json");

   private PackStateStore() {
   }

   public static void load() {
      IMPORTS.clear();
      if (Files.exists(STATE_PATH)) {
         try (Reader reader = Files.newBufferedReader(STATE_PATH, StandardCharsets.UTF_8)) {
            JsonObject root = (JsonObject)GSON.fromJson(reader, JsonObject.class);
            if (root != null && root.has("imports")) {
               JsonObject imports = root.getAsJsonObject("imports");

               for (String packId : imports.keySet()) {
                  try {
                     PackStateStore.ImportedPackState state = (PackStateStore.ImportedPackState)GSON.fromJson(
                        imports.get(packId), PackStateStore.ImportedPackState.class
                     );
                     if (state != null) {
                        if (state.packId == null || state.packId.isBlank()) {
                           state.packId = packId;
                        }

                        if (state.displayName == null || state.displayName.isBlank()) {
                           state.displayName = state.packId;
                        }

                        IMPORTS.put(state.packId, state);
                     }
                  } catch (Exception e) {
                     LOGGER.warn("Failed to parse pack state entry: {}", packId, e);
                  }
               }
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to load pack state store", e);
         }
      }

      migrateLegacyImportedPacks();
   }

   public static void save() {
      try {
         Files.createDirectories(STATE_PATH.getParent());
         JsonObject root = new JsonObject();
         JsonObject imports = new JsonObject();

         for (Entry<String, PackStateStore.ImportedPackState> entry : IMPORTS.entrySet()) {
            imports.add(entry.getKey(), GSON.toJsonTree(entry.getValue()));
         }

         root.add("imports", imports);
         Files.writeString(STATE_PATH, GSON.toJson(root), StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOGGER.warn("Failed to save pack state store", e);
      }
   }

   public static Collection<PackStateStore.ImportedPackState> getImportedPacks() {
      return IMPORTS.values().stream().map(PackStateStore.ImportedPackState::copy).toList();
   }

   @Nullable
   public static PackStateStore.ImportedPackState getImportedPack(String packId) {
      PackStateStore.ImportedPackState state = IMPORTS.get(packId);
      return state == null ? null : state.copy();
   }

   public static boolean hasImportedState(String packId) {
      return IMPORTS.containsKey(packId);
   }

   public static boolean isImported(String packId) {
      return IMPORTS.containsKey(packId) && hasLocalSceneCopy(packId);
   }

   public static boolean hasLocalSceneCopy(String packId) {
      Path packDir = SceneStore.getPackSceneDir(packId);
      if (containsJsonFiles(packDir)) {
         return true;
      }

      Path legacyPackDir = SceneStore.getSceneDir().resolve("_packs").resolve(packId);
      return containsJsonFiles(legacyPackDir);
   }

   private static boolean containsJsonFiles(@Nullable Path packDir) {
      if (packDir != null && Files.exists(packDir)) {
         try (Stream<Path> paths = Files.walk(packDir)) {
            return paths.anyMatch(path -> Files.isRegularFile(path) && path.getFileName().toString().toLowerCase().endsWith(".json"));
         } catch (IOException e) {
            LOGGER.warn("Failed to inspect imported local pack directory: {}", packDir, e);
            return false;
         }
      } else {
         return false;
      }
   }

   public static PackStateStore.ImportedPackState markImported(PonderPackInfo info) {
      PackStateStore.ImportedPackState state = IMPORTS.computeIfAbsent(info.name, ignored -> new PackStateStore.ImportedPackState());
      state.packId = info.name;
      state.displayName = info.name;
      state.sourceFile = info.sourcePath != null && info.sourcePath.getFileName() != null ? info.sourcePath.getFileName().toString() : state.sourceFile;
      state.importedVersion = info.version;
      state.currentSourceVersion = info.version;
      state.importedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
      save();
      return state.copy();
   }

   public static void updateCurrentSource(PonderPackInfo info) {
      PackStateStore.ImportedPackState state = IMPORTS.get(info.name);
      if (state != null) {
         boolean changed = false;
         if (!equalsNullable(state.displayName, info.name)) {
            state.displayName = info.name;
            changed = true;
         }

         String sourceFile = info.sourcePath != null && info.sourcePath.getFileName() != null ? info.sourcePath.getFileName().toString() : null;
         if (!equalsNullable(state.sourceFile, sourceFile)) {
            state.sourceFile = sourceFile;
            changed = true;
         }

         if (!equalsNullable(state.currentSourceVersion, info.version)) {
            state.currentSourceVersion = info.version;
            changed = true;
         }

         if (changed) {
            save();
         }
      }
   }

   public static void clearMissingSources(Collection<String> presentPackIds) {
      TreeSet<String> present = new TreeSet<>(presentPackIds);
      boolean changed = false;

      for (PackStateStore.ImportedPackState state : IMPORTS.values()) {
         if (state.packId != null && !state.packId.isBlank() && !present.contains(state.packId) && state.currentSourceVersion != null) {
            state.currentSourceVersion = null;
            changed = true;
         }
      }

      if (changed) {
         save();
      }
   }

   public static boolean shouldNotifySourceUpdate(String packId, String sourceVersion) {
      PackStateStore.ImportedPackState state = IMPORTS.get(packId);
      if (state == null || state.importedVersion == null || state.importedVersion.isBlank()) {
         return false;
      } else if (sourceVersion == null || sourceVersion.isBlank()) {
         return false;
      } else {
         return sourceVersion.equals(state.importedVersion) ? false : !sourceVersion.equals(state.lastNotifiedSourceVersion);
      }
   }

   public static void markNotified(String packId, String sourceVersion) {
      PackStateStore.ImportedPackState state = IMPORTS.get(packId);
      if (state != null) {
         if (!equalsNullable(state.lastNotifiedSourceVersion, sourceVersion)) {
            state.lastNotifiedSourceVersion = sourceVersion;
            save();
         }
      }
   }

   public static void removeImportedPack(String packId) {
      if (IMPORTS.remove(packId) != null) {
         save();
      }
   }

   private static void migrateLegacyImportedPacks() {
      Map<String, PackStateStore.LegacyPackEntry> legacyRegistry = loadLegacyRegistry();
      boolean changed = false;

      for (String packId : listLocalImportedPackIds()) {
         PackStateStore.ImportedPackState state = IMPORTS.get(packId);
         PackStateStore.LegacyPackEntry legacy = legacyRegistry.get(packId);
         if (state == null) {
            state = new PackStateStore.ImportedPackState();
            state.packId = packId;
            state.displayName = legacy != null && legacy.name != null && !legacy.name.isBlank() ? legacy.name : packId;
            state.sourceFile = legacy != null ? legacy.sourceFile : null;
            state.importedVersion = legacy != null ? legacy.version : null;
            state.currentSourceVersion = legacy != null ? legacy.version : null;
            state.importedAt = legacy != null ? legacy.loadedAt : null;
            IMPORTS.put(packId, state);
            changed = true;
         } else {
            if ((state.displayName == null || state.displayName.isBlank()) && legacy != null && legacy.name != null && !legacy.name.isBlank()) {
               state.displayName = legacy.name;
               changed = true;
            }

            if ((state.sourceFile == null || state.sourceFile.isBlank()) && legacy != null && legacy.sourceFile != null && !legacy.sourceFile.isBlank()) {
               state.sourceFile = legacy.sourceFile;
               changed = true;
            }

            if ((state.importedVersion == null || state.importedVersion.isBlank()) && legacy != null && legacy.version != null && !legacy.version.isBlank()) {
               state.importedVersion = legacy.version;
               changed = true;
            }

            if ((state.importedAt == null || state.importedAt.isBlank()) && legacy != null && legacy.loadedAt != null && !legacy.loadedAt.isBlank()) {
               state.importedAt = legacy.loadedAt;
               changed = true;
            }
         }
      }

      if (changed) {
         save();
      }
   }

   private static Map<String, PackStateStore.LegacyPackEntry> loadLegacyRegistry() {
      Map<String, PackStateStore.LegacyPackEntry> entries = new LinkedHashMap<>();
      if (!Files.exists(LEGACY_REGISTRY_PATH)) {
         return entries;
      }

      try (Reader reader = Files.newBufferedReader(LEGACY_REGISTRY_PATH, StandardCharsets.UTF_8)) {
         JsonObject root = (JsonObject)GSON.fromJson(reader, JsonObject.class);
         if (root != null && root.has("packs")) {
            JsonObject packs = root.getAsJsonObject("packs");

            for (String key : packs.keySet()) {
               try {
                  PackStateStore.LegacyPackEntry entry = (PackStateStore.LegacyPackEntry)GSON.fromJson(packs.get(key), PackStateStore.LegacyPackEntry.class);
                  if (entry != null) {
                     String packId = entry.name;
                     if ((packId == null || packId.isBlank()) && entry.packPrefix != null && entry.packPrefix.startsWith("[") && entry.packPrefix.endsWith("]")
                        )
                      {
                        packId = entry.packPrefix.substring(1, entry.packPrefix.length() - 1);
                     }

                     if (packId != null && !packId.isBlank()) {
                        if (entry.name == null || entry.name.isBlank()) {
                           entry.name = packId;
                        }

                        entries.put(packId, entry);
                     }
                  }
               } catch (Exception e) {
                  LOGGER.warn("Failed to parse legacy pack registry entry: {}", key, e);
               }
            }

            return entries;
         } else {
            return entries;
         }
      } catch (IOException e) {
         LOGGER.warn("Failed to read legacy pack registry", e);
         return entries;
      }
   }

   private static TreeSet<String> listLocalImportedPackIds() {
      TreeSet<String> packIds = new TreeSet<>();
      collectPackIds(SceneStore.getPacksRoot(), packIds);
      collectPackIds(SceneStore.getSceneDir().resolve("_packs"), packIds);
      collectPackIds(SceneStore.getStructureDir().resolve("_packs"), packIds);
      return packIds;
   }

   private static void collectPackIds(Path packsDir, TreeSet<String> packIds) {
      if (Files.exists(packsDir)) {
         try (Stream<Path> paths = Files.list(packsDir)) {
            paths.filter(x$0 -> Files.isDirectory(x$0))
               .map(path -> path.getFileName() != null ? path.getFileName().toString() : null)
               .filter(name -> name != null && !name.isBlank())
               .forEach(packIds::add);
         } catch (IOException e) {
            LOGGER.warn("Failed to inspect imported pack directory: {}", packsDir, e);
         }
      }
   }

   private static boolean equalsNullable(@Nullable String left, @Nullable String right) {
      return left == null ? right == null : left.equals(right);
   }

   public static final class ImportedPackState {
      public String packId;
      public String displayName;
      public String sourceFile;
      public String importedVersion;
      public String currentSourceVersion;
      public String lastNotifiedSourceVersion;
      public String importedAt;

      PackStateStore.ImportedPackState copy() {
         PackStateStore.ImportedPackState copy = new PackStateStore.ImportedPackState();
         copy.packId = this.packId;
         copy.displayName = this.displayName;
         copy.sourceFile = this.sourceFile;
         copy.importedVersion = this.importedVersion;
         copy.currentSourceVersion = this.currentSourceVersion;
         copy.lastNotifiedSourceVersion = this.lastNotifiedSourceVersion;
         copy.importedAt = this.importedAt;
         return copy;
      }
   }

   private static final class LegacyPackEntry {
      public String name;
      public String version;
      public String sourceFile;
      public String loadedAt;
      public String packPrefix;
   }
}

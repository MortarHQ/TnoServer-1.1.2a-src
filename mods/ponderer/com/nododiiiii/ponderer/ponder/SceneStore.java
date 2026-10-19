package com.nododiiiii.ponderer.ponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.util.SafePaths;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;

public final class SceneStore {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = new GsonBuilder()
      .setLenient()
      .disableHtmlEscaping()
      .registerTypeAdapter(LocalizedText.class, new LocalizedText.GsonAdapter())
      .create();
   private static final Gson GSON_PRETTY = new GsonBuilder()
      .setPrettyPrinting()
      .disableHtmlEscaping()
      .registerTypeAdapter(LocalizedText.class, new LocalizedText.GsonAdapter())
      .create();
   private static final String BASE_DIR = "ponderer";
   private static final String SCRIPT_DIR = "scripts";
   private static final String STRUCTURE_DIR = "structures";
   private static final String PACKS_SUBDIR = "_packs";
   private static final String SAVE_ERROR_KEY_PREFIX = "ponderer.ui.save_error.";
   private static final String[] BASIC_STRUCTURES = new String[]{"basic", "basic_xs", "basic_s", "basic_l", "basic_xl", "basic_xxl"};

   private SceneStore() {
   }

   @Nullable
   public static DslScene copyScene(@Nullable DslScene scene) {
      return scene == null ? null : (DslScene)GSON.fromJson(GSON.toJson(scene), DslScene.class);
   }

   public static Path getSceneDir() {
      return PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve("scripts");
   }

   public static Path getStructureDir() {
      return PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve("structures");
   }

   public static Path getReadonlyCacheRoot() {
      return PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve(".cache").resolve("readonly");
   }

   public static Path getPacksRoot() {
      return PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve("_packs");
   }

   private static Path getReadonlyCachePacksRoot() {
      return getReadonlyCacheRoot().resolve("_packs");
   }

   private static Path getLegacyPackSceneRoot() {
      return getSceneDir().resolve("_packs");
   }

   private static Path getLegacyPackStructureRoot() {
      return getStructureDir().resolve("_packs");
   }

   private static Path getServerPacksRoot(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("ponderer").resolve("_packs");
   }

   private static Path getLegacyServerPackSceneRoot(MinecraftServer server) {
      return getServerSceneDir(server).resolve("_packs");
   }

   private static Path getLegacyServerPackStructureRoot(MinecraftServer server) {
      return getServerStructureDir(server).resolve("_packs");
   }

   @Nullable
   private static Path resolvePackTypeDir(@Nullable Path packDir, String typeDir) {
      return packDir == null ? null : SafePaths.resolveFileName(packDir, typeDir);
   }

   @Nullable
   public static Path getReadonlyCachePackDir(String packName) {
      return SafePaths.resolveFileName(getReadonlyCachePacksRoot(), packName);
   }

   @Nullable
   public static Path getReadonlyCachePackSceneDir(String packName) {
      return resolvePackTypeDir(getReadonlyCachePackDir(packName), "scripts");
   }

   @Nullable
   public static Path getReadonlyCachePackStructureDir(String packName) {
      return resolvePackTypeDir(getReadonlyCachePackDir(packName), "structures");
   }

   @Nullable
   public static Path getPackDir(String packName) {
      return SafePaths.resolveFileName(getPacksRoot(), packName);
   }

   @Nullable
   public static Path getPackSceneDir(String packName) {
      return resolvePackTypeDir(getPackDir(packName), "scripts");
   }

   @Nullable
   public static Path getPackStructureDir(String packName) {
      return resolvePackTypeDir(getPackDir(packName), "structures");
   }

   @Nullable
   private static Path getLegacyPackSceneDir(String packName) {
      return SafePaths.resolveFileName(getLegacyPackSceneRoot(), packName);
   }

   @Nullable
   private static Path getLegacyPackStructureDir(String packName) {
      return SafePaths.resolveFileName(getLegacyPackStructureRoot(), packName);
   }

   @Nullable
   private static Path getServerPackDir(MinecraftServer server, String packName) {
      return SafePaths.resolveFileName(getServerPacksRoot(server), packName);
   }

   @Nullable
   public static Path getServerPackSceneDir(MinecraftServer server, String packName) {
      return resolvePackTypeDir(getServerPackDir(server, packName), "scripts");
   }

   @Nullable
   public static Path getServerPackStructureDir(MinecraftServer server, String packName) {
      return resolvePackTypeDir(getServerPackDir(server, packName), "structures");
   }

   @Nullable
   public static Path getStructurePath(String path) {
      return SafePaths.resolveRelativePath(getStructureDir(), path + ".nbt");
   }

   @Nullable
   public static Path getStructurePath(ResourceLocation id) {
      return resolveStructurePath(getStructureDir(), id);
   }

   @Nullable
   public static Path resolveStructurePath(String path, @Nullable String packName) {
      if (packName != null && !packName.isEmpty()) {
         Path packPath = resolvePackScopedPath(getPackStructureDir(packName), packName, path + ".nbt");
         if (packPath != null && Files.exists(packPath)) {
            return packPath;
         }

         Path readonlyPackPath = resolvePackScopedPath(getReadonlyCachePackStructureDir(packName), packName, path + ".nbt");
         if (readonlyPackPath != null && Files.exists(readonlyPackPath)) {
            return readonlyPackPath;
         }
      }

      Path flatPath = SafePaths.resolveRelativePath(getStructureDir(), path + ".nbt");
      if (flatPath != null && Files.exists(flatPath)) {
         return flatPath;
      }

      Path importedPath = findStructureInPackRoots(getPacksRoot(), path);
      return importedPath != null ? importedPath : findStructureInPackRoots(getReadonlyCachePacksRoot(), path);
   }

   private static Path getPackContentSearchRoot(Path packDir, String typeDir) {
      if (packDir == null) {
         return null;
      }

      Path typedRoot = packDir.resolve(typeDir);
      return Files.isDirectory(typedRoot) ? typedRoot : packDir;
   }

   @Nullable
   private static Path findStructureInPackRoots(Path packsDir, String path) {
      if (!Files.exists(packsDir)) {
         return null;
      }

      try (Stream<Path> packDirs = Files.list(packsDir)) {
         for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).toList()) {
            Path searchRoot = getPackContentSearchRoot(packDir, "structures");
            if (searchRoot != null && Files.exists(searchRoot)) {
               try (Stream<Path> files = Files.walk(searchRoot)) {
                  for (Path file : files.filter(x$0 -> Files.isRegularFile(x$0)).toList()) {
                     String fname = file.getFileName().toString();
                     if (fname.endsWith(".nbt")) {
                        Path relative = searchRoot.relativize(file);
                        if (relative.getNameCount() != 0) {
                           String first = relative.getName(0).toString();
                           String prefix = DslScene.extractPackPrefix(first);
                           if (prefix != null) {
                              first = first.substring(prefix.length()).trim();
                           }

                           String candidate = relative.getNameCount() == 1
                              ? first
                              : first + "/" + relative.subpath(1, relative.getNameCount()).toString().replace("\\", "/");
                           if (candidate.endsWith(".nbt")) {
                              candidate = candidate.substring(0, candidate.length() - 4);
                           }

                           String defaultNamespaceCandidate = candidate.startsWith("ponderer/") ? candidate.substring("ponderer/".length()) : candidate;
                           if (candidate.equals(path) || defaultNamespaceCandidate.equals(path)) {
                              return file;
                           }
                        }
                     }
                  }
               } catch (IOException var19) {
               }
            }
         }
      } catch (IOException var21) {
      }

      return null;
   }

   public static Path getServerSceneDir(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("ponderer").resolve("scripts");
   }

   public static Path getServerStructureDir(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("ponderer").resolve("structures");
   }

   @Nullable
   private static Path resolveScenePath(Path root, ResourceLocation id) {
      return SafePaths.resolveNamespacedPath(root, id, "ponderer", ".json");
   }

   @Nullable
   private static Path resolveStructurePath(Path root, ResourceLocation id) {
      return SafePaths.resolveNamespacedPath(root, id, "ponderer", ".nbt");
   }

   @Nullable
   private static Path resolveServerNamespacedPath(Path root, ResourceLocation id, String extension) {
      return SafePaths.resolveRelativePath(root, id.getNamespace() + "/" + id.getPath() + extension);
   }

   @Nullable
   private static Path resolvePackScopedPath(Path packDir, String packName, String relativePath) {
      List<String> segments = SafePaths.splitValidatedRelativePath(relativePath);
      if (packDir != null && segments != null && !segments.isEmpty()) {
         List<String> targetSegments = new ArrayList<>(segments);
         targetSegments.set(0, "[" + packName + "] " + targetSegments.get(0));
         return SafePaths.resolveRelativePath(packDir, targetSegments);
      } else {
         return null;
      }
   }

   @Nullable
   public static Path resolveServerScenePath(MinecraftServer server, ResourceLocation id, @Nullable String pack) {
      if (pack != null && !pack.isBlank()) {
         ensureServerPackLayoutMigrated(server);
         Path packDir = getServerPackSceneDir(server, pack);
         return packDir == null ? null : resolvePackScopedPath(packDir, pack, id.getNamespace() + "/" + id.getPath() + ".json");
      } else {
         return resolveServerNamespacedPath(getServerSceneDir(server), id, ".json");
      }
   }

   @Nullable
   public static Path resolveServerStructurePath(MinecraftServer server, ResourceLocation id, @Nullable String pack) {
      if (pack != null && !pack.isBlank()) {
         ensureServerPackLayoutMigrated(server);
         Path packDir = getServerPackStructureDir(server, pack);
         return packDir == null ? null : resolvePackScopedPath(packDir, pack, id.getNamespace() + "/" + id.getPath() + ".nbt");
      } else {
         return resolveServerNamespacedPath(getServerStructureDir(server), id, ".nbt");
      }
   }

   @Nullable
   public static Path resolveLocalSyncStructurePath(ResourceLocation id, @Nullable String pack) {
      if (pack != null && !pack.isBlank()) {
         Path packDir = getPackStructureDir(pack);
         if (packDir == null) {
            return null;
         }

         String relativePath = id.getNamespace().equals("ponderer") ? id.getPath() + ".nbt" : id.getNamespace() + "/" + id.getPath() + ".nbt";
         return resolvePackScopedPath(packDir, pack, relativePath);
      } else {
         return resolveStructurePath(getStructureDir(), id);
      }
   }

   public static String displaySceneKey(String id, @Nullable String pack) {
      return pack != null && !pack.isBlank() ? "[" + pack + "] " + id : id;
   }

   public static boolean saveToServer(MinecraftServer server, String sceneId, @Nullable String pack, String json) {
      ResourceLocation sceneLoc = ResourceLocation.tryParse(sceneId);
      if (sceneLoc == null) {
         LOGGER.warn("Invalid scene id: {}", sceneId);
         return false;
      }

      Path scenePath = resolveServerScenePath(server, sceneLoc, pack);
      if (scenePath == null) {
         LOGGER.warn("Rejected unsafe scene path for id {} pack {}", sceneId, pack);
         return false;
      }

      try {
         Files.createDirectories(scenePath.getParent());
         Files.writeString(scenePath, json, StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOGGER.error("Failed to write scene json: {}", scenePath, e);
         return false;
      }

      LOGGER.info("Uploaded scene {} to server storage (pack={})", sceneId, pack);
      return true;
   }

   public static boolean saveStructureToServer(MinecraftServer server, String structureId, @Nullable String pack, byte[] structureBytes) {
      if (structureId != null && !structureId.isBlank() && structureBytes != null) {
         ResourceLocation structureLoc = ResourceLocation.tryParse(structureId);
         if (structureLoc == null) {
            LOGGER.warn("Invalid structure id: {}", structureId);
            return false;
         }

         Path structurePath = resolveServerStructurePath(server, structureLoc, pack);
         if (structurePath == null) {
            LOGGER.warn("Rejected unsafe structure path for id {} pack {}", structureId, pack);
            return false;
         }

         try {
            Files.createDirectories(structurePath.getParent());
            Files.write(structurePath, structureBytes);
            return true;
         } catch (IOException e) {
            LOGGER.error("Failed to write structure: {}", structurePath, e);
            return false;
         }
      } else {
         return true;
      }
   }

   public static List<SceneStore.SyncFileRef> collectServerScriptRefs(MinecraftServer server) {
      ensureServerPackLayoutMigrated(server);
      Path flatRoot = getServerSceneDir(server);
      Path packsRoot = getServerPacksRoot(server);
      if (!Files.exists(flatRoot) && !Files.exists(packsRoot)) {
         return List.of();
      }

      List<SceneStore.SyncFileRef> entries = new ArrayList<>();
      collectServerSceneRefsFromRoot(flatRoot, null, entries);
      if (!Files.exists(packsRoot)) {
         return entries;
      }

      try (Stream<Path> packDirs = Files.list(packsRoot)) {
         for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).sorted().toList()) {
            String packId = packDir.getFileName() != null ? packDir.getFileName().toString() : null;
            if (packId != null && !packId.isBlank()) {
               collectServerSceneRefsFromRoot(packDir.resolve("scripts"), packId, entries);
            }
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to collect packed server scripts", e);
      }

      return entries;
   }

   private static void collectServerSceneRefsFromRoot(Path root, @Nullable String explicitPack, List<SceneStore.SyncFileRef> entries) {
      if (root != null && Files.exists(root)) {
         try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.filter(p -> p.toString().toLowerCase(Locale.ROOT).endsWith(".json"))
               .filter(pathx -> !pathx.startsWith(root.resolve("_packs")))
               .sorted(Comparator.comparing(Path::toString))
               .toList()) {
               SceneStore.SyncFileRef ref = toServerSceneRef(root, path, explicitPack);
               if (ref != null) {
                  entries.add(ref);
               }
            }
         } catch (Exception e) {
            LOGGER.warn("Failed to collect server scripts from {}", root, e);
         }
      }
   }

   public static List<SceneStore.SyncFileRef> collectServerStructureRefs(MinecraftServer server) {
      ensureServerPackLayoutMigrated(server);
      Path flatRoot = getServerStructureDir(server);
      Path packsRoot = getServerPacksRoot(server);
      if (!Files.exists(flatRoot) && !Files.exists(packsRoot)) {
         return List.of();
      }

      List<SceneStore.SyncFileRef> entries = new ArrayList<>();
      collectServerStructureRefsFromRoot(flatRoot, null, entries);
      if (!Files.exists(packsRoot)) {
         return entries;
      }

      try (Stream<Path> packDirs = Files.list(packsRoot)) {
         for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).sorted().toList()) {
            String packId = packDir.getFileName() != null ? packDir.getFileName().toString() : null;
            if (packId != null && !packId.isBlank()) {
               collectServerStructureRefsFromRoot(packDir.resolve("structures"), packId, entries);
            }
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to collect packed server structures", e);
      }

      return entries;
   }

   private static void collectServerStructureRefsFromRoot(Path root, @Nullable String explicitPack, List<SceneStore.SyncFileRef> entries) {
      if (root != null && Files.exists(root)) {
         try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.filter(p -> p.toString().toLowerCase(Locale.ROOT).endsWith(".nbt"))
               .filter(pathx -> !pathx.startsWith(root.resolve("_packs")))
               .sorted(Comparator.comparing(Path::toString))
               .toList()) {
               SceneStore.SyncFileRef ref = toServerStructureRef(root, path, ".nbt", explicitPack);
               if (ref != null) {
                  entries.add(ref);
               }
            }
         } catch (Exception e) {
            LOGGER.warn("Failed to collect server structures from {}", root, e);
         }
      }
   }

   @Nullable
   private static SceneStore.SyncFileRef toServerSceneRef(Path root, Path file, @Nullable String explicitPack) {
      try {
         String json = Files.readString(file, StandardCharsets.UTF_8);
         DslScene scene = (DslScene)GSON.fromJson(json, DslScene.class);
         if (scene != null && scene.id != null && !scene.id.isBlank()) {
            String inferredPack = explicitPack != null && !explicitPack.isBlank() ? explicitPack : inferPackName(root, file);
            String pack = scene.pack != null && !scene.pack.isBlank() ? scene.pack : inferredPack;
            return new SceneStore.SyncFileRef(scene.id, pack, file);
         } else {
            LOGGER.warn("Skipping invalid server scene file (missing id): {}", file);
            return null;
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to inspect server scene file: {}", file, e);
         return null;
      }
   }

   @Nullable
   static SceneStore.SyncFileRef toServerStructureRef(Path root, Path file, String ext, @Nullable String explicitPack) {
      Path rel = root.relativize(file);
      if (rel.getNameCount() < 1) {
         return null;
      }

      String pack = explicitPack;
      Path idPath = rel;
      if ((pack == null || pack.isBlank()) && "_packs".equals(rel.getName(0).toString()) && rel.getNameCount() >= 3) {
         pack = rel.getName(1).toString();
         idPath = rel.subpath(2, rel.getNameCount());
      }

      if (pack != null && !pack.isBlank()) {
         String first = idPath.getName(0).toString();
         String prefix = "[" + pack + "] ";
         if (first.startsWith(prefix)) {
            idPath = idPath.getNameCount() == 1
               ? Path.of(first.substring(prefix.length()))
               : Path.of(first.substring(prefix.length()), idPath.subpath(1, idPath.getNameCount()).toString());
         }
      }

      if (idPath.getNameCount() == 0) {
         return null;
      }

      String namespace;
      String path;
      if (idPath.getNameCount() == 1) {
         namespace = "ponderer";
         path = idPath.getName(0).toString();
      } else {
         namespace = idPath.getName(0).toString();
         path = idPath.subpath(1, idPath.getNameCount()).toString().replace("\\", "/");
      }

      if (path.endsWith(ext)) {
         path = path.substring(0, path.length() - ext.length());
      }

      return !namespace.isBlank() && !path.isBlank() ? new SceneStore.SyncFileRef(namespace + ":" + path, pack, file) : null;
   }

   @Nullable
   private static String inferPackName(Path root, Path file) {
      Path rel = root.relativize(file);
      return rel.getNameCount() >= 2 && "_packs".equals(rel.getName(0).toString()) ? rel.getName(1).toString() : null;
   }

   @Nullable
   public static Path resolveLocalScenePath(DslScene scene) {
      if (scene != null && scene.id != null && !scene.id.isBlank()) {
         ResourceLocation loc = ResourceLocation.tryParse(scene.id);
         if (loc == null) {
            return null;
         }

         Path dir;
         String filename;
         if (scene.pack != null && !scene.pack.isEmpty()) {
            dir = getPackSceneDir(scene.pack);
            if (dir == null) {
               return null;
            }

            filename = "[" + scene.pack + "] " + loc.getPath().replace('/', '_') + ".json";
         } else {
            dir = getSceneDir();
            filename = loc.getPath().replace('/', '_') + ".json";
         }

         Path filePath = SafePaths.resolveFileName(dir, filename);
         if (filePath == null) {
            return null;
         }

         Path existingFile = findExistingFileForScene(scene);
         return existingFile != null ? existingFile : filePath;
      } else {
         return null;
      }
   }

   @Nullable
   public static Path findLocalSceneFile(String sceneId) {
      return findLocalSceneFile(sceneId, null);
   }

   @Nullable
   public static Path findLocalSceneFile(String sceneId, @Nullable String pack) {
      if (sceneId != null && !sceneId.isBlank()) {
         DslScene temp = new DslScene();
         temp.id = sceneId;
         temp.pack = pack;
         if (pack != null && !pack.isBlank()) {
            Path packDir = getPackSceneDir(pack);
            Path existing = packDir == null ? null : findExistingFile(packDir, sceneId);
            return existing != null ? existing : resolveLocalScenePath(temp);
         }

         Path existing = findExistingFile(getSceneDir(), sceneId);
         if (existing != null) {
            return existing;
         }

         ResourceLocation loc = ResourceLocation.tryParse(sceneId);
         return loc == null ? null : resolveLocalScenePath(temp);
      } else {
         return null;
      }
   }

   public static SceneStore.LocalSaveResult saveSceneToLocalDetailed(DslScene scene) {
      SceneStore.LocalSaveResult validationFailure = validateLocalSceneSave(scene);
      if (validationFailure != null) {
         LOGGER.warn(validationFailure.englishMessage());
         return validationFailure;
      }

      Path filePath = resolveLocalScenePath(scene);
      if (filePath == null) {
         SceneStore.LocalSaveResult failure = SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + scene.id + "' because the output path could not be resolved safely", "ponderer.ui.save_error.file_name_invalid"
         );
         LOGGER.warn(failure.englishMessage());
         return failure;
      }

      try {
         Files.createDirectories(filePath.getParent());
         sanitizeScene(scene);
         String json = GSON_PRETTY.toJson(scene);
         Files.writeString(filePath, json, StandardCharsets.UTF_8);
         if (scene.pack != null && !scene.pack.isBlank()) {
            deleteDirectoryRecursive(getReadonlyCachePackSceneDir(scene.pack));
            deleteDirectoryRecursive(getReadonlyCachePackStructureDir(scene.pack));
         }

         LOGGER.info("Saved scene {} to {}", scene.id, filePath);
         return SceneStore.LocalSaveResult.success(filePath, "Saved scene '" + scene.id + "' to " + filePath);
      } catch (IOException e) {
         SceneStore.LocalSaveResult failure = mapIoFailure(scene.id, filePath, e);
         LOGGER.error(failure.englishMessage(), e);
         return failure;
      }
   }

   public static boolean saveSceneToLocal(DslScene scene) {
      return saveSceneToLocalDetailed(scene).isSuccess();
   }

   @Nullable
   private static Path findExistingFileForScene(DslScene scene) {
      if (scene.pack != null && !scene.pack.isEmpty()) {
         Path packDir = getPackSceneDir(scene.pack);
         return packDir == null ? null : findExistingFile(packDir, scene.id);
      } else {
         return findExistingFile(getSceneDir(), scene.id);
      }
   }

   private static Path findExistingFile(Path dir, String sceneId) {
      if (!Files.exists(dir)) {
         return null;
      }

      try (Stream<Path> paths = Files.walk(dir)) {
         for (Path path : paths.filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json")).toList()) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
               DslScene existing = (DslScene)GSON.fromJson(reader, DslScene.class);
               if (existing != null && sceneId.equals(existing.id)) {
                  return path;
               }
            } catch (Exception var11) {
            }
         }
      } catch (IOException var13) {
      }

      return null;
   }

   public static boolean deleteSceneLocal(String sceneId) {
      if (sceneId != null && !sceneId.isBlank()) {
         Path existing = findExistingFile(getSceneDir(), sceneId);
         if (existing == null) {
            existing = findExistingFileInPacks(sceneId);
         }

         if (existing == null) {
            LOGGER.warn("No local file found for scene id: {}", sceneId);
            return false;
         }

         try {
            Files.deleteIfExists(existing);
            LOGGER.info("Deleted scene file: {}", existing);
            return true;
         } catch (IOException e) {
            LOGGER.error("Failed to delete scene file: {}", existing, e);
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static Path findExistingFileInPacks(String sceneId) {
      Path packsDir = getPacksRoot();
      if (!Files.exists(packsDir)) {
         return null;
      }

      try (Stream<Path> packDirs = Files.list(packsDir)) {
         for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).toList()) {
            Path searchRoot = getPackContentSearchRoot(packDir, "scripts");
            if (searchRoot != null && Files.exists(searchRoot)) {
               Path found = findExistingFile(searchRoot, sceneId);
               if (found != null) {
                  return found;
               }
            }
         }
      } catch (IOException var10) {
      }

      return null;
   }

   @Nullable
   private static Path findExistingFileByKey(String sceneKey) {
      if (sceneKey != null && !sceneKey.isBlank()) {
         String packPrefix = DslScene.extractPackPrefix(sceneKey);
         String sceneId;
         String packName;
         if (packPrefix != null) {
            sceneId = sceneKey.substring(packPrefix.length()).trim();
            packName = packPrefix.substring(1, packPrefix.length() - 1);
         } else {
            sceneId = sceneKey;
            packName = null;
         }

         Path searchDir = packName != null ? getPackSceneDir(packName) : getSceneDir();
         if (searchDir == null) {
            return null;
         } else {
            Path result = findExistingFile(searchDir, sceneId);
            if (result != null) {
               return result;
            } else {
               return packName != null ? findExistingFile(getSceneDir(), sceneId) : null;
            }
         }
      } else {
         return null;
      }
   }

   public static boolean deleteSceneByKey(String sceneKey) {
      if (sceneKey != null && !sceneKey.isBlank()) {
         Path existing = findExistingFileByKey(sceneKey);
         if (existing == null) {
            LOGGER.warn("No local file found for scene key: {}", sceneKey);
            return false;
         }

         try {
            Files.deleteIfExists(existing);
            LOGGER.info("Deleted scene file: {}", existing);
            return true;
         } catch (IOException e) {
            LOGGER.error("Failed to delete scene file: {}", existing, e);
            return false;
         }
      } else {
         return false;
      }
   }

   private static void ensureLocalPackLayoutMigrated() {
      migrateLegacyPackTypeRoot(getLegacyPackSceneRoot(), getPacksRoot(), "scripts", "local script");
      migrateLegacyPackTypeRoot(getLegacyPackStructureRoot(), getPacksRoot(), "structures", "local structure");
   }

   private static void ensureServerPackLayoutMigrated(MinecraftServer server) {
      migrateLegacyPackTypeRoot(getLegacyServerPackSceneRoot(server), getServerPacksRoot(server), "scripts", "server script");
      migrateLegacyPackTypeRoot(getLegacyServerPackStructureRoot(server), getServerPacksRoot(server), "structures", "server structure");
   }

   private static void migrateLegacyPackTypeRoot(Path legacyRoot, Path packsRoot, String typeDir, String label) {
      if (legacyRoot != null && Files.exists(legacyRoot)) {
         try (Stream<Path> packDirs = Files.list(legacyRoot)) {
            for (Path legacyPackDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).toList()) {
               String packId = legacyPackDir.getFileName() != null ? legacyPackDir.getFileName().toString() : null;
               if (packId != null && !packId.isBlank()) {
                  Path packDir = SafePaths.resolveFileName(packsRoot, packId);
                  Path targetDir = resolvePackTypeDir(packDir, typeDir);
                  if (packDir != null && targetDir != null) {
                     migrateLegacyPackDirectory(legacyPackDir, targetDir, label);
                  } else {
                     LOGGER.warn("Rejected unsafe legacy {} pack path for {}", label, legacyPackDir);
                  }
               }
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to inspect legacy {} pack root {}", new Object[]{label, legacyRoot, e});
         }

         deleteEmptyDirectories(legacyRoot);
      }
   }

   private static void migrateLegacyPackDirectory(Path legacyDir, Path targetDir, String label) {
      try {
         Path targetParent = targetDir.getParent();
         if (targetParent == null) {
            LOGGER.warn("Missing parent for migrated {} pack directory {}", label, targetDir);
            return;
         }

         Files.createDirectories(targetParent);
         if (!Files.exists(targetDir)) {
            Files.move(legacyDir, targetDir);
            LOGGER.info("Migrated legacy {} pack directory: {} -> {}", new Object[]{label, legacyDir, targetDir});
            return;
         }

         try (Stream<Path> paths = Files.walk(legacyDir)) {
            for (Path path : paths.sorted(Comparator.comparingInt(Path::getNameCount)).toList()) {
               Path relative = legacyDir.relativize(path);
               if (relative.getNameCount() != 0) {
                  Path targetPath = SafePaths.resolveRelativePath(targetDir, relative.toString().replace("\\", "/"));
                  if (targetPath == null) {
                     LOGGER.warn("Rejected unsafe migrated {} relative path {} in {}", new Object[]{label, relative, legacyDir});
                  } else if (Files.isDirectory(path)) {
                     Files.createDirectories(targetPath);
                  } else {
                     Files.createDirectories(targetPath.getParent());
                     if (!Files.exists(targetPath)) {
                        Files.move(path, targetPath);
                     } else if (filesHaveSameContent(path, targetPath)) {
                        Files.deleteIfExists(path);
                     } else {
                        LOGGER.warn("Keeping conflicting legacy {} file {} because target already exists at {}", new Object[]{label, path, targetPath});
                     }
                  }
               }
            }
         }

         deleteEmptyDirectories(legacyDir);
         LOGGER.info("Merged legacy {} pack directory into {}", label, targetDir);
      } catch (IOException e) {
         LOGGER.warn("Failed to migrate legacy {} pack directory {}", new Object[]{label, legacyDir, e});
      }
   }

   private static boolean filesHaveSameContent(Path left, Path right) {
      try {
         return Files.size(left) == Files.size(right) && Files.mismatch(left, right) == -1L;
      } catch (IOException e) {
         return false;
      }
   }

   private static void deleteEmptyDirectories(Path root) {
      if (root != null && Files.exists(root)) {
         try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
               if (Files.isDirectory(path)) {
                  try (Stream<Path> children = Files.list(path)) {
                     if (children.findAny().isEmpty()) {
                        Files.deleteIfExists(path);
                     }
                  } catch (IOException var10) {
                  }
               }
            }
         } catch (IOException var12) {
         }
      }
   }

   public static void extractDefaultsIfNeeded() {
      Path baseDir = PondererServices.PLATFORM.getConfigDir().resolve("ponderer");
      Path marker = baseDir.resolve(".initialized");
      Path scriptsDir = getSceneDir();
      Path structureDir = getStructureDir();

      try {
         Files.createDirectories(scriptsDir);
         Files.createDirectories(structureDir);
      } catch (IOException e) {
         LOGGER.error("Failed to create ponderer directories", e);
         return;
      }

      for (String name : BASIC_STRUCTURES) {
         extractResource("data/ponderer/default_structures/" + name + ".nbt", structureDir.resolve(name + ".nbt"));
      }

      if (!Files.exists(marker)) {
         try {
            extractResource("data/ponderer/default_scripts/ponderer_example.json", scriptsDir.resolve("ponderer_example.json"));
            Files.writeString(marker, "initialized", StandardCharsets.UTF_8);
         } catch (IOException e) {
            LOGGER.warn("Failed to write initialization marker", e);
         }
      }
   }

   public static boolean ensureBuiltinStructure(String path) {
      Path target = SafePaths.resolveRelativePath(getStructureDir(), path + ".nbt");
      if (target == null) {
         LOGGER.warn("Rejected unsafe built-in structure path: {}", path);
         return false;
      }

      if (Files.exists(target)) {
         return true;
      }

      try (InputStream in = openBuiltinStructure(path)) {
         if (in == null) {
            return false;
         }

         Files.createDirectories(target.getParent());
         Files.copy(in, target);
         LOGGER.info("Copied built-in structure to local: {}", target);
         return true;
      } catch (IOException e) {
         LOGGER.warn("Failed to copy built-in structure: {}", target, e);
         return false;
      }
   }

   private static void extractResource(String resourcePath, Path target) {
      if (!Files.exists(target)) {
         try (InputStream in = SceneStore.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in != null) {
               Files.copy(in, target);
               LOGGER.info("Extracted default file: {}", target);
            } else {
               LOGGER.warn("Default resource not found in jar: {}", resourcePath);
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to extract default file: {}", target, e);
         }
      }
   }

   public static InputStream openBuiltinStructure(String path) {
      List<String> segments = SafePaths.splitValidatedRelativePath(path);
      if (segments == null) {
         return null;
      }

      String resourcePath = "data/ponderer/default_structures/" + String.join("/", segments) + ".nbt";
      return SceneStore.class.getClassLoader().getResourceAsStream(resourcePath);
   }

   public static boolean isBuiltinStructureName(String name) {
      if (name != null && !name.isBlank()) {
         String cleaned = name.trim();
         if (cleaned.toLowerCase(Locale.ROOT).endsWith(".nbt")) {
            cleaned = cleaned.substring(0, cleaned.length() - 4);
         }

         try (InputStream in = openBuiltinStructure(cleaned)) {
            return in != null;
         } catch (IOException ignored) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static int reloadFromDisk() {
      ensureLocalPackLayoutMigrated();
      Path dir = getSceneDir();
      List<DslScene> loaded = new ArrayList<>();

      try {
         Files.createDirectories(dir);
      } catch (IOException e) {
         LOGGER.error("Failed to create ponderer scene directory: {}", dir, e);
         SceneRuntime.setScenes(List.of());
         return 0;
      }

      try (Stream<Path> paths = Files.walk(dir)) {
         paths.filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json"))
            .filter(path -> !path.startsWith(dir.resolve("_packs")))
            .sorted(Comparator.comparing(Path::toString))
            .forEach(path -> loadSceneFile(path, loaded));
      } catch (IOException e) {
         LOGGER.error("Failed to list scene directory: {}", dir, e);
      }

      loadPackScenesFromRoot(getPacksRoot(), loaded);
      loadPackScenesFromRoot(getReadonlyCachePacksRoot(), loaded);
      SceneRuntime.setScenes(loaded);
      LOGGER.info("Loaded {} ponderer scene(s) from {}", loaded.size(), dir);
      return loaded.size();
   }

   private static void loadPackScenesFromRoot(Path packsDir, List<DslScene> loaded) {
      if (Files.exists(packsDir)) {
         try (Stream<Path> packDirs = Files.list(packsDir)) {
            for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).sorted().toList()) {
               Path searchRoot = getPackContentSearchRoot(packDir, "scripts");
               if (searchRoot != null && Files.exists(searchRoot)) {
                  try (Stream<Path> paths = Files.walk(searchRoot)) {
                     paths.filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json"))
                        .sorted(Comparator.comparing(Path::toString))
                        .forEach(path -> loadSceneFile(path, loaded));
                  } catch (IOException e) {
                     LOGGER.warn("Failed to list pack directory: {}", packDir, e);
                  }
               }
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to list packs directory: {}", packsDir, e);
         }
      }
   }

   private static void loadSceneFile(Path path, List<DslScene> loaded) {
      try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
         DslScene scene = (DslScene)GSON.fromJson(reader, DslScene.class);
         if (scene != null && scene.id != null && !scene.id.isBlank()) {
            sanitizeScene(scene);
            scene.sourceFile = path.getFileName().toString();
            loaded.add(scene);
         } else {
            LOGGER.warn("Skipping invalid scene file (missing id): {}", path);
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to read scene file: {}", path, e);
      }
   }

   public static void sanitizeScene(DslScene scene) {
      if (scene.editable == null) {
         scene.editable = (Boolean)Config.DEFAULT_EDITABLE.get();
      }

      if (scene.scenes != null) {
         for (DslScene.SceneSegment seg : scene.scenes) {
            ensureFirstStepIsShowStructure(seg);
         }
      }
   }

   private static void ensureFirstStepIsShowStructure(DslScene.SceneSegment seg) {
      if (seg.steps != null && !seg.steps.isEmpty()) {
         for (DslScene.DslStep step : seg.steps) {
            if (step != null && step.type != null) {
               if ("show_structure".equalsIgnoreCase(step.type) || "show_interface".equalsIgnoreCase(step.type)) {
                  return;
               }
               break;
            }
         }

         List<DslScene.DslStep> fixed = new ArrayList<>();
         DslScene.DslStep showStep = new DslScene.DslStep();
         showStep.type = "show_structure";
         fixed.add(showStep);
         DslScene.DslStep idleStep = new DslScene.DslStep();
         idleStep.type = "idle";
         idleStep.duration = 20;
         fixed.add(idleStep);
         fixed.addAll(seg.steps);
         seg.steps = fixed;
         return;
      }
   }

   @Nullable
   private static SceneStore.LocalSaveResult validateLocalSceneSave(DslScene scene) {
      if (scene != null && scene.id != null && !scene.id.isBlank()) {
         String invalidSceneIdChar = findInvalidSceneIdCharacter(scene.id);
         ResourceLocation loc = ResourceLocation.tryParse(scene.id);
         if (loc == null) {
            return invalidSceneIdChar != null
               ? SceneStore.LocalSaveResult.failure(
                  "Cannot save scene '" + scene.id + "' because the scene id contains invalid character '" + invalidSceneIdChar + "'",
                  "ponderer.ui.save_error.scene_id_invalid_char",
                  invalidSceneIdChar
               )
               : SceneStore.LocalSaveResult.failure(
                  "Cannot save scene '" + scene.id + "' because the scene id is invalid", "ponderer.ui.save_error.scene_id_invalid"
               );
         }

         if (scene.pack != null && !scene.pack.isBlank()) {
            SafePaths.FileNameValidationError packError = SafePaths.diagnosePortableAssetName(scene.pack);
            if (packError != null) {
               return toPackNameFailure(scene.id, scene.pack, packError);
            }
         }

         String fileName = buildLocalSceneFileName(scene.pack, loc);
         SafePaths.FileNameValidationError fileNameError = SafePaths.diagnoseWindowsFileNameSegment(fileName);
         return fileNameError != null ? toFileNameFailure(scene.id, fileName, fileNameError) : null;
      } else {
         return SceneStore.LocalSaveResult.failure("Cannot save scene with blank id", "ponderer.ui.save_error.scene_id_blank");
      }
   }

   private static String buildLocalSceneFileName(@Nullable String packName, ResourceLocation loc) {
      String prefix = packName != null && !packName.isBlank() ? "[" + packName + "] " : "";
      return prefix + loc.getPath().replace('/', '_') + ".json";
   }

   @Nullable
   private static String findInvalidSceneIdCharacter(String sceneId) {
      if (sceneId == null) {
         return null;
      }

      for (int i = 0; i < sceneId.length(); i++) {
         char ch = sceneId.charAt(i);
         boolean allowed = ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9' || ch == '_' || ch == '-' || ch == '.' || ch == '/' || ch == ':';
         if (!allowed) {
            if (Character.isISOControl(ch)) {
               return String.format(Locale.ROOT, "U+%04X", Integer.valueOf(ch));
            }

            return Character.toString(ch);
         }
      }

      return null;
   }

   private static SceneStore.LocalSaveResult toPackNameFailure(String sceneId, String packName, SafePaths.FileNameValidationError error) {
      return switch (error.code()) {
         case INVALID_CHARACTER -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because pack name '" + packName + "' contains invalid character '" + error.offendingText() + "'",
            "ponderer.ui.save_error.pack_name_invalid_char",
            error.offendingText()
         );
         case RESERVED_NAME -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because pack name '" + packName + "' is a reserved Windows name",
            "ponderer.ui.save_error.pack_name_reserved",
            packName
         );
         case TRAILING_SPACE_OR_DOT -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because pack name '" + packName + "' ends with a space or dot", "ponderer.ui.save_error.pack_name_trailing"
         );
         case EMPTY, DOT_SEGMENT -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because pack name '" + packName + "' is invalid", "ponderer.ui.save_error.pack_name_invalid"
         );
      };
   }

   private static SceneStore.LocalSaveResult toFileNameFailure(String sceneId, String fileName, SafePaths.FileNameValidationError error) {
      return switch (error.code()) {
         case INVALID_CHARACTER -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because output filename '" + fileName + "' contains invalid character '" + error.offendingText() + "'",
            "ponderer.ui.save_error.file_name_invalid_char",
            error.offendingText()
         );
         case RESERVED_NAME -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because output filename '" + fileName + "' is a reserved Windows name",
            "ponderer.ui.save_error.file_name_reserved",
            fileName
         );
         case TRAILING_SPACE_OR_DOT -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because output filename '" + fileName + "' ends with a space or dot",
            "ponderer.ui.save_error.file_name_trailing"
         );
         case EMPTY, DOT_SEGMENT -> SceneStore.LocalSaveResult.failure(
            "Cannot save scene '" + sceneId + "' because output filename '" + fileName + "' is invalid", "ponderer.ui.save_error.file_name_invalid"
         );
      };
   }

   private static SceneStore.LocalSaveResult mapIoFailure(String sceneId, Path filePath, IOException e) {
      if (e instanceof AccessDeniedException) {
         return SceneStore.LocalSaveResult.failure(
            "Failed to save scene '" + sceneId + "' to " + filePath + ": access denied",
            "ponderer.ui.save_error.access_denied",
            filePath.getFileName() != null ? filePath.getFileName().toString() : filePath.toString()
         );
      }

      if (e instanceof NoSuchFileException) {
         return SceneStore.LocalSaveResult.failure(
            "Failed to save scene '" + sceneId + "' to " + filePath + ": target path does not exist",
            "ponderer.ui.save_error.path_missing",
            filePath.toString()
         );
      }

      String detail = "I/O error";
      if (e instanceof FileSystemException fileSystemException && fileSystemException.getReason() != null && !fileSystemException.getReason().isBlank()) {
         detail = "filesystem error: " + fileSystemException.getReason();
      } else if (e.getMessage() != null && !e.getMessage().isBlank()) {
         detail = "I/O error: " + e.getMessage();
      }

      return SceneStore.LocalSaveResult.failure("Failed to save scene '" + sceneId + "' to " + filePath + ": " + detail, "ponderer.ui.save_error.io", detail);
   }

   public static SceneStore.PackExportResult packScenesAndStructuresDetailed(String name, String version, String author) {
      SceneStore.PackExportResult nameFailure = validatePackExportName(name);
      if (nameFailure != null) {
         LOGGER.warn(nameFailure.englishMessage());
         return nameFailure;
      }

      String normalizedName = name.trim();
      Path resourcepacksDir = PondererServices.PLATFORM.getGameDir().resolve("resourcepacks");
      Path outputPath = resolvePackExportOutputPath(resourcepacksDir, normalizedName);
      if (outputPath == null) {
         SceneStore.PackExportResult failure = SceneStore.PackExportResult.failure(
            "Cannot export pack '" + normalizedName + "' because the output path could not be resolved safely", "ponderer.ui.save_error.pack_name_invalid"
         );
         LOGGER.warn(failure.englishMessage());
         return failure;
      }

      try {
         Files.createDirectories(resourcepacksDir);
         String filename = outputPath.getFileName() != null ? outputPath.getFileName().toString() : "[Ponderer] " + normalizedName + ".zip";
         String packJson = createPackMetadata(normalizedName, version, author);
         List<Path> allScriptFiles = collectAllScriptFiles();
         Set<String> allStructureRefs = new HashSet<>();

         try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(outputPath), StandardCharsets.UTF_8)) {
            writeZipEntry(zos, "pack.mcmeta", "{\"pack\": {\"pack_format\": 15, \"description\": \"Ponderer scene collection\"}}");
            writeZipEntry(zos, "pack.json", packJson);
            int count = 0;
            Set<String> usedEntryNames = new HashSet<>();

            for (Path p : allScriptFiles) {
               String cleanJson = readAndUpdatePackField(p, normalizedName);
               if (cleanJson != null) {
                  collectStructureReferences(cleanJson, allStructureRefs);
                  String cleanFilename = stripPackPrefix(p.getFileName().toString());
                  cleanFilename = deduplicateFilename(cleanFilename, usedEntryNames);
                  String entryName = "data/ponderer/scripts/" + cleanFilename;
                  zos.putNextEntry(new ZipEntry(entryName));
                  zos.write(cleanJson.getBytes(StandardCharsets.UTF_8));
                  zos.closeEntry();
                  count++;
               }
            }

            count += writeStructuresToZip(zos, allStructureRefs);
            LOGGER.info("Packed {} files into {}", count, filename);
         }

         return SceneStore.PackExportResult.success(outputPath, "Exported pack '" + normalizedName + "' to " + outputPath);
      } catch (IOException e) {
         SceneStore.PackExportResult failure = mapPackExportIoFailure(normalizedName, outputPath, e);
         LOGGER.error(failure.englishMessage(), e);
         return failure;
      }
   }

   public static boolean packScenesAndStructures(String name, String version, String author) {
      return packScenesAndStructuresDetailed(name, version, author).isSuccess();
   }

   public static SceneStore.PackExportResult packSelectedScenesAndStructuresDetailed(String name, String version, String author, Set<String> selectedSceneIds) {
      if (selectedSceneIds != null && !selectedSceneIds.isEmpty()) {
         SceneStore.PackExportResult nameFailure = validatePackExportName(name);
         if (nameFailure != null) {
            LOGGER.warn(nameFailure.englishMessage());
            return nameFailure;
         }

         String normalizedName = name.trim();
         Path resourcepacksDir = PondererServices.PLATFORM.getGameDir().resolve("resourcepacks");
         Path outputPath = resolvePackExportOutputPath(resourcepacksDir, normalizedName);
         if (outputPath == null) {
            SceneStore.PackExportResult failure = SceneStore.PackExportResult.failure(
               "Cannot export pack '" + normalizedName + "' because the output path could not be resolved safely", "ponderer.ui.save_error.pack_name_invalid"
            );
            LOGGER.warn(failure.englishMessage());
            return failure;
         }

         try {
            Files.createDirectories(resourcepacksDir);
            String filename = outputPath.getFileName() != null ? outputPath.getFileName().toString() : "[Ponderer] " + normalizedName + ".zip";
            String packJson = createPackMetadata(normalizedName, version, author);
            Set<String> requiredStructures = new HashSet<>();
            List<Path> exportedFiles = new ArrayList<>();

            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(outputPath), StandardCharsets.UTF_8)) {
               writeZipEntry(zos, "pack.mcmeta", "{\"pack\": {\"pack_format\": 15, \"description\": \"Ponderer scene collection\"}}");
               writeZipEntry(zos, "pack.json", packJson);
               int count = 0;
               Set<String> usedEntryNames = new HashSet<>();

               for (Path p : collectAllScriptFiles()) {
                  try {
                     String rawJson = Files.readString(p, StandardCharsets.UTF_8);
                     DslScene scene = (DslScene)GSON.fromJson(rawJson, DslScene.class);
                     if (scene != null && scene.id != null) {
                        boolean isSelected = selectedSceneIds.contains(scene.id) || selectedSceneIds.contains(scene.sceneKey());
                        if (!isSelected) {
                           String fileBase = p.getFileName().toString();
                           if (fileBase.endsWith(".json")) {
                              fileBase = fileBase.substring(0, fileBase.length() - 5);
                           }

                           String stripped = stripPackPrefix(fileBase);
                           isSelected = selectedSceneIds.stream().anyMatch(id -> id.equals(stripped) || id.endsWith(":" + stripped));
                        }

                        if (isSelected) {
                           scene.pack = normalizedName;
                           String cleanJson = GSON_PRETTY.toJson(scene);
                           collectStructureReferences(cleanJson, requiredStructures);
                           String cleanFilename = stripPackPrefix(p.getFileName().toString());
                           cleanFilename = deduplicateFilename(cleanFilename, usedEntryNames);
                           String entryName = "data/ponderer/scripts/" + cleanFilename;
                           zos.putNextEntry(new ZipEntry(entryName));
                           zos.write(cleanJson.getBytes(StandardCharsets.UTF_8));
                           zos.closeEntry();
                           exportedFiles.add(p);
                           count++;
                        }
                     }
                  } catch (Exception e) {
                     LOGGER.warn("Failed to process scene file: {}", p, e);
                  }
               }

               count += writeStructuresToZip(zos, requiredStructures);
               LOGGER.info("Packed {} files into {} (selected {} scenes)", new Object[]{count, filename, selectedSceneIds.size()});
            }

            return SceneStore.PackExportResult.success(outputPath, "Exported pack '" + normalizedName + "' to " + outputPath);
         } catch (IOException e) {
            SceneStore.PackExportResult failure = mapPackExportIoFailure(normalizedName, outputPath, e);
            LOGGER.error(failure.englishMessage(), e);
            return failure;
         }
      } else {
         return packScenesAndStructuresDetailed(name, version, author);
      }
   }

   public static boolean packSelectedScenesAndStructures(String name, String version, String author, Set<String> selectedSceneIds) {
      return packSelectedScenesAndStructuresDetailed(name, version, author, selectedSceneIds).isSuccess();
   }

   @Nullable
   private static SceneStore.PackExportResult validatePackExportName(String name) {
      if (name != null && !name.isBlank()) {
         SafePaths.FileNameValidationError error = SafePaths.diagnosePortableAssetName(name);
         if (error == null) {
            return null;
         }

         return switch (error.code()) {
            case INVALID_CHARACTER -> SceneStore.PackExportResult.failure(
               "Cannot export pack '" + name + "' because the pack name contains invalid character '" + error.offendingText() + "'",
               "ponderer.ui.save_error.pack_name_invalid_char",
               error.offendingText()
            );
            case RESERVED_NAME -> SceneStore.PackExportResult.failure(
               "Cannot export pack '" + name + "' because the pack name is a reserved Windows name", "ponderer.ui.save_error.pack_name_reserved", name
            );
            case TRAILING_SPACE_OR_DOT -> SceneStore.PackExportResult.failure(
               "Cannot export pack '" + name + "' because the pack name ends with a space or dot", "ponderer.ui.save_error.pack_name_trailing"
            );
            case EMPTY, DOT_SEGMENT -> SceneStore.PackExportResult.failure(
               "Cannot export pack '" + name + "' because the pack name is invalid", "ponderer.ui.save_error.pack_name_invalid"
            );
         };
      } else {
         return SceneStore.PackExportResult.failure("Cannot export pack because the pack name is blank", "ponderer.ui.export.name_empty");
      }
   }

   @Nullable
   private static Path resolvePackExportOutputPath(Path resourcepacksDir, String packName) {
      String filename = "[Ponderer] " + packName + ".zip";
      return SafePaths.resolveFileName(resourcepacksDir, filename);
   }

   private static SceneStore.PackExportResult mapPackExportIoFailure(String packName, @Nullable Path outputPath, IOException e) {
      String target = outputPath == null ? packName : outputPath.toString();
      if (e instanceof AccessDeniedException) {
         return SceneStore.PackExportResult.failure(
            "Failed to export pack '" + packName + "' to " + target + ": access denied",
            "ponderer.ui.save_error.access_denied",
            outputPath != null && outputPath.getFileName() != null ? outputPath.getFileName().toString() : target
         );
      }

      if (e instanceof NoSuchFileException) {
         return SceneStore.PackExportResult.failure(
            "Failed to export pack '" + packName + "' to " + target + ": target path does not exist", "ponderer.ui.save_error.path_missing", target
         );
      }

      String detail = "I/O error";
      if (e instanceof FileSystemException fileSystemException && fileSystemException.getReason() != null && !fileSystemException.getReason().isBlank()) {
         detail = "filesystem error: " + fileSystemException.getReason();
      } else if (e.getMessage() != null && !e.getMessage().isBlank()) {
         detail = "I/O error: " + e.getMessage();
      }

      return SceneStore.PackExportResult.failure("Failed to export pack '" + packName + "' to " + target + ": " + detail, "ponderer.ui.save_error.io", detail);
   }

   private static List<Path> collectAllScriptFiles() {
      List<Path> result = new ArrayList<>();
      collectFilesWithExtension(getSceneDir(), ".json", result);
      Path packsDir = getPacksRoot();
      if (!Files.exists(packsDir)) {
         return result;
      }

      try (Stream<Path> packDirs = Files.list(packsDir)) {
         for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).toList()) {
            collectFilesWithExtension(getPackContentSearchRoot(packDir, "scripts"), ".json", result);
         }
      } catch (IOException var7) {
      }

      return result;
   }

   private static void collectFilesWithExtension(@Nullable Path root, String extension, List<Path> result) {
      if (root != null && Files.exists(root)) {
         try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(x$0 -> Files.isRegularFile(x$0))
               .filter(path -> !path.startsWith(root.resolve("_packs")))
               .filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(extension))
               .forEach(result::add);
         } catch (IOException var8) {
         }
      }
   }

   @Nullable
   private static String readAndUpdatePackField(Path path, String packName) {
      try {
         String rawJson = Files.readString(path, StandardCharsets.UTF_8);
         DslScene scene = (DslScene)GSON.fromJson(rawJson, DslScene.class);
         if (scene == null) {
            return null;
         }

         scene.pack = packName;
         return GSON_PRETTY.toJson(scene);
      } catch (Exception e) {
         LOGGER.warn("Failed to read/update scene file: {}", path, e);
         return null;
      }
   }

   private static String stripPackPrefix(String filename) {
      String prefix = DslScene.extractPackPrefix(filename);
      return prefix != null ? filename.substring(prefix.length()).trim() : filename;
   }

   private static String deduplicateFilename(String filename, Set<String> usedNames) {
      if (usedNames.add(filename)) {
         return filename;
      }

      String base = filename;
      String ext = "";
      int dot = filename.lastIndexOf(46);
      if (dot >= 0) {
         base = filename.substring(0, dot);
         ext = filename.substring(dot);
      }

      int suffix = 1;

      String candidate;
      do {
         candidate = base + "_" + ++suffix + ext;
      } while (!usedNames.add(candidate));

      return candidate;
   }

   private static int writeStructuresToZip(ZipOutputStream zos, Set<String> structureRefs) throws IOException {
      int count = 0;
      List<Path> allStructureFiles = new ArrayList<>();
      collectFilesWithExtension(getStructureDir(), ".nbt", allStructureFiles);
      Path packsDir = getPacksRoot();
      if (Files.exists(packsDir)) {
         try (Stream<Path> packDirs = Files.list(packsDir)) {
            for (Path packDir : packDirs.filter(x$0 -> Files.isDirectory(x$0)).toList()) {
               collectFilesWithExtension(getPackContentSearchRoot(packDir, "structures"), ".nbt", allStructureFiles);
            }
         } catch (IOException var15) {
         }
      }

      Set<String> writtenEntries = new HashSet<>();

      for (Path p : allStructureFiles) {
         String fileName = p.getFileName().toString();
         String cleanFileName = stripPackPrefix(fileName);
         String baseName = cleanFileName.endsWith(".nbt") ? cleanFileName.substring(0, cleanFileName.length() - 4) : cleanFileName;
         boolean isReferenced = structureRefs.stream().anyMatch(ref -> {
            String cleanRef = ref.replace(".nbt", "");
            return cleanRef.equals(baseName) || cleanRef.endsWith(":" + baseName);
         });
         if (isReferenced) {
            String entryName = "data/ponderer/structures/" + cleanFileName;
            if (writtenEntries.add(entryName)) {
               zos.putNextEntry(new ZipEntry(entryName));
               Files.copy(p, zos);
               zos.closeEntry();
               count++;
            }
         }
      }

      return count;
   }

   public static List<PonderPackInfo> scanAvailableSourcePacks() {
      Path resourcepacksDir = PondererServices.PLATFORM.getGameDir().resolve("resourcepacks");
      if (!Files.exists(resourcepacksDir)) {
         return List.of();
      }

      LinkedHashMap<String, PonderPackInfo> deduped = new LinkedHashMap<>();

      try (Stream<Path> paths = Files.list(resourcepacksDir)) {
         for (PonderPackInfo info : paths.filter(path -> path.toString().toLowerCase(Locale.ROOT).endsWith(".zip"))
            .map(PonderPackInfo::fromZip)
            .filter(Objects::nonNull)
            .sorted(
               Comparator.<PonderPackInfo, String>comparing(infox -> infox.name.toLowerCase(Locale.ROOT))
                  .thenComparing(infox -> infox.sourcePath.getFileName().toString().toLowerCase(Locale.ROOT))
            )
            .toList()) {
            PonderPackInfo existing = deduped.get(info.name);
            if (existing == null || info.lastModified >= existing.lastModified) {
               deduped.put(info.name, info);
            }
         }
      } catch (IOException e) {
         LOGGER.warn("Failed to scan resourcepacks directory", e);
      }

      return deduped.values().stream().sorted(Comparator.comparing(infox -> infox.name.toLowerCase(Locale.ROOT))).toList();
   }

   public static SceneStore.PackImportResult importPackFromResourcePack(Path zipPath) {
      if (zipPath != null && Files.exists(zipPath)) {
         ensureLocalPackLayoutMigrated();
         PackStateStore.load();
         PonderPackInfo info = PonderPackInfo.fromZip(zipPath);
         if (info == null) {
            SceneStore.PackImportResult failure = SceneStore.PackImportResult.failure(
               "Invalid Ponderer pack: " + zipPath, "ponderer.ui.import.failed", "Invalid Ponderer pack"
            );
            LOGGER.warn(failure.englishMessage());
            return failure;
         }

         PackStateStore.ImportedPackState existing = PackStateStore.getImportedPack(info.name);
         if (!PackStateStore.isImported(info.name)) {
            Path packScriptsDir = getPackSceneDir(info.name);
            Path packStructuresDir = getPackStructureDir(info.name);
            if (packScriptsDir != null && packStructuresDir != null) {
               try {
                  int count = extractPackContents(zipPath, info, packScriptsDir, packStructuresDir);
                  PackStateStore.markImported(info);
                  return SceneStore.PackImportResult.success(
                     count, "Imported pack '" + info.name + "' to local editable copy", "ponderer.ui.import.success", count, info.name
                  );
               } catch (IOException e) {
                  SceneStore.PackImportResult failure = SceneStore.PackImportResult.failure(
                     "Failed to import pack '" + info.name + "': " + e.getMessage(), "ponderer.ui.import.failed", e.getMessage()
                  );
                  LOGGER.warn(failure.englishMessage(), e);
                  return failure;
               }
            } else {
               SceneStore.PackImportResult failure = SceneStore.PackImportResult.failure(
                  "Cannot import pack '" + info.name + "' because the local destination path is unsafe",
                  "ponderer.ui.import.failed",
                  "Unsafe local destination"
               );
               LOGGER.warn(failure.englishMessage());
               return failure;
            }
         } else if (existing != null && existing.importedVersion != null && existing.importedVersion.equals(info.version)) {
            return SceneStore.PackImportResult.failure("Pack '" + info.name + "' is already imported", "ponderer.ui.import.already_imported", info.name);
         } else {
            return existing != null && existing.importedVersion != null && !existing.importedVersion.isBlank()
               ? SceneStore.PackImportResult.failure(
                  "Pack '"
                     + info.name
                     + "' has a newer source version v"
                     + info.version
                     + " while the imported local copy remains on v"
                     + existing.importedVersion,
                  "ponderer.ui.import.newer_source",
                  info.name,
                  info.version,
                  existing.importedVersion
               )
               : SceneStore.PackImportResult.failure(
                  "Pack '" + info.name + "' already has an imported local copy", "ponderer.ui.import.already_imported", info.name
               );
         }
      } else {
         SceneStore.PackImportResult failure = SceneStore.PackImportResult.failure(
            "Pack file not found: " + zipPath, "ponderer.ui.import.failed", "Pack file not found"
         );
         LOGGER.warn(failure.englishMessage());
         return failure;
      }
   }

   private static int extractPackContents(Path zipPath, PonderPackInfo info, Path packScriptsDir, Path packStructuresDir) throws IOException {
      int count = 0;
      Files.createDirectories(packScriptsDir);
      Files.createDirectories(packStructuresDir);

      ZipEntry entry;
      try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipPath), StandardCharsets.UTF_8)) {
         while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            if (name.startsWith("data/ponderer/scripts/")) {
               String fileName = name.substring("data/ponderer/scripts/".length());
               if (!fileName.isEmpty() && !fileName.endsWith("/")) {
                  Path targetPath = resolvePackScopedPath(packScriptsDir, info.name, fileName);
                  if (targetPath == null) {
                     LOGGER.warn("Skipping unsafe script zip entry '{}' in {}", fileName, zipPath);
                  } else {
                     Files.createDirectories(targetPath.getParent());
                     String json = new String(zis.readAllBytes(), StandardCharsets.UTF_8);
                     Files.writeString(targetPath, injectPackField(json, info.name), StandardCharsets.UTF_8);
                     count++;
                  }
               }
            } else if (name.startsWith("data/ponderer/structures/")) {
               String fileName = name.substring("data/ponderer/structures/".length());
               if (!fileName.isEmpty() && !fileName.endsWith("/")) {
                  Path targetPath = resolvePackScopedPath(packStructuresDir, info.name, fileName);
                  if (targetPath == null) {
                     LOGGER.warn("Skipping unsafe structure zip entry '{}' in {}", fileName, zipPath);
                  } else {
                     Files.createDirectories(targetPath.getParent());
                     Files.write(targetPath, zis.readAllBytes());
                     count++;
                  }
               }
            }
         }
      }

      return count;
   }

   private static void rebuildReadonlyCacheForPack(Path zipPath, PonderPackInfo info) throws IOException {
      Path packScriptsDir = getReadonlyCachePackSceneDir(info.name);
      Path packStructuresDir = getReadonlyCachePackStructureDir(info.name);
      if (packScriptsDir != null && packStructuresDir != null) {
         extractPackContents(zipPath, info, packScriptsDir, packStructuresDir);
      } else {
         LOGGER.warn("Rejected unsafe readonly cache directories for pack {}", info.name);
      }
   }

   private static void clearReadonlyCache() {
      deleteDirectoryRecursive(getReadonlyCacheRoot());
   }

   private static void deleteDirectoryRecursive(Path dir) {
      if (dir != null && Files.exists(dir)) {
         try (Stream<Path> paths = Files.walk(dir)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
               try {
                  Files.deleteIfExists(path);
               } catch (IOException e) {
                  LOGGER.warn("Failed to delete {}", path, e);
               }
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to delete directory tree {}", dir, e);
         }
      }
   }

   private static void reorganizeFilesForPack(String packName, List<Path> exportedFiles) {
      Path packScriptsDir = getPackSceneDir(packName);
      if (packScriptsDir == null) {
         LOGGER.warn("Rejected unsafe pack script directory for pack {}", packName);
      } else {
         try {
            Files.createDirectories(packScriptsDir);
         } catch (IOException e) {
            LOGGER.warn("Failed to create pack directory: {}", packScriptsDir, e);
            return;
         }

         for (Path sourcePath : exportedFiles) {
            try {
               String rawJson = Files.readString(sourcePath, StandardCharsets.UTF_8);
               DslScene scene = (DslScene)GSON.fromJson(rawJson, DslScene.class);
               if (scene != null) {
                  scene.pack = packName;
                  String updatedJson = GSON_PRETTY.toJson(scene);
                  String cleanFilename = stripPackPrefix(sourcePath.getFileName().toString());
                  String prefixedFilename = "[" + packName + "] " + cleanFilename;
                  Path targetPath = SafePaths.resolveFileName(packScriptsDir, prefixedFilename);
                  if (targetPath == null) {
                     LOGGER.warn("Rejected unsafe pack script filename '{}' for pack {}", prefixedFilename, packName);
                  } else {
                     Files.writeString(targetPath, updatedJson, StandardCharsets.UTF_8);
                     if (!sourcePath.equals(targetPath)) {
                        Files.deleteIfExists(sourcePath);
                     }
                  }
               }
            } catch (Exception e) {
               LOGGER.warn("Failed to reorganize file: {}", sourcePath, e);
            }
         }

         reorganizeStructuresForPack(packName);
         LOGGER.info("Reorganized files for pack: {}", packName);
      }
   }

   private static void reorganizeStructuresForPack(String packName) {
      Path packStructuresDir = getPackStructureDir(packName);
      Path packScriptsDir = getPackSceneDir(packName);
      if (packStructuresDir != null && packScriptsDir != null) {
         Set<String> structureRefs = new HashSet<>();
         if (Files.exists(packScriptsDir)) {
            try (Stream<Path> paths = Files.walk(packScriptsDir)) {
               for (Path p : paths.filter(f -> f.getFileName().toString().endsWith(".json")).toList()) {
                  try {
                     String json = Files.readString(p, StandardCharsets.UTF_8);
                     collectStructureReferences(json, structureRefs);
                  } catch (Exception var16) {
                  }
               }
            } catch (IOException var20) {
            }
         }

         if (!structureRefs.isEmpty()) {
            try {
               Files.createDirectories(packStructuresDir);
            } catch (IOException e) {
               LOGGER.warn("Failed to create pack structure directory: {}", packStructuresDir, e);
               return;
            }

            Path structuresDir = getStructureDir();
            if (Files.exists(structuresDir)) {
               try (Stream<Path> paths = Files.walk(structuresDir)) {
                  for (Path p : paths.filter(f -> Files.isRegularFile(f) && f.getFileName().toString().endsWith(".nbt")).toList()) {
                     String fileName = p.getFileName().toString();
                     String baseName = fileName.endsWith(".nbt") ? fileName.substring(0, fileName.length() - 4) : fileName;
                     boolean isReferenced = structureRefs.stream().anyMatch(ref -> {
                        String cleanRef = ref.replace(".nbt", "");
                        return cleanRef.equals(baseName) || cleanRef.endsWith(":" + baseName);
                     });
                     if (isReferenced) {
                        String prefixedName = "[" + packName + "] " + fileName;
                        Path targetPath = SafePaths.resolveFileName(packStructuresDir, prefixedName);
                        if (targetPath == null) {
                           LOGGER.warn("Rejected unsafe pack structure filename '{}' for pack {}", prefixedName, packName);
                        } else if (!Files.exists(targetPath)) {
                           Files.copy(p, targetPath);
                        }
                     }
                  }
               } catch (IOException var18) {
               }
            }
         }
      } else {
         LOGGER.warn("Rejected unsafe pack directories for pack {}", packName);
      }
   }

   private static void collectStructureReferences(String sceneJson, Set<String> structures) {
      try {
         collectStructureReferences((DslScene)GSON.fromJson(sceneJson, DslScene.class), structures);
      } catch (Exception e) {
         LOGGER.warn("Failed to collect structure references from scene", e);
      }
   }

   static void collectStructureReferences(@Nullable DslScene scene, Set<String> structures) {
      if (scene != null && structures != null) {
         List<String> pool = getStructurePool(scene);

         for (String ref : pool) {
            addStructureReference(ref, structures);
         }

         if (scene.scenes != null) {
            for (DslScene.SceneSegment segment : scene.scenes) {
               if (segment != null && segment.steps != null) {
                  for (DslScene.DslStep step : segment.steps) {
                     if (step != null && step.structure != null && !step.structure.isBlank()) {
                        addStructureReference(resolveStructureReference(pool, step.structure), structures);
                     }
                  }
               }
            }
         }
      }
   }

   private static List<String> getStructurePool(DslScene scene) {
      if (scene.structures != null && !scene.structures.isEmpty()) {
         return scene.structures;
      } else {
         return scene.structure != null && !scene.structure.isBlank() ? List.of(scene.structure) : List.of();
      }
   }

   @Nullable
   private static String resolveStructureReference(List<String> pool, String ref) {
      if (ref != null && !ref.isBlank()) {
         String trimmed = ref.trim();
         Integer parsed = tryParseInt(trimmed);
         if (parsed == null) {
            return trimmed;
         }

         int index = -1;
         if (parsed >= 1 && parsed <= pool.size()) {
            index = parsed - 1;
         } else if (parsed >= 0 && parsed < pool.size()) {
            index = parsed;
         }

         return index >= 0 ? pool.get(index) : null;
      } else {
         return null;
      }
   }

   private static void addStructureReference(@Nullable String ref, Set<String> structures) {
      if (ref != null && !ref.isBlank()) {
         String trimmed = ref.trim();
         if (!trimmed.matches("\\d+")) {
            structures.add(trimmed);
         }
      }
   }

   @Nullable
   private static Integer tryParseInt(String raw) {
      try {
         return Integer.parseInt(raw);
      } catch (NumberFormatException ignored) {
         return null;
      }
   }

   private static String createPackMetadata(String name, String version, String author) {
      StringBuilder sb = new StringBuilder();
      sb.append("{\n");
      sb.append("  \"pack\": {\n");
      sb.append("    \"pack_format\": 15,\n");
      sb.append("    \"description\": \"Ponderer scene collection\"\n");
      sb.append("  },\n");
      sb.append("  \"ponderer\": {\n");
      sb.append("    \"name\": \"").append(escapeJson(name)).append("\",\n");
      sb.append("    \"version\": \"").append(escapeJson(version)).append("\",\n");
      sb.append("    \"author\": \"").append(escapeJson(author)).append("\",\n");
      sb.append("    \"description\": \"Ponderer scene pack\"\n");
      sb.append("  }\n");
      sb.append("}\n");
      return sb.toString();
   }

   private static void updateRegistryAfterExport(Path zipPath, String name, String version, String author) {
      try {
         PonderPackInfo info = PonderPackInfo.fromZip(zipPath);
         if (info == null) {
            return;
         }

         String displayName = PonderPackRegistry.getDisplayName(name);
         String fileHash = computeSha256(zipPath);
         PonderPackRegistry.addOrUpdatePack(displayName, info, fileHash);
         LOGGER.info("Updated registry after export: {} v{}", displayName, version);
      } catch (Exception e) {
         LOGGER.warn("Failed to update registry after export", e);
      }
   }

   private static String escapeJson(String str) {
      return str == null ? "" : str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
   }

   private static void writeZipEntry(ZipOutputStream zos, String name, String content) throws IOException {
      zos.putNextEntry(new ZipEntry(name));
      zos.write(content.getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
   }

   @Nullable
   public static SceneStore.PackUpdateInfo loadPonderPackFromResourcePack(Path zipPath, boolean forceOverwrite) throws IOException {
      if (!Files.exists(zipPath)) {
         LOGGER.warn("Pack file not found: {}", zipPath);
         return null;
      }

      PonderPackInfo info = PonderPackInfo.fromZip(zipPath);
      if (info == null) {
         LOGGER.warn("Invalid Ponderer pack: {}", zipPath);
         return null;
      }

      String displayName = PonderPackRegistry.getDisplayName(info.name);
      String newFileHash = computeSha256(zipPath);
      String oldVersion = null;
      long loadedAtMillis = 0L;
      PonderPackRegistry.PackEntry existing = PonderPackRegistry.getPack(displayName);
      if (existing != null) {
         oldVersion = existing.version;
         loadedAtMillis = parseLoadedAtMillis(existing.loadedAt);
         if (!forceOverwrite && existing.version.equals(info.version)) {
            LOGGER.info("Pack {} already loaded (v{}), skipping", info.name, existing.version);
            return null;
         }

         LOGGER.info("Pack {} updating: v{} -> v{}", new Object[]{info.name, existing.version, info.version});
      }

      int[] result = extractPonderPackWithBackup(zipPath, info, loadedAtMillis);
      int count = result[0];
      int conflicts = result[1];
      PonderPackRegistry.addOrUpdatePack(displayName, info, newFileHash);
      LOGGER.info("Loaded Ponderer pack: {} ({} files, {} conflicts)", new Object[]{info.name, count, conflicts});
      return new SceneStore.PackUpdateInfo(info.name, oldVersion, info.version, count, conflicts);
   }

   private static long parseLoadedAtMillis(@Nullable String loadedAt) {
      if (loadedAt != null && !loadedAt.isEmpty()) {
         try {
            LocalDateTime ldt = LocalDateTime.parse(loadedAt, DateTimeFormatter.ISO_DATE_TIME);
            return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
         } catch (Exception e) {
            LOGGER.warn("Failed to parse loadedAt timestamp: {}", loadedAt, e);
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   private static int[] extractPonderPackWithBackup(Path zipPath, PonderPackInfo info, long loadedAtMillis) throws IOException {
      int count = 0;
      int conflicts = 0;
      Path packScriptsDir = getPackSceneDir(info.name);
      Path packStructuresDir = getPackStructureDir(info.name);
      if (packScriptsDir != null && packStructuresDir != null) {
         ZipEntry entry;
         try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipPath), StandardCharsets.UTF_8)) {
            while ((entry = zis.getNextEntry()) != null) {
               String name = entry.getName();
               if (name.startsWith("data/ponderer/scripts/")) {
                  String fileName = name.substring("data/ponderer/scripts/".length());
                  if (!fileName.isEmpty() && !fileName.endsWith("/")) {
                     Path targetPath = resolvePackScopedPath(packScriptsDir, info.name, fileName);
                     if (targetPath == null) {
                        LOGGER.warn("Skipping unsafe script zip entry '{}' in {}", fileName, zipPath);
                     } else {
                        Files.createDirectories(targetPath.getParent());
                        if (backupIfModified(targetPath, loadedAtMillis)) {
                           conflicts++;
                        }

                        byte[] rawBytes = zis.readAllBytes();
                        String json = new String(rawBytes, StandardCharsets.UTF_8);
                        json = injectPackField(json, info.name);
                        Files.writeString(targetPath, json, StandardCharsets.UTF_8);
                        count++;
                     }
                  }
               } else if (name.startsWith("data/ponderer/structures/")) {
                  String fileName = name.substring("data/ponderer/structures/".length());
                  if (!fileName.isEmpty() && !fileName.endsWith("/")) {
                     Path targetPath = resolvePackScopedPath(packStructuresDir, info.name, fileName);
                     if (targetPath == null) {
                        LOGGER.warn("Skipping unsafe structure zip entry '{}' in {}", fileName, zipPath);
                     } else {
                        Files.createDirectories(targetPath.getParent());
                        if (backupIfModified(targetPath, loadedAtMillis)) {
                           conflicts++;
                        }

                        Files.write(targetPath, zis.readAllBytes());
                        count++;
                     }
                  }
               }
            }
         }

         return new int[]{count, conflicts};
      } else {
         LOGGER.warn("Rejected unsafe extraction directories for pack {}", info.name);
         return new int[]{0, 0};
      }
   }

   private static String injectPackField(String json, String packName) {
      try {
         DslScene scene = (DslScene)GSON.fromJson(json, DslScene.class);
         if (scene != null) {
            scene.pack = packName;
            return GSON_PRETTY.toJson(scene);
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to inject pack field, writing raw content", e);
      }

      return json;
   }

   private static boolean backupIfModified(Path targetPath, long loadedAtMillis) {
      if (!Files.exists(targetPath)) {
         return false;
      }

      try {
         long fileModified = Files.getLastModifiedTime(targetPath).toMillis();
         if (loadedAtMillis > 0L && fileModified <= loadedAtMillis + 5000L) {
            return false;
         } else {
            Path bakPath = SafePaths.resolveFileName(targetPath.getParent(), targetPath.getFileName().toString() + ".bak");
            if (bakPath == null) {
               LOGGER.warn("Rejected unsafe backup path for {}", targetPath);
               return false;
            } else {
               Files.copy(targetPath, bakPath, StandardCopyOption.REPLACE_EXISTING);
               LOGGER.info("Backed up user-modified file: {} -> {}", targetPath.getFileName(), bakPath.getFileName());
               return true;
            }
         }
      } catch (IOException e) {
         LOGGER.warn("Failed to backup file: {}", targetPath, e);
         return false;
      }
   }

   private static boolean packScriptFilesExist(String packName) {
      Path packDir = getPackSceneDir(packName);
      if (packDir == null) {
         return false;
      }

      if (Files.exists(packDir)) {
         try (Stream<Path> paths = Files.walk(packDir)) {
            if (paths.anyMatch(path -> Files.isRegularFile(path) && path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json"))) {
               return true;
            }
         } catch (IOException var7) {
         }
      }

      return false;
   }

   private static String computeSha256(Path file) {
      try {
         byte[] data = Files.readAllBytes(file);
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(data);
         StringBuilder sb = new StringBuilder();

         for (byte b : hash) {
            sb.append(String.format("%02x", b));
         }

         return sb.toString();
      } catch (Exception e) {
         LOGGER.warn("Failed to compute SHA-256 for {}", file, e);
         return "";
      }
   }

   public static SceneStore.AutoLoadResult autoLoadPonderPacks() {
      ensureLocalPackLayoutMigrated();
      PackStateStore.load();
      clearReadonlyCache();
      List<SceneStore.PackUpdateInfo> updates = new ArrayList<>();
      Set<String> presentSourcePacks = new HashSet<>();

      for (PonderPackInfo info : scanAvailableSourcePacks()) {
         presentSourcePacks.add(info.name);
         PackStateStore.updateCurrentSource(info);
         if (PackStateStore.isImported(info.name)) {
            PackStateStore.ImportedPackState imported = PackStateStore.getImportedPack(info.name);
            if (imported != null && PackStateStore.shouldNotifySourceUpdate(info.name, info.version)) {
               updates.add(new SceneStore.PackUpdateInfo(info.name, imported.importedVersion, info.version, 0, 0));
               PackStateStore.markNotified(info.name, info.version);
            }
         } else {
            try {
               rebuildReadonlyCacheForPack(info.sourcePath, info);
            } catch (IOException e) {
               LOGGER.warn("Failed to build readonly cache for pack {}", info.sourcePath, e);
            }
         }
      }

      PackStateStore.clearMissingSources(presentSourcePacks);
      return new SceneStore.AutoLoadResult(List.of(), updates);
   }

   public static void removeOrphanedPacks(List<String> displayNames) {
      for (String name : displayNames) {
         PackStateStore.removeImportedPack(name);
         LOGGER.info("Removed imported pack state: {}", name);
      }
   }

   public static class AutoLoadResult {
      public final List<String> orphanedPacks;
      public final List<SceneStore.PackUpdateInfo> updatedPacks;

      public AutoLoadResult(List<String> orphanedPacks, List<SceneStore.PackUpdateInfo> updatedPacks) {
         this.orphanedPacks = orphanedPacks;
         this.updatedPacks = updatedPacks;
      }
   }

   public static final class LocalSaveResult {
      private final boolean success;
      @Nullable
      private final Path path;
      private final String englishMessage;
      @Nullable
      private final String uiMessageKey;
      private final Object[] uiMessageArgs;

      private LocalSaveResult(boolean success, @Nullable Path path, String englishMessage, @Nullable String uiMessageKey, Object[] uiMessageArgs) {
         this.success = success;
         this.path = path;
         this.englishMessage = englishMessage;
         this.uiMessageKey = uiMessageKey;
         this.uiMessageArgs = uiMessageArgs == null ? new Object[0] : Arrays.copyOf(uiMessageArgs, uiMessageArgs.length);
      }

      public boolean isSuccess() {
         return this.success;
      }

      @Nullable
      public Path path() {
         return this.path;
      }

      public String englishMessage() {
         return this.englishMessage;
      }

      @Nullable
      public String uiMessageKey() {
         return this.uiMessageKey;
      }

      public Object[] uiMessageArgs() {
         return Arrays.copyOf(this.uiMessageArgs, this.uiMessageArgs.length);
      }

      private static SceneStore.LocalSaveResult success(Path path, String englishMessage) {
         return new SceneStore.LocalSaveResult(true, path, englishMessage, null, new Object[0]);
      }

      private static SceneStore.LocalSaveResult failure(String englishMessage, String uiMessageKey, Object... uiMessageArgs) {
         return new SceneStore.LocalSaveResult(false, null, englishMessage, uiMessageKey, uiMessageArgs);
      }
   }

   public static final class PackExportResult {
      private final boolean success;
      @Nullable
      private final Path path;
      private final String englishMessage;
      @Nullable
      private final String uiMessageKey;
      private final Object[] uiMessageArgs;

      private PackExportResult(boolean success, @Nullable Path path, String englishMessage, @Nullable String uiMessageKey, Object[] uiMessageArgs) {
         this.success = success;
         this.path = path;
         this.englishMessage = englishMessage;
         this.uiMessageKey = uiMessageKey;
         this.uiMessageArgs = uiMessageArgs == null ? new Object[0] : Arrays.copyOf(uiMessageArgs, uiMessageArgs.length);
      }

      public boolean isSuccess() {
         return this.success;
      }

      @Nullable
      public Path path() {
         return this.path;
      }

      public String englishMessage() {
         return this.englishMessage;
      }

      @Nullable
      public String uiMessageKey() {
         return this.uiMessageKey;
      }

      public Object[] uiMessageArgs() {
         return Arrays.copyOf(this.uiMessageArgs, this.uiMessageArgs.length);
      }

      private static SceneStore.PackExportResult success(Path path, String englishMessage) {
         return new SceneStore.PackExportResult(true, path, englishMessage, null, new Object[0]);
      }

      private static SceneStore.PackExportResult failure(String englishMessage, String uiMessageKey, Object... uiMessageArgs) {
         return new SceneStore.PackExportResult(false, null, englishMessage, uiMessageKey, uiMessageArgs);
      }
   }

   public static final class PackImportResult {
      private final boolean success;
      private final int fileCount;
      private final String englishMessage;
      @Nullable
      private final String uiMessageKey;
      private final Object[] uiMessageArgs;

      private PackImportResult(boolean success, int fileCount, String englishMessage, @Nullable String uiMessageKey, Object[] uiMessageArgs) {
         this.success = success;
         this.fileCount = fileCount;
         this.englishMessage = englishMessage;
         this.uiMessageKey = uiMessageKey;
         this.uiMessageArgs = uiMessageArgs == null ? new Object[0] : Arrays.copyOf(uiMessageArgs, uiMessageArgs.length);
      }

      public boolean isSuccess() {
         return this.success;
      }

      public int fileCount() {
         return this.fileCount;
      }

      public String englishMessage() {
         return this.englishMessage;
      }

      @Nullable
      public String uiMessageKey() {
         return this.uiMessageKey;
      }

      public Object[] uiMessageArgs() {
         return Arrays.copyOf(this.uiMessageArgs, this.uiMessageArgs.length);
      }

      private static SceneStore.PackImportResult success(int fileCount, String englishMessage, String uiMessageKey, Object... uiMessageArgs) {
         return new SceneStore.PackImportResult(true, fileCount, englishMessage, uiMessageKey, uiMessageArgs);
      }

      private static SceneStore.PackImportResult failure(String englishMessage, String uiMessageKey, Object... uiMessageArgs) {
         return new SceneStore.PackImportResult(false, 0, englishMessage, uiMessageKey, uiMessageArgs);
      }
   }

   public static class PackUpdateInfo {
      public final String packName;
      public final String oldVersion;
      public final String newVersion;
      public final int totalFiles;
      public final int conflictCount;

      public PackUpdateInfo(String packName, String oldVersion, String newVersion, int totalFiles, int conflictCount) {
         this.packName = packName;
         this.oldVersion = oldVersion;
         this.newVersion = newVersion;
         this.totalFiles = totalFiles;
         this.conflictCount = conflictCount;
      }
   }

   public record SyncFileRef(String id, @Nullable String pack, Path path) {
   }
}

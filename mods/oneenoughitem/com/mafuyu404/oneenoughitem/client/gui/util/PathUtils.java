package com.mafuyu404.oneenoughitem.client.gui.util;

import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.api.DomainAdapter;
import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;

public class PathUtils {
   private static final String FOLDER_DATAPACKS = "datapacks";
   private static final String DEFAULT_DATA_PACK_NAME = "OEIBF";
   private static final String SAVES_FOLDER = "saves";

   public static Path getDatapackPath(String datapackName) {
      Minecraft minecraft = Minecraft.getInstance();
      Path datapacksPath = getDatapacksPath();
      String finalDatapackName = "OEIBF";
      if (datapackName != null && !datapackName.trim().isEmpty()) {
         finalDatapackName = datapackName.trim();
      }

      return datapacksPath.resolve(finalDatapackName);
   }

   public static Path getReplacementsPath() {
      return getDatapackPath(null).resolve(getReplacementsSubPath());
   }

   private static String getReplacementsSubPath() {
      String domainId;
      try {
         DomainAdapter current = DomainRegistry.current();
         domainId = current != null ? current.id().toLowerCase() : "oei";
      } catch (Throwable t) {
         domainId = "oei";
      }

      return "data/" + domainId + "/replacements";
   }

   public static Path getDatapacksPath() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getSingleplayerServer() != null) {
         Path worldPath = minecraft.getSingleplayerServer().getWorldPath(LevelResource.ROOT);
         return worldPath.resolve("datapacks");
      }

      if (minecraft.level != null) {
         try {
            Path savesPath = minecraft.gameDirectory.toPath().resolve("saves");
            Path worldPath = findCurrentWorldPath(savesPath);
            if (worldPath != null) {
               return worldPath.resolve("datapacks");
            }
         } catch (Exception e) {
            Oneenoughitem.LOGGER.warn("Failed to determine world path", e);
         }
      }

      Path savesPath = minecraft.gameDirectory.toPath().resolve("saves");
      return savesPath.resolve("datapacks");
   }

   public static String getReplacementsSubPath(String domainId) {
      String id = domainId != null && !domainId.isEmpty() ? domainId.toLowerCase() : "oei";
      return "data/" + id + "/replacements";
   }

   public static List<PathUtils.FileInfo> scanAllReplacementFiles() {
      return scanAllReplacementFiles(DomainRegistry.currentId());
   }

   public static List<PathUtils.FileInfo> scanAllReplacementFiles(String domainId) {
      List<PathUtils.FileInfo> jsonFiles = new ArrayList<>();
      Path datapacksPath = getDatapacksPath();
      if (!Files.exists(datapacksPath)) {
         Oneenoughitem.LOGGER.warn("Datapacks directory does not exist: {}", datapacksPath);
         return jsonFiles;
      } else {
         String replacementsSubPath = getReplacementsSubPath(domainId);
         scanReplacementFilesInPath(datapacksPath, replacementsSubPath, jsonFiles);
         return jsonFiles;
      }
   }

   private static void scanReplacementFilesInPath(Path datapacksPath, String replacementsSubPath, List<PathUtils.FileInfo> jsonFiles) {
      try (Stream<Path> datapackDirs = Files.list(datapacksPath)) {
         datapackDirs.filter(x$0 -> Files.isDirectory(x$0)).forEach(datapackDir -> {
            Path replacementsPath = datapackDir.resolve(replacementsSubPath);
            if (Files.exists(replacementsPath)) {
               scanJsonFilesInDirectory(replacementsPath, datapackDir, jsonFiles);
            }
         });
      } catch (IOException e) {
         Oneenoughitem.LOGGER.error("Failed to list datapack directories", e);
      }
   }

   private static void scanJsonFilesInDirectory(Path replacementsPath, Path datapackRoot, List<PathUtils.FileInfo> jsonFiles) {
      try (Stream<Path> paths = Files.walk(replacementsPath)) {
         paths.filter(x$0 -> Files.isRegularFile(x$0)).filter(path -> path.toString().toLowerCase().endsWith(".json")).forEach(path -> {
            String fileName = path.getFileName().toString();
            String displayName = fileName.endsWith(".json") ? fileName.substring(0, fileName.length() - 5) : fileName;
            String datapackName = datapackRoot.getFileName().toString();
            Path relativeToReplacements = replacementsPath.relativize(path.getParent());
            String relativePathStr = relativeToReplacements.toString().equals(".") ? "" : relativeToReplacements.toString();
            String fullPath = String.join("/", "datapacks", datapackName, getReplacementsSubPath(), relativePathStr).replaceAll("\\\\", "/");
            jsonFiles.add(new PathUtils.FileInfo(displayName, path, fullPath, datapackName));
         });
      } catch (IOException e) {
         Oneenoughitem.LOGGER.error("Failed to scan files in directory: {}", replacementsPath, e);
      }
   }

   public static Path findCurrentWorldPath(Path savesPath) {
      try {
         if (!Files.exists(savesPath)) {
            return null;
         }

         try (Stream<Path> stream = Files.list(savesPath)) {
            return stream.filter(x$0 -> Files.isDirectory(x$0)).filter(path -> Files.exists(path.resolve("level.dat"))).max((p1, p2) -> {
               try {
                  return Files.getLastModifiedTime(p1).compareTo(Files.getLastModifiedTime(p2));
               } catch (IOException e) {
                  return 0;
               }
            }).orElse(null);
         }
      } catch (IOException e) {
         Oneenoughitem.LOGGER.error("Error finding current world path", e);
         return null;
      }
   }

   public record FileInfo(String displayName, Path filePath, String fullPath, String datapackName) {
   }
}

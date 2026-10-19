package de.melanx.simplebackups;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.melanx.simplebackups.config.ExperimentalConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public class BackupChain {
   public static final String METADATA_FILE = "metadata.json";
   private final Path parentFolder;
   private final Path fullBackup;
   private final List<Path> children;
   private final ExperimentalConfig.ExperimentalBackupType experimentalBackupType;
   private long lastUpdated;

   public BackupChain(Path parentFolder, Path fullBackup, ExperimentalConfig.ExperimentalBackupType experimentalBackupType) {
      this(parentFolder, fullBackup, new ArrayList<>(), experimentalBackupType, System.currentTimeMillis());
   }

   public BackupChain(
      Path parentFolder, Path fullBackup, List<Path> children, ExperimentalConfig.ExperimentalBackupType experimentalBackupType, long lastUpdated
   ) {
      this.parentFolder = parentFolder;
      this.fullBackup = fullBackup;
      this.children = children;
      this.experimentalBackupType = experimentalBackupType;
      this.lastUpdated = lastUpdated;
   }

   public Path getParentFolder() {
      return this.parentFolder;
   }

   public Path getFullBackup() {
      return this.parentFolder.resolve(this.fullBackup);
   }

   public List<Path> getChildren() {
      return this.children.stream().map(this.parentFolder::resolve).toList();
   }

   public ExperimentalConfig.ExperimentalBackupType getBackupType() {
      return this.experimentalBackupType;
   }

   public void addChild(Path child) {
      this.children.add(child);
   }

   public Path createChild() {
      int index = this.children.size() + 1;
      Path child = Path.of("child-" + String.format("%04d", index) + ".zip");
      this.children.add(child);
      this.lastUpdated = System.currentTimeMillis();
      this.writeMetadata();
      return this.parentFolder.resolve(child);
   }

   public long getLastUpdated() {
      return this.lastUpdated;
   }

   public long getFileSize() {
      try {
         return Files.walk(this.parentFolder).filter(x$0 -> Files.isRegularFile(x$0)).mapToLong(path -> {
            try {
               return Files.size(path);
            } catch (IOException e) {
               SimpleBackups.LOGGER.warn("Failed to get size of {}", path, e);
               return 0L;
            }
         }).sum();
      } catch (IOException e) {
         SimpleBackups.LOGGER.warn("Failed to get size of {}", this.parentFolder, e);
         return 0L;
      }
   }

   public void deleteFiles() {
      if (Files.exists(this.parentFolder)) {
         try {
            Files.walk(this.parentFolder).sorted(Comparator.reverseOrder()).forEach(path -> {
               try {
                  Files.deleteIfExists(path);
               } catch (IOException e) {
                  SimpleBackups.LOGGER.warn("Failed to delete \"{}\"", path, e);
               }
            });
            Files.deleteIfExists(this.parentFolder);
         } catch (IOException e) {
            SimpleBackups.LOGGER.warn("Failed to delete directory tree \"{}\"", this.parentFolder, e);
         }
      }
   }

   public int getSize() {
      return this.children.size() + 1;
   }

   public void writeMetadata() {
      Path meta = this.parentFolder.resolve("metadata.json");
      JsonObject json = new JsonObject();
      json.addProperty("backupType", this.getBackupType().name());
      json.addProperty("fullBackup", this.fullBackup.toString());
      JsonArray children = new JsonArray();

      for (Path child : this.children) {
         children.add(child.toString());
      }

      json.add("children", children);
      json.addProperty("lastUpdated", this.lastUpdated);

      try {
         Files.writeString(meta, json.toString());
      } catch (IOException e) {
         SimpleBackups.LOGGER.warn("Failed to write metadata to {}", meta, e);
      }
   }

   @Nullable
   public static BackupChain readMetadata(Path chainDir) {
      Path meta = chainDir.resolve("metadata.json");

      try {
         JsonObject json = (JsonObject)new Gson().fromJson(Files.newBufferedReader(meta), JsonObject.class);
         Path fullBackup = Path.of(json.get("fullBackup").getAsString());
         List<Path> children = new ArrayList<>();

         for (JsonElement child : json.getAsJsonArray("children")) {
            children.add(Path.of(child.getAsString()));
         }

         ExperimentalConfig.ExperimentalBackupType backupType = ExperimentalConfig.ExperimentalBackupType.valueOf(json.get("backupType").getAsString());
         long lastUpdated = json.get("lastUpdated").getAsLong();
         return new BackupChain(chainDir, fullBackup, children, backupType, lastUpdated);
      } catch (IOException e) {
         SimpleBackups.LOGGER.warn("Failed to read metadata from {}", meta, e);
         return null;
      }
   }
}

package com.nododiiiii.ponderer.ponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public final class SyncMeta {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final String META_FILE = ".sync_hashes.json";

   private SyncMeta() {
   }

   private static Path getMetaPath() {
      return SceneStore.getSceneDir().getParent().resolve(".sync_hashes.json");
   }

   public static Map<String, String> load() {
      Path path = getMetaPath();
      if (!Files.exists(path)) {
         return new HashMap<>();
      }

      try (Reader r = Files.newBufferedReader(path)) {
         Map<String, String> map = (Map<String, String>)GSON.fromJson(r, (new TypeToken<Map<String, String>>() {}).getType());
         return map == null ? new HashMap<>() : new HashMap<>(map);
      } catch (Exception e) {
         LOGGER.warn("Failed to read sync meta: {}", path, e);
         return new HashMap<>();
      }
   }

   public static void save(Map<String, String> hashes) {
      Path path = getMetaPath();

      try {
         Files.createDirectories(path.getParent());

         try (Writer w = Files.newBufferedWriter(path)) {
            GSON.toJson(hashes, w);
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to write sync meta: {}", path, e);
      }
   }

   public static void recordHash(String key, byte[] content) {
      Map<String, String> meta = load();
      meta.put(key, sha256(content));
      save(meta);
   }

   public static String metaKey(String category, String id, @Nullable String pack) {
      return pack != null && !pack.isBlank() ? category + "/[" + pack + "] " + id : category + "/" + id;
   }

   public static void recordHashes(Map<String, byte[]> entries) {
      Map<String, String> meta = load();

      for (Entry<String, byte[]> e : entries.entrySet()) {
         meta.put(e.getKey(), sha256(e.getValue()));
      }

      save(meta);
   }

   public static String sha256(byte[] data) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         byte[] hash = md.digest(data);
         return HexFormat.of().formatHex(hash);
      } catch (Exception e) {
         return "";
      }
   }

   public static String hashLocalFile(Path file) {
      if (!Files.exists(file)) {
         return "";
      }

      try {
         return sha256(Files.readAllBytes(file));
      } catch (Exception e) {
         return "";
      }
   }

   public static String checkConflict(String metaKey, byte[] serverContent, Path localFile) {
      Map<String, String> meta = load();
      String lastSyncHash = meta.getOrDefault(metaKey, "");
      String serverHash = sha256(serverContent);
      String localHash = hashLocalFile(localFile);
      if (localHash.isEmpty()) {
         return "none";
      } else {
         boolean localChanged = !localHash.equals(lastSyncHash);
         boolean serverChanged = !serverHash.equals(lastSyncHash);
         if (localChanged && serverChanged) {
            return localHash.equals(serverHash) ? "none" : "both_modified";
         } else {
            return localChanged ? "local_modified" : "none";
         }
      }
   }
}

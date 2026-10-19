package com.mafuyu404.oneenoughitem.client.gui.cache;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.client.gui.util.PathUtils;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractGlobalReplacementCache extends BaseCache {
   protected final Map<String, String> replacedData = new ConcurrentHashMap<>();
   protected final Map<String, String> replacedTags = new ConcurrentHashMap<>();
   protected final Map<String, String> resultData = new ConcurrentHashMap<>();
   protected final Map<String, String> resultTags = new ConcurrentHashMap<>();
   protected volatile boolean needsRebuild = false;

   protected AbstractGlobalReplacementCache(String dirName, String domainId) {
      super(dirName, domainId + "_global_replacement_cache.dat", 2);
   }

   protected abstract String domainId();

   @Override
   protected void onInitialized() {
      Oneenoughitem.LOGGER
         .info("{} global replacement cache initialized (data: {}, tags: {})", this.domainId(), this.replacedData.size(), this.replacedTags.size());
      if (this.needsRebuild) {
         this.needsRebuild = false;
         this.rebuild();
      }
   }

   @Override
   protected void onVersionMismatch(int foundVersion) {
      Oneenoughitem.LOGGER.warn("{} cache version mismatch (found {} != expected {}), scheduling rebuild", this.domainId(), foundVersion, this.cacheVersion);
      this.needsRebuild = true;
   }

   @Override
   protected void onLoadError(IOException e) {
      this.clearAllMaps();
   }

   @Override
   protected void loadData(DataInputStream dis) throws IOException {
      readStringMap(dis, this.replacedData);
      readStringMap(dis, this.replacedTags);

      try {
         readStringMap(dis, this.resultData);
         readStringMap(dis, this.resultTags);
      } catch (IOException e) {
         Oneenoughitem.LOGGER.info("{} old cache format detected, scheduling rebuild", this.domainId());
         this.needsRebuild = true;
      }
   }

   @Override
   protected void saveData(DataOutputStream dos) throws IOException {
      writeStringMap(dos, this.replacedData);
      writeStringMap(dos, this.replacedTags);
      writeStringMap(dos, this.resultData);
      writeStringMap(dos, this.resultTags);
   }

   public void addReplacement(Collection<String> matchData, Collection<String> matchTags, String resultDataStr, String resultTagStr, String sourceFile) {
      this.withInitializedWriteLock(() -> {
         String result = resultDataStr != null ? resultDataStr : resultTagStr;
         if (result != null && !result.isEmpty()) {
            processValidStrings(matchData, id -> this.replacedData.put(id, result));
            processValidStrings(matchTags, tagId -> this.replacedTags.put(tagId, result));
            addToResultMap(resultDataStr, sourceFile, this.resultData);
            addToResultMap(resultTagStr, sourceFile, this.resultTags);
            this.saveToFileAsync();
         } else {
            Oneenoughitem.LOGGER.warn("Cannot add replacement with null/empty result from file: {}", sourceFile);
         }
      });
   }

   public void removeReplacement(Collection<String> matchData, Collection<String> matchTags) {
      this.withInitializedWriteLock(() -> {
         boolean changed = false;
         changed |= this.removeFromReplacementMap(matchData, this.replacedData, "data");
         changed |= this.removeFromReplacementMap(matchTags, this.replacedTags, "tag");
         if (changed) {
            this.saveToFileAsync();
         }
      });
   }

   public boolean isDataReplaced(String id) {
      return this.withInitializedReadLock(() -> isValidString(id) && this.replacedData.containsKey(id));
   }

   public boolean isTagReplaced(String tagId) {
      return this.withInitializedReadLock(() -> isValidString(tagId) && this.replacedTags.containsKey(tagId));
   }

   public String getDataReplacement(String id) {
      return this.withInitializedReadLock(() -> isValidString(id) ? this.replacedData.get(id) : null);
   }

   public String getTagReplacement(String tagId) {
      return this.withInitializedReadLock(() -> isValidString(tagId) ? this.replacedTags.get(tagId) : null);
   }

   public boolean isDataUsedAsResult(String id) {
      return this.withInitializedReadLock(() -> isValidString(id) && this.resultData.containsKey(id));
   }

   public boolean isTagUsedAsResult(String tagId) {
      return this.withInitializedReadLock(() -> isValidString(tagId) && this.resultTags.containsKey(tagId));
   }

   public Set<String> getAllReplacedData() {
      return this.withInitializedReadLock(() -> new HashSet<>(this.replacedData.keySet()));
   }

   public Set<String> getAllReplacedTags() {
      return this.withInitializedReadLock(() -> new HashSet<>(this.replacedTags.keySet()));
   }

   public void clearAll() {
      this.withInitializedWriteLock(() -> {
         this.clearAllMaps();
         this.saveToFileAsync();
      });
   }

   public void rebuild() {
      this.withWriteLock(
         () -> {
            int oldDataCount = this.replacedData.size();
            int oldTagCount = this.replacedTags.size();
            this.clearAllMaps();
            this.rebuildFromJsonFiles();
            this.saveToFileAsync();
            Oneenoughitem.LOGGER
               .info(
                  "{} global replacement cache rebuilt: {} -> {} data, {} -> {} tags",
                  this.domainId(),
                  oldDataCount,
                  this.replacedData.size(),
                  oldTagCount,
                  this.replacedTags.size()
               );
         }
      );
   }

   protected void rebuildFromJsonFiles() {
      try {
         List<PathUtils.FileInfo> fileInfos = PathUtils.scanAllReplacementFiles(this.domainId());
         fileInfos.forEach(this::processReplacementFile);
         Oneenoughitem.LOGGER
            .info(
               "{} rebuilt from {} files: data {}, tags {}, resultData {}, resultTags {}",
               this.domainId(),
               fileInfos.size(),
               this.replacedData.size(),
               this.replacedTags.size(),
               this.resultData.size(),
               this.resultTags.size()
            );
      } catch (Exception e) {
         Oneenoughitem.LOGGER.error("{} failed to rebuild global replacement cache", this.domainId(), e);
      }
   }

   protected void processReplacementFile(PathUtils.FileInfo fileInfo) {
      try {
         String content = Files.readString(fileInfo.filePath());
         if (content.trim().isEmpty()) {
            return;
         }

         JsonElement parsed = (JsonElement)new Gson().fromJson(content, JsonElement.class);
         if (parsed != null && parsed.isJsonArray()) {
            for (JsonElement element : parsed.getAsJsonArray()) {
               this.processReplacementElement(element, fileInfo);
            }
         }
      } catch (Exception e) {
         Oneenoughitem.LOGGER.warn("{} failed to read replacement file: {}", this.domainId(), fileInfo.filePath(), e);
      }
   }

   protected void processReplacementElement(JsonElement element, PathUtils.FileInfo fileInfo) {
      try {
         DataResult<Replacements> result = Replacements.CODEC.parse(JsonOps.INSTANCE, element);
         if (result.result().isPresent()) {
            Replacements replacement = (Replacements)result.result().get();
            String resultString = replacement.result();

            for (String matchItem : replacement.match()) {
               if (isTag(matchItem)) {
                  this.replacedTags.put(removeTagPrefix(matchItem), resultString);
               } else {
                  this.replacedData.put(matchItem, resultString);
               }
            }

            if (isTag(resultString)) {
               this.resultTags.put(removeTagPrefix(resultString), fileInfo.filePath().toString());
            } else {
               this.resultData.put(resultString, fileInfo.filePath().toString());
            }
         }
      } catch (Exception e) {
         Oneenoughitem.LOGGER.warn("{} failed to parse replacement rule in file: {}", this.domainId(), fileInfo.filePath(), e);
      }
   }

   protected static void readStringMap(DataInputStream dis, Map<String, String> map) throws IOException {
      int count = dis.readInt();

      for (int i = 0; i < count; i++) {
         String key = dis.readUTF();
         String value = dis.readUTF();
         if (isValidString(key) && isValidString(value)) {
            map.put(key, value);
         }
      }
   }

   protected static void writeStringMap(DataOutputStream dos, Map<String, String> map) throws IOException {
      dos.writeInt(map.size());

      for (Entry<String, String> entry : map.entrySet()) {
         dos.writeUTF(entry.getKey());
         dos.writeUTF(entry.getValue());
      }
   }

   protected static boolean isValidString(String str) {
      return str != null && !str.isEmpty();
   }

   protected static boolean isTag(String str) {
      return str != null && str.startsWith("#");
   }

   protected static String removeTagPrefix(String tag) {
      return isTag(tag) ? tag.substring(1) : tag;
   }

   protected static void processValidStrings(Collection<String> strings, Consumer<String> processor) {
      if (strings != null) {
         strings.stream().filter(AbstractGlobalReplacementCache::isValidString).forEach(processor);
      }
   }

   protected static void addToResultMap(String item, String source, Map<String, String> resultMap) {
      if (isValidString(item)) {
         resultMap.put(item, source);
      }
   }

   protected boolean removeFromReplacementMap(Collection<String> items, Map<String, String> replacementMap, String itemType) {
      boolean changed = false;
      if (items != null) {
         for (String item : items) {
            if (isValidString(item)) {
               String removedResult = replacementMap.remove(item);
               if (removedResult != null) {
                  changed = true;
                  this.removeFromResultMaps(removedResult);
               }
            }
         }
      }

      return changed;
   }

   protected void removeFromResultMaps(String result) {
      if (isTag(result)) {
         this.resultTags.remove(removeTagPrefix(result));
      } else {
         this.resultData.remove(result);
      }
   }

   protected void clearAllMaps() {
      this.replacedData.clear();
      this.replacedTags.clear();
      this.resultData.clear();
      this.resultTags.clear();
   }

   protected <T> T withInitializedReadLock(Supplier<T> operation) {
      this.initialize();
      return this.withReadLock(operation);
   }

   protected void withInitializedWriteLock(Runnable operation) {
      this.initialize();
      this.withWriteLock(operation);
   }
}

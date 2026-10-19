package com.mafuyu404.oneenoughitem.client.gui.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mafuyu404.oneenoughitem.client.gui.util.PathUtils;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ReplacementEditorManager {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Set<String> matchDataIds = new HashSet<>();
   private final Set<ResourceLocation> matchTags = new HashSet<>();
   private String resultDataId;
   private ResourceLocation resultTag;
   private Path currentFilePath;
   private String currentFileName = "";
   private int currentObjectIndex = -1;
   private JsonArray currentJsonObjects;
   private Runnable uiUpdateCallback;

   public Set<String> getMatchDataIds() {
      return new HashSet<>(this.matchDataIds);
   }

   public Set<ResourceLocation> getMatchTags() {
      return new HashSet<>(this.matchTags);
   }

   public String getResultDataId() {
      return this.resultDataId;
   }

   public ResourceLocation getResultTag() {
      return this.resultTag;
   }

   public String getCurrentFileName() {
      return this.currentFileName;
   }

   public Path getCurrentFilePath() {
      return this.currentFilePath;
   }

   public int getCurrentObjectIndex() {
      return this.currentObjectIndex;
   }

   public int getObjectSize() {
      return this.currentJsonObjects != null ? this.currentJsonObjects.size() : 0;
   }

   public void setUiUpdateCallback(Runnable callback) {
      this.uiUpdateCallback = callback;
   }

   private void notifyUiUpdate() {
      if (this.uiUpdateCallback != null) {
         this.uiUpdateCallback.run();
      }
   }

   public void addMatchDataId(String dataId) {
      if (dataId != null && !dataId.isEmpty()) {
         this.matchDataIds.add(dataId);
      }
   }

   public void addMatchTag(ResourceLocation tagId) {
      this.matchTags.add(tagId);
   }

   public void removeMatchTag(ResourceLocation tagId) {
      this.matchTags.remove(tagId);
   }

   public boolean removeMatchDataId(String dataId) {
      return dataId != null && this.matchDataIds.remove(dataId);
   }

   public void setResultDataId(String dataId) {
      this.resultDataId = dataId;
      this.resultTag = null;
   }

   public void setResultTag(ResourceLocation tagId) {
      this.resultTag = tagId;
      this.resultDataId = null;
   }

   public void clearMatchData() {
      this.matchDataIds.clear();
      this.matchTags.clear();
   }

   public void clearResultData() {
      this.resultDataId = null;
      this.resultTag = null;
   }

   public void clearAll() {
      this.clearMatchData();
      this.clearResultData();
      this.currentFileName = "";
      this.currentFilePath = null;
      this.currentObjectIndex = -1;
      this.currentJsonObjects = null;
   }

   public void setCurrentObjectIndex(int index) {
      this.currentObjectIndex = index;
      if (index >= 0 && this.currentJsonObjects != null && index < this.currentJsonObjects.size()) {
         this.loadReplacementFromObject(index);
      } else {
         this.clearMatchData();
         this.clearResultData();
         this.notifyUiUpdate();
      }
   }

   private void loadReplacementFromObject(int index) {
      this.executeWithErrorHandling(() -> {
         JsonElement element = this.currentJsonObjects.get(index);
         DataResult<Replacements> result = Replacements.CODEC.parse(JsonOps.INSTANCE, element);
         if (result.result().isPresent()) {
            Replacements replacement = (Replacements)result.result().get();
            this.clearMatchData();
            this.clearResultData();

            for (String matchItem : replacement.match()) {
               if (matchItem.startsWith("#")) {
                  this.matchTags.add(ResourceLocation.parse(matchItem.substring(1)));
               } else {
                  this.matchDataIds.add(matchItem);
               }
            }

            String resultString = replacement.result();
            if (resultString.startsWith("#")) {
               this.resultTag = ResourceLocation.parse(resultString.substring(1));
            } else {
               this.resultDataId = resultString;
            }

            this.notifyUiUpdate();
            this.showMessage(Component.translatable("message.oneenoughitem.object_loaded", new Object[]{index + 1}).withStyle(ChatFormatting.GREEN));
         } else {
            this.showError(Component.translatable("error.oneenoughitem.object_parse_failed", new Object[]{index + 1}).withStyle(ChatFormatting.RED));
         }
      }, e -> this.showError(Component.translatable("error.oneenoughitem.object_load_error", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED)));
   }

   public void deleteObjectElement(int index) {
      if (this.currentJsonObjects != null && index >= 0 && index < this.currentJsonObjects.size()) {
         this.executeWithErrorHandling(() -> {
            JsonElement element = this.currentJsonObjects.get(index);
            this.removeReplacementFromCache(element);
            this.currentJsonObjects.remove(index);
            this.saveJsonObjectsToFile(this.currentJsonObjects);
            this.showMessage(Component.translatable("message.oneenoughitem.object_element_deleted", new Object[]{index + 1}).withStyle(ChatFormatting.GREEN));
            if (this.currentJsonObjects.isEmpty()) {
               Oneenoughitem.LOGGER.debug("JSON array is now empty, rebuilding global cache to ensure consistency");
               DomainRegistry.current().globalCache().rebuild();
            }
         }, e -> this.showError(Component.translatable("error.oneenoughitem.object_delete_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED)));
      } else {
         this.showError(Component.translatable("error.oneenoughitem.object_index_invalid").withStyle(ChatFormatting.RED));
      }
   }

   private void removeReplacementFromCache(JsonElement element) {
      DataResult<Replacements> result = Replacements.CODEC.parse(JsonOps.INSTANCE, element);
      if (result.result().isPresent()) {
         Replacements replacement = (Replacements)result.result().get();
         List<String> matchItemsList = new ArrayList<>();
         List<String> matchTagsList = new ArrayList<>();

         for (String matchItem : replacement.match()) {
            if (matchItem.startsWith("#")) {
               matchTagsList.add(matchItem.substring(1));
            } else {
               matchItemsList.add(matchItem);
            }
         }

         DomainRegistry.current().globalCache().removeReplacement(matchItemsList, matchTagsList);
         DomainRegistry.current().runtimeCache().removeReplacements(matchItemsList, matchTagsList);
         Oneenoughitem.LOGGER.debug("Removed replacement from both global and runtime cache: items={}, tags={}", matchItemsList, matchTagsList);
      }
   }

   public void createReplacementFile(String datapackName, String fileName) {
      this.executeWithErrorHandling(() -> {
         Path datapackPath = PathUtils.getDatapackPath(datapackName);
         Path replacementsPath = datapackPath.resolve("data/" + DomainRegistry.currentId() + "/replacements");
         Files.createDirectories(replacementsPath);
         this.createPackMcmetaIfNeeded(datapackPath);
         Path filePath = replacementsPath.resolve(fileName + ".json");
         if (Files.exists(filePath)) {
            this.showError(Component.translatable("error.oneenoughitem.file_already_exists", new Object[]{fileName + ".json"}).withStyle(ChatFormatting.RED));
         } else {
            JsonArray emptyObjects = new JsonArray();

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
               GSON.toJson(emptyObjects, writer);
            }

            this.currentFilePath = filePath;
            this.currentFileName = fileName;
            this.currentJsonObjects = emptyObjects;
            this.currentObjectIndex = -1;
            this.showMessage(Component.translatable("message.oneenoughitem.file_created", new Object[]{filePath.toString()}).withStyle(ChatFormatting.GREEN));
            Oneenoughitem.LOGGER.info("Created replacement file: {}", filePath);
         }
      }, e -> this.showError(Component.translatable("error.oneenoughitem.file_create_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED)));
   }

   private void createPackMcmetaIfNeeded(Path datapackPath) throws IOException {
      Path packMcmetaPath = datapackPath.resolve("pack.mcmeta");
      if (!Files.exists(packMcmetaPath)) {
         String packMcmetaContent = "{\n   \"pack\": {\n     \"description\": \"The automatically generated OEI data pack\",\n     \"pack_format\": 15\n   }\n}";

         try (FileWriter writer = new FileWriter(packMcmetaPath.toFile())) {
            writer.write(packMcmetaContent);
         }

         Oneenoughitem.LOGGER.info("Created pack.mcmeta file: {}", packMcmetaPath);
      }
   }

   public void selectJsonFile(Path selectedPath, int mode) {
      this.executeWithErrorHandling(
         () -> {
            this.currentFilePath = selectedPath;
            String fileName = selectedPath.getFileName().toString();
            this.currentFileName = fileName.endsWith(".json") ? fileName.substring(0, fileName.length() - 5) : fileName;
            if (!Files.exists(this.currentFilePath)) {
               this.createEmptyJsonFile();
            } else {
               this.currentJsonObjects = this.readExistingJsonObjects();
            }

            this.clearMatchData();
            this.clearResultData();
            this.setupModeAndIndex(mode);
            this.notifyUiUpdate();
            String modeKey = this.getModeTranslationKey(mode);
            this.showMessage(
               Component.translatable("message.oneenoughitem.file_selected", new Object[]{fileName, Component.translatable(modeKey)})
                  .withStyle(ChatFormatting.GREEN)
            );
         },
         e -> this.showError(Component.translatable("error.oneenoughitem.file_select_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED))
      );
   }

   private void createEmptyJsonFile() throws IOException {
      Files.createDirectories(this.currentFilePath.getParent());
      JsonArray emptyObjects = new JsonArray();

      try (FileWriter writer = new FileWriter(this.currentFilePath.toFile())) {
         GSON.toJson(emptyObjects, writer);
      }

      this.currentJsonObjects = emptyObjects;
   }

   private void setupModeAndIndex(int mode) {
      if (mode == 1) {
         this.currentObjectIndex = 0;
         if (!this.currentJsonObjects.isEmpty()) {
            this.loadReplacementFromObject(0);
         } else {
            this.currentObjectIndex = -1;
         }
      } else {
         this.currentObjectIndex = -1;
      }
   }

   private String getModeTranslationKey(int mode) {
      return switch (mode) {
         case 0 -> "mode.oneenoughitem.add";
         case 1 -> "mode.oneenoughitem.modify";
         case 2 -> "mode.oneenoughitem.remove";
         default -> "mode.oneenoughitem.unknown";
      };
   }

   public String getObjectElementDescription(int index) {
      if (this.currentJsonObjects != null && index >= 0 && index < this.currentJsonObjects.size()) {
         try {
            JsonElement element = this.currentJsonObjects.get(index);
            DataResult<Replacements> result = Replacements.CODEC.parse(JsonOps.INSTANCE, element);
            if (result.result().isPresent()) {
               Replacements replacement = (Replacements)result.result().get();
               int matchCount = replacement.match().size();
               String resultType = replacement.result().startsWith("#")
                  ? Component.translatable("description.oneenoughitem.tag").getString()
                  : Component.translatable("description.oneenoughitem.item").getString();
               return Component.translatable("description.oneenoughitem.items_to_type", new Object[]{matchCount, resultType}).getString();
            } else {
               return Component.translatable("description.oneenoughitem.parse_failed").getString();
            }
         } catch (Exception e) {
            return Component.translatable("description.oneenoughitem.error").getString();
         }
      } else {
         return Component.translatable("description.oneenoughitem.invalid").getString();
      }
   }

   public void setCurrentFileName(String fileName) {
      this.currentFileName = fileName != null ? fileName : "";
   }

   public void saveReplacement() {
      ReplacementEditorManager.ValidationResult validation = this.validateReplacementInput();
      if (!validation.isValid()) {
         this.showError(validation.errorMessage());
      } else if (this.currentFilePath == null) {
         this.showError(Component.translatable("error.oneenoughitem.no_file_selected").withStyle(ChatFormatting.RED));
      } else {
         this.executeWithErrorHandling(
            () -> {
               ReplacementEditorManager.ReplacementData data = this.prepareReplacementData();
               DomainRegistry.current()
                  .globalCache()
                  .addReplacement(
                     data.matchDataList(),
                     data.matchTagsList(),
                     data.resultDataString().startsWith("#") ? null : data.resultDataString(),
                     data.resultDataString().startsWith("#") ? data.resultDataString() : null,
                     this.currentFileName
                  );
               List<String> allMatchItems = new ArrayList<>(data.matchDataList());

               for (String tag : data.matchTagsList()) {
                  allMatchItems.add("#" + tag);
               }

               Replacements replacement = new Replacements(allMatchItems, data.resultDataString(), Optional.empty());
               DataResult<JsonElement> result = Replacements.CODEC.encodeStart(JsonOps.INSTANCE, replacement);
               if (result.result().isEmpty()) {
                  this.showError(Component.translatable("error.oneenoughitem.encode_failed").withStyle(ChatFormatting.RED));
               } else {
                  JsonElement replacementElement = (JsonElement)result.result().get();
                  if (this.currentObjectIndex >= 0) {
                     this.updateExistingObject(replacementElement);
                  } else {
                     this.addNewObject(replacementElement);
                  }

                  this.saveJsonObjectsToFile(this.currentJsonObjects);
                  Oneenoughitem.LOGGER.info("Saved replacement to file: {}", this.currentFilePath);
               }
            },
            e -> {
               if (e instanceof IOException) {
                  this.showError(Component.translatable("error.oneenoughitem.save_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED));
               } else {
                  this.showError(Component.translatable("error.oneenoughitem.unexpected_error", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED));
               }
            }
         );
      }
   }

   private ReplacementEditorManager.ValidationResult validateReplacementInput() {
      boolean hasMatchItems = !this.matchDataIds.isEmpty() || !this.matchTags.isEmpty();
      boolean hasResultItem = this.resultDataId != null || this.resultTag != null;
      if (!hasMatchItems && !hasResultItem) {
         return new ReplacementEditorManager.ValidationResult(false, Component.translatable("error.oneenoughitem.both_empty").withStyle(ChatFormatting.RED));
      } else if (!hasMatchItems) {
         return new ReplacementEditorManager.ValidationResult(
            false, Component.translatable("error.oneenoughitem.missing_match_items").withStyle(ChatFormatting.RED)
         );
      } else {
         return !hasResultItem
            ? new ReplacementEditorManager.ValidationResult(
               false, Component.translatable("error.oneenoughitem.missing_result_item").withStyle(ChatFormatting.RED)
            )
            : new ReplacementEditorManager.ValidationResult(true, null);
      }
   }

   private ReplacementEditorManager.ReplacementData prepareReplacementData() {
      List<String> matchItemsList = new ArrayList<>(this.matchDataIds);
      List<String> matchTagsList = new ArrayList<>();

      for (ResourceLocation tagId : this.matchTags) {
         matchTagsList.add(tagId.toString());
      }

      String resultDataString = this.getResultDataString();
      if (resultDataString == null) {
         throw new RuntimeException("Failed to get result data string");
      } else {
         return new ReplacementEditorManager.ReplacementData(matchItemsList, matchTagsList, resultDataString);
      }
   }

   private String getResultDataString() {
      return this.resultTag != null ? "#" + this.resultTag : this.resultDataId;
   }

   private void updateExistingObject(JsonElement replacementElement) {
      if (this.currentObjectIndex < this.currentJsonObjects.size()) {
         this.currentJsonObjects.set(this.currentObjectIndex, replacementElement);
         this.showMessage(
            Component.translatable("message.oneenoughitem.object_updated", new Object[]{this.currentObjectIndex + 1}).withStyle(ChatFormatting.GREEN)
         );
      } else {
         this.showError(Component.translatable("error.oneenoughitem.object_index_out_of_bounds").withStyle(ChatFormatting.RED));
         throw new RuntimeException("Object index out of bounds");
      }
   }

   private void addNewObject(JsonElement replacementElement) {
      this.currentJsonObjects.add(replacementElement);
      this.showMessage(
         Component.translatable("message.oneenoughitem.replacement_added", new Object[]{this.currentFilePath.getFileName().toString()})
            .withStyle(ChatFormatting.GREEN)
      );
   }

   public void deleteFile(Path filePath) {
      this.executeWithErrorHandling(
         () -> {
            if (Files.exists(filePath)) {
               Files.delete(filePath);
               this.showMessage(
                  Component.translatable("message.oneenoughitem.file_deleted", new Object[]{filePath.getFileName().toString()}).withStyle(ChatFormatting.GREEN)
               );
               if (filePath.equals(this.currentFilePath)) {
                  this.resetCurrentFile();
               }
            } else {
               this.showError(Component.translatable("error.oneenoughitem.file_not_exists", new Object[]{filePath.toString()}).withStyle(ChatFormatting.RED));
            }
         },
         e -> this.showError(Component.translatable("error.oneenoughitem.file_delete_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED))
      );
   }

   private void resetCurrentFile() {
      this.currentFilePath = null;
      this.currentFileName = "";
      this.currentJsonObjects = null;
      this.currentObjectIndex = -1;
      this.clearMatchData();
      this.clearResultData();
      this.notifyUiUpdate();
   }

   private void executeWithErrorHandling(ReplacementEditorManager.RunnableWithException operation, Consumer<Exception> errorHandler) {
      try {
         operation.run();
      } catch (Exception e) {
         Oneenoughitem.LOGGER.error("Operation failed", e);
         errorHandler.accept(e);
      }
   }

   private void showMessage(Component message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.sendSystemMessage(message);
      }
   }

   private void showError(Component message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.sendSystemMessage(message);
      }
   }

   private JsonArray readExistingJsonObjects() {
      JsonArray existingObjects;
      if (Files.exists(this.currentFilePath)) {
         try {
            String content = Files.readString(this.currentFilePath);
            if (content.trim().isEmpty()) {
               existingObjects = new JsonArray();
            } else {
               JsonElement parsed = (JsonElement)GSON.fromJson(content, JsonElement.class);
               if (parsed != null && parsed.isJsonArray()) {
                  existingObjects = parsed.getAsJsonArray();
               } else {
                  this.showError(Component.translatable("error.oneenoughitem.file_format_error").withStyle(ChatFormatting.RED));
                  existingObjects = new JsonArray();
               }
            }
         } catch (Exception e) {
            this.showError(Component.translatable("error.oneenoughitem.file_parse_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED));
            Oneenoughitem.LOGGER.warn("Failed to parse existing file, creating new objects", e);
            existingObjects = new JsonArray();
         }
      } else {
         existingObjects = new JsonArray();
      }

      return existingObjects;
   }

   private void saveJsonObjectsToFile(JsonArray jsonObjects) throws IOException {
      try (FileWriter writer = new FileWriter(this.currentFilePath.toFile())) {
         GSON.toJson(jsonObjects, writer);
         writer.flush();
      }
   }

   public void reloadDatapacks() {
      try {
         if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.connection.sendCommand("reload");
            DomainRegistry.current().globalCache().rebuild();
            this.showMessage(Component.translatable("message.oneenoughitem.datapack_reload_triggered").withStyle(ChatFormatting.GREEN));
            Oneenoughitem.LOGGER.info("Datapacks reloaded and global cache rebuilt");
         }
      } catch (Exception e) {
         this.showError(Component.translatable("error.oneenoughitem.reload_failed", new Object[]{e.getMessage()}).withStyle(ChatFormatting.RED));
         Oneenoughitem.LOGGER.error("Failed to reload datapacks", e);
      }
   }

   private record ReplacementData(List<String> matchDataList, List<String> matchTagsList, String resultDataString) {
   }

   @FunctionalInterface
   private interface RunnableWithException {
      void run() throws Exception;
   }

   private record ValidationResult(boolean isValid, Component errorMessage) {
   }
}

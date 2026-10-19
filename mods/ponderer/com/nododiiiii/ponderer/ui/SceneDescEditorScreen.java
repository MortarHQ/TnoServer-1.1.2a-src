package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.LocalizedText;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import com.nododiiiii.ponderer.ui.catnip.LocalizedTextListEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.ConfirmationScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class SceneDescEditorScreen extends AbstractStatefulDeclarativeFormScreen {
   private final DslScene scene;
   private final int sceneIndex;
   private final boolean hasMultiScene;
   private LocalizedText originalPonderTitle;
   private LocalizedText workingPonderTitle;
   private String ponderTitleLang;
   @Nullable
   private LocalizedText originalSceneTitle;
   @Nullable
   private LocalizedText workingSceneTitle;
   private String sceneTitleLang;
   private String originalPonderId;
   private String draftPonderId;
   private String originalSceneId;
   private String draftSceneId;
   private boolean workingEditable;
   @Nullable
   private LocalizedTextListEntry ponderTitleEntry;
   @Nullable
   private LocalizedTextListEntry sceneTitleEntry;

   public SceneDescEditorScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(parent, "ponderer.ui.scope.editor", "ponderer.ui.scene_desc");
      this.scene = scene;
      this.sceneIndex = sceneIndex;
      this.hasMultiScene = scene.scenes != null && !scene.scenes.isEmpty() && sceneIndex >= 0 && sceneIndex < scene.scenes.size();
      this.ponderTitleLang = getCurrentLang();
      this.sceneTitleLang = getCurrentLang();
      this.syncStateFromScene();
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.localizedText(
            FieldBindings.transientString(() -> localizedValue(this.workingPonderTitle, this.ponderTitleLang), value -> {
               this.workingPonderTitle.setForLang(this.ponderTitleLang, value);
               this.clearStatusMessages();
            }),
            "ponderer.ui.scene_desc.ponder_title",
            null,
            "ponderer.ui.scene_desc.hint.ponder_title",
            -1,
            () -> this.ponderTitleLang,
            this::togglePonderTitleLang,
            entry -> this.ponderTitleEntry = entry
         )
      );
      if (this.hasMultiScene && this.workingSceneTitle != null) {
         entries.add(
            FieldSpecs.localizedText(
               FieldBindings.transientString(() -> localizedValue(this.workingSceneTitle, this.sceneTitleLang), value -> {
                  this.workingSceneTitle.setForLang(this.sceneTitleLang, value);
                  this.clearStatusMessages();
               }),
               "ponderer.ui.scene_desc.scene_title",
               null,
               "ponderer.ui.scene_desc.hint.scene_title",
               -1,
               () -> this.sceneTitleLang,
               this::toggleSceneTitleLang,
               entry -> this.sceneTitleEntry = entry
            )
         );
      } else {
         this.sceneTitleEntry = null;
      }

      entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.draftPonderId, value -> {
         this.draftPonderId = value;
         this.clearStatusMessages();
      }), "ponderer.ui.scene_desc.ponder_id", "ponderer.ui.scene_desc.id_hint", "ponderer.ui.scene_desc.hint.ponder_id", -1));
      if (this.hasMultiScene) {
         entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.draftSceneId, value -> {
            this.draftSceneId = value;
            this.clearStatusMessages();
         }), "ponderer.ui.scene_desc.scene_id", "ponderer.ui.scene_desc.id_hint", "ponderer.ui.scene_desc.hint.scene_id", -1));
      }

      entries.add(
         FieldSpecs.toggle("ponderer.ui.scene_desc.editable", "ponderer.ui.scene_desc.editable.tooltip", () -> this.workingEditable, this::toggleEditable)
      );
   }

   @Override
   protected boolean saveEdits() {
      this.clearStatusMessages();
      String newPonderId = this.draftPonderId.trim();
      if (this.validatePonderId(newPonderId) && this.validateSceneId()) {
         DslScene candidate = SceneStore.copyScene(this.scene);
         if (candidate == null) {
            this.setErrorMessage(UIText.of("ponderer.ui.scene_desc.error.prepare_copy"));
            return false;
         }

         candidate.title = copyLocalizedText(this.workingPonderTitle);
         candidate.editable = this.workingEditable;
         if (!newPonderId.isEmpty()) {
            candidate.id = newPonderId;
         }

         if (this.hasMultiScene && candidate.scenes != null && this.workingSceneTitle != null) {
            candidate.scenes.get(this.sceneIndex).title = copyLocalizedText(this.workingSceneTitle);
            String newSceneId = this.draftSceneId.trim();
            if (!newSceneId.isEmpty()) {
               candidate.scenes.get(this.sceneIndex).id = newSceneId;
            }
         }

         SceneStore.LocalSaveResult saveResult = SceneStore.saveSceneToLocalDetailed(candidate);
         if (!saveResult.isSuccess()) {
            this.setErrorMessage(UIText.saveError(saveResult));
            return false;
         } else {
            this.applySavedScene(candidate);
            SceneStore.reloadFromDisk();
            Minecraft.getInstance().execute(PonderIndex::reload);
            this.syncStateFromScene();
            this.markStateSaved();
            this.rebuildListPreservingScroll();
            this.setInfoMessage(UIText.of("ponderer.ui.scene_desc.saved"));
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   protected Map<String, String> snapshotState() {
      Map<String, String> snapshot = new LinkedHashMap<>();
      snapshot.put("ponder_title_plain", String.valueOf(this.workingPonderTitle.isPlain()));
      writeLocalizedSnapshot(snapshot, "ponder_title", this.workingPonderTitle);
      snapshot.put("ponder_id", this.draftPonderId);
      snapshot.put("scene_title_present", String.valueOf(this.workingSceneTitle != null));
      if (this.workingSceneTitle != null) {
         snapshot.put("scene_title_plain", String.valueOf(this.workingSceneTitle.isPlain()));
         writeLocalizedSnapshot(snapshot, "scene_title", this.workingSceneTitle);
      }

      snapshot.put("scene_id", this.draftSceneId);
      snapshot.put("editable", String.valueOf(this.workingEditable));
      return snapshot;
   }

   @Override
   protected void restoreSnapshot(Map<String, String> snapshot) {
      this.workingPonderTitle = readLocalizedSnapshot(snapshot, "ponder_title");
      this.draftPonderId = snapshot.getOrDefault("ponder_id", "");
      boolean hasSceneTitle = Boolean.parseBoolean(snapshot.getOrDefault("scene_title_present", "false"));
      this.workingSceneTitle = hasSceneTitle ? readLocalizedSnapshot(snapshot, "scene_title") : null;
      this.draftSceneId = snapshot.getOrDefault("scene_id", "");
      this.workingEditable = Boolean.parseBoolean(snapshot.getOrDefault("editable", "true"));
   }

   private boolean validatePonderId(String newPonderId) {
      if (!newPonderId.isEmpty() && !newPonderId.equals(this.scene.id)) {
         for (DslScene existingScene : SceneRuntime.getScenes()) {
            if (existingScene != this.scene && newPonderId.equals(existingScene.id)) {
               this.setErrorMessage(Component.translatable("ponderer.ui.scene_desc.error.ponder_id_exists", new Object[]{newPonderId}).getString());
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private boolean validateSceneId() {
      if (!this.hasMultiScene) {
         return true;
      }

      String newSceneId = this.draftSceneId.trim();
      DslScene.SceneSegment currentScene = this.scene.scenes.get(this.sceneIndex);
      if (!newSceneId.isEmpty() && !newSceneId.equals(currentScene.id)) {
         for (int i = 0; i < this.scene.scenes.size(); i++) {
            if (i != this.sceneIndex && newSceneId.equals(this.scene.scenes.get(i).id)) {
               this.setErrorMessage(Component.translatable("ponderer.ui.scene_desc.error.scene_id_exists", new Object[]{newSceneId}).getString());
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private void applySavedScene(DslScene candidate) {
      this.scene.title = candidate.title;
      this.scene.id = candidate.id;
      if (this.hasMultiScene
         && candidate.scenes != null
         && this.scene.scenes != null
         && this.sceneIndex >= 0
         && this.sceneIndex < candidate.scenes.size()
         && this.sceneIndex < this.scene.scenes.size()) {
         this.scene.scenes.get(this.sceneIndex).title = candidate.scenes.get(this.sceneIndex).title;
         this.scene.scenes.get(this.sceneIndex).id = candidate.scenes.get(this.sceneIndex).id;
      }

      this.scene.editable = candidate.editable;
   }

   private void syncStateFromScene() {
      this.originalPonderTitle = copyLocalizedText(this.scene.title);
      this.workingPonderTitle = copyLocalizedText(this.scene.title);
      this.originalPonderId = this.scene.id != null ? this.scene.id : "";
      this.draftPonderId = this.originalPonderId;
      this.workingEditable = this.scene.isEditable((Boolean)Config.DEFAULT_EDITABLE.get());
      if (this.hasMultiScene && this.scene.scenes != null && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         DslScene.SceneSegment currentScene = this.scene.scenes.get(this.sceneIndex);
         this.originalSceneTitle = copyLocalizedText(currentScene.title);
         this.workingSceneTitle = copyLocalizedText(currentScene.title);
         this.originalSceneId = currentScene.id != null ? currentScene.id : "";
         this.draftSceneId = this.originalSceneId;
      } else {
         this.originalSceneTitle = null;
         this.workingSceneTitle = null;
         this.originalSceneId = "";
         this.draftSceneId = "";
      }
   }

   private void toggleEditable() {
      if (!this.workingEditable) {
         this.workingEditable = true;
         this.clearStatusMessages();
      } else if ((Boolean)Config.DEVELOPER_MODE.get()) {
         this.workingEditable = false;
         this.clearStatusMessages();
      } else {
         new ConfirmationScreen()
            .centered()
            .withText(Component.translatable("ponderer.ui.scene_desc.editable.confirm_title"))
            .addText(Component.translatable("ponderer.ui.scene_desc.editable.confirm"))
            .withAction(confirmed -> {
               if (confirmed) {
                  this.workingEditable = false;
                  this.clearStatusMessages();
               }
            })
            .open(this);
      }
   }

   private void togglePonderTitleLang() {
      this.ponderTitleLang = nextLang(this.ponderTitleLang);
      if (this.ponderTitleEntry != null) {
         this.ponderTitleEntry.setValue(localizedValue(this.workingPonderTitle, this.ponderTitleLang));
      }

      this.clearStatusMessages();
   }

   private void toggleSceneTitleLang() {
      if (this.workingSceneTitle != null) {
         this.sceneTitleLang = nextLang(this.sceneTitleLang);
         if (this.sceneTitleEntry != null) {
            this.sceneTitleEntry.setValue(localizedValue(this.workingSceneTitle, this.sceneTitleLang));
         }

         this.clearStatusMessages();
      }
   }

   private static String nextLang(String current) {
      String minecraftLang = getCurrentLang();
      return "en_us".equals(current) && !"en_us".equals(minecraftLang) ? minecraftLang : "en_us";
   }

   private static String localizedValue(@Nullable LocalizedText text, String lang) {
      if (text == null) {
         return "";
      }

      String exact = text.getExact(lang);
      return exact != null ? exact : "";
   }

   private static String getCurrentLang() {
      try {
         return Minecraft.getInstance().getLanguageManager().getSelected();
      } catch (Exception e) {
         return "en_us";
      }
   }

   private static LocalizedText copyLocalizedText(@Nullable LocalizedText text) {
      if (text == null) {
         return LocalizedText.of("");
      } else {
         return text.isPlain() ? LocalizedText.of(text.resolve()) : LocalizedText.ofMap(new LinkedHashMap<>(text.getAllTranslations()));
      }
   }

   private static void writeLocalizedSnapshot(Map<String, String> snapshot, String prefix, LocalizedText text) {
      snapshot.put(prefix + "_count", String.valueOf(text.getAllTranslations().size()));
      int index = 0;

      for (Entry<String, String> entry : text.getAllTranslations().entrySet()) {
         snapshot.put(prefix + "_lang_" + index, entry.getKey());
         snapshot.put(prefix + "_value_" + index, entry.getValue());
         index++;
      }
   }

   private static LocalizedText readLocalizedSnapshot(Map<String, String> snapshot, String prefix) {
      int count = 0;

      try {
         count = Integer.parseInt(snapshot.getOrDefault(prefix + "_count", "0"));
      } catch (NumberFormatException var7) {
      }

      LinkedHashMap<String, String> values = new LinkedHashMap<>();

      for (int i = 0; i < count; i++) {
         String lang = snapshot.get(prefix + "_lang_" + i);
         String value = snapshot.get(prefix + "_value_" + i);
         if (lang != null && value != null) {
            values.put(lang, value);
         }
      }

      return values.isEmpty() ? LocalizedText.of("") : LocalizedText.ofMap(values);
   }
}

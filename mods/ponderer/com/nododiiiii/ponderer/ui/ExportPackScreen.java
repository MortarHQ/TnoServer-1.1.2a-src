package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.client.Minecraft;

public class ExportPackScreen extends AbstractStatefulDeclarativeFormScreen {
   private String draftName = "";
   private String draftVersion = "1.0.0";
   private String draftAuthor = "";
   private Set<String> selectedSceneIds = new HashSet<>();

   public ExportPackScreen() {
      super(new FunctionScreen(), "ponderer.ui.scope.editor", "ponderer.ui.function_page.export.title", 300);
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.draftName, value -> {
         this.draftName = value;
         this.clearStatusMessages();
      }), "ponderer.ui.export.name", null, "ponderer.ui.export.name", -1));
      entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.draftVersion, value -> {
         this.draftVersion = value;
         this.clearStatusMessages();
      }), "ponderer.ui.export.version", null, "ponderer.ui.export.version", -1));
      entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.draftAuthor, value -> {
         this.draftAuthor = value;
         this.clearStatusMessages();
      }), "ponderer.ui.export.author", null, "ponderer.ui.export.author", -1));
      entries.add(
         FieldSpecs.labeledButton(
            "ponderer.ui.export.scene", null, this::openSceneSelector, this::currentSceneSelectionButtonLabel, this::currentSceneSelectionTooltip
         )
      );
   }

   @Override
   protected boolean saveEdits() {
      this.clearStatusMessages();
      String name = this.draftName.trim();
      String version = this.draftVersion.trim();
      String author = this.draftAuthor.trim();
      if (version.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.export.version_empty"));
         return false;
      } else {
         SceneStore.PackExportResult result = this.selectedSceneIds.isEmpty()
            ? SceneStore.packScenesAndStructuresDetailed(name, version, author)
            : SceneStore.packSelectedScenesAndStructuresDetailed(name, version, author, this.selectedSceneIds);
         if (result.isSuccess()) {
            this.markStateSaved();
            this.setInfoMessage(UIText.of("ponderer.ui.export.success", name));
            return true;
         } else {
            String key = result.uiMessageKey();
            this.setErrorMessage(key != null && !key.isBlank() ? UIText.of(key, result.uiMessageArgs()) : UIText.of("ponderer.ui.export.failed"));
            return false;
         }
      }
   }

   @Override
   protected boolean isSaveButtonActive() {
      return true;
   }

   @Override
   protected Map<String, String> snapshotState() {
      Map<String, String> snapshot = new LinkedHashMap<>();
      snapshot.put("name", this.draftName);
      snapshot.put("version", this.draftVersion);
      snapshot.put("author", this.draftAuthor);
      TreeSet<String> ordered = new TreeSet<>(this.selectedSceneIds);
      snapshot.put("scene_count", String.valueOf(ordered.size()));
      int index = 0;

      for (String sceneId : ordered) {
         snapshot.put("scene_" + index, sceneId);
         index++;
      }

      return snapshot;
   }

   @Override
   protected void restoreSnapshot(Map<String, String> snapshot) {
      this.draftName = snapshot.getOrDefault("name", "");
      this.draftVersion = snapshot.getOrDefault("version", "1.0.0");
      this.draftAuthor = snapshot.getOrDefault("author", "");
      this.selectedSceneIds = new HashSet<>();
      int count = 0;

      try {
         count = Integer.parseInt(snapshot.getOrDefault("scene_count", "0"));
      } catch (NumberFormatException var5) {
      }

      for (int i = 0; i < count; i++) {
         String sceneId = snapshot.get("scene_" + i);
         if (sceneId != null && !sceneId.isBlank()) {
            this.selectedSceneIds.add(sceneId);
         }
      }
   }

   private void openSceneSelector() {
      Minecraft.getInstance().setScreen(new PonderItemGridScreen(selectedIds -> {
         this.selectedSceneIds = new HashSet<>(selectedIds);
         Minecraft.getInstance().setScreen(this);
      }, () -> Minecraft.getInstance().setScreen(this), true));
   }

   private String currentSceneSelectionButtonLabel() {
      return this.selectedSceneIds.isEmpty()
         ? UIText.of("ponderer.ui.export.select_scenes")
         : UIText.of("ponderer.ui.export.selected_scenes", this.selectedSceneIds.size());
   }

   private String currentSceneSelectionTooltip() {
      return this.selectedSceneIds.isEmpty()
         ? UIText.of("ponderer.ui.export.all_scenes")
         : UIText.of("ponderer.ui.export.selected_scenes", this.selectedSceneIds.size());
   }
}

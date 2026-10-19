package com.nododiiiii.ponderer.blueprint;

import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.FieldBindings;
import com.nododiiiii.ponderer.ui.FieldSpecs;
import com.nododiiiii.ponderer.ui.UIText;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeFormScreen;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import net.minecraft.client.Minecraft;

public class BlueprintPromptScreen extends AbstractDeclarativeFormScreen {
   private String blueprintName = "";
   private String baselineName = "";
   private boolean awaitingOverrideConfirm = false;

   public BlueprintPromptScreen() {
      super(null, "ponderer.ui.scope.editor", "ponderer.ui.blueprint.prompt.title", 300);
   }

   @Override
   protected void init() {
      super.init();
      if (this.discardChanges != null) {
         this.discardChanges.withCallback(this::discardBlueprint);
         this.discardChanges.active = true;
      }
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.text(FieldBindings.transientString(() -> this.blueprintName, value -> {
         this.blueprintName = value;
         this.awaitingOverrideConfirm = false;
         this.clearStatusMessages();
      }), "ponderer.ui.blueprint.prompt.name", null, "ponderer.ui.blueprint.prompt.name", -1));
   }

   @Override
   protected boolean hasUnsavedChanges() {
      return !this.blueprintName.trim().equals(this.baselineName.trim()) || this.awaitingOverrideConfirm;
   }

   @Override
   protected int getUnsavedChangeCount() {
      return this.hasUnsavedChanges() ? 1 : 0;
   }

   @Override
   protected boolean isDiscardButtonActive() {
      return true;
   }

   @Override
   protected boolean saveEdits() {
      String name = this.blueprintName.trim();
      if (!this.awaitingOverrideConfirm && SceneStore.isBuiltinStructureName(name)) {
         this.awaitingOverrideConfirm = true;
         this.setErrorMessage(UIText.of("ponderer.ui.blueprint.prompt.override_warn"));
         return false;
      } else {
         BlueprintExport.SaveResult result = BlueprintHandler.INSTANCE.saveBlueprint(this.blueprintName);
         if (result.isSuccess()) {
            Minecraft.getInstance().setScreen(null);
            return true;
         } else {
            this.awaitingOverrideConfirm = false;
            this.setErrorMessage(UIText.of(result.uiMessageKey(), result.uiMessageArgs()));
            return false;
         }
      }
   }

   @Override
   protected void discardEdits() {
      this.awaitingOverrideConfirm = false;
      this.blueprintName = this.baselineName;
      this.clearStatusMessages();
      this.rebuildListPreservingScroll();
   }

   public boolean isPauseScreen() {
      return false;
   }

   private void discardBlueprint() {
      BlueprintHandler.INSTANCE.discard();
      Minecraft.getInstance().setScreen(null);
   }
}

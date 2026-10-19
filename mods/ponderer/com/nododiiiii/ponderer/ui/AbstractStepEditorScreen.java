package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractStepEditorScreen extends AbstractSceneEditorFormScreen implements PointPickButtonHost {
   protected static final int STEP_EDITOR_LIST_WIDTH = 300;
   protected final int editIndex;
   @Nullable
   protected final DslScene.DslStep existingStep;
   protected boolean attachKeyFrame = false;
   protected int insertAfterIndex = -1;
   protected static final Map<String, Integer> PALETTE_COLORS = Map.ofEntries(
      Map.entry("white", 15658734),
      Map.entry("black", 2232593),
      Map.entry("red", 16735596),
      Map.entry("green", 9222737),
      Map.entry("blue", 6253743),
      Map.entry("slow", 2293538),
      Map.entry("medium", 34047),
      Map.entry("fast", 16733695),
      Map.entry("input", 8375776),
      Map.entry("output", 14532966)
   );

   protected static int getPaletteColor(String name) {
      return PALETTE_COLORS.getOrDefault(name.toLowerCase(), 16777215);
   }

   protected AbstractStepEditorScreen(Component title, DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      this(title, scene, sceneIndex, parent, -1, null);
   }

   protected AbstractStepEditorScreen(
      Component title, DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, @Nullable DslScene.DslStep existingStep
   ) {
      super(
         scene,
         sceneIndex,
         parent,
         "ponderer.ui.scope.editor",
         "ponderer.ui.step_editor",
         300,
         (screen, mode) -> JeiCompat.setActiveEditor((AbstractStepEditorScreen)screen, mode)
      );
      this.editIndex = editIndex;
      this.existingStep = existingStep;
   }

   protected boolean isEditMode() {
      return this.editIndex >= 0 && this.existingStep != null;
   }

   public AbstractStepEditorScreen setReturnScreen(@Nullable Screen returnScreen) {
      super.setReturnScreen(returnScreen);
      return this;
   }

   public AbstractStepEditorScreen setInsertAfterIndex(int index) {
      this.insertAfterIndex = index;
      return this;
   }

   public AbstractStepEditorScreen setPendingPickRestore(@Nullable Map<String, String> snapshot) {
      this.setPendingFormRestore(snapshot);
      return this;
   }

   @Override
   protected void prepareInitialState() {
      if (this.isEditMode()) {
         this.populateFromStep(this.existingStep);
      }
   }

   @Override
   protected void configureActionButtons() {
      if (this.saveChanges != null) {
         this.saveChanges.withCallback(this::saveAndClose);
      }
   }

   @Override
   protected boolean isSaveButtonActive() {
      return !this.isEditMode() || super.isSaveButtonActive();
   }

   @Override
   protected void addBaseFormStateParticipants(List<SnapshotParticipant> participants) {
      participants.add(FieldBindings.bool("_keyFrame", () -> this.attachKeyFrame, value -> this.attachKeyFrame = value));
   }

   @Override
   protected final void collectFormEntries(List<DeclarativeFormEntry> entries) {
      this.collectStepEntries(entries);
      if (this.showsKeyFrame()) {
         entries.add(
            FieldSpecs.toggle(
               "ponderer.ui.key_frame", "ponderer.ui.key_frame.tooltip", () -> this.attachKeyFrame, () -> this.attachKeyFrame = !this.attachKeyFrame
            )
         );
      }
   }

   protected abstract void collectStepEntries(List<DeclarativeFormEntry> var1);

   @Override
   protected String getBreadcrumbTitleText() {
      return this.getHeaderTitle() + (this.isEditMode() ? UIText.of("ponderer.ui.edit_suffix") : "");
   }

   @Override
   protected boolean saveEdits() {
      this.clearStatusMessages();
      DslScene.DslStep step = this.buildStep();
      if (step == null) {
         return false;
      }

      if (this.attachKeyFrame) {
         step.attachKeyFrame = true;
      }

      if (this.isEditMode()) {
         this.parent.replaceStepAndSave(this.editIndex, step);
      } else {
         this.parent.insertStepAndSave(this.insertAfterIndex, step);
      }

      this.markStateSaved();
      return true;
   }

   protected boolean showsKeyFrame() {
      return true;
   }

   protected void populateFromStep(DslScene.DslStep step) {
      this.attachKeyFrame = Boolean.TRUE.equals(step.attachKeyFrame);
   }

   @Nullable
   protected abstract DslScene.DslStep buildStep();

   @Override
   protected abstract String getHeaderTitle();

   protected abstract String getStepType();

   @Override
   protected SnapshotReturnContext createReturnContext() {
      return new StepEditorContext(this.getStepType(), this.editIndex, this.insertAfterIndex, this.scene, this.sceneIndex, this.parent);
   }

   @Override
   public final void startPointPickFromButton(PickState.TargetField target, boolean halfOffset) {
      PickState.TargetField effectiveTarget = this.resolvePointPickTarget(target);
      PickState.startPick(
         effectiveTarget,
         this.snapshotForm(),
         this.getStepType(),
         this.editIndex,
         this.insertAfterIndex,
         this.scene,
         this.sceneIndex,
         this.parent,
         effectiveTarget == PickState.TargetField.UI_POINT ? false : halfOffset
      );
      PickState.openPonderUIForPick();
   }

   private PickState.TargetField resolvePointPickTarget(PickState.TargetField target) {
      return target == PickState.TargetField.POINT && this.isInterfaceStartScene() ? PickState.TargetField.UI_POINT : target;
   }

   private boolean isInterfaceStartScene() {
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty()) {
         if (this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
            List<DslScene.DslStep> steps = this.scene.scenes.get(this.sceneIndex).steps;
            if (steps == null) {
               return false;
            }

            for (DslScene.DslStep step : steps) {
               if (step != null && step.type != null && !step.type.isBlank()) {
                  return "show_interface".equalsIgnoreCase(step.type);
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean saveAndClose() {
      if (!this.saveEdits()) {
         return false;
      }

      this.returnToParent();
      return true;
   }
}

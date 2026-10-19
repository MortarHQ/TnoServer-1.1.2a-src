package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;

public record StepEditorContext(String stepType, int editIndex, int insertAfterIndex, DslScene scene, int sceneIndex, SceneEditorScreen parent)
   implements SnapshotReturnContext {
   @Nullable
   public AbstractStepEditorScreen createEditor() {
      if (this.editIndex < 0) {
         return StepEditorFactory.createAddScreen(this.stepType, this.scene, this.sceneIndex, this.parent);
      }

      List<DslScene.DslStep> steps = getStepsForScene(this.scene, this.sceneIndex);
      DslScene.DslStep existingStep = steps != null && this.editIndex < steps.size() ? steps.get(this.editIndex) : null;
      return StepEditorFactory.createEditScreen(existingStep, this.editIndex, this.scene, this.sceneIndex, this.parent);
   }

   @Override
   public void reopenEditor(Map<String, String> snapshot) {
      AbstractStepEditorScreen editor = this.createEditor();
      if (editor == null) {
         snapshot.clear();
      } else {
         editor.setInsertAfterIndex(this.insertAfterIndex);
         editor.setPendingPickRestore(snapshot);
         Minecraft.getInstance().setScreen(editor);
      }
   }

   @Nullable
   public static List<DslScene.DslStep> getStepsForScene(@Nullable DslScene scene, int sceneIndex) {
      if (scene == null || scene.scenes == null || scene.scenes.isEmpty()) {
         return null;
      } else {
         return sceneIndex >= 0 && sceneIndex < scene.scenes.size() ? scene.scenes.get(sceneIndex).steps : null;
      }
   }
}

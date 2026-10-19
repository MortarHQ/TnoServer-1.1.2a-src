package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class ZoomSceneScreen extends AbstractStepEditorScreen {
   private final StepXyzFieldHandle centerField = new StepXyzFieldHandle("point");
   private final StepTextFieldHandle scaleField = new StepTextFieldHandle("scale");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");

   public ZoomSceneScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.zoom_scene.add"), scene, sceneIndex, parent);
   }

   public ZoomSceneScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.zoom_scene.edit"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.zoom_scene");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.xyz(this.centerField, "ponderer.ui.zoom_scene.center", "ponderer.ui.zoom_scene.center.tooltip", PickState.TargetField.POINT, true));
      entries.add(FieldSpecs.number(this.scaleField, "ponderer.ui.zoom_scene.scale", "ponderer.ui.zoom_scene.scale.tooltip", "1.0", 60, null));
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.zoom_scene.duration.tooltip", "20", 60));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.point != null && step.point.size() >= 3) {
         this.centerField.setValue(step.point.get(0), step.point.get(1), step.point.get(2));
      }

      if (step.scale != null) {
         this.scaleField.setValue(String.valueOf(step.scale));
      }

      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
      }
   }

   @Override
   protected String getStepType() {
      return "zoom_scene";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Double x = this.parseOptionalDouble(this.centerField.x(), "Center X");
      Double y = this.parseOptionalDouble(this.centerField.y(), "Center Y");
      Double z = this.parseOptionalDouble(this.centerField.z(), "Center Z");
      boolean hasCenter = x != null || y != null || z != null;
      if (!hasCenter || x != null && y != null && z != null) {
         Float scale = null;
         String scaleRaw = this.scaleField.getValue() == null ? "" : this.scaleField.getValue().trim();
         if (!scaleRaw.isEmpty()) {
            scale = this.parseFloat(scaleRaw, "Scale");
            if (scale == null) {
               return null;
            }

            if (scale <= 0.0F) {
               this.setErrorMessage(UIText.of("ponderer.ui.zoom_scene.error.scale_positive"));
               return null;
            }
         }

         int duration = Math.max(0, this.parseIntOr(this.durationField.getValue(), 20));
         DslScene.DslStep s = new DslScene.DslStep();
         s.type = "zoom_scene";
         if (hasCenter) {
            s.point = List.of(x, y, z);
         }

         s.scale = scale;
         s.duration = duration;
         return s;
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.zoom_scene.error.partial_center"));
         return null;
      }
   }

   @Nullable
   private Double parseOptionalDouble(String raw, String label) {
      String trimmed = raw == null ? "" : raw.trim();
      return trimmed.isEmpty() ? null : this.parseDouble(trimmed, label);
   }
}

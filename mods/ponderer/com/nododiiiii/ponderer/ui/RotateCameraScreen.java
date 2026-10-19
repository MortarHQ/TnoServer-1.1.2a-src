package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class RotateCameraScreen extends AbstractStepEditorScreen {
   private final StepTextFieldHandle degreesField = new StepTextFieldHandle("degrees");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");

   public RotateCameraScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.rotate_camera.add"), scene, sceneIndex, parent);
   }

   public RotateCameraScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.rotate_camera.edit"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.rotate_camera");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.number(
            this.degreesField,
            "ponderer.ui.rotate_camera.degrees",
            "ponderer.ui.rotate_camera.degrees.tooltip",
            "90",
            60,
            "ponderer.ui.rotate_camera.degrees.unit"
         )
      );
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.rotate_camera.duration.tooltip", "20", 60));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.degrees != null) {
         this.degreesField.setValue(String.valueOf(step.degrees));
      }

      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
      }
   }

   @Override
   protected String getStepType() {
      return "rotate_camera_y";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      String raw = this.degreesField.getValue() == null ? "" : this.degreesField.getValue().trim();
      raw = raw.replaceAll("[^0-9+\\-\\.]", "").trim();
      if (raw.isEmpty()) {
         raw = "90";
      }

      Float degrees = this.parseFloat(raw, "Degrees");
      if (degrees == null) {
         return null;
      } else {
         DslScene.DslStep s = new DslScene.DslStep();
         s.type = "rotate_camera_y";
         s.degrees = degrees;
         s.duration = this.parseIntOr(this.durationField.getValue(), 20);
         if (s.duration < 0) {
            this.setErrorMessage(UIText.of("ponderer.ui.rotate_camera.error.duration"));
            return null;
         } else {
            return s;
         }
      }
   }
}

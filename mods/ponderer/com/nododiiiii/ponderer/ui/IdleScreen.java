package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class IdleScreen extends AbstractStepEditorScreen {
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");

   public IdleScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.idle"), scene, sceneIndex, parent);
   }

   public IdleScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.idle"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.idle");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.duration.tooltip.idle", "20", 60));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
      }
   }

   @Override
   protected String getStepType() {
      return "idle";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      DslScene.DslStep s = new DslScene.DslStep();
      s.type = "idle";
      s.duration = this.parseIntOr(this.durationField.getValue(), 20);
      if (s.duration < 0) {
         this.setErrorMessage(UIText.of("ponderer.ui.idle.error.duration"));
         return null;
      } else {
         return s;
      }
   }
}

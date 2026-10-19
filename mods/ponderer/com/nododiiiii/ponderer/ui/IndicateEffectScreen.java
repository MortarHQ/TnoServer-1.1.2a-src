package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class IndicateEffectScreen extends AbstractStepEditorScreen {
   private final String stepType;
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");

   public IndicateEffectScreen(String stepType, DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui." + stepType + ".add"), scene, sceneIndex, parent);
      this.stepType = stepType;
   }

   public IndicateEffectScreen(String stepType, DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui." + stepType + ".edit"), scene, sceneIndex, parent, editIndex, step);
      this.stepType = stepType;
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui." + this.stepType);
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.xyz(this.posField, "ponderer.ui." + this.stepType + ".pos", "ponderer.ui." + this.stepType + ".pos.tooltip", PickState.TargetField.POS1)
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }
   }

   @Override
   protected String getStepType() {
      return this.stepType;
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Integer px = this.parseInt(this.posField.x(), "X");
      Integer py = this.parseInt(this.posField.y(), "Y");
      Integer pz = this.parseInt(this.posField.z(), "Z");
      if (px != null && py != null && pz != null) {
         DslScene.DslStep s = new DslScene.DslStep();
         s.type = this.stepType;
         s.blockPos = List.of(px, py, pz);
         return s;
      } else {
         return null;
      }
   }
}

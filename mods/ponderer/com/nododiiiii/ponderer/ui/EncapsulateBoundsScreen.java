package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class EncapsulateBoundsScreen extends AbstractStepEditorScreen {
   private final StepXyzFieldHandle boundsField = new StepXyzFieldHandle("bounds");

   public EncapsulateBoundsScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.encapsulate_bounds"), scene, sceneIndex, parent);
   }

   public EncapsulateBoundsScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.encapsulate_bounds"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.encapsulate_bounds");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.xyz(this.boundsField, "ponderer.ui.encapsulate_bounds.bounds", "ponderer.ui.encapsulate_bounds.bounds.tooltip"));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.bounds != null && step.bounds.size() >= 3) {
         this.boundsField.setValue(step.bounds.get(0), step.bounds.get(1), step.bounds.get(2));
      }
   }

   @Override
   protected String getStepType() {
      return "encapsulate_bounds";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Integer bx = this.parseInt(this.boundsField.x(), "X");
      Integer by = this.parseInt(this.boundsField.y(), "Y");
      Integer bz = this.parseInt(this.boundsField.z(), "Z");
      if (bx != null && by != null && bz != null) {
         DslScene.DslStep s = new DslScene.DslStep();
         s.type = "encapsulate_bounds";
         s.bounds = List.of(bx, by, bz);
         return s;
      } else {
         return null;
      }
   }
}

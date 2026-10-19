package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public class ClearEntitiesScreen extends AbstractStepEditorScreen {
   private final String stepType;
   private final IdFieldMode jeiMode;
   private final StepTextFieldHandle idField = new StepTextFieldHandle("id");
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private boolean fullScene = false;

   public ClearEntitiesScreen(String stepType, DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui." + stepType + ".add"), scene, sceneIndex, parent);
      this.stepType = stepType;
      this.jeiMode = "clear_item_entities".equals(stepType) ? IdFieldMode.ITEM : IdFieldMode.ENTITY;
   }

   public ClearEntitiesScreen(String stepType, DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui." + stepType + ".edit"), scene, sceneIndex, parent, editIndex, step);
      this.stepType = stepType;
      this.jeiMode = "clear_item_entities".equals(stepType) ? IdFieldMode.ITEM : IdFieldMode.ENTITY;
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui." + this.stepType);
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      if (this.jeiMode == IdFieldMode.ITEM) {
         entries.add(
            FieldSpecs.text(
               this.idField,
               "ponderer.ui." + this.stepType + ".id",
               "ponderer.ui." + this.stepType + ".id.tooltip",
               UIText.of("ponderer.ui." + this.stepType + ".id.hint"),
               124,
               FieldDecorators.jei(this.jeiMode),
               FieldDecorators.heldItem(stack -> this.idField.setValue(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString()))
            )
         );
      } else {
         entries.add(
            FieldSpecs.text(
               this.idField,
               "ponderer.ui." + this.stepType + ".id",
               "ponderer.ui." + this.stepType + ".id.tooltip",
               UIText.of("ponderer.ui." + this.stepType + ".id.hint"),
               124,
               FieldDecorators.jei(this.jeiMode)
            )
         );
      }

      entries.add(
         FieldSpecs.xyz(
            this.posField, "ponderer.ui." + this.stepType + ".pos_from", "ponderer.ui." + this.stepType + ".pos_from.tooltip", PickState.TargetField.POS1
         )
      );
      entries.add(
         FieldSpecs.xyz(
            this.pos2Field, "ponderer.ui." + this.stepType + ".pos_to", "ponderer.ui." + this.stepType + ".pos_to.tooltip", PickState.TargetField.POS2
         )
      );
      entries.add(
         FieldSpecs.toggle(
            "ponderer.ui." + this.stepType + ".full_scene",
            "ponderer.ui." + this.stepType + ".full_scene.tooltip",
            () -> this.fullScene,
            () -> this.fullScene = !this.fullScene
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.item != null) {
         this.idField.setValue(step.item);
      }

      if (step.entity != null) {
         this.idField.setValue(step.entity);
      }

      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
      }

      if (step.fullScene != null) {
         this.fullScene = step.fullScene;
      }
   }

   @Override
   protected String getStepType() {
      return this.stepType;
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("fullScene", String.valueOf(this.fullScene));
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("fullScene")) {
         this.fullScene = Boolean.parseBoolean(snapshot.get("fullScene"));
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Integer px = null;
      Integer py = null;
      Integer pz = null;
      Integer px2 = null;
      Integer py2 = null;
      Integer pz2 = null;
      if (!this.fullScene) {
         px = this.parseInt(this.posField.x(), "X");
         py = this.parseInt(this.posField.y(), "Y");
         pz = this.parseInt(this.posField.z(), "Z");
         if (px == null || py == null || pz == null) {
            return null;
         }

         String pos2X = this.pos2Field.x().trim();
         String pos2Y = this.pos2Field.y().trim();
         String pos2Z = this.pos2Field.z().trim();
         boolean hasPos2 = !pos2X.isEmpty() || !pos2Y.isEmpty() || !pos2Z.isEmpty();
         if (hasPos2) {
            if (pos2X.isEmpty() || pos2Y.isEmpty() || pos2Z.isEmpty()) {
               this.setErrorMessage(UIText.of("ponderer.ui." + this.stepType + ".error.partial_to"));
               return null;
            }

            px2 = this.parseInt(pos2X, "X2");
            py2 = this.parseInt(pos2Y, "Y2");
            pz2 = this.parseInt(pos2Z, "Z2");
            if (px2 == null || py2 == null || pz2 == null) {
               return null;
            }
         }
      }

      DslScene.DslStep s = new DslScene.DslStep();
      s.type = this.stepType;
      String id = this.idField.getValue().trim();
      if (!id.isEmpty()) {
         if ("clear_item_entities".equals(this.stepType)) {
            s.item = id;
         } else {
            s.entity = id;
         }
      }

      if (this.fullScene) {
         s.fullScene = true;
      } else {
         s.blockPos = List.of(px, py, pz);
         if (px2 != null) {
            s.blockPos2 = List.of(px2, py2, pz2);
         }
      }

      return s;
   }
}

package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class SectionTransformScreen extends AbstractStepEditorScreen {
   private final String stepType;
   private final boolean rotationMode;
   private final StepTextFieldHandle linkIdField = new StepTextFieldHandle("linkId");
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private final StepXyzFieldHandle xyzField = new StepXyzFieldHandle("x", "y", "z");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");

   public SectionTransformScreen(String stepType, boolean rotationMode, DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui." + stepType + ".add"), scene, sceneIndex, parent);
      this.stepType = stepType;
      this.rotationMode = rotationMode;
   }

   public SectionTransformScreen(
      String stepType, boolean rotationMode, DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step
   ) {
      super(Component.translatable("ponderer.ui." + stepType + ".edit"), scene, sceneIndex, parent, editIndex, step);
      this.stepType = stepType;
      this.rotationMode = rotationMode;
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui." + this.stepType);
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.text(this.linkIdField, "ponderer.ui." + this.stepType + ".link", "ponderer.ui." + this.stepType + ".link.tooltip", "", 140));
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
      entries.add(FieldSpecs.xyz(this.xyzField, "ponderer.ui." + this.stepType + ".xyz", "ponderer.ui." + this.stepType + ".xyz.tooltip"));
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.duration.tooltip.section_animation", "20", 60));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.linkId != null) {
         this.linkIdField.setValue(step.linkId);
      }

      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
      }

      if (this.rotationMode) {
         if (step.rotX != null) {
            this.xyzField.xHandle().setValue(String.valueOf(step.rotX));
         }

         if (step.rotY != null) {
            this.xyzField.yHandle().setValue(String.valueOf(step.rotY));
         }

         if (step.rotZ != null) {
            this.xyzField.zHandle().setValue(String.valueOf(step.rotZ));
         }
      } else if (step.offset != null && step.offset.size() >= 3) {
         this.xyzField.setValue(step.offset.get(0), step.offset.get(1), step.offset.get(2));
      }

      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
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
      Double x = this.parseDouble(this.xyzField.x(), "X");
      Double y = this.parseDouble(this.xyzField.y(), "Y");
      Double z = this.parseDouble(this.xyzField.z(), "Z");
      if (x != null && y != null && z != null) {
         int duration = this.parseIntOr(this.durationField.getValue(), 20);
         Integer px = this.parseOptionalInt(this.posField.x(), "From X");
         Integer py = this.parseOptionalInt(this.posField.y(), "From Y");
         Integer pz = this.parseOptionalInt(this.posField.z(), "From Z");
         boolean hasPos1 = px != null || py != null || pz != null;
         if (!hasPos1 || px != null && py != null && pz != null) {
            String pos2X = this.pos2Field.x().trim();
            String pos2Y = this.pos2Field.y().trim();
            String pos2Z = this.pos2Field.z().trim();
            boolean hasPos2 = !pos2X.isEmpty() || !pos2Y.isEmpty() || !pos2Z.isEmpty();
            Integer px2 = null;
            Integer py2 = null;
            Integer pz2 = null;
            if (hasPos2) {
               if (pos2X.isEmpty() || pos2Y.isEmpty() || pos2Z.isEmpty()) {
                  this.setErrorMessage(UIText.of("ponderer.ui." + this.stepType + ".error.partial_to"));
                  return null;
               }

               px2 = this.parseInt(pos2X, "To X");
               py2 = this.parseInt(pos2Y, "To Y");
               pz2 = this.parseInt(pos2Z, "To Z");
               if (px2 == null || py2 == null || pz2 == null) {
                  return null;
               }
            }

            if (hasPos2 && !hasPos1) {
               this.setErrorMessage(UIText.of("ponderer.ui." + this.stepType + ".error.partial_from"));
               return null;
            }

            DslScene.DslStep s = new DslScene.DslStep();
            s.type = this.stepType;
            String link = this.linkIdField.getValue().trim();
            if (!link.isEmpty()) {
               s.linkId = link;
            }

            s.duration = Math.max(0, duration);
            if (hasPos1) {
               s.blockPos = List.of(px, py, pz);
            }

            if (hasPos2) {
               s.blockPos2 = List.of(px2, py2, pz2);
            }

            if (this.rotationMode) {
               s.rotX = x.floatValue();
               s.rotY = y.floatValue();
               s.rotZ = z.floatValue();
            } else {
               s.offset = List.of(x, y, z);
            }

            return s;
         } else {
            this.setErrorMessage(UIText.of("ponderer.ui." + this.stepType + ".error.partial_from"));
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   private Integer parseOptionalInt(String raw, String label) {
      String trimmed = raw == null ? "" : raw.trim();
      return trimmed.isEmpty() ? null : this.parseInt(trimmed, label);
   }
}

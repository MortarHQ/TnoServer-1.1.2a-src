package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class SelectionOperationScreen extends AbstractStepEditorScreen {
   private final String stepType;
   private final boolean withDirection;
   private final boolean withLinkId;
   private final boolean withDuration;
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private final StepTextFieldHandle linkIdField = new StepTextFieldHandle("linkId");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");
   private final StepTextFieldHandle intervalField = new StepTextFieldHandle("entranceInterval");
   private int directionIndex = 0;
   private int entranceAnimationIndex = 0;
   private boolean smartDisplay = true;
   private final FieldBinding<Integer> directionBinding = FieldBindings.integer("direction", () -> this.directionIndex, value -> this.directionIndex = value);
   private final FieldBinding<Integer> entranceAnimationBinding = FieldBindings.integer(
      "entranceAnimation", () -> this.entranceAnimationIndex, value -> this.entranceAnimationIndex = value
   );
   private final FieldBinding<Boolean> smartDisplayBinding = FieldBindings.bool(
      "smartDisplay", () -> this.smartDisplay, value -> this.smartDisplay = Boolean.TRUE.equals(value)
   );

   public SelectionOperationScreen(String stepType, boolean withDirection, boolean withLinkId, DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      this(stepType, withDirection, withLinkId, false, scene, sceneIndex, parent);
   }

   public SelectionOperationScreen(
      String stepType, boolean withDirection, boolean withLinkId, boolean withDuration, DslScene scene, int sceneIndex, SceneEditorScreen parent
   ) {
      super(Component.translatable("ponderer.ui." + stepType + ".add"), scene, sceneIndex, parent);
      this.stepType = stepType;
      this.withDirection = withDirection;
      this.withLinkId = withLinkId;
      this.withDuration = withDuration;
   }

   public SelectionOperationScreen(
      String stepType,
      boolean withDirection,
      boolean withLinkId,
      DslScene scene,
      int sceneIndex,
      SceneEditorScreen parent,
      int editIndex,
      DslScene.DslStep step
   ) {
      this(stepType, withDirection, withLinkId, false, scene, sceneIndex, parent, editIndex, step);
   }

   public SelectionOperationScreen(
      String stepType,
      boolean withDirection,
      boolean withLinkId,
      boolean withDuration,
      DslScene scene,
      int sceneIndex,
      SceneEditorScreen parent,
      int editIndex,
      DslScene.DslStep step
   ) {
      super(Component.translatable("ponderer.ui." + stepType + ".edit"), scene, sceneIndex, parent, editIndex, step);
      this.stepType = stepType;
      this.withDirection = withDirection;
      this.withLinkId = withLinkId;
      this.withDuration = withDuration;
   }

   @Override
   protected void configureFormState(List<SnapshotParticipant> participants) {
      if (this.withDirection) {
         participants.add(this.directionBinding);
      }

      if (this.supportsEntranceAnimation()) {
         participants.add(this.entranceAnimationBinding);
         participants.add(this.smartDisplayBinding);
      }
   }

   private boolean supportsEntranceAnimation() {
      return "show_section_and_merge".equals(this.stepType);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui." + this.stepType);
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.xyz(
            this.posField,
            "ponderer.ui." + this.stepType + ".pos_from",
            "ponderer.ui." + this.stepType + ".pos_from.tooltip",
            "X",
            "Y",
            "Z",
            FieldDecorators.pointPick(PickState.TargetField.POS1)
         )
      );
      entries.add(
         FieldSpecs.xyz(
            this.pos2Field,
            "ponderer.ui." + this.stepType + ".pos_to",
            "ponderer.ui." + this.stepType + ".pos_to.tooltip",
            "X",
            "Y",
            "Z",
            FieldDecorators.pointPick(PickState.TargetField.POS2)
         )
      );
      if (this.supportsEntranceAnimation()) {
         entries.add(
            FieldSpecs.cycle(
               this.entranceAnimationBinding,
               "ponderer.ui.entrance_animation",
               "ponderer.ui.entrance_animation.tooltip",
               140,
               SelectionAnimationOptions.ENTRANCE_ANIMATIONS.length,
               this::updateTickFieldsEnabledState,
               () -> SelectionAnimationOptions.entranceAnimationLabel(SelectionAnimationOptions.ENTRANCE_ANIMATIONS[this.entranceAnimationIndex]),
               () -> 16777215
            )
         );
      }

      if (this.withDirection) {
         entries.add(
            FieldSpecs.cycle(
               this.directionBinding,
               "ponderer.ui." + this.stepType + ".direction",
               "ponderer.ui." + this.stepType + ".direction.tooltip",
               140,
               SelectionAnimationOptions.DIRECTIONS.length,
               () -> {},
               () -> SelectionAnimationOptions.optionLabel("ponderer.ui.show_controls.direction", SelectionAnimationOptions.DIRECTIONS[this.directionIndex]),
               () -> 16777215
            )
         );
      }

      if (this.withLinkId) {
         entries.add(FieldSpecs.text(this.linkIdField, "ponderer.ui." + this.stepType + ".link", "ponderer.ui." + this.stepType + ".link.tooltip", "", 140));
      }

      if (this.withDuration) {
         entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.duration.tooltip.section_animation", "20", 60));
      }

      if (this.supportsEntranceAnimation()) {
         entries.add(FieldSpecs.ticksNumber(this.intervalField, "ponderer.ui.entrance_interval", "ponderer.ui.entrance_interval.tooltip", "1", 60));
         entries.add(FieldSpecs.toggle(this.smartDisplayBinding, "ponderer.ui.smart_display", "ponderer.ui.smart_display.tooltip"));
         this.updateTickFieldsEnabledState();
      }
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
      }

      if (this.withDirection && step.direction != null) {
         String normalized = SelectionAnimationOptions.normalizeDirection(step.direction);

         for (int i = 0; i < SelectionAnimationOptions.DIRECTIONS.length; i++) {
            if (SelectionAnimationOptions.DIRECTIONS[i].equals(normalized)) {
               this.directionIndex = i;
               break;
            }
         }
      }

      if (this.withLinkId && step.linkId != null) {
         this.linkIdField.setValue(step.linkId);
      }

      if (this.supportsEntranceAnimation() && step.entranceAnimation != null && !step.entranceAnimation.isBlank()) {
         String normalized = SelectionAnimationOptions.normalizeEntranceAnimation(step.entranceAnimation);

         for (int i = 0; i < SelectionAnimationOptions.ENTRANCE_ANIMATIONS.length; i++) {
            if (SelectionAnimationOptions.ENTRANCE_ANIMATIONS[i].equals(normalized)) {
               this.entranceAnimationIndex = i;
               break;
            }
         }
      }

      if (this.withDuration) {
         if (step.entranceDuration != null) {
            this.durationField.setValue(String.valueOf(step.entranceDuration));
         } else if (step.duration != null) {
            this.durationField.setValue(String.valueOf(step.duration));
         }
      }

      if (this.supportsEntranceAnimation() && step.entranceInterval != null) {
         this.intervalField.setValue(String.valueOf(step.entranceInterval));
      }

      if (this.supportsEntranceAnimation() && step.smartDisplay != null) {
         this.smartDisplay = step.smartDisplay;
      }

      this.updateTickFieldsEnabledState();
   }

   private void updateTickFieldsEnabledState() {
      if (this.supportsEntranceAnimation()) {
         String mode = SelectionAnimationOptions.ENTRANCE_ANIMATIONS[this.entranceAnimationIndex];
         boolean durationEnabled = !"none".equals(mode);
         boolean intervalEnabled = !"none".equals(mode) && !"simultaneous".equals(mode);
         if (this.durationField.widget() != null) {
            this.durationField.widget().active = durationEnabled;
         }

         if (this.intervalField.widget() != null) {
            this.intervalField.widget().active = intervalEnabled;
         }
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
      FormParsers.ParseResult<FormParsers.IntRange> range = FormParsers.intRange(
         this.posField, this.pos2Field, UIText.of("ponderer.ui." + this.stepType + ".error.partial_to")
      );
      if (range.failed()) {
         this.setErrorMessage(range.errorMessage());
         return null;
      }

      DslScene.DslStep step = new DslScene.DslStep();
      step.type = this.stepType;
      step.blockPos = range.value().from().toList();
      if (range.value().to() != null) {
         step.blockPos2 = range.value().to().toList();
      }

      if (this.withDirection) {
         step.direction = SelectionAnimationOptions.DIRECTIONS[this.directionIndex];
      }

      if (this.withLinkId) {
         String linkId = this.linkIdField.getValue().trim();
         if (!linkId.isEmpty()) {
            step.linkId = linkId;
         }
      }

      if (this.supportsEntranceAnimation()) {
         String entranceAnimation = SelectionAnimationOptions.ENTRANCE_ANIMATIONS[this.entranceAnimationIndex];
         if ("none".equals(entranceAnimation)) {
            step.entranceAnimation = "none";
            step.duration = 0;
         } else {
            step.entranceAnimation = entranceAnimation;
            step.entranceDuration = Math.max(0, this.parseIntOr(this.durationField.getValue(), 20));
            step.entranceInterval = Math.max(0, this.parseIntOr(this.intervalField.getValue(), 1));
         }

         step.smartDisplay = this.smartDisplay;
      }

      if (this.withDuration && step.entranceAnimation == null) {
         step.duration = Math.max(0, this.parseIntOr(this.durationField.getValue(), 20));
      }

      return step;
   }
}

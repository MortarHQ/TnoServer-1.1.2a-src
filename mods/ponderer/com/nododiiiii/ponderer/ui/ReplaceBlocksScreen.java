package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public class ReplaceBlocksScreen extends AbstractStepEditorScreen {
   private final StepTextFieldHandle blockField = new StepTextFieldHandle("block");
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private final KeyValueListState blockProperties = new KeyValueListState("prop", 1);
   private boolean spawnParticles = true;
   private final FieldBinding<Boolean> spawnParticlesBinding = FieldBindings.bool(
      "particles", () -> this.spawnParticles, value -> this.spawnParticles = Boolean.TRUE.equals(value)
   );

   public ReplaceBlocksScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.replace_blocks.add"), scene, sceneIndex, parent);
   }

   public ReplaceBlocksScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.replace_blocks.edit"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected void configureFormState(List<SnapshotParticipant> participants) {
      participants.add(this.blockProperties);
      participants.add(this.spawnParticlesBinding);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.replace_blocks");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.blockField,
            "ponderer.ui.replace_blocks",
            "ponderer.ui.replace_blocks.tooltip",
            UIText.of("ponderer.ui.replace_blocks.hint"),
            124,
            FieldDecorators.jei(IdFieldMode.BLOCK)
         )
      );
      entries.add(FieldSpecs.blockProperties(this.blockProperties, "ponderer.ui.block_properties", "ponderer.ui.block_properties.tooltip"));
      entries.add(
         FieldSpecs.xyz(
            this.posField,
            "ponderer.ui.replace_blocks.pos_from",
            "ponderer.ui.replace_blocks.pos_from.tooltip",
            "X",
            "Y",
            "Z",
            FieldDecorators.pointPick(PickState.TargetField.POS1)
         )
      );
      entries.add(
         FieldSpecs.xyz(
            this.pos2Field,
            "ponderer.ui.replace_blocks.pos_to",
            "ponderer.ui.replace_blocks.pos_to.tooltip",
            "X",
            "Y",
            "Z",
            FieldDecorators.pointPick(PickState.TargetField.POS2)
         )
      );
      entries.add(FieldSpecs.toggle(this.spawnParticlesBinding, "ponderer.ui.replace_blocks.particles", "ponderer.ui.replace_blocks.particles.tooltip"));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.block != null) {
         this.blockField.setValue(step.block);
      }

      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
      }

      this.blockProperties.replaceFromMap(step.blockProperties);
      if (step.spawnParticles != null) {
         this.spawnParticles = step.spawnParticles;
      }
   }

   @Override
   protected String getStepType() {
      return "replace_blocks";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      FormParsers.ParseResult<String> blockId = FormParsers.registryId(
         this.blockField.getValue(),
         UIText.of("ponderer.ui.replace_blocks.error.required"),
         UIText.of("ponderer.ui.replace_blocks.error.invalid_id"),
         value -> UIText.of("ponderer.ui.replace_blocks.error.unknown", value),
         BuiltInRegistries.BLOCK
      );
      if (blockId.failed()) {
         this.setErrorMessage(blockId.errorMessage());
         return null;
      }

      FormParsers.ParseResult<FormParsers.IntRange> range = FormParsers.intRange(
         this.posField, this.pos2Field, UIText.of("ponderer.ui.replace_blocks.error.partial_to")
      );
      if (range.failed()) {
         this.setErrorMessage(range.errorMessage());
         return null;
      }

      DslScene.DslStep step = new DslScene.DslStep();
      step.type = "replace_blocks";
      step.block = blockId.value();
      step.blockProperties = this.blockProperties.toFilteredMap();
      step.blockPos = range.value().from().toList();
      if (range.value().to() != null) {
         step.blockPos2 = range.value().to().toList();
      }

      if (!this.spawnParticles) {
         step.spawnParticles = false;
      }

      return step;
   }
}

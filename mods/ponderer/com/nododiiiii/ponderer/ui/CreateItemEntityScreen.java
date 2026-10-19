package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.PondererClientCommands;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CreateItemEntityScreen extends AbstractStepEditorScreen {
   private final StepTextFieldHandle itemField = new StepTextFieldHandle("item");
   private final StepTextFieldHandle countField = new StepTextFieldHandle("count");
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle motionField = new StepXyzFieldHandle("motion");
   private final StepTextFieldHandle nbtField = new StepTextFieldHandle("nbt");

   public CreateItemEntityScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.create_item_entity.add"), scene, sceneIndex, parent);
   }

   public CreateItemEntityScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.create_item_entity.edit"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.create_item_entity");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.itemField,
            "ponderer.ui.create_item_entity.item",
            "ponderer.ui.create_item_entity.item.tooltip",
            UIText.of("ponderer.ui.create_item_entity.hint"),
            124,
            FieldDecorators.jei(IdFieldMode.ITEM),
            FieldDecorators.heldItem(stack -> {
               this.itemField.setValue(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
               CompoundTag tag = PondererClientCommands.extractStackNbtFilter(stack);
               if (tag != null && !tag.isEmpty()) {
                  this.nbtField.setValue(tag.toString());
               }
            })
         )
      );
      entries.add(FieldSpecs.number(this.countField, "ponderer.ui.create_item_entity.count", "ponderer.ui.create_item_entity.count.tooltip", "1", 50, null));
      entries.add(
         FieldSpecs.xyz(this.posField, "ponderer.ui.create_item_entity.pos", "ponderer.ui.create_item_entity.pos.tooltip", PickState.TargetField.POS1, true)
      );
      entries.add(FieldSpecs.xyz(this.motionField, "ponderer.ui.create_item_entity.motion", "ponderer.ui.create_item_entity.motion.tooltip"));
      entries.add(
         FieldSpecs.text(
            this.nbtField,
            "ponderer.ui.create_item_entity.nbt",
            "ponderer.ui.create_item_entity.nbt.tooltip",
            "{PickupDelay:40s}",
            124,
            FieldDecorators.nbtPick("nbt")
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.item != null) {
         this.itemField.setValue(step.item);
      }

      if (step.count != null) {
         this.countField.setValue(String.valueOf(step.count));
      }

      if (step.pos != null && step.pos.size() >= 3) {
         this.posField.setValue(step.pos.get(0), step.pos.get(1), step.pos.get(2));
      }

      if (step.motion != null && step.motion.size() >= 3) {
         this.motionField.setValue(step.motion.get(0), step.motion.get(1), step.motion.get(2));
      }

      if (step.nbt != null) {
         this.nbtField.setValue(step.nbt);
      }
   }

   @Override
   protected String getStepType() {
      return "create_item_entity";
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      this.restoreNbtPickNotice(snapshot);
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      String itemId = this.itemField.getValue().trim();
      if (itemId.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.create_item_entity.error.required"));
         return null;
      }

      ResourceLocation itemLoc = ResourceLocation.tryParse(itemId);
      if (itemLoc == null) {
         this.setErrorMessage(UIText.of("ponderer.ui.create_item_entity.error.invalid_id"));
         return null;
      }

      if (BuiltInRegistries.ITEM.getOptional(itemLoc).isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.create_item_entity.error.unknown", itemId));
         return null;
      }

      Double px = this.parseDouble(this.posField.x(), "X");
      Double py = this.parseDouble(this.posField.y(), "Y");
      Double pz = this.parseDouble(this.posField.z(), "Z");
      if (px != null && py != null && pz != null) {
         Double mx = this.motionField.x().trim().isEmpty()
            ? 0.0
            : this.parseDouble(this.motionField.x(), UIText.of("ponderer.ui.create_item_entity.motion") + " X");
         Double my = this.motionField.y().trim().isEmpty()
            ? 0.0
            : this.parseDouble(this.motionField.y(), UIText.of("ponderer.ui.create_item_entity.motion") + " Y");
         Double mz = this.motionField.z().trim().isEmpty()
            ? 0.0
            : this.parseDouble(this.motionField.z(), UIText.of("ponderer.ui.create_item_entity.motion") + " Z");
         if (mx != null && my != null && mz != null) {
            DslScene.DslStep s = new DslScene.DslStep();
            s.type = "create_item_entity";
            s.item = itemId;
            s.count = Math.max(1, this.parseIntOr(this.countField.getValue(), 1));
            s.pos = List.of(px, py, pz);
            s.motion = List.of(mx, my, mz);
            String nbt = this.nbtField.getValue().trim();
            if (!nbt.isEmpty()) {
               try {
                  TagParser.parseTag(nbt);
               } catch (Exception e) {
                  this.setErrorMessage(UIText.of("ponderer.ui.modify_block_entity_nbt.error.invalid"));
                  return null;
               }

               s.nbt = nbt;
            }

            return s;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}

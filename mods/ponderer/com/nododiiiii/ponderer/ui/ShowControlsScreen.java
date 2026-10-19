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

public class ShowControlsScreen extends AbstractStepEditorScreen {
   private static final String[] DIRECTIONS = new String[]{"down", "up", "left", "right"};
   private static final String[] ACTIONS = new String[]{"", "left", "right", "scroll"};
   private final StepXyzFieldHandle pointField = new StepXyzFieldHandle("point");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");
   private final StepTextFieldHandle itemField = new StepTextFieldHandle("item");
   private final StepTextFieldHandle nbtField = new StepTextFieldHandle("nbt");
   private int dirIndex = 0;
   private int actionIndex = 0;
   private boolean whileSneaking = false;
   private boolean whileCTRL = false;

   public ShowControlsScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.show_controls"), scene, sceneIndex, parent);
   }

   public ShowControlsScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.show_controls"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.show_controls");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(FieldSpecs.xyz(this.pointField, "ponderer.ui.point", "ponderer.ui.show_controls.point.tooltip", PickState.TargetField.POINT, true));
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.show_controls.direction",
            "ponderer.ui.show_controls.direction.tooltip",
            100,
            () -> this.dirIndex = (this.dirIndex + 1) % DIRECTIONS.length,
            () -> this.optionLabel("ponderer.ui.show_controls.direction", DIRECTIONS[this.dirIndex])
         )
      );
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.duration.tooltip.controls", "60", 50));
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.show_controls.action",
            "ponderer.ui.show_controls.action.tooltip",
            100,
            () -> this.actionIndex = (this.actionIndex + 1) % ACTIONS.length,
            () -> this.actionIndex == 0 ? UIText.of("ponderer.ui.none") : this.optionLabel("ponderer.ui.show_controls.action", ACTIONS[this.actionIndex])
         )
      );
      entries.add(
         FieldSpecs.text(
            this.itemField,
            "ponderer.ui.show_controls.item",
            "ponderer.ui.show_controls.item.tooltip",
            UIText.of("ponderer.ui.show_controls.item.hint"),
            124,
            FieldDecorators.jei(IdFieldMode.INGREDIENT),
            FieldDecorators.heldItem(stack -> {
               String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
               this.itemField.setValue(itemId);
               CompoundTag tag = PondererClientCommands.extractStackNbtFilter(stack);
               if (tag != null && !tag.isEmpty()) {
                  this.nbtField.setValue(tag.toString());
               } else {
                  this.nbtField.setValue("");
               }
            })
         )
      );
      entries.add(
         FieldSpecs.text(this.nbtField, "ponderer.ui.show_controls.nbt", "ponderer.ui.show_controls.nbt.tooltip", "{}", 124, FieldDecorators.nbtPick("nbt"))
      );
      entries.add(
         FieldSpecs.toggle(
            "ponderer.ui.show_controls.sneaking",
            "ponderer.ui.show_controls.sneaking.tooltip",
            () -> this.whileSneaking,
            () -> this.whileSneaking = !this.whileSneaking
         )
      );
      entries.add(
         FieldSpecs.toggle(
            "ponderer.ui.show_controls.ctrl", "ponderer.ui.show_controls.ctrl.tooltip", () -> this.whileCTRL, () -> this.whileCTRL = !this.whileCTRL
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.point != null && step.point.size() >= 3) {
         this.pointField.setValue(step.point.get(0), step.point.get(1), step.point.get(2));
      }

      if (step.direction != null) {
         for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equalsIgnoreCase(step.direction)) {
               this.dirIndex = i;
               break;
            }
         }
      }

      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
      }

      if (step.action != null) {
         for (int i = 0; i < ACTIONS.length; i++) {
            if (ACTIONS[i].equalsIgnoreCase(step.action)) {
               this.actionIndex = i;
               break;
            }
         }
      }

      if (step.item != null) {
         String itemValue = step.item;
         String nbtValue = step.nbt;
         if (nbtValue == null || nbtValue.isBlank()) {
            int brace = itemValue.indexOf(123);
            if (brace >= 0) {
               nbtValue = itemValue.substring(brace).trim();
               itemValue = itemValue.substring(0, brace).trim();
            }
         }

         this.itemField.setValue(itemValue);
         if (nbtValue != null) {
            this.nbtField.setValue(nbtValue);
         }
      } else if (step.nbt != null) {
         this.nbtField.setValue(step.nbt);
      }

      this.whileSneaking = Boolean.TRUE.equals(step.whileSneaking);
      this.whileCTRL = Boolean.TRUE.equals(step.whileCTRL);
   }

   private String optionLabel(String prefix, String value) {
      String key = prefix + "." + value;
      String translated = UIText.of(key);
      return key.equals(translated) ? value : translated;
   }

   @Override
   protected String getStepType() {
      return "show_controls";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("dirIndex", String.valueOf(this.dirIndex));
      snapshot.put("actionIndex", String.valueOf(this.actionIndex));
      snapshot.put("whileSneaking", String.valueOf(this.whileSneaking));
      snapshot.put("whileCTRL", String.valueOf(this.whileCTRL));
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("dirIndex")) {
         try {
            this.dirIndex = Integer.parseInt(snapshot.get("dirIndex"));
         } catch (NumberFormatException var4) {
         }
      }

      if (snapshot.containsKey("actionIndex")) {
         try {
            this.actionIndex = Integer.parseInt(snapshot.get("actionIndex"));
         } catch (NumberFormatException var3) {
         }
      }

      if (snapshot.containsKey("whileSneaking")) {
         this.whileSneaking = Boolean.parseBoolean(snapshot.get("whileSneaking"));
      }

      if (snapshot.containsKey("whileCTRL")) {
         this.whileCTRL = Boolean.parseBoolean(snapshot.get("whileCTRL"));
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      DslScene.DslStep s = new DslScene.DslStep();
      s.type = "show_controls";
      Double px = this.parseDouble(this.pointField.x(), "X");
      Double py = this.parseDouble(this.pointField.y(), "Y");
      Double pz = this.parseDouble(this.pointField.z(), "Z");
      if (px != null && py != null && pz != null) {
         s.point = List.of(px, py, pz);
         s.direction = DIRECTIONS[this.dirIndex];
         s.duration = this.parseIntOr(this.durationField.getValue(), 60);
         if (this.actionIndex > 0) {
            s.action = ACTIONS[this.actionIndex];
         }

         String item = this.itemField.getValue().trim();
         if (!item.isEmpty()) {
            s.item = item;
         }

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

         if (this.whileSneaking) {
            s.whileSneaking = true;
         }

         if (this.whileCTRL) {
            s.whileCTRL = true;
         }

         return s;
      } else {
         return null;
      }
   }
}

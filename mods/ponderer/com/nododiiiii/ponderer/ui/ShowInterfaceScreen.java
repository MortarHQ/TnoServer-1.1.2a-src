package com.nododiiiii.ponderer.ui;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;

public class ShowInterfaceScreen extends AbstractStepEditorScreen {
   private static final String NBT_SNAPSHOT_KEY = "show_interface_nbt";
   private static final BlockPos SANITIZED_CONTEXT_POS = BlockPos.ZERO;
   private final StepTextFieldHandle blockField = new StepTextFieldHandle("block");
   private final KeyValueListState capturedBlockProperties = new KeyValueListState("prop", 0);
   @Nullable
   private List<Integer> contextPos;
   @Nullable
   private String contextFace;
   @Nullable
   private List<Double> contextHit;
   @Nullable
   private Boolean contextInside;
   @Nullable
   private String capturedNbt;
   private boolean enableNbt = true;
   private final FieldBinding<Boolean> enableNbtBinding = FieldBindings.bool(
      "enable_nbt", () -> this.enableNbt, value -> this.enableNbt = Boolean.TRUE.equals(value)
   );

   public ShowInterfaceScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.show_interface"), scene, sceneIndex, parent);
   }

   public ShowInterfaceScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.show_interface"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected void configureFormState(List<SnapshotParticipant> participants) {
      participants.add(this.capturedBlockProperties);
      participants.add(this.enableNbtBinding);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.show_interface");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.blockField,
            "ponderer.ui.show_interface.block",
            "ponderer.ui.show_interface.block.tooltip",
            UIText.of("ponderer.ui.show_interface.block.hint"),
            124,
            entry -> {
               entry.field().setEditable(false);
               entry.field().setCanLoseFocus(true);
            },
            FieldDecorators.blockPick("show_interface_nbt")
         )
      );
      entries.add(FieldSpecs.toggle(this.enableNbtBinding, "ponderer.ui.show_interface.enable_nbt", "ponderer.ui.show_interface.enable_nbt.tooltip"));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.block != null) {
         this.blockField.setValue(step.block);
      }

      this.contextPos = step.blockPos;
      this.contextFace = step.direction;
      this.contextHit = step.point;
      this.contextInside = step.whileSneaking;
      this.capturedNbt = step.nbt;
      this.capturedBlockProperties.replaceFromMap(step.blockProperties);
      this.enableNbt = !Boolean.FALSE.equals(step.enableNbt);
   }

   @Override
   protected String getStepType() {
      return "show_interface";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      if (this.contextPos != null && this.contextPos.size() >= 3) {
         snapshot.put("ctx_pos", this.contextPos.get(0) + "," + this.contextPos.get(1) + "," + this.contextPos.get(2));
      }

      if (this.contextFace != null) {
         snapshot.put("ctx_face", this.contextFace);
      }

      if (this.contextHit != null && this.contextHit.size() >= 3) {
         snapshot.put("ctx_hit", this.contextHit.get(0) + "," + this.contextHit.get(1) + "," + this.contextHit.get(2));
      }

      if (this.contextInside != null) {
         snapshot.put("ctx_inside", String.valueOf(this.contextInside));
      }

      if (this.capturedNbt != null) {
         snapshot.put("show_interface_nbt", this.capturedNbt);
      }
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("_block_id")) {
         this.blockField.setValue(snapshot.get("_block_id"));
      }

      if (snapshot.containsKey("_block_pos")) {
         FormParsers.Int3 pos = FormParsers.parseInt3(snapshot.get("_block_pos"));
         this.contextPos = pos == null ? null : pos.toList();
      } else if (snapshot.containsKey("ctx_pos")) {
         FormParsers.Int3 pos = FormParsers.parseInt3(snapshot.get("ctx_pos"));
         this.contextPos = pos == null ? null : pos.toList();
      }

      if (snapshot.containsKey("_block_face")) {
         this.contextFace = snapshot.get("_block_face");
      } else if (snapshot.containsKey("ctx_face")) {
         this.contextFace = snapshot.get("ctx_face");
      }

      if (snapshot.containsKey("_block_hit")) {
         FormParsers.Double3 hit = FormParsers.parseDouble3(snapshot.get("_block_hit"));
         this.contextHit = hit == null ? null : hit.toList();
      } else if (snapshot.containsKey("ctx_hit")) {
         FormParsers.Double3 hit = FormParsers.parseDouble3(snapshot.get("ctx_hit"));
         this.contextHit = hit == null ? null : hit.toList();
      }

      if (snapshot.containsKey("_block_inside")) {
         this.contextInside = FormParsers.parseBoolean(snapshot.get("_block_inside"));
      } else if (snapshot.containsKey("ctx_inside")) {
         this.contextInside = FormParsers.parseBoolean(snapshot.get("ctx_inside"));
      }

      if (snapshot.containsKey("show_interface_nbt")) {
         this.capturedNbt = snapshot.get("show_interface_nbt");
      }

      this.restoreNbtPickNotice(snapshot);
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      String blockId = this.blockField.getValue().trim();
      if (blockId.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", UIText.of("ponderer.ui.show_interface.block")));
         return null;
      }

      if (this.contextPos != null && this.contextPos.size() >= 3) {
         DslScene.DslStep step = new DslScene.DslStep();
         step.type = "show_interface";
         step.block = blockId;
         step.duration = null;
         step.blockPos = List.of(SANITIZED_CONTEXT_POS.getX(), SANITIZED_CONTEXT_POS.getY(), SANITIZED_CONTEXT_POS.getZ());
         if (this.contextFace != null && !this.contextFace.isBlank()) {
            step.direction = this.contextFace;
         }

         if (this.contextHit != null && this.contextHit.size() >= 3) {
            step.point = List.of(
               this.contextHit.get(0) - this.contextPos.get(0).intValue(),
               this.contextHit.get(1) - this.contextPos.get(1).intValue(),
               this.contextHit.get(2) - this.contextPos.get(2).intValue()
            );
         }

         if (this.contextInside != null) {
            step.whileSneaking = this.contextInside;
         }

         step.enableNbt = this.enableNbt;
         if (this.enableNbt && this.capturedNbt != null && !this.capturedNbt.isBlank()) {
            step.nbt = sanitizeCapturedNbt(this.capturedNbt, this.contextPos);
         }

         Map<String, String> props = this.capturedBlockProperties.toFilteredMap();
         if (props != null && !props.isEmpty()) {
            step.blockProperties = props;
         }

         return step;
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.show_interface.error.no_context"));
         return null;
      }
   }

   private static String sanitizeCapturedNbt(String rawNbt, List<Integer> sourcePos) {
      if (sourcePos != null && sourcePos.size() >= 3) {
         try {
            CompoundTag parsed = TagParser.parseTag(rawNbt);
            remapEmbeddedPositions(parsed, -sourcePos.get(0), -sourcePos.get(1), -sourcePos.get(2));
            parsed.putInt("x", SANITIZED_CONTEXT_POS.getX());
            parsed.putInt("y", SANITIZED_CONTEXT_POS.getY());
            parsed.putInt("z", SANITIZED_CONTEXT_POS.getZ());
            return parsed.toString();
         } catch (CommandSyntaxException ignored) {
            return rawNbt;
         }
      } else {
         return rawNbt;
      }
   }

   private static int remapEmbeddedPositions(CompoundTag tag, int dx, int dy, int dz) {
      int remapped = 0;
      if (tag.contains("x") && tag.contains("y") && tag.contains("z")) {
         tag.putInt("x", tag.getInt("x") + dx);
         tag.putInt("y", tag.getInt("y") + dy);
         tag.putInt("z", tag.getInt("z") + dz);
         remapped++;
      }

      if (tag.contains("X") && tag.contains("Y") && tag.contains("Z")) {
         tag.putInt("X", tag.getInt("X") + dx);
         tag.putInt("Y", tag.getInt("Y") + dy);
         tag.putInt("Z", tag.getInt("Z") + dz);
         remapped++;
      }

      for (String key : tag.getAllKeys()) {
         if (tag.get(key) instanceof CompoundTag nested) {
            remapped += remapEmbeddedPositions(nested, dx, dy, dz);
         } else if (tag.get(key) instanceof ListTag listTag) {
            remapped += remapEmbeddedPositionsInList(listTag, dx, dy, dz);
         }
      }

      return remapped;
   }

   private static int remapEmbeddedPositionsInList(ListTag listTag, int dx, int dy, int dz) {
      int remapped = 0;

      for (int i = 0; i < listTag.size(); i++) {
         if (listTag.get(i) instanceof CompoundTag nested) {
            remapped += remapEmbeddedPositions(nested, dx, dy, dz);
         } else if (listTag.get(i) instanceof ListTag nestedList) {
            remapped += remapEmbeddedPositionsInList(nestedList, dx, dy, dz);
         }
      }

      return remapped;
   }
}

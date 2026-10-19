package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.PondererClientCommands;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.ConfirmationScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;

public class TriggerEditorScreen extends AbstractSceneEditorFormScreen {
   private static final String[] TRIGGER_MODES = new String[]{"none", "structure", "coordinate"};
   private static final String[] FREQ_VALUES = new String[]{null, "always", "first_time", "until_read"};
   private static final String[] FREQ_KEYS = new String[]{"off", "always", "first_time", "until_read"};
   private final StepTextFieldHandle itemField = new StepTextFieldHandle("itemId");
   private final StepTextFieldHandle itemNbtField = new StepTextFieldHandle("itemNbt");
   private final StepTextFieldHandle titleTextField = new StepTextFieldHandle("titleText");
   private final StepTextFieldHandle subtitleTextField = new StepTextFieldHandle("subtitleText");
   private final StepTextFieldHandle structureField = new StepTextFieldHandle("structure");
   private final StepXyzFieldHandle coord1Field = new StepXyzFieldHandle("coord1_x", "coord1_y", "coord1_z");
   private final StepXyzFieldHandle coord2Field = new StepXyzFieldHandle("coord2_x", "coord2_y", "coord2_z");
   private int triggerModeIndex = 0;
   private int autoFreqIndex = 0;
   private int titleFreqIndex = 0;
   private int subtitleFreqIndex = 0;
   private boolean pendingItemDuplicateConfirm = false;
   @Nullable
   private String pendingStructureSelection = null;
   private final FieldBinding<Integer> triggerModeBinding = FieldBindings.integer(
      "triggerMode", () -> this.triggerModeIndex, value -> this.triggerModeIndex = value
   );
   private final FieldBinding<Integer> autoFreqBinding = FieldBindings.integer("autoFreq", () -> this.autoFreqIndex, value -> this.autoFreqIndex = value);
   private final FieldBinding<Integer> titleFreqBinding = FieldBindings.integer("titleFreq", () -> this.titleFreqIndex, value -> this.titleFreqIndex = value);
   private final FieldBinding<Integer> subtitleFreqBinding = FieldBindings.integer(
      "subtitleFreq", () -> this.subtitleFreqIndex, value -> this.subtitleFreqIndex = value
   );

   public TriggerEditorScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(scene, sceneIndex, parent, "ponderer.ui.scope.editor", "ponderer.ui.step_editor", 300, JeiCompat::setActiveScreen);
      if ("structure".equals(scene.triggerMode)) {
         this.triggerModeIndex = 1;
      } else if ("coordinate".equals(scene.triggerMode)) {
         this.triggerModeIndex = 2;
      }

      this.autoFreqIndex = freqToIndex(scene.hintAuto);
      this.titleFreqIndex = freqToIndex(scene.hintTitle);
      this.subtitleFreqIndex = freqToIndex(scene.hintSubtitle);
      if (this.autoFreqIndex == 0
         && this.titleFreqIndex == 0
         && this.subtitleFreqIndex == 0
         && scene.hintAuto == null
         && scene.hintTitle == null
         && scene.hintSubtitle == null
         && scene.hintStyle != null) {
         String legacyFreq = resolveLegacyFrequency(scene);
         if ("auto".equals(scene.hintStyle)) {
            this.autoFreqIndex = freqToIndex(legacyFreq);
         } else if ("title".equals(scene.hintStyle)) {
            this.titleFreqIndex = freqToIndex(legacyFreq);
         } else {
            this.subtitleFreqIndex = freqToIndex(legacyFreq);
         }
      }

      String currentItem = scene.items != null && !scene.items.isEmpty() ? scene.items.get(0) : "";
      this.itemField.setValue(currentItem);
      this.itemNbtField.setValue(scene.nbtFilter != null ? scene.nbtFilter : "");
      this.titleTextField.setValue(scene.hintTitleText != null ? scene.hintTitleText : "");
      this.subtitleTextField.setValue(scene.hintSubtitleText != null ? scene.hintSubtitleText : "");
      this.structureField.setValue(scene.triggerStructure != null ? scene.triggerStructure : "");
      if (scene.triggerCoord1 != null && scene.triggerCoord1.size() >= 3) {
         this.coord1Field.setValue(scene.triggerCoord1.get(0), scene.triggerCoord1.get(1), scene.triggerCoord1.get(2));
      } else {
         this.coord1Field.setValue(0, 0, 0);
      }

      if (scene.triggerCoord2 != null && scene.triggerCoord2.size() >= 3) {
         this.coord2Field.setValue(scene.triggerCoord2.get(0), scene.triggerCoord2.get(1), scene.triggerCoord2.get(2));
      } else {
         this.coord2Field.setValue(0, 0, 0);
      }
   }

   private static int freqToIndex(@Nullable String freq) {
      if ("always".equals(freq)) {
         return 1;
      } else if ("first_time".equals(freq)) {
         return 2;
      } else {
         return "until_read".equals(freq) ? 3 : 0;
      }
   }

   private static String resolveLegacyFrequency(DslScene scene) {
      if ("first_time".equals(scene.hintFrequency)) {
         return "first_time";
      } else if ("until_read".equals(scene.hintFrequency)) {
         return "until_read";
      } else {
         return Boolean.TRUE.equals(scene.onlyFirstTime) ? "first_time" : "always";
      }
   }

   public TriggerEditorScreen setPendingFormRestore(@Nullable Map<String, String> snapshot) {
      super.setPendingFormRestore(snapshot);
      return this;
   }

   @Override
   protected void configureFormState(List<SnapshotParticipant> participants) {
      participants.add(this.triggerModeBinding);
      participants.add(this.autoFreqBinding);
      participants.add(this.titleFreqBinding);
      participants.add(this.subtitleFreqBinding);
      participants.add(this.itemField);
      participants.add(this.itemNbtField);
      participants.add(this.titleTextField);
      participants.add(this.subtitleTextField);
      participants.add(this.structureField);
      participants.add(this.coord1Field);
      participants.add(this.coord2Field);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.trigger_editor");
   }

   @Override
   protected void configureActionButtons() {
      if (this.confirmButton != null) {
         this.confirmButton.withCallback(this::doConfirm);
      }
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      if (this.pendingStructureSelection != null) {
         this.structureField.setValue(this.pendingStructureSelection);
         this.pendingStructureSelection = null;
      }

      entries.add(
         FieldSpecs.text(
            this.itemField,
            "ponderer.ui.scene_desc.item_id",
            null,
            UIText.of("ponderer.ui.scene_desc.hint.item_id"),
            124,
            FieldDecorators.jei(IdFieldMode.ITEM),
            FieldDecorators.heldItem(this::applyHeldItem)
         )
      );
      entries.add(
         FieldSpecs.text(
            this.itemNbtField,
            "ponderer.ui.scene_desc.item_nbt",
            null,
            UIText.of("ponderer.ui.scene_desc.hint.item_nbt"),
            124,
            FieldDecorators.nbtPick("itemNbt")
         )
      );
      entries.add(
         FieldSpecs.cycle(
            this.triggerModeBinding,
            "ponderer.ui.trigger_editor.trigger_mode",
            "ponderer.ui.trigger_editor.trigger_mode.tooltip",
            100,
            TRIGGER_MODES.length,
            this::rebuildFormPreservingState,
            () -> UIText.of("ponderer.ui.trigger_editor.trigger_mode." + TRIGGER_MODES[this.triggerModeIndex]),
            () -> 16777215
         )
      );
      if (this.triggerModeIndex != 0) {
         entries.add(
            FieldSpecs.cycle(
               this.autoFreqBinding,
               "ponderer.ui.trigger_editor.hint_style.auto",
               "ponderer.ui.trigger_editor.hint_style.auto.tooltip",
               100,
               FREQ_VALUES.length,
               () -> {},
               () -> UIText.of("ponderer.ui.trigger_editor.hint_freq." + FREQ_KEYS[this.autoFreqIndex]),
               () -> this.autoFreqIndex != 0 ? 16733525 : 16777215
            )
         );
         entries.add(
            FieldSpecs.cycle(
               this.titleFreqBinding,
               "ponderer.ui.trigger_editor.hint_style.title",
               "ponderer.ui.trigger_editor.hint_style.title.tooltip",
               100,
               FREQ_VALUES.length,
               this::rebuildFormPreservingState,
               () -> UIText.of("ponderer.ui.trigger_editor.hint_freq." + FREQ_KEYS[this.titleFreqIndex]),
               () -> 16777215
            )
         );
         if (this.titleFreqIndex != 0) {
            entries.add(
               FieldSpecs.text(
                  this.titleTextField,
                  "ponderer.ui.trigger_editor.hint_text",
                  "ponderer.ui.trigger_editor.hint_text.tooltip",
                  UIText.of("ponderer.ui.trigger_editor.hint_text.placeholder"),
                  120
               )
            );
         }

         entries.add(
            FieldSpecs.cycle(
               this.subtitleFreqBinding,
               "ponderer.ui.trigger_editor.hint_style.subtitle",
               "ponderer.ui.trigger_editor.hint_style.subtitle.tooltip",
               100,
               FREQ_VALUES.length,
               this::rebuildFormPreservingState,
               () -> UIText.of("ponderer.ui.trigger_editor.hint_freq." + FREQ_KEYS[this.subtitleFreqIndex]),
               () -> 16777215
            )
         );
         if (this.subtitleFreqIndex != 0) {
            entries.add(
               FieldSpecs.text(
                  this.subtitleTextField,
                  "ponderer.ui.trigger_editor.hint_text",
                  "ponderer.ui.trigger_editor.hint_text.tooltip",
                  UIText.of("ponderer.ui.trigger_editor.hint_text.placeholder"),
                  120
               )
            );
         }

         String mode = TRIGGER_MODES[this.triggerModeIndex];
         if ("structure".equals(mode)) {
            entries.add(
               FieldSpecs.text(
                  this.structureField,
                  "ponderer.ui.trigger_editor.trigger_structure",
                  null,
                  UIText.of("ponderer.ui.trigger_editor.hint.trigger_structure"),
                  124,
                  FieldDecorators.textAction(20, this::openStructureList, () -> "L", () -> 16777215, UIText.of("ponderer.ui.scene_desc.structure_list_title"))
               )
            );
         } else if ("coordinate".equals(mode)) {
            entries.add(
               FieldSpecs.xyz(
                  this.coord1Field,
                  "ponderer.ui.trigger_editor.trigger_coord1",
                  null,
                  "X",
                  "Y",
                  "Z",
                  FieldDecorators.xyzAction(20, this::startCoordinatePick, () -> "+", () -> 8454143, UIText.of("ponderer.ui.pick.tooltip"))
               )
            );
            entries.add(
               FieldSpecs.xyz(
                  this.coord2Field,
                  "ponderer.ui.trigger_editor.trigger_coord2",
                  null,
                  "X",
                  "Y",
                  "Z",
                  FieldDecorators.xyzAction(20, this::startCoordinatePick, () -> "+", () -> 8454143, UIText.of("ponderer.ui.pick.tooltip"))
               )
            );
         }
      }
   }

   private void applyHeldItem(ItemStack stack) {
      String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
      this.itemField.setValue(itemId);
      CompoundTag tag = PondererClientCommands.extractStackNbtFilter(stack);
      if (tag != null && !tag.isEmpty()) {
         this.itemNbtField.setValue(tag.toString());
      } else {
         this.itemNbtField.setValue("");
      }
   }

   private void openStructureList() {
      Minecraft.getInstance().setScreen(new StructureListScreen(this, selected -> this.pendingStructureSelection = selected));
   }

   private void startCoordinatePick() {
      CoordPickState.startPick(this.snapshotForm(), new TriggerEditorContext(this.scene, this.sceneIndex, this.parent));
      Minecraft.getInstance().setScreen(null);
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      this.restoreNbtPickNotice(snapshot);
   }

   @Override
   protected void renderFormForeground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.renderFormForeground(graphics, mouseX, mouseY, partialTicks);
      if (this.triggerModeIndex != 0 && this.autoFreqIndex != 0) {
         String warning = UIText.of("ponderer.ui.trigger_editor.auto_warning");
         int warnY = this.height - 58;
         int var10003 = this.width / 2;
         graphics.drawCenteredString(Minecraft.getInstance().font, warning, var10003, warnY, 16733525);
      }
   }

   @Override
   protected boolean saveEdits() {
      return this.doSave();
   }

   @Override
   protected SnapshotReturnContext createReturnContext() {
      return new TriggerEditorContext(this.scene, this.sceneIndex, this.parent);
   }

   private void doConfirm() {
      if (this.doSave()) {
         this.returnToParent();
      }
   }

   private boolean doSave() {
      this.clearStatusMessages();
      String newItemId = this.itemField.getValue().trim();
      if (newItemId.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.scene_desc.empty_item"));
         return false;
      }

      String oldItemId = this.scene.items != null && !this.scene.items.isEmpty() ? this.scene.items.get(0) : "";
      if (!newItemId.equals(oldItemId) && !this.pendingItemDuplicateConfirm) {
         for (DslScene s : SceneRuntime.getScenes()) {
            if (s != this.scene && s.items != null && s.items.contains(newItemId)) {
               new ConfirmationScreen()
                  .centered()
                  .withText(Component.translatable("ponderer.ui.scene_desc.error.item_exists_title"))
                  .addText(Component.translatable("ponderer.ui.scene_desc.error.item_exists", new Object[]{newItemId}))
                  .withAction(confirmed -> {
                     if (confirmed) {
                        this.pendingItemDuplicateConfirm = true;
                        this.doConfirm();
                     }
                  })
                  .open(this);
               return false;
            }
         }
      }

      this.pendingItemDuplicateConfirm = false;
      DslScene candidate = SceneStore.copyScene(this.scene);
      if (candidate == null) {
         this.setErrorMessage(UIText.of("ponderer.ui.save_error.io", "Unable to prepare scene copy"));
         return false;
      }

      candidate.items = List.of(newItemId);
      String nbt = this.itemNbtField.getValue().trim();
      candidate.nbtFilter = nbt.isEmpty() ? null : nbt;
      String triggerMode = TRIGGER_MODES[this.triggerModeIndex];
      if ("structure".equals(triggerMode)) {
         String structId = this.structureField.getValue().trim();
         if (!structId.isEmpty() && !this.isValidStructureId(structId)) {
            this.setErrorMessage(UIText.of("ponderer.ui.trigger_editor.error.invalid_structure"));
            return false;
         }
      }

      candidate.triggerMode = "none".equals(triggerMode) ? null : triggerMode;
      candidate.hintAuto = FREQ_VALUES[this.autoFreqIndex];
      candidate.hintTitle = FREQ_VALUES[this.titleFreqIndex];
      candidate.hintSubtitle = FREQ_VALUES[this.subtitleFreqIndex];
      candidate.hintTitleText = this.titleFreqIndex != 0 ? emptyToNull(this.titleTextField.getValue()) : null;
      candidate.hintSubtitleText = this.subtitleFreqIndex != 0 ? emptyToNull(this.subtitleTextField.getValue()) : null;
      candidate.hintStyle = null;
      candidate.hintFrequency = null;
      candidate.onlyFirstTime = null;
      if ("structure".equals(triggerMode)) {
         candidate.triggerStructure = emptyToNull(this.structureField.getValue());
         candidate.triggerStructureRange = null;
         candidate.triggerCoord1 = null;
         candidate.triggerCoord2 = null;
      } else if ("coordinate".equals(triggerMode)) {
         candidate.triggerCoord1 = List.of(
            this.parseIntOr(this.coord1Field.x(), 0), this.parseIntOr(this.coord1Field.y(), 0), this.parseIntOr(this.coord1Field.z(), 0)
         );
         candidate.triggerCoord2 = List.of(
            this.parseIntOr(this.coord2Field.x(), 0), this.parseIntOr(this.coord2Field.y(), 0), this.parseIntOr(this.coord2Field.z(), 0)
         );
         candidate.triggerStructure = null;
         candidate.triggerStructureRange = null;
      } else {
         candidate.triggerStructure = null;
         candidate.triggerStructureRange = null;
         candidate.triggerCoord1 = null;
         candidate.triggerCoord2 = null;
      }

      SceneStore.LocalSaveResult saveResult = SceneStore.saveSceneToLocalDetailed(candidate);
      if (!saveResult.isSuccess()) {
         this.setErrorMessage(UIText.saveError(saveResult));
         return false;
      } else {
         this.scene.items = candidate.items;
         this.scene.nbtFilter = candidate.nbtFilter;
         this.scene.triggerMode = candidate.triggerMode;
         this.scene.hintAuto = candidate.hintAuto;
         this.scene.hintTitle = candidate.hintTitle;
         this.scene.hintSubtitle = candidate.hintSubtitle;
         this.scene.hintTitleText = candidate.hintTitleText;
         this.scene.hintSubtitleText = candidate.hintSubtitleText;
         this.scene.hintStyle = candidate.hintStyle;
         this.scene.hintFrequency = candidate.hintFrequency;
         this.scene.onlyFirstTime = candidate.onlyFirstTime;
         this.scene.triggerStructure = candidate.triggerStructure;
         this.scene.triggerStructureRange = candidate.triggerStructureRange;
         this.scene.triggerCoord1 = candidate.triggerCoord1;
         this.scene.triggerCoord2 = candidate.triggerCoord2;
         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         return true;
      }
   }

   private boolean isValidStructureId(String structId) {
      ResourceLocation loc = ResourceLocation.tryParse(structId);
      if (loc == null) {
         return false;
      }

      try {
         MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
         if (server != null) {
            return server.registryAccess().registryOrThrow(Registries.STRUCTURE).containsKey(loc);
         }

         ClientPacketListener connection = Minecraft.getInstance().getConnection();
         if (connection != null) {
            return connection.registryAccess().registryOrThrow(Registries.STRUCTURE).containsKey(loc);
         }
      } catch (Exception var5) {
      }

      return true;
   }

   @Nullable
   private static String emptyToNull(@Nullable String s) {
      return s != null && !s.trim().isEmpty() ? s.trim() : null;
   }
}

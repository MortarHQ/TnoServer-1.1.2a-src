package com.nododiiiii.ponderer.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.mixin.PonderUIAccessor;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.LocalizedText;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.ActionStripListEntry;
import com.nododiiiii.ponderer.ui.catnip.PonderIconStencils;
import com.nododiiiii.ponderer.ui.catnip.SceneStepListEntry;
import com.nododiiiii.ponderer.ui.catnip.SectionHeaderListEntry;
import com.nododiiiii.ponderer.ui.catnip.WorkspaceHeaderListEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.createmod.catnip.animation.LerpedFloat.Chaser;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.ConfirmationScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.createmod.ponder.foundation.PonderIndex;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SceneEditorScreen extends AbstractDeclarativeListScreen {
   private static final Gson STEP_GSON = new GsonBuilder().registerTypeAdapter(LocalizedText.class, new LocalizedText.GsonAdapter()).create();
   private static final int SIDEBAR_BUTTON_SIZE = 20;
   private static final int SIDEBAR_BUTTON_GAP = 10;
   private static final int STEP_ACTION_ICON_SIZE = 12;
   private static final SceneEditorScreen.HistorySession HISTORY_SESSION = new SceneEditorScreen.HistorySession();
   @Nullable
   private static DslScene.DslStep clipboard = null;
   @Nullable
   private static PonderScreenNavigation.ReturnState pendingPonderReturnState = null;
   private final DslScene scene;
   @Nullable
   private final PonderScreenNavigation.ReturnState ponderReturnState;
   private int sceneIndex;
   @Nullable
   private BoxWidget undoButton;
   @Nullable
   private BoxWidget redoButton;

   public SceneEditorScreen(DslScene scene, int sceneIndex) {
      this(scene, sceneIndex, consumePendingPonderReturnState());
   }

   private SceneEditorScreen(DslScene scene, int sceneIndex, @Nullable PonderScreenNavigation.ReturnState ponderReturnState) {
      super(null, "ponderer.ui.scope.editor", "ponderer.ui.scene_editor", 400);
      this.scene = scene;
      this.sceneIndex = sceneIndex;
      this.ponderReturnState = ponderReturnState;
   }

   public static boolean canModifyScene(DslScene scene) {
      return scene != null && (scene.isEditable((Boolean)Config.DEFAULT_EDITABLE.get()) || (Boolean)Config.DEVELOPER_MODE.get());
   }

   @Override
   protected void init() {
      super.init();
      this.configureActionButtons();
      this.initHistoryButtons();
      this.restoreRememberedListScroll();
   }

   @Override
   public void tick() {
      super.tick();
      this.refreshHistoryButtonState();
   }

   @Override
   protected void collectHeaderEntries(List<Entry> entries) {
      entries.add(
         new WorkspaceHeaderListEntry(
            this::screenTitleText,
            this::screenSubtitleText,
            List.of(
               WorkspaceHeaderListEntry.iconButton(
                  PonderIconStencils.centered(PonderGuiTextures.ICON_PONDER_LEFT),
                  () -> this.switchScene(this.sceneIndex - 1),
                  null,
                  () -> this.getSceneCount() > 1
               ),
               WorkspaceHeaderListEntry.iconButton(
                  PonderIconStencils.centered(PonderGuiTextures.ICON_PONDER_RIGHT),
                  () -> this.switchScene(this.sceneIndex + 1),
                  null,
                  () -> this.getSceneCount() > 1
               )
            )
         )
      );
      entries.add(
         new ActionStripListEntry(
            List.of(
               ActionStripListEntry.button(() -> UIText.of("ponderer.ui.scene_editor.desc"), null, this::openSceneDescription, () -> 16777215, () -> true),
               ActionStripListEntry.button(() -> UIText.of("ponderer.ui.scene_editor.trigger"), null, this::openTriggerEditor, () -> 16777215, () -> true),
               ActionStripListEntry.button(() -> UIText.of("ponderer.ui.scene_editor.split"), null, this::openSplitTypeSelector, () -> 16777215, () -> true),
               ActionStripListEntry.button(() -> UIText.of("ponderer.ui.scene_editor.delete_scene"), null, this::confirmDeleteScene, () -> -32640, () -> true)
            )
         )
      );
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      List<DslScene.DslStep> steps = this.getSteps();
      if (steps.isEmpty()) {
         entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.scene_editor.no_steps")));
      } else {
         for (int i = 0; i < steps.size(); i++) {
            int stepIndex = i;
            entries.add(
               new SceneStepListEntry(
                  () -> stepIndex + 1 + ". " + this.formatStep(steps.get(stepIndex)),
                  () -> this.openEditorForStep(stepIndex),
                  List.of(
                     SceneStepListEntry.iconButton(
                        PonderIconStencils.rotated(PonderGuiTextures.ICON_CONFIG_PREV, 90.0F, 12),
                        () -> List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.move_up"))),
                        () -> this.moveStepUp(stepIndex),
                        null,
                        () -> !this.isActionDisabled(0, stepIndex)
                     ),
                     SceneStepListEntry.iconButton(
                        PonderIconStencils.rotated(PonderGuiTextures.ICON_CONFIG_NEXT, 90.0F, 12),
                        () -> List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.move_down"))),
                        () -> this.moveStepDown(stepIndex),
                        null,
                        () -> !this.isActionDisabled(1, stepIndex)
                     ),
                     SceneStepListEntry.button(
                        "+",
                        () -> List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.insert"))),
                        () -> this.openInsertAfter(stepIndex),
                        null,
                        () -> -8323200,
                        () -> !this.isActionDisabled(2, stepIndex)
                     ),
                     SceneStepListEntry.button("C", this::copyButtonTooltip, () -> this.copyStep(stepIndex), null, () -> -8339201, () -> true),
                     SceneStepListEntry.button("P", this::pasteButtonTooltip, () -> this.pasteAfter(stepIndex), null, () -> -12160, () -> clipboard != null),
                     SceneStepListEntry.dangerIconButton(
                        PonderIconStencils.centered(PonderGuiTextures.ICON_DISABLE, 12),
                        () -> List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.delete"))),
                        () -> this.removeStepAndSave(stepIndex),
                        null,
                        () -> !this.isActionDisabled(5, stepIndex)
                     )
                  )
               )
            );
         }
      }
   }

   @Override
   protected boolean hasUnsavedChanges() {
      return false;
   }

   @Override
   protected int getUnsavedChangeCount() {
      return 0;
   }

   @Override
   protected boolean saveEdits() {
      this.reloadAndExit();
      return true;
   }

   @Override
   protected void discardEdits() {
   }

   @Override
   protected boolean isSaveButtonActive() {
      return true;
   }

   @Override
   protected boolean isDiscardButtonActive() {
      return true;
   }

   @Override
   protected int getEntryHeight() {
      return 40;
   }

   @Override
   protected void attemptBackToParent() {
      this.cancelAndExit();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (Screen.hasControlDown()) {
         if (keyCode == 90) {
            this.performUndo();
            return true;
         }

         if (keyCode == 89) {
            this.performRedo();
            return true;
         }
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   private void configureActionButtons() {
      if (this.saveChanges != null) {
         this.saveChanges.withCallback(this::saveEdits);
         this.saveChanges.getToolTip().clear();
         this.saveChanges.getToolTip().add(Component.translatable("ponderer.ui.save"));
      }

      if (this.discardChanges != null) {
         this.discardChanges.withCallback(this::cancelAndExit);
         this.discardChanges.getToolTip().clear();
         this.discardChanges.getToolTip().add(Component.translatable("ponderer.ui.cancel"));
      }

      if (this.goBack != null) {
         this.goBack.withCallback(this::reloadAndExit);
         this.goBack.visible = true;
         this.goBack.active = true;
         this.goBack.getToolTip().clear();
         this.goBack.getToolTip().add(Component.translatable("ponderer.ui.scene_editor.back"));
      }

      this.relayoutSidebarButtons();
   }

   private void initHistoryButtons() {
      int actionX = this.actionButtonX();
      this.undoButton = this.createHistoryButton(
         actionX,
         0,
         this::performUndo,
         PonderIconStencils.mirrored(PonderGuiTextures.ICON_CONFIG_RESET),
         "ponderer.ui.scene_editor.undo",
         "ponderer.ui.scene_editor.undo.tooltip"
      );
      this.redoButton = this.createHistoryButton(
         actionX,
         0,
         this::performRedo,
         PonderIconStencils.centered(PonderGuiTextures.ICON_CONFIG_RESET),
         "ponderer.ui.scene_editor.redo",
         "ponderer.ui.scene_editor.redo.tooltip"
      );
      this.relayoutSidebarButtons();
      this.refreshHistoryButtonState();
   }

   private int actionButtonX() {
      return this.width / 2 + this.currentListWidthValue() / 2 + 10;
   }

   private BoxWidget createHistoryButton(int x, int y, Runnable callback, DelegatedStencilElement icon, String tooltipTitleKey, String tooltipBodyKey) {
      BoxWidget button = (BoxWidget)new BoxWidget(x, y, 20, 20).withPadding(2.0F, 2.0F).withCallback(callback);
      PonderIconStencils.attach(button, icon);
      this.configureSidebarTooltip(button, Component.translatable(tooltipTitleKey), Component.translatable(tooltipBodyKey));
      button.updateGradientFromState();
      this.addRenderableWidget(button);
      return button;
   }

   private void configureSidebarTooltip(BoxWidget button, Component title, Component detail) {
      button.getToolTip().clear();
      button.getToolTip().add(title);
      button.getToolTip().addAll(FontHelper.cutTextComponent(detail, Palette.ALL_GRAY));
   }

   private void relayoutSidebarButtons() {
      int actionX = this.actionButtonX();
      int totalHeight = 140;
      int currentY = this.height / 2 - totalHeight / 2;
      currentY = this.placeSidebarButton(this.undoButton, actionX, currentY);
      currentY = this.placeSidebarButton(this.redoButton, actionX, currentY);
      currentY = this.placeSidebarButton(this.saveChanges, actionX, currentY);
      currentY = this.placeSidebarButton(this.discardChanges, actionX, currentY);
      this.placeSidebarButton(this.goBack, actionX, currentY);
   }

   private int placeSidebarButton(@Nullable BoxWidget button, int x, int y) {
      if (button != null) {
         button.setX(x);
         button.setY(y);
         button.setWidth(20);
         button.setHeight(20);
      }

      return y + 20 + 10;
   }

   private void refreshHistoryButtonState() {
      UndoManager history = this.undoManager();
      this.updateButtonState(this.undoButton, history.canUndo());
      this.updateButtonState(this.redoButton, history.canRedo());
   }

   public static void markUiToEditorTransition(DslScene scene) {
      if (scene != null) {
         HISTORY_SESSION.markUiToEditorTransition(historySessionKey(scene));
         pendingPonderReturnState = PonderScreenNavigation.captureReturnState();
      }
   }

   @Nullable
   private static PonderScreenNavigation.ReturnState consumePendingPonderReturnState() {
      PonderScreenNavigation.ReturnState state = pendingPonderReturnState;
      pendingPonderReturnState = null;
      return state;
   }

   public static void handlePonderUiRemoved(@Nullable DslScene scene) {
      if (scene == null) {
         HISTORY_SESSION.clear();
      } else {
         HISTORY_SESSION.handlePonderUiRemoved(historySessionKey(scene));
      }
   }

   public static void handlePonderUiFocusChanged(@Nullable DslScene scene) {
      if (scene == null) {
         HISTORY_SESSION.clear();
      } else {
         HISTORY_SESSION.clearIfLeavingPonder(historySessionKey(scene));
      }
   }

   private UndoManager undoManager() {
      return HISTORY_SESSION.historyFor(this.historySessionKey(), this.currentSceneHistoryKey());
   }

   private void clearCurrentPonderHistory() {
      HISTORY_SESSION.clearPonder(this.historySessionKey());
   }

   private String historySessionKey() {
      return historySessionKey(this.scene);
   }

   private static String historySessionKey(DslScene scene) {
      String key = scene.sceneKey();
      if (key == null) {
         key = "<unknown>";
      }

      return scene.nbtFilter != null && !scene.nbtFilter.isBlank() ? key + "|nbt:" + scene.nbtFilter : key;
   }

   private String currentSceneHistoryKey() {
      if (this.scene.scenes != null && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         DslScene.SceneSegment segment = this.scene.scenes.get(this.sceneIndex);
         if (segment != null && segment.id != null && !segment.id.isBlank()) {
            return "segment:" + segment.id;
         }
      }

      return "index:" + this.sceneIndex;
   }

   private int getSceneCount() {
      return this.scene.scenes != null && !this.scene.scenes.isEmpty() ? this.scene.scenes.size() : this.getScenes().size();
   }

   private void switchScene(int newIndex) {
      int count = this.getSceneCount();
      if (count > 1) {
         if (newIndex < 0) {
            newIndex = count - 1;
         }

         if (newIndex >= count) {
            newIndex = 0;
         }

         this.sceneIndex = newIndex;
         this.clearStatusMessages();
         this.rebuildListAtTop();
      }
   }

   private void openSceneDescription() {
      this.rememberCurrentListScroll();
      ScreenOpener.open(new SceneDescEditorScreen(this.scene, this.sceneIndex, this));
   }

   private void openTriggerEditor() {
      this.rememberCurrentListScroll();
      ScreenOpener.open(new TriggerEditorScreen(this.scene, this.sceneIndex, this));
   }

   private void openEditorForStep(int index) {
      List<DslScene.DslStep> steps = this.getSteps();
      if (index >= 0 && index < steps.size()) {
         this.rememberCurrentListScroll();
         AbstractStepEditorScreen editor = StepEditorFactory.createEditScreen(steps.get(index), index, this.scene, this.sceneIndex, this);
         if (editor != null) {
            ScreenOpener.open(editor);
         }
      }
   }

   private static DslScene.DslStep deepCopy(DslScene.DslStep step) {
      return (DslScene.DslStep)STEP_GSON.fromJson(STEP_GSON.toJson(step), DslScene.DslStep.class);
   }

   private boolean isActionDisabled(int actionId, int rowIndex) {
      List<DslScene.DslStep> steps = this.getSteps();
      if (steps.isEmpty()) {
         return false;
      }

      DslScene.DslStep first = steps.get(0);
      if (first != null && first.type != null) {
         boolean firstIsSceneStart = "show_structure".equalsIgnoreCase(first.type) || "show_interface".equalsIgnoreCase(first.type);
         if (!firstIsSceneStart) {
            return false;
         } else {
            return rowIndex != 0 || actionId != 5 && actionId != 0 && actionId != 1 ? rowIndex == 1 && actionId == 0 : true;
         }
      } else {
         return false;
      }
   }

   private List<Component> copyButtonTooltip() {
      return List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.copy")));
   }

   private List<Component> pasteButtonTooltip() {
      return List.of(Component.literal(UIText.of("ponderer.ui.scene_editor.btn.paste_after")));
   }

   private void copyStep(int index) {
      List<DslScene.DslStep> steps = this.getSteps();
      if (index >= 0 && index < steps.size()) {
         clipboard = deepCopy(steps.get(index));
         this.rebuildListPreservingScroll();
      }
   }

   private void openInsertAfter(int afterIndex) {
      this.rememberCurrentListScroll();
      ScreenOpener.open(new StepTypeSelectorScreen(this.scene, this.sceneIndex, this, 0, afterIndex));
   }

   private void pasteAfter(int afterIndex) {
      if (clipboard != null) {
         this.insertStepAndSave(afterIndex, deepCopy(clipboard));
         this.rebuildListPreservingScroll();
      }
   }

   private void performUndo() {
      List<DslScene.DslStep> restored = this.undoManager().undo(this.getSteps());
      if (restored != null) {
         this.setMutableSteps(restored);
         this.saveToFile();
         this.rebuildListPreservingScroll();
      }
   }

   private void performRedo() {
      List<DslScene.DslStep> restored = this.undoManager().redo(this.getSteps());
      if (restored != null) {
         this.setMutableSteps(restored);
         this.saveToFile();
         this.rebuildListPreservingScroll();
      }
   }

   private void setMutableSteps(List<DslScene.DslStep> newSteps) {
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty() && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         this.scene.scenes.get(this.sceneIndex).steps = new ArrayList<>(newSteps);
      }
   }

   private List<DslScene.DslStep> getSteps() {
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty() && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         List<DslScene.DslStep> steps = this.scene.scenes.get(this.sceneIndex).steps;
         return steps != null ? steps : List.of();
      } else {
         return List.of();
      }
   }

   private List<DslScene.DslStep> getMutableSteps() {
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty() && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         DslScene.SceneSegment sc = this.scene.scenes.get(this.sceneIndex);
         if (sc.steps == null) {
            sc.steps = new ArrayList<>();
         } else if (!(sc.steps instanceof ArrayList)) {
            sc.steps = new ArrayList<>(sc.steps);
         }

         return sc.steps;
      } else {
         return new ArrayList<>();
      }
   }

   private List<DslScene.SceneSegment> getScenes() {
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty()) {
         return this.scene.scenes;
      }

      List<DslScene.SceneSegment> result = new ArrayList<>();
      DslScene.SceneSegment fallback = new DslScene.SceneSegment();
      fallback.steps = new ArrayList<>();
      result.add(fallback);
      return result;
   }

   private String screenTitleText() {
      String sceneTitle = this.scene.title != null && !this.scene.title.isEmpty() ? this.scene.title.resolve() : this.scene.id;
      return UIText.of("ponderer.ui.scene_editor.title", sceneTitle);
   }

   private String screenSubtitleText() {
      int sceneCount = this.getSceneCount();
      String currentSceneName = this.currentSceneName();
      return sceneCount > 1 ? "[" + (this.sceneIndex + 1) + "/" + sceneCount + ": " + currentSceneName + "]" : currentSceneName;
   }

   private String currentSceneName() {
      int sceneCount = this.getSceneCount();
      if (this.sceneIndex >= 0 && this.sceneIndex < sceneCount && this.scene.scenes != null && this.sceneIndex < this.scene.scenes.size()) {
         DslScene.SceneSegment currentScene = this.scene.scenes.get(this.sceneIndex);
         if (currentScene.title != null && !currentScene.title.isEmpty()) {
            return currentScene.title.resolve();
         }

         if (currentScene.id != null && !currentScene.id.isBlank()) {
            return currentScene.id;
         }
      }

      return this.scene.id != null ? this.scene.id : String.valueOf(this.sceneIndex + 1);
   }

   private String formatStep(DslScene.DslStep step) {
      if (step != null && step.type != null) {
         return switch (step.type.toLowerCase(Locale.ROOT)) {
            case "show_structure" -> UIText.of("ponderer.ui.step.summary.show_structure", this.stepTypeName("show_structure"));
            case "idle" -> UIText.of("ponderer.ui.step.summary.idle", this.stepTypeName("idle"), step.durationOrDefault(20), UIText.of("ponderer.ui.ticks"));
            case "text" -> UIText.of(
               "ponderer.ui.step.summary.text", this.stepTypeName("text"), this.truncate(step.text != null ? step.text.resolve() : "", 20)
            );
            case "shared_text" -> UIText.of("ponderer.ui.step.summary.shared_text", this.stepTypeName("shared_text"), step.key != null ? step.key : "?");
            case "create_entity" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("create_entity"), step.entity != null ? step.entity : "?"
            );
            case "create_item_entity" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("create_item_entity"), step.item != null ? step.item : "?"
            );
            case "rotate_camera_y" -> UIText.of(
               "ponderer.ui.step.summary.rotate_camera", this.stepTypeName("rotate_camera_y"), step.degrees != null ? step.degrees : 90.0F
            );
            case "zoom_scene" -> this.stepTypeName("zoom_scene");
            case "highlight_section" -> this.stepTypeName("highlight_section");
            case "show_controls" -> UIText.of(
               "ponderer.ui.step.summary.show_controls_action_item",
               this.stepTypeName("show_controls"),
               this.controlActionName(step.action),
               step.item != null && !step.item.isBlank() ? step.item : UIText.of("ponderer.ui.none")
            );
            case "show_interface" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("show_interface"), step.block != null ? step.block : "?"
            );
            case "change_interface_slot" -> UIText.of(
               "ponderer.ui.step.summary.slot_count", this.stepTypeName("change_interface_slot"), step.interfaceSlots != null ? step.interfaceSlots.size() : 0
            );
            case "click_interface" -> this.stepTypeName("click_interface");
            case "encapsulate_bounds" -> this.stepTypeName("encapsulate_bounds");
            case "play_sound" -> UIText.of("ponderer.ui.step.summary.single_arg", this.stepTypeName("play_sound"), step.sound != null ? step.sound : "?");
            case "set_block" -> UIText.of("ponderer.ui.step.summary.single_arg", this.stepTypeName("set_block"), step.block != null ? step.block : "?");
            case "destroy_block" -> this.stepTypeName("destroy_block");
            case "replace_blocks" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("replace_blocks"), step.block != null ? step.block : "?"
            );
            case "hide_section" -> this.stepTypeName("hide_section");
            case "show_section_and_merge" -> this.stepTypeName("show_section_and_merge");
            case "toggle_redstone_power" -> this.stepTypeName("toggle_redstone_power");
            case "modify_block_entity_nbt" -> this.stepTypeName("modify_block_entity_nbt");
            case "rotate_section" -> this.stepTypeName("rotate_section");
            case "move_section" -> this.stepTypeName("move_section");
            case "indicate_redstone" -> this.stepTypeName("indicate_redstone");
            case "indicate_success" -> this.stepTypeName("indicate_success");
            case "clear_entities" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("clear_entities"), step.entity != null && !step.entity.isEmpty() ? step.entity : "*"
            );
            case "clear_item_entities" -> UIText.of(
               "ponderer.ui.step.summary.single_arg", this.stepTypeName("clear_item_entities"), step.item != null && !step.item.isEmpty() ? step.item : "*"
            );
            case "modify_entities_nbt" -> UIText.of(
               "ponderer.ui.step.summary.single_arg",
               this.stepTypeName("modify_entities_nbt"),
               step.entity != null && !step.entity.isEmpty() ? step.entity : "*"
            );
            case "modify_item_entities_nbt" -> UIText.of(
               "ponderer.ui.step.summary.single_arg",
               this.stepTypeName("modify_item_entities_nbt"),
               step.item != null && !step.item.isEmpty() ? step.item : "*"
            );
            case "next_scene" -> UIText.of("ponderer.ui.step.summary.next_scene");
            default -> step.type;
         };
      } else {
         return UIText.of("ponderer.ui.invalid");
      }
   }

   private String stepTypeName(String type) {
      return UIText.of("ponderer.ui.step.type." + type);
   }

   private String controlActionName(String action) {
      if (action != null && !action.isBlank()) {
         String key = "ponderer.ui.show_controls.action." + action.toLowerCase(Locale.ROOT);
         String translated = UIText.of(key);
         return key.equals(translated) ? action : translated;
      } else {
         return UIText.of("ponderer.ui.none");
      }
   }

   private String truncate(String text, int max) {
      if (text == null) {
         return "";
      } else {
         return text.length() > max ? text.substring(0, max) + "..." : text;
      }
   }

   public void addStepAndSave(DslScene.DslStep newStep) {
      this.insertStepAndSave(-1, newStep);
   }

   public void insertStepAndSave(int afterIndex, DslScene.DslStep newStep) {
      this.undoManager().saveState(this.getSteps());
      List<DslScene.DslStep> steps = this.getMutableSteps();
      int insertedIndex;
      if (afterIndex >= 0 && afterIndex < steps.size()) {
         steps.add(afterIndex + 1, newStep);
         insertedIndex = afterIndex + 1;
      } else {
         steps.add(newStep);
         insertedIndex = steps.size() - 1;
      }

      if (isShowInterfaceStep(newStep)) {
         pruneFollowingInterfaceSlotSteps(steps, insertedIndex);
      }

      this.saveToFile();
   }

   public void replaceStepAndSave(int index, DslScene.DslStep newStep) {
      List<DslScene.DslStep> steps = this.getMutableSteps();
      if (index >= 0 && index < steps.size()) {
         this.undoManager().saveState(this.getSteps());
         DslScene.DslStep oldStep = steps.get(index);
         steps.set(index, newStep);
         if (didShowInterfaceContextChange(oldStep, newStep)) {
            pruneFollowingInterfaceSlotSteps(steps, index);
         }

         this.saveToFile();
      }
   }

   private static boolean isShowInterfaceStep(@Nullable DslScene.DslStep step) {
      return step != null && step.type != null && "show_interface".equalsIgnoreCase(step.type);
   }

   private static boolean didShowInterfaceContextChange(@Nullable DslScene.DslStep oldStep, @Nullable DslScene.DslStep newStep) {
      if (!isShowInterfaceStep(newStep)) {
         return false;
      } else {
         return !isShowInterfaceStep(oldStep)
            ? true
            : !Objects.equals(oldStep.block, newStep.block) || !Objects.equals(oldStep.blockProperties, newStep.blockProperties);
      }
   }

   private static void pruneFollowingInterfaceSlotSteps(List<DslScene.DslStep> steps, int showInterfaceIndex) {
      int i = showInterfaceIndex + 1;

      while (i < steps.size()) {
         DslScene.DslStep step = steps.get(i);
         if (isShowInterfaceStep(step)) {
            break;
         }

         if (step != null && step.type != null && "change_interface_slot".equalsIgnoreCase(step.type)) {
            steps.remove(i);
         } else {
            i++;
         }
      }
   }

   public void removeStepAndSave(int index) {
      List<DslScene.DslStep> steps = this.getMutableSteps();
      if (index >= 0 && index < steps.size()) {
         this.undoManager().saveState(this.getSteps());
         steps.remove(index);
         this.saveToFile();
         this.rebuildListPreservingScroll();
      }
   }

   private void moveStepUp(int index) {
      List<DslScene.DslStep> steps = this.getMutableSteps();
      if (index > 0 && index < steps.size()) {
         this.undoManager().saveState(this.getSteps());
         DslScene.DslStep temp = steps.get(index);
         steps.set(index, steps.get(index - 1));
         steps.set(index - 1, temp);
         this.saveToFile();
         this.rebuildListPreservingScroll();
      }
   }

   private void moveStepDown(int index) {
      List<DslScene.DslStep> steps = this.getMutableSteps();
      if (index >= 0 && index < steps.size() - 1) {
         this.undoManager().saveState(this.getSteps());
         DslScene.DslStep temp = steps.get(index);
         steps.set(index, steps.get(index + 1));
         steps.set(index + 1, temp);
         this.saveToFile();
         this.rebuildListPreservingScroll();
      }
   }

   private void openSplitTypeSelector() {
      this.rememberCurrentListScroll();
      ScreenOpener.open(new SceneTypeSelectorScreen(this));
   }

   public void insertSplitStep(String startStepType) {
      String normalizedType = startStepType != null && !startStepType.isBlank() ? startStepType.toLowerCase(Locale.ROOT) : "show_structure";
      if (!"show_structure".equals(normalizedType) && !"show_interface".equals(normalizedType)) {
         normalizedType = "show_structure";
      }

      this.undoManager().saveState(this.getSteps());
      int newSceneIndex = -1;
      if (this.scene.scenes != null && !this.scene.scenes.isEmpty() && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         DslScene.SceneSegment newScene = new DslScene.SceneSegment();
         newScene.steps = new ArrayList<>();
         DslScene.DslStep startStep = new DslScene.DslStep();
         startStep.type = normalizedType;
         newScene.steps.add(startStep);
         DslScene.DslStep idleStep = new DslScene.DslStep();
         idleStep.type = "idle";
         idleStep.duration = 20;
         newScene.steps.add(idleStep);
         newScene.id = generateUniqueSegmentId(this.scene);
         if (!(this.scene.scenes instanceof ArrayList)) {
            this.scene.scenes = new ArrayList<>(this.scene.scenes);
         }

         this.scene.scenes.add(this.sceneIndex + 1, newScene);
         newSceneIndex = this.sceneIndex + 1;
      } else {
         DslScene.DslStep ns = new DslScene.DslStep();
         ns.type = "next_scene";
         this.getMutableSteps().add(ns);
         DslScene.DslStep startStep = new DslScene.DslStep();
         startStep.type = normalizedType;
         this.getMutableSteps().add(startStep);
         DslScene.DslStep idleStep = new DslScene.DslStep();
         idleStep.type = "idle";
         idleStep.duration = 20;
         this.getMutableSteps().add(idleStep);
      }

      this.saveToFile();
      if (newSceneIndex >= 0) {
         this.sceneIndex = newSceneIndex;
         this.rememberListScroll(0.0);
         this.clearCurrentPonderHistory();
         ScreenOpener.open(new SceneDescEditorScreen(this.scene, this.sceneIndex, this));
      } else {
         this.rebuildListAtTop();
      }
   }

   private static String generateUniqueSegmentId(DslScene scene) {
      Set<String> existing = new HashSet<>();
      if (scene.scenes != null) {
         for (DslScene.SceneSegment segment : scene.scenes) {
            if (segment.id != null) {
               existing.add(segment.id);
            }
         }
      }

      ThreadLocalRandom rng = ThreadLocalRandom.current();

      String candidate;
      do {
         candidate = "s_" + Integer.toHexString(rng.nextInt(65536, 1048575));
      } while (existing.contains(candidate));

      return candidate;
   }

   private boolean saveToFile() {
      SceneStore.LocalSaveResult result = SceneStore.saveSceneToLocalDetailed(this.scene);
      if (result.isSuccess()) {
         this.clearStatusMessages();
         return true;
      } else {
         this.setErrorMessage(UIText.saveError(result));
         return false;
      }
   }

   private void confirmDeleteScene() {
      int sceneCount = this.getSceneCount();
      if (sceneCount > 1 && this.scene.scenes != null && !this.scene.scenes.isEmpty()) {
         new ConfirmationScreen()
            .centered()
            .withText(Component.translatable("ponderer.ui.scene_editor.delete_scene_title"))
            .addText(Component.translatable("ponderer.ui.scene_editor.delete_scene_msg", new Object[]{this.currentSceneName()}))
            .withAction(confirmed -> {
               if (confirmed) {
                  this.deleteCurrentScene();
               }
            })
            .open(this);
      } else {
         new ConfirmationScreen()
            .centered()
            .withText(Component.translatable("ponderer.ui.scene_editor.delete_ponder_title"))
            .addText(Component.translatable("ponderer.ui.scene_editor.delete_ponder_msg", new Object[]{this.scene.id}))
            .withAction(confirmed -> {
               if (confirmed) {
                  this.deletePonderAndExit();
               }
            })
            .open(this);
      }
   }

   private void deleteCurrentScene() {
      if (this.scene.scenes != null && this.scene.scenes.size() > 1 && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         if (!(this.scene.scenes instanceof ArrayList)) {
            this.scene.scenes = new ArrayList<>(this.scene.scenes);
         }

         this.scene.scenes.remove(this.sceneIndex);
         if (this.sceneIndex >= this.scene.scenes.size()) {
            this.sceneIndex = this.scene.scenes.size() - 1;
         }

         this.clearCurrentPonderHistory();
         this.rememberListScroll(0.0);
         this.saveToFile();
         Minecraft.getInstance().setScreen(this);
      }
   }

   private void deletePonderAndExit() {
      this.clearCurrentPonderHistory();
      boolean deleted = SceneStore.deleteSceneLocal(this.scene.id);
      Minecraft mc = Minecraft.getInstance();
      mc.setScreen(null);
      if (deleted) {
         SceneStore.reloadFromDisk();
         mc.execute(PonderIndex::reload);
      }
   }

   private boolean cancelAndExit() {
      this.reopenPonderUi(false);
      return true;
   }

   private void reloadAndExit() {
      this.reopenPonderUi(true);
   }

   private void reopenPonderUi(boolean reloadFromDisk) {
      Minecraft mc = Minecraft.getInstance();
      PonderScreenNavigation.restoreReturnState(this.ponderReturnState);
      mc.setScreen(null);
      ResourceLocation itemId = null;
      if (this.scene.items != null && !this.scene.items.isEmpty()) {
         itemId = ResourceLocation.tryParse(this.scene.items.get(0));
      }

      ResourceLocation reopenId = itemId;
      String targetSceneId = this.computeCurrentSceneId();
      String nbtFilter = this.scene.nbtFilter;
      if (reloadFromDisk) {
         SceneStore.reloadFromDisk();
      }

      mc.execute(() -> {
         if (reloadFromDisk) {
            PonderIndex.reload();
         }

         if (reopenId != null && PonderIndex.getSceneAccess().doScenesExistForId(reopenId)) {
            PonderUI ui;
            if (nbtFilter != null && !nbtFilter.isBlank()) {
               ui = PonderUI.of(buildFilteredStack(reopenId, nbtFilter));
            } else {
               ui = PonderUI.of(reopenId);
            }

            if (targetSceneId != null) {
               navigateToScene(ui, targetSceneId);
            }

            mc.setScreen(ui);
         }
      });
   }

   private static ItemStack buildFilteredStack(ResourceLocation itemId, String nbtFilter) {
      Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
      ItemStack stack = new ItemStack(item);

      try {
         CompoundTag filterTag = TagParser.parseTag(nbtFilter);
         CompoundTag fullTag = new CompoundTag();
         fullTag.putString("id", itemId.toString());
         fullTag.putInt("count", 1);
         fullTag.put("tag", filterTag);
         ClientLevel level = Minecraft.getInstance().level;
         if (level != null) {
            ItemStack parsed = ItemStack.parseOptional(level.registryAccess(), fullTag);
            if (!parsed.isEmpty()) {
               stack = parsed;
            }
         }
      } catch (Exception var8) {
      }

      return stack;
   }

   @Nullable
   private String computeCurrentSceneId() {
      ResourceLocation baseId = ResourceLocation.tryParse(this.scene.id);
      if (baseId == null) {
         return null;
      } else {
         String basePath = baseId.getPath();
         List<DslScene.SceneSegment> sceneList = this.getScenes();
         if (sceneList.size() <= 1) {
            return "ponderer:" + basePath;
         } else if (this.sceneIndex >= 0 && this.sceneIndex < sceneList.size()) {
            DslScene.SceneSegment current = sceneList.get(this.sceneIndex);
            String suffix = current.id != null && !current.id.isBlank() ? current.id : String.valueOf(this.sceneIndex + 1);
            return "ponderer:" + basePath + "_" + suffix;
         } else {
            return "ponderer:" + basePath;
         }
      }
   }

   private static void navigateToScene(PonderUI ui, String targetSceneId) {
      PonderUIAccessor accessor = (PonderUIAccessor)ui;
      List<PonderScene> scenes = accessor.ponderer$getScenes();

      for (int i = 0; i < scenes.size(); i++) {
         if (targetSceneId.equals(scenes.get(i).getId().toString())) {
            accessor.ponderer$setIndex(i);
            accessor.ponderer$getLazyIndex().chase(i, 0.33333334F, Chaser.EXP);
            accessor.ponderer$getLazyIndex().startWithValue(i);
            break;
         }
      }
   }

   private static final class HistorySession {
      @Nullable
      private String activePonderKey;
      private final Map<String, UndoManager> sceneHistories = new HashMap<>();
      private boolean preserveOnNextPonderUiRemoval;

      private UndoManager historyFor(String ponderKey, String sceneKey) {
         this.switchToPonder(ponderKey);
         return this.sceneHistories.computeIfAbsent(sceneKey, ignored -> new UndoManager());
      }

      private void markUiToEditorTransition(String ponderKey) {
         this.switchToPonder(ponderKey);
         this.preserveOnNextPonderUiRemoval = true;
      }

      private void handlePonderUiRemoved(String ponderKey) {
         if (Objects.equals(this.activePonderKey, ponderKey)) {
            if (this.preserveOnNextPonderUiRemoval) {
               this.preserveOnNextPonderUiRemoval = false;
            } else {
               this.clear();
            }
         }
      }

      private void clearIfLeavingPonder(String ponderKey) {
         if (this.activePonderKey != null && !Objects.equals(this.activePonderKey, ponderKey)) {
            this.clear();
         }
      }

      private void clearPonder(String ponderKey) {
         if (Objects.equals(this.activePonderKey, ponderKey)) {
            this.sceneHistories.clear();
            this.preserveOnNextPonderUiRemoval = false;
         }
      }

      private void switchToPonder(String ponderKey) {
         if (!Objects.equals(this.activePonderKey, ponderKey)) {
            this.activePonderKey = ponderKey;
            this.sceneHistories.clear();
            this.preserveOnNextPonderUiRemoval = false;
         }
      }

      private void clear() {
         this.activePonderKey = null;
         this.sceneHistories.clear();
         this.preserveOnNextPonderUiRemoval = false;
      }
   }
}

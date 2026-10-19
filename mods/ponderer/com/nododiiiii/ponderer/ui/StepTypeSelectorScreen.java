package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.AbstractReadonlyDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.FullButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.PageTurnListEntry;
import java.util.List;
import java.util.Locale;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.ScreenOpener;

public class StepTypeSelectorScreen extends AbstractReadonlyDeclarativeListScreen {
   private static final int STEP_TYPE_BUTTON_W = 180;
   private static final String[][] PAGE_TYPES = new String[][]{
      {"idle", "text", "show_controls", "rotate_camera_y", "zoom_scene"},
      {"set_block", "destroy_block", "replace_blocks", "modify_block_entity_nbt"},
      {"show_section_and_merge", "hide_section", "rotate_section", "move_section"},
      {"create_entity", "create_item_entity", "clear_entities", "clear_item_entities", "modify_entities_nbt", "modify_item_entities_nbt"},
      {"highlight_section", "indicate_success", "indicate_redstone", "toggle_redstone_power", "play_sound"}
   };
   private static final String[] PAGE_KEYS = new String[]{
      "ponderer.ui.step.page.story",
      "ponderer.ui.step.page.block",
      "ponderer.ui.step.page.section",
      "ponderer.ui.step.page.entity",
      "ponderer.ui.step.page.effect"
   };
   private static final String[][] INTERFACE_SCENE_PAGE_TYPES = new String[][]{
      {"idle", "text", "show_controls", "change_interface_slot", "click_interface", "play_sound"}
   };
   private static final String[] INTERFACE_SCENE_PAGE_KEYS = new String[]{"ponderer.ui.step.page.story"};
   private final DslScene scene;
   private final int sceneIndex;
   private final SceneEditorScreen parent;
   private final String[][] pageTypes;
   private final String[] pageKeys;
   private final int pageIndex;
   private final int insertAfterIndex;

   public StepTypeSelectorScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      this(scene, sceneIndex, parent, 0, -1);
   }

   public StepTypeSelectorScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int pageIndex) {
      this(scene, sceneIndex, parent, pageIndex, -1);
   }

   public StepTypeSelectorScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int pageIndex, int insertAfterIndex) {
      super(parent, "ponderer.ui.scope.editor", "ponderer.ui.step_selector.title", 300);
      this.scene = scene;
      this.sceneIndex = sceneIndex;
      this.parent = parent;
      if (isInterfaceStartScene(scene, sceneIndex)) {
         this.pageTypes = INTERFACE_SCENE_PAGE_TYPES;
         this.pageKeys = INTERFACE_SCENE_PAGE_KEYS;
      } else {
         this.pageTypes = PAGE_TYPES;
         this.pageKeys = PAGE_KEYS;
      }

      this.pageIndex = Math.max(0, Math.min(pageIndex, this.pageTypes.length - 1));
      this.insertAfterIndex = insertAfterIndex;
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      entries.add(
         new PageTurnListEntry(
            this::currentPageLabel,
            () -> this.openPage(this.pageIndex - 1),
            () -> this.openPage(this.pageIndex + 1),
            this::previousPageTooltip,
            this::nextPageTooltip,
            () -> this.pageIndex > 0,
            () -> this.pageIndex + 1 < this.pageTypes.length
         )
      );

      for (String type : this.pageTypes[this.pageIndex]) {
         entries.add(new FullButtonListEntry(UIText.of(this.stepTypeLabelKey(type)), null, () -> this.openEditorForType(type)).setMaxButtonWidth(180));
      }
   }

   @Override
   protected int getEntryHeight() {
      return 24;
   }

   @Override
   protected int fixedVisibleRowCount() {
      return 7;
   }

   private void openPage(int newPageIndex) {
      ScreenOpener.open(new StepTypeSelectorScreen(this.scene, this.sceneIndex, this.parent, newPageIndex, this.insertAfterIndex));
   }

   private String currentPageLabel() {
      return UIText.of(this.pageKeys[this.pageIndex]) + " [" + (this.pageIndex + 1) + "/" + this.pageTypes.length + "]";
   }

   private String previousPageTooltip() {
      return this.pageIndex > 0 ? UIText.of(this.pageKeys[this.pageIndex - 1]) : "";
   }

   private String nextPageTooltip() {
      return this.pageIndex + 1 < this.pageTypes.length ? UIText.of(this.pageKeys[this.pageIndex + 1]) : "";
   }

   private static boolean isInterfaceStartScene(DslScene scene, int sceneIndex) {
      if (scene != null && scene.scenes != null && !scene.scenes.isEmpty()) {
         if (sceneIndex >= 0 && sceneIndex < scene.scenes.size()) {
            List<DslScene.DslStep> steps = scene.scenes.get(sceneIndex).steps;
            if (steps == null) {
               return false;
            }

            for (DslScene.DslStep step : steps) {
               if (step != null && step.type != null && !step.type.isBlank()) {
                  return "show_interface".equals(step.type.toLowerCase(Locale.ROOT));
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void openEditorForType(String type) {
      if ("show_interface".equalsIgnoreCase(type)) {
         ShowInterfaceExperimentalNoticeScreen.openIfNeeded(this, () -> this.openEditorDirectly(type));
      } else {
         this.openEditorDirectly(type);
      }
   }

   private void openEditorDirectly(String type) {
      AbstractStepEditorScreen editor = StepEditorFactory.createAddScreen(type, this.scene, this.sceneIndex, this.parent);
      if (editor != null) {
         editor.setReturnScreen(this);
         editor.setInsertAfterIndex(this.insertAfterIndex);
         ScreenOpener.open(editor);
      }
   }

   private String stepTypeLabelKey(String type) {
      return "ponderer.ui.step.type." + type;
   }
}

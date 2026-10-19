package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.mixin.PonderUIAccessor;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.NbtSceneFilter;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.ActionStripListEntry;
import com.nododiiiii.ponderer.ui.catnip.FullButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.SectionHeaderListEntry;
import com.nododiiiii.ponderer.ui.catnip.WorkspaceHeaderListEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.gui.ScreenOpener;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.ConfirmationScreen.Response;
import net.createmod.catnip.gui.element.BoxElement;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.theme.Color;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;

public class PonderItemGridScreen extends AbstractDeclarativeListScreen {
   private static final int CELL_SIZE = 20;
   private static final int GRID_PAD_X = 12;
   private static final int GRID_PAD_Y = 8;
   private static final int SECTION_HEADER_H = 16;
   private static final int SCROLL_SPEED = 22;
   private final PonderItemGridScreen.Mode mode;
   @Nullable
   private final Consumer<String> onSelectSingle;
   @Nullable
   private final Consumer<Set<String>> onSelectMulti;
   @Nullable
   private final Runnable onCancel;
   private final Set<String> selectedSceneKeys = new HashSet<>();
   private final Set<String> baselineSelectedSceneKeys = new HashSet<>();
   private List<PonderItemGridScreen.PackSection> sections = List.of();
   private List<PonderItemGridScreen.PackSection> visibleSections = List.of();
   private PonderItemGridScreen.GroupMode groupMode = PonderItemGridScreen.GroupMode.BY_PACK;
   private String searchFilter = "";
   private String initialSearchText = "";
   private double pendingPanelScroll = 0.0;
   private int totalSceneCount;
   private int totalItemCount;
   private int lastMouseX;
   private int lastMouseY;
   @Nullable
   private PonderItemGridScreen.GridContentPanel gridPanel;
   @Nullable
   public static PonderItemGridScreen returnScreen;

   public PonderItemGridScreen() {
      this(new FunctionScreen());
   }

   public PonderItemGridScreen(@Nullable Screen parent) {
      this(parent, PonderItemGridScreen.Mode.LIST, null, null, null);
   }

   public PonderItemGridScreen(Consumer<String> onSelect, Runnable onCancel) {
      this(null, onSelect, onCancel);
   }

   public PonderItemGridScreen(@Nullable Screen parent, Consumer<String> onSelect, Runnable onCancel) {
      this(parent, PonderItemGridScreen.Mode.SINGLE_SELECT, onSelect, null, onCancel);
   }

   public PonderItemGridScreen(Consumer<Set<String>> onSelectMulti, Runnable onCancel, boolean multi) {
      this(null, onSelectMulti, onCancel, multi);
   }

   public PonderItemGridScreen(@Nullable Screen parent, Consumer<Set<String>> onSelectMulti, Runnable onCancel, boolean multi) {
      this(parent, PonderItemGridScreen.Mode.MULTI_SELECT, null, onSelectMulti, onCancel);
   }

   private static String titleKeyFor(PonderItemGridScreen.Mode mode) {
      return switch (mode) {
         case LIST -> "ponderer.ui.item_grid.select_scene";
         case SINGLE_SELECT -> "ponderer.ui.item_grid.select_scene";
         case MULTI_SELECT -> "ponderer.ui.item_grid.select_scenes";
      };
   }

   private PonderItemGridScreen(
      @Nullable Screen parent,
      PonderItemGridScreen.Mode mode,
      @Nullable Consumer<String> onSelectSingle,
      @Nullable Consumer<Set<String>> onSelectMulti,
      @Nullable Runnable onCancel
   ) {
      super(parent, "ponderer.ui.scope.editor", titleKeyFor(mode), 300);
      this.mode = mode;
      this.onSelectSingle = onSelectSingle;
      this.onSelectMulti = onSelectMulti;
      this.onCancel = onCancel;
      this.refreshVisibleSections();
   }

   @Override
   protected void init() {
      super.init();
      if (this.list != null) {
         this.removeWidget(this.list);
         this.list = null;
      }

      this.gridPanel = new PonderItemGridScreen.GridContentPanel(
         this.panelLeft(), this.bodyContentTop(), this.currentListWidthValue(), this.bodyContentHeight()
      );
      this.addRenderableWidget(this.gridPanel);
      this.configureActionButtons();
      if (this.search != null) {
         this.search.setResponder(this::updateSearchFilter);
         if (!this.initialSearchText.isBlank()) {
            this.search.setValue(this.initialSearchText);
         }

         this.updateSearchFilter(this.search.getValue());
      } else {
         this.updateSearchFilter(this.searchFilter);
      }

      if (this.gridPanel != null) {
         this.relayoutGridPanel();
         this.gridPanel.setScroll(this.pendingPanelScroll);
         this.gridPanel.clampScroll();
      }
   }

   @Override
   public void resize(Minecraft client, int width, int height) {
      this.pendingPanelScroll = this.gridPanel != null ? this.gridPanel.scrollY() : 0.0;
      this.initialSearchText = this.search != null ? this.search.getValue() : this.initialSearchText;
      super.resize(client, width, height);
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
   }

   @Override
   protected void collectHeaderEntries(List<Entry> entries) {
      entries.add(new WorkspaceHeaderListEntry(this::screenTitleText, this::screenSubtitleText, List.of()));
      List<ActionStripListEntry.ButtonModel> buttons = new ArrayList<>();
      buttons.add(ActionStripListEntry.button(this::groupModeLabel, null, this::toggleGroupMode, () -> 16777215, () -> true));
      if (this.mode == PonderItemGridScreen.Mode.MULTI_SELECT) {
         buttons.add(ActionStripListEntry.button(UIText.of("ponderer.ui.item_grid.select_all"), null, this::handleSelectAll));
         buttons.add(ActionStripListEntry.button(UIText.of("ponderer.ui.item_grid.deselect_all"), null, this::handleDeselectAll));
      }

      entries.add(new ActionStripListEntry(buttons));
   }

   @Override
   protected boolean hasUnsavedChanges() {
      return this.mode == PonderItemGridScreen.Mode.MULTI_SELECT && !this.baselineSelectedSceneKeys.equals(this.selectedSceneKeys);
   }

   @Override
   protected int getUnsavedChangeCount() {
      return this.hasUnsavedChanges() ? 1 : 0;
   }

   @Override
   protected boolean saveEdits() {
      return this.confirmSelection();
   }

   @Override
   protected void discardEdits() {
      this.selectedSceneKeys.clear();
      this.selectedSceneKeys.addAll(this.baselineSelectedSceneKeys);
   }

   @Override
   protected boolean isSaveButtonActive() {
      return this.mode == PonderItemGridScreen.Mode.MULTI_SELECT;
   }

   @Override
   protected boolean isDiscardButtonActive() {
      return this.mode == PonderItemGridScreen.Mode.MULTI_SELECT;
   }

   @Override
   protected void attemptBackToParent() {
      if (this.mode == PonderItemGridScreen.Mode.MULTI_SELECT && this.hasUnsavedChanges()) {
         this.showLeavingPrompt(response -> {
            if (response != Response.Cancel) {
               if (response == Response.Confirm) {
                  if (this.saveEdits()) {
                     this.runCancelAction();
                  }
               } else {
                  this.discardEdits();
                  this.runCancelAction();
               }
            }
         });
      } else {
         this.runCancelAction();
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      return this.gridPanel != null && this.gridPanel.mouseScrolled(mouseX, mouseY, deltaX, deltaY)
         ? true
         : super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      }

      if (keyCode == 87) {
         PonderItemGridScreen.HitResult hit = this.hitTest(this.lastMouseX, this.lastMouseY);
         if (hit != null) {
            returnScreen = this.copyForReturn();
            openFilteredPonderUI(hit.entry);
            return true;
         }
      }

      return false;
   }

   private void refreshVisibleSections() {
      this.sections = this.collectSectionsForCurrentMode();
      this.visibleSections = this.searchFilter.isBlank() ? this.sections : this.filterSections(this.searchFilter);
      this.recomputeCounts(this.visibleSections);
   }

   private List<PonderItemGridScreen.PackSection> collectSectionsForCurrentMode() {
      return this.groupMode == PonderItemGridScreen.GroupMode.BY_PACK ? collectGroupedSections() : collectFlatSections();
   }

   private void recomputeCounts(List<PonderItemGridScreen.PackSection> source) {
      int items = 0;
      Set<String> allKeys = new HashSet<>();

      for (PonderItemGridScreen.PackSection section : source) {
         items += section.entries.size();

         for (PonderItemGridScreen.ItemEntry entry : section.entries) {
            allKeys.addAll(entry.sceneKeys);
         }
      }

      this.totalItemCount = items;
      this.totalSceneCount = allKeys.size();
   }

   private List<PonderItemGridScreen.PackSection> filterSections(String normalizedQuery) {
      List<PonderItemGridScreen.PackSection> filtered = new ArrayList<>();

      for (PonderItemGridScreen.PackSection section : this.sections) {
         boolean sectionMatches = section.displayName.toLowerCase(Locale.ROOT).contains(normalizedQuery);
         List<PonderItemGridScreen.ItemEntry> matched = new ArrayList<>();

         for (PonderItemGridScreen.ItemEntry entry : section.entries) {
            if (sectionMatches || matchesQuery(entry, normalizedQuery)) {
               matched.add(entry);
            }
         }

         if (!matched.isEmpty()) {
            filtered.add(new PonderItemGridScreen.PackSection(section.packPrefix, section.displayName, matched));
         }
      }

      return filtered;
   }

   private static boolean matchesQuery(PonderItemGridScreen.ItemEntry entry, String normalizedQuery) {
      if (entry.stack.getHoverName().getString().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
         return true;
      }

      ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(entry.stack.getItem());
      if (itemId != null && itemId.toString().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
         return true;
      }

      if (entry.nbtFilter != null && entry.nbtFilter.toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
         return true;
      }

      for (String sceneKey : entry.sceneKeys) {
         if (sceneKey.toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
            return true;
         }
      }

      return false;
   }

   private void updateSearchFilter(String query) {
      this.searchFilter = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
      this.initialSearchText = query == null ? "" : query;
      this.refreshVisibleSections();
      if (this.search != null) {
         this.search
            .setTextColor(
               this.visibleSections.isEmpty() && !this.searchFilter.isBlank()
                  ? ((Color)AbstractSimiWidget.COLOR_FAIL.getFirst()).getRGB()
                  : ((Color)UIRenderHelper.COLOR_TEXT.getFirst()).getRGB()
            );
      }

      if (this.gridPanel != null) {
         this.relayoutGridPanel();
         this.gridPanel.setScroll(0.0);
      }
   }

   private void toggleGroupMode() {
      this.groupMode = this.groupMode == PonderItemGridScreen.GroupMode.BY_PACK
         ? PonderItemGridScreen.GroupMode.BY_ITEM
         : PonderItemGridScreen.GroupMode.BY_PACK;
      this.refreshVisibleSections();
      if (this.gridPanel != null) {
         this.relayoutGridPanel();
         this.gridPanel.setScroll(0.0);
      }
   }

   private void handleSelectAll() {
      for (PonderItemGridScreen.PackSection section : this.visibleSections) {
         for (PonderItemGridScreen.ItemEntry entry : section.entries) {
            this.selectedSceneKeys.addAll(entry.sceneKeys);
         }
      }
   }

   private void handleDeselectAll() {
      this.selectedSceneKeys.clear();
   }

   private void configureActionButtons() {
      if (this.saveChanges != null) {
         if (this.mode == PonderItemGridScreen.Mode.MULTI_SELECT) {
            this.saveChanges.visible = true;
            this.saveChanges.active = true;
            this.saveChanges.withCallback(this::confirmSelection);
            this.saveChanges.getToolTip().clear();
            this.saveChanges.getToolTip().add(Component.translatable("ponderer.ui.confirm"));
         } else {
            this.saveChanges.visible = false;
            this.saveChanges.active = false;
         }
      }

      if (this.discardChanges != null) {
         if (this.mode == PonderItemGridScreen.Mode.MULTI_SELECT) {
            this.discardChanges.visible = true;
            this.discardChanges.active = true;
            this.discardChanges.withCallback(this::cancelSelection);
            this.discardChanges.getToolTip().clear();
            this.discardChanges.getToolTip().add(Component.translatable("ponderer.ui.cancel"));
         } else {
            this.discardChanges.visible = false;
            this.discardChanges.active = false;
         }
      }
   }

   private int panelLeft() {
      return this.width / 2 - this.currentListWidthValue() / 2;
   }

   private void relayoutGridPanel() {
      if (this.gridPanel != null) {
         int panelHeight = this.bodyContentHeight();
         this.gridPanel.setY(this.bodyContentTop());
         this.gridPanel.setHeight(panelHeight);
         this.gridPanel.clampScroll();
      }
   }

   private boolean confirmSelection() {
      if (this.mode == PonderItemGridScreen.Mode.MULTI_SELECT && this.onSelectMulti != null) {
         this.baselineSelectedSceneKeys.clear();
         this.baselineSelectedSceneKeys.addAll(this.selectedSceneKeys);
         this.onSelectMulti.accept(new HashSet<>(this.selectedSceneKeys));
         return true;
      } else {
         return false;
      }
   }

   private void cancelSelection() {
      this.discardEdits();
      this.runCancelAction();
   }

   private void runCancelAction() {
      if (this.onCancel != null) {
         this.onCancel.run();
      } else {
         ScreenOpener.open(this.parent);
      }
   }

   private int getSelectionState(PonderItemGridScreen.ItemEntry entry) {
      if (this.mode != PonderItemGridScreen.Mode.MULTI_SELECT) {
         return 0;
      }

      boolean any = false;
      boolean all = true;

      for (String key : entry.sceneKeys) {
         if (this.selectedSceneKeys.contains(key)) {
            any = true;
         } else {
            all = false;
         }
      }

      if (all) {
         return 2;
      } else {
         return any ? 1 : 0;
      }
   }

   @Nullable
   private PonderItemGridScreen.HitResult hitTest(double mouseX, double mouseY) {
      return this.gridPanel == null ? null : this.gridPanel.hitTest(mouseX, mouseY);
   }

   private void handleItemClick(PonderItemGridScreen.ItemEntry entry) {
      switch (this.mode) {
         case LIST:
            returnScreen = this.copyForReturn();
            openFilteredPonderUI(entry);
            break;
         case SINGLE_SELECT:
            if (entry.sceneKeys.size() == 1) {
               if (this.onSelectSingle != null) {
                  this.onSelectSingle.accept(entry.sceneKeys.get(0));
               }
            } else {
               Minecraft.getInstance()
                  .setScreen(
                     new PonderItemGridScreen.SceneIdListScreen(
                        entry.sceneKeys,
                        PonderItemGridScreen.SceneIdListScreen.SelectMode.SINGLE,
                        this.onSelectSingle,
                        null,
                        null,
                        () -> Minecraft.getInstance().setScreen(this)
                     )
                  );
            }
            break;
         case MULTI_SELECT:
            if (entry.sceneKeys.size() == 1) {
               String key = entry.sceneKeys.get(0);
               if (this.selectedSceneKeys.contains(key)) {
                  this.selectedSceneKeys.remove(key);
               } else {
                  this.selectedSceneKeys.add(key);
               }
            } else {
               Set<String> pre = new HashSet<>();

               for (String key : entry.sceneKeys) {
                  if (this.selectedSceneKeys.contains(key)) {
                     pre.add(key);
                  }
               }

               PonderItemGridScreen self = this;
               Minecraft.getInstance()
                  .setScreen(
                     new PonderItemGridScreen.SceneIdListScreen(
                        entry.sceneKeys, PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI, null, returnedKeys -> {
                           entry.sceneKeys.forEach(this.selectedSceneKeys::remove);
                           this.selectedSceneKeys.addAll(returnedKeys);
                           Minecraft.getInstance().setScreen(self);
                        }, pre, () -> Minecraft.getInstance().setScreen(self)
                     )
                  );
            }
      }
   }

   private PonderItemGridScreen copyForReturn() {
      PonderItemGridScreen copy = new PonderItemGridScreen(this.parent, this.mode, this.onSelectSingle, this.onSelectMulti, this.onCancel);
      copy.groupMode = this.groupMode;
      copy.initialSearchText = this.search != null ? this.search.getValue() : this.initialSearchText;
      copy.pendingPanelScroll = this.gridPanel != null ? this.gridPanel.scrollY() : 0.0;
      copy.selectedSceneKeys.addAll(this.selectedSceneKeys);
      copy.baselineSelectedSceneKeys.addAll(this.baselineSelectedSceneKeys);
      copy.refreshVisibleSections();
      return copy;
   }

   private static List<PonderItemGridScreen.PackSection> collectGroupedSections() {
      Map<String, Map<String, List<String>>> packGroups = new LinkedHashMap<>();
      Map<String, Map<String, List<String>>> packItemFilters = new LinkedHashMap<>();

      for (DslScene scene : SceneRuntime.getScenes()) {
         if (scene.items != null && scene.id != null) {
            String packPrefix = scene.getPackPrefix();
            String packKey = packPrefix != null ? packPrefix : "";
            String sceneKey = scene.sceneKey();

            for (String itemId : scene.items) {
               String nf = scene.nbtFilter;
               String entryKey = normalizedEntryKey(itemId, nf);
               packGroups.computeIfAbsent(packKey, ignored -> new LinkedHashMap<>()).computeIfAbsent(entryKey, ignored -> new ArrayList<>());
               List<String> keys = packGroups.get(packKey).get(entryKey);
               if (!keys.contains(sceneKey)) {
                  keys.add(sceneKey);
               }

               packItemFilters.computeIfAbsent(packKey, ignored -> new LinkedHashMap<>()).computeIfAbsent(itemId, ignored -> new ArrayList<>());
               List<String> filters = packItemFilters.get(packKey).get(itemId);
               if (nf != null && !nf.isBlank()) {
                  if (!filters.contains(nf)) {
                     filters.add(nf);
                  }
               } else if (!filters.contains(null)) {
                  filters.add(0, null);
               }
            }
         }
      }

      List<PonderItemGridScreen.PackSection> result = new ArrayList<>();
      List<String> packKeys = new ArrayList<>(packGroups.keySet());
      packKeys.sort((a, b) -> {
         if (a.isEmpty() && !b.isEmpty()) {
            return -1;
         } else {
            return !a.isEmpty() && b.isEmpty() ? 1 : a.compareToIgnoreCase(b);
         }
      });

      for (String packKey : packKeys) {
         Map<String, List<String>> entryMap = packGroups.get(packKey);
         Map<String, List<String>> filterMap = packItemFilters.getOrDefault(packKey, Map.of());
         List<PonderItemGridScreen.ItemEntry> entries = buildEntries(entryMap, filterMap);
         if (!entries.isEmpty()) {
            String prefix = packKey.isEmpty() ? null : packKey;
            String displayName = packKey.isEmpty() ? UIText.of("ponderer.ui.item_grid.section_local") : packKey;
            result.add(new PonderItemGridScreen.PackSection(prefix, displayName, entries));
         }
      }

      return result;
   }

   private static List<PonderItemGridScreen.PackSection> collectFlatSections() {
      Map<String, List<String>> entryMap = new LinkedHashMap<>();
      Map<String, List<String>> itemFilters = new LinkedHashMap<>();

      for (DslScene scene : SceneRuntime.getScenes()) {
         if (scene.items != null && scene.id != null) {
            String sceneKey = scene.sceneKey();

            for (String itemId : scene.items) {
               String nf = scene.nbtFilter;
               String key = normalizedEntryKey(itemId, nf);
               entryMap.computeIfAbsent(key, ignored -> new ArrayList<>());
               List<String> keys = entryMap.get(key);
               if (!keys.contains(sceneKey)) {
                  keys.add(sceneKey);
               }

               itemFilters.computeIfAbsent(itemId, ignored -> new ArrayList<>());
               List<String> filters = itemFilters.get(itemId);
               if (nf != null && !nf.isBlank()) {
                  if (!filters.contains(nf)) {
                     filters.add(nf);
                  }
               } else if (!filters.contains(null)) {
                  filters.add(0, null);
               }
            }
         }
      }

      List<PonderItemGridScreen.ItemEntry> entries = buildEntries(entryMap, itemFilters);
      return entries.isEmpty() ? List.of() : List.of(new PonderItemGridScreen.PackSection(null, UIText.of("ponderer.ui.item_grid.section_all"), entries));
   }

   private static List<PonderItemGridScreen.ItemEntry> buildEntries(Map<String, List<String>> entryMap, Map<String, List<String>> filterMap) {
      List<PonderItemGridScreen.ItemEntry> entries = new ArrayList<>();

      for (java.util.Map.Entry<String, List<String>> itemEntry : filterMap.entrySet()) {
         String itemId = itemEntry.getKey();
         ResourceLocation rl = ResourceLocation.tryParse(itemId);
         if (rl != null) {
            Item item = (Item)BuiltInRegistries.ITEM.get(rl);
            if (item != null && item != Items.AIR) {
               Set<String> seenNormalizedKeys = new HashSet<>();

               for (String nf : itemEntry.getValue()) {
                  ItemStack stack = new ItemStack(item);
                  if (nf != null) {
                     try {
                        CompoundTag filterTag = TagParser.parseTag(nf);
                        CompoundTag fullTag = new CompoundTag();
                        fullTag.putString("id", rl.toString());
                        fullTag.putInt("count", 1);
                        fullTag.put("tag", filterTag);
                        ClientLevel level = Minecraft.getInstance().level;
                        if (level != null) {
                           ItemStack parsed = ItemStack.parseOptional(level.registryAccess(), fullTag);
                           if (!parsed.isEmpty()) {
                              stack = parsed;
                           }
                        }
                     } catch (Exception var16) {
                     }
                  }

                  String key = normalizedEntryKey(itemId, nf);
                  if (seenNormalizedKeys.add(key)) {
                     List<String> sceneKeys = entryMap.getOrDefault(key, List.of());
                     if (!sceneKeys.isEmpty()) {
                        entries.add(new PonderItemGridScreen.ItemEntry(stack, nf, sceneKeys));
                     }
                  }
               }
            }
         }
      }

      return entries;
   }

   private static String normalizedEntryKey(String itemId, @Nullable String nbtFilter) {
      String nf = nbtFilter != null ? nbtFilter : "";
      if ("minecraft:written_book".equals(itemId) && !nf.isBlank()) {
         String title = NbtSceneFilter.extractWrittenBookTitleFromFilterSnbt(nf);
         if (title != null && !title.isBlank()) {
            return itemId + "|title:" + title;
         }
      }

      return itemId + "|" + nf;
   }

   private static String formatNbtFilterForTooltip(PonderItemGridScreen.ItemEntry entry) {
      if (entry.nbtFilter == null) {
         return "";
      }

      ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(entry.stack.getItem());
      if (itemId != null && "minecraft:written_book".equals(itemId.toString())) {
         String title = NbtSceneFilter.extractWrittenBookTitleFromFilterSnbt(entry.nbtFilter);
         if (title != null && !title.isBlank()) {
            return "title: " + title;
         }
      }

      return entry.nbtFilter;
   }

   private static void openFilteredPonderUI(PonderItemGridScreen.ItemEntry entry) {
      PonderUI ui = PonderUI.of(entry.stack);
      PonderUIAccessor accessor = (PonderUIAccessor)ui;
      List<PonderScene> allScenes = accessor.ponderer$getScenes();
      Set<SceneRuntime.PonderSceneRef> refs = SceneRuntime.resolvePonderSceneRefs(entry.sceneKeys);
      if (!refs.isEmpty()) {
         Map<String, int[]> idCounters = new LinkedHashMap<>();
         List<PonderScene> filtered = new ArrayList<>();

         for (PonderScene ps : allScenes) {
            String psId = ps.getId().toString();
            int[] counter = idCounters.computeIfAbsent(psId, ignored -> new int[]{0});
            int occ = (int)(counter[0]++);
            if (refs.contains(new SceneRuntime.PonderSceneRef(psId, occ))) {
               filtered.add(ps);
            }
         }

         if (!filtered.isEmpty() && filtered.size() < allScenes.size()) {
            allScenes.clear();
            allScenes.addAll(filtered);
         }
      }

      ScreenOpener.open(ui);
   }

   private static int rowsFor(int size, int columns) {
      return (size + columns - 1) / columns;
   }

   private String screenTitleText() {
      return UIText.of(this.titleKey);
   }

   private String screenSubtitleText() {
      return this.mode == PonderItemGridScreen.Mode.MULTI_SELECT
         ? UIText.of("ponderer.ui.item_grid.selected_count", this.selectedSceneKeys.size(), this.totalSceneCount)
         : UIText.of("ponderer.ui.item_grid.total_scenes", this.totalItemCount, this.totalSceneCount);
   }

   private String groupModeLabel() {
      return this.groupMode == PonderItemGridScreen.GroupMode.BY_PACK
         ? UIText.of("ponderer.ui.item_grid.group_by_pack")
         : UIText.of("ponderer.ui.item_grid.group_by_item");
   }

   private final class GridContentPanel extends AbstractSimiWidget {
      private double scrollY = 0.0;
      private boolean draggingScrollbar = false;
      private double scrollbarGrabOffset = 0.0;

      private GridContentPanel(int x, int y, int width, int height) {
         super(x, y, width, height);
      }

      public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
         PonderItemGridScreen.this.lastMouseX = mouseX;
         PonderItemGridScreen.this.lastMouseY = mouseY;
         this.clampScroll();
         this.renderConfigScreenListFrame(graphics);
         graphics.enableScissor(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());

         try {
            this.renderGridEntry(graphics, this.gridRowY(), mouseX, mouseY);
         } finally {
            graphics.disableScissor();
         }

         this.renderScrollbar(graphics, mouseX, mouseY);
         this.renderTooltip(graphics, mouseX, mouseY);
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button != 0 || !this.isMouseOver(mouseX, mouseY)) {
            return false;
         }

         if (this.isOverScrollbar(mouseX, mouseY)) {
            if (this.contentHeight() <= this.getHeight()) {
               return true;
            }

            if (this.isOverScrollbarThumb(mouseX, mouseY)) {
               this.draggingScrollbar = true;
               this.scrollbarGrabOffset = mouseY - this.scrollbarThumbY();
            } else {
               this.jumpScrollbar(mouseY);
            }

            return true;
         } else {
            PonderItemGridScreen.HitResult hit = this.hitTest(mouseX, mouseY);
            if (hit != null) {
               PonderItemGridScreen.this.handleItemClick(hit.entry);
               return true;
            } else {
               return false;
            }
         }
      }

      public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
         if (this.draggingScrollbar && button == 0) {
            this.dragScrollbar(mouseY - this.scrollbarGrabOffset + this.scrollbarThumbHeight() / 2.0);
            return true;
         } else {
            return false;
         }
      }

      public boolean mouseReleased(double mouseX, double mouseY, int button) {
         if (button == 0 && this.draggingScrollbar) {
            this.draggingScrollbar = false;
            return true;
         } else {
            return super.mouseReleased(mouseX, mouseY, button);
         }
      }

      public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
         if (!this.isMouseOver(mouseX, mouseY)) {
            return false;
         }

         this.setScroll(this.scrollY - deltaY * 22.0);
         return true;
      }

      private double scrollY() {
         return this.scrollY;
      }

      private void setScroll(double value) {
         this.scrollY = Mth.clamp(value, 0.0, Math.max(0, this.contentHeight() - this.getHeight()));
      }

      private void clampScroll() {
         this.setScroll(this.scrollY);
      }

      private int gridRowY() {
         return this.getY() - Mth.floor(this.scrollY);
      }

      private int contentHeight() {
         return this.gridEntryHeight();
      }

      private int gridEntryHeight() {
         if (PonderItemGridScreen.this.visibleSections.isEmpty()) {
            return 88;
         }

         int columns = this.columns();
         int height = 16;

         for (PonderItemGridScreen.PackSection section : PonderItemGridScreen.this.visibleSections) {
            height += 16;
            height += PonderItemGridScreen.rowsFor(section.entries.size(), columns) * 20;
         }

         return Math.max(88, height);
      }

      private int entryContentLeft() {
         return this.getX() + 12;
      }

      private int scrollbarX() {
         return this.getX() + this.getWidth() - 4 - 4;
      }

      private int scrollbarTop() {
         return this.getY() + 4;
      }

      private int scrollbarHeight() {
         return this.getHeight() - 8;
      }

      private int scrollbarThumbHeight() {
         return this.contentHeight() <= this.getHeight()
            ? this.scrollbarHeight()
            : Math.max(8, this.scrollbarHeight() * this.getHeight() / this.contentHeight());
      }

      private int scrollbarThumbY() {
         if (this.contentHeight() <= this.getHeight()) {
            return this.scrollbarTop();
         }

         int trackRange = this.scrollbarHeight() - this.scrollbarThumbHeight();
         int contentRange = this.contentHeight() - this.getHeight();
         return this.scrollbarTop() + Mth.floor(this.scrollY / contentRange * trackRange);
      }

      private boolean isOverScrollbar(double mouseX, double mouseY) {
         return mouseX >= this.scrollbarX()
            && mouseX < this.scrollbarX() + 4
            && mouseY >= this.scrollbarTop()
            && mouseY < this.scrollbarTop() + this.scrollbarHeight();
      }

      private boolean isOverScrollbarThumb(double mouseX, double mouseY) {
         int thumbY = this.scrollbarThumbY();
         int thumbH = this.scrollbarThumbHeight();
         return mouseX >= this.scrollbarX() && mouseX < this.scrollbarX() + 4 && mouseY >= thumbY && mouseY < thumbY + thumbH;
      }

      private void jumpScrollbar(double mouseY) {
         this.dragScrollbar(mouseY);
      }

      private void dragScrollbar(double thumbCenterY) {
         if (this.contentHeight() <= this.getHeight()) {
            this.setScroll(0.0);
         } else {
            int thumbH = this.scrollbarThumbHeight();
            int trackRange = this.scrollbarHeight() - thumbH;
            double clampedCenter = Mth.clamp(thumbCenterY, this.scrollbarTop() + thumbH / 2.0, this.scrollbarTop() + this.scrollbarHeight() - thumbH / 2.0);
            double trackOffset = clampedCenter - this.scrollbarTop() - thumbH / 2.0;
            double ratio = trackRange <= 0 ? 0.0 : trackOffset / trackRange;
            this.setScroll(ratio * (this.contentHeight() - this.getHeight()));
         }
      }

      private int columns() {
         int availableWidth = Math.max(20, this.gridRightLimit() - this.gridLeft());
         return Math.max(1, availableWidth / 20);
      }

      private int gridInnerWidth() {
         return this.columns() * 20;
      }

      private int gridLeft() {
         return this.entryContentLeft();
      }

      private int gridRightLimit() {
         return this.scrollbarX() - 6;
      }

      private int gridRight() {
         return this.gridLeft() + this.gridInnerWidth();
      }

      private void renderConfigScreenListFrame(GuiGraphics graphics) {
         Color c = new Color(1610612736);
         UIRenderHelper.angledGradient(graphics, 90.0F, this.getX() + this.getWidth() / 2, this.getY(), this.getWidth(), 5.0F, c, Color.TRANSPARENT_BLACK);
         UIRenderHelper.angledGradient(
            graphics, -90.0F, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight(), this.getWidth(), 5.0F, c, Color.TRANSPARENT_BLACK
         );
         UIRenderHelper.angledGradient(graphics, 0.0F, this.getX(), this.getY() + this.getHeight() / 2, this.getHeight(), 5.0F, c, Color.TRANSPARENT_BLACK);
         UIRenderHelper.angledGradient(
            graphics, 180.0F, this.getX() + this.getWidth(), this.getY() + this.getHeight() / 2, this.getHeight(), 5.0F, c, Color.TRANSPARENT_BLACK
         );
      }

      private void renderGridEntry(GuiGraphics graphics, int y, int mouseX, int mouseY) {
         int height = this.gridEntryHeight();
         if (PonderItemGridScreen.this.visibleSections.isEmpty()) {
            if (y + height > this.getY() && y < this.getY() + this.getHeight()) {
               graphics.drawCenteredString(
                  PonderItemGridScreen.this.font,
                  Component.translatable("ponderer.ui.item_grid.empty"),
                  this.getX() + this.getWidth() / 2,
                  y + height / 2 - 4,
                  ((Color)UIRenderHelper.COLOR_TEXT_DARKER.getFirst()).getRGB()
               );
            }
         } else {
            int contentY = y + 8;
            int columns = this.columns();

            for (PonderItemGridScreen.PackSection section : PonderItemGridScreen.this.visibleSections) {
               int headerY = contentY;
               if (headerY + 16 > this.getY() && headerY < this.getY() + this.getHeight()) {
                  graphics.drawString(
                     PonderItemGridScreen.this.font,
                     section.displayName,
                     this.gridLeft() + 2,
                     headerY + 3,
                     ((Color)UIRenderHelper.COLOR_TEXT_STRONG_ACCENT.getFirst()).getRGB()
                  );
                  graphics.fill(this.gridLeft(), headerY + 16 - 1, this.gridRight(), headerY + 16, 553648127);
               }

               contentY += 16;

               for (int index = 0; index < section.entries.size(); index++) {
                  int col = index % columns;
                  int row = index / columns;
                  int itemX = this.gridLeft() + col * 20;
                  int itemY = contentY + row * 20;
                  if (itemY + 20 > this.getY() && itemY < this.getY() + this.getHeight()) {
                     PonderItemGridScreen.ItemEntry entry = section.entries.get(index);
                     boolean hovered = mouseX >= itemX && mouseX < itemX + 20 && mouseY >= itemY && mouseY < itemY + 20;
                     this.renderItemCell(graphics, itemX, itemY, entry, hovered);
                  }
               }

               contentY += PonderItemGridScreen.rowsFor(section.entries.size(), columns) * 20;
            }
         }
      }

      private void renderItemCell(GuiGraphics graphics, int x, int y, PonderItemGridScreen.ItemEntry entry, boolean hovered) {
         int selectionState = PonderItemGridScreen.this.getSelectionState(entry);
         Couple<Color> border = null;
         Color background = null;
         int iconX = x + 2;
         int iconY = y + 2;
         int iconSize = 16;
         if (selectionState == 2) {
            border = AbstractSimiWidget.COLOR_SUCCESS;
            background = new Color(612956040, true);
         } else if (selectionState == 1) {
            border = Couple.create(new Color(-570437771, true), new Color(-1862297040, true));
            background = new Color(553628487, true);
         } else if (hovered) {
            border = AbstractSimiWidget.COLOR_HOVER;
            background = new Color(352321535, true);
         }

         if (border != null) {
            new BoxElement()
               .withBackground(background)
               .gradientBorder((Color)border.getFirst(), (Color)border.getSecond())
               .at(iconX + 1, iconY + 1, 0.0F)
               .withBounds(iconSize - 2, iconSize - 2)
               .render(graphics);
         }

         graphics.renderItem(entry.stack, iconX, iconY);
         if (entry.nbtFilter != null) {
            graphics.fill(x + 20 - 5, y + 1, x + 20 - 1, y + 5, -22016);
         }

         if (entry.sceneKeys.size() > 1) {
            graphics.fill(x + 1, y + 1, x + 5, y + 5, -11162881);
         }
      }

      @Nullable
      private PonderItemGridScreen.HitResult hitTest(double mouseX, double mouseY) {
         if (!this.isMouseOver(mouseX, mouseY)) {
            return null;
         }

         int columns = this.columns();
         int contentY = this.gridRowY() + 8;

         for (PonderItemGridScreen.PackSection section : PonderItemGridScreen.this.visibleSections) {
            contentY += 16;

            for (int index = 0; index < section.entries.size(); index++) {
               int col = index % columns;
               int row = index / columns;
               int itemX = this.gridLeft() + col * 20;
               int itemY = contentY + row * 20;
               if (mouseX >= itemX
                  && mouseX < itemX + 20
                  && mouseY >= itemY
                  && mouseY < itemY + 20
                  && itemY >= this.getY()
                  && itemY + 20 <= this.getY() + this.getHeight()) {
                  return new PonderItemGridScreen.HitResult(section.entries.get(index));
               }
            }

            contentY += PonderItemGridScreen.rowsFor(section.entries.size(), columns) * 20;
         }

         return null;
      }

      private void renderScrollbar(GuiGraphics graphics, int mouseX, int mouseY) {
         if (this.contentHeight() > this.getHeight()) {
            int trackX = this.scrollbarX();
            int trackY = this.scrollbarTop();
            int trackH = this.scrollbarHeight();
            int thumbY = this.scrollbarThumbY();
            int thumbH = this.scrollbarThumbHeight();
            boolean hovered = this.isOverScrollbarThumb(mouseX, mouseY) || this.draggingScrollbar;
            graphics.fill(trackX, trackY, trackX + 4, trackY + trackH, 822083583);
            graphics.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, hovered ? -1056964609 : -2130706433);
         }
      }

      private void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
         PonderItemGridScreen.HitResult hit = this.hitTest(mouseX, mouseY);
         if (hit != null) {
            PonderItemGridScreen.ItemEntry entry = hit.entry;
            ClientLevel level = Minecraft.getInstance().level;
            TooltipContext tooltipContext = level == null ? TooltipContext.EMPTY : TooltipContext.of(level);
            List<Component> tooltip = new ArrayList<>(entry.stack.getTooltipLines(tooltipContext, Minecraft.getInstance().player, TooltipFlag.NORMAL));
            tooltip.add(Component.literal(""));
            if (entry.sceneKeys.size() == 1) {
               String key = entry.sceneKeys.get(0);
               String prefix = PonderItemGridScreen.this.mode == PonderItemGridScreen.Mode.MULTI_SELECT
                     && PonderItemGridScreen.this.selectedSceneKeys.contains(key)
                  ? "✓ "
                  : "";
               tooltip.add(
                  Component.literal(prefix)
                     .append(Component.translatable("ponderer.ui.item_grid.scene_label", new Object[]{key}))
                     .withStyle(ChatFormatting.AQUA)
               );
            } else {
               tooltip.add(Component.translatable("ponderer.ui.item_grid.scenes_count", new Object[]{entry.sceneKeys.size()}).withStyle(ChatFormatting.AQUA));

               for (int i = 0; i < Math.min(entry.sceneKeys.size(), 8); i++) {
                  String key = entry.sceneKeys.get(i);
                  String prefix = PonderItemGridScreen.this.mode == PonderItemGridScreen.Mode.MULTI_SELECT
                        && PonderItemGridScreen.this.selectedSceneKeys.contains(key)
                     ? "✓ "
                     : "  ";
                  tooltip.add(Component.literal(prefix + key).withStyle(ChatFormatting.DARK_AQUA));
               }

               if (entry.sceneKeys.size() > 8) {
                  tooltip.add(Component.literal("  ...").withStyle(ChatFormatting.GRAY));
               }
            }

            if (entry.nbtFilter != null) {
               tooltip.add(Component.translatable("ponderer.ui.item_list.nbt_filter").withStyle(ChatFormatting.GOLD));
               tooltip.add(Component.literal(PonderItemGridScreen.formatNbtFilterForTooltip(entry)).withStyle(ChatFormatting.GRAY));
            }

            graphics.renderComponentTooltip(PonderItemGridScreen.this.font, tooltip, mouseX, mouseY);
         }
      }
   }

   enum GroupMode {
      BY_PACK,
      BY_ITEM;
   }

   private record HitResult(PonderItemGridScreen.ItemEntry entry) {
   }

   record ItemEntry(ItemStack stack, @Nullable String nbtFilter, List<String> sceneKeys) {
   }

   public enum Mode {
      LIST,
      SINGLE_SELECT,
      MULTI_SELECT;
   }

   record PackSection(@Nullable String packPrefix, String displayName, List<PonderItemGridScreen.ItemEntry> entries) {
   }

   static class SceneIdListScreen extends AbstractDeclarativeListScreen {
      private final List<String> sceneKeys;
      private final PonderItemGridScreen.SceneIdListScreen.SelectMode selectMode;
      @Nullable
      private final Consumer<String> onSelectSingle;
      @Nullable
      private final Consumer<Set<String>> onSelectMulti;
      private final Runnable onCancel;
      private final Set<String> selectedItems = new HashSet<>();
      private final Set<String> baselineSelectedItems = new HashSet<>();

      SceneIdListScreen(List<String> sceneKeys, Consumer<String> onSelect, Runnable onCancel) {
         this(sceneKeys, PonderItemGridScreen.SceneIdListScreen.SelectMode.SINGLE, onSelect, null, null, onCancel);
      }

      SceneIdListScreen(
         List<String> sceneKeys,
         PonderItemGridScreen.SceneIdListScreen.SelectMode selectMode,
         @Nullable Consumer<String> onSelectSingle,
         @Nullable Consumer<Set<String>> onSelectMulti,
         @Nullable Set<String> preSelected,
         Runnable onCancel
      ) {
         super(null, "ponderer.ui.scope.editor", "ponderer.ui.item_grid.select_scene_id", 300);
         this.sceneKeys = sceneKeys;
         this.selectMode = selectMode;
         this.onSelectSingle = onSelectSingle;
         this.onSelectMulti = onSelectMulti;
         this.onCancel = onCancel;
         if (preSelected != null) {
            this.selectedItems.addAll(preSelected);
            this.baselineSelectedItems.addAll(preSelected);
         }
      }

      @Override
      protected void init() {
         super.init();
         if (this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.SINGLE) {
            if (this.saveChanges != null) {
               this.saveChanges.visible = false;
               this.saveChanges.active = false;
            }

            if (this.discardChanges != null) {
               this.discardChanges.visible = false;
               this.discardChanges.active = false;
            }
         }
      }

      @Override
      protected void collectEntries(List<Entry> entries) {
         if (this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI) {
            entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.item_grid.sub_selected", this.selectedItems.size(), this.sceneKeys.size())));
         }

         for (String sceneKey : this.sceneKeys) {
            entries.add(
               new FullButtonListEntry(
                  () -> this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI && this.selectedItems.contains(sceneKey)
                     ? "[x] " + sceneKey
                     : sceneKey,
                  null,
                  () -> this.handleSceneClick(sceneKey),
                  () -> this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI && this.selectedItems.contains(sceneKey)
                     ? 8454143
                     : 16777215,
                  () -> true
               )
            );
         }
      }

      @Override
      protected boolean hasUnsavedChanges() {
         return this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI && !this.baselineSelectedItems.equals(this.selectedItems);
      }

      @Override
      protected int getUnsavedChangeCount() {
         return this.hasUnsavedChanges() ? 1 : 0;
      }

      @Override
      protected boolean saveEdits() {
         if (this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.MULTI && this.onSelectMulti != null) {
            this.onSelectMulti.accept(new HashSet<>(this.selectedItems));
            return true;
         } else {
            return false;
         }
      }

      @Override
      protected void discardEdits() {
         this.selectedItems.clear();
         this.selectedItems.addAll(this.baselineSelectedItems);
         this.rebuildListPreservingScroll();
      }

      @Override
      protected int getEntryHeight() {
         return 24;
      }

      @Override
      protected void attemptBackToParent() {
         if (!this.hasUnsavedChanges()) {
            this.onCancel.run();
         } else {
            this.showLeavingPrompt(response -> {
               if (response != Response.Cancel) {
                  if (response == Response.Confirm) {
                     if (!this.saveEdits()) {
                        return;
                     }
                  } else {
                     this.discardEdits();
                  }

                  this.onCancel.run();
               }
            });
         }
      }

      private void handleSceneClick(String sceneKey) {
         if (this.selectMode == PonderItemGridScreen.SceneIdListScreen.SelectMode.SINGLE) {
            if (this.onSelectSingle != null) {
               this.onSelectSingle.accept(sceneKey);
            }
         } else {
            if (this.selectedItems.contains(sceneKey)) {
               this.selectedItems.remove(sceneKey);
            } else {
               this.selectedItems.add(sceneKey);
            }

            this.rebuildListPreservingScroll();
         }
      }

      public enum SelectMode {
         SINGLE,
         MULTI;
      }
   }
}

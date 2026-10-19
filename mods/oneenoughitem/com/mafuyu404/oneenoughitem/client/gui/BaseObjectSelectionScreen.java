package com.mafuyu404.oneenoughitem.client.gui;

import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mafuyu404.oneenoughitem.client.gui.components.ItemGridWidget;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.util.JECHCompat;
import com.mafuyu404.oneenoughitem.util.ReplacementControl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class BaseObjectSelectionScreen<T> extends Screen {
   protected static final int ITEMS_PER_PAGE = 45;
   protected static final int GRID_WIDTH = 9;
   protected static final int GRID_HEIGHT = 5;
   protected final ReplacementEditorScreen parent;
   protected final boolean isForMatch;
   protected EditBox searchBox;
   protected Button sortButton;
   protected Button prevPageButton;
   protected Button nextPageButton;
   protected Button backButton;
   protected ItemGridWidget itemGrid;
   protected List<T> allObjects;
   protected List<T> filteredObjects;
   protected int currentPage = 0;
   protected final Set<String> selectedIds = new HashSet<>();
   protected Button confirmSelectionButton;
   protected Button clearSelectionButton;
   protected BaseObjectSelectionScreen.SortMode sortMode = BaseObjectSelectionScreen.SortMode.NAME;
   private int lastSearchLength = 0;

   protected BaseObjectSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch, Component title) {
      super(title);
      this.parent = parent;
      this.isForMatch = isForMatch;
      this.allObjects = new ArrayList<>();
      this.filteredObjects = new ArrayList<>();
      this.updateGrid();
   }

   protected abstract Component sortLabel();

   protected abstract List<T> loadAllObjects();

   protected abstract String getId(T var1);

   protected String getName(T obj) {
      return this.getId(obj);
   }

   protected abstract void renderObject(T var1, GuiGraphics var2, int var3, int var4);

   protected abstract void onSelectSingle(String var1);

   protected abstract boolean isSelectable(String var1, boolean var2);

   protected void init() {
      int centerX = this.width / 2;
      this.searchBox = new EditBox(this.font, centerX - 80, 15, 160, 18, Component.translatable("gui.oneenoughitem.search"));
      this.addRenderableWidget(this.searchBox);
      this.sortButton = GuiUtils.createButton(this.getSortLabel(this.sortMode), btn -> this.onSort(), centerX + 90, 15, 70, 18);
      this.addRenderableWidget(this.sortButton);
      int gridStartX = centerX - 81;
      int gridStartY = 45;
      this.itemGrid = new ItemGridWidget(gridStartX, gridStartY, 9, 5, this::handleGridItemClick);
      this.addRenderableWidget(this.itemGrid);
      int buttonY = gridStartY + 90 + 10;
      this.prevPageButton = GuiUtils.createButton(Component.literal("<"), btn -> this.previousPage(), centerX - 80, buttonY, 25, 18);
      this.addRenderableWidget(this.prevPageButton);
      this.nextPageButton = GuiUtils.createButton(Component.literal(">"), btn -> this.nextPage(), centerX + 55, buttonY, 25, 18);
      this.addRenderableWidget(this.nextPageButton);
      this.backButton = GuiUtils.createButton(Component.translatable("gui.oneenoughitem.back"), btn -> this.onClose(), centerX - 40, buttonY, 80, 18);
      this.addRenderableWidget(this.backButton);
      if (this.isForMatch) {
         this.confirmSelectionButton = GuiUtils.createButton(
            Component.translatable("gui.oneenoughitem.add_selected"), btn -> this.confirmSelected(), centerX - 120, buttonY + 25, 100, 18
         );
         this.addRenderableWidget(this.confirmSelectionButton);
         this.clearSelectionButton = GuiUtils.createButton(Component.translatable("gui.oneenoughitem.clear_selected"), btn -> {
            this.selectedIds.clear();
            this.updateGrid();
            this.updateConfirmButtonsVisibility();
         }, centerX + 20, buttonY + 25, 100, 18);
         this.addRenderableWidget(this.clearSelectionButton);
      }

      this.allObjects = this.loadAllObjects();
      this.filteredObjects = new ArrayList<>(this.allObjects);
      this.applySort();
      this.updateGrid();
      this.updateNavigationButtons();
      this.updateConfirmButtonsVisibility();
   }

   protected Component getSortLabel(BaseObjectSelectionScreen.SortMode mode) {
      return switch (mode) {
         case NAME -> Component.translatable("gui.oneenoughitem.sort.name");
         case MOD -> Component.translatable("gui.oneenoughitem.sort.mod");
         case ID -> Component.translatable("gui.oneenoughitem.sort.id");
      };
   }

   protected Comparator<T> getComparator(BaseObjectSelectionScreen.SortMode mode) {
      return (a, b) -> 0;
   }

   protected void onSort() {
      this.sortMode = switch (this.sortMode) {
         case NAME -> BaseObjectSelectionScreen.SortMode.MOD;
         case MOD -> BaseObjectSelectionScreen.SortMode.ID;
         case ID -> BaseObjectSelectionScreen.SortMode.NAME;
      };
      this.applySort();
      this.updateGrid();
      this.updateNavigationButtons();
      this.sortButton.setMessage(this.getSortLabel(this.sortMode));
   }

   protected void applySort() {
      Comparator<T> cmp = this.getComparator(this.sortMode);
      if (cmp != null) {
         this.filteredObjects = new ArrayList<>(this.filteredObjects);
         this.filteredObjects.sort(cmp);
      }
   }

   public void tick() {
      super.tick();
      if (this.searchBox.getValue().length() != this.getLastSearchLength()) {
         this.filterObjects();
         this.currentPage = 0;
         this.updateGrid();
         this.updateNavigationButtons();
      }
   }

   private int getLastSearchLength() {
      int current = this.lastSearchLength;
      this.lastSearchLength = this.searchBox.getValue().length();
      return current;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      super.render(graphics, mouseX, mouseY, partialTick);
      int totalPages = (this.filteredObjects.size() + 45 - 1) / 45;
      String pageInfo = this.currentPage + 1 + " / " + Math.max(1, totalPages);
      int buttonY = 145;
      graphics.drawCenteredString(this.font, pageInfo, this.width / 2, buttonY + 25, 16777215);
      String count = this.filteredObjects.size() + " entries";
      graphics.drawString(this.font, count, 10, buttonY + 35, 16777215);
   }

   protected void filterObjects() {
      String search = this.searchBox.getValue().toLowerCase();
      if (search.isEmpty()) {
         this.filteredObjects = new ArrayList<>(this.allObjects);
      } else {
         this.filteredObjects = this.allObjects.stream().filter(obj -> {
            String id = Optional.ofNullable(this.getName((T)obj)).orElse("").toLowerCase();
            return JECHCompat.contains(id, search);
         }).toList();
      }
   }

   protected void updateGrid() {
   }

   protected void updateNavigationButtons() {
      int totalPages = (this.filteredObjects.size() + 45 - 1) / 45;
      this.prevPageButton.active = this.currentPage > 0;
      this.nextPageButton.active = this.currentPage < totalPages - 1;
   }

   protected void previousPage() {
      if (this.currentPage > 0) {
         this.currentPage--;
         this.updateGrid();
         this.updateNavigationButtons();
      }
   }

   protected void nextPage() {
      int totalPages = (this.filteredObjects.size() + 45 - 1) / 45;
      if (this.currentPage < totalPages - 1) {
         this.currentPage++;
         this.updateGrid();
         this.updateNavigationButtons();
      }
   }

   protected void confirmSelected() {
      if (this.isForMatch && !this.selectedIds.isEmpty()) {
         int added = 0;

         for (String id : new ArrayList<>(this.selectedIds)) {
            if (this.isSelectable(id, true)) {
               this.onSelectSingle(id);
               added++;
            }
         }

         this.selectedIds.clear();
         this.updateGrid();
         this.updateConfirmButtonsVisibility();
         this.onClose();
      }
   }

   protected void updateConfirmButtonsVisibility() {
      if (this.isForMatch) {
         boolean hasSelection = !this.selectedIds.isEmpty();
         if (this.confirmSelectionButton != null) {
            this.confirmSelectionButton.active = hasSelection;
         }

         if (this.clearSelectionButton != null) {
            this.clearSelectionButton.active = hasSelection;
         }
      }
   }

   public void onClose() {
      this.minecraft.setScreen(this.parent);
   }

   private void handleGridItemClick(ItemStack stack) {
      String id = ReplacementControl.withSkipReplacement(() -> DomainRegistry.current().dataIdFromItem(stack.getItem()));
      if (id != null) {
         if (this.isForMatch && hasControlDown()) {
            if (this.selectedIds.contains(id)) {
               this.selectedIds.remove(id);
            } else {
               this.selectedIds.add(id);
            }

            this.updateGrid();
            this.updateConfirmButtonsVisibility();
         } else if (this.isSelectable(id, this.isForMatch)) {
            this.onSelectSingle(id);
            this.onClose();
         }
      }
   }

   protected enum SortMode {
      NAME,
      MOD,
      ID;
   }
}

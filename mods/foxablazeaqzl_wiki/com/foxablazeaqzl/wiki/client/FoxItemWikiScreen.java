package com.foxablazeaqzl.wiki.client;

import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public final class FoxItemWikiScreen extends SimpleScreen implements IScrollBar {
   public static final Component TITLE = Component.translatable("foxablazeaqzl_wiki.menu.item_archive");
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "textures/gui/item_wiki.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final Component PLACEHOLDER = Component.translatable("foxablazeaqzl_wiki.placeholder");
   private static final int GUI_Y_OFFSET = 2;
   private static final int WIKI_TITLE_X = 121;
   private static final int WIKI_TITLE_Y = 33;
   private static final int WIKI_TITLE_WIDTH = 102;
   private static final int WIKI_TITLE_HEIGHT = 15;
   private static final int WIKI_DESCRIPTION_X = 121;
   private static final int WIKI_DESCRIPTION_Y = 59;
   private static final int WIKI_DESCRIPTION_WIDTH = 102;
   private static final int WIKI_DESCRIPTION_HEIGHT = 95;
   private static final int SEARCH_FIELD_X = 19;
   private static final int SEARCH_FIELD_Y = 50;
   private static final int ITEM_LIST_X = 6;
   private static final int ITEM_LIST_Y = 66;
   private static final int ITEM_LIST_HOVER_X = 5;
   private static final int ITEM_LIST_HOVER_Y = 65;
   private static final int ITEM_LIST_HOVER_WIDTH = 104;
   private static final int ITEM_LIST_HOVER_HEIGHT = 93;
   private static final int GROUP_ARROW_X = 78;
   private static final int GROUP_TEXT_WIDTH = 72;
   private static final float DESCRIPTION_TEXT_SCALE = 0.8F;
   private final List<FoxItemWikiData.Entry> items = new ArrayList<>();
   private final List<FoxItemWikiData.Entry> filteredItems = new ArrayList<>();
   private final List<FoxItemWikiScreen.ItemListRow> displayRows = new ArrayList<>();
   private FoxItemWikiData.Entry selectedItem;
   private EditBox searchField;
   private String nameFilter = "";
   private float scrollOffset;
   private boolean scrolling;
   private boolean schematicsExpanded;
   private int listStartIndex;
   private int textStartIndex;

   public FoxItemWikiScreen() {
      super(TITLE, 256, 167);
      this.shouldFade = false;
   }

   protected void init() {
      super.init();
      guiTop += 2;
      guiCenterY += 2;
      this.shouldRenderWidgets = false;
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.textStartIndex = 0;
      ScreenHelper.getMenuTabs(4, guiLeft, guiTop).stream().filter(button -> button.getX() >= guiLeft + 200).forEach(x$0 -> {
         SimpleButton var10000 = (SimpleButton)this.addRenderableWidget(x$0);
      });
      FoxWikiTabButtons.disableOriginalThirdRightTab(this, guiLeft, guiTop);
      this.addRenderableWidget(FoxWikiTabButtons.createMainButton(guiLeft, guiTop));
      if (FoxWikiTabButtons.isTrBeyondLoaded()) {
         FoxWikiTabButtons.disableTrBeyondTab(this, guiLeft, guiTop);
         this.addRenderableWidget(FoxWikiTabButtons.createTrBeyondButton(guiLeft, guiTop));
      }

      this.searchField = new EditBox(this.font, guiLeft + 19, guiTop + 50, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(value -> {
         this.nameFilter = value;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFilteredItems();
      });
      this.addRenderableWidget(this.searchField);
      this.searchField.setValue(this.nameFilter == null ? "" : this.nameFilter);
      FoxItemWikiUnlockState.observeInventory(this.minecraft == null ? null : this.minecraft.player);
      this.updateVisibleItems();
      this.updateFilteredItems();
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      FoxWikiTopTabs.render(graphics, this, guiLeft, guiTop, mouseX, mouseY);
      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10, 16777215);
      if (FoxWikiTabButtons.isTrBeyondLoaded()) {
         FoxWikiTabButtons.renderTrBeyondTabBase(graphics, guiLeft, guiTop, mouseX, mouseY);
      }

      super.renderWidgets(graphics, mouseX, mouseY, partialTick);
      FoxWikiTabButtons.renderCurrentWikiTab(graphics, this, guiLeft, guiTop, mouseX, mouseY);
      if (FoxItemWikiUnlockState.observeInventory(this.minecraft == null ? null : this.minecraft.player)) {
         this.updateVisibleItems();
         this.updateFilteredItems();
      }

      this.renderItemList(graphics, mouseX, mouseY);
      this.renderScrollBar(graphics, mouseX, mouseY);
      this.renderWikiText(graphics);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (FoxWikiTopTabs.clicked(mouseX, mouseY, button, guiLeft, guiTop, FoxWikiTopTabs.Tab.ITEM)) {
         return true;
      }

      if (this.clickedScrollBar(mouseX, mouseY)) {
         return true;
      }

      if (!RenderHelper.mouseOver(
         mouseX,
         mouseY,
         this.searchField.getX(),
         this.searchField.getX() + this.searchField.getWidth(),
         this.searchField.getY(),
         this.searchField.getY() + this.searchField.getHeight()
      )) {
         this.searchField.setFocused(false);
      }

      int x = guiLeft + 6;
      int y = guiTop + 66;
      int lastVisibleIndex = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.displayRows.size());

      for (int i = this.listStartIndex; i < lastVisibleIndex; i++) {
         int rowY = y + (i - this.listStartIndex) * 13;
         if (RenderHelper.mouseOver(mouseX, mouseY, x, x + 89, rowY, rowY + 13)) {
            FoxItemWikiScreen.ItemListRow row = this.displayRows.get(i);
            if (row.isGroup()) {
               this.schematicsExpanded = !this.schematicsExpanded;
               this.setScrollOffset(0.0F);
               this.setListStartIndex(0);
               this.updateFilteredItems();
               ScreenHelper.clicked();
               return true;
            }

            this.selectedItem = row.entry();
            this.textStartIndex = 0;
            ScreenHelper.clicked();
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mouseY) || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 121, guiLeft + 121 + 102, guiTop + 59, guiTop + 59 + 95)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
         return true;
      } else if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 5, guiLeft + 5 + 104, guiTop + 65, guiTop + 65 + 93)) {
         this.scrolledScrollBar(deltaY);
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void renderItemList(GuiGraphics graphics, int mouseX, int mouseY) {
      int x = guiLeft + 6;
      int y = guiTop + 66;
      int lastVisibleIndex = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.displayRows.size());

      for (int i = this.listStartIndex; i < lastVisibleIndex; i++) {
         int rowY = y + (i - this.listStartIndex) * 13;
         FoxItemWikiScreen.ItemListRow row = this.displayRows.get(i);
         MutableComponent name = Component.literal(row.label());
         boolean hovered = RenderHelper.mouseOver(mouseX, mouseY, x, x + 89, rowY, rowY + 13);
         boolean selected = !row.isGroup() && this.selectedItem != null && this.selectedItem.id().equals(row.entry().id());
         graphics.blit(ABILITY_BAR, x, rowY, 0.0F, !hovered && !selected ? 0.0F : 13.0F, 89, 13, 89, 26);
         if (row.isGroup()) {
            RenderHelper.drawScrollingText(graphics, this.font, name, x + 3, rowY + 3, 72, 16777215);
            graphics.drawString(this.font, this.getSchematicsArrow(), x + 78, rowY + 3, 16777215, false);
         } else {
            RenderHelper.drawScrollingText(graphics, this.font, name, x + 3, rowY + 3, 83, 16777215);
         }

         if (hovered) {
            this.setTooltipForNextRenderPass(name);
         }
      }
   }

   private void renderWikiText(GuiGraphics graphics) {
      if (this.selectedItem != null) {
         MutableComponent title = Component.literal(this.selectedItem.name());
         int titleY = guiTop + 33 + (15 - 9) / 2;
         RenderHelper.drawCenteredText(graphics, this.font, title, guiLeft + 121, titleY, 102, 16777215);
         List<FoxItemWikiScreen.DescriptionLine> lines = this.splitDescription(
            this.selectedItem.description().isBlank() ? PLACEHOLDER.getString() : this.selectedItem.description()
         );
         int start = Math.clamp(this.textStartIndex, 0, Math.max(0, lines.size() - 1));
         this.textStartIndex = start;
         this.renderDescriptionLines(graphics, lines, start);
      }
   }

   private List<FoxItemWikiScreen.DescriptionLine> splitDescription(String text) {
      List<FoxItemWikiScreen.DescriptionLine> lines = new ArrayList<>();
      boolean hasContentLine = false;
      boolean lastRenderedLineBlank = false;
      int splitWidth = Math.max(1, Math.round(127.5F));

      for (String rawLine : text.split("\\R", -1)) {
         String line = rawLine.trim();
         if (line.isBlank()) {
            if (hasContentLine && !lines.isEmpty()) {
               lines.add(new FoxItemWikiScreen.DescriptionLine(Component.empty().getVisualOrderText()));
               lastRenderedLineBlank = true;
            }
         } else {
            if (isItemSectionHeader(line) && hasContentLine && !lastRenderedLineBlank) {
               lines.add(new FoxItemWikiScreen.DescriptionLine(Component.empty().getVisualOrderText()));
               lastRenderedLineBlank = true;
            }

            for (FormattedCharSequence sequence : this.font.split(Component.literal(line), splitWidth)) {
               lines.add(new FoxItemWikiScreen.DescriptionLine(sequence));
            }

            hasContentLine = true;
            lastRenderedLineBlank = false;
         }
      }

      return lines;
   }

   private static boolean isItemSectionHeader(String line) {
      String lowerLine = line.toLowerCase(Locale.ROOT);
      return line.startsWith("来源：")
         || line.startsWith("用途：")
         || line.startsWith("状态：")
         || lowerLine.startsWith("obtainment:")
         || lowerLine.startsWith("usage:")
         || lowerLine.startsWith("status:");
   }

   private void renderDescriptionLines(GuiGraphics graphics, List<FoxItemWikiScreen.DescriptionLine> lines, int start) {
      int x = guiLeft + 121;
      int y = guiTop + 59;
      int bottom = y + 95;
      graphics.enableScissor(x, y, x + 102, bottom);

      for (int i = start; i < lines.size() && y < bottom; i++) {
         FoxItemWikiScreen.DescriptionLine line = lines.get(i);
         int lineHeight = Math.max(1, Math.round(9.0F * 0.8F));
         graphics.pose().pushPose();
         graphics.pose().translate(x, y, 0.0F);
         graphics.pose().scale(0.8F, 0.8F, 1.0F);
         graphics.drawString(this.font, line.text(), 0, 0, 16777215, false);
         graphics.pose().popPose();
         y += lineHeight;
      }

      graphics.disableScissor();
   }

   private void updateFilteredItems() {
      this.filteredItems.clear();
      this.displayRows.clear();
      String filter = this.nameFilter == null ? "" : this.nameFilter.trim().toLowerCase(Locale.ROOT);
      this.filteredItems.addAll(this.items.stream().filter(entry -> entry.matches(filter)).toList());
      this.rebuildDisplayRows(!filter.isEmpty());
      this.scrolledScrollBar(0.0);
   }

   private void rebuildDisplayRows(boolean forceExpandedGroups) {
      List<FoxItemWikiData.Entry> schematics = this.filteredItems.stream().filter(FoxItemWikiData.Entry::isSchematic).toList();
      boolean schematicsGroupAdded = false;

      for (FoxItemWikiData.Entry entry : this.filteredItems) {
         if (!entry.isSchematic()) {
            this.displayRows.add(FoxItemWikiScreen.ItemListRow.item(entry));
         } else if (!schematicsGroupAdded) {
            schematicsGroupAdded = true;
            this.displayRows.add(FoxItemWikiScreen.ItemListRow.group(entry.categoryName()));
            if (this.schematicsExpanded || forceExpandedGroups) {
               schematics.stream().map(FoxItemWikiScreen.ItemListRow::item).forEach(this.displayRows::add);
            }
         }
      }
   }

   private String getSchematicsArrow() {
      return !this.schematicsExpanded && (this.nameFilter == null || this.nameFilter.trim().isEmpty()) ? "▶" : "▼";
   }

   private void updateVisibleItems() {
      this.items.clear();
      this.items.addAll(FoxItemWikiData.getEntries().stream().filter(FoxItemWikiUnlockState::isVisible).toList());
      if (this.selectedItem != null && this.items.stream().noneMatch(entry -> entry.id().equals(this.selectedItem.id()))) {
         this.selectedItem = null;
         this.textStartIndex = 0;
      }
   }

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 66;
   }

   public int getScrollBarTotalSpace() {
      return 91;
   }

   public int getScrollBarListSize() {
      return this.displayRows.size();
   }

   public int getScrollBarRenderCount() {
      return 7;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setListStartIndex(int value) {
      this.listStartIndex = value;
   }

   public void setScrolling(boolean value) {
      this.scrolling = value;
   }

   public void setScrollOffset(float value) {
      this.scrollOffset = value;
   }

   private record DescriptionLine(FormattedCharSequence text) {
   }

   private record ItemListRow(FoxItemWikiData.Entry entry, String label) {
      static FoxItemWikiScreen.ItemListRow item(FoxItemWikiData.Entry entry) {
         return new FoxItemWikiScreen.ItemListRow(entry, entry.name());
      }

      static FoxItemWikiScreen.ItemListRow group(String label) {
         return new FoxItemWikiScreen.ItemListRow(null, label);
      }

      boolean isGroup() {
         return this.entry == null;
      }
   }
}

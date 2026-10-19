package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.client.Textures;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractCompoundFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.DumpedFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.client.FTBFilterSystemClient;
import dev.ftb.mods.ftbfiltersystem.client.GuiUtil;
import dev.ftb.mods.ftbfiltersystem.client.SelectionPanel;
import dev.ftb.mods.ftbfiltersystem.network.SyncFilterMessage;
import dev.ftb.mods.ftbfiltersystem.registry.item.SmartFilterItem;
import dev.ftb.mods.ftbfiltersystem.util.FilterParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.LinearLayout.Orientation;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;

public class FilterScreen extends AbstractFilterScreen {
   private static SelectionPanel selectionPanel = null;
   private final SmartFilter filter;
   private final String origFilterStr;
   private final InteractionHand interactionHand;
   private int leftPos;
   private int topPos;
   private int guiWidth;
   private int guiHeight;
   private FilterScreen.FilterList filterList;
   private Button addFilterBtn;
   private Button deleteFilterBtn;
   private Button configFilterBtn;
   private Button titleEditBtn;
   private EditBox titleEditBox;
   private SmartFilter newSelection = null;
   private Component newTitle = null;
   private boolean showingTitleEdit = false;

   public FilterScreen(Component component, SmartFilter filter, InteractionHand interactionHand) {
      super(component);
      this.interactionHand = interactionHand;
      selectionPanel = null;
      this.filter = filter;
      this.origFilterStr = filter.asString(FTBFilterSystemClient.registryAccess());
   }

   protected void init() {
      super.init();
      this.setupGuiDimensions();
      this.getSelectionPanel().visitWidgets(x$0 -> {
         AbstractWidget var10000 = (AbstractWidget)this.addWidget(x$0);
      });
      this.filterList = new FilterScreen.FilterList(this.minecraft, this.topPos + 20, this.getListWidth(), this.getListHeight());
      this.filterList.setX(this.leftPos + 8);
      this.addWidget(this.filterList);
      this.titleEditBtn = (Button)this.addRenderableWidget(
         new ImageButton(
            this.leftPos, this.topPos + 3, 16, 16, new WidgetSprites(Textures.EDIT_BUTTON, Textures.EDIT_BUTTON_HI), b -> this.showingTitleEdit = true
         )
      );
      this.titleEditBox = (EditBox)this.addRenderableWidget(
         new EditBox(this.font, this.leftPos + 5, this.topPos + 4, this.getListWidth(), 9 + 4, Component.empty())
      );
      this.titleEditBox.visible = false;
      this.titleEditBox.setValue(this.title.getString());
      int buttonWidth = this.guiWidth - this.getListWidth() - 25;
      LinearLayout buttonPanel = new LinearLayout(this.leftPos + this.getListWidth() + 15, this.topPos + 20, Orientation.VERTICAL).spacing(2);
      this.addFilterBtn = (Button)buttonPanel.addChild(
         Button.builder(Component.translatable("ftbfiltersystem.gui.add"), b -> this.getSelectionPanel().setVisible(true)).width(buttonWidth).build()
      );
      this.deleteFilterBtn = (Button)buttonPanel.addChild(
         Button.builder(Component.translatable("ftbfiltersystem.gui.delete"), b -> this.deleteSelectedFilter(true)).width(buttonWidth).build()
      );
      this.configFilterBtn = (Button)buttonPanel.addChild(
         Button.builder(Component.translatable("ftbfiltersystem.gui.configure"), b -> this.configureSelectedFilter(false)).width(buttonWidth).build()
      );
      buttonPanel.arrangeElements();
      buttonPanel.visitWidgets(x$0 -> {
         AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(x$0);
      });
      LinearLayout bottomPanel = new LinearLayout(this.leftPos, this.topPos + this.guiHeight - 25, Orientation.HORIZONTAL);
      ((FrameLayout)bottomPanel.addChild(new FrameLayout(this.guiWidth / 2, 20)))
         .addChild(Button.builder(CommonComponents.GUI_DONE, b -> this.applyChanges()).size(80, 20).build());
      ((FrameLayout)bottomPanel.addChild(new FrameLayout(this.guiWidth / 2, 20)))
         .addChild(Button.builder(CommonComponents.GUI_CANCEL, b -> this.closeWithConfirmation()).size(80, 20).build());
      bottomPanel.arrangeElements();
      bottomPanel.visitWidgets(x$0 -> {
         AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(x$0);
      });
      if (this.filterList.getSelected() == null) {
         this.findAndSelect(this.filter);
      } else {
         this.setupButtons();
      }
   }

   private void applyNewTitle() {
      this.newTitle = Component.literal(this.titleEditBox.getValue());
      if (this.newTitle.getString().equals(this.title.getString())) {
         this.newTitle = null;
      }

      this.showingTitleEdit = false;
   }

   private SelectionPanel getSelectionPanel() {
      if (selectionPanel == null) {
         selectionPanel = new SelectionPanel(this.font, this::createNewFilter, this.height - (this.topPos + 20) - 5);
      }

      return selectionPanel;
   }

   private void setupButtons() {
      FilterScreen.FilterList.FilterEntry selected = (FilterScreen.FilterList.FilterEntry)this.filterList.getSelected();
      this.addFilterBtn.active = selected != null && this.canAddMoreFilters(selected.dumpedFilter.filter());
      this.deleteFilterBtn.active = selected != null && selected.dumpedFilter.filter().getParent() != null;
      this.configFilterBtn.active = selected != null && selected.dumpedFilter.filter().isConfigurable();
   }

   private boolean canAddMoreFilters(SmartFilter filter) {
      SmartFilter.Compound parent = filter instanceof AbstractCompoundFilter acf ? acf : filter.getParent();
      return parent != null && parent.getChildren().size() < parent.maxChildren();
   }

   private void applyChanges() {
      NetworkManager.sendToServer(
         new SyncFilterMessage(
            this.filter.asString(FTBFilterSystemClient.registryAccess()),
            this.newTitle == null ? Optional.empty() : Optional.of(this.newTitle.getString()),
            this.interactionHand
         )
      );
      this.onClose();
      if (this.changesHaveBeenMade()) {
         Minecraft.getInstance()
            .player
            .displayClientMessage(Component.translatable("ftbfiltersystem.message.changes_saved").withStyle(ChatFormatting.GREEN), true);
      }
   }

   private boolean changesHaveBeenMade() {
      return !this.filter.asString(FTBFilterSystemClient.registryAccess()).equals(this.origFilterStr) || this.newTitle != null;
   }

   private void createNewFilter(ResourceLocation filterId) {
      this.getSelectionPanel().setVisible(false);
      if (this.filterList.getSelected() != null) {
         SmartFilter selectedFilter = ((FilterScreen.FilterList.FilterEntry)this.filterList.getSelected()).dumpedFilter.filter();
         SmartFilter.Compound parent = selectedFilter instanceof AbstractCompoundFilter acf ? acf : selectedFilter.getParent();
         if (parent != null) {
            FTBFilterSystemAPI.api().createDefaultFilter(parent, filterId).ifPresent(newFilter -> {
               parent.getChildren().add(newFilter);
               this.filterList.addChildren();
               this.filterList.findAndSelect(newFilter);
               this.configureSelectedFilter(true);
            });
         }
      }
   }

   @Override
   public void deleteSelectedFilter(boolean rebuildList) {
      if (this.filterList.getSelected() != null) {
         SmartFilter subFilter = ((FilterScreen.FilterList.FilterEntry)this.filterList.getSelected()).dumpedFilter.filter();
         SmartFilter parent = subFilter.getParent();
         if (parent instanceof SmartFilter.Compound compound) {
            List<SmartFilter> l = compound.getChildren().stream().filter(f -> f != subFilter).toList();
            compound.getChildren().clear();
            compound.getChildren().addAll(l);
            if (rebuildList) {
               this.filterList.addChildren();
               this.filterList.findAndSelect(parent);
            }
         }
      }
   }

   private void configureSelectedFilter(boolean deleteOnCancel) {
      FilterScreen.FilterList.FilterEntry selected = (FilterScreen.FilterList.FilterEntry)this.filterList.getSelected();
      if (selected != null) {
         FTBFilterSystemClient.INSTANCE.openFilterConfigScreen(selected.dumpedFilter.filter(), this, deleteOnCancel);
      }
   }

   @Override
   public <T extends SmartFilter> void replaceFilter(T filter, T newFilter) {
      if (filter.getParent() != null) {
         List<SmartFilter> children = filter.getParent().getChildren();
         int idx = children.indexOf(filter);
         if (idx >= 0) {
            children.set(idx, newFilter);
            this.newSelection = newFilter;
         }
      }
   }

   public void tick() {
      if (!(this.minecraft.player.getItemInHand(this.interactionHand).getItem() instanceof SmartFilterItem)) {
         this.onClose();
      } else if (this.showingTitleEdit && !this.titleEditBox.isVisible()) {
         this.titleEditBox.visible = true;
         this.titleEditBtn.visible = false;
         if (Screen.hasShiftDown()) {
            this.titleEditBox.moveCursorToEnd(false);
            this.titleEditBox.setHighlightPos(0);
         }

         this.setFocused(this.titleEditBox);
      } else if (!this.showingTitleEdit && this.titleEditBox.isVisible()) {
         this.titleEditBox.visible = false;
         this.titleEditBtn.visible = true;
         this.setFocused(this.filterList);
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      if (this.guiHeight > 0) {
         GuiUtil.drawPanel(
            guiGraphics,
            new Rect2i(this.leftPos + 7, this.topPos + 19, this.getListWidth() + 2, this.getListHeight() + 1),
            -6250336,
            -6250336,
            GuiUtil.BorderStyle.INSET,
            1
         );
         this.filterList.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
         Component displayTitle = this.newTitle == null ? this.title : this.newTitle;
         guiGraphics.drawString(this.font, displayTitle, this.leftPos + 8, this.topPos + 7, 4210752, false);
         this.titleEditBtn.setX(this.leftPos + this.font.width(displayTitle) + 8);
      }

      if (this.getSelectionPanel().isVisible()) {
         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
         guiGraphics.fill(this.leftPos, this.topPos, this.leftPos + this.guiWidth, this.topPos + this.guiHeight, -1608507360);
         this.getSelectionPanel().positionAndRender(guiGraphics, this.addFilterBtn.getY(), this.addFilterBtn.getX() - 10, mouseX, mouseY, partialTick);
         guiGraphics.pose().popPose();
      }
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      guiGraphics.blitSprite(Textures.BACKGROUND, this.leftPos, this.topPos, this.guiWidth, this.guiHeight);
   }

   public void onClose() {
      this.getSelectionPanel().setVisible(false);
      super.onClose();
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifier) {
      if (keyCode != 256 && !this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
         if (keyCode == 257 || keyCode == 335) {
            if (this.titleEditBox.canConsumeInput()) {
               this.applyNewTitle();
               return true;
            }

            if (Screen.hasShiftDown()) {
               this.applyChanges();
               return true;
            }
         }

         return super.keyPressed(keyCode, scanCode, modifier);
      } else {
         if (this.getSelectionPanel().isVisible()) {
            this.getSelectionPanel().setVisible(false);
            return true;
         }

         if (this.titleEditBox.canConsumeInput()) {
            if (keyCode == 256) {
               this.titleEditBox.setValue(this.newTitle == null ? this.title.getString() : this.newTitle.getString());
               this.showingTitleEdit = false;
            } else {
               this.titleEditBox.keyPressed(keyCode, scanCode, modifier);
            }

            return true;
         } else {
            this.closeWithConfirmation();
            return true;
         }
      }
   }

   private void closeWithConfirmation() {
      if (this.changesHaveBeenMade()) {
         this.newSelection = this.filterList.getSelectedFilter();
         this.minecraft
            .setScreen(
               new ConfirmScreen(
                  this::exitCallback,
                  Component.translatable("ftbfiltersystem.gui.changes_made"),
                  Component.translatable("ftbfiltersystem.gui.changes_made.question")
               )
            );
      } else {
         this.onClose();
      }
   }

   private void exitCallback(boolean confirmed) {
      if (confirmed) {
         this.onClose();
      } else {
         this.minecraft.setScreen(this);
         this.findAndSelect(this.newSelection);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.getSelectionPanel().isVisible() && !this.getSelectionPanel().isMouseOver(mouseX, mouseY)) {
         this.getSelectionPanel().setVisible(false);
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   private int getListWidth() {
      return this.guiWidth - 100;
   }

   private int getListHeight() {
      return this.guiHeight - 50;
   }

   public void resize(Minecraft minecraft, int width, int height) {
      this.width = width;
      this.height = height;
      this.setupGuiDimensions();
      SmartFilter selected = this.filterList.getSelected() == null
         ? null
         : ((FilterScreen.FilterList.FilterEntry)this.filterList.getSelected()).dumpedFilter.filter();
      this.repositionElements();
      this.getSelectionPanel().resize(height - (this.topPos + 20) - 5);
      this.findAndSelect(selected);
   }

   private void setupGuiDimensions() {
      this.guiWidth = this.width * 2 / 3;
      this.guiHeight = this.height * 3 / 4;
      this.leftPos = (this.width - this.guiWidth) / 2;
      this.topPos = (this.height - this.guiHeight) / 2;
   }

   @Override
   public void findAndSelect(@Nullable SmartFilter filter) {
      if (filter != null) {
         this.filterList.findAndSelect(filter);
      }
   }

   private class FilterList extends ObjectSelectionList<FilterScreen.FilterList.FilterEntry> {
      private static final int ELEMENT_HEIGHT = 12;
      private FilterScreen.FilterList.FilterEntry dragging = null;
      private SmartFilter.Compound dragTarget = null;

      public FilterList(Minecraft minecraft, int y, int width, int height) {
         super(minecraft, width, height, y, 12);
         this.addChildren();
      }

      protected void renderListBackground(GuiGraphics guiGraphics) {
      }

      private void addChildren() {
         List<FilterScreen.FilterList.FilterEntry> entries = new ArrayList<>();
         List<DumpedFilter> dumped = FTBFilterSystemAPI.api().dump(FilterScreen.this.filter);
         dumped.forEach(entry -> entries.add(new FilterScreen.FilterList.FilterEntry(entry)));
         this.children().clear();
         this.children().addAll(entries);
         if (FilterScreen.this.newSelection != null) {
            this.findAndSelect(FilterScreen.this.newSelection);
            FilterScreen.this.newSelection = null;
         }
      }

      private SmartFilter getSelectedFilter() {
         return this.getSelected() == null ? null : ((FilterScreen.FilterList.FilterEntry)this.getSelected()).dumpedFilter.filter();
      }

      public int getRowWidth() {
         return FilterScreen.this.getListWidth();
      }

      protected int getScrollbarPosition() {
         return FilterScreen.this.leftPos + this.width + 8;
      }

      public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
      }

      public void setSelected(@Nullable FilterScreen.FilterList.FilterEntry entry) {
         super.setSelected(entry);
         FilterScreen.this.setupButtons();
      }

      public boolean mouseReleased(double mouseX, double mouseY, int btn) {
         if (this.dragging != null && this.dragTarget != null) {
            FilterParser.parseFilterList(
                  this.dragTarget, this.dragging.dumpedFilter.filter().asString(FTBFilterSystemClient.registryAccess()), FTBFilterSystemClient.registryAccess()
               )
               .stream()
               .findFirst()
               .ifPresent(newFilter -> {
                  this.dragTarget.getChildren().add(newFilter);
                  FilterScreen.this.deleteSelectedFilter(false);
                  this.addChildren();
                  this.findAndSelect(newFilter);
               });
         }

         this.dragging = null;
         this.dragTarget = null;
         return super.mouseReleased(mouseX, mouseY, btn);
      }

      public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
         if (this.dragging == null) {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
         }

         FilterScreen.FilterList.FilterEntry entry = (FilterScreen.FilterList.FilterEntry)this.getEntryAtPosition(pMouseX, pMouseY);
         if (entry != null
            && entry.dumpedFilter.filter() instanceof SmartFilter.Compound compound
            && (!(this.dragging.dumpedFilter.filter() instanceof SmartFilter.Compound) || entry.dumpedFilter.indent() <= this.dragging.dumpedFilter.indent())
            && compound != this.dragging.dumpedFilter.filter()
            && compound != this.dragging.dumpedFilter.filter().getParent()
            && compound.getChildren().size() < compound.maxChildren()) {
            this.dragTarget = compound;
         } else {
            this.dragTarget = null;
         }

         return true;
      }

      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         if (keyCode != 32 && keyCode != 257) {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }

         FilterScreen.this.configureSelectedFilter(false);
         return true;
      }

      protected void renderDecorations(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
         if (this.dragging != null) {
            FilterScreen.FilterList.FilterEntry entry = (FilterScreen.FilterList.FilterEntry)this.getEntryAtPosition(pMouseX, pMouseY);
            if (entry != null && entry.dumpedFilter.filter() != this.dragging.dumpedFilter.filter()) {
               int w = FilterScreen.this.font.width(this.dragging.dumpedFilter.filter().getDisplayName());
               guiGraphics.fill(pMouseX, pMouseY - 6 + 1, pMouseX + w + 10, pMouseY + 6, -1058934275);
               guiGraphics.renderOutline(pMouseX, pMouseY - 6 + 1, w + 10, 12, -1069531072);
               guiGraphics.drawString(
                  FilterScreen.this.font, this.dragging.dumpedFilter.filter().getDisplayName(), pMouseX + 5, pMouseY - 6 + 3, -1069531072, false
               );
            }
         }
      }

      protected void renderSelection(GuiGraphics guiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {
         int minX = this.getX() + (this.width - pWidth) / 2;
         int maxX = this.getX() + (this.width + pWidth) / 2;
         int col = this.isFocused() ? -1969667 : -5851964;
         GuiUtil.drawPanel(guiGraphics, new Rect2i(minX + 1, pTop - 2, maxX - minX - 2, pHeight + 3), col, -12164180, GuiUtil.BorderStyle.PLAIN, 1);
      }

      protected void renderListSeparators(GuiGraphics guiGraphics) {
      }

      private void findAndSelect(SmartFilter filter) {
         this.children().stream().filter(entry -> entry.dumpedFilter.filter() == filter).findFirst().ifPresent(this::setSelected);
      }

      private class FilterEntry extends Entry<FilterScreen.FilterList.FilterEntry> {
         private final DumpedFilter dumpedFilter;
         private long lastClickTime;

         public FilterEntry(DumpedFilter dumpedFilter) {
            this.dumpedFilter = dumpedFilter;
         }

         public void render(
            GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick
         ) {
            int labelLeft = left + this.dumpedFilter.indent() * 10;
            int yBase = top + height / 2;
            if (this.dumpedFilter.filter() == FilterList.this.dragTarget && FilterList.this.dragging.dumpedFilter.filter() != FilterList.this.dragTarget) {
               guiGraphics.fill(labelLeft - 2, top - 3, labelLeft + FilterScreen.this.font.width(this.getLabel()) + 2, top + 9, -3479106);
               guiGraphics.renderOutline(labelLeft - 2, top - 3, FilterScreen.this.font.width(this.getLabel()) + 4, 9 + 4, -13604796);
            }

            guiGraphics.drawString(FilterScreen.this.font, this.getLabel(), labelLeft, top, 4210752, false);
            if (index > 0) {
               guiGraphics.hLine(labelLeft - 8, labelLeft - 2, yBase, -11513728);
               int yOff = this.calcYoffset(index);
               guiGraphics.vLine(labelLeft - 8, yBase, yBase - yOff, -11513728);
            }
         }

         private int calcYoffset(int index) {
            int res = 0;

            for (int i = index - 1; i >= 0; i--) {
               res += FilterList.this.itemHeight;
               if (((FilterScreen.FilterList.FilterEntry)FilterList.this.children().get(i)).dumpedFilter.indent() < this.dumpedFilter.indent()) {
                  break;
               }
            }

            return res - 4;
         }

         public boolean mouseClicked(double x, double y, int button) {
            FilterList.this.setSelected(this);
            if (this.dumpedFilter.filter().getParent() != null) {
               FilterList.this.dragging = this;
            }

            if (Util.getMillis() - this.lastClickTime < 250L) {
               FilterScreen.this.configureSelectedFilter(false);
            } else {
               this.lastClickTime = Util.getMillis();
            }

            return true;
         }

         private Component getLabel() {
            Component disp = this.dumpedFilter.filter().getDisplayName();
            if (this.dumpedFilter.filter() instanceof SmartFilter.Compound) {
               return disp;
            }

            Component text = this.dumpedFilter.filter().getDisplayArg(FilterList.this.minecraft.level.registryAccess());
            return disp.copy().append(" ").append(text.copy().withStyle(ChatFormatting.DARK_BLUE));
         }

         public Component getNarration() {
            return Component.translatable("narrator.select", new Object[]{this.getLabel()});
         }
      }
   }
}

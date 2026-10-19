package xaero.hud.category.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.IXaeroMinimap;
import xaero.hud.category.ObjectCategory;
import xaero.hud.category.ui.entry.ConnectionLineType;
import xaero.hud.category.ui.entry.EditorListEntry;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.node.EditorCategoryNode;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.EditorSettingsNode;
import xaero.lib.client.gui.ScreenBase;
import xaero.lib.client.gui.widget.Tooltip;

public abstract class GuiCategoryEditor<C extends ObjectCategory<?, C>, ED extends EditorCategoryNode<C, SD, ED>, CB extends ObjectCategory.Builder<C, CB>, SD extends EditorSettingsNode<?>, SDB extends EditorSettingsNode.Builder<SD, SDB>, EDB extends EditorCategoryNode.Builder<C, ED, SD, SDB, EDB>>
   extends ScreenBase {
   public static final Component READ_ONLY_COMPONENT = Component.translatable("gui.xaero_category_editor_read_only").withStyle(ChatFormatting.YELLOW);
   private static final int FRAME_TOP_SIZE = 32;
   private static final int FRAME_BOTTOM_SIZE = 48;
   public static final int ROW_HEIGHT = 24;
   public static final int ROW_WIDTH = 220;
   private GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList rowList;
   private final EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> dataConverter;
   private ED editorData;
   protected ED cutCategory;
   protected ED cutCategorySuper;
   protected final IXaeroMinimap modMain;
   protected final boolean readOnly;

   protected GuiCategoryEditor(
      IXaeroMinimap modMain,
      Screen parent,
      Screen escape,
      Component title,
      EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> dataConverter,
      boolean readOnly
   ) {
      super(parent, escape, title);
      this.modMain = modMain;
      this.dataConverter = dataConverter;
      this.readOnly = readOnly;
      this.editorData = this.constructEditorData(dataConverter);
   }

   protected abstract ED constructEditorData(EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> var1);

   protected abstract ED constructDefaultData(EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> var1);

   protected abstract void onConfigConfirmed(C var1);

   public void init() {
      super.init();
      if (this.readOnly) {
         this.addRenderableWidget(
            Button.builder(Component.translatable("gui.xaero_back"), b -> this.cancel(this.parent))
               .bounds(this.width / 2 - 100, this.height - 32, 200, 20)
               .build()
         );
      } else {
         this.addRenderableWidget(
            Button.builder(Component.translatable("gui.xaero_category_settings_cancel"), b -> this.minecraft.setScreen(new ConfirmScreen(result -> {
                  if (result) {
                     this.cancel(this.parent);
                  } else {
                     this.minecraft.setScreen(this);
                  }
               }, Component.translatable("gui.xaero_category_settings_cancel_confirm"), Component.literal(""))))
               .bounds(this.width / 2 + 5, this.height - 32, 150, 20)
               .build()
         );
         this.addRenderableWidget(
            Button.builder(Component.translatable("gui.xaero_category_settings_confirm"), b -> this.confirm())
               .bounds(this.width / 2 - 155, this.height - 32, 150, 20)
               .build()
         );
      }

      Button resetButton = Button.builder(Component.translatable("gui.xaero_category_settings_reset"), b -> {
         if (!this.readOnly) {
            this.minecraft.setScreen(new ConfirmScreen(result -> {
               if (result) {
                  this.editorData = this.constructDefaultData(this.dataConverter);
               }

               this.minecraft.setScreen(this);
            }, Component.translatable("gui.xaero_category_settings_reset_confirm1"), Component.translatable("gui.xaero_category_settings_reset_confirm2")));
         }
      }).bounds(6, 6, 120, 20).build();
      resetButton.active = !this.readOnly;
      this.addRenderableWidget(resetButton);
      this.rowList = new GuiCategoryEditor.SettingRowList(this.dataConverter);
      this.addWidget(this.rowList);
   }

   private void confirm() {
      super.onExit(this.parent);
      if (!this.readOnly) {
         this.onConfigConfirmed(this.dataConverter.getConfiguredBuilder(this.editorData).build());
      }
   }

   public void onExit(Screen screen) {
      if (this.readOnly) {
         super.onExit(screen);
      } else {
         this.minecraft.setScreen(new ConfirmScreen(result -> {
            if (result) {
               this.confirm();
            }

            this.cancel(screen);
         }, Component.translatable("gui.xaero_category_settings_save_confirm"), Component.literal("")) {
            public boolean keyPressed(int i, int j, int k) {
               return i == 256 ? true : super.keyPressed(i, j, k);
            }
         });
      }
   }

   protected void cancel(Screen screen) {
      super.onExit(screen);
   }

   public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
      super.renderBackground(guiGraphics, i, j, f);
      this.rowList.render(guiGraphics, i, j, f);
      guiGraphics.drawCenteredString(this.minecraft.font, this.title, this.width / 2, 5, 16777215);
   }

   public void render(GuiGraphics guiGraphics, int i, int j, float f) {
      PoseStack poseStack = guiGraphics.pose();
      super.render(guiGraphics, i, j, f);
      if (this.readOnly) {
         guiGraphics.drawString(this.font, READ_ONLY_COMPONENT, this.width - 5 - this.font.width(READ_ONLY_COMPONENT), 5, -1);
      }

      if (this.rowList.hovered != null) {
         Supplier<Tooltip> tooltipSupplier = this.rowList.hovered.getTooltipSupplier();
         if (tooltipSupplier != null) {
            Tooltip tooltip = tooltipSupplier.get();
            if (tooltip != null) {
               poseStack.pushPose();
               poseStack.translate(0.0, 0.0, 0.1);
               tooltip.drawBox(guiGraphics, i, j, this.width, this.height);
               poseStack.popPose();
            }
         }
      }
   }

   public boolean keyPressed(int i, int j, int k) {
      return this.rowList.isFocused() && i == 257 && this.rowList.confirmSelection() ? true : super.keyPressed(i, j, k);
   }

   public void tick() {
      this.rowList.tick();
      super.tick();
   }

   public GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList getRowList() {
      return this.rowList;
   }

   public boolean mouseClicked(double d, double e, int i) {
      return super.mouseClicked(d, e, i) ? true : this.rowList.mouseClicked(d, e, i);
   }

   public boolean mouseReleased(double d, double e, int i) {
      return super.mouseReleased(d, e, i) ? true : this.rowList.mouseReleased(d, e, i);
   }

   public boolean mouseDragged(double d, double e, int i, double f, double g) {
      return super.mouseDragged(d, e, i, f, g) ? true : this.rowList.mouseDragged(d, e, i, f, g);
   }

   public boolean mouseScrolled(double d, double e, double f, double g) {
      return super.mouseScrolled(d, e, f, g) ? true : this.rowList.mouseScrolled(d, e, f, g);
   }

   public class SettingRowList extends ObjectSelectionList<GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry> {
      private EditorNode lastExpandedData;
      private boolean restoreScrollAfterUpdate;
      private EditorListEntry hovered;
      private final EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> dataConverter;
      public final boolean readOnly;
      private static final Component USAGE_NARRATION = Component.translatable("narration.selection.usage");
      private static final Component LEFT_RIGHT_USAGE = Component.translatable("narration.xaero_ui_list_left_right_usage");

      public SettingRowList(EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> dataConverter) {
         super(GuiCategoryEditor.this.minecraft, GuiCategoryEditor.this.width, Math.max(4, GuiCategoryEditor.this.height - 48 - 32), 32, 24);
         this.dataConverter = dataConverter;
         this.readOnly = GuiCategoryEditor.this.readOnly;
         this.updateEntries();
      }

      protected boolean isSelectedItem(int i) {
         return false;
      }

      public boolean hasCut() {
         if (GuiCategoryEditor.this.cutCategory == null) {
            return false;
         }

         if (GuiCategoryEditor.this.cutCategorySuper.getSubCategories().contains(GuiCategoryEditor.this.cutCategory)) {
            return true;
         }

         this.setCutCategory(null, null);
         return false;
      }

      public ED getCut() {
         return GuiCategoryEditor.this.cutCategory;
      }

      public boolean isCut(ED category) {
         return GuiCategoryEditor.this.cutCategory == category ? this.hasCut() : false;
      }

      public void setCutCategory(ED cutCategory, ED cutCategorySuper) {
         GuiCategoryEditor.this.cutCategory = cutCategory;
         GuiCategoryEditor.this.cutCategorySuper = cutCategorySuper;
      }

      public void pasteTo(ED destination) {
         if (GuiCategoryEditor.this.cutCategory != null) {
            if (destination != GuiCategoryEditor.this.cutCategory && destination != GuiCategoryEditor.this.cutCategorySuper) {
               destination.getExpandAction(this).run();
               this.setLastExpandedData(GuiCategoryEditor.this.cutCategory);
               GuiCategoryEditor.this.cutCategorySuper.getSubCategories().remove(GuiCategoryEditor.this.cutCategory);
               destination.getSubCategories().add(0, GuiCategoryEditor.this.cutCategory);
               this.setCutCategory(null, null);
            } else {
               this.setCutCategory(null, null);
               this.updateEntries();
            }
         }
      }

      public boolean isFocused() {
         return GuiCategoryEditor.this.getFocused() == this;
      }

      public void setLastExpandedData(EditorNode lastExpandedData) {
         this.lastExpandedData = lastExpandedData;
      }

      public void restoreScrollAfterUpdate() {
         this.restoreScrollAfterUpdate = true;
      }

      public void updateEntries() {
         double scrollBackup = this.getScrollAmount();
         this.clearEntries();
         GuiCategoryEditor.this.editorData.setExpanded(true);
         this.addEntriesForExpanded(GuiCategoryEditor.this.editorData, null);
         if (this.getSelected() != null) {
            this.centerScrollOn((GuiCategoryEditor.SettingRowList.Entry)this.getSelected());
         }

         if (this.restoreScrollAfterUpdate) {
            this.setScrollAmount(scrollBackup);
            this.restoreScrollAfterUpdate = false;
         }
      }

      private void addEntriesForExpanded(EditorNode data, EditorNode parent) {
         int nextIndex = this.children().size();
         List<EditorNode> subExpandables = data.getSubNodes();
         if (subExpandables != null) {
            EditorNode expandedData = null;

            for (EditorNode sed : subExpandables) {
               if (sed.isExpanded()) {
                  expandedData = sed;
                  break;
               }
            }

            EditorListRootEntry wrappedEntry = data.getListEntryFactory()
               .get(data, parent, nextIndex, nextIndex == 0 ? ConnectionLineType.NONE : ConnectionLineType.PATH, this, this.width, expandedData == null);
            GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry currentEntry = new GuiCategoryEditor.SettingRowList.Entry(wrappedEntry, nextIndex++);
            this.addEntry(currentEntry);
            if (data == this.lastExpandedData) {
               this.setFocused(currentEntry);
            }

            if (expandedData != null) {
               this.addEntriesForExpanded(expandedData, data);
            } else {
               if (this.lastExpandedData == null && data.isExpanded()) {
                  this.setFocused(currentEntry);
               }

               boolean first = true;

               for (EditorNode sed : subExpandables) {
                  wrappedEntry = sed.getListEntryFactory()
                     .get(sed, data, nextIndex, first ? ConnectionLineType.HEAD_LEAF : ConnectionLineType.TAIL_LEAF, this, this.width, false);
                  GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry leafEntry = new GuiCategoryEditor.SettingRowList.Entry(
                     wrappedEntry, nextIndex++
                  );
                  this.addEntry(leafEntry);
                  if (sed == this.lastExpandedData) {
                     this.setFocused(leafEntry);
                  }

                  first = false;
               }
            }
         }
      }

      public boolean mouseClicked(double d, double e, int i) {
         if (!this.isMouseOver(d, e)) {
            this.setFocused(null);
         }

         return super.mouseClicked(d, e, i);
      }

      public void mouseMoved(double d, double e) {
         if (this.getSelected() != null) {
            ((GuiCategoryEditor.SettingRowList.Entry)this.getSelected()).mouseMoved(d, e);
         }

         super.mouseMoved(d, e);
      }

      public boolean keyReleased(int i, int j, int k) {
         return this.getSelected() != null && ((GuiCategoryEditor.SettingRowList.Entry)this.getSelected()).keyReleased(i, j, k)
            ? true
            : super.keyReleased(i, j, k);
      }

      public boolean charTyped(char c, int i) {
         if (this.getSelected() != null) {
            boolean result = ((GuiCategoryEditor.SettingRowList.Entry)this.getSelected()).charTyped(c, i);
            if (result) {
               return true;
            }
         }

         return super.charTyped(c, i);
      }

      public void tick() {
         if (this.getSelected() != null) {
            ((GuiCategoryEditor.SettingRowList.Entry)this.getSelected()).tick();
         }
      }

      public boolean confirmSelection() {
         GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry entry = (GuiCategoryEditor.SettingRowList.Entry)this.getSelected();
         if (entry == null) {
            return false;
         }

         EditorListEntry selectedSubEntry = entry.wrappedEntry.confirmSelection();
         return selectedSubEntry != null;
      }

      public void setFocused(GuiEventListener guiEventListener) {
         if (this.children().contains(guiEventListener) && this.getFocused() != guiEventListener) {
            if (this.getSelected() != null) {
               ((GuiCategoryEditor.SettingRowList.Entry)this.getSelected()).wrappedEntry.unfocusRecursively();
            }

            if (this.getFocused() != null) {
               ((GuiCategoryEditor.SettingRowList.Entry)this.getFocused()).setFocused(false);
            }

            GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry entry = (GuiCategoryEditor.SettingRowList.Entry)guiEventListener;
            if (entry != null) {
               entry.wrappedEntry.focusFirstRecursively();
            }

            super.setFocused(guiEventListener);
            if (guiEventListener == null) {
               this.setSelected((GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry)null);
            }

            this.narrateSelection();
         }
      }

      public void setSelected(GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry entry) {
         super.setSelected(entry);
      }

      public int getRowWidth() {
         return this.width;
      }

      protected int getScrollbarPosition() {
         return this.width / 2 + 164;
      }

      public void narrateSelection() {
         GuiCategoryEditor.this.afterKeyboardAction();
      }

      public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
         super.updateWidgetNarration(narrationElementOutput);
         if (this.isFocused()) {
            narrationElementOutput.add(NarratedElementType.USAGE, new Component[]{USAGE_NARRATION, LEFT_RIGHT_USAGE});
         }
      }

      public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
         this.hovered = null;
         super.renderWidget(guiGraphics, i, j, f);
      }

      public EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> getDataConverter() {
         return this.dataConverter;
      }

      public class Entry extends net.minecraft.client.gui.components.ObjectSelectionList.Entry<GuiCategoryEditor<C, ED, CB, SD, SDB, EDB>.SettingRowList.Entry> {
         private EditorListRootEntry wrappedEntry;
         private int index;
         private int lastX;
         private int lastY;

         public Entry(EditorListRootEntry entryInfo, int index) {
            this.wrappedEntry = entryInfo;
            this.index = index;
         }

         public void render(
            GuiGraphics guiGraphics, int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTicks
         ) {
            PoseStack poseStack = guiGraphics.pose();
            this.lastX = x;
            this.lastY = y;
            poseStack.pushPose();
            poseStack.translate(x, y, 0.0F);
            boolean includesSelected = SettingRowList.this.getSelected() == this;
            this.wrappedEntry.preRender(guiGraphics, includesSelected, true);
            EditorListEntry hoveredInRow = this.wrappedEntry
               .render(
                  guiGraphics,
                  index,
                  rowWidth,
                  rowHeight,
                  mouseX - x - this.wrappedEntry.getEntryRelativeX(),
                  mouseY - y - this.wrappedEntry.getEntryRelativeY(),
                  isMouseOver,
                  partialTicks,
                  GuiCategoryEditor.this.font,
                  mouseX,
                  mouseY,
                  includesSelected,
                  true
               );
            this.wrappedEntry.postRender(guiGraphics);
            poseStack.popPose();
            if (hoveredInRow != null) {
               SettingRowList.this.hovered = hoveredInRow;
            }
         }

         public boolean mouseClicked(double mouseX, double mouseY, int i) {
            SettingRowList.this.setFocused(this);
            double relativeMouseX = mouseX - this.lastX - this.wrappedEntry.getEntryRelativeX();
            double relativeMouseY = mouseY - this.lastY - this.wrappedEntry.getEntryRelativeY();
            this.wrappedEntry.mouseClicked(this, relativeMouseX, relativeMouseY, i);
            return true;
         }

         public boolean mouseReleased(double mouseX, double mouseY, int i) {
            double relativeMouseX = mouseX - this.lastX - this.wrappedEntry.getEntryRelativeX();
            double relativeMouseY = mouseY - this.lastY - this.wrappedEntry.getEntryRelativeY();
            this.wrappedEntry.mouseReleased(relativeMouseX, relativeMouseY, i);
            return super.mouseReleased(mouseX, mouseY, i);
         }

         public boolean mouseScrolled(double mouseX, double mouseY, double f, double g) {
            double relativeMouseX = mouseX - this.lastX - this.wrappedEntry.getEntryRelativeX();
            double relativeMouseY = mouseY - this.lastY - this.wrappedEntry.getEntryRelativeY();
            return this.wrappedEntry.mouseScrolled(relativeMouseX, relativeMouseY, f, g) ? true : super.mouseScrolled(mouseX, mouseY, f, g);
         }

         public boolean mouseDragged(double mouseX, double mouseY, int i, double f, double g) {
            double relativeMouseX = mouseX - this.lastX - this.wrappedEntry.getEntryRelativeX();
            double relativeMouseY = mouseY - this.lastY - this.wrappedEntry.getEntryRelativeY();
            return this.wrappedEntry.mouseDragged(relativeMouseX, relativeMouseY, i, f, g) ? true : super.mouseDragged(mouseX, mouseY, i, f, g);
         }

         public boolean keyPressed(int i, int j, int k) {
            return this.wrappedEntry.keyPressed(i, j, k, true) ? true : super.keyPressed(i, j, k);
         }

         public boolean keyReleased(int i, int j, int k) {
            return this.wrappedEntry.keyReleased(i, j, k) ? true : super.keyReleased(i, j, k);
         }

         public boolean charTyped(char c, int i) {
            return this.wrappedEntry.charTyped(c, i) ? true : super.charTyped(c, i);
         }

         public void setFocused(boolean bl) {
            this.wrappedEntry.setFocused(bl);
            super.setFocused(bl);
         }

         public void tick() {
            this.wrappedEntry.tick();
         }

         public Component getNarration() {
            String selectedNarrationString = this.wrappedEntry.getSubNarration();
            return selectedNarrationString == null ? Component.literal("") : Component.translatable("narrator.select", new Object[]{selectedNarrationString});
         }
      }
   }
}

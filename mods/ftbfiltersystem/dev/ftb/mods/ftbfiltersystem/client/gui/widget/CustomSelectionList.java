package dev.ftb.mods.ftbfiltersystem.client.gui.widget;

import dev.ftb.mods.ftbfiltersystem.client.GuiUtil;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.Rect2i;

public abstract class CustomSelectionList<T extends net.minecraft.client.gui.components.ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
   public CustomSelectionList(Minecraft minecraft, int width, int height, int y, int elementHeight) {
      super(minecraft, width, height, y, elementHeight);
      this.addChildren();
   }

   protected abstract List<T> buildChildrenList();

   public final void addChildren() {
      this.children().clear();
      this.children().addAll(this.buildChildrenList());
      this.setScrollAmount(0.0);
   }

   public void selectAndCenter(T entry) {
      this.setSelected(entry);
      this.centerScrollOn(entry);
   }

   public int getRowWidth() {
      return this.width - 8;
   }

   protected int getScrollbarPosition() {
      return this.getX() + this.width - 6;
   }

   public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
   }

   protected void renderListItems(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      int w = this.getMaxScroll() > 0 ? this.width - 6 : this.width;
      GuiUtil.drawPanel(guiGraphics, new Rect2i(this.getX(), this.getY(), w, this.height + 4), -6250336, -6250336, GuiUtil.BorderStyle.INSET, 1);
      super.renderListItems(guiGraphics, mouseX, mouseY, partialTick);
   }

   protected void renderSelection(GuiGraphics guiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {
      int minX = this.getX() + (this.width - pWidth) / 2;
      int maxX = this.getX() + (this.width + pWidth) / 2 + (this.getMaxScroll() > 0 ? -3 : 3);
      int col = this.isFocused() ? -1969667 : -3614227;
      GuiUtil.drawPanel(guiGraphics, new Rect2i(minX + 1, pTop - 2, maxX - minX - 2, pHeight + 4), col, -12164180, GuiUtil.BorderStyle.PLAIN, 1);
   }

   public abstract static class Entry<E extends CustomSelectionList.Entry<E>> extends net.minecraft.client.gui.components.ObjectSelectionList.Entry<E> {
      private long lastClickTime;

      protected abstract boolean onMouseClick(double var1, double var3, int var5, boolean var6);

      public boolean mouseClicked(double x, double y, int button) {
         if (Util.getMillis() - this.lastClickTime < 250L) {
            return this.onMouseClick(x, y, button, true);
         }

         this.lastClickTime = Util.getMillis();
         return this.onMouseClick(x, y, button, false);
      }
   }
}

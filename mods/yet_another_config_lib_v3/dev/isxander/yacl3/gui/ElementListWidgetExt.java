package dev.isxander.yacl3.gui;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ElementListWidgetExt<E extends ElementListWidgetExt.Entry<E>> extends ContainerObjectSelectionList<E> implements LayoutElement {
   protected static final int SCROLLBAR_WIDTH = 6;
   private double smoothScrollAmount = this.scrollAmount();
   private boolean returnSmoothAmount = false;
   private final boolean doSmoothScrolling;
   private boolean usingScrollbar;

   public ElementListWidgetExt(Minecraft client, int x, int y, int width, int height, boolean smoothScrolling) {
      super(client, width, x, y, height);
      this.doSmoothScrolling = smoothScrolling;
      this.setRenderHeader(false, 0);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
      double scroll = vertical + horizontal;
      this.setScrollAmount(this.scrollAmount() - scroll * 20.0);
      return true;
   }

   protected int scrollBarX() {
      return this.getScrollbarPosition();
   }

   protected int getScrollbarPosition() {
      return this.getX() + this.getWidth() - 6;
   }

   protected int maxScrollAmount() {
      return this.getMaxPosition();
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
      if (this.usingScrollbar) {
         this.resetSmoothScrolling();
      }

      this.smoothScrollAmount = Mth.lerp(delta * 0.5, this.smoothScrollAmount, this.scrollAmount());
      if (Math.abs(this.smoothScrollAmount - this.scrollAmount()) < 1.0E-5) {
         this.smoothScrollAmount = this.scrollAmount();
      }

      this.returnSmoothAmount = true;
      graphics.enableScissor(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
      super.renderWidget(graphics, mouseX, mouseY, delta);
      graphics.disableScissor();
      this.returnSmoothAmount = false;
   }

   protected boolean isValidMouseClick(int button) {
      return button == 0 || button == 1 || button == 2;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && mouseX >= this.scrollBarX() && mouseX < this.scrollBarX() + 6) {
         this.usingScrollbar = true;
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0) {
         this.usingScrollbar = false;
      }

      return super.mouseReleased(mouseX, mouseY, button);
   }

   public void updateDimensions(ScreenRectangle rectangle) {
      this.setX(rectangle.left());
      this.setY(rectangle.top());
      this.setWidth(rectangle.width());
      this.setHeight(rectangle.height());
   }

   protected double scrollAmount() {
      return this.getScrollAmount();
   }

   public double getScrollAmount() {
      return this.returnSmoothAmount && this.doSmoothScrolling ? this.smoothScrollAmount : super.getScrollAmount();
   }

   protected void resetSmoothScrolling() {
      this.smoothScrollAmount = super.getScrollAmount();
   }

   @Nullable
   protected E getEntryAtPosition(double x, double y) {
      y += this.scrollAmount();
      if (!(x < this.getX()) && !(x > this.getX() + this.getWidth())) {
         int currentY = this.getY() - this.headerHeight + 4;

         for (E entry : this.children()) {
            if (y >= currentY && y <= currentY + entry.getItemHeight()) {
               return entry;
            }

            currentY += entry.getItemHeight();
         }

         return null;
      } else {
         return null;
      }
   }

   protected int contentHeight() {
      return this.getMaxPosition();
   }

   protected int getMaxPosition() {
      return this.children().stream().mapToInt(ElementListWidgetExt.Entry::getItemHeight).sum() + this.headerHeight;
   }

   protected void centerScrollOn(E entry) {
      double d = this.height / -2.0;

      for (int i = 0; i < this.children().indexOf(entry) && i < this.getItemCount(); i++) {
         d += ((ElementListWidgetExt.Entry)this.children().get(i)).getItemHeight();
      }

      this.setScrollAmount(d);
   }

   protected int getRowTop(int index) {
      int integer = this.getY() + 4 - (int)this.scrollAmount() + this.headerHeight;

      for (int i = 0; i < this.children().size() && i < index; i++) {
         integer += ((ElementListWidgetExt.Entry)this.children().get(i)).getItemHeight();
      }

      return integer;
   }

   protected void ensureVisible(E entry) {
      int entryIndex = this.children().indexOf(entry);
      int top = this.getRowTop(entryIndex);
      int j = top - this.getY() - 4 - entry.getItemHeight();
      if (j < 0) {
         this.setScrollAmount(this.scrollAmount() + j);
      }

      int k = this.getY() + this.getHeight() - top - entry.getItemHeight() * 2;
      if (k < 0) {
         this.setScrollAmount(this.scrollAmount() - k);
      }
   }

   protected void renderListItems(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
      int left = this.getRowLeft();
      int right = this.getRowWidth();
      int count = this.getItemCount();

      for (int i = 0; i < count; i++) {
         E entry = (E)this.children().get(i);
         int top = this.getRowTop(i);
         int bottom = top + entry.getItemHeight();
         int entryHeight = entry.getItemHeight() - 4;
         if (bottom >= this.getY() && top <= this.getY() + this.getHeight()) {
            this.renderItem(graphics, mouseX, mouseY, delta, i, left, top, right, entryHeight);
         }
      }
   }

   public void visitWidgets(Consumer<net.minecraft.client.gui.components.AbstractWidget> consumer) {
   }

   public abstract static class Entry<E extends ElementListWidgetExt.Entry<E>>
      extends net.minecraft.client.gui.components.ContainerObjectSelectionList.Entry<E> {
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         for (GuiEventListener child : this.children()) {
            if (child.mouseClicked(mouseX, mouseY, button)) {
               if (button == 0 || button == 1) {
                  this.setDragging(true);
               }

               return true;
            }
         }

         return false;
      }

      public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
         if (this.isDragging() && (button == 0 || button == 1)) {
            for (GuiEventListener child : this.children()) {
               if (child.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
                  return true;
               }
            }
         }

         return false;
      }

      public int getItemHeight() {
         return 22;
      }
   }
}

package dev.ftb.mods.ftblibrary.ui;

import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.util.Mth;

public class ScrollBar extends Widget {
   protected final ScrollBar.Plane plane;
   private final int scrollBarSize;
   private double value = 0.0;
   private double scrollStep = 20.0;
   private double grab = -10000.0;
   private double minValue = 0.0;
   private double maxValue = 100.0;
   private boolean canAlwaysScroll = false;
   private boolean canAlwaysScrollPlane = true;

   public ScrollBar(Panel parent, ScrollBar.Plane p, int ss) {
      super(parent);
      this.plane = p;
      this.scrollBarSize = Math.max(ss, 0);
   }

   public void setCanAlwaysScroll(boolean v) {
      this.canAlwaysScroll = v;
   }

   public void setCanAlwaysScrollPlane(boolean v) {
      this.canAlwaysScrollPlane = v;
   }

   public ScrollBar.Plane getPlane() {
      return this.plane;
   }

   public double getMinValue() {
      return this.minValue;
   }

   public void setMinValue(double min) {
      this.minValue = min;
      this.setValue(this.getValue());
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public void setMaxValue(double max) {
      this.maxValue = max;
      this.setValue(this.getValue());
   }

   public int getScrollBarSize() {
      return this.scrollBarSize;
   }

   @Override
   public boolean mousePressed(MouseButton button) {
      if (this.isMouseOver()) {
         this.grab = this.plane.isVertical
            ? this.getMouseY() - (this.getY() + this.getMappedValue(this.height - this.getScrollBarSize()))
            : this.getMouseX() - (this.getX() + this.getMappedValue(this.width - this.getScrollBarSize()));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double scroll) {
      if (scroll != 0.0 && this.canMouseScrollPlane() && this.canMouseScroll()) {
         this.setValue(this.getValue() - this.getScrollStep() * scroll);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void addMouseOverText(TooltipList list) {
      if (this.showValueOnMouseOver()) {
         Component t = this.getTitle();
         list.string(t.getContents() == PlainTextContents.EMPTY ? Double.toString(this.getValue()) : t + ": " + this.getValue());
      }

      if (Theme.renderDebugBoxes) {
         list.styledString("Size: " + this.getScrollBarSize(), ChatFormatting.DARK_GRAY);
         list.styledString("Max: " + this.getMaxValue(), ChatFormatting.DARK_GRAY);
         list.styledString("Value: " + this.getValue(), ChatFormatting.DARK_GRAY);
      }
   }

   public boolean showValueOnMouseOver() {
      return false;
   }

   @Override
   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      int scrollBarSize = this.getScrollBarSize();
      if (scrollBarSize > 0) {
         double v = this.getValue();
         if (this.grab != -10000.0) {
            if (isMouseButtonDown(MouseButton.LEFT)) {
               if (this.plane.isVertical) {
                  v = (this.getMouseY() - (y + this.grab)) * this.getMaxValue() / (this.height - scrollBarSize);
               } else {
                  v = (this.getMouseX() - (x + this.grab)) * this.getMaxValue() / (this.width - scrollBarSize);
               }
            } else {
               this.grab = -10000.0;
            }
         }

         this.setValue(v);
      }

      this.drawBackground(graphics, theme, x, y, this.width, this.height);
      if (scrollBarSize > 0) {
         if (this.plane.isVertical) {
            this.drawScrollBar(graphics, theme, x, (int)(y + this.getMappedValue(this.height - scrollBarSize)), this.width, scrollBarSize);
         } else {
            this.drawScrollBar(graphics, theme, (int)(x + this.getMappedValue(this.width - scrollBarSize)), y, scrollBarSize, this.height);
         }
      }
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      theme.drawScrollBarBackground(graphics, x, y, w, h, this.getWidgetType());
   }

   public void drawScrollBar(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      theme.drawScrollBar(graphics, x, y, w, h, WidgetType.mouseOver(this.grab != -10000.0), this.plane.isVertical);
   }

   public void onMoved() {
   }

   public boolean canMouseScrollPlane() {
      return this.canAlwaysScrollPlane || isShiftKeyDown() != this.plane.isVertical;
   }

   public boolean canMouseScroll() {
      return this.canAlwaysScroll || this.isMouseOver();
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(double v) {
      v = Mth.clamp(v, this.getMinValue(), this.getMaxValue());
      if (this.value != v) {
         this.value = v;
         this.onMoved();
      }
   }

   public double getMappedValue(double max) {
      return MathUtils.map(this.getMinValue(), this.getMaxValue(), 0.0, max, this.value);
   }

   public double getScrollStep() {
      return this.scrollStep;
   }

   public void setScrollStep(double s) {
      this.scrollStep = Math.max(0.0, s);
   }

   public enum Plane {
      HORIZONTAL(false),
      VERTICAL(true);

      public final boolean isVertical;

      Plane(boolean v) {
         this.isVertical = v;
      }
   }
}

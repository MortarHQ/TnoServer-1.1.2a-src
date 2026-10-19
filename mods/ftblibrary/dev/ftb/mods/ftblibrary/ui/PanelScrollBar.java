package dev.ftb.mods.ftblibrary.ui;

public class PanelScrollBar extends ScrollBar {
   private final Panel panel;

   public PanelScrollBar(Panel parent, ScrollBar.Plane plane, Panel p) {
      super(parent, plane, 0);
      this.panel = p;
   }

   public PanelScrollBar(Panel parent, Panel panel) {
      this(parent, ScrollBar.Plane.VERTICAL, panel);
   }

   public Panel getPanel() {
      return this.panel;
   }

   @Override
   public double getMinValue() {
      return 0.0;
   }

   @Override
   public void setMinValue(double min) {
   }

   @Override
   public double getMaxValue() {
      return this.plane.isVertical ? this.panel.getContentHeight() - this.panel.getHeight() : this.panel.getContentWidth() - this.panel.getWidth();
   }

   @Override
   public void setMaxValue(double max) {
      throw new UnsupportedOperationException("attempt to set max value of panel scrollbar");
   }

   @Override
   public double getScrollStep() {
      return this.panel.getScrollStep();
   }

   @Override
   public void setScrollStep(double s) {
      this.panel.setScrollStep(s);
   }

   @Override
   public int getScrollBarSize() {
      double max = this.getMaxValue();
      if (max <= 0.0) {
         return 0;
      }

      int size = this.plane.isVertical
         ? (int)(this.panel.height / (max + this.panel.height) * this.height)
         : (int)(this.panel.width / (max + this.panel.width) * this.width);
      return Math.max(size, 10);
   }

   @Override
   public void onMoved() {
      double value = this.getMaxValue() <= 0.0 ? 0.0 : this.getValue();
      if (this.plane.isVertical) {
         this.panel.setScrollY(value);
      } else {
         this.panel.setScrollX(value);
      }
   }

   @Override
   public boolean canMouseScroll() {
      return super.canMouseScroll() || this.panel.isMouseOver();
   }

   @Override
   public boolean shouldDraw() {
      return this.getScrollBarSize() > 0;
   }

   @Override
   public boolean isEnabled() {
      return this.getScrollBarSize() > 0;
   }
}

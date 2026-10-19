package team.creative.creativecore.common.gui;

import team.creative.creativecore.common.util.math.geo.Rect;

public class GuiControlRect {
   protected final GuiControl control;
   private int left;
   private int top;
   private int right;
   private int bottom;

   public static int getMinWidth(GuiControl control, int availableWidth) {
      if (control.preferred != null) {
         int minWidth = control.preferred.minWidth(control, availableWidth);
         if (minWidth != -1) {
            return minWidth;
         }
      }

      return control.minWidth(availableWidth);
   }

   public static int getPreferredWidth(GuiControl control, int availableWidth) {
      if (control.preferred != null) {
         int prefWidth = control.preferred.preferredWidth(control, availableWidth);
         if (prefWidth != -1) {
            return prefWidth;
         }
      }

      return control.preferredWidth(availableWidth);
   }

   public static int getMaxWidth(GuiControl control, int availableWidth) {
      if (control.preferred != null) {
         int maxWidth = control.preferred.maxWidth(control, availableWidth);
         if (maxWidth != -1) {
            return maxWidth;
         }
      }

      return control.maxWidth(availableWidth);
   }

   public static int getMinHeight(GuiControl control, int width, int availableHeight) {
      if (control.preferred != null) {
         int minHeight = control.preferred.minHeight(control, width, availableHeight);
         if (minHeight != -1) {
            return minHeight;
         }
      }

      return control.minHeight(width, availableHeight);
   }

   public static int getPreferredHeight(GuiControl control, int width, int availableHeight) {
      if (control.preferred != null) {
         int prefHeight = control.preferred.preferredHeight(control, width, availableHeight);
         if (prefHeight != -1) {
            return prefHeight;
         }
      }

      return control.preferredHeight(width, availableHeight);
   }

   public static int getMaxHeight(GuiControl control, int width, int availableHeight) {
      if (control.preferred != null) {
         int maxHeight = control.preferred.maxHeight(control, width, availableHeight);
         if (maxHeight != -1) {
            return maxHeight;
         }
      }

      return control.maxHeight(width, availableHeight);
   }

   public GuiControlRect(GuiControl control) {
      this.control = control;
   }

   public int getX() {
      return this.left;
   }

   public void setX(int x) {
      int width = this.getWidth();
      this.left = x;
      this.right = x + width;
   }

   public int getY() {
      return this.top;
   }

   public void setY(int y) {
      int height = this.getHeight();
      this.top = y;
      this.bottom = y + height;
   }

   public int getWidth() {
      return this.right - this.left;
   }

   public int getHeight() {
      return this.bottom - this.top;
   }

   public int getBottom() {
      return this.bottom;
   }

   public int getRight() {
      return this.right;
   }

   public void setRight(int x) {
      this.right = x;
   }

   public void setBottom(int y) {
      this.bottom = y;
   }

   public int getContentWidth() {
      return this.getWidth() - this.control.getContentOffset() * 2;
   }

   public int getContentHeight() {
      return this.getHeight() - this.control.getContentOffset() * 2;
   }

   protected int clampWidth(int width, int availableWidth) {
      int min = this.getMinWidth(availableWidth);
      if (min != -1) {
         width = Math.max(width, min);
      }

      int max = this.getMaxWidth(availableWidth);
      if (max != -1) {
         width = Math.min(width, max);
      }

      return width;
   }

   public int setWidth(int width, int availableWidth) {
      width = this.clampWidth(width, availableWidth);
      this.right = this.left + width;
      return width;
   }

   protected int clampHeight(int height, int availableHeight) {
      int min = this.getMinHeight(availableHeight);
      if (min != -1) {
         height = Math.max(height, min);
      }

      int max = this.getMaxHeight(availableHeight);
      if (max != -1) {
         height = Math.min(height, max);
      }

      return height;
   }

   public int setHeight(int height, int availableHeight) {
      height = this.clampHeight(height, availableHeight);
      this.bottom = this.top + height;
      return height;
   }

   public int addWidth(int additional, int availableWidth) {
      int before = this.getWidth();
      this.setWidth(this.getWidth() + additional, availableWidth);
      return this.getWidth() - before;
   }

   public int addHeight(int additional, int availableHeight) {
      int before = this.getHeight();
      this.setHeight(this.getHeight() + additional, availableHeight);
      return this.getHeight() - before;
   }

   public boolean isMaxWidth(int availableWidth) {
      return this.getMaxWidth(availableWidth) != -1 ? this.getWidth() >= this.getMaxWidth(availableWidth) : false;
   }

   public boolean isMaxHeight(int availableHeight) {
      return this.getMaxHeight(availableHeight) != -1 ? this.getHeight() >= this.getMaxHeight(availableHeight) : false;
   }

   public int getMinWidth(int availableWidth) {
      int min = getMinWidth(this.control, availableWidth);
      return min != -1 ? min + this.control.getContentOffset() * 2 : -1;
   }

   public int getMaxWidth(int availableWidth) {
      int max = getMaxWidth(this.control, availableWidth);
      return max != -1 ? max + this.control.getContentOffset() * 2 : -1;
   }

   public int getPreferredWidth(int availableWidth) {
      return this.clampWidth(getPreferredWidth(this.control, availableWidth) + this.control.getContentOffset() * 2, availableWidth);
   }

   public int getMinHeight(int availableHeight) {
      int min = getMinHeight(this.control, this.getContentWidth(), availableHeight);
      return min != -1 ? min + this.control.getContentOffset() * 2 : -1;
   }

   public int getMaxHeight(int availableHeight) {
      int max = getMaxHeight(this.control, this.getContentWidth(), availableHeight);
      return max != -1 ? max + this.control.getContentOffset() * 2 : -1;
   }

   public int getPreferredHeight(int availableHeight) {
      return this.clampHeight(getPreferredHeight(this.control, this.getContentWidth(), availableHeight) + this.control.getContentOffset() * 2, availableHeight);
   }

   public void flowX() {
      int width = this.getContentWidth();
      this.control.flowX(width, this.control.preferredWidth(width));
   }

   public void flowY() {
      int width = this.getContentWidth();
      int height = this.getContentHeight();
      this.control.flowY(width, height, this.control.preferredHeight(width, height));
   }

   public boolean inside(double x, double y) {
      return x >= this.left && x < this.right && y >= this.top && y < this.bottom;
   }

   public boolean insideLocalPos(double x, double y) {
      return x >= 0.0 && x < this.getWidth() && y >= 0.0 && y < this.getHeight();
   }

   public boolean isExpandableX() {
      return this.control.isExpandableX();
   }

   public boolean isExpandableY() {
      return this.control.isExpandableY();
   }

   public Rect rectCopy() {
      return new Rect(this.left, this.top, this.right, this.bottom);
   }

   @Override
   public String toString() {
      return "[" + this.left + "," + this.top + "," + this.right + "," + this.bottom + "]";
   }
}

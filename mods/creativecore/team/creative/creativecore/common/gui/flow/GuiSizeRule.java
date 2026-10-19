package team.creative.creativecore.common.gui.flow;

import team.creative.creativecore.common.gui.GuiControl;

public abstract class GuiSizeRule {
   public abstract int minWidth(GuiControl var1, int var2);

   public abstract int preferredWidth(GuiControl var1, int var2);

   public abstract int maxWidth(GuiControl var1, int var2);

   public abstract int minHeight(GuiControl var1, int var2, int var3);

   public abstract int preferredHeight(GuiControl var1, int var2, int var3);

   public abstract int maxHeight(GuiControl var1, int var2, int var3);

   public static class GuiFixedDimension extends GuiSizeRule {
      public final int width;
      public final int height;

      public GuiFixedDimension(int width, int height) {
         this.width = width;
         this.height = height;
      }

      public GuiFixedDimension(int width) {
         this(width, -1);
      }

      @Override
      public int minWidth(GuiControl control, int availableWidth) {
         return -1;
      }

      @Override
      public int preferredWidth(GuiControl control, int availableWidth) {
         return this.width;
      }

      @Override
      public int maxWidth(GuiControl control, int availableWidth) {
         return -1;
      }

      @Override
      public int minHeight(GuiControl control, int width, int availableHeight) {
         return -1;
      }

      @Override
      public int preferredHeight(GuiControl control, int width, int availableHeight) {
         return this.height;
      }

      @Override
      public int maxHeight(GuiControl control, int width, int availableHeight) {
         return -1;
      }
   }

   public static class GuiRatioDimension extends GuiSizeRule {
      public final float width;
      public final float height;

      public GuiRatioDimension(float width, float height) {
         this.width = width;
         this.height = height;
      }

      @Override
      public int minWidth(GuiControl control, int availableWidth) {
         return -1;
      }

      @Override
      public int preferredWidth(GuiControl control, int availableWidth) {
         return (int)(this.width * availableWidth);
      }

      @Override
      public int maxWidth(GuiControl control, int availableWidth) {
         return -1;
      }

      @Override
      public int minHeight(GuiControl control, int width, int availableHeight) {
         return -1;
      }

      @Override
      public int preferredHeight(GuiControl control, int width, int availableHeight) {
         return (int)(this.height * availableHeight);
      }

      @Override
      public int maxHeight(GuiControl control, int width, int availableHeight) {
         return -1;
      }
   }

   public static class GuiSizeRatioRules extends GuiSizeRule {
      public int minWidth = -1;
      public float width = -1.0F;
      public int maxWidth = -1;
      public int minHeight = -1;
      public float height = -1.0F;
      public int maxHeight = -1;

      public GuiSizeRatioRules() {
      }

      public GuiSizeRatioRules(float width, float height) {
         this.width = width;
         this.height = height;
      }

      public GuiSizeRule.GuiSizeRatioRules minWidth(int value) {
         this.minWidth = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRatioRules widthRatio(float value) {
         this.width = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRatioRules maxWidth(int value) {
         this.maxWidth = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRatioRules minHeight(int value) {
         this.minHeight = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRatioRules heightRatio(float value) {
         this.height = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRatioRules maxHeight(int value) {
         this.maxHeight = value;
         return this;
      }

      @Override
      public int minWidth(GuiControl control, int availableWidth) {
         return this.minWidth;
      }

      @Override
      public int preferredWidth(GuiControl control, int availableWidth) {
         return this.width == -1.0F ? -1 : (int)(this.width * availableWidth);
      }

      @Override
      public int maxWidth(GuiControl control, int availableWidth) {
         return this.maxWidth;
      }

      @Override
      public int minHeight(GuiControl control, int width, int availableHeight) {
         return this.minHeight;
      }

      @Override
      public int preferredHeight(GuiControl control, int width, int availableHeight) {
         return this.height == -1.0F ? -1 : (int)(this.height * availableHeight);
      }

      @Override
      public int maxHeight(GuiControl control, int width, int availableHeight) {
         return this.maxHeight;
      }
   }

   public static class GuiSizeRules extends GuiSizeRule {
      public int minWidth = -1;
      public int prefWidth = -1;
      public int maxWidth = -1;
      public int minHeight = -1;
      public int prefHeight = -1;
      public int maxHeight = -1;

      public GuiSizeRule.GuiSizeRules minWidth(int value) {
         this.minWidth = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRules prefWidth(int value) {
         this.prefWidth = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRules maxWidth(int value) {
         this.maxWidth = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRules minHeight(int value) {
         this.minHeight = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRules prefHeight(int value) {
         this.prefHeight = value;
         return this;
      }

      public GuiSizeRule.GuiSizeRules maxHeight(int value) {
         this.maxHeight = value;
         return this;
      }

      @Override
      public int minWidth(GuiControl control, int availableWidth) {
         return this.minWidth;
      }

      @Override
      public int preferredWidth(GuiControl control, int availableWidth) {
         return this.prefWidth;
      }

      @Override
      public int maxWidth(GuiControl control, int availableWidth) {
         return this.maxWidth;
      }

      @Override
      public int minHeight(GuiControl control, int width, int availableHeight) {
         return this.minHeight;
      }

      @Override
      public int preferredHeight(GuiControl control, int width, int availableHeight) {
         return this.prefHeight;
      }

      @Override
      public int maxHeight(GuiControl control, int width, int availableHeight) {
         return this.maxHeight;
      }
   }
}

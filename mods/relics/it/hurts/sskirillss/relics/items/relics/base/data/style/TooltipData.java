package it.hurts.sskirillss.relics.items.relics.base.data.style;

public class TooltipData {
   private int borderTop;
   private int borderBottom;
   private int backgroundTop;
   private int backgroundBottom;
   @Deprecated(forRemoval = true)
   private boolean textured;
   @Deprecated(forRemoval = true)
   private String icon;

   private static int $default$borderTop() {
      return -1;
   }

   private static int $default$borderBottom() {
      return -1;
   }

   private static int $default$backgroundTop() {
      return -1;
   }

   private static int $default$backgroundBottom() {
      return -1;
   }

   private static boolean $default$textured() {
      return false;
   }

   private static String $default$icon() {
      return "";
   }

   TooltipData(int borderTop, int borderBottom, int backgroundTop, int backgroundBottom, boolean textured, String icon) {
      this.borderTop = borderTop;
      this.borderBottom = borderBottom;
      this.backgroundTop = backgroundTop;
      this.backgroundBottom = backgroundBottom;
      this.textured = textured;
      this.icon = icon;
   }

   public static TooltipData.TooltipDataBuilder builder() {
      return new TooltipData.TooltipDataBuilder();
   }

   public int getBorderTop() {
      return this.borderTop;
   }

   public int getBorderBottom() {
      return this.borderBottom;
   }

   public int getBackgroundTop() {
      return this.backgroundTop;
   }

   public int getBackgroundBottom() {
      return this.backgroundBottom;
   }

   @Deprecated
   public boolean isTextured() {
      return this.textured;
   }

   @Deprecated
   public String getIcon() {
      return this.icon;
   }

   public void setBorderTop(int borderTop) {
      this.borderTop = borderTop;
   }

   public void setBorderBottom(int borderBottom) {
      this.borderBottom = borderBottom;
   }

   public void setBackgroundTop(int backgroundTop) {
      this.backgroundTop = backgroundTop;
   }

   public void setBackgroundBottom(int backgroundBottom) {
      this.backgroundBottom = backgroundBottom;
   }

   @Deprecated
   public void setTextured(boolean textured) {
      this.textured = textured;
   }

   @Deprecated
   public void setIcon(String icon) {
      this.icon = icon;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TooltipData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getBorderTop() != other.getBorderTop()) {
            return false;
         }

         if (this.getBorderBottom() != other.getBorderBottom()) {
            return false;
         }

         if (this.getBackgroundTop() != other.getBackgroundTop()) {
            return false;
         }

         if (this.getBackgroundBottom() != other.getBackgroundBottom()) {
            return false;
         }

         if (this.isTextured() != other.isTextured()) {
            return false;
         }

         Object this$icon = this.getIcon();
         Object other$icon = other.getIcon();
         return this$icon == null ? other$icon == null : this$icon.equals(other$icon);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof TooltipData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getBorderTop();
      result = result * 59 + this.getBorderBottom();
      result = result * 59 + this.getBackgroundTop();
      result = result * 59 + this.getBackgroundBottom();
      result = result * 59 + (this.isTextured() ? 79 : 97);
      Object $icon = this.getIcon();
      return result * 59 + ($icon == null ? 43 : $icon.hashCode());
   }

   @Override
   public String toString() {
      return "TooltipData(borderTop="
         + this.getBorderTop()
         + ", borderBottom="
         + this.getBorderBottom()
         + ", backgroundTop="
         + this.getBackgroundTop()
         + ", backgroundBottom="
         + this.getBackgroundBottom()
         + ", textured="
         + this.isTextured()
         + ", icon="
         + this.getIcon()
         + ")";
   }

   public static class TooltipDataBuilder {
      private boolean borderTop$set;
      private int borderTop$value;
      private boolean borderBottom$set;
      private int borderBottom$value;
      private boolean backgroundTop$set;
      private int backgroundTop$value;
      private boolean backgroundBottom$set;
      private int backgroundBottom$value;
      private boolean textured$set;
      private boolean textured$value;
      private boolean icon$set;
      private String icon$value;

      TooltipDataBuilder() {
      }

      public TooltipData.TooltipDataBuilder borderTop(int borderTop) {
         this.borderTop$value = borderTop;
         this.borderTop$set = true;
         return this;
      }

      public TooltipData.TooltipDataBuilder borderBottom(int borderBottom) {
         this.borderBottom$value = borderBottom;
         this.borderBottom$set = true;
         return this;
      }

      public TooltipData.TooltipDataBuilder backgroundTop(int backgroundTop) {
         this.backgroundTop$value = backgroundTop;
         this.backgroundTop$set = true;
         return this;
      }

      public TooltipData.TooltipDataBuilder backgroundBottom(int backgroundBottom) {
         this.backgroundBottom$value = backgroundBottom;
         this.backgroundBottom$set = true;
         return this;
      }

      @Deprecated
      public TooltipData.TooltipDataBuilder textured(boolean textured) {
         this.textured$value = textured;
         this.textured$set = true;
         return this;
      }

      @Deprecated
      public TooltipData.TooltipDataBuilder icon(String icon) {
         this.icon$value = icon;
         this.icon$set = true;
         return this;
      }

      public TooltipData build() {
         int borderTop$value = this.borderTop$value;
         if (!this.borderTop$set) {
            borderTop$value = TooltipData.$default$borderTop();
         }

         int borderBottom$value = this.borderBottom$value;
         if (!this.borderBottom$set) {
            borderBottom$value = TooltipData.$default$borderBottom();
         }

         int backgroundTop$value = this.backgroundTop$value;
         if (!this.backgroundTop$set) {
            backgroundTop$value = TooltipData.$default$backgroundTop();
         }

         int backgroundBottom$value = this.backgroundBottom$value;
         if (!this.backgroundBottom$set) {
            backgroundBottom$value = TooltipData.$default$backgroundBottom();
         }

         boolean textured$value = this.textured$value;
         if (!this.textured$set) {
            textured$value = TooltipData.$default$textured();
         }

         String icon$value = this.icon$value;
         if (!this.icon$set) {
            icon$value = TooltipData.$default$icon();
         }

         return new TooltipData(borderTop$value, borderBottom$value, backgroundTop$value, backgroundBottom$value, textured$value, icon$value);
      }

      @Override
      public String toString() {
         return "TooltipData.TooltipDataBuilder(borderTop$value="
            + this.borderTop$value
            + ", borderBottom$value="
            + this.borderBottom$value
            + ", backgroundTop$value="
            + this.backgroundTop$value
            + ", backgroundBottom$value="
            + this.backgroundBottom$value
            + ", textured$value="
            + this.textured$value
            + ", icon$value="
            + this.icon$value
            + ")";
      }
   }
}

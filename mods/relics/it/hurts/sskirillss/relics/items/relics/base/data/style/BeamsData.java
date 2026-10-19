package it.hurts.sskirillss.relics.items.relics.base.data.style;

public class BeamsData {
   private int startColor;
   private int endColor;

   private static int $default$startColor() {
      return -256;
   }

   private static int $default$endColor() {
      return 16711680;
   }

   BeamsData(int startColor, int endColor) {
      this.startColor = startColor;
      this.endColor = endColor;
   }

   public static BeamsData.BeamsDataBuilder builder() {
      return new BeamsData.BeamsDataBuilder();
   }

   public int getStartColor() {
      return this.startColor;
   }

   public int getEndColor() {
      return this.endColor;
   }

   public void setStartColor(int startColor) {
      this.startColor = startColor;
   }

   public void setEndColor(int endColor) {
      this.endColor = endColor;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BeamsData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         return this.getStartColor() != other.getStartColor() ? false : this.getEndColor() == other.getEndColor();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof BeamsData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getStartColor();
      return result * 59 + this.getEndColor();
   }

   @Override
   public String toString() {
      return "BeamsData(startColor=" + this.getStartColor() + ", endColor=" + this.getEndColor() + ")";
   }

   public static class BeamsDataBuilder {
      private boolean startColor$set;
      private int startColor$value;
      private boolean endColor$set;
      private int endColor$value;

      BeamsDataBuilder() {
      }

      public BeamsData.BeamsDataBuilder startColor(int startColor) {
         this.startColor$value = startColor;
         this.startColor$set = true;
         return this;
      }

      public BeamsData.BeamsDataBuilder endColor(int endColor) {
         this.endColor$value = endColor;
         this.endColor$set = true;
         return this;
      }

      public BeamsData build() {
         int startColor$value = this.startColor$value;
         if (!this.startColor$set) {
            startColor$value = BeamsData.$default$startColor();
         }

         int endColor$value = this.endColor$value;
         if (!this.endColor$set) {
            endColor$value = BeamsData.$default$endColor();
         }

         return new BeamsData(startColor$value, endColor$value);
      }

      @Override
      public String toString() {
         return "BeamsData.BeamsDataBuilder(startColor$value=" + this.startColor$value + ", endColor$value=" + this.endColor$value + ")";
      }
   }
}

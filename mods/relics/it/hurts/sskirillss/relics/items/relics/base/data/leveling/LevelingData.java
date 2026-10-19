package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import it.hurts.sskirillss.relics.config.data.LevelingConfigData;

public class LevelingData {
   private int initialCost;
   private int maxLevel;
   private int step;
   private LevelingSourcesData sources;

   @Deprecated(forRemoval = true)
   public LevelingData(int initialCost, int maxLevel, int step) {
      this.initialCost = initialCost;
      this.maxLevel = maxLevel;
      this.step = step;
      this.sources = LevelingSourcesData.builder().build();
   }

   public LevelingConfigData toConfigData() {
      return new LevelingConfigData(this.initialCost, this.maxLevel, this.step);
   }

   private static int $default$initialCost() {
      return 100;
   }

   private static int $default$maxLevel() {
      return 10;
   }

   private static int $default$step() {
      return 100;
   }

   private static LevelingSourcesData $default$sources() {
      return LevelingSourcesData.builder().build();
   }

   public static LevelingData.LevelingDataBuilder builder() {
      return new LevelingData.LevelingDataBuilder();
   }

   public int getInitialCost() {
      return this.initialCost;
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public int getStep() {
      return this.step;
   }

   public LevelingSourcesData getSources() {
      return this.sources;
   }

   public void setInitialCost(int initialCost) {
      this.initialCost = initialCost;
   }

   public void setMaxLevel(int maxLevel) {
      this.maxLevel = maxLevel;
   }

   public void setStep(int step) {
      this.step = step;
   }

   public void setSources(LevelingSourcesData sources) {
      this.sources = sources;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LevelingData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getInitialCost() != other.getInitialCost()) {
            return false;
         }

         if (this.getMaxLevel() != other.getMaxLevel()) {
            return false;
         }

         if (this.getStep() != other.getStep()) {
            return false;
         }

         Object this$sources = this.getSources();
         Object other$sources = other.getSources();
         return this$sources == null ? other$sources == null : this$sources.equals(other$sources);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LevelingData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getInitialCost();
      result = result * 59 + this.getMaxLevel();
      result = result * 59 + this.getStep();
      Object $sources = this.getSources();
      return result * 59 + ($sources == null ? 43 : $sources.hashCode());
   }

   @Override
   public String toString() {
      return "LevelingData(initialCost="
         + this.getInitialCost()
         + ", maxLevel="
         + this.getMaxLevel()
         + ", step="
         + this.getStep()
         + ", sources="
         + this.getSources()
         + ")";
   }

   public LevelingData(int initialCost, int maxLevel, int step, LevelingSourcesData sources) {
      this.initialCost = initialCost;
      this.maxLevel = maxLevel;
      this.step = step;
      this.sources = sources;
   }

   public static class LevelingDataBuilder {
      private boolean initialCost$set;
      private int initialCost$value;
      private boolean maxLevel$set;
      private int maxLevel$value;
      private boolean step$set;
      private int step$value;
      private boolean sources$set;
      private LevelingSourcesData sources$value;

      LevelingDataBuilder() {
      }

      public LevelingData.LevelingDataBuilder initialCost(int initialCost) {
         this.initialCost$value = initialCost;
         this.initialCost$set = true;
         return this;
      }

      public LevelingData.LevelingDataBuilder maxLevel(int maxLevel) {
         this.maxLevel$value = maxLevel;
         this.maxLevel$set = true;
         return this;
      }

      public LevelingData.LevelingDataBuilder step(int step) {
         this.step$value = step;
         this.step$set = true;
         return this;
      }

      public LevelingData.LevelingDataBuilder sources(LevelingSourcesData sources) {
         this.sources$value = sources;
         this.sources$set = true;
         return this;
      }

      public LevelingData build() {
         int initialCost$value = this.initialCost$value;
         if (!this.initialCost$set) {
            initialCost$value = LevelingData.$default$initialCost();
         }

         int maxLevel$value = this.maxLevel$value;
         if (!this.maxLevel$set) {
            maxLevel$value = LevelingData.$default$maxLevel();
         }

         int step$value = this.step$value;
         if (!this.step$set) {
            step$value = LevelingData.$default$step();
         }

         LevelingSourcesData sources$value = this.sources$value;
         if (!this.sources$set) {
            sources$value = LevelingData.$default$sources();
         }

         return new LevelingData(initialCost$value, maxLevel$value, step$value, sources$value);
      }

      @Override
      public String toString() {
         return "LevelingData.LevelingDataBuilder(initialCost$value="
            + this.initialCost$value
            + ", maxLevel$value="
            + this.maxLevel$value
            + ", step$value="
            + this.step$value
            + ", sources$value="
            + this.sources$value
            + ")";
      }
   }
}

package it.hurts.sskirillss.relics.config.data;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;

public class LevelingConfigData {
   @Prop(comment = "Amount of experience required to level up to relic level 1")
   private int initialCost;
   @Prop(comment = "Maximum level of the relic")
   private int maxLevel;
   @Prop(comment = "Increment in experience required for each subsequent level of the relic")
   private int step;

   public LevelingData toData(IRelicItem relic) {
      LevelingData data = relic.getLevelingData();
      data.setInitialCost(this.initialCost);
      data.setMaxLevel(this.maxLevel);
      data.setStep(this.step);
      return data;
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

   public void setInitialCost(int initialCost) {
      this.initialCost = initialCost;
   }

   public void setMaxLevel(int maxLevel) {
      this.maxLevel = maxLevel;
   }

   public void setStep(int step) {
      this.step = step;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LevelingConfigData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getInitialCost() != other.getInitialCost()) {
         return false;
      } else {
         return this.getMaxLevel() != other.getMaxLevel() ? false : this.getStep() == other.getStep();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LevelingConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getInitialCost();
      result = result * 59 + this.getMaxLevel();
      return result * 59 + this.getStep();
   }

   @Override
   public String toString() {
      return "LevelingConfigData(initialCost=" + this.getInitialCost() + ", maxLevel=" + this.getMaxLevel() + ", step=" + this.getStep() + ")";
   }

   public LevelingConfigData() {
   }

   public LevelingConfigData(int initialCost, int maxLevel, int step) {
      this.initialCost = initialCost;
      this.maxLevel = maxLevel;
      this.step = step;
   }
}

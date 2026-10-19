package it.hurts.sskirillss.relics.config.data;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AbilityConfigData {
   @Prop(comment = "Number of leveling points needed to increase the ability level")
   private int requiredPoints;
   @Prop(comment = "Relic level at which the ability becomes unlocked")
   private int requiredLevel;
   @Prop(comment = "Highest level to which the ability can be upgraded")
   private int maxLevel;
   private Map<String, StatConfigData> stats = new LinkedHashMap<>();

   public AbilityData toData(IRelicItem relic, String ability) {
      AbilityData data = relic.constructDefaultRelicData().getAbilities().getAbilities().get(ability);
      data.setRequiredPoints(this.requiredPoints);
      data.setRequiredLevel(this.requiredLevel);
      data.setMaxLevel(this.maxLevel);
      data.setStats(
         data.getStats()
            .entrySet()
            .stream()
            .collect(
               Collectors.toMap(
                  Entry::getKey, entry -> this.stats.get(entry.getKey()).toData(relic, ability, (String)entry.getKey()), (o1, o2) -> o1, LinkedHashMap::new
               )
            )
      );
      return data;
   }

   public int getRequiredPoints() {
      return this.requiredPoints;
   }

   public int getRequiredLevel() {
      return this.requiredLevel;
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public Map<String, StatConfigData> getStats() {
      return this.stats;
   }

   public void setRequiredPoints(int requiredPoints) {
      this.requiredPoints = requiredPoints;
   }

   public void setRequiredLevel(int requiredLevel) {
      this.requiredLevel = requiredLevel;
   }

   public void setMaxLevel(int maxLevel) {
      this.maxLevel = maxLevel;
   }

   public void setStats(Map<String, StatConfigData> stats) {
      this.stats = stats;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilityConfigData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getRequiredPoints() != other.getRequiredPoints()) {
            return false;
         }

         if (this.getRequiredLevel() != other.getRequiredLevel()) {
            return false;
         }

         if (this.getMaxLevel() != other.getMaxLevel()) {
            return false;
         }

         Object this$stats = this.getStats();
         Object other$stats = other.getStats();
         return this$stats == null ? other$stats == null : this$stats.equals(other$stats);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AbilityConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getRequiredPoints();
      result = result * 59 + this.getRequiredLevel();
      result = result * 59 + this.getMaxLevel();
      Object $stats = this.getStats();
      return result * 59 + ($stats == null ? 43 : $stats.hashCode());
   }

   @Override
   public String toString() {
      return "AbilityConfigData(requiredPoints="
         + this.getRequiredPoints()
         + ", requiredLevel="
         + this.getRequiredLevel()
         + ", maxLevel="
         + this.getMaxLevel()
         + ", stats="
         + this.getStats()
         + ")";
   }

   public AbilityConfigData() {
   }

   public AbilityConfigData(int requiredPoints, int requiredLevel, int maxLevel, Map<String, StatConfigData> stats) {
      this.requiredPoints = requiredPoints;
      this.requiredLevel = requiredLevel;
      this.maxLevel = maxLevel;
      this.stats = stats;
   }
}

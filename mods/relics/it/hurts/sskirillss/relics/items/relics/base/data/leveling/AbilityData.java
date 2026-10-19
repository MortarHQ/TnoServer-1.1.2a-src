package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import com.mojang.datafixers.util.Function3;
import it.hurts.sskirillss.relics.config.data.AbilityConfigData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AbilityData {
   private final String id;
   private Function3<Player, ItemStack, String, String> icon;
   private Map<String, StatData> stats;
   private int maxLevel;
   private int requiredLevel;
   private int requiredPoints;
   private CastData castData;
   private ResearchData researchData;

   public static AbilityData.AbilityDataBuilder builder(String id) {
      AbilityData.AbilityDataBuilder builder = new AbilityData.AbilityDataBuilder();
      builder.id(id);
      return builder;
   }

   public AbilityConfigData toConfigData() {
      return new AbilityConfigData(
         this.requiredPoints,
         this.requiredLevel,
         this.maxLevel,
         this.stats
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, e -> ((StatData)e.getValue()).toConfigData(), (o1, o2) -> o1, LinkedHashMap::new))
      );
   }

   private static Function3<Player, ItemStack, String, String> $default$icon() {
      return (player, stack, ability) -> ability;
   }

   private static int $default$maxLevel() {
      return 10;
   }

   private static int $default$requiredLevel() {
      return 0;
   }

   private static int $default$requiredPoints() {
      return 1;
   }

   AbilityData(
      String id,
      Function3<Player, ItemStack, String, String> icon,
      Map<String, StatData> stats,
      int maxLevel,
      int requiredLevel,
      int requiredPoints,
      CastData castData,
      ResearchData researchData
   ) {
      this.id = id;
      this.icon = icon;
      this.stats = stats;
      this.maxLevel = maxLevel;
      this.requiredLevel = requiredLevel;
      this.requiredPoints = requiredPoints;
      this.castData = castData;
      this.researchData = researchData;
   }

   public String getId() {
      return this.id;
   }

   public Function3<Player, ItemStack, String, String> getIcon() {
      return this.icon;
   }

   public Map<String, StatData> getStats() {
      return this.stats;
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public int getRequiredLevel() {
      return this.requiredLevel;
   }

   public int getRequiredPoints() {
      return this.requiredPoints;
   }

   public CastData getCastData() {
      return this.castData;
   }

   public ResearchData getResearchData() {
      return this.researchData;
   }

   public void setIcon(Function3<Player, ItemStack, String, String> icon) {
      this.icon = icon;
   }

   public void setStats(Map<String, StatData> stats) {
      this.stats = stats;
   }

   public void setMaxLevel(int maxLevel) {
      this.maxLevel = maxLevel;
   }

   public void setRequiredLevel(int requiredLevel) {
      this.requiredLevel = requiredLevel;
   }

   public void setRequiredPoints(int requiredPoints) {
      this.requiredPoints = requiredPoints;
   }

   public void setCastData(CastData castData) {
      this.castData = castData;
   }

   public void setResearchData(ResearchData researchData) {
      this.researchData = researchData;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilityData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getMaxLevel() != other.getMaxLevel()) {
            return false;
         }

         if (this.getRequiredLevel() != other.getRequiredLevel()) {
            return false;
         }

         if (this.getRequiredPoints() != other.getRequiredPoints()) {
            return false;
         }

         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$icon = this.getIcon();
            Object other$icon = other.getIcon();
            if (this$icon == null ? other$icon == null : this$icon.equals(other$icon)) {
               Object this$stats = this.getStats();
               Object other$stats = other.getStats();
               if (this$stats == null ? other$stats == null : this$stats.equals(other$stats)) {
                  Object this$castData = this.getCastData();
                  Object other$castData = other.getCastData();
                  if (this$castData == null ? other$castData == null : this$castData.equals(other$castData)) {
                     Object this$researchData = this.getResearchData();
                     Object other$researchData = other.getResearchData();
                     return this$researchData == null ? other$researchData == null : this$researchData.equals(other$researchData);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AbilityData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMaxLevel();
      result = result * 59 + this.getRequiredLevel();
      result = result * 59 + this.getRequiredPoints();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $icon = this.getIcon();
      result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
      Object $stats = this.getStats();
      result = result * 59 + ($stats == null ? 43 : $stats.hashCode());
      Object $castData = this.getCastData();
      result = result * 59 + ($castData == null ? 43 : $castData.hashCode());
      Object $researchData = this.getResearchData();
      return result * 59 + ($researchData == null ? 43 : $researchData.hashCode());
   }

   @Override
   public String toString() {
      return "AbilityData(id="
         + this.getId()
         + ", icon="
         + this.getIcon()
         + ", stats="
         + this.getStats()
         + ", maxLevel="
         + this.getMaxLevel()
         + ", requiredLevel="
         + this.getRequiredLevel()
         + ", requiredPoints="
         + this.getRequiredPoints()
         + ", castData="
         + this.getCastData()
         + ", researchData="
         + this.getResearchData()
         + ")";
   }

   public static class AbilityDataBuilder {
      private String id;
      private boolean icon$set;
      private Function3<Player, ItemStack, String, String> icon$value;
      private boolean maxLevel$set;
      private int maxLevel$value;
      private boolean requiredLevel$set;
      private int requiredLevel$value;
      private boolean requiredPoints$set;
      private int requiredPoints$value;
      private Map<String, StatData> stats = new LinkedHashMap<>();
      private CastData castData = CastData.builder().build();
      private ResearchData researchData = ResearchData.builder().build();

      private AbilityData.AbilityDataBuilder castData(CastData data) {
         return this;
      }

      private AbilityData.AbilityDataBuilder researchData(ResearchData data) {
         return this;
      }

      public AbilityData.AbilityDataBuilder research(ResearchData data) {
         this.researchData = data;
         return this;
      }

      public AbilityData.AbilityDataBuilder active(CastData data) {
         this.castData = data;
         return this;
      }

      public AbilityData.AbilityDataBuilder stat(StatData stat) {
         this.stats.put(stat.getId(), stat);
         return this;
      }

      private AbilityData.AbilityDataBuilder id(String id) {
         this.id = id;
         return this;
      }

      AbilityDataBuilder() {
      }

      public AbilityData.AbilityDataBuilder icon(Function3<Player, ItemStack, String, String> icon) {
         this.icon$value = icon;
         this.icon$set = true;
         return this;
      }

      public AbilityData.AbilityDataBuilder stats(Map<String, StatData> stats) {
         this.stats = stats;
         return this;
      }

      public AbilityData.AbilityDataBuilder maxLevel(int maxLevel) {
         this.maxLevel$value = maxLevel;
         this.maxLevel$set = true;
         return this;
      }

      public AbilityData.AbilityDataBuilder requiredLevel(int requiredLevel) {
         this.requiredLevel$value = requiredLevel;
         this.requiredLevel$set = true;
         return this;
      }

      public AbilityData.AbilityDataBuilder requiredPoints(int requiredPoints) {
         this.requiredPoints$value = requiredPoints;
         this.requiredPoints$set = true;
         return this;
      }

      public AbilityData build() {
         Function3<Player, ItemStack, String, String> icon$value = this.icon$value;
         if (!this.icon$set) {
            icon$value = AbilityData.$default$icon();
         }

         int maxLevel$value = this.maxLevel$value;
         if (!this.maxLevel$set) {
            maxLevel$value = AbilityData.$default$maxLevel();
         }

         int requiredLevel$value = this.requiredLevel$value;
         if (!this.requiredLevel$set) {
            requiredLevel$value = AbilityData.$default$requiredLevel();
         }

         int requiredPoints$value = this.requiredPoints$value;
         if (!this.requiredPoints$set) {
            requiredPoints$value = AbilityData.$default$requiredPoints();
         }

         return new AbilityData(this.id, icon$value, this.stats, maxLevel$value, requiredLevel$value, requiredPoints$value, this.castData, this.researchData);
      }

      @Override
      public String toString() {
         return "AbilityData.AbilityDataBuilder(id="
            + this.id
            + ", icon$value="
            + this.icon$value
            + ", stats="
            + this.stats
            + ", maxLevel$value="
            + this.maxLevel$value
            + ", requiredLevel$value="
            + this.requiredLevel$value
            + ", requiredPoints$value="
            + this.requiredPoints$value
            + ", castData="
            + this.castData
            + ", researchData="
            + this.researchData
            + ")";
      }
   }
}

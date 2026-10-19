package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import it.hurts.sskirillss.relics.config.data.AbilitiesConfigData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AbilitiesData {
   private Map<String, AbilityData> abilities;

   public AbilitiesConfigData toConfigData() {
      return new AbilitiesConfigData(
         this.abilities
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, e -> ((AbilityData)e.getValue()).toConfigData(), (o1, o2) -> o1, LinkedHashMap::new))
      );
   }

   AbilitiesData(Map<String, AbilityData> abilities) {
      this.abilities = abilities;
   }

   public static AbilitiesData.AbilitiesDataBuilder builder() {
      return new AbilitiesData.AbilitiesDataBuilder();
   }

   public Map<String, AbilityData> getAbilities() {
      return this.abilities;
   }

   public void setAbilities(Map<String, AbilityData> abilities) {
      this.abilities = abilities;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilitiesData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$abilities = this.getAbilities();
         Object other$abilities = other.getAbilities();
         return this$abilities == null ? other$abilities == null : this$abilities.equals(other$abilities);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AbilitiesData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $abilities = this.getAbilities();
      return result * 59 + ($abilities == null ? 43 : $abilities.hashCode());
   }

   @Override
   public String toString() {
      return "AbilitiesData(abilities=" + this.getAbilities() + ")";
   }

   public static class AbilitiesDataBuilder {
      private Map<String, AbilityData> abilities = new LinkedHashMap<>();

      public AbilitiesData.AbilitiesDataBuilder ability(AbilityData ability) {
         this.abilities.put(ability.getId(), ability);
         return this;
      }

      AbilitiesDataBuilder() {
      }

      public AbilitiesData.AbilitiesDataBuilder abilities(Map<String, AbilityData> abilities) {
         this.abilities = abilities;
         return this;
      }

      public AbilitiesData build() {
         return new AbilitiesData(this.abilities);
      }

      @Override
      public String toString() {
         return "AbilitiesData.AbilitiesDataBuilder(abilities=" + this.abilities + ")";
      }
   }
}

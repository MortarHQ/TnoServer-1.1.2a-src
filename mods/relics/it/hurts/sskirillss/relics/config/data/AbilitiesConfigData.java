package it.hurts.sskirillss.relics.config.data;

import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AbilitiesConfigData {
   private Map<String, AbilityConfigData> abilities = new LinkedHashMap<>();

   public AbilitiesData toData(IRelicItem relic) {
      AbilitiesData data = relic.getAbilitiesData();
      data.setAbilities(
         this.abilities
            .entrySet()
            .stream()
            .collect(
               Collectors.toMap(Entry::getKey, e -> ((AbilityConfigData)e.getValue()).toData(relic, (String)e.getKey()), (o1, o2) -> o1, LinkedHashMap::new)
            )
      );
      return data;
   }

   public Map<String, AbilityConfigData> getAbilities() {
      return this.abilities;
   }

   public void setAbilities(Map<String, AbilityConfigData> abilities) {
      this.abilities = abilities;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilitiesConfigData other)) {
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
      return other instanceof AbilitiesConfigData;
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
      return "AbilitiesConfigData(abilities=" + this.getAbilities() + ")";
   }

   public AbilitiesConfigData() {
   }

   public AbilitiesConfigData(Map<String, AbilityConfigData> abilities) {
      this.abilities = abilities;
   }
}

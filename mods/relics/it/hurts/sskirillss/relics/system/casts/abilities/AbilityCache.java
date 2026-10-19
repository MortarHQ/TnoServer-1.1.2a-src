package it.hurts.sskirillss.relics.system.casts.abilities;

import java.util.HashMap;
import java.util.Map;

public class AbilityCache {
   private Map<String, Boolean> predicates = new HashMap<>();
   private int iconShakeDelta = 0;

   public Map<String, Boolean> getPredicates() {
      return this.predicates;
   }

   public int getIconShakeDelta() {
      return this.iconShakeDelta;
   }

   public void setPredicates(Map<String, Boolean> predicates) {
      this.predicates = predicates;
   }

   public void setIconShakeDelta(int iconShakeDelta) {
      this.iconShakeDelta = iconShakeDelta;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbilityCache other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getIconShakeDelta() != other.getIconShakeDelta()) {
            return false;
         }

         Object this$predicates = this.getPredicates();
         Object other$predicates = other.getPredicates();
         return this$predicates == null ? other$predicates == null : this$predicates.equals(other$predicates);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AbilityCache;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getIconShakeDelta();
      Object $predicates = this.getPredicates();
      return result * 59 + ($predicates == null ? 43 : $predicates.hashCode());
   }

   @Override
   public String toString() {
      return "AbilityCache(predicates=" + this.getPredicates() + ", iconShakeDelta=" + this.getIconShakeDelta() + ")";
   }

   public AbilityCache() {
   }

   public AbilityCache(Map<String, Boolean> predicates, int iconShakeDelta) {
      this.predicates = predicates;
      this.iconShakeDelta = iconShakeDelta;
   }
}

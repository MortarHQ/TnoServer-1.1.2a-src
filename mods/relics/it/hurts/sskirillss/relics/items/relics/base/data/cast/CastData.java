package it.hurts.sskirillss.relics.items.relics.base.data.cast;

import it.hurts.sskirillss.relics.init.RelicContainerRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base.RelicContainer;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public class CastData {
   private List<RelicContainer> containers;
   private CastType type;
   private Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> predicates;

   private static CastType $default$type() {
      return CastType.NONE;
   }

   CastData(List<RelicContainer> containers, CastType type, Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> predicates) {
      this.containers = containers;
      this.type = type;
      this.predicates = predicates;
   }

   public static CastData.CastDataBuilder builder() {
      return new CastData.CastDataBuilder();
   }

   public List<RelicContainer> getContainers() {
      return this.containers;
   }

   public CastType getType() {
      return this.type;
   }

   public Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> getPredicates() {
      return this.predicates;
   }

   public void setContainers(List<RelicContainer> containers) {
      this.containers = containers;
   }

   public void setType(CastType type) {
      this.type = type;
   }

   public void setPredicates(Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> predicates) {
      this.predicates = predicates;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CastData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$containers = this.getContainers();
         Object other$containers = other.getContainers();
         if (this$containers == null ? other$containers == null : this$containers.equals(other$containers)) {
            Object this$type = this.getType();
            Object other$type = other.getType();
            if (this$type == null ? other$type == null : this$type.equals(other$type)) {
               Object this$predicates = this.getPredicates();
               Object other$predicates = other.getPredicates();
               return this$predicates == null ? other$predicates == null : this$predicates.equals(other$predicates);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof CastData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $containers = this.getContainers();
      result = result * 59 + ($containers == null ? 43 : $containers.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $predicates = this.getPredicates();
      return result * 59 + ($predicates == null ? 43 : $predicates.hashCode());
   }

   @Override
   public String toString() {
      return "CastData(containers=" + this.getContainers() + ", type=" + this.getType() + ", predicates=" + this.getPredicates() + ")";
   }

   public static class CastDataBuilder {
      private boolean type$set;
      private CastType type$value;
      private Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> predicates = new HashMap<>();
      private List<RelicContainer> containers = List.of(RelicContainerRegistry.CURIOS.get());

      public CastData.CastDataBuilder predicate(String id, PredicateType type, BiFunction<Player, ItemStack, Boolean> predicate) {
         this.predicates.put(id, Pair.of(type, predicate));
         return this;
      }

      public CastData.CastDataBuilder container(RelicContainer... containers) {
         this.containers = Arrays.asList(containers);
         return this;
      }

      CastDataBuilder() {
      }

      public CastData.CastDataBuilder containers(List<RelicContainer> containers) {
         this.containers = containers;
         return this;
      }

      public CastData.CastDataBuilder type(CastType type) {
         this.type$value = type;
         this.type$set = true;
         return this;
      }

      public CastData.CastDataBuilder predicates(Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> predicates) {
         this.predicates = predicates;
         return this;
      }

      public CastData build() {
         CastType type$value = this.type$value;
         if (!this.type$set) {
            type$value = CastData.$default$type();
         }

         return new CastData(this.containers, type$value, this.predicates);
      }

      @Override
      public String toString() {
         return "CastData.CastDataBuilder(containers=" + this.containers + ", type$value=" + this.type$value + ", predicates=" + this.predicates + ")";
      }
   }
}

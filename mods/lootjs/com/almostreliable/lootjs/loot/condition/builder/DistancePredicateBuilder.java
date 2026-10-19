package com.almostreliable.lootjs.loot.condition.builder;

import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;

public class DistancePredicateBuilder {
   private Doubles x = Doubles.ANY;
   private Doubles y = Doubles.ANY;
   private Doubles z = Doubles.ANY;
   private Doubles horizontal = Doubles.ANY;
   private Doubles absolute = Doubles.ANY;

   public DistancePredicateBuilder x(Doubles bounds) {
      this.x = bounds;
      return this;
   }

   public DistancePredicateBuilder y(Doubles bounds) {
      this.y = bounds;
      return this;
   }

   public DistancePredicateBuilder z(Doubles bounds) {
      this.z = bounds;
      return this;
   }

   public DistancePredicateBuilder horizontal(Doubles bounds) {
      this.horizontal = bounds;
      return this;
   }

   public DistancePredicateBuilder absolute(Doubles bounds) {
      this.absolute = bounds;
      return this;
   }

   public DistancePredicate build() {
      return new DistancePredicate(this.x, this.y, this.z, this.horizontal, this.absolute);
   }
}

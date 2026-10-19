package com.almostreliable.lootjs.kube.wrappers;

import com.almostreliable.lootjs.LootJS;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;

public class MinMaxBoundsWrapper {
   private static Optional<Number> saveNumber(@Nullable Object o) {
      return o instanceof Number n ? Optional.of(n) : Optional.empty();
   }

   public static Doubles ofMinMaxDoubles(RegistryAccessContainer registry, Object o) {
      if (o instanceof String s && s.equalsIgnoreCase("any")) {
         return Doubles.ANY;
      } else {
         if (o instanceof List<?> list) {
            if (list.size() == 1) {
               return ofMinMaxDoubles(registry, list.getFirst());
            }

            if (list.size() == 2) {
               Optional<Double> min = saveNumber(list.get(0)).map(Number::doubleValue);
               Optional<Double> max = saveNumber(list.get(1)).map(Number::doubleValue);
               return new Doubles(min, max, min.map(d -> d * d), max.map(d -> d * d));
            }
         }

         if (o instanceof Number n) {
            return Doubles.exactly(n.doubleValue());
         } else if (o instanceof MinMaxBounds<? extends Number> minMaxBounds) {
            Optional<Double> min = minMaxBounds.min().map(Number::doubleValue);
            Optional<Double> max = minMaxBounds.max().map(Number::doubleValue);
            return new Doubles(min, max, min.map(d -> d * d), max.map(d -> d * d));
         } else {
            if (o instanceof Map) {
               return (Doubles)Doubles.CODEC.parse(registry.java(), o).getOrThrow();
            }

            LootJS.LOG.warn("Failed creating bounds, got: " + o);
            return Doubles.exactly(Double.MAX_VALUE);
         }
      }
   }

   public static Ints ofMinMaxInt(RegistryAccessContainer registry, Object o) {
      Doubles doubles = ofMinMaxDoubles(registry, o);
      Optional<Integer> min = doubles.min().map(Double::intValue);
      Optional<Integer> max = doubles.max().map(Double::intValue);
      return new Ints(min, max, min.map(i -> (long)i.intValue() * i.intValue()), max.map(i -> (long)i.intValue() * i.intValue()));
   }
}

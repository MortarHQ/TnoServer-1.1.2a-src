package com.almostreliable.lootjs.kube.wrappers;

import com.almostreliable.lootjs.LootJS;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.ExactMatcher;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.PropertyMatcher;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.RangedMatcher;

public class StatePropsPredicateWrapper {
   public static StatePropertiesPredicate of(@Nullable Object o) {
      if (o instanceof StatePropertiesPredicate s) {
         return s;
      } else if (o instanceof Map<?, ?> map) {
         ArrayList props = new ArrayList();
         loadMatchers(map, props::add);
         return new StatePropertiesPredicate(props);
      } else {
         throw new IllegalArgumentException("Invalid object for state properties. Given type: " + (o == null ? "NULL" : o.getClass().getName()));
      }
   }

   private static void loadMatchers(Map<?, ?> map, Consumer<PropertyMatcher> onAdd) {
      for (Entry<?, ?> entry : map.entrySet()) {
         if (entry.getKey() instanceof String propertyName) {
            Object unknownValue = entry.getValue();
            if (unknownValue instanceof Map<?, ?> minMaxValue) {
               Optional<String> min = Optional.ofNullable(getSafePropertyValue(minMaxValue.get("min")));
               Optional<String> max = Optional.ofNullable(getSafePropertyValue(minMaxValue.get("max")));
               RangedMatcher matcher = new RangedMatcher(min, max);
               onAdd.accept(new PropertyMatcher(propertyName, matcher));
            } else {
               String value = getSafePropertyValue(unknownValue);
               if (value == null) {
                  LootJS.LOG.warn("Can't validate value '" + unknownValue + "' for state properties. Skipping current property!");
               } else {
                  ExactMatcher matcher = new ExactMatcher(value);
                  onAdd.accept(new PropertyMatcher(propertyName, matcher));
               }
            }
         }
      }
   }

   @Nullable
   private static String getSafePropertyValue(@Nullable Object o) {
      if (o instanceof String s) {
         return s;
      } else if (o instanceof Boolean b) {
         return Boolean.toString(b);
      } else {
         return o instanceof Number n ? Integer.toString(n.intValue()) : null;
      }
   }
}

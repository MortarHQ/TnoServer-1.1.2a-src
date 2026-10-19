package com.blamejared.crafttweaker.api.recipe.replacement;

import java.util.function.Predicate;

public final class DescriptivePredicate<T> implements Predicate<T> {
   private final Predicate<T> predicate;
   private final String description;

   private DescriptivePredicate(Predicate<T> predicate, String description) {
      this.predicate = predicate;
      this.description = description;
   }

   public static <T> DescriptivePredicate<T> wrap(Predicate<T> predicate) {
      return of(predicate, "a custom predicate");
   }

   public static <T> DescriptivePredicate<T> of(Predicate<T> predicate, String description) {
      return new DescriptivePredicate<>(predicate, description);
   }

   @Override
   public boolean test(T t) {
      return this.predicate.test(t);
   }

   public String describe() {
      return this.description;
   }
}

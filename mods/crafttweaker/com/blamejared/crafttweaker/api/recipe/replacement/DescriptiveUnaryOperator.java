package com.blamejared.crafttweaker.api.recipe.replacement;

import java.util.function.UnaryOperator;

public final class DescriptiveUnaryOperator<T> implements UnaryOperator<T> {
   private final UnaryOperator<T> operator;
   private final String description;

   private DescriptiveUnaryOperator(UnaryOperator<T> operator, String description) {
      this.operator = operator;
      this.description = description;
   }

   public static <T> DescriptiveUnaryOperator<T> wrap(UnaryOperator<T> operator) {
      return of(operator, "a custom substitute");
   }

   public static <T> DescriptiveUnaryOperator<T> of(UnaryOperator<T> operator, String description) {
      return new DescriptiveUnaryOperator<>(operator, description);
   }

   @Override
   public T apply(T t) {
      return this.operator.apply(t);
   }

   public String describe() {
      return this.description;
   }
}

package com.blamejared.crafttweaker.api.recipe.component;

import java.util.List;
import java.util.Set;

public interface IDecomposedRecipe {
   static DecomposedRecipeBuilder builder() {
      return DecomposedRecipeBuilder.of();
   }

   <C> List<C> get(IRecipeComponent<C> var1);

   default <C> List<C> getOrThrow(IRecipeComponent<C> component) {
      List<C> list = this.get(component);
      if (list == null) {
         throw new IllegalArgumentException("Missing " + component.getCommandString());
      } else {
         return list;
      }
   }

   default <C> C getOrThrowSingle(IRecipeComponent<C> component) {
      List<C> list = this.getOrThrow(component);
      if (list.size() != 1) {
         String message = String.format(
            "Expected a list with a single element for %s, but got %d-sized list: %s", component.getCommandString(), list.size(), list
         );
         throw new IllegalArgumentException(message);
      } else {
         return list.get(0);
      }
   }

   <C> void set(IRecipeComponent<C> var1, List<C> var2);

   default <C> void set(IRecipeComponent<C> component, C object) {
      this.set(component, List.of(object));
   }

   Set<IRecipeComponent<?>> components();
}

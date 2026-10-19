package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.Services;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DecomposedRecipeBuilder {
   private final Map<IRecipeComponent<?>, List<?>> components = new HashMap<>();

   private DecomposedRecipeBuilder() {
   }

   static DecomposedRecipeBuilder of() {
      return new DecomposedRecipeBuilder();
   }

   public <C> DecomposedRecipeBuilder with(IRecipeComponent<C> component, C object) {
      return this.with(component, List.of(object));
   }

   public <C> DecomposedRecipeBuilder with(IRecipeComponent<C> component, List<C> object) {
      this.components.put(component, object);
      return this;
   }

   public IDecomposedRecipe build() {
      return new DecomposedRecipeBuilder.SimpleDecomposedRecipe(new HashMap<>(this.components));
   }

   private static final class SimpleDecomposedRecipe implements IDecomposedRecipe {
      private static final boolean FAST_PATH = !Services.PLATFORM.isDevelopmentEnvironment();
      private final Map<IRecipeComponent<?>, List<?>> components = new HashMap<>();

      SimpleDecomposedRecipe(Map<IRecipeComponent<?>, List<?>> components) {
         components.forEach((key, value) -> this.components.put((IRecipeComponent<?>)key, List.copyOf(value)));
      }

      @Override
      public <C> List<C> get(IRecipeComponent<C> component) {
         return GenericUtil.uncheck(this.components.get(this.verify(component)));
      }

      @Override
      public <C> void set(IRecipeComponent<C> component, List<C> object) {
         this.components.put(this.verify(component), List.copyOf(object));
      }

      @Override
      public Set<IRecipeComponent<?>> components() {
         return this.components.keySet();
      }

      private <C> IRecipeComponent<C> verify(IRecipeComponent<C> component) {
         if (FAST_PATH) {
            return component;
         }

         try {
            return IRecipeComponent.find(component.id());
         } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Component " + component + " was not registered", e);
         }
      }
   }
}

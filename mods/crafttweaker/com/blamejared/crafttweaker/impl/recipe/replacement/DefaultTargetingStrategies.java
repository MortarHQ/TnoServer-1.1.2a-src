package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.openzen.zencode.java.ZenCodeType;

public final class DefaultTargetingStrategies {
   private DefaultTargetingStrategies() {
   }

   public static <T> @ZenCodeType.Nullable T shallow(IRecipeComponent<T> component, T object, Function<T, @ZenCodeType.Nullable T> replacer) {
      return replacer.apply(object);
   }

   public static <T> @ZenCodeType.Nullable T deep(IRecipeComponent<T> component, T object, Function<T, @ZenCodeType.Nullable T> replacer) {
      List<T> components = new ArrayList<>(component.unwrap(object));
      boolean any = false;
      int i = 0;

      for (int s = components.size(); i < s; i++) {
         T replaced = replacer.apply(components.get(i));
         if (replaced != null) {
            any = true;
            components.set(i, replaced);
         }
      }

      return any ? component.wrap(components) : null;
   }
}

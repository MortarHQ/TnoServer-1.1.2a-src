package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

final class RecipeComponentRegistrationHandler implements IRecipeComponentRegistrationHandler {
   private final Map<ResourceLocation, IRecipeComponent<?>> components = new HashMap<>();

   private RecipeComponentRegistrationHandler() {
   }

   static Collection<IRecipeComponent<?>> gather(Consumer<IRecipeComponentRegistrationHandler> populatingConsumer) {
      RecipeComponentRegistrationHandler handler = new RecipeComponentRegistrationHandler();
      populatingConsumer.accept(handler);
      return Collections.unmodifiableCollection(handler.components.values());
   }

   @Override
   public <T> void registerRecipeComponent(IRecipeComponent<T> component) {
      ResourceLocation id = component.id();
      if (this.components.containsKey(id)) {
         throw new IllegalArgumentException("Component '" + id + "' was already registered with type " + this.components.get(id).objectType());
      }

      this.components.put(id, component);
   }
}

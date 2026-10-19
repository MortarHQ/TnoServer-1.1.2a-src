package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.world.item.crafting.Recipe;

final class RecipeHandlerRegistrationHandler implements IRecipeHandlerRegistrationHandler {
   private final Map<Class<? extends Recipe<?>>, RecipeHandlerRegistrationHandler.HandlerData> handlers = new HashMap<>();

   private RecipeHandlerRegistrationHandler() {
   }

   static Collection<RecipeHandlerRegistrationHandler.HandlerData> gather(Consumer<IRecipeHandlerRegistrationHandler> consumer) {
      RecipeHandlerRegistrationHandler handler = new RecipeHandlerRegistrationHandler();
      consumer.accept(handler);
      return Collections.unmodifiableCollection(handler.handlers.values());
   }

   @Override
   public <T extends Recipe<?>> void registerRecipeHandler(Class<? extends T> recipe, IRecipeHandler<T> handler) {
      if (recipe == GenericUtil.uncheck(Recipe.class)) {
         throw new IllegalArgumentException("Unable to register a global handler for all recipes: attempted with " + handler);
      }

      RecipeHandlerRegistrationHandler.HandlerData data = this.handlers.get(recipe);
      if (data != null) {
         throw new IllegalArgumentException(
            "A handler for the class '" + recipe.getName() + "' has already been registered: using " + data.handler() + " and not " + handler
         );
      }

      this.handlers.put(recipe, new RecipeHandlerRegistrationHandler.HandlerData(recipe, handler));
   }

   record HandlerData(Class<? extends Recipe<?>> recipeClass, IRecipeHandler<?> handler) {
   }
}

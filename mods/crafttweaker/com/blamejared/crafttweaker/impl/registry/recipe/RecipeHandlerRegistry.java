package com.blamejared.crafttweaker.impl.registry.recipe;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.world.item.crafting.Recipe;

public final class RecipeHandlerRegistry implements IRecipeHandlerRegistry {
   private final Map<Class<? extends Recipe<?>>, IRecipeHandler<?>> recipeHandlers = new HashMap<>();

   public <T extends Recipe<?>> void register(Class<? extends T> clazz, IRecipeHandler<T> handler) {
      this.recipeHandlers.put(clazz, handler);
   }

   @Override
   public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(T recipe) {
      return this.getRecipeHandlerFor(recipe.getClass());
   }

   @Override
   public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> recipeClass) {
      return (IRecipeHandler<T>)this.getRecipeHandlerForClass(recipeClass).orElse(DefaultRecipeHandler.INSTANCE);
   }

   private Optional<IRecipeHandler<?>> getRecipeHandlerForClass(Class<?> recipeClass) {
      Deque<Class<?>> classes = new ArrayDeque<>();
      classes.offer(recipeClass);

      while (!classes.isEmpty()) {
         Class<?> target = classes.poll();
         if (target != Recipe.class) {
            IRecipeHandler<?> attempt = this.recipeHandlers.get(target);
            if (attempt != null) {
               return Optional.of(attempt);
            }

            if (target.getSuperclass() != null) {
               classes.offer(target.getSuperclass());
            }

            Arrays.stream(target.getInterfaces()).forEach(classes::offer);
         }
      }

      return Optional.empty();
   }
}

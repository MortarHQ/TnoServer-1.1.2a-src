package com.blamejared.crafttweaker.api.recipe.handler;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface IRecipeHandlerRegistry {
   static <T extends Recipe<?>> IRecipeHandler<T> getHandlerFor(T recipe) {
      return CraftTweakerAPI.getRegistry().getRecipeHandlerFor(recipe);
   }

   static <T extends Recipe<?>> IRecipeHandler<T> getHandlerFor(RecipeHolder<T> recipe) {
      return CraftTweakerAPI.getRegistry().getRecipeHandlerFor(recipe);
   }

   <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(T var1);

   <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> var1);
}

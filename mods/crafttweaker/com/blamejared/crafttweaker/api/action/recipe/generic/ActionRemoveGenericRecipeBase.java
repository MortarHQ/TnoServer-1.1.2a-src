package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class ActionRemoveGenericRecipeBase extends ActionWholeRegistryBase {
   @Override
   public void apply() {
      Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
      int numberOfRemovedRecipes = 0;
      Map<RecipeType<Recipe<?>>, RecipeList<?>> recipeLists = this.getRecipeLists();

      for (RecipeType<Recipe<?>> recipeType : recipeLists.keySet()) {
         int removedRecipes = this.applyToRegistry(recipeLists.get(recipeType));
         if (removedRecipes > 0) {
            String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
            numberOfRemovedRecipesByType.put(commandString, removedRecipes);
            numberOfRemovedRecipes += removedRecipes;
         }
      }

      int numberOfRecipeTypes = numberOfRemovedRecipesByType.size();
      String recipeTypeList = this.makeRecipeList(numberOfRemovedRecipesByType);
      this.logger().info("Removed {} recipes registered in these {} recipe managers: {}", numberOfRemovedRecipes, numberOfRecipeTypes, recipeTypeList);
   }

   private int applyToRegistry(RecipeList<?> list) {
      int initialSize = list.getSize();
      list.removeByRecipeTest(this::shouldRemove);
      return initialSize - list.getSize();
   }

   protected abstract boolean shouldRemove(RecipeHolder<?> var1);
}

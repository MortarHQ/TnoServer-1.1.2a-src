package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ActionRemoveAllGenericRecipes extends ActionWholeRegistryBase {
   @Override
   public void apply() {
      Map<RecipeType<Recipe<?>>, RecipeList<?>> recipeLists = this.getRecipeLists();
      Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
      int totalRemoved = 0;

      for (RecipeType<Recipe<?>> recipeType : recipeLists.keySet()) {
         int removedRecipes = this.remove(recipeLists.get(recipeType));
         if (removedRecipes > 0) {
            totalRemoved += removedRecipes;
            String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
            numberOfRemovedRecipesByType.put(commandString, removedRecipes);
         }
      }

      int managerCount = numberOfRemovedRecipesByType.size();
      String recipeTypeList = this.makeRecipeList(numberOfRemovedRecipesByType);
      this.logger().info("Removed {} recipes across these {} managers: {}", totalRemoved, managerCount, recipeTypeList);
   }

   private int remove(RecipeList<?> list) {
      int size = list.getSize();
      list.removeAll();
      return size;
   }

   @Override
   public String describe() {
      return "Removing all recipes";
   }
}

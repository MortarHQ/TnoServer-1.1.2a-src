package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class ActionWholeRegistryBase extends CraftTweakerAction implements IRuntimeAction {
   private RecipeManager getRecipeManager() {
      return CraftTweakerAPI.getAccessibleElementsProvider().recipeManager();
   }

   protected Map<RecipeType<Recipe<?>>, RecipeList<?>> getRecipeLists() {
      return RecipeTypeBracketHandler.getManagerInstances()
         .stream()
         .map(IRecipeManager::getRecipeList)
         .collect(Collectors.toMap(RecipeList::getRecipeType, Function.identity()));
   }

   protected String makeRecipeList(Map<String, Integer> recipeTypes) {
      return recipeTypes.entrySet()
         .stream()
         .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
         .collect(Collectors.joining(", ", "[", "]"));
   }
}

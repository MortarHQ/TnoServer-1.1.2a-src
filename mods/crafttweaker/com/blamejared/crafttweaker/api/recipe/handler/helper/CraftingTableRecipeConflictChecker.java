package com.blamejared.crafttweaker.api.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;

public final class CraftingTableRecipeConflictChecker {
   public static boolean checkConflicts(IRecipeManager<?> manager, Recipe<?> first, Recipe<?> second) {
      if (first.isSpecial() || second.isSpecial()) {
         return false;
      } else {
         return !(first instanceof ShapedRecipe) && second instanceof ShapedRecipe
            ? redirect(manager, second, first)
            : checkConflictsMaybeDifferent(first, second);
      }
   }

   private static <T extends Recipe<?>> boolean redirect(IRecipeManager<?> manager, T second, Recipe<?> first) {
      return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(GenericUtil.uncheck(manager), second, first);
   }

   private static boolean checkConflictsMaybeDifferent(Recipe<?> first, Recipe<?> second) {
      if (first instanceof ShapedRecipe shapedFirst) {
         return second instanceof ShapedRecipe shapedSecond
            ? doShapedShapedConflict(shapedFirst, shapedSecond)
            : doShapedShapelessConflict(shapedFirst, second);
      } else {
         return doShapelessShapelessConflict(first, second);
      }
   }

   private static boolean doShapedShapedConflict(ShapedRecipe first, ShapedRecipe second) {
      if (first.getHeight() != second.getHeight()) {
         return false;
      }

      if (first.getWidth() != second.getWidth()) {
         return false;
      }

      NonNullList<Ingredient> firstIngredients = first.getIngredients();
      NonNullList<Ingredient> secondIngredients = second.getIngredients();

      for (int i = 0; i < firstIngredients.size(); i++) {
         Ingredient firstIngredient = (Ingredient)firstIngredients.get(i);
         Ingredient secondIngredient = (Ingredient)secondIngredients.get(i);
         if (!IngredientUtil.canConflict(firstIngredient, secondIngredient)) {
            return false;
         }
      }

      return true;
   }

   private static boolean doShapedShapelessConflict(ShapedRecipe first, Recipe<?> second) {
      return doShapelessShapelessConflict(
         first.getIngredients().stream().filter(it -> it != Ingredient.EMPTY).collect(Collectors.toList()), second.getIngredients()
      );
   }

   private static boolean doShapelessShapelessConflict(Recipe<?> first, Recipe<?> second) {
      return doShapelessShapelessConflict(first.getIngredients(), second.getIngredients());
   }

   private static boolean doShapelessShapelessConflict(List<Ingredient> first, List<Ingredient> second) {
      return first.size() != second.size() ? false : IngredientUtil.doIngredientsConflict(first, second);
   }
}

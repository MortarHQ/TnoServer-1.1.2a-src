package com.blamejared.crafttweaker.impl.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTransformRecipe;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import org.jetbrains.annotations.Nullable;

public final class SmithingRecipeConflictChecker {
   public static boolean doesConflict(IRecipeManager<?> manager, SmithingRecipe first, SmithingRecipe second) {
      SmithingRecipeConflictChecker.BasicRecipe firstRecipe = SmithingRecipeConflictChecker.BasicRecipe.from(first);
      SmithingRecipeConflictChecker.BasicRecipe secondRecipe = SmithingRecipeConflictChecker.BasicRecipe.from(second);
      return firstRecipe != null && secondRecipe != null
         ? IngredientUtil.canConflict(firstRecipe.template(), secondRecipe.template())
            && IngredientUtil.canConflict(firstRecipe.base(), secondRecipe.base())
            && IngredientUtil.canConflict(firstRecipe.addition(), secondRecipe.addition())
         : redirect(GenericUtil.uncheck(manager), second, first);
   }

   private static <T extends Recipe<?>> boolean redirect(IRecipeManager<?> manager, T second, Recipe<?> first) {
      return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(GenericUtil.uncheck(manager), second, first);
   }

   record BasicRecipe(Ingredient template, Ingredient base, Ingredient addition) {
      @Nullable
      static SmithingRecipeConflictChecker.BasicRecipe from(SmithingRecipe recipe) {
         if (recipe instanceof SmithingTrimRecipe trim) {
            return from(trim);
         } else {
            return recipe instanceof SmithingTransformRecipe transform ? from(transform) : null;
         }
      }

      static SmithingRecipeConflictChecker.BasicRecipe from(SmithingTrimRecipe recipe) {
         AccessSmithingTrimRecipe access = (AccessSmithingTrimRecipe)recipe;
         return new SmithingRecipeConflictChecker.BasicRecipe(
            access.crafttweaker$getTemplate(), access.crafttweaker$getBase(), access.crafttweaker$getAddition()
         );
      }

      static SmithingRecipeConflictChecker.BasicRecipe from(SmithingTransformRecipe recipe) {
         AccessSmithingTransformRecipe access = (AccessSmithingTransformRecipe)recipe;
         return new SmithingRecipeConflictChecker.BasicRecipe(
            access.crafttweaker$getTemplate(), access.crafttweaker$getBase(), access.crafttweaker$getAddition()
         );
      }
   }
}

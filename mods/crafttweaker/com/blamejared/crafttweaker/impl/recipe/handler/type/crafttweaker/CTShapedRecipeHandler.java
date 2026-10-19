package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

@IRecipeHandler.For(CTShapedRecipe.class)
public final class CTShapedRecipeHandler implements IRecipeHandler<CTShapedRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super CTShapedRecipe> manager, RegistryAccess registryAccess, RecipeHolder<CTShapedRecipe> holder) {
      return String.format(
         "craftingTable.addShaped(%s, %s, %s%s);",
         StringUtil.quoteAndEscape(holder.id()),
         ((CTShapedRecipe)holder.value()).getCtOutput().getCommandString(),
         Arrays.stream(((CTShapedRecipe)holder.value()).getCtIngredients())
            .map(row -> Arrays.stream(row).map(IIngredient::getCommandString).collect(Collectors.joining(", ", "[", "]")))
            .collect(Collectors.joining(", ", "[", "]")),
         ((CTShapedRecipe)holder.value()).getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CTShapedRecipe> manager, CTShapedRecipe firstRecipe, U secondRecipe) {
      return CraftingTableRecipeConflictChecker.checkConflicts(manager, firstRecipe, secondRecipe);
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CTShapedRecipe> manager, RegistryAccess registryAccess, CTShapedRecipe recipe) {
      int width = recipe.getWidth();
      int height = recipe.getHeight();
      RecipeFunction2D function = recipe.getFunction();
      List<IIngredient> ingredients = RecipeUtil.flatten(recipe.getCtIngredients(), width, height);
      IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D, Pair.of(width, height))
         .with(BuiltinRecipeComponents.Metadata.MIRROR_AXIS, recipe.getMirrorAxis())
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
         .with(BuiltinRecipeComponents.Output.ITEMS, recipe.getCtOutput())
         .build();
      if (function != null) {
         decomposedRecipe.set(BuiltinRecipeComponents.Processing.FUNCTION_2D, function);
      }

      return Optional.of(decomposedRecipe);
   }

   @Override
   public Optional<CTShapedRecipe> recompose(IRecipeManager<? super CTShapedRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      Pair<Integer, Integer> size = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
      MirrorAxis axis = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.MIRROR_AXIS);
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
      List<RecipeFunction2D> function = recipe.get(BuiltinRecipeComponents.Processing.FUNCTION_2D);
      IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
      int width = (Integer)size.getFirst();
      int height = (Integer)size.getSecond();
      if (width > 0 && height > 0) {
         if (width * height != ingredients.size()) {
            throw new IllegalArgumentException("Invalid shape size: incompatible with ingredients, got " + size + " with " + ingredients.size());
         }

         if (output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty item");
         }

         IIngredient[][] matrix = RecipeUtil.inflate(ingredients, width, height);
         RecipeFunction2D recipeFunction = function == null ? null : function.get(0);
         return Optional.of(new CTShapedRecipe(output, matrix, axis, recipeFunction));
      } else {
         throw new IllegalArgumentException("Invalid shape size: bounds must be positive but got " + size);
      }
   }
}

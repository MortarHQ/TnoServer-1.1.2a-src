package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.crafttweaker.api.util.StringUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

@IRecipeHandler.For(CTShapelessRecipe.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTShapelessRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super CTShapelessRecipe> manager, RegistryAccess registryAccess, RecipeHolder<CTShapelessRecipe> holder) {
      CTShapelessRecipe recipe = (CTShapelessRecipe)holder.value();
      return String.format(
         "craftingTable.addShapeless(%s, %s, %s%s);",
         StringUtil.quoteAndEscape(holder.id()),
         recipe.getCtOutput().getCommandString(),
         Arrays.stream(recipe.getCtIngredients()).map(IIngredient::getCommandString).collect(Collectors.joining(", ", "[", "]")),
         recipe.getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CTShapelessRecipe> manager, CTShapelessRecipe firstRecipe, U secondRecipe) {
      return CraftingTableRecipeConflictChecker.checkConflicts(manager, firstRecipe, secondRecipe);
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CTShapelessRecipe> manager, RegistryAccess registryAccess, CTShapelessRecipe recipe) {
      RecipeFunction1D function = recipe.getFunction();
      List<IIngredient> ingredients = Arrays.asList(recipe.getCtIngredients());
      IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
         .with(BuiltinRecipeComponents.Output.ITEMS, recipe.getCtOutput())
         .build();
      if (function != null) {
         decomposedRecipe.set(BuiltinRecipeComponents.Processing.FUNCTION_1D, function);
      }

      return Optional.of(decomposedRecipe);
   }

   @Override
   public Optional<CTShapelessRecipe> recompose(IRecipeManager<? super CTShapelessRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
      List<RecipeFunction1D> function = recipe.get(BuiltinRecipeComponents.Processing.FUNCTION_1D);
      IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
      if (ingredients.stream().anyMatch(IIngredient::isEmpty)) {
         throw new IllegalArgumentException("Invalid inputs: found empty ingredient in list " + ingredients);
      }

      if (output.isEmpty()) {
         throw new IllegalArgumentException("Invalid output: empty item");
      }

      IIngredient[] list = ingredients.toArray(IIngredient[]::new);
      RecipeFunction1D recipeFunction = function == null ? null : function.get(0);
      CTShapelessRecipe.checkEmptyIngredient(CraftTweakerConstants.rl("__under_replacement__"), list);
      return Optional.of(new CTShapelessRecipe(output, list, recipeFunction));
   }
}

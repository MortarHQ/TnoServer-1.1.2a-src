package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.recipe.handler.helper.SmithingRecipeConflictChecker;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;

@IRecipeHandler.For(SmithingTrimRecipe.class)
public final class SmithingTrimRecipeHandler implements IRecipeHandler<SmithingTrimRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super SmithingTrimRecipe> manager, RegistryAccess registryAccess, RecipeHolder<SmithingTrimRecipe> holder) {
      SmithingTrimRecipe recipe = (SmithingTrimRecipe)holder.value();
      return String.format(
         "smithing.addTrimRecipe(%s, %s, %s, %s);",
         StringUtil.quoteAndEscape(holder.id()),
         IIngredient.fromIngredient(((AccessSmithingTrimRecipe)recipe).crafttweaker$getTemplate()).getCommandString(),
         IIngredient.fromIngredient(((AccessSmithingTrimRecipe)recipe).crafttweaker$getBase()).getCommandString(),
         IIngredient.fromIngredient(((AccessSmithingTrimRecipe)recipe).crafttweaker$getAddition()).getCommandString()
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super SmithingTrimRecipe> manager, SmithingTrimRecipe firstRecipe, U secondRecipe) {
      return !(secondRecipe instanceof SmithingRecipe)
         ? false
         : SmithingRecipeConflictChecker.doesConflict(manager, firstRecipe, GenericUtil.uncheck(secondRecipe));
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super SmithingTrimRecipe> manager, RegistryAccess registryAccess, SmithingTrimRecipe recipe) {
      AccessSmithingTrimRecipe access = (AccessSmithingTrimRecipe)recipe;
      IIngredient template = IIngredient.fromIngredient(access.crafttweaker$getTemplate());
      IIngredient base = IIngredient.fromIngredient(access.crafttweaker$getBase());
      IIngredient addition = IIngredient.fromIngredient(access.crafttweaker$getAddition());
      IDecomposedRecipe decomposed = IDecomposedRecipe.builder().with(BuiltinRecipeComponents.Input.INGREDIENTS, List.of(template, base, addition)).build();
      return Optional.of(decomposed);
   }

   @Override
   public Optional<SmithingTrimRecipe> recompose(IRecipeManager<? super SmithingTrimRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
      if (ingredients.size() != 3) {
         throw new IllegalArgumentException("Invalid inputs: expected three ingredients for recipe, but got " + ingredients.size() + ": " + ingredients);
      }

      if (ingredients.stream().anyMatch(IIngredient::isEmpty)) {
         throw new IllegalArgumentException("Invalid inputs: empty ingredients");
      }

      Ingredient template = ingredients.get(0).asVanillaIngredient();
      Ingredient base = ingredients.get(1).asVanillaIngredient();
      Ingredient addition = ingredients.get(2).asVanillaIngredient();
      return Optional.of(AccessSmithingTrimRecipe.crafttweaker$createSmithingTrimRecipe(template, base, addition));
   }
}

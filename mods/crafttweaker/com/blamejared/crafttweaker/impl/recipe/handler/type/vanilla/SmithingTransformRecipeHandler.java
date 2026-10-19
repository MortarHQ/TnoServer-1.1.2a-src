package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.recipe.handler.helper.SmithingRecipeConflictChecker;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTransformRecipe;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

@IRecipeHandler.For(SmithingTransformRecipe.class)
public final class SmithingTransformRecipeHandler implements IRecipeHandler<SmithingTransformRecipe> {
   @Override
   public String dumpToCommandString(
      IRecipeManager<? super SmithingTransformRecipe> manager, RegistryAccess registryAccess, RecipeHolder<SmithingTransformRecipe> holder
   ) {
      SmithingTransformRecipe recipe = (SmithingTransformRecipe)holder.value();
      return String.format(
         "smithing.addTransformRecipe(%s, %s, %s, %s, %s);",
         StringUtil.quoteAndEscape(holder.id()),
         ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
         IIngredient.fromIngredient(((AccessSmithingTransformRecipe)recipe).crafttweaker$getTemplate()).getCommandString(),
         IIngredient.fromIngredient(((AccessSmithingTransformRecipe)recipe).crafttweaker$getBase()).getCommandString(),
         IIngredient.fromIngredient(((AccessSmithingTransformRecipe)recipe).crafttweaker$getAddition()).getCommandString()
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(
      IRecipeManager<? super SmithingTransformRecipe> manager, SmithingTransformRecipe firstRecipe, U secondRecipe
   ) {
      return !(secondRecipe instanceof SmithingRecipe)
         ? false
         : SmithingRecipeConflictChecker.doesConflict(manager, firstRecipe, GenericUtil.uncheck(secondRecipe));
   }

   public Optional<IDecomposedRecipe> decompose(
      IRecipeManager<? super SmithingTransformRecipe> manager, RegistryAccess registryAccess, SmithingTransformRecipe recipe
   ) {
      AccessSmithingTransformRecipe access = (AccessSmithingTransformRecipe)recipe;
      IIngredient template = IIngredient.fromIngredient(access.crafttweaker$getTemplate());
      IIngredient base = IIngredient.fromIngredient(access.crafttweaker$getBase());
      IIngredient addition = IIngredient.fromIngredient(access.crafttweaker$getAddition());
      IDecomposedRecipe decomposed = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, List.of(template, base, addition))
         .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem(registryAccess)))
         .build();
      return Optional.of(decomposed);
   }

   @Override
   public Optional<SmithingTransformRecipe> recompose(
      IRecipeManager<? super SmithingTransformRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe
   ) {
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
      IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
      if (ingredients.size() != 3) {
         throw new IllegalArgumentException("Invalid inputs: expected three ingredients for recipe, but got " + ingredients.size() + ": " + ingredients);
      }

      if (ingredients.stream().anyMatch(IIngredient::isEmpty)) {
         throw new IllegalArgumentException("Invalid inputs: empty ingredients");
      }

      if (output.isEmpty()) {
         throw new IllegalArgumentException("Invalid outputs: empty item");
      }

      Ingredient template = ingredients.get(0).asVanillaIngredient();
      Ingredient base = ingredients.get(1).asVanillaIngredient();
      Ingredient addition = ingredients.get(2).asVanillaIngredient();
      return Optional.of(new SmithingTransformRecipe(template, base, addition, output.getInternal()));
   }
}

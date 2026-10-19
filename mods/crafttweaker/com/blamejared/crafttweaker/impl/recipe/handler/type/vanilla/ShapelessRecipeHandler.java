package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

@IRecipeHandler.For(ShapelessRecipe.class)
public final class ShapelessRecipeHandler implements IRecipeHandler<ShapelessRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super ShapelessRecipe> manager, RegistryAccess registryAccess, RecipeHolder<ShapelessRecipe> holder) {
      ShapelessRecipe recipe = (ShapelessRecipe)holder.value();
      return String.format(
         "craftingTable.addShapeless(%s, %s, %s);",
         StringUtil.quoteAndEscape(holder.id()),
         ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
         recipe.getIngredients().stream().map(IIngredient::fromIngredient).map(IIngredient::getCommandString).collect(Collectors.joining(", ", "[", "]"))
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ShapelessRecipe> manager, ShapelessRecipe firstRecipe, U secondRecipe) {
      return CraftingTableRecipeConflictChecker.checkConflicts(manager, firstRecipe, secondRecipe);
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ShapelessRecipe> manager, RegistryAccess registryAccess, ShapelessRecipe recipe) {
      List<IIngredient> ingredients = recipe.getIngredients().stream().map(IIngredient::fromIngredient).toList();
      IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
         .with(BuiltinRecipeComponents.Metadata.CRAFTING_BOOK_CATEGORY, recipe.category())
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
         .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem(registryAccess)))
         .build();
      return Optional.of(decomposedRecipe);
   }

   @Override
   public Optional<ShapelessRecipe> recompose(IRecipeManager<? super ShapelessRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
      CraftingBookCategory category = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.CRAFTING_BOOK_CATEGORY);
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
      IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
      if (ingredients.stream().anyMatch(IIngredient::isEmpty)) {
         throw new IllegalArgumentException("Invalid inputs: found empty ingredient in list " + ingredients);
      }

      if (output.isEmpty()) {
         throw new IllegalArgumentException("Invalid output: empty item");
      }

      NonNullList<Ingredient> recipeIngredients = ingredients.stream()
         .map(IIngredient::asVanillaIngredient)
         .collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
      return Optional.of(new ShapelessRecipe(group, category, output.getInternal(), recipeIngredients));
   }
}

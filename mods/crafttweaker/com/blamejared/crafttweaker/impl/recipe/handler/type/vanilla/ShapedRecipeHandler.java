package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mojang.datafixers.util.Pair;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;

@IRecipeHandler.For(ShapedRecipe.class)
public final class ShapedRecipeHandler implements IRecipeHandler<ShapedRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super ShapedRecipe> manager, RegistryAccess registryAccess, RecipeHolder<ShapedRecipe> holder) {
      ShapedRecipe recipe = (ShapedRecipe)holder.value();
      NonNullList<Ingredient> ingredients = recipe.getIngredients();
      return String.format(
         "craftingTable.addShaped(%s, %s, %s);",
         StringUtil.quoteAndEscape(holder.id()),
         ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
         IntStream.range(0, recipe.getHeight())
            .mapToObj(
               y -> IntStream.range(0, recipe.getWidth())
                  .mapToObj(x -> (Ingredient)ingredients.get(y * recipe.getWidth() + x))
                  .map(IIngredient::fromIngredient)
                  .map(IIngredient::getCommandString)
                  .collect(Collectors.joining(", ", "[", "]"))
            )
            .collect(Collectors.joining(", ", "[", "]"))
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ShapedRecipe> manager, ShapedRecipe firstRecipe, U secondRecipe) {
      return CraftingTableRecipeConflictChecker.checkConflicts(manager, firstRecipe, secondRecipe);
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ShapedRecipe> manager, RegistryAccess registryAccess, ShapedRecipe recipe) {
      List<IIngredient> ingredients = recipe.getIngredients().stream().map(IIngredient::fromIngredient).toList();
      IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
         .with(BuiltinRecipeComponents.Metadata.CRAFTING_BOOK_CATEGORY, recipe.category())
         .with(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D, Pair.of(recipe.getWidth(), recipe.getHeight()))
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
         .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem(registryAccess)))
         .build();
      return Optional.of(decomposedRecipe);
   }

   @Override
   public Optional<ShapedRecipe> recompose(IRecipeManager<? super ShapedRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
      CraftingBookCategory category = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.CRAFTING_BOOK_CATEGORY);
      Pair<Integer, Integer> size = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
      List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
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

         NonNullList<Ingredient> recipeIngredients = ingredients.stream()
            .map(IIngredient::asVanillaIngredient)
            .collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
         return Optional.of(new ShapedRecipe(group, category, RecipeUtil.createPattern(recipeIngredients, width, height), output.getInternal()));
      } else {
         throw new IllegalArgumentException("Invalid shape size: bounds must be positive but got " + size);
      }
   }
}

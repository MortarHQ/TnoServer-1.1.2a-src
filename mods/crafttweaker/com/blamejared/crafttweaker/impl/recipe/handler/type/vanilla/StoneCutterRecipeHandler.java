package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.StonecutterRecipe;

@IRecipeHandler.For(StonecutterRecipe.class)
public final class StoneCutterRecipeHandler implements IRecipeHandler<StonecutterRecipe> {
   @Override
   public String dumpToCommandString(IRecipeManager<? super StonecutterRecipe> manager, RegistryAccess registryAccess, RecipeHolder<StonecutterRecipe> holder) {
      StonecutterRecipe recipe = (StonecutterRecipe)holder.value();
      return String.format(
         "stoneCutter.addRecipe(%s, %s, %s);",
         StringUtil.quoteAndEscape(holder.id()),
         ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
         IIngredient.fromIngredient((Ingredient)recipe.getIngredients().get(0)).getCommandString()
      );
   }

   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super StonecutterRecipe> manager, StonecutterRecipe firstRecipe, U secondRecipe) {
      return false;
   }

   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super StonecutterRecipe> manager, RegistryAccess registryAccess, StonecutterRecipe recipe) {
      IIngredient input = IIngredient.fromIngredient((Ingredient)recipe.getIngredients().get(0));
      IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
         .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
         .with(BuiltinRecipeComponents.Input.INGREDIENTS, input)
         .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem(registryAccess)))
         .build();
      return Optional.of(decomposedRecipe);
   }

   @Override
   public Optional<StonecutterRecipe> recompose(IRecipeManager<? super StonecutterRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
      IIngredient input = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS);
      IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
      return Optional.of(new StonecutterRecipe(group, input.asVanillaIngredient(), output.getInternal()));
   }
}

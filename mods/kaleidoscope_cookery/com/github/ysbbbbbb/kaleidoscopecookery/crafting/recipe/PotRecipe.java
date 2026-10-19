package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

public record PotRecipe(int time, int stirFryCount, Ingredient carrier, NonNullList<Ingredient> ingredients, ItemStack result)
   implements BaseRecipe<SimpleInput> {
   public PotRecipe(int time, int stirFryCount, Ingredient carrier, List<Ingredient> ingredients, ItemStack result) {
      this(time, stirFryCount, carrier, NonNullList.of(Ingredient.EMPTY, BaseRecipe.fillInputs(ingredients)), result);
   }

   public boolean matches(SimpleInput simpleInput, Level level) {
      return RecipeMatcher.findMatches(simpleInput.getInputs(), this.ingredients) != null;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.ingredients;
   }

   public ItemStack getResultItem(Provider registryAccess) {
      return this.result;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)ModRecipes.POT_SERIALIZER.get();
   }

   public RecipeType<?> getType() {
      return ModRecipes.POT_RECIPE;
   }
}

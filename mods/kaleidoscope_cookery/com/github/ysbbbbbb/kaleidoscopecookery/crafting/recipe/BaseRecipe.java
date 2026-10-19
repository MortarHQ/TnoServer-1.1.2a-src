package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

public interface BaseRecipe<C extends RecipeInput> extends Recipe<C> {
   int RECIPES_SIZE = 9;

   static Ingredient[] fillInputs(List<Ingredient> inputs) {
      Ingredient[] newInputs = new Ingredient[9];

      for (int i = 0; i < 9; i++) {
         if (i < inputs.size()) {
            newInputs[i] = inputs.get(i);
         } else {
            newInputs[i] = Ingredient.EMPTY;
         }
      }

      return newInputs;
   }

   default ItemStack assemble(C container, Provider registryAccess) {
      return this.getResultItem(registryAccess).copy();
   }

   default boolean isSpecial() {
      return true;
   }

   default boolean canCraftInDimensions(int width, int height) {
      return false;
   }
}

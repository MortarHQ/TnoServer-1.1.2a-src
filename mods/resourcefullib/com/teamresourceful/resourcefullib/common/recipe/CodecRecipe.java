package com.teamresourceful.resourcefullib.common.recipe;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public interface CodecRecipe<C extends RecipeInput> extends Recipe<C> {
   default boolean isSpecial() {
      return true;
   }

   @NotNull
   default ItemStack assemble(C container, Provider provider) {
      return ItemStack.EMPTY;
   }

   @NotNull
   default ItemStack getResultItem(Provider provider) {
      return ItemStack.EMPTY;
   }

   default boolean canCraftInDimensions(int pWidth, int pHeight) {
      return true;
   }

   @NotNull
   default RecipeSerializer<?> getSerializer() {
      return this.serializer();
   }

   CodecRecipeSerializer<? extends CodecRecipe<C>> serializer();
}

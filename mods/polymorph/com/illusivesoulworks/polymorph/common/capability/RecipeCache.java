package com.illusivesoulworks.polymorph.common.capability;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class RecipeCache {
   private final RecipeCache.Entry<? extends RecipeInput, ? extends Recipe<?>>[] entries;
   private WeakReference<RecipeManager> cachedRecipeManager = new WeakReference<>(null);

   public RecipeCache(int size) {
      this.entries = new RecipeCache.Entry[size];
   }

   public <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> get(Level level, RecipeType<T> recipeType, I recipeInput) {
      if (recipeInput.isEmpty()) {
         return List.of();
      }

      this.validateRecipeManager(level);

      for (int i = 0; i < this.entries.length; i++) {
         RecipeCache.Entry<?, ?> entry = this.entries[i];
         if (entry != null && entry.matches(recipeInput)) {
            this.moveEntryToFront(i);
            return (List<RecipeHolder<T>>)entry.recipes;
         }
      }

      return this.compute(level, recipeType, recipeInput);
   }

   private void validateRecipeManager(Level level) {
      RecipeManager recipeManager = level.getRecipeManager();
      if (recipeManager != this.cachedRecipeManager.get()) {
         this.cachedRecipeManager = new WeakReference<>(recipeManager);
         Arrays.fill(this.entries, null);
      }
   }

   private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> compute(Level level, RecipeType<T> recipeType, I recipeInput) {
      List<RecipeHolder<T>> list = level.getRecipeManager().getRecipesFor(recipeType, recipeInput, level);
      this.insert(recipeInput, list);
      return list;
   }

   private void moveEntryToFront(int index) {
      if (index > 0) {
         RecipeCache.Entry<?, ?> entry = this.entries[index];
         System.arraycopy(this.entries, 0, this.entries, 1, index);
         this.entries[0] = (RecipeCache.Entry<? extends RecipeInput, ? extends Recipe<?>>)entry;
      }
   }

   private <I extends RecipeInput, T extends Recipe<I>> void insert(I recipeInput, List<RecipeHolder<T>> recipes) {
      NonNullList<ItemStack> list = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);

      for (int i = 0; i < recipeInput.size(); i++) {
         list.set(i, recipeInput.getItem(i).copyWithCount(1));
      }

      System.arraycopy(this.entries, 0, this.entries, 1, this.entries.length - 1);
      RecipeCache.Entry<I, T> entry;
      if (recipeInput instanceof CraftingInput craftingInput) {
         entry = new RecipeCache.CraftingEntry<>(list, craftingInput.width(), craftingInput.height(), recipeInput.size(), recipes);
      } else {
         entry = new RecipeCache.Entry<>(list, recipeInput.size(), recipes);
      }

      this.entries[0] = entry;
   }

   static class CraftingEntry<I extends RecipeInput, T extends Recipe<I>> extends RecipeCache.Entry<I, T> {
      private final int width;
      private final int height;

      public CraftingEntry(NonNullList<ItemStack> list, int width, int height, int size, List<RecipeHolder<T>> recipes) {
         super(list, size, recipes);
         this.width = width;
         this.height = height;
      }

      @Override
      public boolean matches(RecipeInput recipeInput) {
         return recipeInput instanceof CraftingInput craftingInput && craftingInput.width() == this.width && craftingInput.height() == this.height
            ? super.matches(recipeInput)
            : false;
      }
   }

   static class Entry<I extends RecipeInput, T extends Recipe<I>> {
      private final NonNullList<ItemStack> key;
      private final int size;
      private final List<RecipeHolder<T>> recipes;

      public Entry(NonNullList<ItemStack> list, int size, List<RecipeHolder<T>> recipes) {
         this.key = list;
         this.size = size;
         this.recipes = recipes;
      }

      public boolean matches(RecipeInput recipeInput) {
         if (this.size == recipeInput.size()) {
            for (int i = 0; i < this.key.size(); i++) {
               if (!ItemStack.isSameItemSameComponents((ItemStack)this.key.get(i), recipeInput.getItem(i))) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }
}

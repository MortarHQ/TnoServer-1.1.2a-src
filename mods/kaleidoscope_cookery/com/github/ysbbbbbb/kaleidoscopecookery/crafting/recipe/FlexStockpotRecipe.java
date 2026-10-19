package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.StockpotInput;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

public record FlexStockpotRecipe(
   NonNullList<Ingredient> ingredients, ResourceLocation soupBase, ItemStack result, int time, Ingredient carrier, StockpotVisuals visuals
) implements BaseRecipe<StockpotInput> {
   public FlexStockpotRecipe(List<Ingredient> ingredients, ResourceLocation soupBase, ItemStack result, int time, Ingredient carrier, StockpotVisuals visuals) {
      this(NonNullList.of(Ingredient.EMPTY, BaseRecipe.fillInputs(ingredients)), soupBase, result, time, carrier, visuals);
   }

   public FlexStockpotRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int time, ItemStack container) {
      this(ingredients, StockpotRecipeSerializer.DEFAULT_SOUP_BASE, result, time, Ingredient.of(new ItemStack[]{container}), StockpotVisuals.DEFAULT);
   }

   public ResourceLocation cookingTexture() {
      return this.visuals.cookingTexture();
   }

   public ResourceLocation finishedTexture() {
      return this.visuals.finishedTexture();
   }

   public int cookingBubbleColor() {
      return this.visuals.cookingBubbleColor();
   }

   public int finishedBubbleColor() {
      return this.visuals.finishedBubbleColor();
   }

   public boolean matches(StockpotInput container, Level level) {
      if (!container.getSoupBase().equals(this.soupBase)) {
         return false;
      }

      NonNullList<ItemStack> merged = NonNullList.withSize(9, ItemStack.EMPTY);
      Set<Item> record = Sets.newHashSet();
      int index = 0;

      for (int i = 0; i < container.size(); i++) {
         ItemStack stack = container.getItem(i);
         Item item = stack.getItem();
         if (!stack.isEmpty() && !record.contains(item)) {
            merged.set(index, stack);
            record.add(item);
            if (++index >= merged.size()) {
               break;
            }
         }
      }

      return RecipeMatcher.findMatches(merged, this.ingredients) != null;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.ingredients;
   }

   public ItemStack getResultItem(Provider registryAccess) {
      return this.result;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)ModRecipes.FLEX_STOCKPOT_SERIALIZER.get();
   }

   public RecipeType<?> getType() {
      return ModRecipes.FLEX_STOCKPOT_RECIPE;
   }
}

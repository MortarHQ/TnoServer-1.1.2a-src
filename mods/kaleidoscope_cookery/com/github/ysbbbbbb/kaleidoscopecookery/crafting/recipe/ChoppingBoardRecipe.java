package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class ChoppingBoardRecipe extends SingleItemRecipe {
   private final int cutCount;
   private final ResourceLocation modelId;

   public ChoppingBoardRecipe(Ingredient ingredient, ItemStack result, int cutCount, ResourceLocation modelId) {
      super(ModRecipes.CHOPPING_BOARD_RECIPE, (RecipeSerializer)ModRecipes.CHOPPING_BOARD_SERIALIZER.get(), "", ingredient, result);
      this.cutCount = Math.max(cutCount, 1);
      this.modelId = modelId;
   }

   public boolean matches(SingleRecipeInput inv, Level level) {
      return this.ingredient.test(inv.getItem(0));
   }

   public boolean isSpecial() {
      return true;
   }

   public Ingredient getIngredient() {
      return this.ingredient;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public int getCutCount() {
      return this.cutCount;
   }

   public ResourceLocation getModelId() {
      return this.modelId;
   }
}

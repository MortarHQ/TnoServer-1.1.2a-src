package com.github.ysbbbbbb.kaleidoscopecookery.crafting;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.RiceBowlRecipe;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class RiceBowlRecipeMaker {
   public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
      List<RecipeHolder<CraftingRecipe>> recipes = Lists.newArrayList();
      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return List.of();
      }

      level.getRecipeManager().byType(RecipeType.CRAFTING).forEach(holder -> addRiceBowlRecipe(recipes, holder));
      return recipes;
   }

   private static void addRiceBowlRecipe(List<RecipeHolder<CraftingRecipe>> recipes, RecipeHolder<CraftingRecipe> recipe) {
      if (recipe.value() instanceof RiceBowlRecipe riceBowlRecipe) {
         NonNullList<Ingredient> ingredients = riceBowlRecipe.getIngredients();
         ItemStack result = riceBowlRecipe.getResult();
         ShapelessRecipe shapelessRecipe = new ShapelessRecipe("rice_bowl", CraftingBookCategory.MISC, result, ingredients);
         recipes.add(new RecipeHolder(recipe.id(), shapelessRecipe));
      }
   }
}

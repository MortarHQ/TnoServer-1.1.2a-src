package net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.util.ColorHelper;

public class DyeRecipesMaker {
   private DyeRecipesMaker() {
   }

   public static List<RecipeHolder<CraftingRecipe>> getRecipes() {
      return getRecipes(RecipeHolder::new);
   }

   public static <R> List<R> getRecipes(BiFunction<ResourceLocation, ShapedRecipe, R> transformRecipe) {
      List<R> recipes = new ArrayList<>();
      addSingleColorRecipes(recipes, transformRecipe);
      addMultipleColorsRecipe(recipes, transformRecipe);
      return recipes;
   }

   private static <R> void addMultipleColorsRecipe(List<R> recipes, BiFunction<ResourceLocation, ShapedRecipe, R> transformRecipe) {
      NonNullList<Ingredient> ingredients = NonNullList.create();
      ingredients.add(Ingredient.of(DyeColor.YELLOW.getTag()));
      ingredients.add(Ingredient.of(new ItemLike[]{(ItemLike)ModItems.BACKPACK.get()}));
      ingredients.add(Ingredient.EMPTY);
      ingredients.add(Ingredient.of(DyeColor.LIME.getTag()));
      ingredients.add(Ingredient.of(DyeColor.BLUE.getTag()));
      ingredients.add(Ingredient.of(DyeColor.BLACK.getTag()));
      ItemStack backpackOutput = new ItemStack((ItemLike)ModItems.BACKPACK.get());
      int clothColor = ColorHelper.calculateColor(-3382982, -3382982, List.of(DyeColor.YELLOW, DyeColor.LIME));
      int trimColor = ColorHelper.calculateColor(-10342886, -10342886, List.of(DyeColor.BLUE, DyeColor.BLACK));
      BackpackWrapper.fromStack(backpackOutput).setColors(clothColor, trimColor);
      ShapedRecipePattern pattern = new ShapedRecipePattern(3, 1, ingredients, Optional.empty());
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "multiple_colors");
      recipes.add(transformRecipe.apply(id, new ShapedRecipe("", CraftingBookCategory.MISC, pattern, backpackOutput)));
   }

   private static <R> void addSingleColorRecipes(List<R> recipes, BiFunction<ResourceLocation, ShapedRecipe, R> transformRecipe) {
      for (DyeColor color : DyeColor.values()) {
         ItemStack backpackOutput = new ItemStack((ItemLike)ModItems.BACKPACK.get());
         BackpackWrapper.fromStack(backpackOutput).setColors(color.getTextureDiffuseColor(), color.getTextureDiffuseColor());
         NonNullList<Ingredient> ingredients = NonNullList.create();
         ingredients.add(Ingredient.of(new ItemLike[]{(ItemLike)ModItems.BACKPACK.get()}));
         ingredients.add(Ingredient.of(color.getTag()));
         ShapedRecipePattern pattern = new ShapedRecipePattern(1, 2, ingredients, Optional.empty());
         ResourceLocation id = ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "single_color_" + color.getSerializedName());
         recipes.add(transformRecipe.apply(id, new ShapedRecipe("", CraftingBookCategory.MISC, pattern, backpackOutput)));
      }
   }
}

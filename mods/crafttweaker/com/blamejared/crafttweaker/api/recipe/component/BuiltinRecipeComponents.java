package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction0D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.google.gson.reflect.TypeToken;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public final class BuiltinRecipeComponents {
   private BuiltinRecipeComponents() {
   }

   public static final class Input {
      public static final IRecipeComponent<IIngredient> INGREDIENTS = IRecipeComponent.composite(
         CraftTweakerConstants.rl("input/ingredients"),
         new TypeToken<IIngredient>() {},
         RecipeComponentEqualityCheckers::areIngredientsEqual,
         ingredient -> Arrays.asList(ingredient.getItems()),
         items -> items.isEmpty() ? IIngredientEmpty.getInstance() : items.stream().reduce(IIngredient::or).orElseThrow()
      );
      public static final IRecipeComponent<IIngredientWithAmount> INGREDIENTS_WITH_AMOUNTS = IRecipeComponent.simple(
         CraftTweakerConstants.rl("input/ingredients_with_amounts"),
         new TypeToken<IIngredientWithAmount>() {},
         RecipeComponentEqualityCheckers::areIngredientsEqual
      );
      public static final IRecipeComponent<CTFluidIngredient> FLUID_INGREDIENTS = IRecipeComponent.composite(
         CraftTweakerConstants.rl("input/fluid_ingredients"),
         new TypeToken<CTFluidIngredient>() {},
         RecipeComponentEqualityCheckers::areFluidIngredientsEqual,
         ingredient -> ingredient instanceof CTFluidIngredient.CompoundFluidIngredient cfi ? cfi.getElements() : List.of(ingredient),
         items -> items.isEmpty() ? CTFluidIngredient.EMPTY.get() : items.stream().reduce(CTFluidIngredient::asCompound).orElseThrow()
      );

      private Input() {
      }
   }

   public static final class Metadata {
      public static final IRecipeComponent<String> GROUP = IRecipeComponent.simple(
         CraftTweakerConstants.rl("metadata/group"), new TypeToken<String>() {}, Object::equals
      );
      public static final IRecipeComponent<CookingBookCategory> COOKING_BOOK_CATEGORY = IRecipeComponent.simple(
         CraftTweakerConstants.rl("metadata/cooking_book_category"), new TypeToken<CookingBookCategory>() {}, Object::equals
      );
      public static final IRecipeComponent<CraftingBookCategory> CRAFTING_BOOK_CATEGORY = IRecipeComponent.simple(
         CraftTweakerConstants.rl("metadata/crafting_book_category"), new TypeToken<CraftingBookCategory>() {}, Object::equals
      );
      public static final IRecipeComponent<MirrorAxis> MIRROR_AXIS = IRecipeComponent.simple(
         CraftTweakerConstants.rl("metadata/mirror_axis"), new TypeToken<MirrorAxis>() {}, Object::equals
      );
      public static final IRecipeComponent<Pair<Integer, Integer>> SHAPE_SIZE_2D = IRecipeComponent.simple(
         CraftTweakerConstants.rl("metadata/shape_size_2d"), new TypeToken<Pair<Integer, Integer>>() {}, RecipeComponentEqualityCheckers::notComparable
      );

      private Metadata() {
      }
   }

   public static final class Output {
      public static final IRecipeComponent<Percentaged<IItemStack>> CHANCED_ITEMS = IRecipeComponent.simple(
         CraftTweakerConstants.rl("output/chanced_items"), new TypeToken<Percentaged<IItemStack>>() {}, RecipeComponentEqualityCheckers::areStacksEqual
      );
      public static final IRecipeComponent<Float> EXPERIENCE = IRecipeComponent.simple(
         CraftTweakerConstants.rl("output/experience"), new TypeToken<Float>() {}, RecipeComponentEqualityCheckers::areNumbersEqual
      );
      public static final IRecipeComponent<IFluidStack> FLUIDS = IRecipeComponent.simple(
         CraftTweakerConstants.rl("output/fluids"), new TypeToken<IFluidStack>() {}, RecipeComponentEqualityCheckers::areFluidStacksEqual
      );
      public static final IRecipeComponent<IItemStack> ITEMS = IRecipeComponent.simple(
         CraftTweakerConstants.rl("output/items"), new TypeToken<IItemStack>() {}, RecipeComponentEqualityCheckers::areStacksEqual
      );

      private Output() {
      }
   }

   public static final class Processing {
      public static final IRecipeComponent<RecipeFunction0D> FUNCTION_0D = IRecipeComponent.simple(
         CraftTweakerConstants.rl("processing/function_0d"), new TypeToken<RecipeFunction0D>() {}, RecipeComponentEqualityCheckers::notComparable
      );
      public static final IRecipeComponent<RecipeFunction1D> FUNCTION_1D = IRecipeComponent.simple(
         CraftTweakerConstants.rl("processing/function_1d"), new TypeToken<RecipeFunction1D>() {}, RecipeComponentEqualityCheckers::notComparable
      );
      public static final IRecipeComponent<RecipeFunction2D> FUNCTION_2D = IRecipeComponent.simple(
         CraftTweakerConstants.rl("processing/function_2d"), new TypeToken<RecipeFunction2D>() {}, RecipeComponentEqualityCheckers::notComparable
      );
      public static final IRecipeComponent<Integer> TIME = IRecipeComponent.simple(
         CraftTweakerConstants.rl("processing/time"), new TypeToken<Integer>() {}, RecipeComponentEqualityCheckers::areNumbersEqual
      );

      private Processing() {
      }
   }
}

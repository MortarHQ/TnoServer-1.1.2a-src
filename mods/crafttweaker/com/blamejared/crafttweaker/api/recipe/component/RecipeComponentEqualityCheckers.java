package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import java.util.Objects;

public final class RecipeComponentEqualityCheckers {
   public static boolean areIngredientsEqual(IIngredient a, IIngredient b) {
      return Objects.equals(a, b) || a.contains(b) && b.contains(a);
   }

   public static boolean areIngredientsEqual(IIngredientWithAmount a, IIngredientWithAmount b) {
      return areIngredientsEqual(a.ingredient(), b.ingredient()) && areNumbersEqual(a.amount(), b.amount());
   }

   public static boolean areStacksEqual(IItemStack a, IItemStack b) {
      return a.matches(b);
   }

   public static boolean areStacksEqual(Percentaged<IItemStack> a, Percentaged<IItemStack> b) {
      return a.getData().matches(b.getData()) && a.getPercentage() >= b.getPercentage();
   }

   public static <T extends Number> boolean areNumbersEqual(T a, T b) {
      return a.doubleValue() == b.doubleValue();
   }

   public static <T> boolean notComparable(T a, T b) {
      throw new UnsupportedOperationException("Cannot compare " + a + " and " + b);
   }

   public static boolean areFluidIngredientsEqual(CTFluidIngredient a, CTFluidIngredient b) {
      return Objects.equals(a, b) || a.contains(b) && b.contains(a);
   }

   public static boolean areFluidStacksEqual(IFluidStack a, IFluidStack b) {
      return a.matches(b);
   }
}

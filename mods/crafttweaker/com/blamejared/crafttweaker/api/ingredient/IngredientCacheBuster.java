package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.platform.Services;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientCacheBuster {
   private static boolean claimed;
   private static final List<Ingredient> ingredients = new ArrayList<>();

   public static void claim() {
      claimed = true;
   }

   public static void release() {
      claimed = false;
      Services.PLATFORM.invalidateIngredients(ingredients);
   }

   public static boolean claimed() {
      return claimed;
   }

   public static void store(Ingredient ingredient) {
      if (ingredient == null) {
         throw new IllegalStateException("Cannot store a null ingredient!");
      }

      ingredients.add(ingredient);
   }
}

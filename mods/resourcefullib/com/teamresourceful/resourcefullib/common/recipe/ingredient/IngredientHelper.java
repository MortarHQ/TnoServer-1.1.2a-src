package com.teamresourceful.resourcefullib.common.recipe.ingredient;

import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import com.teamresourceful.resourcefullib.common.recipe.ingredient.neoforge.IngredientHelperImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.item.crafting.Ingredient;

public final class IngredientHelper {
   private IngredientHelper() throws UtilityClassException {
      throw new UtilityClassException();
   }

   @ExpectPlatform
   @Transformed
   public static <T extends CodecIngredient<T>> Ingredient getIngredient(T ingredient) {
      return IngredientHelperImpl.getIngredient(ingredient);
   }

   @ExpectPlatform
   @Transformed
   public static <C extends CodecIngredient<C>, T extends CodecIngredientSerializer<C>> void registerIngredient(T serializer) {
      IngredientHelperImpl.registerIngredient(serializer);
   }
}

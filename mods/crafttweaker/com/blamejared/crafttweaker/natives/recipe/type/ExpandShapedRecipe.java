package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.stream.IntStream;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/type/ShapedRecipe")
@NativeTypeRegistration(value = ShapedRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.ShapedRecipe")
public class ExpandShapedRecipe {
   @ZenCodeType.Method
   public static IIngredient[][] getIngredientArray(ShapedRecipe internal) {
      return IntStream.range(0, internal.getHeight())
         .mapToObj(
            y -> IntStream.range(0, internal.getWidth())
               .mapToObj(x -> (Ingredient)internal.getIngredients().get(y * internal.getWidth() + x))
               .map(IIngredient::fromIngredient)
               .toArray(IIngredient[]::new)
         )
         .toArray(IIngredient[][]::new);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("width")
   public static int getWidth(ShapedRecipe internal) {
      return internal.getWidth();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("height")
   public static int getHeight(ShapedRecipe internal) {
      return internal.getHeight();
   }
}

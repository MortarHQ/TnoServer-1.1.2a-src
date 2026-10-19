package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.Lists;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/type/ShapelessRecipe")
@NativeTypeRegistration(value = ShapelessRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.ShapelessRecipe")
public class ExpandShapelessRecipe {
   @ZenCodeType.Method
   public static IIngredient[] getIngredientArray(ShapelessRecipe internal) {
      return Lists.transform(internal.getIngredients(), IIngredient::fromIngredient).toArray(IIngredient[]::new);
   }
}

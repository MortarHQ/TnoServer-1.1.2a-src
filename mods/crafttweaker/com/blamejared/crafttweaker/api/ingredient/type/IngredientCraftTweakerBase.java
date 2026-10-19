package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;
import net.minecraft.world.item.crafting.Ingredient.Value;

public interface IngredientCraftTweakerBase extends Predicate<ItemStack> {
   IIngredient getCrTIngredient();

   default boolean test(@Nullable ItemStack stack) {
      return stack != null && this.getCrTIngredient().matches(IItemStack.of(stack));
   }

   default boolean isSimple() {
      return false;
   }

   static Stream<Value> getValues(IItemStack[] items) {
      return Arrays.stream(items).map(IIngredient::getItems).flatMap(Arrays::stream).map(IItemStack::getInternal).map(ItemValue::new);
   }
}

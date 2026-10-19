package com.teamresourceful.resourcefullib.common.recipe.ingredient;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface CodecIngredient<T extends CodecIngredient<T>> extends Predicate<ItemStack> {
   boolean test(@Nullable ItemStack var1);

   List<ItemStack> getStacks();

   default boolean isComplex() {
      return true;
   }

   CodecIngredientSerializer<T> serializer();
}

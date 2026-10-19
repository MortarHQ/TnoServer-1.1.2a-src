package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;
import net.minecraft.world.item.crafting.Ingredient.Value;
import org.jetbrains.annotations.Nullable;

public interface CraftTweakerVanillaIngredient {
   boolean test(@Nullable ItemStack var1);

   List<ItemStack> getMatchingStacks();

   boolean requiresTesting();

   boolean isEmpty();

   <T extends CraftTweakerVanillaIngredient> CraftTweakerVanillaIngredientSerializer<T> serializer();

   default Stream<Value> values() {
      return this.getMatchingStacks().stream().map(ItemValue::new);
   }

   default boolean singleton() {
      return false;
   }
}

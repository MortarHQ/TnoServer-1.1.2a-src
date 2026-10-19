package net.darkhax.bookshelf.common.api.data.ingredient;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IngredientLogic<T extends IngredientLogic<T>> {
   boolean test(ItemStack var1);

   default List<ItemStack> getAllMatchingStacks() {
      List<ItemStack> matching = new ArrayList<>();

      for (Item item : BuiltInRegistries.ITEM) {
         ItemStack stack = item.getDefaultInstance();
         if (this.test(stack)) {
            matching.add(stack);
         }
      }

      return matching;
   }

   default boolean requiresTesting() {
      return true;
   }
}

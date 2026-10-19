package dev.ftb.mods.ftbquests.api;

import java.util.function.Predicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ItemFilterAdapter {
   ItemFilterAdapter.Matcher NO_MATCH = stack -> false;

   String getName();

   boolean isFilterStack(ItemStack var1);

   boolean doesItemMatch(ItemStack var1, ItemStack var2, Provider var3);

   ItemFilterAdapter.Matcher getMatcher(ItemStack var1, Provider var2);

   default boolean hasItemTagFilter() {
      return true;
   }

   ItemStack makeTagFilterStack(TagKey<Item> var1);

   interface Matcher extends Predicate<ItemStack> {
   }
}

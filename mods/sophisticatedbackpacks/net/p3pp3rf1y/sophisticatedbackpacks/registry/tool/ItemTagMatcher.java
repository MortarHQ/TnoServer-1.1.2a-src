package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import java.util.function.Predicate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

class ItemTagMatcher implements Predicate<ItemStack> {
   private final TagKey<Item> itemTag;

   public ItemTagMatcher(TagKey<Item> itemTag) {
      this.itemTag = itemTag;
   }

   public boolean test(ItemStack stack) {
      return stack.is(this.itemTag);
   }
}

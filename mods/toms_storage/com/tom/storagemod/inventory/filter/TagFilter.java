package com.tom.storagemod.inventory.filter;

import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.util.AbstractTagFilter;
import net.minecraft.world.item.ItemStack;

public class TagFilter extends AbstractTagFilter implements ItemFilter {
   public TagFilter(ItemStack stack) {
      super(stack);
   }

   @Override
   public boolean configMatch(ItemStack stack) {
      return stack == this.stack;
   }

   @Override
   public boolean test(StoredItemStack stack) {
      return this.test0(stack) == this.allowList;
   }

   private boolean test0(StoredItemStack stack) {
      for (int i = 0; i < this.tags.size(); i++) {
         if (stack.getStack().is(this.tags.get(i))) {
            return true;
         }
      }

      return false;
   }
}

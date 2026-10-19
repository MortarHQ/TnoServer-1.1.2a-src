package com.tom.storagemod.inventory.filter;

import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.util.AbstractSimpleItemFilter;
import net.minecraft.world.item.ItemStack;

public class SimpleItemFilter extends AbstractSimpleItemFilter implements ItemFilter {
   public SimpleItemFilter(ItemStack stack) {
      super(stack);
   }

   @Override
   public boolean test(StoredItemStack stack) {
      return this.matchNBT ? this.testNbt(stack) == this.allowList : this.test0(stack) == this.allowList;
   }

   private boolean test0(StoredItemStack stack) {
      for (int i = 0; i < this.filter.getContainerSize(); i++) {
         ItemStack is = this.filter.getItem(i);
         if (!is.isEmpty() && ItemStack.isSameItem(stack.getStack(), is)) {
            return true;
         }
      }

      return false;
   }

   private boolean testNbt(StoredItemStack stack) {
      for (int i = 0; i < this.filter.getContainerSize(); i++) {
         ItemStack is = this.filter.getItem(i);
         if (!is.isEmpty() && ItemStack.isSameItemSameComponents(stack.getStack(), is)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean configMatch(ItemStack stack) {
      return stack == this.stack;
   }
}

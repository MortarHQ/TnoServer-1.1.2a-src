package dev.ftb.mods.ftblibrary.util.neoforge;

import net.minecraft.world.item.ItemStack;

public final class ItemKey {
   public final ItemStack stack;

   public ItemKey(ItemStack s) {
      this.stack = s;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o != null && this.getClass() == o.getClass() ? ItemStack.isSameItemSameComponents(this.stack, ((ItemKey)o).stack) : false;
      }
   }

   @Override
   public int hashCode() {
      return ItemStack.hashItemAndComponents(this.stack);
   }
}

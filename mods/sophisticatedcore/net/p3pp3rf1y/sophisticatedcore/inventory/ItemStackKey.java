package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.item.ItemStack;

public final class ItemStackKey {
   private final ItemStack stack;
   private static final Map<ItemStack, ItemStackKey> CACHE = new ConcurrentHashMap<>();
   private boolean hashInitialized = false;
   private int hash;

   public static ItemStackKey of(ItemStack stack) {
      return CACHE.computeIfAbsent(stack, ItemStackKey::new);
   }

   private ItemStackKey(ItemStack stack) {
      this.stack = stack.copyWithCount(1);
   }

   public static void clearCache() {
      CACHE.clear();
   }

   public ItemStack getStack() {
      return this.stack;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemStackKey that = (ItemStackKey)o;
         return ItemStack.isSameItemSameComponents(this.stack, that.stack);
      } else {
         return false;
      }
   }

   public boolean hashCodeNotEquals(ItemStack otherStack) {
      return this.hashCode() != ItemStack.hashItemAndComponents(otherStack);
   }

   @Override
   public int hashCode() {
      if (!this.hashInitialized) {
         this.hashInitialized = true;
         this.hash = ItemStack.hashItemAndComponents(this.stack);
      }

      return this.hash;
   }

   public boolean matches(ItemStack stack) {
      return this.hashCode() == ItemStack.hashItemAndComponents(stack);
   }

   public ItemStack stack() {
      return this.stack;
   }

   @Override
   public String toString() {
      return "ItemStackKey[stack=" + this.stack + "]";
   }
}

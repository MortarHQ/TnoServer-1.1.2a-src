package com.tom.storagemod.inventory;

import net.minecraft.world.item.ItemStack;

public class StoredItemStack {
   protected ItemStack stack;
   protected long count;
   private int hash;
   private boolean hashZero;

   public StoredItemStack(ItemStack stack, long count) {
      this.stack = stack;
      this.count = count;
   }

   public StoredItemStack(ItemStack stack) {
      this.stack = stack.copy();
      this.stack.setCount(1);
      this.count = stack.getCount();
   }

   public StoredItemStack(ItemStack stack, long count, int hash) {
      this.stack = stack;
      this.count = count;
      this.hash = hash;
      if (hash == 0) {
         this.hashZero = true;
      }
   }

   public StoredItemStack(StoredItemStack st) {
      this.stack = st.stack;
      this.count = st.count;
      this.hash = st.hash;
      this.hashZero = st.hashZero;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public long getQuantity() {
      return this.count;
   }

   public ItemStack getActualStack() {
      ItemStack s = this.stack.copy();
      s.setCount((int)this.count);
      return s;
   }

   @Override
   public int hashCode() {
      if (this.hash == 0 && !this.hashZero) {
         int prime = 31;
         int result = 1;
         result = 31 * result + (this.stack == null ? 0 : this.stack.getItem().hashCode());
         result = 31 * result + (this.stack == null ? 0 : this.stack.getComponentsPatch().hashCode());
         this.hash = result;
         if (this.hash == 0) {
            this.hashZero = true;
         }

         return result;
      } else {
         return this.hash;
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null) {
         return false;
      }

      if (this.getClass() != obj.getClass()) {
         return false;
      }

      StoredItemStack other = (StoredItemStack)obj;
      if (this.stack == null) {
         if (other.stack != null) {
            return false;
         }
      } else if (!ItemStack.isSameItemSameComponents(this.stack, other.stack)) {
         return false;
      }

      return true;
   }

   public boolean equals(StoredItemStack other) {
      if (this == other) {
         return true;
      }

      if (other == null) {
         return false;
      }

      if (this.count != other.count) {
         return false;
      }

      if (this.stack == null) {
         if (other.stack != null) {
            return false;
         }
      } else if (!ItemStack.isSameItemSameComponents(this.stack, other.stack)) {
         return false;
      }

      return true;
   }

   public boolean equalItem(StoredItemStack other) {
      if (this == other) {
         return true;
      }

      if (other == null) {
         return false;
      }

      if (this.stack == null) {
         if (other.stack != null) {
            return false;
         }
      } else if (!ItemStack.isSameItemSameComponents(this.stack, other.stack)) {
         return false;
      }

      return true;
   }

   public void grow(long c) {
      this.count += c;
   }

   public void setCount(long count) {
      this.count = count;
   }

   public int getMaxStackSize() {
      return this.stack.getMaxStackSize();
   }
}

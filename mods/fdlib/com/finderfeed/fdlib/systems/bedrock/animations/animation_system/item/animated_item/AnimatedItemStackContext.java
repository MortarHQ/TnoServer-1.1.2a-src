package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item;

import java.util.Objects;
import net.minecraft.world.item.ItemStack;

public abstract class AnimatedItemStackContext {
   private ItemStack itemStack;

   public AnimatedItemStackContext(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AnimatedItemStackContext that = (AnimatedItemStackContext)o;
         return this.itemStack.getItem() == that.itemStack.getItem();
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.itemStack.getItem());
   }
}

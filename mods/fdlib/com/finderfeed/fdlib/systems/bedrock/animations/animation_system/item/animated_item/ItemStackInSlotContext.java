package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item;

import java.util.Objects;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ItemStackInSlotContext extends AnimatedItemStackContext {
   private Slot slot;

   public ItemStackInSlotContext(Slot slot, ItemStack itemStack) {
      super(itemStack);
      this.slot = slot;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o == null || this.getClass() != o.getClass()) {
         return false;
      }

      if (!super.equals(o)) {
         return false;
      }

      ItemStackInSlotContext that = (ItemStackInSlotContext)o;
      return Objects.equals(this.slot, that.slot);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.slot, this.getItemStack().getItem());
   }
}

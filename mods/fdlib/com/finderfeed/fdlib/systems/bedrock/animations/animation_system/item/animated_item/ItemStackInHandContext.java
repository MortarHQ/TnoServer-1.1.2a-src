package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item;

import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemStackInHandContext extends AnimatedItemStackContext {
   private LivingEntity owner;
   private InteractionHand hand;

   public ItemStackInHandContext(LivingEntity owner, InteractionHand hand, ItemStack itemStack) {
      super(itemStack);
      this.owner = owner;
      this.hand = hand;
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

      ItemStackInHandContext that = (ItemStackInHandContext)o;
      return Objects.equals(this.owner, that.owner) && this.hand == that.hand;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.owner, this.hand, this.getItemStack().getItem());
   }
}

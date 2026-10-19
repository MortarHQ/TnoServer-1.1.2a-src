package it.hurts.sskirillss.relics.system.casts.slots.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotReference {
   public ItemStack gatherStack(Player player) {
      return ItemStack.EMPTY;
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.putString("class", this.getClass().getName());
      return tag;
   }

   public SlotReference deserializeNBT(CompoundTag tag) {
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof SlotReference other) ? false : other.canEqual(this);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof SlotReference;
   }

   @Override
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Override
   public String toString() {
      return "SlotReference()";
   }
}

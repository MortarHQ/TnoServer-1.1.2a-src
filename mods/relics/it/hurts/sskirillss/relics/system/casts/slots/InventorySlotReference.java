package it.hurts.sskirillss.relics.system.casts.slots;

import it.hurts.sskirillss.relics.system.casts.slots.base.SlotReference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventorySlotReference extends SlotReference {
   private int index;

   @Override
   public ItemStack gatherStack(Player player) {
      return player.getInventory().getItem(this.getIndex());
   }

   @Override
   public CompoundTag serializeNBT() {
      CompoundTag tag = super.serializeNBT();
      tag.putInt("index", this.index);
      return tag;
   }

   @Override
   public SlotReference deserializeNBT(CompoundTag tag) {
      this.index = tag.getInt("index");
      return this;
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InventorySlotReference other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.getIndex() == other.getIndex();
      }
   }

   @Override
   protected boolean canEqual(Object other) {
      return other instanceof InventorySlotReference;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + this.getIndex();
   }

   @Override
   public String toString() {
      return "InventorySlotReference(index=" + this.getIndex() + ")";
   }

   public InventorySlotReference() {
   }

   public InventorySlotReference(int index) {
      this.index = index;
   }
}

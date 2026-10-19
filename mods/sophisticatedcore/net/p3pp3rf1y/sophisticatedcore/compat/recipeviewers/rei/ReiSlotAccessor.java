package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ReiSlotAccessor implements SlotAccessor {
   protected Slot slot;

   public static SlotAccessor fromSlot(Slot slot) {
      return new ReiSlotAccessor(slot);
   }

   public ReiSlotAccessor(Slot slot) {
      this.slot = slot;
   }

   public ItemStack getItemStack() {
      return this.slot.getItem();
   }

   public void setItemStack(ItemStack stack) {
      this.slot.set(stack);
   }

   public ItemStack takeStack(int amount) {
      return this.slot.remove(amount);
   }

   public int getIndex() {
      return this.slot.index;
   }
}

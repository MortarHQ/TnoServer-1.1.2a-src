package io.github.Memoires.mysticism.menu.slot;

import io.github.Memoires.mysticism.menu.container.BookStorageContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BookSlot extends Slot {
   public BookSlot(Container pContainer, int pSlot, int xPosition, int yPosition) {
      super(pContainer, pSlot, xPosition, yPosition);
   }

   public int getMaxStackSize(ItemStack pStack) {
      return !pStack.isStackable() ? super.getMaxStackSize(pStack) : this.getMaxStackSize();
   }

   public ItemStack getItem() {
      return this.container.getItem(this.getContainerSlot());
   }

   public void set(ItemStack pStack) {
      this.container.setItem(this.getContainerSlot(), pStack);
      this.setChanged();
      this.container.setChanged();
   }

   public ItemStack remove(int pAmount) {
      ItemStack remove = this.container.removeItem(this.getContainerSlot(), pAmount);
      this.container.setChanged();
      return remove;
   }

   public boolean mayPlace(ItemStack stack) {
      return BookStorageContainer.isBook(stack);
   }
}

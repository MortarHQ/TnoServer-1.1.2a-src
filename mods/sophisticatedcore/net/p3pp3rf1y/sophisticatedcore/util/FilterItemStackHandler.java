package net.p3pp3rf1y.sophisticatedcore.util;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class FilterItemStackHandler extends ItemStackHandler {
   private boolean onlyEmptyFilters = true;

   public FilterItemStackHandler(int size) {
      super(size);
   }

   public int getSlotLimit(int slot) {
      return 1;
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return ItemStack.EMPTY;
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return ItemStack.EMPTY;
   }

   protected void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      this.updateEmptyFilters();
   }

   protected void onLoad() {
      super.onLoad();
      this.updateEmptyFilters();
   }

   private void updateEmptyFilters() {
      this.onlyEmptyFilters = InventoryHelper.iterate(this, (s, filter) -> filter.isEmpty(), () -> true, result -> !result);
   }

   public boolean hasOnlyEmptyFilters() {
      return this.onlyEmptyFilters;
   }
}

package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class CachedFailedInsertInventoryHandler<T extends IItemHandlerModifiable & IInsertBlockOverride> implements IItemHandlerModifiable, IInsertBlockOverride {
   private final Supplier<T> wrappedHandlerGetter;
   private final LongSupplier timeSupplier;
   private long currentCacheTime = 0L;
   private final Set<ItemStack> failedInsertStacks = new HashSet<>();

   public CachedFailedInsertInventoryHandler(Supplier<T> wrappedHandlerGetter, LongSupplier timeSupplier) {
      this.wrappedHandlerGetter = wrappedHandlerGetter;
      this.timeSupplier = timeSupplier;
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      this.wrappedHandlerGetter.get().setStackInSlot(slot, stack);
   }

   public int getSlots() {
      return this.wrappedHandlerGetter.get().getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      return this.wrappedHandlerGetter.get().getStackInSlot(slot);
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (simulate) {
         return this.wrappedHandlerGetter.get().insertItem(slot, stack, true);
      }

      if (this.currentCacheTime != this.timeSupplier.getAsLong()) {
         this.failedInsertStacks.clear();
         this.currentCacheTime = this.timeSupplier.getAsLong();
      }

      if (this.failedInsertStacks.contains(stack)) {
         return stack;
      }

      ItemStack result = this.wrappedHandlerGetter.get().insertItem(slot, stack, false);
      if (result == stack) {
         this.failedInsertStacks.add(stack);
      }

      return result;
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.wrappedHandlerGetter.get().extractItem(slot, amount, simulate);
   }

   public int getSlotLimit(int slot) {
      return this.wrappedHandlerGetter.get().getSlotLimit(slot);
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.wrappedHandlerGetter.get().isItemValid(slot, stack);
   }

   @Override
   public boolean isInsertBlocked() {
      return this.wrappedHandlerGetter.get().isInsertBlocked();
   }
}

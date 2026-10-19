package net.p3pp3rf1y.sophisticatedcore.common.gui;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.ISlotChangeResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;

public class StorageInventorySlot extends SlotSuppliedHandler {
   private final boolean isClientSide;
   private final IStorageWrapper storageWrapper;
   private final int slotIndex;
   private final Player player;

   public StorageInventorySlot(boolean isClientSide, IStorageWrapper storageWrapper, int slotIndex, Player player) {
      super(storageWrapper::getInventoryHandler, slotIndex, 0, 0);
      this.isClientSide = isClientSide;
      this.storageWrapper = storageWrapper;
      this.slotIndex = slotIndex;
      this.player = player;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return this.storageWrapper.getInventoryHandler().isItemValid(this.slotIndex, stack, this.player);
   }

   @Override
   public void setChanged() {
      super.setChanged();
      this.storageWrapper.getInventoryHandler().onContentsChanged(this.slotIndex);
      this.processSlotChangeResponse(this.slotIndex, this.storageWrapper.getInventoryHandler(), this.storageWrapper);
   }

   private void processSlotChangeResponse(int slot, IItemHandler handler, IStorageWrapper storageWrapper) {
      if (!this.isClientSide) {
         storageWrapper.getUpgradeHandler()
            .getWrappersThatImplementFromMainStorage(ISlotChangeResponseUpgrade.class)
            .forEach(u -> u.onSlotChange(handler, slot));
      }
   }

   public int getMaxStackSize(ItemStack stack) {
      return this.storageWrapper.getInventoryHandler().getStackLimit(this.slotIndex, stack);
   }

   public ItemStack safeInsert(ItemStack stack, int maxCount) {
      if (!stack.isEmpty() && this.mayPlace(stack)) {
         ItemStack itemstack = this.getItem();
         int i = Math.min(Math.min(maxCount, stack.getCount()), this.getMaxStackSize(stack) - itemstack.getCount());
         if (itemstack.isEmpty()) {
            this.set(stack.split(i));
         } else if (ItemStack.isSameItemSameComponents(itemstack, stack)) {
            stack.shrink(i);
            ItemStack copy = itemstack.copy();
            copy.grow(i);
            this.set(copy);
         }

         return stack;
      } else {
         return stack;
      }
   }

   public boolean isInfinite() {
      return this.storageWrapper.getInventoryHandler().isInfinite(this.slotIndex);
   }
}

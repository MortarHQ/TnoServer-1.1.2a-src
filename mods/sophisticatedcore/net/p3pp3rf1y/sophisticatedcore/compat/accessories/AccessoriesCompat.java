package net.p3pp3rf1y.sophisticatedcore.compat.accessories;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.slot.SlotType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;
import net.p3pp3rf1y.sophisticatedcore.compat.ICompat;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class AccessoriesCompat implements ICompat {
   @Override
   public void setup() {
      InventoryHelper.registerPlayerInventoryProvider(
         player -> AccessoriesCapability.getOptionally(player)
            .map(cap -> new AccessoriesCompat.AccessoriesHandler(player, cap))
            .orElse(EmptyItemHandler.INSTANCE)
      );
      InventoryHelper.registerEquipmentInventoryProvider(
         player -> AccessoriesCapability.getOptionally(player)
            .map(cap -> new AccessoriesCompat.AccessoriesHandler(player, cap))
            .orElse(EmptyItemHandler.INSTANCE)
      );
   }

   private static class AccessoriesHandler implements IItemHandlerModifiable {
      private final AccessoriesCapability cap;
      private final Player player;
      private Map<SlotType, Integer> identifierBaseIndexes = new LinkedHashMap<>();
      private final int totalSize;

      public AccessoriesHandler(Player player, AccessoriesCapability cap) {
         this.cap = cap;
         this.player = player;
         AtomicInteger totalSlots = new AtomicInteger(0);
         cap.getContainers().forEach((identifier, container) -> {
            this.identifierBaseIndexes.put(container.slotType(), totalSlots.get());
            totalSlots.addAndGet(container.getSize());
         });
         this.totalSize = totalSlots.get();
      }

      public int getSlots() {
         return this.totalSize;
      }

      public ItemStack getStackInSlot(int i) {
         return this.totalSize <= i ? ItemStack.EMPTY : this.getContainer(i).getAccessories().getItem(this.getLocalIndex(i));
      }

      private AccessoriesContainer getContainer(int slot) {
         for (Entry<SlotType, Integer> entry : this.identifierBaseIndexes.entrySet()) {
            SlotType slotType = entry.getKey();
            int baseIndex = entry.getValue();
            AccessoriesContainer container = this.cap.getContainer(slotType);
            if (slot < baseIndex + container.getSize()) {
               return container;
            }
         }

         throw new IndexOutOfBoundsException("Slot " + slot + " is out of bounds for accessories inventory of size " + this.totalSize);
      }

      private int getLocalIndex(int slot) {
         for (Entry<SlotType, Integer> entry : this.identifierBaseIndexes.entrySet()) {
            SlotType slotType = entry.getKey();
            int baseIndex = entry.getValue();
            AccessoriesContainer container = this.cap.getContainer(slotType);
            if (slot < baseIndex + container.getSize()) {
               return slot - baseIndex;
            }
         }

         throw new IndexOutOfBoundsException("Slot " + slot + " is out of bounds for accessories inventory of size " + this.totalSize);
      }

      private SlotType getSlotType(int slot) {
         for (Entry<SlotType, Integer> entry : this.identifierBaseIndexes.entrySet()) {
            SlotType slotType = entry.getKey();
            int baseIndex = entry.getValue();
            AccessoriesContainer container = this.cap.getContainer(slotType);
            if (slot < baseIndex + container.getSize()) {
               return slotType;
            }
         }

         throw new IndexOutOfBoundsException("Slot " + slot + " is out of bounds for accessories inventory of size " + this.totalSize);
      }

      public int getSlotLimit(int i) {
         return this.totalSize <= i ? 0 : this.getContainer(i).getAccessories().getMaxStackSize();
      }

      public boolean isItemValid(int i, ItemStack itemStack) {
         return this.totalSize <= i ? false : this.getContainer(i).getAccessories().canPlaceItem(this.getLocalIndex(i), itemStack);
      }

      public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
         if (this.totalSize <= slot) {
            return itemStack;
         }

         int localIndex = this.getLocalIndex(slot);
         SlotReference slotReference = SlotReference.of(this.player, this.getSlotType(slot).name(), localIndex);
         ItemStack currentStack = slotReference.getStack();
         if (currentStack != null && !currentStack.isEmpty() && !ItemStack.isSameItemSameComponents(itemStack, currentStack)) {
            return itemStack;
         }

         int currentCount = currentStack != null ? currentStack.getCount() : 0;
         int countToInsert = Math.min(itemStack.getCount(), this.getContainer(slot).getAccessories().getMaxStackSize(itemStack) - currentCount);
         if (countToInsert > 0 && this.getContainer(slot).getAccessories().canPlaceItem(localIndex, itemStack)) {
            if (!simulate) {
               slotReference.setStack(itemStack.copyWithCount(countToInsert + currentCount));
            }

            return itemStack.getCount() == countToInsert ? ItemStack.EMPTY : itemStack.copyWithCount(itemStack.getCount() - countToInsert);
         } else {
            return itemStack;
         }
      }

      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         if (this.totalSize <= slot) {
            return ItemStack.EMPTY;
         }

         int localIndex = this.getLocalIndex(slot);
         SlotReference slotReference = SlotReference.of(this.player, this.getSlotType(slot).name(), localIndex);
         ItemStack currentStack = slotReference.getStack();
         if (currentStack != null && !currentStack.isEmpty()) {
            int countToExtract = Math.min(amount, currentStack.getCount());
            ItemStack extractedStack = currentStack.copyWithCount(countToExtract);
            if (!simulate) {
               if (currentStack.getCount() == countToExtract) {
                  slotReference.setStack(ItemStack.EMPTY);
               } else {
                  slotReference.setStack(currentStack.copyWithCount(currentStack.getCount() - countToExtract));
               }
            }

            return extractedStack;
         } else {
            return ItemStack.EMPTY;
         }
      }

      public void setStackInSlot(int i, ItemStack itemStack) {
         if (this.totalSize > i) {
            SlotReference slotReference = SlotReference.of(this.player, this.getSlotType(i).name(), this.getLocalIndex(i));
            slotReference.setStack(itemStack);
         }
      }
   }
}

package fuzs.puzzleslib.api.container.v1;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.NonInteractiveResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ContainerMenuHelper {
   private ContainerMenuHelper() {
   }

   public static void setSelectedSlotLocked(AbstractContainerMenu containerMenu) {
      for (int i = 0; i < containerMenu.slots.size(); i++) {
         Slot slot = (Slot)containerMenu.slots.get(i);
         if (slot.container instanceof Inventory inventory && inventory.selected == slot.getContainerSlot()) {
            NonInteractiveResultSlot newSlot = new NonInteractiveResultSlot(slot.container, slot.getContainerSlot(), slot.x, slot.y) {
               public boolean isFake() {
                  return false;
               }
            };
            newSlot.index = slot.index;
            containerMenu.slots.set(i, newSlot);
            break;
         }
      }
   }

   public static void addInventorySlots(AbstractContainerMenu containerMenu, Inventory inventory, int offsetY) {
      addInventorySlots(containerMenu, inventory, 8, offsetY);
   }

   public static void addInventorySlots(AbstractContainerMenu containerMenu, Inventory inventory, int offsetX, int offsetY) {
      int slotSize = 18;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            containerMenu.addSlot(new Slot(inventory, j + i * 9 + 9, offsetX + j * 18, offsetY));
         }

         offsetY += 18;
      }

      offsetY += 4;

      for (int i = 0; i < 9; i++) {
         containerMenu.addSlot(new Slot(inventory, i, offsetX + i * 18, offsetY));
      }
   }

   public static SimpleContainer createListBackedContainer(NonNullList<ItemStack> items, @Nullable Container listener) {
      return createListBackedContainer(items, listener != null ? $ -> listener.setChanged() : null);
   }

   public static SimpleContainer createListBackedContainer(NonNullList<ItemStack> items, @Nullable ContainerListener listener) {
      SimpleContainer simpleContainer = new SimpleContainer(new ItemStack[0]);
      simpleContainer.size = items.size();
      simpleContainer.items = items;
      if (listener != null) {
         simpleContainer.addListener(listener);
      }

      return simpleContainer;
   }

   public static void copyItemsIntoContainer(NonNullList<ItemStack> from, Container to) {
      for (int i = 0; i < from.size(); i++) {
         if (i < to.getContainerSize()) {
            to.setItem(i, (ItemStack)from.get(i));
         }
      }
   }

   public static void copyItemsIntoList(NonNullList<ItemStack> from, NonNullList<ItemStack> to) {
      for (int i = 0; i < from.size(); i++) {
         if (i < to.size()) {
            to.set(i, (ItemStack)from.get(i));
         }
      }
   }
}

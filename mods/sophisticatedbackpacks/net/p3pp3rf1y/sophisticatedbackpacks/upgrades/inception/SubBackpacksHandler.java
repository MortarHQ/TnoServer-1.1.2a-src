package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;

public class SubBackpacksHandler {
   private final Map<Integer, IStorageWrapper> subBackpacks = new LinkedHashMap<>();
   private final InventoryHandler inventoryHandler;
   private final Set<Consumer<Collection<IStorageWrapper>>> refreshListeners = new HashSet<>();
   private final Set<Consumer<Collection<IStorageWrapper>>> beforeRefreshListeners = new HashSet<>();

   public SubBackpacksHandler(InventoryHandler inventoryHandler) {
      this.inventoryHandler = inventoryHandler;
      this.inventoryHandler.addListener(this::onContentsChanged);
      this.refreshSubBackpacks();
   }

   public void addRefreshListener(Consumer<Collection<IStorageWrapper>> listener) {
      this.refreshListeners.add(listener);
   }

   public Collection<IStorageWrapper> getSubBackpacks() {
      return this.subBackpacks.values();
   }

   private void onContentsChanged(int slot) {
      ItemStack stackInSlot = this.inventoryHandler.getStackInSlot(slot);
      boolean backpackIsInTheSlot = stackInSlot.getItem() instanceof BackpackItem;
      boolean backpackWasInTheSlot = this.subBackpacks.containsKey(slot);
      if (backpackWasInTheSlot || backpackIsInTheSlot) {
         if (backpackWasInTheSlot != backpackIsInTheSlot) {
            this.notifyAndRefreshSubbackpacks();
         } else if (BackpackWrapper.fromStack(stackInSlot) != this.subBackpacks.get(slot)) {
            this.notifyAndRefreshSubbackpacks();
         }
      }
   }

   private void notifyAndRefreshSubbackpacks() {
      this.notifyBeforeRefresh();
      this.refreshSubBackpacks();
      this.notifyAfterRefresh();
   }

   private void notifyAfterRefresh() {
      this.runRefreshListeners(this.refreshListeners);
   }

   private void runRefreshListeners(Set<Consumer<Collection<IStorageWrapper>>> refreshListeners) {
      for (Consumer<Collection<IStorageWrapper>> refreshListener : refreshListeners) {
         refreshListener.accept(this.subBackpacks.values());
      }
   }

   private void notifyBeforeRefresh() {
      this.runRefreshListeners(this.beforeRefreshListeners);
   }

   private void refreshSubBackpacks() {
      this.subBackpacks.clear();

      for (int slot = 0; slot < this.inventoryHandler.getSlots(); slot++) {
         ItemStack slotStack = this.inventoryHandler.getStackInSlot(slot);
         if (slotStack.getItem() instanceof BackpackItem) {
            this.subBackpacks.put(slot, BackpackWrapper.fromStack(slotStack));
         }
      }
   }

   public void addBeforeRefreshListener(Consumer<Collection<IStorageWrapper>> listener) {
      this.beforeRefreshListeners.add(listener);
   }
}

package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import java.util.List;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IInventoryWrapperUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;

public class InventoryModificationHandler {
   private final IStorageWrapper backpackWrapper;
   private ITrackedContentsItemHandler modifiedInventoryHandler;

   public InventoryModificationHandler(IStorageWrapper backpackWrapper) {
      this.backpackWrapper = backpackWrapper;
   }

   public ITrackedContentsItemHandler getModifiedInventoryHandler() {
      if (this.modifiedInventoryHandler == null) {
         this.initializeWrappedInventory(this.backpackWrapper.getInventoryHandler());
      }

      return this.modifiedInventoryHandler;
   }

   private void initializeWrappedInventory(ITrackedContentsItemHandler inventoryHandler) {
      List<IInventoryWrapperUpgrade> inventoryWrapperUpgrades = this.backpackWrapper
         .getUpgradeHandler()
         .getWrappersThatImplement(IInventoryWrapperUpgrade.class);
      ITrackedContentsItemHandler wrappedHandler = inventoryHandler;

      for (IInventoryWrapperUpgrade inventoryWrapperUpgrade : inventoryWrapperUpgrades) {
         wrappedHandler = inventoryWrapperUpgrade.wrapInventory(wrappedHandler);
      }

      this.modifiedInventoryHandler = wrappedHandler;
   }
}

package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.ArrayList;
import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.api.IIOFilterUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;

public class InventoryIOHandler {
   private final ITrackedContentsItemHandler filteredItemHandler;

   public InventoryIOHandler(IStorageWrapper storageWrapper) {
      List<FilterLogic> inputFilters = new ArrayList<>();
      List<FilterLogic> outputFilters = new ArrayList<>();
      this.addFilters(storageWrapper, inputFilters, outputFilters);
      ITrackedContentsItemHandler modifiedInventory = storageWrapper.getInventoryForUpgradeProcessing();
      if (inputFilters.isEmpty() && outputFilters.isEmpty()) {
         this.filteredItemHandler = modifiedInventory;
      } else {
         this.filteredItemHandler = new FilteredItemHandler.Modifiable(modifiedInventory, inputFilters, outputFilters);
      }
   }

   public ITrackedContentsItemHandler getFilteredItemHandler() {
      return this.filteredItemHandler;
   }

   private void addFilters(IStorageWrapper storageWrapper, List<FilterLogic> inputFilters, List<FilterLogic> outputFilters) {
      for (IIOFilterUpgrade wrapper : storageWrapper.getUpgradeHandler().getWrappersThatImplement(IIOFilterUpgrade.class)) {
         wrapper.getInputFilter().ifPresent(inputFilters::add);
         wrapper.getOutputFilter().ifPresent(outputFilters::add);
      }
   }
}

package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class InceptionEnergyStorage implements IEnergyStorage {
   @Nullable
   private final IEnergyStorage wrappedEnergyStorage;
   private final InventoryOrder inventoryOrder;
   private final SubBackpacksHandler subBackpacksHandler;
   private IEnergyStorage[] energyStorages;

   public InceptionEnergyStorage(@Nullable IEnergyStorage wrappedEnergyStorage, InventoryOrder inventoryOrder, SubBackpacksHandler subBackpacksHandler) {
      this.wrappedEnergyStorage = wrappedEnergyStorage;
      this.inventoryOrder = inventoryOrder;
      this.subBackpacksHandler = subBackpacksHandler;
      subBackpacksHandler.addRefreshListener(sbs -> this.refreshHandlers());
      this.refreshHandlers();
   }

   private void refreshHandlers() {
      List<IEnergyStorage> storages = new ArrayList<>();
      if (this.wrappedEnergyStorage != null && this.inventoryOrder == InventoryOrder.MAIN_FIRST) {
         storages.add(this.wrappedEnergyStorage);
      }

      this.subBackpacksHandler.getSubBackpacks().forEach(sbp -> sbp.getEnergyStorage().ifPresent(storages::add));
      if (this.wrappedEnergyStorage != null && this.inventoryOrder == InventoryOrder.INCEPTED_FIRST) {
         storages.add(this.wrappedEnergyStorage);
      }

      this.energyStorages = storages.toArray(new IEnergyStorage[0]);
   }

   public int receiveEnergy(int maxReceive, boolean simulate) {
      int totalReceived = 0;

      for (IEnergyStorage storage : this.energyStorages) {
         totalReceived += storage.receiveEnergy(maxReceive - totalReceived, simulate);
         if (totalReceived == maxReceive) {
            break;
         }
      }

      return totalReceived;
   }

   public int extractEnergy(int maxExtract, boolean simulate) {
      int totalExtracted = 0;

      for (IEnergyStorage storage : this.energyStorages) {
         totalExtracted += storage.extractEnergy(maxExtract - totalExtracted, simulate);
         if (totalExtracted == maxExtract) {
            break;
         }
      }

      return totalExtracted;
   }

   public int getEnergyStored() {
      int totalEnergyStored = 0;

      for (IEnergyStorage storage : this.energyStorages) {
         totalEnergyStored += storage.getEnergyStored();
      }

      return totalEnergyStored;
   }

   public int getMaxEnergyStored() {
      int totalMaxEnergy = 0;

      for (IEnergyStorage storage : this.energyStorages) {
         if (totalMaxEnergy > Integer.MAX_VALUE - storage.getMaxEnergyStored()) {
            return Integer.MAX_VALUE;
         }

         totalMaxEnergy += storage.getMaxEnergyStored();
      }

      return totalMaxEnergy;
   }

   public boolean canExtract() {
      return this.energyStorages.length > 0;
   }

   public boolean canReceive() {
      return this.energyStorages.length > 0;
   }
}

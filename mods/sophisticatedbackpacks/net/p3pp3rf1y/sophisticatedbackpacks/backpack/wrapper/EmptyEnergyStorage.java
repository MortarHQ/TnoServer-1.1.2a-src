package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class EmptyEnergyStorage implements IEnergyStorage {
   public static final EmptyEnergyStorage INSTANCE = new EmptyEnergyStorage();

   private EmptyEnergyStorage() {
   }

   public int receiveEnergy(int maxReceive, boolean simulate) {
      return 0;
   }

   public int extractEnergy(int maxExtract, boolean simulate) {
      return 0;
   }

   public int getEnergyStored() {
      return 0;
   }

   public int getMaxEnergyStored() {
      return 0;
   }

   public boolean canExtract() {
      return false;
   }

   public boolean canReceive() {
      return false;
   }
}

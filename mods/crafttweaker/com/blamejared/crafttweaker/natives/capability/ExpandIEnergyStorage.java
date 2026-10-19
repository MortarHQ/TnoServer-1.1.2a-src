package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/IEnergyStorage")
@NativeTypeRegistration(value = IEnergyStorage.class, zenCodeName = "crafttweaker.api.capability.IEnergyStorage")
public class ExpandIEnergyStorage {
   @ZenCodeType.Method
   public static int receiveEnergy(IEnergyStorage internal, int maxReceive, boolean simulate) {
      return internal.receiveEnergy(maxReceive, simulate);
   }

   @ZenCodeType.Method
   public static int extractEnergy(IEnergyStorage internal, int maxExtract, boolean simulate) {
      return internal.extractEnergy(maxExtract, simulate);
   }

   @ZenCodeType.Getter("energyStored")
   public static int getEnergyStored(IEnergyStorage internal) {
      return internal.getEnergyStored();
   }

   @ZenCodeType.Getter("maxEnergyStored")
   public static int getMaxEnergyStored(IEnergyStorage internal) {
      return internal.getMaxEnergyStored();
   }

   @ZenCodeType.Getter("canExtract")
   public static boolean canExtract(IEnergyStorage internal) {
      return internal.canExtract();
   }

   @ZenCodeType.Getter("canReceive")
   public static boolean canReceive(IEnergyStorage internal) {
      return internal.canReceive();
   }
}

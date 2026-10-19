package net.p3pp3rf1y.sophisticatedbackpacks.api;

import javax.annotation.Nullable;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyStorageUpgradeWrapper {
   @Nullable
   IEnergyStorage wrapStorage(@Nullable IEnergyStorage var1);
}

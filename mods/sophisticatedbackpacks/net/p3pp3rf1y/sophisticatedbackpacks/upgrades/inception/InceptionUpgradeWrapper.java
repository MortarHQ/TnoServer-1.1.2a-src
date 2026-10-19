package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IEnergyStorageUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IFluidHandlerWrapperUpgrade;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IInventoryWrapperUpgrade;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModDataComponents;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeAccessModifier;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapperAccessor;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class InceptionUpgradeWrapper
   extends UpgradeWrapperBase<InceptionUpgradeWrapper, InceptionUpgradeItem>
   implements IInventoryWrapperUpgrade,
   IUpgradeAccessModifier,
   IFluidHandlerWrapperUpgrade,
   IEnergyStorageUpgradeWrapper {
   private SubBackpacksHandler subBackpacksHandler = null;

   public InceptionUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(backpackWrapper, upgrade, upgradeSaveHandler);
   }

   public boolean hideSettingsTab() {
      return Boolean.FALSE.equals(Config.SERVER.inceptionUpgrade.upgradesUseInventoriesOfBackpacksInBackpack.get());
   }

   public InventoryOrder getInventoryOrder() {
      return (InventoryOrder)this.upgrade.getOrDefault(ModDataComponents.INVENTORY_ORDER, InventoryOrder.MAIN_FIRST);
   }

   public void setInventoryOrder(InventoryOrder inventoryOrder) {
      this.upgrade.set(ModDataComponents.INVENTORY_ORDER, inventoryOrder);
      this.save();
      this.storageWrapper.refreshInventoryForUpgradeProcessing();
   }

   @Override
   public ITrackedContentsItemHandler wrapInventory(ITrackedContentsItemHandler inventory) {
      if (Boolean.TRUE.equals(Config.SERVER.inceptionUpgrade.upgradesUseInventoriesOfBackpacksInBackpack.get())) {
         this.initSubBackpacksHandler();
         return new InceptionInventoryHandler(inventory, this.getInventoryOrder(), this.subBackpacksHandler);
      } else {
         return inventory;
      }
   }

   private void initSubBackpacksHandler() {
      this.subBackpacksHandler = new SubBackpacksHandler(this.storageWrapper.getInventoryHandler());
   }

   public IUpgradeWrapperAccessor wrapAccessor(IUpgradeWrapperAccessor upgradeWrapperAccessor) {
      if (Boolean.TRUE.equals(Config.SERVER.inceptionUpgrade.upgradesInContainedBackpacksAreFunctional.get())) {
         this.initSubBackpacksHandler();
         return new InceptionWrapperAccessor(this.storageWrapper, this.subBackpacksHandler);
      } else {
         return upgradeWrapperAccessor;
      }
   }

   @Nullable
   @Override
   public IStorageFluidHandler wrapHandler(@Nullable IStorageFluidHandler fluidHandler, ItemStack backpack) {
      if (Boolean.TRUE.equals(Config.SERVER.inceptionUpgrade.upgradesInContainedBackpacksAreFunctional.get())) {
         this.initSubBackpacksHandler();
         return new InceptionFluidHandler(fluidHandler, backpack, this.getInventoryOrder(), this.subBackpacksHandler);
      } else {
         return fluidHandler;
      }
   }

   @Nullable
   @Override
   public IEnergyStorage wrapStorage(@Nullable IEnergyStorage energyStorage) {
      if (Boolean.TRUE.equals(Config.SERVER.inceptionUpgrade.upgradesInContainedBackpacksAreFunctional.get())) {
         this.initSubBackpacksHandler();
         return new InceptionEnergyStorage(energyStorage, this.getInventoryOrder(), this.subBackpacksHandler);
      } else {
         return energyStorage;
      }
   }
}

package net.p3pp3rf1y.sophisticatedcore.upgrades.battery;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedBatteryUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IStackableContentsUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class BatteryUpgradeWrapper
   extends UpgradeWrapperBase<BatteryUpgradeWrapper, BatteryUpgradeItem>
   implements IRenderedBatteryUpgrade,
   IEnergyStorage,
   ITickableUpgrade,
   IStackableContentsUpgrade {
   public static final int INPUT_SLOT = 0;
   public static final int OUTPUT_SLOT = 1;
   private Consumer<IRenderedBatteryUpgrade.BatteryRenderInfo> updateTankRenderInfoCallback;
   private final BatteryUpgradeWrapper.BatteryComponentItemHandler inventory;
   private int energyStored;

   protected BatteryUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      if (upgrade.has(DataComponents.CONTAINER)) {
         upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, (ItemContainerContents)upgrade.get(DataComponents.CONTAINER));
      }

      upgrade.remove(DataComponents.CONTAINER);
      this.inventory = new BatteryUpgradeWrapper.BatteryComponentItemHandler(upgrade);
      this.energyStored = getEnergyStored(upgrade);
   }

   public static int getEnergyStored(ItemStack upgrade) {
      return (Integer)upgrade.getOrDefault(ModCoreDataComponents.ENERGY_STORED, 0);
   }

   public int receiveEnergy(int maxReceive, boolean simulate) {
      return this.innerReceiveEnergy(maxReceive, simulate);
   }

   private int innerReceiveEnergy(int maxReceive, boolean simulate) {
      int ret = Math.min(this.getMaxEnergyStored() - this.energyStored, Math.min(this.getMaxInOut(), maxReceive));
      if (!simulate) {
         this.energyStored += ret;
         this.serializeEnergyStored();
      }

      return ret;
   }

   private void serializeEnergyStored() {
      this.upgrade.set(ModCoreDataComponents.ENERGY_STORED, this.energyStored);
      this.save();
      this.forceUpdateBatteryRenderInfo();
   }

   public int extractEnergy(int maxExtract, boolean simulate) {
      return this.innerExtractEnergy(maxExtract, simulate);
   }

   private int innerExtractEnergy(int maxExtract, boolean simulate) {
      int ret = Math.min(this.energyStored, Math.min(this.getMaxInOut(), maxExtract));
      if (!simulate) {
         this.energyStored -= ret;
         this.serializeEnergyStored();
      }

      return ret;
   }

   public int getEnergyStored() {
      return this.energyStored;
   }

   public int getMaxEnergyStored() {
      return this.upgradeItem.getMaxEnergyStored(this.storageWrapper);
   }

   public boolean canExtract() {
      return true;
   }

   public boolean canReceive() {
      return true;
   }

   private int getMaxInOut() {
      double stackMultiplier = this.upgradeItem.getAdjustedStackMultiplier(this.storageWrapper);
      int baseInOut = (Integer)this.upgradeItem.getBatteryUpgradeConfig().maxInputOutput.get() * this.storageWrapper.getNumberOfSlotRows();
      return stackMultiplier > Integer.MAX_VALUE / baseInOut ? Integer.MAX_VALUE : (int)(baseInOut * stackMultiplier);
   }

   private boolean isValidEnergyItem(ItemStack stack, boolean isOutput) {
      IEnergyStorage energyStorage = (IEnergyStorage)stack.getCapability(EnergyStorage.ITEM);
      if (energyStorage == null) {
         return false;
      } else {
         return isOutput ? energyStorage.canReceive() : energyStorage.canExtract() && energyStorage.getEnergyStored() > 0;
      }
   }

   @Override
   public void setBatteryRenderInfoUpdateCallback(Consumer<IRenderedBatteryUpgrade.BatteryRenderInfo> updateTankRenderInfoCallback) {
      this.updateTankRenderInfoCallback = updateTankRenderInfoCallback;
   }

   @Override
   public void forceUpdateBatteryRenderInfo() {
      IRenderedBatteryUpgrade.BatteryRenderInfo batteryRenderInfo = new IRenderedBatteryUpgrade.BatteryRenderInfo(1.0F);
      batteryRenderInfo.setChargeRatio((float)this.energyStored / this.getMaxEnergyStored());
      this.updateTankRenderInfoCallback.accept(batteryRenderInfo);
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (this.energyStored < this.getMaxEnergyStored()) {
         ItemStack energyContainer = this.inventory.getStackInSlot(0);
         IEnergyStorage energyStorage = (IEnergyStorage)energyContainer.getCapability(EnergyStorage.ITEM);
         if (energyStorage != null) {
            this.receiveFromStorage(energyContainer, energyStorage);
         }
      }

      if (this.energyStored > 0) {
         ItemStack energyContainer = this.inventory.getStackInSlot(1);
         IEnergyStorage energyStorage = (IEnergyStorage)energyContainer.getCapability(EnergyStorage.ITEM);
         if (energyStorage != null) {
            this.extractToStorage(energyContainer, energyStorage);
         }
      }
   }

   private void extractToStorage(ItemStack energyContainer, IEnergyStorage energyStorage) {
      int toExtract = this.innerExtractEnergy(this.getMaxInOut(), true);
      if (toExtract > 0) {
         toExtract = energyStorage.receiveEnergy(toExtract, true);
         if (toExtract > 0) {
            energyStorage.receiveEnergy(toExtract, false);
            this.innerExtractEnergy(toExtract, false);
            this.inventory.setStackInSlotWithoutValidation(1, energyContainer);
         }
      }
   }

   private void receiveFromStorage(ItemStack energyContainer, IEnergyStorage energyStorage) {
      int toReceive = this.innerReceiveEnergy(this.getMaxInOut(), true);
      if (toReceive > 0) {
         toReceive = energyStorage.extractEnergy(toReceive, true);
         if (toReceive > 0) {
            energyStorage.extractEnergy(toReceive, false);
            this.innerReceiveEnergy(toReceive, false);
            this.inventory.setStackInSlotWithoutValidation(0, energyContainer);
         }
      }
   }

   public IItemHandler getInventory() {
      return this.inventory;
   }

   @Override
   public int getMinimumMultiplierRequired() {
      return (int)Math.ceil((float)this.energyStored / this.upgradeItem.getMaxEnergyBase(this.storageWrapper));
   }

   @Override
   public boolean canBeDisabled() {
      return false;
   }

   private class BatteryComponentItemHandler extends ComponentItemHandler {
      public BatteryComponentItemHandler(ItemStack upgrade) {
         super(upgrade, ModCoreDataComponents.LENIENT_CONTAINER.get(), 2);
      }

      protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
         super.onContentsChanged(slot, oldStack, newStack);
         BatteryUpgradeWrapper.this.save();
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         if (slot == 0) {
            return stack.isEmpty() || this.isValidInputItem(stack);
         } else {
            return slot != 1 ? false : stack.isEmpty() || this.isValidOutputItem(stack);
         }
      }

      private boolean isValidInputItem(ItemStack stack) {
         return BatteryUpgradeWrapper.this.isValidEnergyItem(stack, false);
      }

      private boolean isValidOutputItem(ItemStack stack) {
         return BatteryUpgradeWrapper.this.isValidEnergyItem(stack, true);
      }

      public int getSlotLimit(int slot) {
         return 1;
      }

      public void setStackInSlotWithoutValidation(int slot, ItemStack stack) {
         super.updateContents(this.getContents(), stack, slot);
      }
   }
}

package net.p3pp3rf1y.sophisticatedcore.upgrades.battery;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeSlotChangeResult;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;

public class BatteryUpgradeItem extends UpgradeItemBase<BatteryUpgradeWrapper> {
   public static final UpgradeType<BatteryUpgradeWrapper> TYPE = new UpgradeType<>(BatteryUpgradeWrapper::new);
   public static final List<IUpgradeItem.UpgradeConflictDefinition> UPGRADE_CONFLICT_DEFINITIONS = List.of(
      new IUpgradeItem.UpgradeConflictDefinition(BatteryUpgradeItem.class::isInstance, 0, TranslationHelper.INSTANCE.translError("add.battery_exists"))
   );
   private final BatteryUpgradeConfig batteryUpgradeConfig;

   public BatteryUpgradeItem(BatteryUpgradeConfig batteryUpgradeConfig, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.batteryUpgradeConfig = batteryUpgradeConfig;
   }

   public BatteryUpgradeConfig getBatteryUpgradeConfig() {
      return this.batteryUpgradeConfig;
   }

   @Override
   public UpgradeType<BatteryUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public int getInventoryColumnsTaken() {
      return 2;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return UPGRADE_CONFLICT_DEFINITIONS;
   }

   @Override
   public UpgradeSlotChangeResult checkExtraInsertConditions(
      ItemStack upgradeStack, IStorageWrapper storageWrapper, boolean isClientSide, @Nullable IUpgradeItem<?> upgradeInSlot
   ) {
      int maxEnergyAfter = (int)(
         this.getMaxEnergyStored(storageWrapper) / (upgradeInSlot instanceof StackUpgradeItem stackUpgrade ? stackUpgrade.getStackSizeMultiplier() : 1.0)
      );
      double multiplierRequired = (double)BatteryUpgradeWrapper.getEnergyStored(upgradeStack) / maxEnergyAfter;
      if (multiplierRequired > 1.0) {
         DecimalFormat multiplierFormat = new DecimalFormat("0.#");
         String formattedMultiplierRequired = multiplierFormat.format(Math.ceil(10.0 * multiplierRequired) / 10.0);
         return UpgradeSlotChangeResult.fail(
            TranslationHelper.INSTANCE.translError("add.battery_energy_high", formattedMultiplierRequired),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
         );
      } else {
         return UpgradeSlotChangeResult.success();
      }
   }

   public int getMaxEnergyStored(IStorageWrapper storageWrapper) {
      double stackMultiplier = this.getAdjustedStackMultiplier(storageWrapper);
      int maxEnergyBase = this.getMaxEnergyBase(storageWrapper);
      return 2.147483647E9 / stackMultiplier < maxEnergyBase ? Integer.MAX_VALUE : (int)(maxEnergyBase * stackMultiplier);
   }

   public double getAdjustedStackMultiplier(IStorageWrapper storageWrapper) {
      return 1.0 + (Double)this.batteryUpgradeConfig.stackMultiplierRatio.get() * (storageWrapper.getInventoryHandler().getStackSizeMultiplier() - 1.0);
   }

   public int getMaxEnergyBase(IStorageWrapper storageWrapper) {
      return (Integer)this.batteryUpgradeConfig.energyPerSlotRow.get() * storageWrapper.getNumberOfSlotRows();
   }
}

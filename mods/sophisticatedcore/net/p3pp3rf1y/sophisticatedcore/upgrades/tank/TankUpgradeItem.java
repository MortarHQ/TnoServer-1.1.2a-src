package net.p3pp3rf1y.sophisticatedcore.upgrades.tank;

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

public class TankUpgradeItem extends UpgradeItemBase<TankUpgradeWrapper> {
   public static final UpgradeType<TankUpgradeWrapper> TYPE = new UpgradeType<>(TankUpgradeWrapper::new);
   public static final List<IUpgradeItem.UpgradeConflictDefinition> UPGRADE_CONFLICT_DEFINITIONS = List.of(
      new IUpgradeItem.UpgradeConflictDefinition(TankUpgradeItem.class::isInstance, 1, TranslationHelper.INSTANCE.translError("add.two_tank_upgrades_present"))
   );
   private final TankUpgradeConfig tankUpgradeConfig;

   public TankUpgradeItem(TankUpgradeConfig tankUpgradeConfig, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.tankUpgradeConfig = tankUpgradeConfig;
   }

   public int getBaseCapacity(IStorageWrapper storageWrapper) {
      return (Integer)this.tankUpgradeConfig.capacityPerSlotRow.get() * storageWrapper.getNumberOfSlotRows();
   }

   public double getAdjustedStackMultiplier(IStorageWrapper storageWrapper) {
      return 1.0 + (Double)this.tankUpgradeConfig.stackMultiplierRatio.get() * (storageWrapper.getInventoryHandler().getStackSizeMultiplier() - 1.0);
   }

   public int getTankCapacity(IStorageWrapper storageWrapper) {
      double stackMultiplier = this.getAdjustedStackMultiplier(storageWrapper);
      int baseCapacity = this.getBaseCapacity(storageWrapper);
      return 2.147483647E9 / stackMultiplier < baseCapacity ? Integer.MAX_VALUE : (int)(baseCapacity * stackMultiplier);
   }

   public TankUpgradeConfig getTankUpgradeConfig() {
      return this.tankUpgradeConfig;
   }

   @Override
   public UpgradeType<TankUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public UpgradeSlotChangeResult checkExtraInsertConditions(
      ItemStack upgradeStack, IStorageWrapper storageWrapper, boolean isClientSide, @Nullable IUpgradeItem<?> upgradeInSlot
   ) {
      int capacityAfter = (int)(
         this.getTankCapacity(storageWrapper) / (upgradeInSlot instanceof StackUpgradeItem stackUpgrade ? stackUpgrade.getStackSizeMultiplier() : 1.0)
      );
      double multiplierRequired = (double)TankUpgradeWrapper.getContents(upgradeStack).getAmount() / capacityAfter;
      if (multiplierRequired > 1.0) {
         DecimalFormat multiplierFormat = new DecimalFormat("0.#");
         String formattedMultiplierRequired = multiplierFormat.format(Math.ceil(10.0 * multiplierRequired) / 10.0);
         return UpgradeSlotChangeResult.fail(
            TranslationHelper.INSTANCE.translError("add.tank_capacity_high", formattedMultiplierRequired),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
         );
      } else {
         return UpgradeSlotChangeResult.success();
      }
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return UPGRADE_CONFLICT_DEFINITIONS;
   }

   @Override
   public int getInventoryColumnsTaken() {
      return 2;
   }
}

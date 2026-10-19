package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class DepositUpgradeItem extends UpgradeItemBase<DepositUpgradeWrapper> {
   private static final UpgradeType<DepositUpgradeWrapper> TYPE = new UpgradeType(DepositUpgradeWrapper::new);
   private final IntSupplier filterSlotCount;

   public DepositUpgradeItem(IntSupplier filterSlotCount) {
      super(Config.SERVER.maxUpgradesPerStorage);
      this.filterSlotCount = filterSlotCount;
   }

   public UpgradeType<DepositUpgradeWrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public int getFilterSlotCount() {
      return this.filterSlotCount.getAsInt();
   }
}

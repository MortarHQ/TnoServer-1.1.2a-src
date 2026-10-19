package net.p3pp3rf1y.sophisticatedcore.upgrades.compacting;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class CompactingUpgradeItem extends UpgradeItemBase<CompactingUpgradeWrapper> {
   private static final UpgradeType<CompactingUpgradeWrapper> TYPE = new UpgradeType<>(CompactingUpgradeWrapper::new);
   private final boolean shouldCompactThreeByThree;
   private final IntSupplier filterSlotCount;

   public CompactingUpgradeItem(boolean shouldCompactThreeByThree, IntSupplier filterSlotCount, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.shouldCompactThreeByThree = shouldCompactThreeByThree;
      this.filterSlotCount = filterSlotCount;
   }

   @Override
   public UpgradeType<CompactingUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public boolean shouldCompactThreeByThree() {
      return this.shouldCompactThreeByThree;
   }

   public int getFilterSlotCount() {
      return this.filterSlotCount.getAsInt();
   }
}

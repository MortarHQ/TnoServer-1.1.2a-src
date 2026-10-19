package net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class AlchemyUpgradeItem extends UpgradeItemBase<AlchemyUpgradeWrapper> {
   private static final UpgradeType<AlchemyUpgradeWrapper> TYPE = new UpgradeType<>(AlchemyUpgradeWrapper::new);
   private final IntSupplier filterSlotCount;

   public AlchemyUpgradeItem(IntSupplier filterSlotCount, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.filterSlotCount = filterSlotCount;
   }

   @Override
   public UpgradeType<AlchemyUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public int getFilterSlotCount() {
      return this.filterSlotCount.getAsInt();
   }
}

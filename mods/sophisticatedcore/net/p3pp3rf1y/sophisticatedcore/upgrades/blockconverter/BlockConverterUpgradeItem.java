package net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter;

import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;

public abstract class BlockConverterUpgradeItem<U extends BlockConverterUpgradeItem<U, W>, W extends BlockConverterUpgradeWrapper<U, W>>
   extends UpgradeItemBase<W> {
   public BlockConverterUpgradeItem(IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }
}

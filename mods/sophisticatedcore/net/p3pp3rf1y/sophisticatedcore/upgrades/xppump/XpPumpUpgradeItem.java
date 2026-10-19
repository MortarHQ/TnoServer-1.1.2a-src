package net.p3pp3rf1y.sophisticatedcore.upgrades.xppump;

import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class XpPumpUpgradeItem extends UpgradeItemBase<XpPumpUpgradeWrapper> {
   public static final UpgradeType<XpPumpUpgradeWrapper> TYPE = new UpgradeType<>(XpPumpUpgradeWrapper::new);
   private final XpPumpUpgradeConfig xpPumpUpgradeConfig;

   public XpPumpUpgradeItem(XpPumpUpgradeConfig xpPumpUpgradeConfig, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.xpPumpUpgradeConfig = xpPumpUpgradeConfig;
   }

   public XpPumpUpgradeConfig getXpPumpUpgradeConfig() {
      return this.xpPumpUpgradeConfig;
   }

   @Override
   public UpgradeType<XpPumpUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }
}

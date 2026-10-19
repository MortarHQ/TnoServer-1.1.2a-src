package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class AutoSmokingUpgradeItem extends UpgradeItemBase<AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper> implements IAutoCookingUpgradeItem {
   public static final UpgradeType<AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper> TYPE = new UpgradeType<>(
      AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper::new
   );
   private final AutoCookingUpgradeConfig autoSmokingUpgradeConfig;

   public AutoSmokingUpgradeItem(AutoCookingUpgradeConfig autoSmokingUpgradeConfig, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.autoSmokingUpgradeConfig = autoSmokingUpgradeConfig;
   }

   @Override
   public UpgradeType<AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   @Override
   public AutoCookingUpgradeConfig getAutoCookingUpgradeConfig() {
      return this.autoSmokingUpgradeConfig;
   }

   @Override
   public UpgradeGroup getUpgradeGroup() {
      return ICookingUpgrade.UPGRADE_GROUP;
   }
}

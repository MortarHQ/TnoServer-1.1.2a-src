package net.p3pp3rf1y.sophisticatedcore.upgrades.voiding;

import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class VoidUpgradeItem extends UpgradeItemBase<VoidUpgradeWrapper> {
   public static final UpgradeType<VoidUpgradeWrapper> TYPE = new UpgradeType<>(VoidUpgradeWrapper::new);
   private final VoidUpgradeConfig voidUpgradeConfig;

   public VoidUpgradeItem(VoidUpgradeConfig voidUpgradeConfig, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.voidUpgradeConfig = voidUpgradeConfig;
   }

   @Override
   public UpgradeType<VoidUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public int getFilterSlotCount() {
      return (Integer)this.voidUpgradeConfig.filterSlots.get();
   }

   public boolean isVoidAlwaysEnabled() {
      return (Boolean)this.voidUpgradeConfig.voidAlwaysEnabled.get();
   }
}

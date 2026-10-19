package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import java.util.List;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class PumpUpgradeItem extends UpgradeItemBase<PumpUpgradeWrapper> {
   private static final UpgradeType<PumpUpgradeWrapper> TYPE = new UpgradeType<>(PumpUpgradeWrapper::new);
   private final boolean interactWithHandDefault;
   private final boolean interactWithWorldDefault;
   private final boolean interactWithFluidHandlersDefault;
   private final PumpUpgradeConfig pumpUpgradeConfig;

   public PumpUpgradeItem(
      boolean interactWithHandDefault,
      boolean interactWithWorldDefault,
      boolean interactWithFluidHandlersDefault,
      PumpUpgradeConfig pumpUpgradeConfig,
      IUpgradeCountLimitConfig upgradeTypeLimitConfig
   ) {
      super(upgradeTypeLimitConfig);
      this.interactWithHandDefault = interactWithHandDefault;
      this.interactWithWorldDefault = interactWithWorldDefault;
      this.interactWithFluidHandlersDefault = interactWithFluidHandlersDefault;
      this.pumpUpgradeConfig = pumpUpgradeConfig;
   }

   @Override
   public UpgradeType<PumpUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public boolean getInteractWithHandDefault() {
      return this.interactWithHandDefault;
   }

   public boolean getInteractWithWorldDefault() {
      return this.interactWithWorldDefault;
   }

   public PumpUpgradeConfig getPumpUpgradeConfig() {
      return this.pumpUpgradeConfig;
   }

   public boolean getInteractWithFluidHandlersDefault() {
      return this.interactWithFluidHandlersDefault;
   }
}

package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing;

import java.util.List;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class SmithingUpgradeItem extends UpgradeItemBase<SmithingUpgradeWrapper> {
   private static final UpgradeType<SmithingUpgradeWrapper> TYPE = new UpgradeType(SmithingUpgradeWrapper::new);

   public SmithingUpgradeItem() {
      super(Config.SERVER.maxUpgradesPerStorage);
   }

   public UpgradeType<SmithingUpgradeWrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }
}

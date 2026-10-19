package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil;

import java.util.List;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class AnvilUpgradeItem extends UpgradeItemBase<AnvilUpgradeWrapper> {
   private static final UpgradeType<AnvilUpgradeWrapper> TYPE = new UpgradeType(AnvilUpgradeWrapper::new);

   public AnvilUpgradeItem() {
      super(Config.SERVER.maxUpgradesPerStorage);
   }

   public UpgradeType<AnvilUpgradeWrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }
}

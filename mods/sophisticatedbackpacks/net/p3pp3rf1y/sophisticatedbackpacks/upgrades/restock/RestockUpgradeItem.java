package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class RestockUpgradeItem extends UpgradeItemBase<RestockUpgradeWrapper> {
   private static final UpgradeType<RestockUpgradeWrapper> TYPE = new UpgradeType(RestockUpgradeWrapper::new);
   private final IntSupplier filterSlotCount;

   public RestockUpgradeItem(IntSupplier filterSlotCount) {
      super(Config.SERVER.maxUpgradesPerStorage);
      this.filterSlotCount = filterSlotCount;
   }

   public UpgradeType<RestockUpgradeWrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public int getFilterSlotCount() {
      return this.filterSlotCount.getAsInt();
   }
}

package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class RefillUpgradeItem extends UpgradeItemBase<RefillUpgradeWrapper> {
   private static final UpgradeType<RefillUpgradeWrapper> TYPE = new UpgradeType(RefillUpgradeWrapper::new);
   private final IntSupplier filterSlotCount;
   private final boolean targetSlotSelection;
   private final boolean supportsBlockPick;

   public RefillUpgradeItem(IntSupplier filterSlotCount, boolean targetSlotSelection, boolean supportsBlockPick) {
      super(Config.SERVER.maxUpgradesPerStorage);
      this.filterSlotCount = filterSlotCount;
      this.targetSlotSelection = targetSlotSelection;
      this.supportsBlockPick = supportsBlockPick;
   }

   public boolean supportsBlockPick() {
      return this.supportsBlockPick;
   }

   public UpgradeType<RefillUpgradeWrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public int getFilterSlotCount() {
      return this.filterSlotCount.getAsInt();
   }

   public boolean allowsTargetSlotSelection() {
      return this.targetSlotSelection;
   }
}

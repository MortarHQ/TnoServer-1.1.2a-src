package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.util.List;
import java.util.function.IntSupplier;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;

public class JukeboxUpgradeItem extends UpgradeItemBase<JukeboxUpgradeWrapper> {
   public static final UpgradeGroup UPGRADE_GROUP = new UpgradeGroup("jukebox_upgrades", TranslationHelper.INSTANCE.translUpgradeGroup("jukebox_upgrades"));
   public static final UpgradeType<JukeboxUpgradeWrapper> TYPE = new UpgradeType<>(JukeboxUpgradeWrapper::new);
   private final IntSupplier numberOfSlots;
   private final IntSupplier slotsInRow;

   public JukeboxUpgradeItem(IUpgradeCountLimitConfig upgradeTypeLimitConfig, IntSupplier numberOfSlots, IntSupplier slotsInRow) {
      super(upgradeTypeLimitConfig);
      this.numberOfSlots = numberOfSlots;
      this.slotsInRow = slotsInRow;
   }

   @Override
   public UpgradeType<JukeboxUpgradeWrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   @Override
   public UpgradeGroup getUpgradeGroup() {
      return UPGRADE_GROUP;
   }

   public int getNumberOfSlots() {
      return this.numberOfSlots.getAsInt();
   }

   public int getSlotsInRow() {
      return this.slotsInRow.getAsInt();
   }
}

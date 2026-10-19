package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeSlotChangeResult;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class InceptionUpgradeItem extends UpgradeItemBase<InceptionUpgradeWrapper> {
   public static final UpgradeType<InceptionUpgradeWrapper> TYPE = new UpgradeType(InceptionUpgradeWrapper::new);
   public static final List<UpgradeConflictDefinition> UPGRADE_CONFLICT_DEFINITIONS = List.of(
      new UpgradeConflictDefinition(InceptionUpgradeItem.class::isInstance, 0, SBPTranslationHelper.INSTANCE.translError("add.inception_exists", new Object[0]))
   );

   public InceptionUpgradeItem() {
      super(Config.SERVER.maxUpgradesPerStorage);
   }

   public UpgradeType<InceptionUpgradeWrapper> getType() {
      return TYPE;
   }

   public UpgradeSlotChangeResult canAddUpgradeTo(IStorageWrapper storageWrapper, ItemStack upgradeStack, boolean firstLevelStorage, boolean isClientSide) {
      UpgradeSlotChangeResult result = super.canAddUpgradeTo(storageWrapper, upgradeStack, firstLevelStorage, isClientSide);
      if (!result.successful()) {
         return result;
      }

      if (!firstLevelStorage) {
         return UpgradeSlotChangeResult.fail(
            SBPTranslationHelper.INSTANCE.translError("add.inception_sub_backpack", new Object[0]),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
         );
      }

      Set<Integer> errorUpgradeSlots = new HashSet<>();
      storageWrapper.getUpgradeHandler().getSlotWrappers().forEach((slot, wrapper) -> {
         if (wrapper instanceof InceptionUpgradeWrapper) {
            errorUpgradeSlots.add(slot);
         }
      });
      return !errorUpgradeSlots.isEmpty()
         ? UpgradeSlotChangeResult.fail(
            SBPTranslationHelper.INSTANCE.translError("add.inception_exists", new Object[0]), errorUpgradeSlots, Collections.emptySet(), Collections.emptySet()
         )
         : UpgradeSlotChangeResult.success();
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return UPGRADE_CONFLICT_DEFINITIONS;
   }

   public UpgradeSlotChangeResult canRemoveUpgradeFrom(IStorageWrapper storageWrapper, boolean isClientSide) {
      Set<Integer> slots = InventoryHelper.getItemSlots(storageWrapper.getInventoryHandler(), stack -> stack.getItem() instanceof BackpackItem);
      return !slots.isEmpty()
         ? UpgradeSlotChangeResult.fail(
            SBPTranslationHelper.INSTANCE.translError("remove.inception_sub_backpack", new Object[0]), Collections.emptySet(), slots, Collections.emptySet()
         )
         : UpgradeSlotChangeResult.success();
   }

   public UpgradeSlotChangeResult canSwapUpgradeFor(ItemStack upgradeStackToPut, int upgradeSlot, IStorageWrapper storageWrapper, boolean isClientSide) {
      return upgradeStackToPut.getItem() == this ? UpgradeSlotChangeResult.success() : this.canRemoveUpgradeFrom(storageWrapper, isClientSide);
   }
}

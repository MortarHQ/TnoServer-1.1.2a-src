package net.p3pp3rf1y.sophisticatedcore.upgrades.infinity;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeSlotChangeResult;
import net.p3pp3rf1y.sophisticatedcore.inventory.IInventoryPartHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryPartRegistry;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.SlotRange;

public class InfinityUpgradeItem extends UpgradeItemBase<InfinityUpgradeItem.Wrapper> {
   public static final List<IUpgradeItem.UpgradeConflictDefinition> UPGRADE_CONFLICT_DEFINITIONS = List.of(
      new IUpgradeItem.UpgradeConflictDefinition(
         i -> true, 0, TranslationHelper.INSTANCE.translError("add.any_upgrade_exists"), TranslationHelper.INSTANCE.translError("add.no_upgrade_can_be_added")
      )
   );
   public static final UpgradeType<InfinityUpgradeItem.Wrapper> TYPE = new UpgradeType<>(InfinityUpgradeItem.Wrapper::new);
   private final boolean admin;

   public InfinityUpgradeItem(IUpgradeCountLimitConfig upgradeTypeLimitConfig, boolean admin) {
      super(upgradeTypeLimitConfig);
      this.admin = admin;
   }

   @Override
   public UpgradeType<InfinityUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return UPGRADE_CONFLICT_DEFINITIONS;
   }

   @Override
   public UpgradeSlotChangeResult canRemoveUpgradeFrom(IStorageWrapper storageWrapper, boolean isClientSide, Player player) {
      return player.hasPermissions(this.getPermissionLevel())
         ? super.canRemoveUpgradeFrom(storageWrapper, isClientSide, player)
         : UpgradeSlotChangeResult.fail(TranslationHelper.INSTANCE.translError("remove.infinity_upgrade_only_admin"), Set.of(), Set.of(), Set.of());
   }

   public int getPermissionLevel() {
      return this.admin ? 2 : 0;
   }

   public IInventoryPartHandler createInventoryPartHandler(InventoryHandler parent, SlotRange slotRange) {
      return this.admin ? new InfinityInventoryPart.Admin(parent, slotRange) : new InfinityInventoryPart.Survival(parent, slotRange);
   }

   static {
      InventoryPartRegistry.registerFactory("infinity", (parent, slotRange, getMemorySettings) -> new InfinityInventoryPart.Admin(parent, slotRange));
      InventoryPartRegistry.registerFactory(
         "survival_infinity", (parent, slotRange, getMemorySettings) -> new InfinityInventoryPart.Survival(parent, slotRange)
      );
   }

   public static class Wrapper extends UpgradeWrapperBase<InfinityUpgradeItem.Wrapper, InfinityUpgradeItem> {
      protected Wrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler);
      }

      @Override
      public boolean canBeDisabled() {
         return false;
      }

      @Override
      public void onAdded() {
         super.onAdded();
         InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();
         inventoryHandler.getInventoryPartitioner()
            .addInventoryPart(
               0, Integer.MAX_VALUE, this.upgradeItem.createInventoryPartHandler(inventoryHandler, new SlotRange(0, inventoryHandler.getSlots()))
            );
         this.storageWrapper.getSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class).itemsChanged();
      }

      @Override
      public void onBeforeRemoved() {
         super.onBeforeRemoved();
         this.storageWrapper.getInventoryHandler().getInventoryPartitioner().removeInventoryPart(0);
         this.save();
      }

      public int getPermissionLevel() {
         return this.upgradeItem.getPermissionLevel();
      }
   }
}

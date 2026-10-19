package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModDataComponents;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.StatefulComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class AnvilUpgradeWrapper extends UpgradeWrapperBase<AnvilUpgradeWrapper, AnvilUpgradeItem> {
   private final StatefulComponentItemHandler inventory;

   protected AnvilUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      if (upgrade.has(DataComponents.CONTAINER)) {
         upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, (ItemContainerContents)upgrade.get(DataComponents.CONTAINER));
      }

      upgrade.remove(DataComponents.CONTAINER);
      this.inventory = new StatefulComponentItemHandler(upgrade, (DataComponentType)ModCoreDataComponents.LENIENT_CONTAINER.get(), 2) {
         protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
            super.onContentsChanged(slot, oldStack, newStack);
            AnvilUpgradeWrapper.this.save();
         }

         public boolean isItemValid(int slot, ItemStack stack) {
            return true;
         }
      };
   }

   public StatefulComponentItemHandler getInventory() {
      return this.inventory;
   }

   public boolean canBeDisabled() {
      return false;
   }

   public boolean shouldShiftClickIntoStorage() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, true);
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      this.upgrade.set(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, shiftClickIntoStorage);
      this.save();
   }

   public String getItemName() {
      return (String)this.upgrade.getOrDefault(ModDataComponents.ITEM_NAME, "");
   }

   public void setItemName(String itemName) {
      this.upgrade.set(ModDataComponents.ITEM_NAME, itemName);
      this.save();
   }
}

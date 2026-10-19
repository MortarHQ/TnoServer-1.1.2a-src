package net.p3pp3rf1y.sophisticatedcore.upgrades.crafting;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.StatefulComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class CraftingUpgradeWrapper extends UpgradeWrapperBase<CraftingUpgradeWrapper, CraftingUpgradeItem> {
   private final StatefulComponentItemHandler inventory;

   public CraftingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      if (upgrade.has(DataComponents.CONTAINER)) {
         upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, (ItemContainerContents)upgrade.get(DataComponents.CONTAINER));
      }

      upgrade.remove(DataComponents.CONTAINER);
      this.inventory = new StatefulComponentItemHandler(upgrade, ModCoreDataComponents.LENIENT_CONTAINER.get(), 9) {
         @Override
         protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
            super.onContentsChanged(slot, oldStack, newStack);
            CraftingUpgradeWrapper.this.save();
         }

         @Override
         public boolean isItemValid(int slot, ItemStack stack) {
            return true;
         }
      };
   }

   public StatefulComponentItemHandler getInventory() {
      return this.inventory;
   }

   @Override
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

   public boolean shouldRefillCraftingGridNBT() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.REFILL_CRAFTING_GRID, false);
   }

   public void setRefillCraftingGridNBT(boolean replenish) {
      this.upgrade.set(ModCoreDataComponents.REFILL_CRAFTING_GRID, replenish);
      this.save();
   }

   public boolean extractFromStorageOrPlayer(Player player, ItemStack stack) {
      return this.extractFromStorage(stack) || this.extractFromPlayer(player, stack);
   }

   private boolean extractFromPlayer(Player player, ItemStack stack) {
      int playerInvMatchingIndex = player.getInventory().findSlotMatchingItem(stack);
      if (playerInvMatchingIndex >= 0) {
         player.getInventory().removeItem(playerInvMatchingIndex, 1);
         return true;
      } else {
         return false;
      }
   }

   private boolean extractFromStorage(ItemStack stack) {
      return !InventoryHelper.extractFromInventory(s -> ItemStack.isSameItemSameComponents(s, stack), 1, this.storageWrapper.getInventoryHandler(), false)
         .isEmpty();
   }

   public boolean insertIntoStorageOrPlayer(Player player, ItemStack stack) {
      return this.shouldShiftClickIntoStorage() && InventoryHelper.insertIntoInventory(stack, this.storageWrapper.getInventoryHandler(), false).isEmpty()
         ? true
         : player.getInventory().add(stack);
   }
}

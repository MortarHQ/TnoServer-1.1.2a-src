package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterAttributes;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class DepositFilterLogic extends FilterLogic {
   private Set<ItemStackKey> inventoryFilterStacks = new HashSet<>();

   public DepositFilterLogic(
      ItemStack upgrade,
      Consumer<ItemStack> saveHandler,
      int filterSlotCount,
      DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> contentsComponent
   ) {
      super(upgrade, saveHandler, filterSlotCount, contentsComponent);
   }

   public DepositFilterType getDepositFilterType() {
      if (this.shouldFilterByInventory()) {
         return DepositFilterType.INVENTORY;
      } else {
         return this.isAllowList() ? DepositFilterType.ALLOW : DepositFilterType.BLOCK;
      }
   }

   public void setDepositFilterType(DepositFilterType depositFilterType) {
      switch (depositFilterType) {
         case ALLOW:
            this.setFilterByInventory(false);
            this.setAllowList(true);
            break;
         case BLOCK:
            this.setFilterByInventory(false);
            this.setAllowList(false);
            break;
         case INVENTORY:
         default:
            this.setFilterByInventory(true);
            this.save();
      }
   }

   public void setInventory(IItemHandler inventory) {
      this.inventoryFilterStacks = InventoryHelper.getUniqueStacks(inventory);
   }

   public boolean matchesFilter(ItemStack stack) {
      if (!this.shouldFilterByInventory()) {
         return super.matchesFilter(stack);
      }

      for (ItemStackKey filterStack : this.inventoryFilterStacks) {
         if (this.stackMatchesFilter(stack, filterStack.getStack())) {
            return true;
         }
      }

      return false;
   }

   private void setFilterByInventory(boolean filterByInventory) {
      this.upgrade.set(ModDataComponents.FILTER_BY_INVENTORY, filterByInventory);
      this.save();
   }

   private boolean shouldFilterByInventory() {
      return (Boolean)this.upgrade.getOrDefault(ModDataComponents.FILTER_BY_INVENTORY, false);
   }
}

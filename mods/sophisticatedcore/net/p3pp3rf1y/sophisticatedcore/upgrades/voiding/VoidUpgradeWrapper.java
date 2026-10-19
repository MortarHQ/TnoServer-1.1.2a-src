package net.p3pp3rf1y.sophisticatedcore.upgrades.voiding;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.ISlotChangeResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IInsertResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IOverflowResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class VoidUpgradeWrapper
   extends UpgradeWrapperBase<VoidUpgradeWrapper, VoidUpgradeItem>
   implements IInsertResponseUpgrade,
   IFilteredUpgrade,
   ISlotChangeResponseUpgrade,
   ITickableUpgrade,
   IOverflowResponseUpgrade {
   private final FilterLogic filterLogic;
   private final Set<Integer> slotsToVoid = new HashSet<>();
   private VoidType voidType;

   public VoidUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new FilterLogic(upgrade, upgradeSaveHandler, this.upgradeItem.getFilterSlotCount(), ModCoreDataComponents.FILTER_ATTRIBUTES);
      this.filterLogic.setAllowByDefault(true);
      this.setFromLegacyComponent();
      this.setVoidOverflowDefaultOrLoadFromNbt(VoidType.ALWAYS);
   }

   private void setFromLegacyComponent() {
      if (this.upgrade.has(ModCoreDataComponents.LEGACY_SHOULD_VOID_OVERFLOW)) {
         VoidType migratedVoidType = this.upgrade.get(ModCoreDataComponents.LEGACY_SHOULD_VOID_OVERFLOW) ? VoidType.SLOT_OVERFLOW : VoidType.ALWAYS;
         this.upgrade.remove(ModCoreDataComponents.LEGACY_SHOULD_VOID_OVERFLOW);
         this.setVoidType(migratedVoidType);
      }
   }

   @Override
   public ItemStack onBeforeInsert(InventoryHandler inventoryHandler, int slot, ItemStack stack, boolean simulate) {
      if (this.voidType == VoidType.SLOT_OVERFLOW && inventoryHandler.getStackInSlot(slot).isEmpty() && this.filterLogic.matchesFilter(stack)) {
         return inventoryHandler.getSlotTracker().getFullStacks().contains(ItemStackKey.of(stack)) ? ItemStack.EMPTY : stack;
      } else {
         return this.voidType == VoidType.ALWAYS && this.filterLogic.matchesFilter(stack) ? ItemStack.EMPTY : stack;
      }
   }

   @Override
   public ItemStack onBeforeInsert(InventoryHandler inventoryHandler, ItemStack stack, boolean simulate) {
      return this.voidType == VoidType.ALWAYS && this.filterLogic.matchesFilter(stack) ? ItemStack.EMPTY : stack;
   }

   @Override
   public FilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   public void setShouldWorkdInGUI(boolean shouldWorkdInGUI) {
      this.upgrade.set(ModCoreDataComponents.SHOULD_WORK_IN_GUI, shouldWorkdInGUI);
      this.save();
   }

   public boolean shouldWorkInGUI() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.SHOULD_WORK_IN_GUI, false);
   }

   public void setVoidType(VoidType voidType) {
      if (voidType != VoidType.ALWAYS || this.upgradeItem.isVoidAlwaysEnabled()) {
         this.voidType = voidType;
         this.upgrade.set(ModCoreDataComponents.VOID_TYPE, voidType);
         this.save();
      }
   }

   public void setVoidOverflowDefaultOrLoadFromNbt(VoidType voidOverflowDefault) {
      VoidType vt = (VoidType)this.upgrade.getOrDefault(ModCoreDataComponents.VOID_TYPE, voidOverflowDefault);
      if (!this.upgradeItem.isVoidAlwaysEnabled() && vt == VoidType.ALWAYS) {
         vt = VoidType.SLOT_OVERFLOW;
      }

      this.voidType = vt;
   }

   public boolean shouldVoidOverflow() {
      return !this.upgradeItem.isVoidAlwaysEnabled() || this.voidType != VoidType.ALWAYS;
   }

   public VoidType getVoidType() {
      return this.voidType == VoidType.ALWAYS && !this.upgradeItem.isVoidAlwaysEnabled() ? VoidType.SLOT_OVERFLOW : this.voidType;
   }

   @Override
   public void onSlotChange(IItemHandler inventoryHandler, int slot) {
      if (this.shouldWorkInGUI() && !this.shouldVoidOverflow()) {
         ItemStack slotStack = inventoryHandler.getStackInSlot(slot);
         if (this.filterLogic.matchesFilter(slotStack)) {
            this.slotsToVoid.add(slot);
         }
      }
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.slotsToVoid.isEmpty()) {
         InventoryHandler storageInventory = this.storageWrapper.getInventoryHandler();

         for (int slot : this.slotsToVoid) {
            storageInventory.extractItem(slot, storageInventory.getStackInSlot(slot).getCount(), false);
         }

         this.slotsToVoid.clear();
      }
   }

   @Override
   public boolean worksInGui() {
      return this.shouldWorkInGUI();
   }

   @Override
   public ItemStack onSlotOverflow(ItemStack stack) {
      return this.voidType == VoidType.SLOT_OVERFLOW && this.filterLogic.matchesFilter(stack) ? ItemStack.EMPTY : stack;
   }

   @Override
   public ItemStack onStorageOverflow(ItemStack stack) {
      return this.voidType == VoidType.STORAGE_OVERFLOW && this.filterLogic.matchesFilter(stack) ? ItemStack.EMPTY : stack;
   }

   @Override
   public boolean stackMatchesFilter(ItemStack stack) {
      return this.filterLogic.matchesFilter(stack);
   }

   public boolean isVoidAlwaysEnabled() {
      return this.upgradeItem.isVoidAlwaysEnabled();
   }
}

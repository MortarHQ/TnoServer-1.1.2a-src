package net.p3pp3rf1y.sophisticatedcore.upgrades.compacting;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.ISlotChangeResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IExtractResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IInsertResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;

public class CompactingUpgradeWrapper
   extends UpgradeWrapperBase<CompactingUpgradeWrapper, CompactingUpgradeItem>
   implements IInsertResponseUpgrade,
   IFilteredUpgrade,
   ISlotChangeResponseUpgrade,
   ITickableUpgrade,
   IExtractResponseUpgrade {
   private final FilterLogic filterLogic;
   private final Set<Integer> slotsToCompact = new HashSet<>();
   private boolean fullSlotsCalculated = false;
   private final Map<Item, Integer> fullSlotsToCompactLater = new HashMap<>();

   public CompactingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new FilterLogic(
         upgrade,
         upgradeSaveHandler,
         this.upgradeItem.getFilterSlotCount(),
         stack -> RecipeHelper.getItemCompactingShapes(stack).stream().anyMatch(shape -> shape != RecipeHelper.CompactingShape.NONE),
         ModCoreDataComponents.FILTER_ATTRIBUTES
      );
      FilterLogic.ObservableFilterItemStackHandler filterHandler = this.filterLogic.getFilterHandler();
      filterHandler.setOnSlotChange(s -> this.resetFullSlotInfo());
   }

   @Override
   public void onAfterInsert(IItemHandlerSimpleInserter inventoryHandler, int slot) {
      this.compactSlot(inventoryHandler, slot);
   }

   @Override
   public void onAfterExtract(IItemHandlerSimpleInserter inventoryHandler, int slot, ItemStack originalContents) {
      if (this.fullSlotsToCompactLater.containsKey(originalContents.getItem())) {
         int fullSlot = this.fullSlotsToCompactLater.get(originalContents.getItem());
         this.slotsToCompact.add(fullSlot);
      }
   }

   private void compactSlot(IItemHandlerSimpleInserter handler, int slot) {
      ItemStack slotStack = handler.getStackInSlot(slot);
      if (!slotStack.isEmpty() && this.filterLogic.matchesFilter(slotStack)) {
         Set<RecipeHelper.CompactingShape> shapes = RecipeHelper.getItemCompactingShapes(slotStack);
         if (!this.upgradeItem.shouldCompactThreeByThree()
            || !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE)
               && (!this.shouldCompactNonUncraftable() || !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE))) {
            if (shapes.contains(RecipeHelper.CompactingShape.TWO_BY_TWO_UNCRAFTABLE)
               || this.shouldCompactNonUncraftable() && shapes.contains(RecipeHelper.CompactingShape.TWO_BY_TWO)) {
               this.tryCompacting(handler, slot, slotStack, 2, 2);
            }
         } else {
            this.tryCompacting(handler, slot, slotStack, 3, 3);
         }
      }
   }

   private void tryCompacting(IItemHandlerSimpleInserter handler, int slot, ItemStack stack, int width, int height) {
      int totalCount = width * height;
      RecipeHelper.CompactingResult compactingResult = RecipeHelper.getCompactingResult(stack, width, height);
      if (!compactingResult.getResult().isEmpty()) {
         ItemStack extractedStack = InventoryHelper.extractFromInventory(stack.copyWithCount(totalCount), handler, true);
         if (extractedStack.getCount() != totalCount) {
            return;
         }

         while (extractedStack.getCount() == totalCount) {
            ItemStack resultCopy = compactingResult.getResult().copy();
            List<ItemStack> remainingItemsCopy = compactingResult.getRemainingItems().isEmpty()
               ? Collections.emptyList()
               : compactingResult.getRemainingItems().stream().<ItemStack>map(ItemStack::copy).toList();
            if (!this.fitsResultAndRemainingItems(handler, remainingItemsCopy, resultCopy)) {
               if (handler instanceof InventoryHandler inventoryHandler
                  && inventoryHandler.getStackInSlot(slot).getCount() >= inventoryHandler.getStackLimit(slot, stack)) {
                  this.fullSlotsToCompactLater.put(resultCopy.getItem(), slot);
               }

               return;
            }

            this.fullSlotsToCompactLater.remove(resultCopy.getItem());
            InventoryHelper.extractFromInventory(stack.copyWithCount(totalCount), handler, false);
            handler.insertItem(resultCopy, false);
            InventoryHelper.insertIntoInventory(remainingItemsCopy, handler, false);
            extractedStack = InventoryHelper.extractFromInventory(stack.copyWithCount(totalCount), handler, true);
         }
      }
   }

   private boolean fitsResultAndRemainingItems(IItemHandler inventoryHandler, List<ItemStack> remainingItems, ItemStack result) {
      if (remainingItems.isEmpty()) {
         return InventoryHelper.insertIntoInventory(result, inventoryHandler, true).isEmpty();
      }

      IItemHandler clonedHandler = InventoryHelper.cloneInventory(inventoryHandler);
      return InventoryHelper.insertIntoInventory(result, clonedHandler, false).isEmpty()
         && InventoryHelper.insertIntoInventory(remainingItems, clonedHandler, false).isEmpty();
   }

   @Override
   public FilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   public boolean shouldCompactNonUncraftable() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.COMPACT_NON_UNCRAFTABLE, false);
   }

   public void setCompactNonUncraftable(boolean shouldCompactNonUncraftable) {
      this.upgrade.set(ModCoreDataComponents.COMPACT_NON_UNCRAFTABLE, shouldCompactNonUncraftable);
      this.save();
   }

   @Override
   public void onSlotChange(IItemHandler handler, int slot) {
      if (this.shouldWorkInGUI()) {
         this.slotsToCompact.add(slot);
      } else if (handler instanceof InventoryHandler inventoryHandler) {
         this.calculateFullSlot(inventoryHandler, slot);
      }
   }

   public void setShouldWorkdInGUI(boolean shouldWorkdInGUI) {
      this.upgrade.set(ModCoreDataComponents.SHOULD_WORK_IN_GUI, shouldWorkdInGUI);
      this.save();
   }

   public boolean shouldWorkInGUI() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.SHOULD_WORK_IN_GUI, false);
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.fullSlotsCalculated) {
         this.calculateFullSlots();
         this.fullSlotsCalculated = true;
      }

      if (!this.slotsToCompact.isEmpty()) {
         for (int slot : this.slotsToCompact) {
            this.compactSlot(this.storageWrapper.getInventoryHandler(), slot);
         }

         this.slotsToCompact.clear();
      }
   }

   private void calculateFullSlots() {
      InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();

      for (int slot = 0; slot < inventoryHandler.getSlots(); slot++) {
         this.calculateFullSlot(inventoryHandler, slot);
      }
   }

   private void calculateFullSlot(InventoryHandler inventoryHandler, int slot) {
      ItemStack slotStack = inventoryHandler.getStackInSlot(slot);
      if (!slotStack.isEmpty() && this.filterLogic.matchesFilter(slotStack) && slotStack.getCount() >= inventoryHandler.getStackLimit(slot, slotStack)) {
         Set<RecipeHelper.CompactingShape> shapes = RecipeHelper.getItemCompactingShapes(slotStack);
         boolean canCompact = false;
         if (!this.upgradeItem.shouldCompactThreeByThree()
            || !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE)
               && (!this.shouldCompactNonUncraftable() || !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE))) {
            if (shapes.contains(RecipeHelper.CompactingShape.TWO_BY_TWO_UNCRAFTABLE)
               || this.shouldCompactNonUncraftable() && shapes.contains(RecipeHelper.CompactingShape.TWO_BY_TWO)) {
               canCompact = true;
            }
         } else {
            canCompact = true;
         }

         if (canCompact && slotStack.getCount() >= slotStack.getMaxStackSize()) {
            RecipeHelper.CompactingShape shape = !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE)
                  && (!this.shouldCompactNonUncraftable() || !shapes.contains(RecipeHelper.CompactingShape.THREE_BY_THREE))
               ? RecipeHelper.CompactingShape.TWO_BY_TWO
               : RecipeHelper.CompactingShape.THREE_BY_THREE;
            int width = shape == RecipeHelper.CompactingShape.THREE_BY_THREE ? 3 : 2;
            int height = shape == RecipeHelper.CompactingShape.THREE_BY_THREE ? 3 : 2;
            RecipeHelper.CompactingResult compactingResult = RecipeHelper.getCompactingResult(slotStack, width, height);
            if (!compactingResult.getResult().isEmpty()) {
               ItemStack resultCopy = compactingResult.getResult().copy();
               List<ItemStack> remainingItemsCopy = compactingResult.getRemainingItems().isEmpty()
                  ? Collections.emptyList()
                  : compactingResult.getRemainingItems().stream().<ItemStack>map(ItemStack::copy).toList();
               if (!this.fitsResultAndRemainingItems(inventoryHandler, remainingItemsCopy, resultCopy)) {
                  this.fullSlotsToCompactLater.put(resultCopy.getItem(), slot);
               }
            }
         }
      }
   }

   public void resetFullSlotInfo() {
      this.fullSlotsCalculated = false;
      this.fullSlotsToCompactLater.clear();
   }
}

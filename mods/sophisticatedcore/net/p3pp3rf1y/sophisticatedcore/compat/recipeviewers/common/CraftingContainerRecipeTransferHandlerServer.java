package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

public class CraftingContainerRecipeTransferHandlerServer {
   private CraftingContainerRecipeTransferHandlerServer() {
   }

   public static void setItemsWithSlotIDMap(
      Player player,
      ResourceLocation recipeId,
      RecipeType<?> recipeType,
      Map<Integer, Integer> slotIdMap,
      List<Integer> craftingSlots,
      List<Integer> inventorySlots,
      boolean maxTransfer
   ) {
      if (player.containerMenu instanceof StorageContainerMenuBase<?> container) {
         HashMap var14 = new HashMap(slotIdMap.size());

         for (Entry<Integer, Integer> entry : slotIdMap.entrySet()) {
            Slot slot = container.getSlot(entry.getValue());
            ItemStack slotStack = slot.getItem();
            if (slotStack.isEmpty()) {
               return;
            }

            ItemStack stack = slotStack.copy();
            stack.setCount(1);
            var14.put(entry.getKey(), stack);
         }

         setItems(container, player, recipeId, recipeType, var14, craftingSlots, inventorySlots, maxTransfer);
      }
   }

   public static void setItemsWithStacks(
      Player player,
      ResourceLocation recipeId,
      RecipeType<?> recipeType,
      List<ItemStack> stacks,
      List<Integer> craftingSlots,
      List<Integer> inventorySlots,
      boolean maxTransfer
   ) {
      if (player.containerMenu instanceof StorageContainerMenuBase<?> container) {
         HashMap var11 = new HashMap(stacks.size());

         for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (!stack.isEmpty()) {
               stack.setCount(1);
               var11.put(craftingSlots.get(i), stack);
            }
         }

         setItems(container, player, recipeId, recipeType, var11, craftingSlots, inventorySlots, maxTransfer);
      }
   }

   private static void setItems(
      StorageContainerMenuBase<?> container,
      Player player,
      ResourceLocation recipeId,
      RecipeType<?> recipeType,
      Map<Integer, ItemStack> slotMap,
      List<Integer> craftingSlots,
      List<Integer> inventorySlots,
      boolean maxTransfer
   ) {
      Map<Integer, ItemStack> toTransfer = removeItemsFromInventory(player, container, slotMap, craftingSlots, inventorySlots, maxTransfer);
      if (!toTransfer.isEmpty()) {
         List<ItemStack> clearedCraftingItems = clearAndPutItemsIntoGrid(player, recipeId, recipeType, craftingSlots, container, toTransfer);
         putIntoInventory(player, inventorySlots, container, clearedCraftingItems);
         container.broadcastChanges();
      }
   }

   private static void putIntoInventory(
      Player player, List<Integer> inventorySlots, StorageContainerMenuBase<?> container, List<ItemStack> clearedCraftingItems
   ) {
      for (ItemStack oldCraftingItem : clearedCraftingItems) {
         int added = addStack(container, inventorySlots, oldCraftingItem);
         if (added < oldCraftingItem.getCount()) {
            ItemStack remainingStack = added == 0 ? oldCraftingItem : oldCraftingItem.copyWithCount(oldCraftingItem.getCount() - added);
            if (!player.getInventory().add(remainingStack)) {
               player.drop(remainingStack, false);
            }
         }
      }
   }

   private static List<ItemStack> clearAndPutItemsIntoGrid(
      Player player,
      ResourceLocation recipeId,
      RecipeType<?> recipeType,
      List<Integer> craftingSlots,
      AbstractContainerMenu container,
      Map<Integer, ItemStack> toTransfer
   ) {
      List<ItemStack> clearedCraftingItems = new ArrayList<>();
      int minSlotStackLimit = Integer.MAX_VALUE;

      for (int craftingSlotNumberIndex = 0; craftingSlotNumberIndex < craftingSlots.size(); craftingSlotNumberIndex++) {
         int craftingSlotNumber = craftingSlots.get(craftingSlotNumberIndex);
         Slot craftingSlot = container.getSlot(craftingSlotNumber);
         if (craftingSlot.mayPickup(player)) {
            if (craftingSlot.hasItem()) {
               ItemStack craftingItem = craftingSlot.remove(craftingSlot.getItem().getCount());
               clearedCraftingItems.add(craftingItem);
            }

            ItemStack transferItem = toTransfer.get(craftingSlotNumberIndex);
            if (transferItem != null) {
               int slotStackLimit = craftingSlot.getMaxStackSize(transferItem);
               minSlotStackLimit = Math.min(slotStackLimit, minSlotStackLimit);
            }
         }
      }

      putItemIntoGrid(container, toTransfer, clearedCraftingItems, minSlotStackLimit);
      if (container instanceof StorageContainerMenuBase<?> storageContainerMenu) {
         storageContainerMenu.getOpenOrFirstCraftingContainer(recipeType).ifPresent(c -> ((ICraftingContainer)c).setRecipeUsed(recipeId));
      }

      return clearedCraftingItems;
   }

   private static void putItemIntoGrid(
      AbstractContainerMenu container, Map<Integer, ItemStack> toTransfer, List<ItemStack> clearedCraftingItems, int minSlotStackLimit
   ) {
      for (Entry<Integer, ItemStack> entry : toTransfer.entrySet()) {
         Integer craftingSlotIndex = entry.getKey();
         Slot slot = container.getSlot(craftingSlotIndex);
         ItemStack stack = entry.getValue();
         if (slot.mayPlace(stack)) {
            if (stack.getCount() > minSlotStackLimit) {
               ItemStack remainder = stack.split(stack.getCount() - minSlotStackLimit);
               clearedCraftingItems.add(remainder);
            }

            slot.set(stack);
         } else {
            clearedCraftingItems.add(stack);
         }
      }
   }

   private static Map<Integer, ItemStack> removeItemsFromInventory(
      Player player,
      StorageContainerMenuBase<?> container,
      Map<Integer, ItemStack> required,
      List<Integer> craftingSlots,
      List<Integer> inventorySlots,
      boolean maxTransfer
   ) {
      Map<Integer, ItemStack> result = new HashMap<>(required.size());

      boolean noItemsFound;
      do {
         Map<Slot, ItemStack> originalSlotContents = new HashMap<>();
         Map<Integer, ItemStack> foundItemsInSet = new HashMap<>(required.size());
         noItemsFound = true;

         for (Entry<Integer, ItemStack> entry : required.entrySet()) {
            ItemStack requiredStack = entry.getValue().copy();
            Slot slot = getSlotWithStack(container, requiredStack, craftingSlots, inventorySlots);
            boolean itemFound = slot != null && !slot.getItem().isEmpty() && slot.mayPickup(player);
            ItemStack resultItemStack = result.get(entry.getKey());
            boolean resultItemStackLimitReached = resultItemStack != null && resultItemStack.getCount() == resultItemStack.getMaxStackSize();
            if (!itemFound || resultItemStackLimitReached) {
               for (Entry<Slot, ItemStack> slotEntry : originalSlotContents.entrySet()) {
                  ItemStack stack = slotEntry.getValue();
                  slotEntry.getKey().set(stack);
               }

               return result;
            }

            if (!originalSlotContents.containsKey(slot)) {
               originalSlotContents.put(slot, slot.getItem().copy());
            }

            ItemStack removedItemStack = slot.remove(1);
            foundItemsInSet.put(entry.getKey(), removedItemStack);
            noItemsFound = false;
         }

         for (Entry<Integer, ItemStack> entry : foundItemsInSet.entrySet()) {
            ItemStack resultItemStack = result.get(entry.getKey());
            if (resultItemStack == null) {
               result.put(entry.getKey(), entry.getValue());
            } else {
               resultItemStack.grow(1);
            }
         }
      } while (maxTransfer && !noItemsFound);

      return result;
   }

   @Nullable
   private static Slot getSlotWithStack(StorageContainerMenuBase<?> container, ItemStack stack, List<Integer> craftingSlots, List<Integer> inventorySlots) {
      Slot slot = getSlotWithStack(container, craftingSlots, stack);
      if (slot == null) {
         slot = getSlotWithStack(container, inventorySlots, stack);
      }

      return slot;
   }

   private static int addStack(StorageContainerMenuBase<?> container, Collection<Integer> slotIndexes, ItemStack stack) {
      int added = 0;

      for (Integer slotIndex : slotIndexes) {
         if (slotIndex >= 0 && slotIndex < getTotalSlotsSize(container)) {
            Slot slot = container.getSlot(slotIndex);
            ItemStack inventoryStack = slot.getItem();
            if (!inventoryStack.isEmpty() && inventoryStack.isStackable() && ItemStack.isSameItemSameComponents(inventoryStack, stack)) {
               int remain = stack.getCount() - added;
               int maxStackSize = slot.getMaxStackSize(inventoryStack);
               int space = maxStackSize - inventoryStack.getCount();
               if (space > 0) {
                  if (space >= remain) {
                     inventoryStack.grow(remain);
                     return stack.getCount();
                  }

                  inventoryStack.setCount(maxStackSize);
                  added += space;
               }
            }
         }
      }

      if (added >= stack.getCount()) {
         return added;
      }

      for (Integer slotIndex : slotIndexes) {
         if (slotIndex >= 0 && slotIndex < getTotalSlotsSize(container)) {
            Slot slot = container.getSlot(slotIndex);
            ItemStack inventoryStack = slot.getItem();
            if (inventoryStack.isEmpty()) {
               ItemStack stackToAdd = stack.copy();
               stackToAdd.setCount(stack.getCount() - added);
               slot.set(stackToAdd);
               return stack.getCount();
            }
         }
      }

      return added;
   }

   @Nullable
   private static Slot getSlotWithStack(StorageContainerMenuBase<?> container, Iterable<Integer> slotNumbers, ItemStack itemStack) {
      for (Integer slotNumber : slotNumbers) {
         if (slotNumber >= 0 && slotNumber < getTotalSlotsSize(container)) {
            Slot slot = container.getSlot(slotNumber);
            ItemStack slotStack = slot.getItem();
            if (ItemStack.isSameItemSameComponents(itemStack, slotStack)) {
               return slot;
            }
         }
      }

      return null;
   }

   private static int getTotalSlotsSize(StorageContainerMenuBase<?> container) {
      return container.upgradeSlots.size() + container.realInventorySlots.size();
   }
}

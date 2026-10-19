package net.p3pp3rf1y.sophisticatedcore.compat.craftingtweaks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.blay09.mods.craftingtweaks.api.CraftingGrid;
import net.blay09.mods.craftingtweaks.api.CraftingGridBuilder;
import net.blay09.mods.craftingtweaks.api.CraftingGridProvider;
import net.blay09.mods.craftingtweaks.api.GridBalanceHandler;
import net.blay09.mods.craftingtweaks.api.GridTransferHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

public class CraftingUpgradeTweakProvider implements CraftingGridProvider {
   public String getModId() {
      return "sophisticatedcore";
   }

   public boolean handles(AbstractContainerMenu abstractContainerMenu) {
      return abstractContainerMenu instanceof StorageContainerMenuBase;
   }

   public void buildCraftingGrids(CraftingGridBuilder builder, AbstractContainerMenu containerMenu) {
      if (containerMenu instanceof StorageContainerMenuBase<?> storageContainer) {
         builder.addGrid(getCraftingGridStart(storageContainer), getCraftingGridSize(storageContainer))
            .clearHandler((craftingGrid, player, menu, forced) -> this.clearGrid(player, menu, forced))
            .rotateHandler((craftingGrid, player, menu, reverse) -> this.rotateGrid(menu, reverse))
            .balanceHandler(new CraftingUpgradeTweakProvider.StorageCraftingGridBalanceHandler())
            .transferHandler(new CraftingUpgradeTweakProvider.StorageCraftingGridTransferHandler())
            .hideAllTweakButtons();
      }
   }

   public void clearGrid(Player player, AbstractContainerMenu menu, boolean forced) {
      if (menu instanceof StorageContainerMenuBase<?> storageContainer) {
         getCraftMatrix(storageContainer).ifPresent(craftMatrix -> {
            for (Slot recipeSlot : getRecipeSlots(storageContainer)) {
               int slotIndex = recipeSlot.getContainerSlot();
               ItemStack itemStack = craftMatrix.getItem(slotIndex);
               if (!itemStack.isEmpty()) {
                  ItemStack returnStack = itemStack.copy();
                  player.getInventory().add(returnStack);
                  craftMatrix.setItem(slotIndex, returnStack.getCount() == 0 ? ItemStack.EMPTY : returnStack);
                  if (returnStack.getCount() > 0 && forced) {
                     player.drop(returnStack, false);
                     craftMatrix.setItem(slotIndex, ItemStack.EMPTY);
                  }
               }
            }

            storageContainer.broadcastChanges();
         });
      }
   }

   private int rotateSlotId(int slotId, boolean counterClockwise) {
      if (!counterClockwise) {
         switch (slotId) {
            case 0:
               return 1;
            case 1:
               return 2;
            case 2:
               return 5;
            case 3:
               return 0;
            case 4:
            default:
               break;
            case 5:
               return 8;
            case 6:
               return 3;
            case 7:
               return 6;
            case 8:
               return 7;
         }
      } else {
         switch (slotId) {
            case 0:
               return 3;
            case 1:
               return 0;
            case 2:
               return 1;
            case 3:
               return 6;
            case 4:
            default:
               break;
            case 5:
               return 2;
            case 6:
               return 7;
            case 7:
               return 8;
            case 8:
               return 5;
         }
      }

      return 0;
   }

   private boolean ignoresSlotId(int slotId) {
      return slotId == 4;
   }

   private void rotateGrid(AbstractContainerMenu containerMenu, boolean counterClockwise) {
      if (containerMenu instanceof StorageContainerMenuBase<?> storageContainer) {
         getCraftMatrix(storageContainer).ifPresent(craftMatrix -> {
            List<Slot> recipeSlots = getRecipeSlots(storageContainer);
            int size = recipeSlots.size();
            if (size == 9) {
               Container matrixClone = new SimpleContainer(size);

               for (int i = 0; i < size; i++) {
                  int slotIndex = recipeSlots.get(i).getContainerSlot();
                  matrixClone.setItem(i, craftMatrix.getItem(slotIndex));
               }

               for (int i = 0; i < size; i++) {
                  if (!this.ignoresSlotId(i)) {
                     int slotIndex = recipeSlots.get(this.rotateSlotId(i, counterClockwise)).getContainerSlot();
                     craftMatrix.setItem(slotIndex, matrixClone.getItem(i));
                  }
               }

               storageContainer.broadcastChanges();
            }
         });
      }
   }

   private static Optional<Container> getCraftMatrix(StorageContainerMenuBase<?> container) {
      return getOpenCraftingContainer(container).map(ICraftingContainer::getCraftMatrix);
   }

   public boolean requiresServerSide() {
      return true;
   }

   private static Optional<ICraftingContainer> getOpenCraftingContainer(StorageContainerMenuBase<?> container) {
      return container.getOpenContainer().flatMap(c -> c instanceof ICraftingContainer craftingContainer ? Optional.of(craftingContainer) : Optional.empty());
   }

   private static int getCraftingGridStart(StorageContainerMenuBase<?> container) {
      return getOpenCraftingContainer(container).map(cc -> {
         List<Slot> recipeSlots = cc.getRecipeSlots();
         return !recipeSlots.isEmpty() ? container.slots.indexOf(recipeSlots.get(0)) : 0;
      }).orElse(0);
   }

   private static int getCraftingGridSize(StorageContainerMenuBase<?> container) {
      return getOpenCraftingContainer(container).map(cc -> cc.getRecipeSlots().size()).orElse(0);
   }

   private static List<Slot> getRecipeSlots(StorageContainerMenuBase<?> container) {
      return getOpenCraftingContainer(container).map(ICraftingContainer::getRecipeSlots).orElse(List.of());
   }

   private static class StorageCraftingGridBalanceHandler implements GridBalanceHandler<AbstractContainerMenu> {
      public void balanceGrid(CraftingGrid grid, Player player, AbstractContainerMenu menu) {
         if (menu instanceof StorageContainerMenuBase<?> storageContainer) {
            CraftingUpgradeTweakProvider.getCraftMatrix(storageContainer).ifPresent(craftMatrix -> {
               ArrayListMultimap<String, Integer> itemMap = ArrayListMultimap.create();
               Multiset<String> itemCount = HashMultiset.create();

               for (Slot recipeSlot : CraftingUpgradeTweakProvider.getRecipeSlots(storageContainer)) {
                  int slotIndex = recipeSlot.getContainerSlot();
                  ItemStack itemStack = craftMatrix.getItem(slotIndex);
                  if (!itemStack.isEmpty() && itemStack.getMaxStackSize() > 1) {
                     ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
                     String key = Objects.toString(registryName);
                     if (!itemStack.getComponentsPatch().isEmpty()) {
                        key = key + "@" + itemStack.getComponentsPatch();
                     }

                     itemMap.put(key, slotIndex);
                     itemCount.add(key, itemStack.getCount());
                  }
               }

               for (String key : itemMap.keySet()) {
                  List<Integer> balanceList = itemMap.get(key);
                  int totalCount = itemCount.count(key);
                  int countPerStack = totalCount / balanceList.size();
                  int restCount = totalCount % balanceList.size();

                  for (int slotIndex : balanceList) {
                     ItemStack itemStack = craftMatrix.getItem(slotIndex);
                     itemStack.setCount(countPerStack);
                     craftMatrix.setItem(slotIndex, itemStack);
                  }

                  int idx = 0;

                  while (restCount > 0) {
                     int slotIndex = balanceList.get(idx);
                     ItemStack itemStack = craftMatrix.getItem(slotIndex);
                     if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                        itemStack.grow(1);
                        craftMatrix.setItem(slotIndex, itemStack);
                        restCount--;
                     }

                     if (++idx >= balanceList.size()) {
                        idx = 0;
                     }
                  }
               }

               menu.broadcastChanges();
            });
         }
      }

      public void spreadGrid(CraftingGrid grid, Player player, AbstractContainerMenu menu) {
         if (menu instanceof StorageContainerMenuBase<?> storageContainer) {
            CraftingUpgradeTweakProvider.getCraftMatrix(storageContainer).ifPresent(craftMatrix -> {
               boolean emptyBiggestSlot;
               do {
                  ItemStack biggestSlotStack = null;
                  int biggestSlotSize = 1;
                  int biggestSlotIndex = -1;

                  for (Slot recipeSlot : CraftingUpgradeTweakProvider.getRecipeSlots(storageContainer)) {
                     int slotIndex = recipeSlot.getContainerSlot();
                     ItemStack itemStack = craftMatrix.getItem(slotIndex);
                     if (!itemStack.isEmpty() && itemStack.getCount() > biggestSlotSize) {
                        biggestSlotStack = itemStack;
                        biggestSlotSize = itemStack.getCount();
                        biggestSlotIndex = slotIndex;
                     }
                  }

                  if (biggestSlotStack == null) {
                     return;
                  }

                  emptyBiggestSlot = false;

                  for (Slot recipeSlot : CraftingUpgradeTweakProvider.getRecipeSlots(storageContainer)) {
                     int slotIndex = recipeSlot.getContainerSlot();
                     ItemStack itemStack = craftMatrix.getItem(slotIndex);
                     if (itemStack.isEmpty()) {
                        if (biggestSlotStack.getCount() > 1) {
                           craftMatrix.setItem(slotIndex, biggestSlotStack.split(1));
                           craftMatrix.setItem(biggestSlotIndex, biggestSlotStack);
                        } else {
                           emptyBiggestSlot = true;
                        }
                     }
                  }
               } while (emptyBiggestSlot);

               this.balanceGrid(grid, player, menu);
            });
         }
      }
   }

   private static class StorageCraftingGridTransferHandler implements GridTransferHandler<AbstractContainerMenu> {
      public ItemStack putIntoGrid(CraftingGrid craftingGrid, Player player, AbstractContainerMenu menu, int slotId, ItemStack itemStack) {
         return menu instanceof StorageContainerMenuBase<?> storageContainer
            ? CraftingUpgradeTweakProvider.getCraftMatrix(storageContainer).map(craftMatrix -> {
               ItemStack craftStack = craftMatrix.getItem(slotId);
               if (!craftStack.isEmpty()) {
                  if (ItemStack.isSameItemSameComponents(craftStack, itemStack)) {
                     int spaceLeft = Math.min(craftMatrix.getMaxStackSize(), craftStack.getMaxStackSize()) - craftStack.getCount();
                     if (spaceLeft > 0) {
                        ItemStack splitStack = itemStack.split(Math.min(spaceLeft, itemStack.getCount()));
                        craftStack.grow(splitStack.getCount());
                        if (itemStack.getCount() <= 0) {
                           return ItemStack.EMPTY;
                        }
                     }
                  }
               } else {
                  ItemStack transferStack = itemStack.split(Math.min(itemStack.getCount(), craftMatrix.getMaxStackSize()));
                  craftMatrix.setItem(slotId, transferStack);
               }

               return itemStack.getCount() <= 0 ? ItemStack.EMPTY : itemStack;
            }).orElse(itemStack)
            : itemStack;
      }

      public boolean transferIntoGrid(CraftingGrid craftingGrid, Player player, AbstractContainerMenu menu, Slot fromSlot) {
         return menu instanceof StorageContainerMenuBase<?> storageContainer
            ? CraftingUpgradeTweakProvider.getCraftMatrix(storageContainer).map(craftMatrix -> {
               int start = CraftingUpgradeTweakProvider.getCraftingGridStart(storageContainer);
               int size = CraftingUpgradeTweakProvider.getCraftingGridSize(storageContainer);
               ItemStack itemStack = fromSlot.getItem();
               if (itemStack.isEmpty()) {
                  return false;
               }

               int firstEmptySlot = -1;

               for (int i = start; i < start + size; i++) {
                  int slotIndex = menu.getSlot(i).getContainerSlot();
                  ItemStack craftStack = craftMatrix.getItem(slotIndex);
                  if (!craftStack.isEmpty()) {
                     if (ItemStack.isSameItemSameComponents(craftStack, itemStack)) {
                        int spaceLeft = Math.min(craftMatrix.getMaxStackSize(), craftStack.getMaxStackSize()) - craftStack.getCount();
                        if (spaceLeft > 0) {
                           ItemStack splitStack = itemStack.split(Math.min(spaceLeft, itemStack.getCount()));
                           craftStack.grow(splitStack.getCount());
                           if (itemStack.getCount() <= 0) {
                              return true;
                           }
                        }
                     }
                  } else if (firstEmptySlot == -1) {
                     firstEmptySlot = slotIndex;
                  }
               }

               if (itemStack.getCount() > 0 && firstEmptySlot != -1) {
                  ItemStack transferStack = itemStack.split(Math.min(itemStack.getCount(), craftMatrix.getMaxStackSize()));
                  craftMatrix.setItem(firstEmptySlot, transferStack);
                  return true;
               } else {
                  return false;
               }
            }).orElse(false)
            : false;
      }

      public boolean canTransferFrom(Player player, AbstractContainerMenu menu, Slot sourceSlot, CraftingGrid craftingGrid) {
         return !(menu instanceof StorageContainerMenuBase<?> storageContainer)
            ? false
            : sourceSlot.mayPickup(player) && sourceSlot.index < storageContainer.realInventorySlots.size();
      }
   }
}

package net.p3pp3rf1y.sophisticatedcore.controller;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.IInsertBlockOverride;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleExtractor;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;
import org.apache.logging.log4j.util.Supplier;

public abstract class ControllerBlockEntityBase extends BlockEntity implements IItemHandlerSimpleInserter, IInsertBlockOverride {
   private static final long INVALID_SLOT_LOG_INTERVAL_TICKS = 20L;
   private static final int INVALID_SLOT_REFRESH_THRESHOLD = 2;
   private static final long INVALID_SLOT_REFRESH_COOLDOWN_TICKS = 40L;
   private List<BlockPos> storagePositions = new ArrayList<>();
   private final Map<BlockPos, Integer> storagePositionIndexes = new HashMap<>();
   private List<Integer> baseIndexes = new ArrayList<>();
   private int totalSlots = 0;
   protected final Map<ItemStackKey, Set<BlockPos>> stackStorages = new HashMap<>();
   private final Map<BlockPos, Set<ItemStackKey>> storageStacks = new HashMap<>();
   protected final Map<Item, Set<ItemStackKey>> itemStackKeys = new HashMap<>();
   private final Comparator<BlockPos> distanceComparator = Comparator.<BlockPos>comparingDouble(p -> p.distSqr(this.getBlockPos()))
      .thenComparing(Comparator.naturalOrder());
   protected final Set<BlockPos> emptySlotsStorages = new TreeSet<>(this.distanceComparator);
   protected final Map<Item, Set<BlockPos>> memorizedItemStorages = new HashMap<>();
   private final Map<BlockPos, Set<Item>> storageMemorizedItems = new HashMap<>();
   protected final Map<Integer, Set<BlockPos>> memorizedStackStorages = new HashMap<>();
   private final Map<BlockPos, Set<Integer>> storageMemorizedStacks = new HashMap<>();
   protected final Map<Item, Set<BlockPos>> filterItemStorages = new HashMap<>();
   private final Map<BlockPos, Set<Item>> storageFilterItems = new HashMap<>();
   private Set<BlockPos> linkedBlocks = new TreeSet<>(this.distanceComparator);
   private Set<BlockPos> connectingBlocks = new TreeSet<>(this.distanceComparator);
   private Set<BlockPos> nonConnectingBlocks = new TreeSet<>(this.distanceComparator);
   private WeakReference<IItemHandlerModifiable>[] cachedHandlers = new WeakReference[0];
   private long lastInvalidSlotLogTime = -20L;
   private long lastInvalidSlotRefreshTime = -40L;
   private int invalidSlotIncidentCount = 0;
   private boolean refreshingAfterInvalidSlots = false;

   public boolean addLinkedBlock(BlockPos linkedPos) {
      if (this.level != null
         && !this.level.isClientSide()
         && this.isWithinRange(linkedPos)
         && !this.linkedBlocks.contains(linkedPos)
         && !this.storagePositions.contains(linkedPos)) {
         this.linkedBlocks.add(linkedPos);
         this.setChanged();
         WorldHelper.getBlockEntity(this.level, linkedPos, ILinkable.class).ifPresent(l -> {
            if (l.connectLinkedSelf()) {
               Set<BlockPos> positionsToCheck = new LinkedHashSet<>();
               positionsToCheck.add(linkedPos);
               this.searchAndAddBoundables(positionsToCheck, true);
            }

            this.searchAndAddBoundables(new LinkedHashSet<>(l.getConnectablePositions()), false);
         });
         WorldHelper.notifyBlockUpdate(this);
         return true;
      } else {
         return false;
      }
   }

   public void removeLinkedBlock(BlockPos storageBlockPos) {
      this.linkedBlocks.remove(storageBlockPos);
      this.setChanged();
      this.verifyStoragesConnected();
      WorldHelper.notifyBlockUpdate(this);
   }

   public void onLoad() {
      super.onLoad();
      if (this.level != null && !this.level.isClientSide()) {
         this.stackStorages.clear();
         this.storageStacks.clear();
         this.itemStackKeys.clear();
         this.emptySlotsStorages.clear();
         this.storagePositions.forEach(this::addStorageStacksAndRegisterListeners);
      }
   }

   public boolean isStorageConnected(BlockPos storagePos) {
      return this.storagePositions.contains(storagePos);
   }

   public void searchAndAddBoundables() {
      Set<BlockPos> positionsToCheck = new HashSet<>();

      for (Direction dir : Direction.values()) {
         positionsToCheck.add(this.getBlockPos().offset(dir.getNormal()));
      }

      this.searchAndAddBoundables(positionsToCheck, false);
   }

   public void changeSlots(BlockPos storagePos, int newSlots, boolean hasEmptySlots) {
      this.updateBaseIndexesAndTotalSlots(storagePos, newSlots);
      this.updateEmptySlots(storagePos, hasEmptySlots);
   }

   public void updateEmptySlots(BlockPos storagePos, boolean hasEmptySlots) {
      if (this.emptySlotsStorages.contains(storagePos) && !hasEmptySlots) {
         this.emptySlotsStorages.remove(storagePos);
      } else if (!this.emptySlotsStorages.contains(storagePos) && hasEmptySlots) {
         this.emptySlotsStorages.add(storagePos);
      }
   }

   private void updateBaseIndexesAndTotalSlots(BlockPos storagePos, int newSlots) {
      int index = this.storagePositions.indexOf(storagePos);
      int originalSlots = this.getStorageSlots(index);
      int diff = newSlots - originalSlots;

      for (int i = index; i < this.baseIndexes.size(); i++) {
         this.baseIndexes.set(i, this.baseIndexes.get(i) + diff);
      }

      this.totalSlots += diff;
      WorldHelper.notifyBlockUpdate(this);
   }

   private int getStorageSlots(int index) {
      int previousBaseIndex = index == 0 ? 0 : this.baseIndexes.get(index - 1);
      return this.baseIndexes.get(index) - previousBaseIndex;
   }

   public int getSlots(int storageIndex) {
      return storageIndex >= 0 && storageIndex < this.baseIndexes.size() ? this.getStorageSlots(storageIndex) : 0;
   }

   private void searchAndAddBoundables(Set<BlockPos> positionsToCheck, boolean addingLinkedSelf) {
      Set<BlockPos> positionsChecked = new HashSet<>();

      for (boolean first = true; !positionsToCheck.isEmpty(); first = false) {
         Iterator<BlockPos> it = positionsToCheck.iterator();
         BlockPos posToCheck = it.next();
         it.remove();
         boolean finalFirst = first;
         WorldHelper.getLoadedBlockEntity(this.level, posToCheck, IControllerBoundable.class)
            .ifPresentOrElse(
               boundable -> this.tryToConnectStorageAndAddPositionsToCheckAround(
                  positionsToCheck, addingLinkedSelf, positionsChecked, posToCheck, finalFirst, boundable
               ),
               () -> positionsChecked.add(posToCheck)
            );
      }
   }

   private void tryToConnectStorageAndAddPositionsToCheckAround(
      Set<BlockPos> positionsToCheck,
      boolean addingLinkedSelf,
      Set<BlockPos> positionsChecked,
      BlockPos posToCheck,
      boolean finalFirst,
      IControllerBoundable boundable
   ) {
      if (boundable.canBeConnected() || addingLinkedSelf && finalFirst) {
         if (boundable instanceof ILinkable linkable && linkable.isLinked() && (!addingLinkedSelf || !finalFirst)) {
            this.linkedBlocks.remove(posToCheck);
            linkable.setNotLinked();
            this.clearCachedHandlers();
         } else if (boundable instanceof IControllableStorage storage && storage.hasStorageData()) {
            this.addStorageData(posToCheck);
         } else {
            if (boundable.canConnectStorages()) {
               this.connectingBlocks.add(posToCheck);
            } else {
               this.nonConnectingBlocks.add(posToCheck);
            }

            boundable.registerController(this);
         }

         if (boundable.canConnectStorages()) {
            this.addUncheckedPositionsAround(positionsToCheck, positionsChecked, posToCheck);
         }
      }
   }

   private void clearCachedHandlers() {
      this.cachedHandlers = new WeakReference[this.storagePositions.size()];
   }

   public void clearCachedHandler(BlockPos storagePos) {
      Integer index = this.storagePositionIndexes.get(storagePos);
      if (index != null && index < this.cachedHandlers.length) {
         this.cachedHandlers[index] = null;
      }
   }

   private void addUncheckedPositionsAround(Set<BlockPos> positionsToCheck, Set<BlockPos> positionsChecked, BlockPos currentPos) {
      for (Direction dir : Direction.values()) {
         BlockPos pos = currentPos.offset(dir.getNormal());
         if (!positionsChecked.contains(pos)
            && (
               !this.storagePositions.contains(pos) && !this.connectingBlocks.contains(pos) && !this.nonConnectingBlocks.contains(pos)
                  || this.linkedBlocks.contains(pos)
            )
            && this.isWithinRange(pos)) {
            positionsToCheck.add(pos);
         }
      }
   }

   private boolean isWithinRange(BlockPos pos) {
      return Math.abs(pos.getX() - this.getBlockPos().getX()) <= this.getSearchRange()
         && Math.abs(pos.getY() - this.getBlockPos().getY()) <= this.getSearchRange()
         && Math.abs(pos.getZ() - this.getBlockPos().getZ()) <= this.getSearchRange();
   }

   protected abstract int getSearchRange();

   public void addStorage(BlockPos storagePos) {
      if (this.storagePositions.contains(storagePos)) {
         if (this.level != null) {
            WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllableStorage.class)
               .ifPresent(storage -> storage.getControllerPos().filter(this.getBlockPos()::equals).ifPresent(pos -> storage.unregisterController()));
         }

         this.removeStorageInventoryData(storagePos);
         this.clearCachedHandlers();
      }

      if (this.isWithinRange(storagePos)) {
         HashSet<BlockPos> positionsToCheck = new LinkedHashSet<>();
         positionsToCheck.add(storagePos);
         this.searchAndAddBoundables(positionsToCheck, false);
      }

      WorldHelper.notifyBlockUpdate(this);
   }

   private void addStorageData(BlockPos storagePos) {
      this.storagePositions.add(storagePos);
      int index = this.storagePositions.size() - 1;
      this.storagePositionIndexes.put(storagePos, index);
      this.totalSlots = this.totalSlots + this.getHandlerFromIndex(index).getSlots();
      this.baseIndexes.add(this.totalSlots);
      this.addStorageStacksAndRegisterListeners(storagePos);
      this.setChanged();
      WorldHelper.notifyBlockUpdate(this);
   }

   public void addStorageStacksAndRegisterListeners(BlockPos storagePos) {
      WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllableStorage.class).ifPresent(storage -> {
         ITrackedContentsItemHandler handler = storage.getStorageWrapper().getInventoryForInputOutput();
         handler.getTrackedStacks().forEach(k -> this.addStorageStack(storagePos, k));
         if (handler.hasEmptySlots()) {
            this.emptySlotsStorages.add(storagePos);
         }

         MemorySettingsCategory memorySettings = storage.getStorageWrapper().getSettingsHandler().getTypeCategory(MemorySettingsCategory.class);
         memorySettings.getFilterItemSlots().keySet().forEach(i -> this.addStorageMemorizedItem(storagePos, i));
         memorySettings.getFilterStackSlots().keySet().forEach(stackHash -> this.addStorageMemorizedStack(storagePos, stackHash));
         this.setStorageFilterItems(storagePos, storage.getStorageWrapper().getInventoryHandler().getFilterItems());
         storage.registerController(this);
      });
   }

   public void addStorageMemorizedItem(BlockPos storagePos, Item item) {
      this.memorizedItemStorages.computeIfAbsent(item, stackKey -> new LinkedHashSet<>()).add(storagePos);
      this.storageMemorizedItems.computeIfAbsent(storagePos, pos -> new HashSet<>()).add(item);
   }

   public void addStorageMemorizedStack(BlockPos storagePos, int stackHash) {
      this.memorizedStackStorages.computeIfAbsent(stackHash, stackKey -> new LinkedHashSet<>()).add(storagePos);
      this.storageMemorizedStacks.computeIfAbsent(storagePos, pos -> new HashSet<>()).add(stackHash);
   }

   public void removeStorageMemorizedItem(BlockPos storagePos, Item item) {
      this.memorizedItemStorages.computeIfPresent(item, (i, positions) -> {
         positions.remove(storagePos);
         return positions;
      });
      if (this.memorizedItemStorages.containsKey(item) && this.memorizedItemStorages.get(item).isEmpty()) {
         this.memorizedItemStorages.remove(item);
      }

      this.storageMemorizedItems.remove(storagePos);
   }

   public void removeStorageMemorizedStack(BlockPos storagePos, int stackHash) {
      this.memorizedStackStorages.computeIfPresent(stackHash, (i, positions) -> {
         positions.remove(storagePos);
         return positions;
      });
      if (this.memorizedStackStorages.containsKey(stackHash) && this.memorizedStackStorages.get(stackHash).isEmpty()) {
         this.memorizedStackStorages.remove(stackHash);
      }

      this.storageMemorizedStacks.remove(storagePos);
   }

   private <T> Optional<T> getWrapperValueFromHolder(BlockPos storagePos, Function<IStorageWrapper, T> valueGetter) {
      return WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllableStorage.class).map(holder -> valueGetter.apply(holder.getStorageWrapper()));
   }

   public void addStorageStack(BlockPos storagePos, ItemStackKey itemStackKey) {
      this.stackStorages.computeIfAbsent(itemStackKey, stackKey -> new LinkedHashSet<>()).add(storagePos);
      this.storageStacks.computeIfAbsent(storagePos, pos -> new HashSet<>()).add(itemStackKey);
      this.itemStackKeys.computeIfAbsent(itemStackKey.getStack().getItem(), item -> new LinkedHashSet<>()).add(itemStackKey);
   }

   public void removeStorageStack(BlockPos storagePos, ItemStackKey stackKey) {
      this.stackStorages.computeIfPresent(stackKey, (sk, positions) -> {
         positions.remove(storagePos);
         return positions;
      });
      if (this.stackStorages.containsKey(stackKey) && this.stackStorages.get(stackKey).isEmpty()) {
         this.stackStorages.remove(stackKey);
         this.itemStackKeys.computeIfPresent(stackKey.getStack().getItem(), (i, stackKeys) -> {
            stackKeys.remove(stackKey);
            return stackKeys;
         });
         if (this.itemStackKeys.containsKey(stackKey.getStack().getItem()) && this.itemStackKeys.get(stackKey.getStack().getItem()).isEmpty()) {
            this.itemStackKeys.remove(stackKey.getStack().getItem());
         }
      }

      this.storageStacks.computeIfPresent(storagePos, (pos, stackKeys) -> {
         stackKeys.remove(stackKey);
         return stackKeys;
      });
      if (this.storageStacks.containsKey(storagePos) && this.storageStacks.get(storagePos).isEmpty()) {
         this.storageStacks.remove(storagePos);
      }
   }

   public void removeStorageStacks(BlockPos storagePos) {
      this.storageStacks.computeIfPresent(storagePos, (pos, stackKeys) -> {
         stackKeys.forEach(stackKey -> {
            Set<BlockPos> storages = this.stackStorages.get(stackKey);
            if (storages != null) {
               storages.remove(storagePos);
               if (storages.isEmpty()) {
                  this.stackStorages.remove(stackKey);
                  this.itemStackKeys.computeIfPresent(stackKey.getStack().getItem(), (i, positions) -> {
                     positions.remove(stackKey);
                     return positions;
                  });
                  if (this.itemStackKeys.containsKey(stackKey.getStack().getItem()) && this.itemStackKeys.get(stackKey.getStack().getItem()).isEmpty()) {
                     this.itemStackKeys.remove(stackKey.getStack().getItem());
                  }
               }
            }
         });
         return stackKeys;
      });
      this.storageStacks.remove(storagePos);
   }

   protected boolean hasItem(Item item) {
      return this.itemStackKeys.containsKey(item);
   }

   protected boolean isMemorizedItem(ItemStack stack) {
      return this.memorizedItemStorages.containsKey(stack.getItem()) || this.memorizedStackStorages.containsKey(ItemStack.hashItemAndComponents(stack));
   }

   protected boolean isFilterItem(Item item) {
      return this.filterItemStorages.containsKey(item);
   }

   public void removeBoundable(BlockPos boundablePos) {
      this.removeConnectingBlock(boundablePos);
      this.verifyStoragesConnected();
   }

   public void removeStorage(BlockPos storagePos) {
      this.removeConnectingBlock(storagePos);
      this.removeStorageInventoryDataAndUnregisterController(storagePos);
      this.verifyStoragesConnected();
   }

   private void removeConnectingBlock(BlockPos storagePos) {
      if (this.connectingBlocks.remove(storagePos)) {
         WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController);
      }
   }

   public void removeNonConnectingBlock(BlockPos storagePos) {
      if (this.nonConnectingBlocks.remove(storagePos)) {
         WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController);
      }
   }

   private void removeStorageInventoryDataAndUnregisterController(BlockPos storagePos) {
      if (this.storagePositions.contains(storagePos)) {
         this.removeStorageInventoryData(storagePos);
         this.linkedBlocks.remove(storagePos);
         WorldHelper.getLoadedBlockEntity(this.level, storagePos, IControllableStorage.class).ifPresent(IControllableStorage::unregisterController);
         this.clearCachedHandlers();
         this.setChanged();
         WorldHelper.notifyBlockUpdate(this);
      }
   }

   private void removeStorageInventoryData(BlockPos storagePos) {
      int idx = this.storagePositions.indexOf(storagePos);
      this.totalSlots = this.totalSlots - this.getStorageSlots(idx);
      this.removeStorageStacks(storagePos);
      this.removeStorageMemorizedItems(storagePos);
      this.removeStorageMemorizedStacks(storagePos);
      this.removeStorageWithEmptySlots(storagePos);
      this.removeStorageFilterItems(storagePos);
      this.storagePositions.remove(idx);
      this.removeStoragePositionIndex(storagePos);
      this.removeBaseIndexAt(idx);
   }

   private void removeStoragePositionIndex(BlockPos storagePos) {
      Integer removedIndex = this.storagePositionIndexes.remove(storagePos);
      if (removedIndex != null) {
         for (Entry<BlockPos, Integer> entry : this.storagePositionIndexes.entrySet()) {
            int index = entry.getValue();
            if (index > removedIndex) {
               entry.setValue(index - 1);
            }
         }
      }
   }

   private void removeStorageFilterItems(BlockPos storagePos) {
      this.storageFilterItems.computeIfPresent(storagePos, (pos, items) -> {
         items.forEach(item -> {
            Set<BlockPos> storages = this.filterItemStorages.get(item);
            if (storages != null) {
               storages.remove(storagePos);
               if (storages.isEmpty()) {
                  this.filterItemStorages.remove(item);
               }
            }
         });
         return items;
      });
      this.storageFilterItems.remove(storagePos);
   }

   private void removeStorageMemorizedItems(BlockPos storagePos) {
      this.storageMemorizedItems.computeIfPresent(storagePos, (pos, items) -> {
         items.forEach(item -> {
            Set<BlockPos> storages = this.memorizedItemStorages.get(item);
            if (storages != null) {
               storages.remove(storagePos);
               if (storages.isEmpty()) {
                  this.memorizedItemStorages.remove(item);
               }
            }
         });
         return items;
      });
      this.storageMemorizedItems.remove(storagePos);
   }

   private void removeStorageMemorizedStacks(BlockPos storagePos) {
      this.storageMemorizedStacks.computeIfPresent(storagePos, (pos, items) -> {
         items.forEach(stackHash -> {
            Set<BlockPos> storages = this.memorizedStackStorages.get(stackHash);
            if (storages != null) {
               storages.remove(storagePos);
               if (storages.isEmpty()) {
                  this.memorizedStackStorages.remove(stackHash);
               }
            }
         });
         return items;
      });
      this.storageMemorizedStacks.remove(storagePos);
   }

   private void verifyStoragesConnected() {
      HashSet<BlockPos> toVerify = new HashSet<>(this.storagePositions);
      toVerify.addAll(this.connectingBlocks);
      toVerify.addAll(this.nonConnectingBlocks);
      Set<BlockPos> positionsToCheck = new HashSet<>();

      for (Direction dir : Direction.values()) {
         BlockPos offsetPos = this.getBlockPos().offset(dir.getNormal());
         if (toVerify.contains(offsetPos)) {
            positionsToCheck.add(offsetPos);
         }
      }

      Set<BlockPos> positionsChecked = new HashSet<>();
      this.verifyConnected(toVerify, positionsToCheck, positionsChecked);
      this.linkedBlocks.forEach(linkedPosition -> WorldHelper.getBlockEntity(this.getLevel(), linkedPosition, ILinkable.class).ifPresent(l -> {
         if (l.connectLinkedSelf() && toVerify.contains(linkedPosition)) {
            positionsToCheck.add(linkedPosition);
         }

         l.getConnectablePositions().forEach(p -> {
            if (toVerify.contains(p)) {
               positionsToCheck.add(p);
            }
         });
      }));
      this.verifyConnected(toVerify, positionsToCheck, positionsChecked);
      toVerify.forEach(storagePos -> {
         this.removeConnectingBlock(storagePos);
         this.removeNonConnectingBlock(storagePos);
         this.removeStorageInventoryDataAndUnregisterController(storagePos);
      });
      this.clearCachedHandlers();
   }

   private void verifyConnected(HashSet<BlockPos> toVerify, Set<BlockPos> positionsToCheck, Set<BlockPos> positionsChecked) {
      while (!positionsToCheck.isEmpty()) {
         Iterator<BlockPos> it = positionsToCheck.iterator();
         BlockPos posToCheck = it.next();
         it.remove();
         positionsChecked.add(posToCheck);
         WorldHelper.getLoadedBlockEntity(this.level, posToCheck, IControllerBoundable.class).ifPresent(h -> {
            toVerify.remove(posToCheck);
            if (h.canConnectStorages()) {
               for (Direction dir : Direction.values()) {
                  BlockPos pos = posToCheck.offset(dir.getNormal());
                  if (!positionsChecked.contains(pos) && toVerify.contains(pos)) {
                     positionsToCheck.add(pos);
                  }
               }
            }
         });
      }
   }

   private void removeBaseIndexAt(int idx) {
      if (idx < this.baseIndexes.size()) {
         int slotsRemoved = this.getStorageSlots(idx);
         this.baseIndexes.remove(idx);

         for (int i = idx; i < this.baseIndexes.size(); i++) {
            this.baseIndexes.set(i, this.baseIndexes.get(i) - slotsRemoved);
         }
      }
   }

   protected ControllerBlockEntityBase(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
      super(blockEntityType, pos, state);
   }

   public int getSlots() {
      return this.totalSlots;
   }

   private int getIndexForSlot(int slot) {
      if (slot < 0) {
         return -1;
      }

      for (int i = 0; i < this.baseIndexes.size(); i++) {
         if (slot - this.baseIndexes.get(i) < 0) {
            return i;
         }
      }

      return -1;
   }

   protected IItemHandlerModifiable getHandlerFromIndex(int index) {
      if (index >= 0 && index < this.storagePositions.size()) {
         if (index >= this.cachedHandlers.length) {
            this.cachedHandlers = Arrays.copyOf(this.cachedHandlers, index + 1);
         }

         if (this.cachedHandlers[index] != null) {
            IItemHandlerModifiable handler = this.cachedHandlers[index].get();
            if (handler != null) {
               return handler;
            }
         }

         IItemHandlerModifiable handler = this.<IItemHandlerModifiable>getWrapperValueFromHolder(
               this.storagePositions.get(index), wrapper -> wrapper.getInventoryForInputOutput()
            )
            .orElse((IItemHandlerModifiable)EmptyItemHandler.INSTANCE);
         this.cachedHandlers[index] = new WeakReference<>(handler);
         return handler;
      } else {
         return (IItemHandlerModifiable)EmptyItemHandler.INSTANCE;
      }
   }

   protected int getSlotFromIndex(int slot, int index) {
      return index > 0 && index < this.baseIndexes.size() ? slot - this.baseIndexes.get(index - 1) : slot;
   }

   public ItemStack getStackInSlot(int slot) {
      if (this.isSlotIndexInvalid(slot)) {
         return ItemStack.EMPTY;
      }

      int handlerIndex = this.getIndexForSlot(slot);
      IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
      slot = this.getSlotFromIndex(slot, handlerIndex);
      return this.validateHandlerSlotIndex(handler, handlerIndex, slot, "getStackInSlot") ? handler.getStackInSlot(slot) : ItemStack.EMPTY;
   }

   private boolean isSlotIndexInvalid(int slot) {
      return slot < 0 || slot >= this.totalSlots;
   }

   private boolean validateHandlerSlotIndex(IItemHandler handler, int handlerIndex, int slot, String methodName) {
      if (slot >= 0 && slot < handler.getSlots()) {
         return true;
      }

      this.handleInvalidSlotAccess(handlerIndex, slot, methodName);
      return false;
   }

   private void handleInvalidSlotAccess(int handlerIndex, int slot, String methodName) {
      if (this.level != null) {
         long gameTime = this.level.getGameTime();
         if (gameTime - this.lastInvalidSlotLogTime >= 20L) {
            this.lastInvalidSlotLogTime = gameTime;
            this.invalidSlotIncidentCount++;
            if (handlerIndex >= 0 && handlerIndex < this.storagePositions.size()) {
               SophisticatedCore.LOGGER
                  .debug(
                     "Invalid slot {} passed into controller's {} method for storage at {}. If you see many of these messages try replacing controller at {}",
                     new Supplier[]{
                        () -> slot, () -> methodName, () -> this.storagePositions.get(handlerIndex).toShortString(), () -> this.getBlockPos().toShortString()
                     }
                  );
            } else {
               SophisticatedCore.LOGGER
                  .debug(
                     "Invalid handler index calculated {} in controller's {} method. If you see many of these messages try replacing controller at {}",
                     new Supplier[]{() -> handlerIndex, () -> methodName, () -> this.getBlockPos().toShortString()}
                  );
            }

            if (!this.refreshingAfterInvalidSlots && this.invalidSlotIncidentCount >= 2 && gameTime - this.lastInvalidSlotRefreshTime >= 40L) {
               this.lastInvalidSlotRefreshTime = gameTime;
               this.refreshingAfterInvalidSlots = true;
               SophisticatedCore.LOGGER
                  .debug(
                     "Refreshing controller at {} after {} invalid slot incidents were logged",
                     new Supplier[]{() -> this.getBlockPos().toShortString(), () -> this.invalidSlotIncidentCount}
                  );
               this.refreshConnectedStoragesAfterRepeatedInvalidSlots();
               this.invalidSlotIncidentCount = 0;
               this.refreshingAfterInvalidSlots = false;
            }
         }
      }
   }

   private void refreshConnectedStoragesAfterRepeatedInvalidSlots() {
      this.unregisterCurrentConnections();
      this.clearControllerStateForRefresh();
      this.searchAndAddBoundables();
      this.rebuildLinkedBlockConnections();
      this.setChanged();
      WorldHelper.notifyBlockUpdate(this);
   }

   private void unregisterCurrentConnections() {
      this.storagePositions
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllableStorage.class).ifPresent(IControllableStorage::unregisterController));
      this.connectingBlocks
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController));
      this.nonConnectingBlocks
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController));
   }

   private void clearControllerStateForRefresh() {
      this.storagePositions.clear();
      this.storagePositionIndexes.clear();
      this.baseIndexes.clear();
      this.totalSlots = 0;
      this.stackStorages.clear();
      this.storageStacks.clear();
      this.itemStackKeys.clear();
      this.emptySlotsStorages.clear();
      this.memorizedItemStorages.clear();
      this.storageMemorizedItems.clear();
      this.memorizedStackStorages.clear();
      this.storageMemorizedStacks.clear();
      this.filterItemStorages.clear();
      this.storageFilterItems.clear();
      this.connectingBlocks.clear();
      this.nonConnectingBlocks.clear();
      this.cachedHandlers = new WeakReference[0];
   }

   private void rebuildLinkedBlockConnections() {
      for (BlockPos linkedPos : new ArrayList<>(this.linkedBlocks)) {
         WorldHelper.getBlockEntity(this.level, linkedPos, ILinkable.class).ifPresent(l -> {
            if (l.connectLinkedSelf()) {
               Set<BlockPos> positionsToCheck = new LinkedHashSet<>();
               positionsToCheck.add(linkedPos);
               this.searchAndAddBoundables(positionsToCheck, true);
            }

            this.searchAndAddBoundables(new LinkedHashSet<>(l.getConnectablePositions()), false);
         });
      }
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (this.isSlotIndexInvalid(slot)) {
         return stack;
      }

      if (simulate) {
         int handlerIndex = this.getIndexForSlot(slot);
         IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
         slot = this.getSlotFromIndex(slot, handlerIndex);
         if (this.validateHandlerSlotIndex(handler, handlerIndex, slot, "insertItem")) {
            return handler.insertItem(slot, stack, true);
         }
      }

      return this.insertItem(stack, simulate, true);
   }

   @Override
   public ItemStack insertItem(ItemStack stack, boolean simulate) {
      return this.insertItem(stack, simulate, true);
   }

   protected ItemStack insertItem(ItemStack stack, boolean simulate, boolean insertIntoAnyEmpty) {
      ItemStackKey stackKey = ItemStackKey.of(stack);
      ItemStack remaining = stack;
      remaining = this.insertIntoStoragesThatMatchStack(remaining, stackKey, simulate);
      if (remaining.isEmpty()) {
         return remaining;
      }

      int stackHash = stackKey.hashCode();
      if (this.memorizedStackStorages.containsKey(stackHash)) {
         remaining = this.insertIntoStorages(this.memorizedStackStorages.get(stackHash), remaining, simulate, false);
         if (remaining.isEmpty()) {
            return remaining;
         }
      }

      remaining = this.insertIntoStoragesThatMatchItem(remaining, simulate);
      if (remaining.isEmpty()) {
         return remaining;
      }

      if (this.memorizedItemStorages.containsKey(stack.getItem())) {
         remaining = this.insertIntoStorages(this.memorizedItemStorages.get(stack.getItem()), remaining, simulate, false);
         if (remaining.isEmpty()) {
            return remaining;
         }
      }

      if (this.filterItemStorages.containsKey(stack.getItem())) {
         remaining = this.insertIntoStorages(this.filterItemStorages.get(stack.getItem()), remaining, simulate, false);
         if (remaining.isEmpty()) {
            return remaining;
         }
      }

      return insertIntoAnyEmpty ? this.insertIntoStorages(this.emptySlotsStorages, remaining, simulate, false) : remaining;
   }

   private ItemStack insertIntoStoragesThatMatchStack(ItemStack remaining, ItemStackKey stackKey, boolean simulate) {
      if (this.stackStorages.containsKey(stackKey)) {
         Set<BlockPos> positions = this.stackStorages.get(stackKey);
         remaining = this.insertIntoStorages(positions, remaining, simulate, false);
      }

      return remaining;
   }

   private ItemStack insertIntoStoragesThatMatchItem(ItemStack remaining, boolean simulate) {
      if (!this.emptySlotsStorages.isEmpty() && this.itemStackKeys.containsKey(remaining.getItem())) {
         Set<ItemStackKey> matchingStackKeys = this.itemStackKeys.get(remaining.getItem());
         if (remaining.getCount() > remaining.getMaxStackSize()) {
            matchingStackKeys = new LinkedHashSet<>(matchingStackKeys);
         }

         for (ItemStackKey key : matchingStackKeys) {
            if (this.stackStorages.containsKey(key)) {
               Set<BlockPos> positions = this.stackStorages.get(key);
               remaining = this.insertIntoStorages(positions, remaining, simulate, true);
               if (remaining.isEmpty()) {
                  return ItemStack.EMPTY;
               }
            }
         }
      }

      return remaining;
   }

   private ItemStack insertIntoStorages(Set<BlockPos> positions, ItemStack stack, boolean simulate, boolean checkHasEmptySlotFirst) {
      ItemStack remaining = stack;

      for (BlockPos storagePos : new LinkedHashSet<>(positions)) {
         if (!checkHasEmptySlotFirst || this.emptySlotsStorages.contains(storagePos)) {
            remaining = this.insertIntoStorage(storagePos, remaining, simulate);
            if (remaining.isEmpty()) {
               return ItemStack.EMPTY;
            }
         }
      }

      return remaining;
   }

   protected ItemStack insertIntoStorage(BlockPos storagePos, ItemStack remaining, boolean simulate) {
      Integer idx = this.storagePositionIndexes.get(storagePos);
      if (idx == null) {
         return remaining;
      } else {
         return this.getHandlerFromIndex(idx) instanceof IItemHandlerSimpleInserter simpleInserter ? simpleInserter.insertItem(remaining, simulate) : remaining;
      }
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (this.isSlotIndexInvalid(slot)) {
         return ItemStack.EMPTY;
      }

      int handlerIndex = this.getIndexForSlot(slot);
      IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
      slot = this.getSlotFromIndex(slot, handlerIndex);
      return this.validateHandlerSlotIndex(handler, handlerIndex, slot, "extractItem(int slot, int amount, boolean simulate)")
         ? handler.extractItem(slot, amount, simulate)
         : ItemStack.EMPTY;
   }

   public ItemStack extractItem(ItemStack stack, boolean simulate) {
      ItemStackKey stackKey = ItemStackKey.of(stack);
      if (!this.stackStorages.containsKey(stackKey)) {
         return ItemStack.EMPTY;
      }

      Set<BlockPos> positionsCopy = new LinkedHashSet<>(this.stackStorages.get(stackKey));
      ItemStack remaining = stack;

      for (BlockPos storagePos : positionsCopy) {
         Integer idx = this.storagePositionIndexes.get(storagePos);
         if (idx != null && this.getHandlerFromIndex(idx) instanceof IItemHandlerSimpleExtractor simpleExtractor) {
            ItemStack extracted = simpleExtractor.extractItem(stack, simulate);
            if (extracted.getCount() > 0) {
               remaining = stack.copyWithCount(remaining.getCount() - extracted.getCount());
            }

            if (remaining.isEmpty()) {
               break;
            }
         }
      }

      return stack.getCount() == remaining.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - remaining.getCount());
   }

   public int getSlotLimit(int slot) {
      if (this.isSlotIndexInvalid(slot)) {
         return 0;
      }

      int handlerIndex = this.getIndexForSlot(slot);
      IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
      int localSlot = this.getSlotFromIndex(slot, handlerIndex);
      return this.validateHandlerSlotIndex(handler, handlerIndex, localSlot, "getSlotLimit(int slot)") ? handler.getSlotLimit(localSlot) : 0;
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      if (this.isSlotIndexInvalid(slot)) {
         return false;
      }

      int handlerIndex = this.getIndexForSlot(slot);
      IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
      int localSlot = this.getSlotFromIndex(slot, handlerIndex);
      return this.validateHandlerSlotIndex(handler, handlerIndex, localSlot, "isItemValid(int slot, ItemStack stack)")
         ? handler.isItemValid(localSlot, stack)
         : false;
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      if (!this.isSlotIndexInvalid(slot)) {
         int handlerIndex = this.getIndexForSlot(slot);
         IItemHandlerModifiable handler = this.getHandlerFromIndex(handlerIndex);
         slot = this.getSlotFromIndex(slot, handlerIndex);
         if (this.validateHandlerSlotIndex(handler, handlerIndex, slot, "setStackInSlot(int slot, ItemStack stack)")) {
            handler.setStackInSlot(slot, stack);
         }
      }
   }

   public void onChunkUnloaded() {
      super.onChunkUnloaded();
      this.detachFromStoragesAndUnlinkBlocks();
   }

   public void detachFromStoragesAndUnlinkBlocks() {
      this.storagePositions
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllableStorage.class).ifPresent(IControllableStorage::unregisterController));
      this.connectingBlocks
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController));
      this.nonConnectingBlocks
         .forEach(pos -> WorldHelper.getLoadedBlockEntity(this.level, pos, IControllerBoundable.class).ifPresent(IControllerBoundable::unregisterController));
      new HashSet<>(this.linkedBlocks)
         .forEach(linkedPos -> WorldHelper.getLoadedBlockEntity(this.level, linkedPos, ILinkable.class).ifPresent(ILinkable::unlinkFromController));
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      this.saveData(tag);
   }

   private CompoundTag saveData(CompoundTag tag) {
      NBTHelper.putList(tag, "storagePositions", this.storagePositions, p -> LongTag.valueOf(p.asLong()));
      NBTHelper.putList(tag, "connectingBlocks", this.connectingBlocks, p -> LongTag.valueOf(p.asLong()));
      NBTHelper.putList(tag, "nonConnectingBlocks", this.nonConnectingBlocks, p -> LongTag.valueOf(p.asLong()));
      NBTHelper.putList(tag, "linkedBlocks", this.linkedBlocks, p -> LongTag.valueOf(p.asLong()));
      NBTHelper.putList(tag, "baseIndexes", this.baseIndexes, IntTag::valueOf);
      tag.putInt("totalSlots", this.totalSlots);
      return tag;
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.storagePositions = (List<BlockPos>)NBTHelper.<BlockPos, ArrayList<E>>getCollection(
            tag, "storagePositions", (byte)4, t -> Optional.of(BlockPos.of(((LongTag)t).getAsLong())), ArrayList::new
         )
         .orElseGet(ArrayList::new);
      this.setupStoragePositionIndexes();
      this.connectingBlocks = (Set<BlockPos>)NBTHelper.<BlockPos, LinkedHashSet<E>>getCollection(
            tag, "connectingBlocks", (byte)4, t -> Optional.of(BlockPos.of(((LongTag)t).getAsLong())), LinkedHashSet::new
         )
         .orElseGet(LinkedHashSet::new);
      this.nonConnectingBlocks = (Set<BlockPos>)NBTHelper.<BlockPos, LinkedHashSet<E>>getCollection(
            tag, "nonConnectingBlocks", (byte)4, t -> Optional.of(BlockPos.of(((LongTag)t).getAsLong())), LinkedHashSet::new
         )
         .orElseGet(LinkedHashSet::new);
      this.baseIndexes = (List<Integer>)NBTHelper.<Integer, ArrayList<E>>getCollection(
            tag, "baseIndexes", (byte)3, t -> Optional.of(((IntTag)t).getAsInt()), ArrayList::new
         )
         .orElseGet(ArrayList::new);
      this.totalSlots = tag.getInt("totalSlots");
      this.linkedBlocks = (Set<BlockPos>)NBTHelper.<BlockPos, LinkedHashSet<E>>getCollection(
            tag, "linkedBlocks", (byte)4, t -> Optional.of(BlockPos.of(((LongTag)t).getAsLong())), LinkedHashSet::new
         )
         .orElseGet(LinkedHashSet::new);
   }

   private void setupStoragePositionIndexes() {
      this.storagePositionIndexes.clear();

      for (int i = 0; i < this.storagePositions.size(); i++) {
         this.storagePositionIndexes.put(this.storagePositions.get(i), i);
      }
   }

   public CompoundTag getUpdateTag(Provider registries) {
      return this.saveData(super.getUpdateTag(registries));
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void addStorageWithEmptySlots(BlockPos storageBlockPos) {
      this.emptySlotsStorages.add(storageBlockPos);
   }

   public void removeStorageWithEmptySlots(BlockPos storageBlockPos) {
      this.emptySlotsStorages.remove(storageBlockPos);
   }

   public Set<BlockPos> getLinkedBlocks() {
      return this.linkedBlocks;
   }

   public List<BlockPos> getStoragePositions() {
      return this.storagePositions;
   }

   public void setStorageFilterItems(BlockPos storagePos, Set<Item> filterItems) {
      this.removeStorageFilterItems(storagePos);
      if (!filterItems.isEmpty()) {
         for (Item item : filterItems) {
            this.filterItemStorages.computeIfAbsent(item, stackKey -> new LinkedHashSet<>()).add(storagePos);
         }

         this.storageFilterItems.put(storagePos, new LinkedHashSet<>(filterItems));
      }
   }

   public boolean hasMatchingStack(ItemStackKey stackKey) {
      return this.stackStorages.containsKey(stackKey) || this.memorizedStackStorages.containsKey(stackKey.hashCode());
   }

   public boolean hasMatchingItem(Item item) {
      return this.itemStackKeys.containsKey(item) || this.memorizedItemStorages.containsKey(item) || this.filterItemStorages.containsKey(item);
   }

   @Override
   public boolean isInsertBlocked() {
      return this.storagePositions
         .stream()
         .allMatch(pos -> this.<Boolean>getWrapperValueFromHolder(pos, storageWrapper -> storageWrapper.getInventoryHandler().isInsertBlocked()).orElse(true));
   }
}

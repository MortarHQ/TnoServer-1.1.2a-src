package net.p3pp3rf1y.sophisticatedcore.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IExtractResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IInsertResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IOverflowResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.voiding.VoidUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.util.CodecHelper;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.MathHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.SlotValueMap;

public abstract class InventoryHandler extends ItemStackHandler implements ITrackedContentsItemHandler, IInsertBlockOverride {
   public static final String INVENTORY_TAG = "inventory";
   private static final String PARTITIONER_TAG = "partitioner";
   protected final IStorageWrapper storageWrapper;
   private final CompoundTag contentsNbt;
   private final Runnable saveHandler;
   private final List<IntConsumer> onContentsChangedListeners = new ArrayList<>();
   private boolean persistent = true;
   private final Map<Integer, Tag> stackNbts = new LinkedHashMap<>();
   private ISlotTracker slotTracker = new ISlotTracker.Noop();
   private int baseSlotLimit;
   private double maxStackSizeMultiplier;
   private boolean isInitializing;
   private final StackUpgradeConfig stackUpgradeConfig;
   private final InventoryPartitioner inventoryPartitioner;
   private Consumer<Set<Item>> filterItemsChangeListener = s -> {};
   private final SlotValueMap<Item> filterItemSlots = new SlotValueMap<>();
   private BooleanSupplier shouldInsertIntoEmpty = () -> true;
   private boolean voidUpgradeInfoInitialized = false;
   private boolean hasVoidUpgrade = false;

   protected InventoryHandler(
      int numberOfInventorySlots,
      IStorageWrapper storageWrapper,
      CompoundTag contentsNbt,
      Runnable saveHandler,
      int baseSlotLimit,
      StackUpgradeConfig stackUpgradeConfig
   ) {
      super(numberOfInventorySlots);
      this.stackUpgradeConfig = stackUpgradeConfig;
      this.isInitializing = true;
      this.storageWrapper = storageWrapper;
      this.contentsNbt = contentsNbt;
      this.saveHandler = saveHandler;
      this.setBaseSlotLimit(baseSlotLimit);
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> this.deserializeNBT(registryAccess, contentsNbt.getCompound("inventory")));
      this.inventoryPartitioner = new InventoryPartitioner(
         contentsNbt.getCompound("partitioner"), this, () -> storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class)
      );
      this.initStackNbts();
      this.getSlotTracker().refreshSlotIndexesFrom(this);
      this.isInitializing = false;
   }

   public ISlotTracker getSlotTracker() {
      this.initSlotTracker();
      return this.slotTracker;
   }

   public void setSize(int size) {
      super.setSize(this.stacks.size());
   }

   private void initStackNbts() {
      this.stackNbts.clear();

      for (int slot = 0; slot < this.stacks.size(); slot++) {
         ItemStack slotStack = (ItemStack)this.stacks.get(slot);
         if (!slotStack.isEmpty()) {
            this.stackNbts.put(slot, this.getSlotsStackNbt(slot, slotStack));
         }
      }
   }

   public void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      this.inventoryPartitioner.getPartBySlot(slot).onContentsChanged(slot, (x$0, x$1) -> super.setStackInSlot(x$0, x$1));
      if (this.persistent && this.updateSlotNbt(slot)) {
         this.saveInventory();
         this.triggerOnChangeListeners(slot);
      }
   }

   public void triggerOnChangeListeners(int slot) {
      for (IntConsumer onContentsChangedListener : this.onContentsChangedListeners) {
         onContentsChangedListener.accept(slot);
      }
   }

   private boolean updateSlotNbt(int slot) {
      ItemStack slotStack = this.getSlotStack(slot);
      if (slotStack.isEmpty()) {
         if (this.stackNbts.containsKey(slot)) {
            this.stackNbts.remove(slot);
            return true;
         }
      } else {
         Tag itemTag = this.getSlotsStackNbt(slot, slotStack);
         if (!this.stackNbts.containsKey(slot) || !this.stackNbts.get(slot).equals(itemTag)) {
            this.stackNbts.put(slot, itemTag);
            return true;
         }
      }

      return false;
   }

   private Tag getSlotsStackNbt(int slot, ItemStack slotStack) {
      CompoundTag itemTag = new CompoundTag();
      itemTag.putInt("Slot", slot);
      return RegistryHelper.getRegistryAccess()
         .map(
            registryAccess -> (Tag)CodecHelper.OVERSIZED_ITEM_STACK_CODEC
               .encode(slotStack, registryAccess.createSerializationContext(NbtOps.INSTANCE), itemTag)
               .getOrThrow()
         )
         .orElse(itemTag);
   }

   private Optional<ItemStack> getStackFromNbt(int slot, Tag itemTag, RegistryAccess registryAccess) {
      try {
         return CodecHelper.OVERSIZED_ITEM_STACK_CODEC
            .parse(registryAccess.createSerializationContext(NbtOps.INSTANCE), itemTag)
            .resultOrPartial(
               errorMessage -> SophisticatedCore.LOGGER
                  .error(
                     "Failed to deserialize stored item in storage '{}' slot {} - {}. Raw item data: {}", this.getStorageLogName(), slot, errorMessage, itemTag
                  )
            );
      } catch (Exception e) {
         SophisticatedCore.LOGGER
            .error("Error deserializing stored item in storage '{}' slot {}. Raw item data: {}", this.getStorageLogName(), slot, itemTag, e);
         return Optional.empty();
      }
   }

   private String getStorageLogName() {
      String displayName = Optional.ofNullable(this.storageWrapper.getDisplayName()).<String>map(Component::getString).orElse("");
      return !displayName.isEmpty() ? this.storageWrapper.getStorageType() + " (" + displayName + ")" : this.storageWrapper.getStorageType();
   }

   public void deserializeNBT(Provider registries, CompoundTag nbt) {
      this.slotTracker.clear();
      this.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : this.stacks.size());
      ListTag tagList = nbt.getList("Items", 10);
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> {
         for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTag = tagList.getCompound(i);
            int slot = itemTag.getInt("Slot");
            if (slot >= 0 && slot < this.stacks.size()) {
               this.getStackFromNbt(slot, itemTag, registryAccess).ifPresent(stack -> this.stacks.set(slot, stack));
            }
         }
      });
      this.slotTracker.refreshSlotIndexesFrom(this);
      this.onLoad();
   }

   public int getBaseSlotLimit() {
      return this.baseSlotLimit;
   }

   public int getSlotLimit(int slot) {
      return this.inventoryPartitioner.getPartBySlot(slot).getSlotLimit(slot);
   }

   public int getBaseStackLimit(ItemStack stack) {
      if (!this.stackUpgradeConfig.canStackItem(stack.getItem())) {
         return stack.getMaxStackSize();
      }

      int maxStackSize = stack.isEmpty() ? this.getBaseSlotLimit() : stack.getMaxStackSize();
      if (this.baseSlotLimit < 64) {
         return (int)Math.max(1.0, (double)maxStackSize * this.baseSlotLimit / 64.0);
      }

      int limit = MathHelper.intMaxCappedMultiply(maxStackSize, this.baseSlotLimit / 64);
      int remainder = this.baseSlotLimit % 64;
      if (remainder > 0) {
         limit = MathHelper.intMaxCappedAddition(limit, remainder * maxStackSize / 64);
      }

      return limit;
   }

   public int getStackLimit(int slot, ItemStack stack) {
      return this.inventoryPartitioner.getPartBySlot(slot).getStackLimit(slot, stack);
   }

   public Item getFilterItem(int slot) {
      return this.inventoryPartitioner.getPartBySlot(slot).getFilterItem(slot);
   }

   public boolean isFilterItem(Item item) {
      return this.inventoryPartitioner.isFilterItem(item);
   }

   public void setBaseSlotLimit(int baseSlotLimit) {
      this.voidUpgradeInfoInitialized = false;
      this.baseSlotLimit = baseSlotLimit;
      this.maxStackSizeMultiplier = baseSlotLimit / 64.0F;
      if (this.inventoryPartitioner != null) {
         this.inventoryPartitioner.onSlotLimitChange();
      }

      if (!this.isInitializing) {
         this.slotTracker.refreshSlotIndexesFrom(this);
      }
   }

   public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
      if (amount == 0) {
         return ItemStack.EMPTY;
      }

      this.validateSlotIndex(slot);
      ItemStack existing = this.getSlotStack(slot);
      if (existing.isEmpty()) {
         return ItemStack.EMPTY;
      }

      int toExtract = Math.min(amount, existing.getMaxStackSize());
      if (existing.getCount() <= toExtract) {
         if (!simulate) {
            this.setSlotStack(slot, ItemStack.EMPTY);
            this.runOnAfterExtract(slot, this, existing);
            return existing;
         } else {
            return existing.copy();
         }
      } else {
         if (!simulate) {
            this.setSlotStack(slot, existing.copyWithCount(existing.getCount() - toExtract));
            this.runOnAfterExtract(slot, this, existing);
         }

         return existing.copyWithCount(toExtract);
      }
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.inventoryPartitioner.getPartBySlot(slot).extractItem(slot, amount, simulate);
   }

   public void validateSlotIndex(int slot) {
      super.validateSlotIndex(slot);
   }

   public ItemStack getSlotStack(int slot) {
      return (ItemStack)this.stacks.get(slot);
   }

   public void setSlotStack(int slot, ItemStack stack) {
      this.stacks.set(slot, stack);
      this.getSlotTracker().removeAndSetSlotIndexes(this, slot, stack);
      this.onContentsChanged(slot);
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return this.getSlotTracker()
         .insertItemIntoHandler(
            this,
            this::runOnBeforeInsert,
            this::insertItemInternal,
            this::triggerSlotOverflowUpgrades,
            this::triggerStorageOverflowUpgrades,
            slot,
            stack,
            simulate
         );
   }

   public ItemStack insertItemOnlyToSlot(int slot, ItemStack stack, boolean simulate) {
      this.initSlotTracker();
      return ItemStack.isSameItemSameComponents(this.getStackInSlot(slot), stack)
         ? this.triggerSlotOverflowUpgrades(this.insertItemInternal(slot, stack, simulate))
         : this.insertItemInternal(slot, stack, simulate);
   }

   private void initSlotTracker() {
      if (!(this.slotTracker instanceof InventoryHandlerSlotTracker)) {
         this.slotTracker = new InventoryHandlerSlotTracker(
            this.storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class), this.filterItemSlots
         );
         this.slotTracker.refreshSlotIndexesFrom(this);
         this.slotTracker.setShouldInsertIntoEmpty(this.shouldInsertIntoEmpty);
      }
   }

   private ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate) {
      ItemStack ret = this.runOnBeforeInsert(slot, stack, simulate, this, this.storageWrapper);
      if (ret.isEmpty()) {
         return ret;
      }

      ret = this.inventoryPartitioner.getPartBySlot(slot).insertItem(slot, ret, simulate, (x$0, x$1, x$2) -> super.insertItem(x$0, x$1, x$2));
      if (!simulate) {
         this.getSlotTracker().removeAndSetSlotIndexes(this, slot, this.getStackInSlot(slot));
      }

      if (ret == stack) {
         return ret;
      }

      this.runOnAfterInsert(slot, simulate, this, this.storageWrapper);
      return ret;
   }

   private ItemStack triggerSlotOverflowUpgrades(ItemStack ret) {
      for (IOverflowResponseUpgrade overflowUpgrade : this.storageWrapper.getUpgradeHandler().getWrappersThatImplement(IOverflowResponseUpgrade.class)) {
         ret = overflowUpgrade.onSlotOverflow(ret);
         if (ret.isEmpty()) {
            break;
         }
      }

      return ret;
   }

   private ItemStack triggerStorageOverflowUpgrades(ItemStack ret) {
      for (IOverflowResponseUpgrade overflowUpgrade : this.storageWrapper
         .getUpgradeHandler()
         .getWrappersThatImplementFromMainStorage(IOverflowResponseUpgrade.class)) {
         ret = overflowUpgrade.onStorageOverflow(ret);
         if (ret.isEmpty()) {
            break;
         }
      }

      return ret;
   }

   private void runOnAfterInsert(int slot, boolean simulate, IItemHandlerSimpleInserter handler, IStorageWrapper storageWrapper) {
      if (!simulate) {
         storageWrapper.getUpgradeHandler().getWrappersThatImplementFromMainStorage(IInsertResponseUpgrade.class).forEach(u -> u.onAfterInsert(handler, slot));
      }
   }

   private ItemStack runOnBeforeInsert(ItemStack stack, boolean simulate) {
      List<IInsertResponseUpgrade> wrappers = this.storageWrapper.getUpgradeHandler().getWrappersThatImplement(IInsertResponseUpgrade.class);
      ItemStack remaining = stack;

      for (IInsertResponseUpgrade upgrade : wrappers) {
         remaining = upgrade.onBeforeInsert(this.storageWrapper.getInventoryHandler(), remaining, simulate);
         if (remaining.isEmpty()) {
            return ItemStack.EMPTY;
         }
      }

      return remaining;
   }

   private ItemStack runOnBeforeInsert(int slot, ItemStack stack, boolean simulate, InventoryHandler handler, IStorageWrapper storageWrapper) {
      List<IInsertResponseUpgrade> wrappers = storageWrapper.getUpgradeHandler().getWrappersThatImplementFromMainStorage(IInsertResponseUpgrade.class);
      ItemStack remaining = stack;

      for (IInsertResponseUpgrade upgrade : wrappers) {
         remaining = upgrade.onBeforeInsert(handler, slot, remaining, simulate);
         if (remaining.isEmpty()) {
            return ItemStack.EMPTY;
         }
      }

      return remaining;
   }

   private void runOnAfterExtract(int slot, IItemHandlerSimpleInserter handler, ItemStack originalContents) {
      for (IExtractResponseUpgrade upgrade : this.storageWrapper.getUpgradeHandler().getWrappersThatImplementFromMainStorage(IExtractResponseUpgrade.class)) {
         upgrade.onAfterExtract(handler, slot, originalContents);
      }
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      this.inventoryPartitioner.getPartBySlot(slot).setStackInSlot(slot, stack, (x$0, x$1) -> super.setStackInSlot(x$0, x$1));
      this.getSlotTracker().removeAndSetSlotIndexes(this, slot, stack);
   }

   public void setPersistent(boolean persistent) {
      this.persistent = persistent;
   }

   public boolean isItemValid(int slot, ItemStack stack, @Nullable Player player) {
      return this.inventoryPartitioner.getPartBySlot(slot).isItemValid(slot, stack, player, (x$0, x$1) -> super.isItemValid(x$0, x$1))
         && this.isAllowed(stack)
         && this.storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class).matchesFilter(slot, stack);
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.isItemValid(slot, stack, null);
   }

   public ItemStack getStackInSlot(int slot) {
      return this.inventoryPartitioner.getPartBySlot(slot).getStackInSlot(slot, x$0 -> super.getStackInSlot(x$0));
   }

   protected abstract boolean isAllowed(ItemStack var1);

   public void saveInventory() {
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> this.contentsNbt.put("inventory", this.serializeNBT(registryAccess)));
      if (this.inventoryPartitioner != null) {
         this.contentsNbt.put("partitioner", this.inventoryPartitioner.serializeNBT());
      }

      this.saveHandler.run();
   }

   @Nullable
   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon(int slotIndex) {
      return this.inventoryPartitioner.getNoItemIcon(slotIndex);
   }

   public void copyStacksTo(InventoryHandler otherHandler) {
      InventoryHelper.copyTo(this, otherHandler);
   }

   public void addListener(IntConsumer onContentsChanged) {
      this.onContentsChangedListeners.add(onContentsChanged);
   }

   public void clearListeners() {
      this.onContentsChangedListeners.clear();
   }

   public CompoundTag serializeNBT(Provider registries) {
      ListTag nbtTagList = new ListTag();
      nbtTagList.addAll(this.stackNbts.values());
      CompoundTag nbt = new CompoundTag();
      nbt.put("Items", nbtTagList);
      nbt.putInt("Size", this.getSlots());
      return nbt;
   }

   public double getStackSizeMultiplier() {
      return this.maxStackSizeMultiplier;
   }

   @Override
   public ItemStack insertItem(ItemStack stack, boolean simulate) {
      return this.getSlotTracker()
         .insertItemIntoHandler(
            this, this::runOnBeforeInsert, this::insertItemInternal, this::triggerSlotOverflowUpgrades, this::triggerStorageOverflowUpgrades, stack, simulate
         );
   }

   @Override
   public ItemStack extractItem(ItemStack stack, boolean simulate) {
      return this.getSlotTracker().extractItemFromHandler(this, this::extractItemInternal, stack, simulate);
   }

   public void changeSlots(int diff) {
      NonNullList<ItemStack> previousStacks = this.stacks;
      this.stacks = NonNullList.withSize(previousStacks.size() + diff, ItemStack.EMPTY);

      for (int slot = 0; slot < previousStacks.size() && slot < this.stacks.size(); slot++) {
         this.stacks.set(slot, (ItemStack)previousStacks.get(slot));
      }

      this.initStackNbts();
      this.saveInventory();
      this.getSlotTracker().refreshSlotIndexesFrom(this);
   }

   @Override
   public Set<ItemStackKey> getTrackedStacks() {
      this.initSlotTracker();
      HashSet<ItemStackKey> ret = new HashSet<>(this.slotTracker.getFullStacks());
      ret.addAll(this.slotTracker.getPartialStacks());
      return ret;
   }

   @Override
   public void registerTrackingListeners(
      Consumer<ItemStackKey> onAddStackKey, Consumer<ItemStackKey> onRemoveStackKey, Runnable onAddFirstEmptySlot, Runnable onRemoveLastEmptySlot
   ) {
      this.getSlotTracker().registerListeners(onAddStackKey, onRemoveStackKey, onAddFirstEmptySlot, onRemoveLastEmptySlot);
   }

   @Override
   public void unregisterStackKeyListeners() {
      this.slotTracker.unregisterStackKeyListeners();
   }

   @Override
   public boolean hasEmptySlots() {
      return this.slotTracker.hasEmptySlots();
   }

   public InventoryPartitioner getInventoryPartitioner() {
      return this.inventoryPartitioner;
   }

   public boolean isSlotAccessible(int slot) {
      return this.inventoryPartitioner.getPartBySlot(slot).isSlotAccessible(slot);
   }

   public Set<Integer> getNoSortSlots() {
      return this.inventoryPartitioner.getNoSortSlots();
   }

   public void onSlotFilterChanged(int slot) {
      this.inventoryPartitioner.getPartBySlot(slot).onSlotFilterChanged(slot);
   }

   public void registerFilterItemsChangeListener(Consumer<Set<Item>> listener) {
      this.filterItemsChangeListener = listener;
   }

   public void unregisterFilterItemsChangeListener() {
      this.filterItemsChangeListener = s -> {};
   }

   public void initFilterItems() {
      this.inventoryPartitioner.getFilterItems().forEach((item, slots) -> slots.forEach(slot -> this.filterItemSlots.add(slot, item)));
   }

   public void onFilterItemsChanged() {
      this.slotTracker.refreshSlotIndexesFrom(this);
      if (this.inventoryPartitioner != null) {
         this.filterItemSlots.clear();
         this.inventoryPartitioner.getFilterItems().forEach((item, slots) -> slots.forEach(slot -> this.filterItemSlots.add(slot, item)));
         this.filterItemsChangeListener.accept(this.filterItemSlots.keySet());
      }
   }

   public Set<Item> getFilterItems() {
      return this.filterItemSlots.keySet();
   }

   public void onInit() {
      if (this.inventoryPartitioner != null) {
         this.inventoryPartitioner.onInit();
         this.slotTracker = new ISlotTracker.Noop();
      }
   }

   public void setShouldInsertIntoEmpty(BooleanSupplier shouldInsertIntoEmpty) {
      this.shouldInsertIntoEmpty = shouldInsertIntoEmpty;
      this.getSlotTracker().setShouldInsertIntoEmpty(shouldInsertIntoEmpty);
   }

   public boolean isInfinite(int slot) {
      return this.inventoryPartitioner.isInfinite(slot);
   }

   private boolean hasVoidUpgrade() {
      if (!this.voidUpgradeInfoInitialized) {
         this.hasVoidUpgrade = !this.storageWrapper.getUpgradeHandler().getTypeWrappers(VoidUpgradeItem.TYPE).isEmpty();
         this.voidUpgradeInfoInitialized = true;
      }

      return this.hasVoidUpgrade;
   }

   @Override
   public boolean isInsertBlocked() {
      if (this.hasVoidUpgrade()) {
         return false;
      }

      for (int slot = 0; slot < this.stacks.size(); slot++) {
         ItemStack stack = (ItemStack)this.stacks.get(slot);
         if (stack.getCount() < this.getStackLimit(slot, stack)) {
            return false;
         }
      }

      return true;
   }
}

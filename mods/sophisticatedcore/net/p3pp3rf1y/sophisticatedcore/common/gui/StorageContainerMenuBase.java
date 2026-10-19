package net.p3pp3rf1y.sophisticatedcore.common.gui;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntComparators;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.network.SyncAdditionalSlotInfoPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerClientDataPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncEmptySlotIconsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncSlotChangeErrorPayload;
import net.p3pp3rf1y.sophisticatedcore.network.TransferItemsPayload;
import net.p3pp3rf1y.sophisticatedcore.settings.ISlotColorCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsManager;
import net.p3pp3rf1y.sophisticatedcore.settings.main.MainSettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.nosort.NoSortSettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IOverflowResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.util.DummySlot;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.MathHelper;
import net.p3pp3rf1y.sophisticatedcore.util.NoopStorageWrapper;

public abstract class StorageContainerMenuBase<S extends IStorageWrapper> extends AbstractContainerMenu implements IAdditionalSlotInfoMenu {
   public static final int NUMBER_OF_PLAYER_SLOTS = 36;
   public static final ResourceLocation EMPTY_UPGRADE_SLOT_BACKGROUND = ResourceLocation.fromNamespaceAndPath("sophisticatedcore", "item/empty_upgrade_slot");
   public static final Pair<ResourceLocation, ResourceLocation> INACCESSIBLE_SLOT_BACKGROUND = new Pair(
      InventoryMenu.BLOCK_ATLAS, SophisticatedCore.getRL("item/inaccessible_slot")
   );
   protected static final String UPGRADE_ENABLED_TAG = "upgradeEnabled";
   protected static final String UPGRADE_SLOT_TAG = "upgradeSlot";
   protected static final String ACTION_TAG = "action";
   protected static final String OPEN_TAB_ID_TAG = "openTabId";
   protected static final String SORT_BY_TAG = "sortBy";
   private static final String SEARCH_PHRASE_TAG = "searchPhrase";
   private static final Method ON_SWAP_CRAFT = ObfuscationReflectionHelper.findMethod(Slot.class, "onSwapCraft", new Class[]{int.class});
   public final NonNullList<ItemStack> lastUpgradeSlots = NonNullList.create();
   public final List<Slot> upgradeSlots = Lists.newArrayList();
   public final NonNullList<ItemStack> remoteUpgradeSlots = NonNullList.create();
   public final NonNullList<ItemStack> lastRealSlots = NonNullList.create();
   public final List<Slot> realInventorySlots = Lists.newArrayList();
   private final Map<Integer, UpgradeContainerBase<?, ?>> upgradeContainers = new LinkedHashMap<>();
   private final NonNullList<ItemStack> remoteRealSlots = NonNullList.create();
   protected final Player player;
   protected final S storageWrapper;
   protected final IStorageWrapper parentStorageWrapper;
   private final int storageItemSlotIndex;
   private final boolean shouldLockStorageItemSlot;
   private final List<Slot> extraSlots;
   private int storageItemSlotNumber = -1;
   private Consumer<StorageContainerMenuBase<?>> upgradeChangeListener = null;
   private boolean isUpdatingFromPacket = false;
   private long errorResultExpirationTime = 0L;
   @Nullable
   private UpgradeSlotChangeResult errorUpgradeSlotChangeResult;
   private CompoundTag lastSettingsNbt = null;
   private boolean inventorySlotStackChanged = false;
   private final Set<Integer> inaccessibleSlots = new HashSet<>();
   private final Map<Integer, Integer> slotLimitOverrides = new HashMap<>();
   private final Set<Integer> infiniteSlots = new HashSet<>();
   private final Map<Integer, ItemStack> slotFilterItems = new HashMap<>();
   private final Map<Integer, Pair<ResourceLocation, ResourceLocation>> emptySlotIcons = new HashMap<>();
   private boolean slotsChangedSinceStartOfClick = false;
   private boolean tryingToMergeUpgrade = false;
   private boolean initialBroadcast = true;
   private int extraSlotsSize = 0;
   private int columnsChange = 0;
   private int inventorySlotsBeforeClickHandled;

   protected StorageContainerMenuBase(
      MenuType<?> menuType,
      int containerId,
      Player player,
      S storageWrapper,
      IStorageWrapper parentStorageWrapper,
      int storageItemSlotIndex,
      boolean shouldLockStorageItemSlot
   ) {
      this(menuType, containerId, player, storageWrapper, parentStorageWrapper, storageItemSlotIndex, shouldLockStorageItemSlot, Collections.emptyList());
   }

   protected StorageContainerMenuBase(
      MenuType<?> menuType,
      int containerId,
      Player player,
      S storageWrapper,
      IStorageWrapper parentStorageWrapper,
      int storageItemSlotIndex,
      boolean shouldLockStorageItemSlot,
      List<Slot> extraSlots
   ) {
      super(menuType, containerId);
      this.player = player;
      this.storageWrapper = storageWrapper;
      this.parentStorageWrapper = parentStorageWrapper;
      this.storageItemSlotIndex = storageItemSlotIndex;
      this.shouldLockStorageItemSlot = shouldLockStorageItemSlot;
      this.extraSlots = extraSlots;
      this.removeOpenTabIfKeepOff();
      storageWrapper.fillWithLoot(player);
      this.initSlotsAndContainers(player, storageItemSlotIndex, shouldLockStorageItemSlot, extraSlots);
      this.inventorySlotsBeforeClickHandled = this.getInventorySlotsSize();
   }

   public abstract Optional<BlockPos> getBlockPosition();

   public abstract Optional<Entity> getEntity();

   protected void initSlotsAndContainers(Player player, int storageItemSlotIndex, boolean shouldLockStorageItemSlot, List<Slot> extraSlots) {
      this.addStorageInventorySlots();
      this.addPlayerInventorySlots(player.getInventory(), storageItemSlotIndex, shouldLockStorageItemSlot);
      this.addExtraSlots(extraSlots);
      this.addUpgradeSlots();
      this.addUpgradeSettingsContainers(player);
   }

   private void addExtraSlots(List<Slot> extraSlots) {
      extraSlots.forEach(this::addExtraSlot);
   }

   protected void addExtraSlot(Slot slot) {
      this.extraSlotsSize++;
      this.addSlot(slot);
   }

   public List<Slot> getExtraSlots() {
      return this.extraSlots;
   }

   public S getStorageWrapper() {
      return this.storageWrapper;
   }

   protected void addUpgradeSettingsContainers(Player player) {
      UpgradeHandler upgradeHandler = this.storageWrapper.getUpgradeHandler();
      upgradeHandler.getSlotWrappers()
         .forEach(
            (slot, wrapper) -> UpgradeContainerRegistry.instantiateContainer(player, slot, wrapper)
               .ifPresent(containerx -> this.upgradeContainers.put(slot, containerx))
         );

      for (UpgradeContainerBase<?, ?> container : this.upgradeContainers.values()) {
         container.getSlots().forEach(this::addUpgradeSlot);
         container.onInit();
      }

      this.storageWrapper.getOpenTabId().ifPresent(id -> {
         if (this.upgradeContainers.containsKey(id)) {
            this.upgradeContainers.get(id).setIsOpen(true);
         }
      });
   }

   private void addUpgradeSlots() {
      UpgradeHandler upgradeHandler = this.storageWrapper.getUpgradeHandler();
      int numberOfSlots = upgradeHandler.getSlots();
      if (numberOfSlots != 0) {
         for (int slotIndex = 0; slotIndex < upgradeHandler.getSlots(); slotIndex++) {
            this.addUpgradeSlot(this.instantiateUpgradeSlot(upgradeHandler, slotIndex));
         }
      }
   }

   public int getColumnsTaken() {
      return this.storageWrapper.getColumnsTaken();
   }

   public Optional<UpgradeSlotChangeResult> getErrorUpgradeSlotChangeResult() {
      if (this.errorUpgradeSlotChangeResult != null && this.player.level().getGameTime() >= this.errorResultExpirationTime) {
         this.clearErrorUpgradeSlotChangeResult();
      }

      return Optional.ofNullable(this.errorUpgradeSlotChangeResult);
   }

   private void clearErrorUpgradeSlotChangeResult() {
      this.errorResultExpirationTime = 0L;
      this.errorUpgradeSlotChangeResult = null;
   }

   protected void sendStorageSettingsToClient() {
   }

   protected abstract StorageContainerMenuBase<S>.StorageUpgradeSlot instantiateUpgradeSlot(UpgradeHandler var1, int var2);

   protected void addUpgradeSlot(Slot slot) {
      slot.index = this.getTotalSlotsNumber();
      this.upgradeSlots.add(slot);
      this.lastUpgradeSlots.add(ItemStack.EMPTY);
      this.remoteUpgradeSlots.add(ItemStack.EMPTY);
   }

   protected void addNoSortSlot(Slot slot) {
      slot.index = this.getInventorySlotsSize();
      this.realInventorySlots.add(slot);
      this.lastRealSlots.add(ItemStack.EMPTY);
      this.remoteRealSlots.add(ItemStack.EMPTY);
   }

   protected Slot addSlot(Slot slot) {
      slot.index = this.getInventorySlotsSize();
      this.slots.add(slot);
      this.lastSlots.add(ItemStack.EMPTY);
      this.remoteSlots.add(ItemStack.EMPTY);
      this.realInventorySlots.add(slot);
      this.lastRealSlots.add(ItemStack.EMPTY);
      this.remoteRealSlots.add(ItemStack.EMPTY);
      return slot;
   }

   public int getInventorySlotsSize() {
      return this.realInventorySlots.size();
   }

   public int getNumberOfStorageInventorySlots() {
      return this.storageWrapper.getInventoryHandler().getSlots();
   }

   public int getNumberOfUpgradeSlots() {
      return this.storageWrapper.getUpgradeHandler().getSlots();
   }

   public Map<Integer, UpgradeContainerBase<?, ?>> getUpgradeContainers() {
      return this.upgradeContainers;
   }

   protected void addStorageInventorySlots() {
      InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();
      int slotIndex = 0;
      Set<Integer> noSortSlotIndexes = this.getNoSortSlotIndexes();

      while (slotIndex < inventoryHandler.getSlots()) {
         final int finalSlotIndex = slotIndex;
         StorageInventorySlot slot = new StorageInventorySlot(this.player.level().isClientSide, this.storageWrapper, finalSlotIndex, this.player) {
            public void set(@Nonnull ItemStack stack) {
               super.set(stack);
               StorageContainerMenuBase.this.onStorageInventorySlotSet(finalSlotIndex);
            }

            @Nullable
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
               return StorageContainerMenuBase.this.inaccessibleSlots.contains(finalSlotIndex)
                  ? StorageContainerMenuBase.INACCESSIBLE_SLOT_BACKGROUND
                  : StorageContainerMenuBase.this.emptySlotIcons.getOrDefault(finalSlotIndex, null);
            }

            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
               return !StorageContainerMenuBase.this.inaccessibleSlots.contains(finalSlotIndex) && super.mayPlace(stack);
            }

            public boolean mayPickup(Player playerIn) {
               return !StorageContainerMenuBase.this.inaccessibleSlots.contains(finalSlotIndex) && super.mayPickup(playerIn);
            }

            @Override
            public int getMaxStackSize(ItemStack stack) {
               return StorageContainerMenuBase.this.slotLimitOverrides.containsKey(finalSlotIndex)
                  ? StorageContainerMenuBase.this.slotLimitOverrides.get(finalSlotIndex)
                  : super.getMaxStackSize(stack);
            }

            @Override
            public int getMaxStackSize() {
               return StorageContainerMenuBase.this.slotLimitOverrides.containsKey(finalSlotIndex)
                  ? StorageContainerMenuBase.this.slotLimitOverrides.get(finalSlotIndex)
                  : super.getMaxStackSize();
            }
         };
         if (noSortSlotIndexes.contains(slotIndex)) {
            this.addNoSortSlot(slot);
         } else {
            this.addSlot(slot);
         }

         slotIndex++;
      }
   }

   protected void onStorageInventorySlotSet(int slotIndex) {
   }

   protected void addPlayerInventorySlots(Inventory playerInventory, int storageItemSlotIndex, boolean shouldLockStorageItemSlot) {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            int slotIndex = j + i * 9 + 9;
            Slot slot = this.addStorageItemSafeSlot(playerInventory, slotIndex, storageItemSlotIndex, shouldLockStorageItemSlot);
            this.addSlotAndUpdateStorageItemSlotNumber(storageItemSlotIndex, shouldLockStorageItemSlot, slotIndex, slot);
         }
      }

      for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
         Slot slot = this.addStorageItemSafeSlot(playerInventory, slotIndex, storageItemSlotIndex, shouldLockStorageItemSlot);
         this.addSlotAndUpdateStorageItemSlotNumber(storageItemSlotIndex, shouldLockStorageItemSlot, slotIndex, slot);
      }
   }

   private Slot addStorageItemSafeSlot(Inventory playerInventory, int slotIndex, int storageItemSlotIndex, boolean shouldLockStorageItemSlot) {
      Slot slot;
      if (shouldLockStorageItemSlot && slotIndex == storageItemSlotIndex) {
         slot = new Slot(playerInventory, slotIndex, 0, 0) {
            public boolean mayPickup(Player playerIn) {
               return false;
            }
         };
      } else {
         slot = new Slot(playerInventory, slotIndex, 0, 0);
      }

      return this.addSlot(slot);
   }

   public boolean hasSomethingMessedWithStorage() {
      return !this.isClientSide()
         && (this.storageItemHasChanged() || this.realInventorySlots.size() != this.storageWrapper.getInventoryHandler().getSlots() + 36 + this.extraSlotsSize);
   }

   protected boolean isClientSide() {
      return this.player.level().isClientSide;
   }

   private void addSlotAndUpdateStorageItemSlotNumber(int storageItemSlotIndex, boolean lockStorageItemSlot, int slotIndex, Slot slot) {
      if (lockStorageItemSlot && slotIndex == storageItemSlotIndex) {
         this.storageItemSlotNumber = slot.index;
      }
   }

   public int getNumberOfRows() {
      return this.storageWrapper.getNumberOfSlotRows();
   }

   public int getFirstUpgradeSlot() {
      return this.getInventorySlotsSize();
   }

   public boolean isFirstLevelStorage() {
      return this.parentStorageWrapper == NoopStorageWrapper.INSTANCE;
   }

   public void initializeContents(int stateId, List<ItemStack> items, ItemStack carried) {
      this.storageWrapper.setPersistent(this.player.level().isClientSide);
      this.isUpdatingFromPacket = true;
      super.initializeContents(stateId, items, carried);
      this.isUpdatingFromPacket = false;
      this.storageWrapper.setPersistent(true);
      this.storageWrapper.getInventoryHandler().saveInventory();
      this.storageWrapper.getUpgradeHandler().saveInventory();
   }

   protected boolean isUpgradeSettingsSlot(int index) {
      return index >= this.getNumberOfStorageInventorySlots() + this.getNumberOfUpgradeSlots() + 36 && index < this.getTotalSlotsNumber();
   }

   public boolean isStorageInventorySlot(int index) {
      return index >= 0 && index < this.getNumberOfStorageInventorySlots();
   }

   protected boolean isUpgradeSlot(int index) {
      return index >= this.getFirstUpgradeSlot() && index - this.getFirstUpgradeSlot() < this.getNumberOfUpgradeSlots();
   }

   public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
      this.inventorySlotsBeforeClickHandled = this.getInventorySlotsSize();
      if (this.isUpgradeSettingsSlot(slotId) && this.getSlot(slotId) instanceof IFilterSlot && this.getSlot(slotId).mayPlace(this.getCarried())) {
         Slot slot = this.getSlot(slotId);
         ItemStack cursorStack = this.getCarried().copy();
         if (cursorStack.getCount() > 1) {
            cursorStack.setCount(1);
         }

         slot.set(cursorStack);
      } else if (this.isUpgradeSlot(slotId) && this.getSlot(slotId) instanceof StorageContainerMenuBase<?>.StorageUpgradeSlot slot) {
         ItemStack slotStack = slot.getItem();
         if (slot.mayPlace(this.getCarried())) {
            ItemStack carriedStack = this.getCarried();
            IUpgradeItem<?> upgradeItem = (IUpgradeItem<?>)carriedStack.getItem();
            int newColumnsTaken = upgradeItem.getInventoryColumnsTaken();
            int currentColumnsTaken = 0;
            if (!slotStack.isEmpty()) {
               currentColumnsTaken = ((IUpgradeItem)slotStack.getItem()).getInventoryColumnsTaken();
            }

            if (this.needsSlotsThatAreOccupied(carriedStack, currentColumnsTaken, newColumnsTaken)) {
               return;
            }

            int columnsToRemove = newColumnsTaken - currentColumnsTaken;
            if (slotStack.isEmpty()) {
               slot.set(carriedStack.split(1));
               if (carriedStack.isEmpty()) {
                  this.setCarried(ItemStack.EMPTY);
               }
            } else if (carriedStack.getCount() == 1) {
               slot.set(carriedStack);
               this.setCarried(upgradeItem.getCleanedUpgradeStack(slotStack.copy()));
            }

            this.updateColumnsTaken(columnsToRemove);
            slot.setChanged();
            if (columnsToRemove != 0 && player.level().isClientSide()) {
               this.onUpgradesChanged();
            }
         } else if (this.getCarried().isEmpty() && !slotStack.isEmpty() && slot.mayPickup(player)) {
            int k2 = dragType == 0
               ? Math.min(slotStack.getCount(), slotStack.getMaxStackSize())
               : Math.min(slotStack.getMaxStackSize() + 1, slotStack.getCount() + 1) / 2;
            IUpgradeItem<?> upgradeItem = (IUpgradeItem<?>)slotStack.getItem();
            int columnsTaken = upgradeItem.getInventoryColumnsTaken();
            if (clickType == ClickType.QUICK_MOVE) {
               this.quickMoveStack(player, slotId);
            } else {
               this.setCarried(upgradeItem.getCleanedUpgradeStack(slot.remove(k2)));
            }

            slot.wasEmpty = false;
            this.updateColumnsTaken(-columnsTaken);
            slot.onTake(player, this.getCarried());
         }
      } else if (!this.isOverflowLogicSlotAndAction(slotId, clickType) || !this.handleOverflow(slotId, clickType, dragType, player)) {
         super.clicked(slotId, dragType, clickType, player);
      }
   }

   public boolean isValidSlotIndex(int slotIndex) {
      return slotIndex == -1 || slotIndex == -999 || slotIndex < this.getTotalSlotsNumber();
   }

   private boolean handleOverflow(int slotId, ClickType clickType, int dragType, Player player) {
      ItemStack cursorStack = clickType == ClickType.SWAP ? player.getInventory().getItem(dragType) : this.getCarried();
      Consumer<ItemStack> updateCursorStack = clickType == ClickType.SWAP ? s -> player.getInventory().setItem(dragType, s) : this::setCarried;
      Slot slot = this.getSlot(slotId);
      if ((clickType == ClickType.SWAP || !cursorStack.isEmpty()) && slot.mayPlace(cursorStack)) {
         ItemStack slotStack = slot.getItem();
         if (!slotStack.isEmpty()
            && (
               !slot.mayPickup(player)
                  || slotStack.getItem() == cursorStack.getItem()
                  || cursorStack.getCount() > slot.getMaxStackSize(cursorStack)
                  || slotStack.getCount() > slotStack.getMaxStackSize()
            )) {
            return slotStack.getItem() == cursorStack.getItem()
               ? this.processOverflowForAnythingOverSlotMaxSize(cursorStack, updateCursorStack, slot, slotStack)
               : false;
         } else {
            return this.processOverflowIfSlotWithSameItemFound(cursorStack, updateCursorStack);
         }
      } else {
         return false;
      }
   }

   private boolean processOverflowForAnythingOverSlotMaxSize(ItemStack cursorStack, Consumer<ItemStack> updateCursorStack, Slot slot, ItemStack slotStack) {
      int remainingSpaceInSlot = slot.getMaxStackSize(cursorStack) - slotStack.getCount();
      if (remainingSpaceInSlot < cursorStack.getCount()) {
         ItemStack overflow = cursorStack.copy();
         int overflowCount = cursorStack.getCount() - remainingSpaceInSlot;
         overflow.setCount(overflowCount);
         ItemStack result = this.processOverflowLogic(overflow);
         if (result.getCount() < overflowCount) {
            cursorStack.shrink(overflowCount - result.getCount());
            if (cursorStack.isEmpty()) {
               updateCursorStack.accept(ItemStack.EMPTY);
               return true;
            }

            updateCursorStack.accept(cursorStack);
         }
      }

      return false;
   }

   private boolean processOverflowIfSlotWithSameItemFound(ItemStack cursorStack, Consumer<ItemStack> updateCursorStack) {
      for (IOverflowResponseUpgrade overflowUpgrade : this.storageWrapper
         .getUpgradeHandler()
         .getWrappersThatImplementFromMainStorage(IOverflowResponseUpgrade.class)) {
         if (overflowUpgrade.stackMatchesFilter(cursorStack)
            && overflowUpgrade.worksInGui()
            && this.findSlotWithMatchingStack(cursorStack, updateCursorStack, overflowUpgrade)) {
            return true;
         }
      }

      return false;
   }

   private boolean findSlotWithMatchingStack(ItemStack cursorStack, Consumer<ItemStack> updateCursorStack, IOverflowResponseUpgrade overflowUpgrade) {
      if (this.storageWrapper.getInventoryHandler().getSlotTracker().getFullStacks().contains(ItemStackKey.of(cursorStack))) {
         ItemStack result = cursorStack;
         result = overflowUpgrade.onSlotOverflow(result);
         updateCursorStack.accept(result);
         if (result.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private boolean isOverflowLogicSlotAndAction(int slotId, ClickType clickType) {
      return this.isStorageInventorySlot(slotId) && (clickType == ClickType.SWAP || clickType == ClickType.PICKUP);
   }

   protected void updateColumnsTaken(int columnsChange) {
      if (this.player.level().isClientSide()) {
         this.columnsChange = columnsChange;
      } else {
         this.actuallyUpdateColumnsTaken(columnsChange);
      }
   }

   private void actuallyUpdateColumnsTaken(int columnsChange) {
      if (columnsChange != 0) {
         AtomicInteger columnsTaken = new AtomicInteger(0);
         InventoryHelper.iterate(this.storageWrapper.getUpgradeHandler(), (slot, upgradeStack) -> {
            if (upgradeStack.getItem() instanceof UpgradeItemBase<?> upgradeItem) {
               columnsTaken.addAndGet(upgradeItem.getInventoryColumnsTaken());
            }
         });
         this.storageWrapper.setColumnsTaken(columnsTaken.get(), true);
         this.storageWrapper.onContentsNbtUpdated();
         this.refreshAllSlots();
      }
   }

   protected boolean needsSlotsThatAreOccupied(ItemStack cursorStack, int currentColumnsTaken, int newColumnsTaken) {
      if (currentColumnsTaken >= newColumnsTaken) {
         return false;
      }

      int slotsToCheck = (newColumnsTaken - currentColumnsTaken) * this.getNumberOfRows();
      InventoryHandler invHandler = this.storageWrapper.getInventoryHandler();
      Set<Integer> errorSlots = new HashSet<>();
      int slots = this.getNumberOfStorageInventorySlots();

      for (int slotIndex = slots - 1; slotIndex >= slots - slotsToCheck; slotIndex--) {
         if (!invHandler.getStackInSlot(slotIndex).isEmpty()) {
            errorSlots.add(slotIndex);
         }
      }

      if (!errorSlots.isEmpty()) {
         this.updateSlotChangeError(
            UpgradeSlotChangeResult.fail(
               TranslationHelper.INSTANCE.translError("add.needs_occupied_inventory_slots", slotsToCheck, cursorStack.getHoverName()),
               Collections.emptySet(),
               errorSlots,
               Collections.emptySet()
            )
         );
         return true;
      } else {
         return false;
      }
   }

   public int getUpgradeSlotsSize() {
      return this.upgradeSlots.size();
   }

   public List<Integer> getSlotOverlayColors(int slot) {
      List<Integer> ret = new ArrayList<>();
      this.storageWrapper.getSettingsHandler().getCategoriesThatImplement(ISlotColorCategory.class).forEach(c -> c.getSlotColor(slot).ifPresent(ret::add));
      return ret;
   }

   public Optional<UpgradeContainerBase<?, ?>> getOpenContainer() {
      return this.storageWrapper
         .getOpenTabId()
         .flatMap(id -> this.upgradeContainers.containsKey(id) ? Optional.of(this.upgradeContainers.get(id)) : Optional.empty());
   }

   protected void sendToServer(Consumer<CompoundTag> addData) {
      CompoundTag data = new CompoundTag();
      addData.accept(data);
      PacketDistributor.sendToServer(new SyncContainerClientDataPayload(data), new CustomPacketPayload[0]);
   }

   public void setUpgradeEnabled(int upgradeSlot, boolean enabled) {
      Map<Integer, IUpgradeWrapper> slotWrappers = this.storageWrapper.getUpgradeHandler().getSlotWrappers();
      if (slotWrappers.containsKey(upgradeSlot)) {
         if (this.isClientSide()) {
            this.sendToServer(data -> {
               data.putBoolean("upgradeEnabled", enabled);
               data.putInt("upgradeSlot", upgradeSlot);
            });
         }

         slotWrappers.get(upgradeSlot).setEnabled(enabled);
      }
   }

   public boolean getUpgradeEnabled(int upgradeSlot) {
      Map<Integer, IUpgradeWrapper> slotWrappers = this.storageWrapper.getUpgradeHandler().getSlotWrappers();
      return !slotWrappers.containsKey(upgradeSlot) ? false : slotWrappers.get(upgradeSlot).isEnabled();
   }

   public boolean isUpgradeRunnable(int upgradeSlot) {
      Map<Integer, IUpgradeWrapper> slotWrappers = this.storageWrapper.getUpgradeHandler().getSlotWrappers();
      if (!slotWrappers.containsKey(upgradeSlot)) {
         return false;
      }

      IUpgradeWrapper upgradeWrapper = slotWrappers.get(upgradeSlot);
      return !(upgradeWrapper instanceof ITickableUpgrade) || this.storageWrapper.isUpgradeRunnable(upgradeWrapper.getUpgradeStack());
   }

   public boolean canDisableUpgrade(int upgradeSlot) {
      Map<Integer, IUpgradeWrapper> slotWrappers = this.storageWrapper.getUpgradeHandler().getSlotWrappers();
      return !slotWrappers.containsKey(upgradeSlot) ? false : slotWrappers.get(upgradeSlot).canBeDisabled();
   }

   public void sort() {
      if (this.isClientSide()) {
         this.sendToServer(data -> data.putString("action", "sort"));
      } else {
         this.storageWrapper.sort();
      }
   }

   public void setOpenTabId(int tabId) {
      if (this.isClientSide()) {
         this.sendToServer(data -> data.putInt("openTabId", tabId));
      }

      if (tabId == -1) {
         this.storageWrapper.removeOpenTabId();
      } else {
         this.storageWrapper.setOpenTabId(tabId);
      }
   }

   public void removeOpenTabId() {
      this.setOpenTabId(-1);
   }

   public SortBy getSortBy() {
      return this.storageWrapper.getSortBy();
   }

   public void setSortBy(SortBy sortBy) {
      if (this.isClientSide()) {
         this.sendToServer(data -> data.putString("sortBy", sortBy.getSerializedName()));
      }

      this.storageWrapper.setSortBy(sortBy);
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("containerId")) {
         int containerId = data.getInt("containerId");
         if (this.upgradeContainers.containsKey(containerId)) {
            this.upgradeContainers.get(containerId).handlePacket(data);
         }
      } else if (data.contains("openTabId")) {
         this.setOpenTabId(data.getInt("openTabId"));
      } else if (data.contains("sortBy")) {
         this.setSortBy(SortBy.fromName(data.getString("sortBy")));
      } else if (data.contains("searchPhrase")) {
         this.setSearchPhrase(data.getString("searchPhrase"));
      } else if (data.contains("action")) {
         String actionName = data.getString("action");
         switch (actionName) {
            case "sort":
               this.sort();
               break;
            case "openSettings":
               this.openSettings();
         }
      } else if (data.contains("upgradeEnabled")) {
         this.setUpgradeEnabled(data.getInt("upgradeSlot"), data.getBoolean("upgradeEnabled"));
      }
   }

   public Optional<UpgradeContainerBase<?, ?>> getSlotUpgradeContainer(Slot slot) {
      if (this.isUpgradeSettingsSlot(slot.index)) {
         for (UpgradeContainerBase<?, ?> upgradeContainer : this.upgradeContainers.values()) {
            if (upgradeContainer.containsSlot(slot)) {
               return Optional.of(upgradeContainer);
            }
         }
      }

      return Optional.empty();
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.getSlot(index);
      if (slot.hasItem()) {
         Optional<UpgradeContainerBase<?, ?>> upgradeContainer = this.getSlotUpgradeContainer(slot);
         ItemStack slotStack = upgradeContainer.<ItemStack>map(c -> c.getSlotStackToTransfer(slot)).orElse(slot.getItem());
         itemstack = slotStack.copy();
         ItemStack stackToMerge = this.isUpgradeSlot(index) && slotStack.getItem() instanceof IUpgradeItem<?> upgradeItem
            ? upgradeItem.getCleanedUpgradeStack(slotStack.copy())
            : slotStack;
         if (!this.mergeSlotStack(slot, index, stackToMerge)) {
            return ItemStack.EMPTY;
         }

         if (stackToMerge.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         slot.onQuickCraft(slotStack, itemstack);
         if (upgradeContainer.isPresent()) {
            upgradeContainer.ifPresent(c -> c.onTakeFromSlot(slot, player, slotStack));
         } else {
            slot.onTake(player, slotStack);
         }
      }

      return itemstack;
   }

   private boolean mergeSlotStack(Slot slot, int index, ItemStack slotStack) {
      if (this.isUpgradeSlot(index)) {
         return this.mergeStackToPlayersInventory(slot, slotStack) || this.mergeStackToExtraSlots(slot, slotStack) || this.mergeStackToStorage(slot, slotStack);
      } else if (this.isStorageInventorySlot(index)) {
         return this.shouldShiftClickIntoOpenTabFirst()
            ? this.mergeStackToOpenUpgradeTab(slot, slotStack)
               || this.mergeStackToPlayersInventory(slot, slotStack)
               || this.mergeStackToExtraSlots(slot, slotStack)
            : this.mergeStackToPlayersInventory(slot, slotStack)
               || this.mergeStackToExtraSlots(slot, slotStack)
               || this.mergeStackToOpenUpgradeTab(slot, slotStack);
      } else if (this.isUpgradeSettingsSlot(index)) {
         return this.getSlotUpgradeContainer(slot).map(c -> c.mergeIntoStorageFirst(slot)).orElse(true)
            ? this.mergeStackToStorage(slot, slotStack) || this.mergeStackToPlayersInventory(slot, slotStack) || this.mergeStackToExtraSlots(slot, slotStack)
            : this.mergeStackToPlayersInventory(slot, slotStack) || this.mergeStackToExtraSlots(slot, slotStack) || this.mergeStackToStorage(slot, slotStack);
      } else if (this.isExtraSlot(index)) {
         return this.mergeStackToPlayersInventory(slot, slotStack)
            || this.mergeStackToStorage(slot, slotStack)
            || this.mergeStackToOpenUpgradeTab(slot, slotStack);
      } else {
         return this.shouldShiftClickIntoOpenTabFirst()
            ? this.mergeStackToExtraSlots(slot, slotStack)
               || this.mergeStackToOpenUpgradeTab(slot, slotStack)
               || this.mergeStackToUpgradeSlots(slot, slotStack)
               || this.mergeStackToStorage(slot, slotStack)
            : this.mergeStackToExtraSlots(slot, slotStack)
               || this.mergeStackToUpgradeSlots(slot, slotStack)
               || this.mergeStackToStorage(slot, slotStack)
               || this.mergeStackToOpenUpgradeTab(slot, slotStack);
      }
   }

   private boolean isExtraSlot(int slotIndex) {
      return slotIndex >= this.getInventorySlotsSize() - this.extraSlotsSize && slotIndex < this.getInventorySlotsSize();
   }

   private boolean shouldShiftClickIntoOpenTabFirst() {
      MainSettingsCategory<?> category = this.storageWrapper.getSettingsHandler().getGlobalSettingsCategory();
      return SettingsManager.getSettingValue(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.SHIFT_CLICK_INTO_OPEN_TAB_FIRST);
   }

   public boolean shouldKeepSearchPhrase() {
      MainSettingsCategory<?> category = this.storageWrapper.getSettingsHandler().getGlobalSettingsCategory();
      return SettingsManager.getSettingValue(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.KEEP_SEARCH_PHRASE);
   }

   public String getSearchPhrase() {
      String searchPhrase = "";
      MainSettingsCategory<?> category = this.storageWrapper.getSettingsHandler().getGlobalSettingsCategory();
      if (SettingsManager.getPlayerSetting(this.player, category.getPlayerSettingsTagName(), SettingsManager.KEEP_SEARCH_PHRASE)
         .orElse(SettingsManager.KEEP_SEARCH_PHRASE.getDefaultValue())) {
         searchPhrase = SettingsManager.getPlayerSetting(this.player, category.getPlayerSettingsTagName(), SettingsManager.SEARCH_PHRASE).orElse("");
      }

      if (searchPhrase.isEmpty()
         && Boolean.TRUE
            .equals(SettingsManager.getSettingValue(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.KEEP_SEARCH_PHRASE))) {
         searchPhrase = SettingsManager.getSettingValue(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.SEARCH_PHRASE);
      }

      return searchPhrase;
   }

   public void setSearchPhrase(String searchPhrase) {
      MainSettingsCategory<?> category = this.storageWrapper.getSettingsHandler().getGlobalSettingsCategory();
      if (SettingsManager.getPlayerSetting(this.player, category.getPlayerSettingsTagName(), SettingsManager.KEEP_SEARCH_PHRASE)
         .orElse(SettingsManager.KEEP_SEARCH_PHRASE.getDefaultValue())) {
         SettingsManager.setPlayerSetting(this.player, category.getPlayerSettingsTagName(), SettingsManager.SEARCH_PHRASE, searchPhrase);
         SettingsManager.setSetting(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.SEARCH_PHRASE, "");
      } else {
         SettingsManager.setSetting(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.SEARCH_PHRASE, searchPhrase);
      }

      if (this.isClientSide()) {
         this.sendToServer(data -> data.putString("searchPhrase", searchPhrase));
      }
   }

   private boolean mergeStackToUpgradeSlots(Slot sourceSlot, ItemStack slotStack) {
      if (!(slotStack.getItem() instanceof IUpgradeItem)) {
         return false;
      }

      this.clearErrorUpgradeSlotChangeResult();
      this.tryingToMergeUpgrade = true;
      boolean result = !this.upgradeSlots.isEmpty()
         && this.moveItemStackTo(sourceSlot, slotStack, this.getInventorySlotsSize(), this.getInventorySlotsSize() + this.getNumberOfUpgradeSlots(), false);
      this.tryingToMergeUpgrade = false;
      this.showUpgradeSlotChangeError();
      return result;
   }

   private boolean mergeStackToOpenUpgradeTab(Slot sourceSlot, ItemStack slotStack) {
      return this.getOpenContainer().map(c -> {
         List<Slot> slots = c.getSlots();
         if (slots.isEmpty()) {
            return false;
         }

         int firstSlotIndex = slots.get(0).index;
         int lastSlotIndex = slots.get(slots.size() - 1).index;
         return this.mergeItemStack(sourceSlot, slotStack, firstSlotIndex, lastSlotIndex + 1, false, true);
      }).orElse(false);
   }

   private boolean mergeStackToStorage(Slot slot, ItemStack slotStack) {
      ItemStack remaining = this.mergeItemStack(slotStack, 0, this.getNumberOfStorageInventorySlots(), false, false, true);
      if (remaining.getCount() != slotStack.getCount()) {
         slot.set(remaining);
         return true;
      } else {
         return false;
      }
   }

   private boolean mergeStackToExtraSlots(Slot sourceSlot, ItemStack slotStack) {
      return this.mergeItemStack(sourceSlot, slotStack, this.getInventorySlotsSize() - this.extraSlotsSize, this.getInventorySlotsSize(), true, true);
   }

   private boolean mergeStackToPlayersInventory(Slot sourceSlot, ItemStack slotStack) {
      return this.mergeItemStack(sourceSlot, slotStack, this.getNumberOfStorageInventorySlots(), this.getInventorySlotsSize() - this.extraSlotsSize, true, true);
   }

   public boolean isNotPlayersInventorySlot(int slotNumber) {
      return slotNumber < this.getNumberOfStorageInventorySlots() || slotNumber >= this.getInventorySlotsSize();
   }

   public Optional<ItemStack> getMemorizedStackInSlot(int slotId) {
      return this.storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class).getSlotFilterStack(slotId, false);
   }

   public void setUpgradeChangeListener(Consumer<StorageContainerMenuBase<?>> upgradeChangeListener) {
      this.upgradeChangeListener = upgradeChangeListener;
   }

   public abstract void openSettings();

   protected abstract boolean storageItemHasChanged();

   public <T extends UpgradeContainerBase<?, ?> & ICraftingContainer> Optional<T> getOpenOrFirstCraftingContainer(RecipeType<?> recipeType) {
      T firstContainer = null;

      for (UpgradeContainerBase<?, ?> container : this.upgradeContainers.values()) {
         if (container instanceof ICraftingContainer craftingContainer && craftingContainer.getRecipeType() == recipeType) {
            if (container.isOpen()) {
               return Optional.of((T)container);
            }

            if (firstContainer == null) {
               firstContainer = (T)container;
            }
         }
      }

      return Optional.ofNullable(firstContainer);
   }

   public int getTotalSlotsNumber() {
      return this.getInventorySlotsSize() + this.upgradeSlots.size();
   }

   protected void removeOpenTabIfKeepOff() {
      MainSettingsCategory<?> category = this.storageWrapper.getSettingsHandler().getGlobalSettingsCategory();
      if (Boolean.FALSE.equals(SettingsManager.getSettingValue(this.player, category.getPlayerSettingsTagName(), category, SettingsManager.KEEP_TAB_OPEN))) {
         this.storageWrapper.removeOpenTabId();
      }
   }

   protected Set<Integer> getNoSortSlotIndexes() {
      SettingsHandler settingsHandler = this.storageWrapper.getSettingsHandler();
      Set<Integer> slotIndexesExcludedFromSort = new HashSet<>();
      slotIndexesExcludedFromSort.addAll(settingsHandler.getTypeCategory(NoSortSettingsCategory.class).getNoSortSlots());
      slotIndexesExcludedFromSort.addAll(settingsHandler.getTypeCategory(MemorySettingsCategory.class).getSlotIndexes());
      return slotIndexesExcludedFromSort;
   }

   public void broadcastFullState() {
      this.broadcastFullStateOf(this.lastUpgradeSlots, this.upgradeSlots, this.getFirstUpgradeSlot());
      this.broadcastFullStateOf(this.lastRealSlots, this.realInventorySlots, 0);
      this.sendAllDataToRemote();
   }

   private void broadcastFullStateOf(NonNullList<ItemStack> lastSlotsCollection, List<Slot> slotsCollection, int slotIndexOffset) {
      for (int i = 0; i < slotsCollection.size(); i++) {
         Slot slot = slotsCollection.get(i);
         ItemStack itemstack = slot.getItem();
         this.triggerSlotListeners(i, itemstack, itemstack::copy, lastSlotsCollection, slotIndexOffset, slot);
      }
   }

   protected void triggerSlotListeners(
      int stackIndex, ItemStack slotStack, Supplier<ItemStack> slotStackCopy, NonNullList<ItemStack> lastSlotsCollection, int slotIndexOffset, Slot slot
   ) {
      ItemStack itemstack = (ItemStack)lastSlotsCollection.get(stackIndex);
      if (!ItemStack.matches(itemstack, slotStack)) {
         ItemStack stackCopy = slotStackCopy.get();
         lastSlotsCollection.set(stackIndex, stackCopy);

         for (ContainerListener containerlistener : this.containerListeners) {
            containerlistener.slotChanged(this, stackIndex + slotIndexOffset, stackCopy);
         }

         if (!this.initialBroadcast && this.isUpgradeSettingsSlot(slot.index)) {
            slot.setChanged();
         }
      }
   }

   public void sendAllDataToRemote() {
      for (int i = 0; i < this.getInventorySlotsSize(); i++) {
         this.remoteRealSlots.set(i, this.realInventorySlots.get(i).getItem().copy());
      }

      for (int i = 0; i < this.upgradeSlots.size(); i++) {
         this.remoteUpgradeSlots.set(i, this.upgradeSlots.get(i).getItem().copy());
      }

      NonNullList<ItemStack> allRemoteSlots = NonNullList.create();
      allRemoteSlots.addAll(this.remoteRealSlots);
      allRemoteSlots.addAll(this.remoteUpgradeSlots);
      this.remoteCarried = this.getCarried().copy();
      if (this.synchronizer != null) {
         this.synchronizer.sendInitialData(this, allRemoteSlots, this.remoteCarried, new int[0]);
      }

      this.sendEmptySlotIcons();
      this.sendAdditionalSlotInfo();
   }

   public boolean isInfiniteSlot(int slot) {
      return this.infiniteSlots.contains(slot);
   }

   private void sendEmptySlotIcons() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         HashMap var5 = new HashMap();

         for (int slot = 0; slot < this.storageWrapper.getInventoryHandler().getSlots(); slot++) {
            Pair<ResourceLocation, ResourceLocation> noItemIcon = this.storageWrapper.getInventoryHandler().getNoItemIcon(slot);
            if (noItemIcon != null) {
               var5.computeIfAbsent((ResourceLocation)noItemIcon.getSecond(), rl -> new HashSet<>()).add(slot);
            }
         }

         PacketDistributor.sendToPlayer(serverPlayer, new SyncEmptySlotIconsPayload(var5), new CustomPacketPayload[0]);
      }
   }

   private void sendAdditionalSlotInfo() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         HashSet var10 = new HashSet();
         HashMap slotLimitOverrides = new HashMap();
         HashSet infiniteSlots = new HashSet();
         InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();
         HashMap slotFilterItems = new HashMap();

         for (int slot = 0; slot < inventoryHandler.getSlots(); slot++) {
            if (!inventoryHandler.isSlotAccessible(slot)) {
               var10.add(slot);
            }

            if (inventoryHandler.isInfinite(slot)) {
               infiniteSlots.add(slot);
            }

            ItemStack stackInSlot = inventoryHandler.getStackInSlot(slot);
            int stackLimit = inventoryHandler.getStackLimit(slot, stackInSlot);
            if (stackLimit != inventoryHandler.getBaseStackLimit(stackInSlot)) {
               slotLimitOverrides.put(slot, stackLimit);
            }

            if (inventoryHandler.getFilterItem(slot) != Items.AIR) {
               slotFilterItems.put(slot, inventoryHandler.getFilterItem(slot).builtInRegistryHolder());
            }
         }

         PacketDistributor.sendToPlayer(
            serverPlayer, new SyncAdditionalSlotInfoPayload(var10, slotLimitOverrides, infiniteSlots, slotFilterItems), new CustomPacketPayload[0]
         );
      }
   }

   public void setRemoteSlot(int slotIndex, ItemStack stack) {
      if (slotIndex < this.getInventorySlotsSize()) {
         this.remoteRealSlots.set(slotIndex, stack.copy());
      } else {
         this.remoteUpgradeSlots.set(slotIndex, stack.copy());
      }
   }

   public void setRemoteSlotNoCopy(int slotIndex, ItemStack stack) {
      if (slotIndex < this.getInventorySlotsSize()) {
         ItemStack previous = (ItemStack)this.remoteRealSlots.get(slotIndex);
         this.remoteRealSlots.set(slotIndex, stack);
         if (this.isStorageInventorySlot(slotIndex) && (previous.isEmpty() || stack.isEmpty())) {
            this.inventorySlotStackChanged = true;
         }
      } else {
         this.remoteUpgradeSlots.set(slotIndex - this.inventorySlotsBeforeClickHandled, stack);
      }
   }

   public OptionalInt findSlot(Container container, int slotIdx) {
      for (int i = 0; i < this.getTotalSlotsNumber(); i++) {
         Slot slot = this.getSlot(i);
         if (slot.container == container && slotIdx == slot.getContainerSlot()) {
            return OptionalInt.of(i);
         }
      }

      return OptionalInt.empty();
   }

   private void refreshAllSlots() {
      this.slots.clear();
      this.lastSlots.clear();
      this.realInventorySlots.clear();
      this.lastRealSlots.clear();
      this.remoteRealSlots.clear();
      this.upgradeSlots.clear();
      this.lastUpgradeSlots.clear();
      this.remoteUpgradeSlots.clear();
      this.upgradeContainers.clear();
      this.initSlotsAndContainers(this.player, this.storageItemSlotIndex, this.shouldLockStorageItemSlot, this.extraSlots);
      this.slotsChangedSinceStartOfClick = true;
   }

   protected ItemStack processOverflowLogic(ItemStack stack) {
      ItemStack result = stack;

      for (IOverflowResponseUpgrade overflowUpgrade : this.storageWrapper
         .getUpgradeHandler()
         .getWrappersThatImplementFromMainStorage(IOverflowResponseUpgrade.class)) {
         if (overflowUpgrade.worksInGui()) {
            result = overflowUpgrade.onSlotOverflow(result);
            if (result.isEmpty()) {
               break;
            }
         }
      }

      return result;
   }

   private void onSwapCraft(Slot slot, int numItemsCrafted) {
      try {
         ON_SWAP_CRAFT.invoke(slot, numItemsCrafted);
      } catch (IllegalAccessException | InvocationTargetException e) {
         SophisticatedCore.LOGGER.error("Error invoking onSwapCraft method in Slot class", e);
      }
   }

   public static int getQuickCraftPlaceCount(Slot slot, int quickCraftSlotsSize, int quickCraftingType, ItemStack carriedStack) {
      return Math.min(slot.getMaxStackSize(carriedStack), switch (quickCraftingType) {
         case 0 -> Mth.floor((float)carriedStack.getCount() / quickCraftSlotsSize);
         case 1 -> 1;
         case 2 -> carriedStack.getMaxStackSize();
         default -> carriedStack.getCount();
      });
   }

   protected void doClick(int slotId, int dragType, ClickType clickType, Player player) {
      if (slotId < this.getTotalSlotsNumber()) {
         this.slotsChangedSinceStartOfClick = false;
         Inventory inventory = player.getInventory();
         if (clickType == ClickType.QUICK_CRAFT) {
            int i = this.quickcraftStatus;
            this.quickcraftStatus = getQuickcraftHeader(dragType);
            if ((i != 1 || this.quickcraftStatus != 2) && i != this.quickcraftStatus) {
               this.resetQuickCraft();
            } else if (this.getCarried().isEmpty()) {
               this.resetQuickCraft();
            } else if (this.quickcraftStatus == 0) {
               this.quickcraftType = getQuickcraftType(dragType);
               if (isValidQuickcraftType(this.quickcraftType, player)) {
                  this.quickcraftStatus = 1;
                  this.quickcraftSlots.clear();
               } else {
                  this.resetQuickCraft();
               }
            } else if (this.quickcraftStatus == 1) {
               Slot slot = this.getSlot(slotId);
               ItemStack itemstack = this.getCarried();
               if (canItemQuickReplace(slot, itemstack)
                  && slot.mayPlace(itemstack)
                  && (this.quickcraftType == 2 || itemstack.getCount() > this.quickcraftSlots.size())
                  && this.canDragTo(slot)) {
                  this.quickcraftSlots.add(slot);
               }
            } else if (this.quickcraftStatus == 2) {
               if (!this.quickcraftSlots.isEmpty()) {
                  if (this.quickcraftSlots.size() == 1) {
                     int l = ((Slot)this.quickcraftSlots.iterator().next()).index;
                     this.resetQuickCraft();
                     this.clicked(l, this.quickcraftType, ClickType.PICKUP, player);
                     return;
                  }

                  ItemStack carried = this.getCarried().copy();
                  int j1 = this.getCarried().getCount();

                  for (Slot slot1 : this.quickcraftSlots) {
                     ItemStack itemstack1 = this.getCarried();
                     if (slot1 != null
                        && canItemQuickReplace(slot1, itemstack1)
                        && slot1.mayPlace(itemstack1)
                        && (this.quickcraftType == 2 || itemstack1.getCount() >= this.quickcraftSlots.size())
                        && this.canDragTo(slot1)) {
                        ItemStack carriedCopy = carried.copy();
                        int j = slot1.hasItem() ? slot1.getItem().getCount() : 0;
                        int slotStackLimit = slot1.getMaxStackSize(carriedCopy);
                        if (!(slot1 instanceof StorageInventorySlot) && slotStackLimit > carriedCopy.getMaxStackSize()) {
                           slotStackLimit = carriedCopy.getMaxStackSize();
                        }

                        int l = Math.min(
                           MathHelper.intMaxCappedAddition(getQuickCraftPlaceCount(slot1, this.quickcraftSlots.size(), this.quickcraftType, carriedCopy), j),
                           slotStackLimit
                        );
                        j1 -= l - j;
                        slot1.setByPlayer(carriedCopy.copyWithCount(l));
                     }
                  }

                  carried.setCount(j1);
                  this.setCarried(carried);
               }

               this.resetQuickCraft();
            } else {
               this.resetQuickCraft();
            }
         } else if (this.quickcraftStatus != 0) {
            this.resetQuickCraft();
         } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (dragType == 0 || dragType == 1)) {
            ClickAction clickaction = dragType == 0 ? ClickAction.PRIMARY : ClickAction.SECONDARY;
            if (slotId == -999) {
               if (!this.getCarried().isEmpty()) {
                  if (clickaction == ClickAction.PRIMARY) {
                     player.drop(this.getCarried(), true);
                     this.setCarried(ItemStack.EMPTY);
                  } else {
                     player.drop(this.getCarried().split(1), true);
                  }
               }
            } else if (clickType == ClickType.QUICK_MOVE) {
               if (slotId < 0) {
                  return;
               }

               Slot slot6 = this.getSlot(slotId);
               if (!slot6.mayPickup(player)) {
                  return;
               }

               if (this.isStorageInventorySlot(slotId)) {
                  this.quickMoveStack(this.player, slotId).copy();
               } else {
                  ItemStack itemstack8 = this.quickMoveStack(this.player, slotId);
                  if (!this.getOpenOrFirstCraftingContainer(RecipeType.CRAFTING)
                     .map(rec$ -> ((ICraftingContainer)rec$).shouldRefillCraftingGrid())
                     .orElse(false)) {
                     while (!this.slotsChangedSinceStartOfClick && !itemstack8.isEmpty() && ItemStack.isSameItem(slot6.getItem(), itemstack8)) {
                        itemstack8 = this.quickMoveStack(this.player, slotId);
                     }
                  } else {
                     int i = 1;

                     for (int maxStackSize = itemstack8.getMaxStackSize();
                        !itemstack8.isEmpty() && ItemStack.isSameItemSameComponents(slot6.getItem(), itemstack8) && i < maxStackSize;
                        i++
                     ) {
                        itemstack8 = this.quickMoveStack(this.player, slotId);
                     }
                  }
               }
            } else {
               if (slotId < 0) {
                  return;
               }

               Slot slot7 = this.getSlot(slotId);
               ItemStack slotStack = slot7.getItem();
               ItemStack carriedStack = this.getCarried();
               player.updateTutorialInventoryAction(carriedStack, slot7.getItem(), clickaction);
               if (!carriedStack.overrideStackedOnOther(slot7, clickaction, player)
                  && !slotStack.overrideOtherStackedOnMe(carriedStack, slot7, clickaction, player, this.createCarriedSlotAccess())) {
                  if (slotStack.isEmpty()) {
                     if (!carriedStack.isEmpty()) {
                        int l2 = clickaction == ClickAction.PRIMARY ? carriedStack.getCount() : 1;
                        this.setCarried(slot7.safeInsert(carriedStack, l2));
                     }
                  } else if (slot7.mayPickup(player)) {
                     if (carriedStack.isEmpty()) {
                        int countToRemove = Math.min(slotStack.getCount(), slotStack.getMaxStackSize());
                        if (clickaction == ClickAction.SECONDARY) {
                           countToRemove = countToRemove / 2 + countToRemove % 2;
                        }

                        Optional<ItemStack> optional1 = slot7.tryRemove(countToRemove, Integer.MAX_VALUE, player);
                        optional1.ifPresent(p_150421_ -> {
                           this.setCarried(p_150421_);
                           slot7.onTake(player, p_150421_);
                        });
                     } else if (slot7.mayPlace(carriedStack)) {
                        if (ItemStack.isSameItemSameComponents(slotStack, carriedStack)) {
                           int j3 = clickaction == ClickAction.PRIMARY ? carriedStack.getCount() : 1;
                           this.setCarried(slot7.safeInsert(carriedStack, j3));
                        } else if (carriedStack.getCount() <= slot7.getMaxStackSize(carriedStack) && slotStack.getCount() <= slotStack.getMaxStackSize()) {
                           slot7.set(carriedStack);
                           this.setCarried(slotStack);
                        }
                     } else if (ItemStack.isSameItemSameComponents(slotStack, carriedStack)) {
                        Optional<ItemStack> optional = slot7.tryRemove(slotStack.getCount(), carriedStack.getMaxStackSize() - carriedStack.getCount(), player);
                        optional.ifPresent(p_150428_ -> {
                           carriedStack.grow(p_150428_.getCount());
                           slot7.onTake(player, p_150428_);
                        });
                     }
                  }
               }

               slot7.setChanged();
            }
         } else if (clickType == ClickType.SWAP) {
            Slot slot2 = this.getSlot(slotId);
            ItemStack itemstack4 = inventory.getItem(dragType);
            ItemStack slotStack = slot2.getItem();
            if (!itemstack4.isEmpty() || !slotStack.isEmpty()) {
               if (itemstack4.isEmpty()) {
                  if (slot2.mayPickup(player)) {
                     if (slotStack.getCount() <= slotStack.getMaxStackSize()) {
                        inventory.setItem(dragType, slotStack.copy());
                        this.onSwapCraft(slot2, slotStack.getCount());
                        slot2.set(ItemStack.EMPTY);
                        slot2.onTake(player, slotStack);
                     } else {
                        inventory.setItem(dragType, slotStack.copyWithCount(slotStack.getMaxStackSize()));
                        slot2.set(slotStack.copyWithCount(slotStack.getCount() - slotStack.getMaxStackSize()));
                     }
                  }
               } else if (slotStack.isEmpty()) {
                  if (slot2.mayPlace(itemstack4)) {
                     int l1 = slot2.getMaxStackSize(itemstack4);
                     if (itemstack4.getCount() > l1) {
                        slot2.set(itemstack4.split(l1));
                     } else {
                        slot2.set(itemstack4);
                        inventory.setItem(dragType, ItemStack.EMPTY);
                     }
                  }
               } else if (slotStack.getCount() <= slotStack.getMaxStackSize() && slot2.mayPickup(player) && slot2.mayPlace(itemstack4)) {
                  int i2 = slot2.getMaxStackSize(itemstack4);
                  if (itemstack4.getCount() > i2) {
                     slot2.set(itemstack4.split(i2));
                     slot2.onTake(player, slotStack);
                     if (!inventory.add(slotStack)) {
                        player.drop(slotStack, true);
                     }
                  } else {
                     ItemStack slotStackCopy = slotStack.copy();
                     slot2.set(itemstack4);
                     inventory.setItem(dragType, slotStackCopy);
                     slot2.onTake(player, slotStackCopy);
                  }
               }
            }
         } else if (clickType == ClickType.CLONE && player.getAbilities().instabuild && this.getCarried().isEmpty() && slotId >= 0) {
            Slot slot5 = this.getSlot(slotId);
            if (slot5.hasItem()) {
               ItemStack itemstack6 = slot5.getItem().copy();
               itemstack6.setCount(itemstack6.getMaxStackSize());
               this.setCarried(itemstack6);
            }
         } else if (clickType == ClickType.THROW && this.getCarried().isEmpty() && slotId >= 0) {
            Slot slot4 = this.getSlot(slotId);
            int i1 = dragType == 0 ? 1 : slot4.getItem().getCount();
            ItemStack itemstack8 = slot4.safeTake(i1, slot4.getItem().getMaxStackSize(), player);
            player.drop(itemstack8, true);
         } else if (clickType == ClickType.PICKUP_ALL && slotId >= 0) {
            Slot slot3 = this.getSlot(slotId);
            ItemStack carriedStack = this.getCarried();
            if (!carriedStack.isEmpty() && (!slot3.hasItem() || !slot3.mayPickup(player))) {
               int k1 = dragType == 0 ? 0 : this.getInventorySlotsSize() - 1;
               int j2 = dragType == 0 ? 1 : -1;

               for (int k2 = 0; k2 < 2; k2++) {
                  for (int k3 = k1; k3 >= 0 && k3 < this.getInventorySlotsSize() && carriedStack.getCount() < carriedStack.getMaxStackSize(); k3 += j2) {
                     Slot slot8 = this.getSlot(k3);
                     if (slot8.hasItem()
                        && canItemQuickReplace(slot8, carriedStack)
                        && slot8.mayPickup(player)
                        && this.canTakeItemForPickAll(carriedStack, slot8)) {
                        ItemStack itemstack12 = slot8.getItem();
                        if (k2 != 0 || itemstack12.getCount() != itemstack12.getMaxStackSize()) {
                           ItemStack itemstack13 = slot8.safeTake(itemstack12.getCount(), carriedStack.getMaxStackSize() - carriedStack.getCount(), player);
                           carriedStack.grow(itemstack13.getCount());
                        }
                     }
                  }
               }

               k1 = dragType == 0 ? 0 : this.upgradeSlots.size() - 1;

               for (int j = 0; j < 2; j++) {
                  for (int upgradeSlotId = k1;
                     upgradeSlotId >= 0 && upgradeSlotId < this.upgradeSlots.size() && carriedStack.getCount() < carriedStack.getMaxStackSize();
                     upgradeSlotId += j2
                  ) {
                     Slot upgradeSlot = this.upgradeSlots.get(upgradeSlotId);
                     if (upgradeSlot.hasItem()
                        && canItemQuickReplace(upgradeSlot, carriedStack)
                        && upgradeSlot.mayPickup(this.player)
                        && this.canTakeItemForPickAll(carriedStack, upgradeSlot)) {
                        ItemStack itemstack3 = upgradeSlot.getItem();
                        if (j != 0 || itemstack3.getCount() != itemstack3.getMaxStackSize()) {
                           int l = Math.min(carriedStack.getMaxStackSize() - carriedStack.getCount(), itemstack3.getCount());
                           ItemStack upgradeStack = upgradeSlot.remove(l);
                           carriedStack.grow(l);
                           if (upgradeStack.isEmpty()) {
                              upgradeSlot.set(ItemStack.EMPTY);
                           }

                           upgradeSlot.onTake(this.player, upgradeStack);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
      return this.isUpgradeSlot(slot.index) && slot.getItem().getItem() instanceof IUpgradeItem<?> upgradeItem && upgradeItem.getInventoryColumnsTaken() > 0
         ? false
         : super.canTakeItemForPickAll(stack, slot);
   }

   public void removed(Player player) {
      for (Slot slot : this.upgradeSlots) {
         if (!(slot instanceof StorageContainerMenuBase.StorageUpgradeSlot)
            && this.isInventorySlotInUpgradeTab(player, slot)
            && this.shouldSlotItemBeDroppedFromStorage(slot)) {
            ItemStack slotStack = slot.getItem();
            slot.set(ItemStack.EMPTY);
            if (!player.addItem(slotStack)) {
               player.drop(slotStack, false);
            }
         }
      }

      super.removed(player);
      if (!player.level().isClientSide) {
         this.removeOpenTabIfKeepOff();
      }
   }

   protected ItemStack mergeItemStack(
      ItemStack sourceStack, int startIndex, int endIndex, boolean reverseDirection, boolean transferMaxStackSizeFromSource, boolean runOverflowLogic
   ) {
      int i = startIndex;
      if (reverseDirection) {
         i = endIndex - 1;
      }

      ItemStack result = sourceStack.copy();
      int toTransfer = transferMaxStackSizeFromSource ? Math.min(result.getMaxStackSize(), result.getCount()) : result.getCount();
      if (runOverflowLogic || result.isStackable() || this.getSlot(startIndex).getMaxStackSize() > 64) {
         while (toTransfer > 0 && (reverseDirection ? i >= startIndex : i < endIndex)) {
            Slot slot = this.getSlot(i);
            if (slot.mayPlace(result)) {
               ItemStack destStack = slot.getItem();
               if (!destStack.isEmpty() && ItemStack.isSameItemSameComponents(result, destStack)) {
                  int maxSize = slot.getMaxStackSize(result);
                  if (destStack.getCount() <= maxSize - toTransfer) {
                     result.shrink(toTransfer);
                     ItemStack copy = destStack.copy();
                     copy.setCount(destStack.getCount() + toTransfer);
                     slot.set(copy);
                     toTransfer = 0;
                     slot.setChanged();
                  } else if (destStack.getCount() < maxSize) {
                     result.shrink(maxSize - destStack.getCount());
                     toTransfer -= maxSize - destStack.getCount();
                     ItemStack copy = destStack.copy();
                     copy.setCount(maxSize);
                     slot.set(copy);
                     slot.setChanged();
                  }

                  if (runOverflowLogic && !result.isEmpty()) {
                     ItemStack overflowResult = this.processOverflowLogic(result);
                     if (overflowResult != result) {
                        result.setCount(overflowResult.getCount());
                     }
                  }
               }
            }

            if (reverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (toTransfer > 0) {
         int firstIndex = reverseDirection ? endIndex - 1 : startIndex;
         int increment = reverseDirection ? -1 : 1;
         MemorySettingsCategory memory = this.storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class);

         for (int slotIndex = firstIndex; (reverseDirection ? slotIndex >= startIndex : slotIndex < endIndex) && toTransfer > 0; slotIndex += increment) {
            if (memory.isSlotSelected(slotIndex) && memory.matchesFilter(slotIndex, result)) {
               Slot slot = this.getSlot(slotIndex);
               if (slot.mayPlace(result)) {
                  ItemStack destStack = slot.getItem();
                  if (destStack.isEmpty()) {
                     slot.set(result.split(slot.getMaxStackSize()));
                     slot.setChanged();
                     toTransfer = result.getCount();
                  }
               }
            }
         }
      }

      if (toTransfer > 0) {
         if (reverseDirection) {
            i = endIndex - 1;
         } else {
            i = startIndex;
         }

         while (reverseDirection ? i >= startIndex : i < endIndex) {
            Slot destSlot = this.getSlot(i);
            ItemStack itemstack1 = destSlot.getItem();
            if (itemstack1.isEmpty() && destSlot.mayPlace(result) && !(destSlot instanceof IFilterSlot)) {
               boolean errorMerging = false;
               if (toTransfer > destSlot.getMaxStackSize()) {
                  if (runOverflowLogic && this.processOverflowIfSlotWithSameItemFound(result, s -> {})) {
                     result.shrink(result.getCount());
                  } else if (this.isUpgradeSlot(i)) {
                     IUpgradeItem<?> upgradeItem = (IUpgradeItem<?>)result.getItem();
                     int newColumnsTaken = upgradeItem.getInventoryColumnsTaken();
                     if (!this.needsSlotsThatAreOccupied(result, 0, newColumnsTaken)) {
                        destSlot.set(result.split(destSlot.getMaxStackSize()));
                        this.updateColumnsTaken(newColumnsTaken);
                     } else {
                        errorMerging = true;
                     }
                  } else {
                     destSlot.set(result.split(destSlot.getMaxStackSize()));
                  }
               } else if (this.isUpgradeSlot(i)) {
                  IUpgradeItem<?> upgradeItem = (IUpgradeItem<?>)result.getItem();
                  int newColumnsTaken = upgradeItem.getInventoryColumnsTaken();
                  if (!this.needsSlotsThatAreOccupied(result, 0, newColumnsTaken)) {
                     destSlot.set(result.split(toTransfer));
                     this.updateColumnsTaken(newColumnsTaken);
                     if (this.isClientSide()) {
                        this.onUpgradesChanged();
                     }
                  } else {
                     errorMerging = true;
                  }
               } else if (runOverflowLogic && this.processOverflowIfSlotWithSameItemFound(result, s -> {})) {
                  result.shrink(result.getCount());
               } else {
                  destSlot.set(result.split(toTransfer));
               }

               if (!errorMerging) {
                  destSlot.setChanged();
                  break;
               }
            }

            if (reverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      return result;
   }

   protected boolean moveItemStackTo(Slot sourceSlot, ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
      return this.mergeItemStack(sourceSlot, stack, startIndex, endIndex, reverseDirection, false);
   }

   protected boolean mergeItemStack(
      Slot sourceSlot, ItemStack sourceStack, int startIndex, int endIndex, boolean reverseDirection, boolean transferMaxStackSizeFromSource
   ) {
      ItemStack remaining = this.mergeItemStack(sourceStack, startIndex, endIndex, reverseDirection, transferMaxStackSizeFromSource, false);
      if (remaining.getCount() != sourceStack.getCount()) {
         sourceSlot.set(remaining);
         return true;
      } else {
         return false;
      }
   }

   public void setSynchronizer(ContainerSynchronizer synchronizer) {
      if (this.player instanceof ServerPlayer serverPlayer) {
         super.setSynchronizer(new HighStackCountSynchronizer(serverPlayer));
      } else {
         super.setSynchronizer(synchronizer);
      }
   }

   public static boolean canItemQuickReplace(@Nullable Slot slot, ItemStack stack) {
      boolean flag = slot == null || !slot.hasItem();
      return !flag && ItemStack.isSameItemSameComponents(stack, slot.getItem()) ? slot.getItem().getCount() <= slot.getMaxStackSize(stack) : flag;
   }

   public Slot getSlot(int slotId) {
      if (slotId >= this.getInventorySlotsSize()) {
         int upgradeSlotId = slotId - this.getInventorySlotsSize();
         return this.upgradeSlots.size() > upgradeSlotId ? this.upgradeSlots.get(upgradeSlotId) : DummySlot.INSTANCE;
      } else {
         return this.realInventorySlots.get(slotId);
      }
   }

   public void setItem(int slotId, int stateId, ItemStack stack) {
      if (this.getTotalSlotsNumber() > slotId) {
         super.setItem(slotId, stateId, stack);
      }
   }

   public void broadcastChanges() {
      if (this.hasSomethingMessedWithStorage()) {
         this.player.closeContainer();
      } else {
         this.synchronizeCarriedToRemote();
         this.broadcastChangesIn(this.lastUpgradeSlots, this.remoteUpgradeSlots, this.upgradeSlots, this.getFirstUpgradeSlot());
         this.broadcastChangesIn(this.lastRealSlots, this.remoteRealSlots, this.realInventorySlots, 0);
         if (this.inventorySlotStackChanged) {
            this.inventorySlotStackChanged = false;
            this.sendAdditionalSlotInfo();
         }

         if (this.lastSettingsNbt == null || !this.lastSettingsNbt.equals(this.storageWrapper.getSettingsHandler().getNbt())) {
            this.lastSettingsNbt = this.storageWrapper.getSettingsHandler().getNbt().copy();
            this.sendStorageSettingsToClient();
            this.refreshInventorySlotsIfNeeded();
         }

         this.initialBroadcast = false;
      }
   }

   public Optional<ItemStack> getVisibleStorageItem() {
      return this.storageItemSlotNumber != -1 ? Optional.of(this.getSlot(this.storageItemSlotNumber).getItem()) : Optional.empty();
   }

   private void broadcastChangesIn(
      NonNullList<ItemStack> lastSlotsCollection, NonNullList<ItemStack> remoteSlotsCollection, List<Slot> slotsCollection, int slotIndexOffset
   ) {
      for (int i = 0; i < slotsCollection.size(); i++) {
         Slot slot = slotsCollection.get(i);
         ItemStack itemstack = slot.getItem();
         Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::copy);
         this.triggerSlotListeners(i, itemstack, supplier, lastSlotsCollection, slotIndexOffset, slot);
         this.synchronizeSlotToRemote(i, itemstack, supplier, remoteSlotsCollection, slotIndexOffset);
      }
   }

   private void synchronizeSlotToRemote(
      int slotIndex, ItemStack slotStack, Supplier<ItemStack> slotStackCopy, NonNullList<ItemStack> remoteSlotsCollection, int slotIndexOffset
   ) {
      if (!this.suppressRemoteUpdates) {
         ItemStack remoteStack = (ItemStack)remoteSlotsCollection.get(slotIndex);
         if (!ItemStack.matches(remoteStack, slotStack)) {
            ItemStack stackCopy = slotStackCopy.get();
            remoteSlotsCollection.set(slotIndex, stackCopy);
            if (this.isStorageInventorySlot(slotIndex + slotIndexOffset) && (remoteStack.isEmpty() || slotStack.isEmpty())) {
               this.inventorySlotStackChanged = true;
            }

            if (this.synchronizer != null) {
               this.synchronizer.sendSlotChange(this, slotIndex + slotIndexOffset, stackCopy);
            }
         }
      }
   }

   protected void refreshInventorySlotsIfNeeded() {
      Set<Integer> noSortSlotIndexes = this.getNoSortSlotIndexes();
      boolean needRefresh = false;
      if (this.getInventorySlotsSize() - this.slots.size() != noSortSlotIndexes.size()) {
         needRefresh = true;
      } else {
         for (Slot slot : this.realInventorySlots) {
            if (!this.slots.contains(slot) && !noSortSlotIndexes.contains(slot.index)) {
               needRefresh = true;
               break;
            }
         }
      }

      if (needRefresh) {
         this.slots.clear();
         this.lastSlots.clear();
         this.realInventorySlots.clear();
         this.lastRealSlots.clear();
         this.remoteRealSlots.clear();
         this.addStorageInventorySlots();
         this.addPlayerInventorySlots(this.player.getInventory(), this.storageItemSlotIndex, this.shouldLockStorageItemSlot);
         this.addExtraSlots(this.extraSlots);
      }
   }

   public NonNullList<ItemStack> getItems() {
      NonNullList<ItemStack> list = NonNullList.create();
      this.realInventorySlots.forEach(slot -> list.add(slot.getItem()));
      this.upgradeSlots.forEach(upgradeSlot -> list.add(upgradeSlot.getItem()));
      return list;
   }

   public abstract boolean detectSettingsChangeAndReload();

   protected boolean shouldSlotItemBeDroppedFromStorage(Slot slot) {
      return false;
   }

   private boolean isInventorySlotInUpgradeTab(Player player, Slot slot) {
      return slot.mayPickup(player) && !(slot instanceof ResultSlot);
   }

   private void reloadUpgradeControl(boolean removeOpenTabId) {
      if (!this.isUpdatingFromPacket && removeOpenTabId) {
         this.storageWrapper.removeOpenTabId();
      }

      NonNullList<ItemStack> previousLastUpgradeSlots = NonNullList.create();
      previousLastUpgradeSlots.addAll(this.lastUpgradeSlots);
      NonNullList<ItemStack> previousRemoteUpgradeSlots = NonNullList.create();
      previousRemoteUpgradeSlots.addAll(this.remoteUpgradeSlots);
      this.removeUpgradeSettingsSlots();
      this.upgradeContainers.clear();
      this.addUpgradeSettingsContainers(this.player);
      this.setPreviousLastAndRemoteSlots(previousLastUpgradeSlots, previousRemoteUpgradeSlots);
      this.onUpgradesChanged();
      this.sendEmptySlotIcons();
      this.sendAdditionalSlotInfo();
   }

   private void setPreviousLastAndRemoteSlots(NonNullList<ItemStack> previousLastUpgradeSlots, NonNullList<ItemStack> previousRemoteUpgradeSlots) {
      for (int i = 0; i < this.lastUpgradeSlots.size() && i < previousLastUpgradeSlots.size(); i++) {
         this.lastUpgradeSlots.set(i, (ItemStack)previousLastUpgradeSlots.get(i));
      }

      for (int i = 0; i < this.remoteUpgradeSlots.size() && i < previousRemoteUpgradeSlots.size(); i++) {
         this.remoteUpgradeSlots.set(i, (ItemStack)previousRemoteUpgradeSlots.get(i));
      }
   }

   private void removeUpgradeSettingsSlots() {
      List<Integer> slotNumbersToRemove = new ArrayList<>();

      for (UpgradeContainerBase<?, ?> container : this.upgradeContainers.values()) {
         container.getSlots().forEach(slot -> {
            int upgradeSlotIndex = slot.index - this.getInventorySlotsSize();
            slotNumbersToRemove.add(upgradeSlotIndex);
            this.upgradeSlots.remove(slot);
         });
      }

      slotNumbersToRemove.sort(IntComparators.OPPOSITE_COMPARATOR);

      for (int slotNumber : slotNumbersToRemove) {
         this.lastUpgradeSlots.remove(slotNumber);
         this.remoteUpgradeSlots.remove(slotNumber);
      }
   }

   private void onUpgradesChanged() {
      if (this.upgradeChangeListener != null) {
         this.upgradeChangeListener.accept(this);
      }
   }

   @Override
   public void updateAdditionalSlotInfo(
      Set<Integer> inaccessibleSlots, Map<Integer, Integer> slotLimitOverrides, Set<Integer> infiniteSlots, Map<Integer, Holder<Item>> slotFilterItems
   ) {
      this.inaccessibleSlots.clear();
      this.inaccessibleSlots.addAll(inaccessibleSlots);
      this.slotLimitOverrides.clear();
      this.slotLimitOverrides.putAll(slotLimitOverrides);
      this.infiniteSlots.clear();
      this.infiniteSlots.addAll(infiniteSlots);
      Set<Integer> noSort = this.getNoSortSlotIndexes();
      noSort.addAll(infiniteSlots);
      List<Slot> slotsToMakeIntoNoSort = new ArrayList<>();
      List<Slot> slotsToMakeSortable = new ArrayList<>();

      for (int i = 0; i < this.getNumberOfStorageInventorySlots(); i++) {
         Slot slot = this.realInventorySlots.get(i);
         if (noSort.contains(slot.index) && this.slots.contains(slot)) {
            slotsToMakeIntoNoSort.add(slot);
         } else if (!noSort.contains(slot.index) && !this.slots.contains(slot)) {
            slotsToMakeSortable.add(slot);
         }
      }

      slotsToMakeIntoNoSort.forEach(this.slots::remove);
      this.slots.addAll(slotsToMakeSortable);
      this.slotFilterItems.clear();
      slotFilterItems.forEach((slotx, item) -> this.slotFilterItems.put(slotx, new ItemStack(item)));
   }

   @Override
   public void updateEmptySlotIcons(Map<ResourceLocation, Set<Integer>> emptySlotIcons) {
      this.emptySlotIcons.clear();
      emptySlotIcons.forEach((textureName, slots) -> slots.forEach(slot -> this.emptySlotIcons.put(slot, new Pair(InventoryMenu.BLOCK_ATLAS, textureName))));
   }

   public ItemStack getSlotFilterItem(int slot) {
      return this.slotFilterItems.getOrDefault(slot, ItemStack.EMPTY);
   }

   public void updateSlotChangeError(UpgradeSlotChangeResult result) {
      this.errorUpgradeSlotChangeResult = result;
      if (this.player.level().isClientSide() && this.errorUpgradeSlotChangeResult.successful() && this.columnsChange != 0) {
         this.actuallyUpdateColumnsTaken(this.columnsChange);
         this.onUpgradesChanged();
      }

      this.columnsChange = 0;
      this.showUpgradeSlotChangeError();
   }

   private void showUpgradeSlotChangeError() {
      if (this.errorUpgradeSlotChangeResult != null && !this.tryingToMergeUpgrade) {
         if (this.player.level().isClientSide()) {
            if (!this.errorUpgradeSlotChangeResult.successful()) {
               this.errorResultExpirationTime = this.player.level().getGameTime() + 60L;
            }
         } else if (this.player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new SyncSlotChangeErrorPayload(this.errorUpgradeSlotChangeResult), new CustomPacketPayload[0]);
         }
      }
   }

   public void transferItemsToPlayerInventory(boolean filterByContents) {
      PacketDistributor.sendToServer(new TransferItemsPayload(true, filterByContents), new CustomPacketPayload[0]);
   }

   public void transferItemsToStorage(boolean filterByContents) {
      PacketDistributor.sendToServer(new TransferItemsPayload(false, filterByContents), new CustomPacketPayload[0]);
   }

   public class StorageUpgradeSlot extends SlotItemHandler {
      private boolean wasEmpty = true;
      private final int slotIndex;

      public StorageUpgradeSlot(UpgradeHandler upgradeHandler, int slotIndex) {
         super(upgradeHandler, slotIndex, -15, 0);
         this.slotIndex = slotIndex;
         this.wasEmpty = this.getItem().isEmpty();
      }

      public void setChanged() {
         super.setChanged();
         StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult reloadCheckResult = this.updateWrappersAndCheckForReloadNeeded();
         if (reloadCheckResult.reloadNeeded()) {
            StorageContainerMenuBase.this.reloadUpgradeControl(reloadCheckResult.removeOpenTabId());
            if (!StorageContainerMenuBase.this.isFirstLevelStorage()) {
               StorageContainerMenuBase.this.parentStorageWrapper.getUpgradeHandler().refreshUpgradeWrappers();
            }

            this.onUpgradeChanged();
         }

         this.wasEmpty = this.getItem().isEmpty();
      }

      public void set(ItemStack stack) {
         super.set(stack);
         this.wasEmpty = this.getItem().isEmpty();
      }

      protected void onUpgradeChanged() {
      }

      public boolean mayPlace(ItemStack stack) {
         if (!stack.isEmpty() && this.getItemHandler().isItemValid(this.slotIndex, stack)) {
            UpgradeSlotChangeResult result;
            if (this.getItem().isEmpty()) {
               result = ((IUpgradeItem)stack.getItem())
                  .canAddUpgradeTo(
                     StorageContainerMenuBase.this.storageWrapper,
                     stack,
                     StorageContainerMenuBase.this.isFirstLevelStorage(),
                     StorageContainerMenuBase.this.player.level().isClientSide()
                  );
            } else {
               if (stack.getCount() > 1) {
                  return false;
               }

               result = ((IUpgradeItem)this.getItem().getItem())
                  .canSwapUpgradeFor(
                     stack, this.slotIndex, StorageContainerMenuBase.this.storageWrapper, StorageContainerMenuBase.this.player.level().isClientSide()
                  );
            }

            StorageContainerMenuBase.this.updateSlotChangeError(result);
            return result.successful();
         } else {
            return false;
         }
      }

      public boolean mayPickup(Player player) {
         boolean ret = super.mayPickup(player);
         if (!ret) {
            return false;
         }

         UpgradeSlotChangeResult result = ((IUpgradeItem)this.getItem().getItem())
            .canRemoveUpgradeFrom(StorageContainerMenuBase.this.storageWrapper, player.level().isClientSide(), player);
         if (result.successful() && StorageContainerMenuBase.this.upgradeContainers.containsKey(this.slotIndex)) {
            Set<Integer> errorUpgradeSlots = StorageContainerMenuBase.this.upgradeContainers
               .get(this.slotIndex)
               .getSlots()
               .stream()
               .filter(slot -> !(slot instanceof IFilterSlot) && StorageContainerMenuBase.this.shouldSlotItemBeDroppedFromStorage(slot))
               .map(slot -> slot.getSlotIndex() + StorageContainerMenuBase.this.getNumberOfUpgradeSlots())
               .collect(Collectors.toSet());
            if (!errorUpgradeSlots.isEmpty()) {
               result = UpgradeSlotChangeResult.fail(
                  TranslationHelper.INSTANCE.translError("remove.banned_item"), errorUpgradeSlots, Collections.emptySet(), Collections.emptySet()
               );
            }
         }

         StorageContainerMenuBase.this.updateSlotChangeError(result);
         return result.successful();
      }

      private StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult updateWrappersAndCheckForReloadNeeded() {
         if (!StorageContainerMenuBase.this.isUpdatingFromPacket && this.wasEmpty != this.getItem().isEmpty()) {
            return StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED;
         }

         int checkedContainersCount = 0;

         for (Entry<Integer, IUpgradeWrapper> slotWrapper : StorageContainerMenuBase.this.storageWrapper.getUpgradeHandler().getSlotWrappers().entrySet()) {
            UpgradeContainerBase<?, ?> container = StorageContainerMenuBase.this.upgradeContainers.get(slotWrapper.getKey());
            if (!slotWrapper.getValue().hideSettingsTab()) {
               if (container == null || container.getUpgradeWrapper().isEnabled() != slotWrapper.getValue().isEnabled()) {
                  return StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED;
               }

               if (container.getUpgradeWrapper() != slotWrapper.getValue()) {
                  if (!StorageContainerMenuBase.this.player.level().isClientSide()
                     || container.getUpgradeWrapper().getUpgradeStack().getItem() != slotWrapper.getValue().getUpgradeStack().getItem()) {
                     if (container.getUpgradeWrapper().getUpgradeStack().getItem() == slotWrapper.getValue().getUpgradeStack().getItem()) {
                        return StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED_KEEP_TAB;
                     }

                     return StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED;
                  }

                  container.setUpgradeWrapper(slotWrapper.getValue());
                  checkedContainersCount++;
               } else {
                  checkedContainersCount++;
               }
            } else if (container != null) {
               return StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED;
            }
         }

         return checkedContainersCount != StorageContainerMenuBase.this.upgradeContainers.size()
            ? StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.RELOAD_NEEDED
            : StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult.NO_RELOAD_NEEDED;
      }

      @Nullable
      public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
         return new Pair(InventoryMenu.BLOCK_ATLAS, StorageContainerMenuBase.EMPTY_UPGRADE_SLOT_BACKGROUND);
      }

      private record ReloadCheckResult(boolean reloadNeeded, boolean removeOpenTabId) {
         public static final StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult NO_RELOAD_NEEDED = new StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult(
            false, false
         );
         public static final StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult RELOAD_NEEDED = new StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult(
            true, true
         );
         public static final StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult RELOAD_NEEDED_KEEP_TAB = new StorageContainerMenuBase.StorageUpgradeSlot.ReloadCheckResult(
            true, false
         );
      }
   }
}

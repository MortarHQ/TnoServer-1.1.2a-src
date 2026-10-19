package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IEnergyStorageUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IFluidHandlerWrapperUpgrade;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackTemplates;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModDataComponents;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryIOHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.inventory.StorageWrapperRepository;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.nosort.NoSortSettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.InventorySorter;
import net.p3pp3rf1y.sophisticatedcore.util.LootHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RandHelper;

public class BackpackWrapper implements IBackpackWrapper {
   public static final int DEFAULT_MAIN_COLOR = -3382982;
   public static final int DEFAULT_ACCENT_COLOR = -10342886;
   @Nullable
   private ItemStack backpack;
   private Runnable backpackSaveHandler = () -> {};
   private Runnable inventorySlotChangeHandler = () -> {};
   @Nullable
   private InventoryHandler handler = null;
   @Nullable
   private UpgradeHandler upgradeHandler = null;
   @Nullable
   private InventoryIOHandler inventoryIOHandler = null;
   @Nullable
   private InventoryModificationHandler inventoryModificationHandler = null;
   @Nullable
   private BackpackSettingsHandler settingsHandler = null;
   private boolean fluidHandlerInitialized = false;
   @Nullable
   private IStorageFluidHandler fluidHandler = null;
   private boolean energyStorageInitialized = false;
   @Nullable
   private IEnergyStorage energyStorage = null;
   @Nullable
   private BackpackRenderInfo renderInfo;
   private IntConsumer onSlotsChange = diff -> {};
   private Runnable onInventoryHandlerRefresh = () -> {};
   private Runnable upgradeCachesInvalidatedHandler = () -> {};
   private Runnable onInventoryForInputOutputHandlerRefresh = () -> {};

   public BackpackWrapper(ItemStack backpackStack) {
      this.setBackpackStack(backpackStack);
   }

   public static IBackpackWrapper fromStack(ItemStack stack) {
      return (IBackpackWrapper)StorageWrapperRepository.getStorageWrapper(stack, IBackpackWrapper.class, BackpackWrapper::new);
   }

   public static Optional<IBackpackWrapper> fromExistingData(ItemStack stack) {
      return stack.getItem() instanceof BackpackItem ? StorageWrapperRepository.getExistingStorageWrapper(stack, IBackpackWrapper.class) : Optional.empty();
   }

   public void setContentsChangeHandler(Runnable contentsChangeHandler) {
      this.backpackSaveHandler = contentsChangeHandler;
      this.refreshInventoryForUpgradeProcessing();
   }

   public void setInventorySlotChangeHandler(Runnable slotChangeHandler) {
      this.inventorySlotChangeHandler = slotChangeHandler;
   }

   public ITrackedContentsItemHandler getInventoryForUpgradeProcessing() {
      if (this.inventoryModificationHandler == null) {
         this.inventoryModificationHandler = new InventoryModificationHandler(this);
      }

      return this.inventoryModificationHandler.getModifiedInventoryHandler();
   }

   public InventoryHandler getInventoryHandler() {
      if (this.handler == null) {
         this.handler = new BackpackInventoryHandler(
            this.getNumberOfInventorySlots() - this.getNumberOfSlotRows() * this.getColumnsTaken(), this, this.getBackpackContentsNbt(), () -> {
               this.markBackpackContentsDirty();
               if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
                  this.inventorySlotChangeHandler.run();
               }
            }, StackUpgradeItem.getInventorySlotLimit(this)
         );
         this.handler.addListener(((ItemDisplaySettingsCategory)this.getSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class))::itemChanged);
      }

      return this.handler;
   }

   private int getNumberOfInventorySlots() {
      Integer inventorySlots = (Integer)this.getBackpackStack().get(ModCoreDataComponents.NUMBER_OF_INVENTORY_SLOTS);
      if (inventorySlots != null) {
         return inventorySlots;
      }

      int itemInventorySlots = ((BackpackItem)this.getBackpackStack().getItem()).getNumberOfSlots();
      this.setNumberOfInventorySlots(itemInventorySlots);
      return itemInventorySlots;
   }

   public int getNumberOfSlotRows() {
      int itemInventorySlots = this.getNumberOfInventorySlots();
      return (int)Math.ceil(itemInventorySlots <= 81 ? itemInventorySlots / 9.0 : itemInventorySlots / 12.0);
   }

   private void setNumberOfInventorySlots(int itemInventorySlots) {
      this.getBackpackStack().set(ModCoreDataComponents.NUMBER_OF_INVENTORY_SLOTS, itemInventorySlots);
   }

   private CompoundTag getBackpackContentsNbt() {
      return BackpackStorage.get().getOrCreateBackpackContents(this.getOrCreateContentsUuid());
   }

   private void markBackpackContentsDirty() {
      BackpackStorage.get().setDirty();
   }

   public ITrackedContentsItemHandler getInventoryForInputOutput() {
      if (this.inventoryIOHandler == null) {
         this.inventoryIOHandler = new InventoryIOHandler(this);
      }

      return this.inventoryIOHandler.getFilteredItemHandler();
   }

   public Optional<IStorageFluidHandler> getFluidHandler() {
      if (!this.fluidHandlerInitialized) {
         IStorageFluidHandler wrappedHandler = this.getUpgradeHandler().getTypeWrappers(TankUpgradeItem.TYPE).isEmpty() ? null : new BackpackFluidHandler(this);

         for (IFluidHandlerWrapperUpgrade fluidHandlerWrapperUpgrade : this.getUpgradeHandler().getWrappersThatImplement(IFluidHandlerWrapperUpgrade.class)) {
            wrappedHandler = fluidHandlerWrapperUpgrade.wrapHandler(wrappedHandler, this.getBackpackStack());
         }

         this.fluidHandler = wrappedHandler;
         this.fluidHandlerInitialized = true;
      }

      return Optional.ofNullable(this.fluidHandler);
   }

   @Override
   public Optional<IFluidHandlerItem> getItemFluidHandler() {
      return this.getFluidHandler().map(fh -> new BackpackWrapper.FluidHandlerItemWrapper(this.getBackpackStack(), fh));
   }

   public Optional<IEnergyStorage> getEnergyStorage() {
      if (!this.energyStorageInitialized) {
         IEnergyStorage wrappedStorage = (IEnergyStorage)this.getUpgradeHandler()
            .getWrappersThatImplement(IEnergyStorage.class)
            .stream()
            .findFirst()
            .orElse(null);

         for (IEnergyStorageUpgradeWrapper energyStorageWrapperUpgrade : this.getUpgradeHandler().getWrappersThatImplement(IEnergyStorageUpgradeWrapper.class)) {
            wrappedStorage = energyStorageWrapperUpgrade.wrapStorage(wrappedStorage);
         }

         this.energyStorage = wrappedStorage;
      }

      return this.energyStorage != null && this.energyStorage.getMaxEnergyStored() != 0 ? Optional.of(this.energyStorage) : Optional.empty();
   }

   @Override
   public void copyDataTo(IStorageWrapper otherStorageWrapper) {
      this.getContentsUuid().ifPresent(originalUuid -> {
         this.getInventoryHandler().copyStacksTo(otherStorageWrapper.getInventoryHandler());
         this.getUpgradeHandler().copyTo(otherStorageWrapper.getUpgradeHandler());
         this.getSettingsHandler().copyTo(otherStorageWrapper.getSettingsHandler());
      });
   }

   @Override
   public IBackpackWrapper setBackpackStack(ItemStack backpack) {
      this.backpack = backpack;
      if (this.renderInfo == null) {
         this.renderInfo = new BackpackRenderInfo(backpack, () -> this.backpackSaveHandler);
      }

      return this;
   }

   @Override
   public BackpackSettingsHandler getSettingsHandler() {
      if (this.settingsHandler == null) {
         if (this.getContentsUuid().isPresent()) {
            this.settingsHandler = new BackpackSettingsHandler(this, this.getBackpackContentsNbt(), this::markBackpackContentsDirty);
         } else {
            this.settingsHandler = IBackpackWrapper.Noop.INSTANCE.getSettingsHandler();
         }
      }

      return this.settingsHandler;
   }

   public UpgradeHandler getUpgradeHandler() {
      if (this.upgradeHandler == null) {
         if (this.getContentsUuid().isPresent()) {
            this.upgradeHandler = new UpgradeHandler(
               this.getNumberOfUpgradeSlots(),
               this,
               this.getBackpackContentsNbt(),
               this::markBackpackContentsDirty,
               () -> {
                  if (this.handler != null) {
                     this.handler.clearListeners();
                     this.handler.setBaseSlotLimit(StackUpgradeItem.getInventorySlotLimit(this));
                  }

                  this.getInventoryHandler().clearListeners();
                  this.handler
                     .addListener(((ItemDisplaySettingsCategory)this.getSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class))::itemChanged);
                  this.inventoryIOHandler = null;
                  this.inventoryModificationHandler = null;
                  this.fluidHandlerInitialized = false;
                  this.fluidHandler = null;
                  this.energyStorageInitialized = false;
                  this.energyStorage = null;
                  this.upgradeCachesInvalidatedHandler.run();
               }
            ) {
               public boolean isItemValid(int slot, ItemStack stack) {
                  return super.isItemValid(slot, stack) && (stack.isEmpty() || stack.is(ModItems.BACKPACK_UPGRADE_TAG));
               }
            };
         } else {
            this.upgradeHandler = IBackpackWrapper.Noop.INSTANCE.getUpgradeHandler();
         }
      }

      return this.upgradeHandler;
   }

   public void setUpgradeCachesInvalidatedHandler(Runnable handler) {
      this.upgradeCachesInvalidatedHandler = handler;
   }

   private int getNumberOfUpgradeSlots() {
      Integer upgradeSlots = (Integer)this.getBackpackStack().get(ModCoreDataComponents.NUMBER_OF_UPGRADE_SLOTS);
      if (upgradeSlots != null) {
         return upgradeSlots;
      }

      int itemUpgradeSlots = ((BackpackItem)this.getBackpackStack().getItem()).getNumberOfUpgradeSlots();
      this.setNumberOfUpgradeSlots(itemUpgradeSlots);
      return itemUpgradeSlots;
   }

   public Optional<UUID> getContentsUuid() {
      return Optional.ofNullable((UUID)this.getBackpackStack().get(ModCoreDataComponents.STORAGE_UUID));
   }

   private UUID getOrCreateContentsUuid() {
      Optional<UUID> contentsUuid = this.getContentsUuid();
      if (contentsUuid.isPresent()) {
         return contentsUuid.get();
      }

      this.clearDummyHandlers();
      UUID newUuid = UUID.randomUUID();
      this.setContentsUuid(newUuid);
      return newUuid;
   }

   private void clearDummyHandlers() {
      if (this.upgradeHandler == IBackpackWrapper.Noop.INSTANCE.getUpgradeHandler()) {
         this.upgradeHandler = null;
      }

      if (this.settingsHandler == IBackpackWrapper.Noop.INSTANCE.getSettingsHandler()) {
         this.settingsHandler = null;
      }
   }

   public int getMainColor() {
      return (Integer)this.getBackpackStack().getOrDefault(ModCoreDataComponents.MAIN_COLOR, -3382982);
   }

   public int getAccentColor() {
      return (Integer)this.getBackpackStack().getOrDefault(ModCoreDataComponents.ACCENT_COLOR, -10342886);
   }

   public Optional<Integer> getOpenTabId() {
      return Optional.ofNullable((Integer)this.getBackpackStack().get(ModCoreDataComponents.OPEN_TAB_ID));
   }

   public void setOpenTabId(int openTabId) {
      this.getBackpackStack().set(ModCoreDataComponents.OPEN_TAB_ID, openTabId);
      this.backpackSaveHandler.run();
   }

   public void removeOpenTabId() {
      this.getBackpackStack().remove(ModCoreDataComponents.OPEN_TAB_ID);
      this.backpackSaveHandler.run();
   }

   public void setColors(int mainColor, int accentColor) {
      ItemStack backpackStack = this.getBackpackStack();
      BackpackItem.setColors(backpackStack, mainColor, accentColor);
      this.backpackSaveHandler.run();
   }

   public void setSortBy(SortBy sortBy) {
      this.getBackpackStack().set(ModCoreDataComponents.SORT_BY, sortBy);
      this.backpackSaveHandler.run();
   }

   public SortBy getSortBy() {
      return (SortBy)this.getBackpackStack().getOrDefault(ModCoreDataComponents.SORT_BY, SortBy.NAME);
   }

   public void sort() {
      Set<Integer> slotIndexesExcludedFromSort = new HashSet<>();
      slotIndexesExcludedFromSort.addAll(((NoSortSettingsCategory)this.getSettingsHandler().getTypeCategory(NoSortSettingsCategory.class)).getNoSortSlots());
      slotIndexesExcludedFromSort.addAll(((MemorySettingsCategory)this.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class)).getSlotIndexes());
      InventorySorter.sortHandler(this.getInventoryHandler(), this.getComparator(), slotIndexesExcludedFromSort);
   }

   private Comparator<Entry<ItemStackKey, Integer>> getComparator() {
      return switch (this.getSortBy()) {
         case COUNT -> InventorySorter.BY_COUNT;
         case TAGS -> InventorySorter.BY_TAGS;
         case NAME -> InventorySorter.BY_NAME;
         case MOD -> InventorySorter.BY_MOD;
         default -> throw new MatchException(null, null);
      };
   }

   @Override
   public ItemStack getBackpack() {
      return this.getBackpackStack();
   }

   @Override
   public ItemStack cloneBackpack() {
      ItemStack clonedBackpack = this.cloneBackpack(this);
      this.cloneSubbackpacks(fromStack(clonedBackpack));
      return clonedBackpack;
   }

   private void cloneSubbackpacks(IStorageWrapper wrapperCloned) {
      InventoryHandler inventoryHandler = wrapperCloned.getInventoryHandler();
      InventoryHelper.iterate(inventoryHandler, (slot, stack) -> {
         if (stack.getItem() instanceof BackpackItem) {
            inventoryHandler.setStackInSlot(slot, this.cloneBackpack(fromStack(stack)));
         }
      });
   }

   private ItemStack cloneBackpack(IBackpackWrapper originalWrapper) {
      ItemStack backpackCopy = originalWrapper.getBackpack().copy();
      backpackCopy.remove(ModCoreDataComponents.STORAGE_UUID);
      IBackpackWrapper wrapperCopy = fromStack(backpackCopy);
      originalWrapper.copyDataTo(wrapperCopy);
      return wrapperCopy.getBackpack();
   }

   public void refreshInventoryForInputOutput() {
      this.inventoryIOHandler = null;
      this.upgradeCachesInvalidatedHandler.run();
      this.onInventoryForInputOutputHandlerRefresh.run();
   }

   public void setPersistent(boolean persistent) {
      this.getInventoryHandler().setPersistent(persistent);
      this.getUpgradeHandler().setPersistent(persistent);
   }

   @Override
   public void setSlotNumbers(int numberOfInventorySlots, int numberOfUpgradeSlots) {
      this.setNumberOfInventorySlots(numberOfInventorySlots);
      this.setNumberOfUpgradeSlots(numberOfUpgradeSlots);
   }

   @Override
   public void setLoot(ResourceLocation lootTableName, float lootFactor) {
      this.getBackpackStack().set(ModDataComponents.LOOT_TABLE, lootTableName);
      this.getBackpackStack().set(ModDataComponents.LOOT_FACTOR, lootFactor);
      this.backpackSaveHandler.run();
   }

   @Override
   public void setTemplate(ResourceLocation templateName) {
      this.getBackpackStack().set(ModDataComponents.TEMPLATE_NAME, templateName);
   }

   public void fillWithLoot(Player player) {
      Level level = player.level();
      if (!level.isClientSide) {
         this.fillFromTemplate();
         this.fillWithLoot(level, player.blockPosition(), player);
         this.fillWithExtraItems(stack -> InventoryHelper.insertOrDropItem(player, stack, new IItemHandler[]{this.getInventoryHandler()}));
      }
   }

   private void fillWithExtraItems(Consumer<ItemStack> insertOrDropItem) {
      ItemStack backpack = this.getBackpackStack();
      if (backpack.has(DataComponents.CONTAINER)) {
         ItemContainerContents containerItems = (ItemContainerContents)backpack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

         for (int slot = 0; slot < containerItems.getSlots(); slot++) {
            ItemStack stack = containerItems.getStackInSlot(slot);
            if (!stack.isEmpty()) {
               insertOrDropItem.accept(stack);
            }
         }

         backpack.remove(DataComponents.CONTAINER);
      }
   }

   @Override
   public void fillWithLootAndExtraItems(Level level, BlockPos pos) {
      this.fillWithLoot(level, pos);
      this.fillWithExtraItems(stack -> {
         ItemStack remaining = InventoryHelper.insertIntoInventory(stack, this.getInventoryHandler(), false);
         if (!remaining.isEmpty()) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remaining);
         }
      });
   }

   public void fillWithLoot(Level level, BlockPos pos) {
      this.fillWithLoot(level, pos, null);
   }

   public void fillWithLoot(Level level, BlockPos pos, @Nullable Player player) {
      ResourceLocation lootTable = (ResourceLocation)this.getBackpackStack().get(ModDataComponents.LOOT_TABLE);
      if (lootTable != null) {
         this.fillWithLootFromTable(level, pos, lootTable, player);
      }
   }

   @Override
   public void fillFromTemplate() {
      ItemStack backpack = this.getBackpackStack();
      ResourceLocation templateName = (ResourceLocation)backpack.get(ModDataComponents.TEMPLATE_NAME);
      if (templateName != null) {
         Optional<CompoundTag> templateData = BackpackTemplates.getBackpackTemplate(templateName);
         if (!templateData.isEmpty()) {
            CompoundTag backpackContent = templateData.get().getCompound("backpackContents").copy();
            BackpackStorage.get().setBackpackContents(this.getOrCreateContentsUuid(), backpackContent);
            backpack.remove(ModDataComponents.TEMPLATE_NAME);
         }
      }
   }

   @Override
   public void setContentsUuid(UUID storageUuid) {
      this.getBackpackStack().set(ModCoreDataComponents.STORAGE_UUID, storageUuid);
   }

   @Override
   public void removeContentsUuid() {
      this.getContentsUuid().ifPresent(BackpackStorage.get()::removeBackpackContents);
      this.removeContentsUUIDTag();
   }

   @Override
   public void removeContentsUUIDTag() {
      this.getBackpackStack().remove(ModCoreDataComponents.STORAGE_UUID);
   }

   private ItemStack getBackpackStack() {
      if (this.backpack == null) {
         throw new IllegalStateException("Backpack stack not set");
      } else {
         return this.backpack;
      }
   }

   public BackpackRenderInfo getRenderInfo() {
      return this.renderInfo;
   }

   public void setColumnsTaken(int columnsTaken, boolean hasChanged) {
      int originalColumnsTaken = this.getColumnsTaken();
      this.getBackpackStack().set(ModDataComponents.COLUMNS_TAKEN, columnsTaken);
      if (hasChanged) {
         int diff = (columnsTaken - originalColumnsTaken) * this.getNumberOfSlotRows();
         this.onSlotsChange.accept(diff);
      }

      this.backpackSaveHandler.run();
   }

   @Override
   public void registerOnSlotsChangeListener(IntConsumer onSlotsChange) {
      this.onSlotsChange = onSlotsChange;
   }

   @Override
   public void unregisterOnSlotsChangeListener() {
      this.onSlotsChange = diff -> {};
   }

   public int getColumnsTaken() {
      return (Integer)this.getBackpackStack().getOrDefault(ModDataComponents.COLUMNS_TAKEN, 0);
   }

   private void fillWithLootFromTable(Level level, BlockPos pos, ResourceLocation lootTable, @Nullable Player player) {
      MinecraftServer server = level.getServer();
      if (server != null && level instanceof ServerLevel serverLevel) {
         float lootFactor = (Float)this.getBackpackStack().getOrDefault(ModDataComponents.LOOT_FACTOR, 0.0F);
         this.getBackpackStack().remove(ModDataComponents.LOOT_TABLE);
         this.getBackpackStack().remove(ModDataComponents.LOOT_FACTOR);
         ArrayList loot = new ArrayList();

         while (lootFactor > 0.0F) {
            List<ItemStack> generatedLoot = LootHelper.getLoot(lootTable, server, serverLevel, pos, player);
            generatedLoot.removeIf(stack -> stack.getItem() instanceof BackpackItem);
            loot.addAll(RandHelper.getNRandomElements(generatedLoot, (int)(generatedLoot.size() * (lootFactor > 1.0F ? 1.0F : lootFactor))));
            lootFactor--;
         }

         LootHelper.fillWithLoot(serverLevel.random, loot, this.getInventoryHandler());
      }
   }

   private void setNumberOfUpgradeSlots(int numberOfUpgradeSlots) {
      this.getBackpackStack().set(ModCoreDataComponents.NUMBER_OF_UPGRADE_SLOTS, numberOfUpgradeSlots);
   }

   public void refreshInventoryForUpgradeProcessing() {
      this.inventoryModificationHandler = null;
      this.fluidHandler = null;
      this.fluidHandlerInitialized = false;
      this.energyStorage = null;
      this.energyStorageInitialized = false;
      this.refreshInventoryForInputOutput();
   }

   public void onContentsNbtUpdated() {
      this.handler = null;
      this.upgradeHandler = null;
      this.refreshInventoryForUpgradeProcessing();
      this.onInventoryHandlerRefresh.run();
   }

   @Override
   public void registerOnInventoryHandlerRefreshListener(Runnable onInventoryHandlerRefresh) {
      this.onInventoryHandlerRefresh = onInventoryHandlerRefresh;
   }

   public void registerOnInventoryInputOutputHandlerRefreshListener(Runnable onInventoryForInputOutputHandlerRefresh) {
      this.onInventoryForInputOutputHandlerRefresh = onInventoryForInputOutputHandlerRefresh;
   }

   @Override
   public void unregisterOnInventoryHandlerRefreshListener() {
      this.onInventoryHandlerRefresh = () -> {};
   }

   public ItemStack getWrappedStorageStack() {
      return this.getBackpack();
   }

   public String getStorageType() {
      return "backpack";
   }

   public Component getDisplayName() {
      return this.getBackpack().getHoverName();
   }

   private static class FluidHandlerItemWrapper implements IFluidHandlerItem {
      private final IFluidHandler delegate;
      private final ItemStack container;

      public FluidHandlerItemWrapper(ItemStack container, IFluidHandler delegate) {
         this.container = container;
         this.delegate = delegate;
      }

      public ItemStack getContainer() {
         return this.container;
      }

      public int getTanks() {
         return this.delegate.getTanks();
      }

      public FluidStack getFluidInTank(int tank) {
         return this.delegate.getFluidInTank(tank);
      }

      public int getTankCapacity(int tank) {
         return this.delegate.getTankCapacity(tank);
      }

      public boolean isFluidValid(int tank, FluidStack stack) {
         return this.delegate.isFluidValid(tank, stack);
      }

      public int fill(FluidStack resource, FluidAction action) {
         return this.delegate.fill(resource, action);
      }

      public FluidStack drain(FluidStack resource, FluidAction action) {
         return this.delegate.drain(resource, action);
      }

      public FluidStack drain(int maxDrain, FluidAction action) {
         return this.delegate.drain(maxDrain, action);
      }
   }
}

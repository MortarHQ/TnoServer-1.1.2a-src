package net.p3pp3rf1y.sophisticatedcore.common.gui;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.network.SyncAdditionalSlotInfoPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerClientDataPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncEmptySlotIconsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncTemplateSettingsPayload;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsTemplateStorage;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsContainer;
import net.p3pp3rf1y.sophisticatedcore.settings.main.MainSettingsContainer;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsContainer;
import net.p3pp3rf1y.sophisticatedcore.settings.nosort.NoSortSettingsContainer;

public abstract class SettingsContainerMenu<S extends IStorageWrapper> extends AbstractContainerMenu implements ISyncedContainer, IAdditionalSlotInfoMenu {
   private static final Map<String, SettingsContainerMenu.ISettingsContainerFactory<?, ?>> SETTINGS_CONTAINER_FACTORIES = new HashMap<>();
   protected final Player player;
   protected final S storageWrapper;
   private final List<Slot> storageInventorySlots = new ArrayList<>();
   public final NonNullList<ItemStack> lastGhostSlots = NonNullList.create();
   public final NonNullList<ItemStack> remoteGhostSlots = NonNullList.create();
   private final Map<String, SettingsContainerBase<?>> settingsContainers = new LinkedHashMap<>();
   private final TemplatePersistanceContainer templatePersistanceContainer;
   public final List<Slot> ghostSlots = new ArrayList<>();
   private boolean inventorySlotStackChanged = false;
   private final Set<Integer> inaccessibleSlots = new HashSet<>();
   private final Map<Integer, ItemStack> slotFilterItems = new HashMap<>();
   private final Map<Integer, Pair<ResourceLocation, ResourceLocation>> emptySlotIcons = new HashMap<>();

   protected SettingsContainerMenu(MenuType<?> menuType, int windowId, Player player, S storageWrapper) {
      super(menuType, windowId);
      this.player = player;
      this.storageWrapper = storageWrapper;
      this.addStorageInventorySlots();
      this.addSettingsContainers();
      this.templatePersistanceContainer = new TemplatePersistanceContainer(this, player.level().registryAccess());
   }

   public int getNumberOfStorageInventorySlots() {
      return this.storageWrapper.getInventoryHandler().getSlots();
   }

   public S getStorageWrapper() {
      return this.storageWrapper;
   }

   private void addSettingsContainers() {
      SettingsHandler settingsHandler = this.storageWrapper.getSettingsHandler();
      settingsHandler.getSettingsCategories().forEach((name, category) -> this.settingsContainers.put(name, instantiateContainer(this, name, category)));
   }

   private void addStorageInventorySlots() {
      InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();

      for (int slotIndex = 0; slotIndex < inventoryHandler.getSlots(); slotIndex++) {
         int finalSlotIndex = slotIndex;
         this.storageInventorySlots.add(this.addSlot(new SettingsContainerMenu.ViewOnlyStorageInventorySlot(inventoryHandler, finalSlotIndex)));
      }
   }

   public int getColumnsTaken() {
      return this.storageWrapper.getColumnsTaken();
   }

   protected Slot addSlot(Slot slot) {
      slot.index = this.ghostSlots.size();
      this.ghostSlots.add(slot);
      this.lastGhostSlots.add(ItemStack.EMPTY);
      this.remoteGhostSlots.add(ItemStack.EMPTY);
      return slot;
   }

   public void broadcastChanges() {
      for (int slot = 0; slot < this.ghostSlots.size(); slot++) {
         ItemStack itemstack = this.ghostSlots.get(slot).getItem();
         Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::copy);
         this.triggerSlotListeners(slot, itemstack, supplier);
         this.synchronizeSlotToRemote(slot, itemstack, supplier);
      }

      if (this.inventorySlotStackChanged) {
         this.inventorySlotStackChanged = false;
         this.sendAdditionalSlotInfo();
      }
   }

   public void broadcastFullState() {
      for (int slotIndex = 0; slotIndex < this.ghostSlots.size(); slotIndex++) {
         ItemStack itemstack = this.ghostSlots.get(slotIndex).getItem();
         this.triggerSlotListeners(slotIndex, itemstack, itemstack::copy);
      }

      this.sendAllDataToRemote();
   }

   private void triggerSlotListeners(int slotIndex, ItemStack slotStack, Supplier<ItemStack> slotStackCopy) {
      ItemStack itemstack = (ItemStack)this.lastGhostSlots.get(slotIndex);
      if (!ItemStack.matches(itemstack, slotStack)) {
         ItemStack itemstack1 = slotStackCopy.get();
         this.lastGhostSlots.set(slotIndex, itemstack1);

         for (ContainerListener containerlistener : this.containerListeners) {
            containerlistener.slotChanged(this, slotIndex, itemstack1);
         }
      }
   }

   private void synchronizeSlotToRemote(int slotIndex, ItemStack slotStack, Supplier<ItemStack> slotStackCopy) {
      if (!this.suppressRemoteUpdates) {
         ItemStack remoteStack = (ItemStack)this.remoteGhostSlots.get(slotIndex);
         if (!ItemStack.matches(remoteStack, slotStack)) {
            ItemStack stackCopy = slotStackCopy.get();
            this.remoteGhostSlots.set(slotIndex, stackCopy);
            if (remoteStack.isEmpty() || slotStack.isEmpty()) {
               this.inventorySlotStackChanged = true;
            }

            if (this.synchronizer != null) {
               this.synchronizer.sendSlotChange(this, slotIndex, stackCopy);
            }
         }
      }
   }

   public int getNumberOfSlots() {
      return this.storageWrapper.getInventoryHandler().getSlots();
   }

   public void sendAllDataToRemote() {
      for (int slotIndex = 0; slotIndex < this.ghostSlots.size(); slotIndex++) {
         this.remoteGhostSlots.set(slotIndex, this.ghostSlots.get(slotIndex).getItem().copy());
      }

      if (this.synchronizer != null) {
         this.synchronizer.sendInitialData(this, this.remoteGhostSlots, this.remoteCarried, new int[0]);
      }

      if (this.player instanceof ServerPlayer serverPlayer) {
         SettingsTemplateStorage settingsTemplateStorage = SettingsTemplateStorage.get();
         PacketDistributor.sendToPlayer(
            serverPlayer,
            new SyncTemplateSettingsPayload(
               settingsTemplateStorage.getPlayerTemplates(serverPlayer), settingsTemplateStorage.getPlayerNamedTemplates(serverPlayer)
            ),
            new CustomPacketPayload[0]
         );
      }

      this.sendEmptySlotIcons();
      this.sendAdditionalSlotInfo();
   }

   public abstract void detectSettingsChangeAndReload();

   public void setSynchronizer(ContainerSynchronizer synchronizer) {
      if (this.player instanceof ServerPlayer serverPlayer && this.storageWrapper.getInventoryHandler().getStackSizeMultiplier() > 1.0) {
         super.setSynchronizer(new HighStackCountSynchronizer(serverPlayer));
      } else {
         super.setSynchronizer(synchronizer);
      }
   }

   public Slot getSlot(int slotId) {
      return this.ghostSlots.get(slotId);
   }

   public void onMemorizedStackAdded(int slotNumber) {
      this.storageWrapper.getInventoryHandler().onSlotFilterChanged(slotNumber);
      this.sendAdditionalSlotInfo();
   }

   public void onMemorizedStackRemoved(int slotNumber) {
      if (this.getSlot(slotNumber).getItem().isEmpty()) {
         this.storageWrapper.getInventoryHandler().onSlotFilterChanged(slotNumber);
         this.storageWrapper.getSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class).itemChanged(slotNumber);
         this.sendAdditionalSlotInfo();
      }
   }

   public void onMemorizedItemsChanged() {
      this.storageWrapper.getSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class).itemsChanged();
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public List<Slot> getStorageInventorySlots() {
      return this.storageInventorySlots;
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("categoryName")) {
         String categoryName = data.getString("categoryName");
         if (this.settingsContainers.containsKey(categoryName)) {
            this.settingsContainers.get(categoryName).handlePacket(data);
         }
      } else if (data.contains("templatePersistance", 10)) {
         this.templatePersistanceContainer.handlePacket(data.getCompound("templatePersistance"));
      }
   }

   public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public void forEachSettingsContainer(BiConsumer<String, ? super SettingsContainerBase<?>> consumer) {
      this.settingsContainers.forEach(consumer);
   }

   public Player getPlayer() {
      return this.player;
   }

   public BlockPos getBlockPosition() {
      return BlockPos.ZERO;
   }

   public <T extends ISettingsCategory<?>> Optional<T> getSelectedTemplatesCategory(Class<T> categoryClass) {
      return this.templatePersistanceContainer.getSelectedTemplate().map(selectedTemplate -> selectedTemplate.getTypeCategory(categoryClass));
   }

   public TemplatePersistanceContainer getTemplatePersistanceContainer() {
      return this.templatePersistanceContainer;
   }

   public void refreshTemplateSlots() {
      this.templatePersistanceContainer.refreshTemplateSlots();
   }

   public boolean supportsItemDisplaySideSelection() {
      return false;
   }

   public int getNumberOfRows() {
      return this.storageWrapper.getNumberOfSlotRows();
   }

   protected static <C extends ISettingsCategory<?>, T extends SettingsContainerBase<C>> void addFactory(
      String categoryName, SettingsContainerMenu.ISettingsContainerFactory<C, T> factory
   ) {
      SETTINGS_CONTAINER_FACTORIES.put(categoryName, factory);
   }

   private static <C extends ISettingsCategory<?>> SettingsContainerBase<C> instantiateContainer(
      SettingsContainerMenu<?> settingsContainer, String name, C category
   ) {
      return getSettingsContainerFactory(name).create(settingsContainer, name, category);
   }

   private static <C extends ISettingsCategory<?>, T extends SettingsContainerBase<C>> SettingsContainerMenu.ISettingsContainerFactory<C, T> getSettingsContainerFactory(
      String name
   ) {
      return (SettingsContainerMenu.ISettingsContainerFactory<C, T>)SETTINGS_CONTAINER_FACTORIES.get(name);
   }

   public void sendDataToServer(Supplier<CompoundTag> supplyData) {
      if (!this.isServer()) {
         CompoundTag data = supplyData.get();
         PacketDistributor.sendToServer(new SyncContainerClientDataPayload(data), new CustomPacketPayload[0]);
      }
   }

   protected boolean isServer() {
      return !this.player.level().isClientSide;
   }

   public void sendAdditionalSlotInfo() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         HashSet var6 = new HashSet();
         InventoryHandler inventoryHandler = this.storageWrapper.getInventoryHandler();
         HashMap slotFilterItems = new HashMap();

         for (int slot = 0; slot < inventoryHandler.getSlots(); slot++) {
            if (!inventoryHandler.isSlotAccessible(slot)) {
               var6.add(slot);
            }

            if (inventoryHandler.getFilterItem(slot) != Items.AIR) {
               slotFilterItems.put(slot, inventoryHandler.getFilterItem(slot).builtInRegistryHolder());
            }
         }

         PacketDistributor.sendToPlayer(serverPlayer, new SyncAdditionalSlotInfoPayload(var6, Map.of(), Set.of(), slotFilterItems), new CustomPacketPayload[0]);
      }
   }

   @Override
   public void updateAdditionalSlotInfo(
      Set<Integer> inaccessibleSlots, Map<Integer, Integer> slotLimitOverrides, Set<Integer> infiniteSlots, Map<Integer, Holder<Item>> slotFilterItems
   ) {
      this.inaccessibleSlots.clear();
      this.inaccessibleSlots.addAll(inaccessibleSlots);
      this.slotFilterItems.clear();
      slotFilterItems.forEach((slot, item) -> this.slotFilterItems.put(slot, new ItemStack(item)));
   }

   @Override
   public void updateEmptySlotIcons(Map<ResourceLocation, Set<Integer>> emptySlotIcons) {
      this.emptySlotIcons.clear();
      emptySlotIcons.forEach((textureName, slots) -> slots.forEach(slot -> this.emptySlotIcons.put(slot, new Pair(InventoryMenu.BLOCK_ATLAS, textureName))));
   }

   public void setRemoteSlotNoCopy(int slot, ItemStack stack) {
      ItemStack previous = this.getSlot(slot).getItem();
      super.setRemoteSlotNoCopy(slot, stack);
      if (previous.isEmpty() || stack.isEmpty()) {
         this.inventorySlotStackChanged = true;
      }
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

   public ItemStack getSlotFilterItem(int slot) {
      return this.slotFilterItems.getOrDefault(slot, ItemStack.EMPTY);
   }

   static {
      addFactory("global", MainSettingsContainer::new);
      addFactory("no_sort", NoSortSettingsContainer::new);
      addFactory("memory", MemorySettingsContainer::new);
      addFactory("item_display", ItemDisplaySettingsContainer::new);
   }

   public interface ISettingsContainerFactory<C extends ISettingsCategory<?>, T extends SettingsContainerBase<C>> {
      T create(SettingsContainerMenu<?> var1, String var2, C var3);
   }

   private class ViewOnlyStorageInventorySlot extends SlotItemHandler {
      public ViewOnlyStorageInventorySlot(IItemHandler inventoryHandler, int slotIndex) {
         super(inventoryHandler, slotIndex, 0, 0);
      }

      public boolean mayPickup(Player playerIn) {
         return false;
      }

      @Nullable
      public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
         return SettingsContainerMenu.this.inaccessibleSlots.contains(this.getSlotIndex())
            ? StorageContainerMenuBase.INACCESSIBLE_SLOT_BACKGROUND
            : SettingsContainerMenu.this.emptySlotIcons.getOrDefault(this.getSlotIndex(), null);
      }
   }
}

package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.renderdata.TankPosition;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class UpgradeHandler extends ItemStackHandler {
   public static final String UPGRADE_INVENTORY_TAG = "upgradeInventory";
   private final IStorageWrapper storageWrapper;
   private final Runnable contentsSaveHandler;
   private final Runnable onInvalidateUpgradeCaches;
   private final CompoundTag contentsNbt;
   @Nullable
   private Runnable refreshCallBack = null;
   private final Map<Integer, IUpgradeWrapper> slotWrappers = new LinkedHashMap<>();
   private final Map<UpgradeType<? extends IUpgradeWrapper>, List<? extends IUpgradeWrapper>> typeWrappers = new HashMap<>();
   private boolean justSavingNbtChange = false;
   private boolean wrappersInitialized = false;
   private boolean typeWrappersInitialized = false;
   @Nullable
   private IUpgradeWrapperAccessor wrapperAccessor = null;
   private boolean persistent = true;
   private final Map<Class<? extends IUpgradeWrapper>, Consumer<? extends IUpgradeWrapper>> upgradeDefaultsHandlers = new HashMap<>();

   public UpgradeHandler(
      int numberOfUpgradeSlots, IStorageWrapper storageWrapper, CompoundTag contentsNbt, Runnable contentsSaveHandler, Runnable onInvalidateUpgradeCaches
   ) {
      super(numberOfUpgradeSlots);
      this.contentsNbt = contentsNbt;
      this.storageWrapper = storageWrapper;
      this.contentsSaveHandler = contentsSaveHandler;
      this.onInvalidateUpgradeCaches = onInvalidateUpgradeCaches;
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> this.deserializeNBT(registryAccess, contentsNbt.getCompound("upgradeInventory")));
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && storageWrapper.getRenderInfo().getUpgradeItems().size() != this.getSlots()) {
         this.setRenderUpgradeItems();
      }
   }

   public <T extends IUpgradeWrapper> void registerUpgradeDefaultsHandler(Class<T> upgradeClass, Consumer<T> defaultsHandler) {
      this.upgradeDefaultsHandlers.put(upgradeClass, defaultsHandler);
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return stack.isEmpty() || stack.getItem() instanceof IUpgradeItem;
   }

   protected void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      if (this.persistent) {
         this.saveInventory();
         this.contentsSaveHandler.run();
      }

      if (!this.justSavingNbtChange) {
         this.refreshUpgradeWrappers();
         this.setRenderUpgradeItems();
      }
   }

   public void setRenderUpgradeItems() {
      List<ItemStack> upgradeItems = new ArrayList<>();
      InventoryHelper.iterate(this, (upgradeSlot, upgrade) -> upgradeItems.add(upgrade.copyWithCount(1)));
      this.storageWrapper.getRenderInfo().setUpgradeItems(upgradeItems);
   }

   public void setSize(int size) {
      super.setSize(this.stacks.size());
   }

   public void saveInventory() {
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> this.contentsNbt.put("upgradeInventory", this.serializeNBT(registryAccess)));
   }

   public void setPersistent(boolean persistent) {
      this.persistent = persistent;
   }

   public void setRefreshCallBack(Runnable refreshCallBack) {
      this.refreshCallBack = refreshCallBack;
   }

   public void removeRefreshCallback() {
      this.refreshCallBack = null;
   }

   private void initializeWrappers() {
      if (!this.wrappersInitialized) {
         this.wrappersInitialized = true;
         this.slotWrappers.clear();
         this.typeWrappers.clear();
         if (this.wrapperAccessor != null) {
            this.wrapperAccessor.clearCache();
         }

         InventoryHelper.iterate(this, (slot, upgrade) -> {
            if (!upgrade.isEmpty() && upgrade.getItem() instanceof IUpgradeItem) {
               UpgradeType<?> type = ((IUpgradeItem)upgrade.getItem()).getType();
               IUpgradeWrapper wrapper = type.create(this.storageWrapper, upgrade, upgradeStack -> {
                  this.justSavingNbtChange = true;
                  this.setStackInSlot(slot, upgradeStack);
                  this.justSavingNbtChange = false;
               });
               this.setUpgradeDefaults(wrapper);
               this.slotWrappers.put(slot, wrapper);
            }
         });
         this.initRenderInfoCallbacks(false);
      }
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      ItemStack result = super.insertItem(slot, stack, simulate);
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && result.isEmpty() && !stack.isEmpty()) {
         this.onUpgradeAdded(slot);
      }

      return result;
   }

   private void onUpgradeAdded(int slot) {
      Map<Integer, IUpgradeWrapper> wrappers = this.getSlotWrappers();
      if (wrappers.containsKey(slot)) {
         wrappers.get(slot).onAdded();
      }
   }

   private void setUpgradeDefaults(IUpgradeWrapper wrapper) {
      this.getUpgradeDefaultsHandler(wrapper).accept(wrapper);
   }

   private <T extends IUpgradeWrapper> Consumer<T> getUpgradeDefaultsHandler(T wrapper) {
      return (Consumer<T>)this.upgradeDefaultsHandlers.getOrDefault(wrapper.getClass(), w -> {});
   }

   public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
      ItemStack originalStack = this.getStackInSlot(slot);
      Map<Integer, IUpgradeWrapper> wrappers = this.getSlotWrappers();
      boolean itemsDiffer = !ItemStack.isSameItemSameComponents(originalStack, stack);
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && itemsDiffer && wrappers.containsKey(slot)) {
         wrappers.get(slot).onBeforeRemoved();
      }

      super.setStackInSlot(slot, stack);
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && itemsDiffer) {
         this.onUpgradeAdded(slot);
      }
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (!simulate && Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         ItemStack slotStack = this.getStackInSlot(slot);
         if (this.persistent && !slotStack.isEmpty() && amount == 1) {
            Map<Integer, IUpgradeWrapper> wrappers = this.getSlotWrappers();
            if (wrappers.containsKey(slot)) {
               wrappers.get(slot).onBeforeRemoved();
            }
         }
      }

      return super.extractItem(slot, amount, simulate);
   }

   private void initializeTypeWrappers() {
      if (!this.typeWrappersInitialized) {
         this.initializeWrappers();
         this.typeWrappersInitialized = true;
         this.typeWrappers.clear();
         this.slotWrappers.values().forEach(wrapper -> {
            if (wrapper.getUpgradeStack().getItem() instanceof IUpgradeItem<?> upgradeItem) {
               UpgradeType<?> type = upgradeItem.getType();
               if (wrapper.isEnabled() && (!(wrapper instanceof ITickableUpgrade) || this.storageWrapper.isUpgradeRunnable(wrapper.getUpgradeStack()))) {
                  this.addTypeWrapper(type, wrapper);
               }
            }
         });
         this.initRenderInfoCallbacks(false);
      }
   }

   private <T extends IUpgradeWrapper> void addTypeWrapper(UpgradeType<?> type, T wrapper) {
      this.typeWrappers.computeIfAbsent((UpgradeType<? extends IUpgradeWrapper>)type, t -> new ArrayList<>()).add(wrapper);
   }

   public <T extends IUpgradeWrapper> List<T> getTypeWrappers(UpgradeType<T> type) {
      this.initializeTypeWrappers();
      return (List<T>)this.typeWrappers.getOrDefault(type, Collections.emptyList());
   }

   public <T extends IUpgradeWrapper> boolean hasUpgrade(UpgradeType<T> type) {
      return !this.getTypeWrappers(type).isEmpty();
   }

   public <T> List<T> getWrappersThatImplement(Class<T> upgradeClass) {
      this.initializeWrappers();
      return this.getWrapperAccessor().getWrappersThatImplement(upgradeClass);
   }

   private IUpgradeWrapperAccessor getWrapperAccessor() {
      if (this.wrapperAccessor == null) {
         IUpgradeWrapperAccessor accessor = new UpgradeHandler.Accessor(this);

         for (IUpgradeAccessModifier upgrade : this.getListOfWrappersThatImplement(IUpgradeAccessModifier.class)) {
            accessor = upgrade.wrapAccessor(accessor);
         }

         this.wrapperAccessor = accessor;
      }

      return this.wrapperAccessor;
   }

   public <T> List<T> getWrappersThatImplementFromMainStorage(Class<T> upgradeClass) {
      this.initializeWrappers();
      return this.getWrapperAccessor().getWrappersThatImplementFromMainStorage(upgradeClass);
   }

   public <T> List<T> getListOfWrappersThatImplement(Class<T> uc) {
      List<T> ret = new ArrayList<>();

      for (IUpgradeWrapper wrapper : this.slotWrappers.values()) {
         if (wrapper.isEnabled() && uc.isInstance(wrapper)) {
            ret.add((T)wrapper);
         }
      }

      return ret;
   }

   public Map<Integer, IUpgradeWrapper> getSlotWrappers() {
      this.initializeWrappers();
      return this.slotWrappers;
   }

   public void copyTo(UpgradeHandler otherHandler) {
      InventoryHelper.copyTo(this, otherHandler);
   }

   public void refreshWrappersThatImplementAndTypeWrappers() {
      this.typeWrappersInitialized = false;
      if (this.wrapperAccessor != null) {
         this.wrapperAccessor.clearCache();
      }

      if (this.refreshCallBack != null) {
         this.refreshCallBack.run();
      }
   }

   public void refreshUpgradeWrappers() {
      if (this.wrappersInitialized || this.typeWrappersInitialized) {
         this.wrappersInitialized = false;
         this.typeWrappersInitialized = false;
         if (this.wrapperAccessor != null) {
            this.wrapperAccessor.onBeforeDeconstruct();
            this.wrapperAccessor = null;
         }

         if (this.refreshCallBack != null) {
            this.refreshCallBack.run();
         }

         this.onInvalidateUpgradeCaches.run();
         this.initRenderInfoCallbacks(true);
      }
   }

   private void initRenderInfoCallbacks(boolean forceUpdateRenderInfo) {
      RenderInfo renderInfo = this.storageWrapper.getRenderInfo();
      if (forceUpdateRenderInfo) {
         renderInfo.resetUpgradeInfo(false);
      }

      this.initTankRenderInfoCallbacks(forceUpdateRenderInfo, renderInfo);
      this.initBatteryRenderInfoCallbacks(forceUpdateRenderInfo, renderInfo);
   }

   private void initBatteryRenderInfoCallbacks(boolean forceUpdateRenderInfo, RenderInfo renderInfo) {
      this.getSlotWrappers().forEach((slot, wrapper) -> {
         if (wrapper instanceof IRenderedBatteryUpgrade batteryWrapper) {
            batteryWrapper.setBatteryRenderInfoUpdateCallback(renderInfo::setBatteryRenderInfo);
            if (forceUpdateRenderInfo) {
               batteryWrapper.forceUpdateBatteryRenderInfo();
            }
         }
      });
   }

   private void initTankRenderInfoCallbacks(boolean forceUpdateRenderInfo, RenderInfo renderInfo) {
      AtomicBoolean singleTankRight = new AtomicBoolean(false);
      List<IRenderedTankUpgrade> tankRenderWrappers = new ArrayList<>();
      int minRightSlot = this.getSlots() / 2;
      this.getSlotWrappers().forEach((slot, wrapper) -> {
         if (wrapper instanceof IRenderedTankUpgrade tankUpgrade) {
            tankRenderWrappers.add(tankUpgrade);
            if (slot >= minRightSlot) {
               singleTankRight.set(true);
            }
         }
      });
      TankPosition currentTankPos = tankRenderWrappers.size() == 1 && singleTankRight.get() ? TankPosition.RIGHT : TankPosition.LEFT;

      for (IRenderedTankUpgrade tankRenderWrapper : tankRenderWrappers) {
         TankPosition finalCurrentTankPos = currentTankPos;
         tankRenderWrapper.setTankRenderInfoUpdateCallback(tankRenderInfo -> renderInfo.setTankRenderInfo(finalCurrentTankPos, tankRenderInfo));
         if (forceUpdateRenderInfo) {
            tankRenderWrapper.forceUpdateTankRenderInfo();
         }

         currentTankPos = TankPosition.RIGHT;
      }
   }

   public void increaseSize(int diff) {
      NonNullList<ItemStack> previousStacks = this.stacks;
      this.stacks = NonNullList.withSize(previousStacks.size() + diff, ItemStack.EMPTY);

      for (int slot = 0; slot < previousStacks.size(); slot++) {
         this.stacks.set(slot, (ItemStack)previousStacks.get(slot));
      }

      this.saveInventory();
      this.setRenderUpgradeItems();
   }

   public int getSlotLimit(int slot) {
      return 1;
   }

   private static class Accessor implements IUpgradeWrapperAccessor {
      private final Map<Class<?>, List<?>> interfaceWrappers = new HashMap<>();
      private final UpgradeHandler upgradeHandler;

      public Accessor(UpgradeHandler upgradeHandler) {
         this.upgradeHandler = upgradeHandler;
      }

      @Override
      public <T> List<T> getWrappersThatImplement(Class<T> upgradeClass) {
         return (List<T>)this.interfaceWrappers.computeIfAbsent(upgradeClass, this.upgradeHandler::getListOfWrappersThatImplement);
      }

      @Override
      public <T> List<T> getWrappersThatImplementFromMainStorage(Class<T> upgradeClass) {
         return (List<T>)this.interfaceWrappers.computeIfAbsent(upgradeClass, this.upgradeHandler::getListOfWrappersThatImplement);
      }

      @Override
      public void clearCache() {
         this.interfaceWrappers.clear();
      }
   }
}

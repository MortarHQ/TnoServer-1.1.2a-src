package net.p3pp3rf1y.sophisticatedcore.api;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.util.ITintable;

public interface IStorageWrapper extends ITintable {
   String SETTINGS_TAG = "settings";

   void setContentsChangeHandler(Runnable var1);

   default void setInventorySlotChangeHandler(Runnable slotChangeHandler) {
   }

   ITrackedContentsItemHandler getInventoryForUpgradeProcessing();

   InventoryHandler getInventoryHandler();

   ITrackedContentsItemHandler getInventoryForInputOutput();

   default void setUpgradeCachesInvalidatedHandler(Runnable handler) {
   }

   SettingsHandler getSettingsHandler();

   UpgradeHandler getUpgradeHandler();

   Optional<UUID> getContentsUuid();

   Optional<Integer> getOpenTabId();

   void setOpenTabId(int var1);

   void removeOpenTabId();

   void setSortBy(SortBy var1);

   SortBy getSortBy();

   void sort();

   void onContentsNbtUpdated();

   void refreshInventoryForUpgradeProcessing();

   void refreshInventoryForInputOutput();

   void setPersistent(boolean var1);

   void fillWithLoot(Player var1);

   RenderInfo getRenderInfo();

   void setColumnsTaken(int var1, boolean var2);

   int getColumnsTaken();

   default int getNumberOfSlotRows() {
      return 0;
   }

   default Optional<IStorageFluidHandler> getFluidHandler() {
      return Optional.empty();
   }

   default Optional<IEnergyStorage> getEnergyStorage() {
      return Optional.empty();
   }

   default ItemStack getWrappedStorageStack() {
      return ItemStack.EMPTY;
   }

   default int getBaseStackSizeMultiplier() {
      return 1;
   }

   default void onInit() {
      this.getInventoryHandler().onInit();
   }

   String getStorageType();

   Component getDisplayName();

   default boolean isUpgradeRunnable(ItemStack upgrade) {
      return true;
   }

   default void registerOnInventoryInputOutputHandlerRefreshListener(Runnable onInventoryForInputOutputHandlerRefresh) {
   }
}

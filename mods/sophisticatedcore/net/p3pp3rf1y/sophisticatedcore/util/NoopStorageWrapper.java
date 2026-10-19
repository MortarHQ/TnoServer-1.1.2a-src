package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.main.MainSettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeConfig;

public class NoopStorageWrapper implements IStorageWrapper {
   public static final NoopStorageWrapper INSTANCE = new NoopStorageWrapper();
   @Nullable
   private UpgradeHandler upgradeHandler;
   @Nullable
   private InventoryHandler inventoryHandler;
   @Nullable
   private RenderInfo renderInfo;
   @Nullable
   private SettingsHandler settingsHandler;

   protected NoopStorageWrapper() {
   }

   @Override
   public void setContentsChangeHandler(Runnable contentsChangeHandler) {
   }

   @Override
   public ITrackedContentsItemHandler getInventoryForUpgradeProcessing() {
      return this.getInventoryHandler();
   }

   @Override
   public InventoryHandler getInventoryHandler() {
      if (this.inventoryHandler == null) {
         this.inventoryHandler = new InventoryHandler(0, this, new CompoundTag(), () -> {}, 64, new StackUpgradeConfig(new Builder())) {
            @Override
            protected boolean isAllowed(ItemStack stack) {
               return true;
            }
         };
      }

      return this.inventoryHandler;
   }

   @Override
   public ITrackedContentsItemHandler getInventoryForInputOutput() {
      return this.getInventoryHandler();
   }

   @Override
   public SettingsHandler getSettingsHandler() {
      if (this.settingsHandler == null) {
         this.settingsHandler = new SettingsHandler(new CompoundTag(), () -> {}, this::getInventoryHandler, this::getRenderInfo) {
            @Override
            protected CompoundTag getSettingsNbtFromContentsNbt(CompoundTag contentsNbt) {
               return contentsNbt;
            }

            @Override
            protected void addItemDisplayCategory(
               Supplier<InventoryHandler> inventoryHandlerSupplier, Supplier<RenderInfo> renderInfoSupplier, CompoundTag settingsNbt
            ) {
            }

            @Override
            public String getGlobalSettingsCategoryName() {
               return "";
            }

            @Override
            public ISettingsCategory<?> instantiateGlobalSettingsCategory(CompoundTag categoryNbt, Consumer<CompoundTag> saveNbt) {
               return new MainSettingsCategory(categoryNbt, saveNbt, "");
            }

            @Override
            protected void saveCategoryNbt(CompoundTag settingsNbt, String categoryName, CompoundTag tag) {
            }
         };
      }

      return this.settingsHandler;
   }

   @Override
   public UpgradeHandler getUpgradeHandler() {
      if (this.upgradeHandler == null) {
         this.upgradeHandler = new UpgradeHandler(0, this, new CompoundTag(), () -> {}, () -> {});
      }

      return this.upgradeHandler;
   }

   @Override
   public Optional<UUID> getContentsUuid() {
      return Optional.empty();
   }

   @Override
   public int getMainColor() {
      return -1;
   }

   @Override
   public int getAccentColor() {
      return -1;
   }

   @Override
   public Optional<Integer> getOpenTabId() {
      return Optional.empty();
   }

   @Override
   public void setOpenTabId(int openTabId) {
   }

   @Override
   public void removeOpenTabId() {
   }

   @Override
   public void setColors(int mainColor, int accentColor) {
   }

   @Override
   public void setSortBy(SortBy sortBy) {
   }

   @Override
   public SortBy getSortBy() {
      return SortBy.NAME;
   }

   @Override
   public void sort() {
   }

   @Override
   public void onContentsNbtUpdated() {
   }

   @Override
   public void refreshInventoryForUpgradeProcessing() {
   }

   @Override
   public void refreshInventoryForInputOutput() {
   }

   @Override
   public void setPersistent(boolean persistent) {
   }

   @Override
   public void fillWithLoot(Player playerEntity) {
   }

   @Override
   public RenderInfo getRenderInfo() {
      if (this.renderInfo == null) {
         this.renderInfo = new RenderInfo(() -> () -> {}) {
            @Override
            protected void serializeRenderInfo(CompoundTag renderInfo) {
            }

            @Override
            protected Optional<CompoundTag> getRenderInfoTag() {
               return Optional.empty();
            }
         };
      }

      return this.renderInfo;
   }

   @Override
   public void setColumnsTaken(int columnsTaken, boolean hasChanged) {
   }

   @Override
   public int getColumnsTaken() {
      return 0;
   }

   @Override
   public String getStorageType() {
      return "";
   }

   @Override
   public Component getDisplayName() {
      return Component.empty();
   }
}

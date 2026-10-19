package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import java.util.Optional;
import java.util.UUID;
import java.util.function.IntConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.NoopStorageWrapper;

public interface IBackpackWrapper extends IStorageWrapper {
   IBackpackWrapper setBackpackStack(ItemStack var1);

   BackpackSettingsHandler getSettingsHandler();

   ItemStack getBackpack();

   ItemStack cloneBackpack();

   void copyDataTo(IStorageWrapper var1);

   void setSlotNumbers(int var1, int var2);

   void setLoot(ResourceLocation var1, float var2);

   void setTemplate(ResourceLocation var1);

   void fillFromTemplate();

   default void fillWithLootAndExtraItems(Level level, BlockPos pos) {
   }

   void setContentsUuid(UUID var1);

   default void removeContentsUuid() {
   }

   default void removeContentsUUIDTag() {
   }

   default void registerOnSlotsChangeListener(IntConsumer onSlotsChange) {
   }

   default void unregisterOnSlotsChangeListener() {
   }

   default void registerOnInventoryHandlerRefreshListener(Runnable onInventoryHandlerRefresh) {
   }

   default void unregisterOnInventoryHandlerRefreshListener() {
   }

   default Optional<IFluidHandlerItem> getItemFluidHandler() {
      return Optional.empty();
   }

   class Noop extends NoopStorageWrapper implements IBackpackWrapper {
      public static final IBackpackWrapper.Noop INSTANCE = new IBackpackWrapper.Noop();
      private final ItemStack backpack = new ItemStack((ItemLike)ModItems.BACKPACK.get());
      private final BackpackSettingsHandler settingsHandler = new BackpackSettingsHandler(this, new CompoundTag(), () -> {});

      @Override
      public IBackpackWrapper setBackpackStack(ItemStack backpackStack) {
         return this;
      }

      @Override
      public BackpackSettingsHandler getSettingsHandler() {
         return this.settingsHandler;
      }

      @Override
      public ItemStack getBackpack() {
         return this.backpack;
      }

      @Override
      public ItemStack cloneBackpack() {
         return this.backpack;
      }

      @Override
      public void copyDataTo(IStorageWrapper otherStorageWrapper) {
      }

      @Override
      public void setSlotNumbers(int numberOfInventorySlots, int numberOfUpgradeSlots) {
      }

      @Override
      public void setLoot(ResourceLocation lootTableName, float lootPercentage) {
      }

      @Override
      public void setTemplate(ResourceLocation templateName) {
      }

      @Override
      public void fillFromTemplate() {
      }

      @Override
      public void setContentsUuid(UUID storageUuid) {
      }
   }
}

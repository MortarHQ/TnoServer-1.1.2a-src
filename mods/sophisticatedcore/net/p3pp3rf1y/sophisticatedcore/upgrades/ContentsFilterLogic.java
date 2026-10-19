package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;

public class ContentsFilterLogic extends FilterLogic {
   private final Supplier<InventoryHandler> getInventoryHandler;
   private final MemorySettingsCategory memorySettings;

   public ContentsFilterLogic(
      ItemStack upgrade,
      Consumer<ItemStack> saveHandler,
      int filterSlotCount,
      Supplier<InventoryHandler> getInventoryHandler,
      MemorySettingsCategory memorySettings,
      DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> filterAttributesComponent
   ) {
      super(upgrade, saveHandler, filterSlotCount, filterAttributesComponent);
      this.getInventoryHandler = getInventoryHandler;
      this.memorySettings = memorySettings;
   }

   public ContentsFilterType getFilterType() {
      if (this.shouldFilterByStorage()) {
         return ContentsFilterType.STORAGE;
      } else {
         return this.isAllowList() ? ContentsFilterType.ALLOW : ContentsFilterType.BLOCK;
      }
   }

   public void setDepositFilterType(ContentsFilterType contentsFilterType) {
      switch (contentsFilterType) {
         case ALLOW:
            this.setFilterByStorage(false);
            this.setAllowList(true);
            break;
         case BLOCK:
            this.setFilterByStorage(false);
            this.setAllowList(false);
            break;
         case STORAGE:
            this.setFilterByStorage(true);
            this.save();
      }
   }

   @Override
   public boolean matchesFilter(ItemStack stack) {
      if (!this.shouldFilterByStorage()) {
         return super.matchesFilter(stack);
      }

      for (ItemStackKey filterStack : this.getInventoryHandler.get().getSlotTracker().getFullStacks()) {
         if (this.stackMatchesFilter(stack, filterStack.getStack())) {
            return true;
         }
      }

      for (ItemStackKey filterStack : this.getInventoryHandler.get().getSlotTracker().getPartialStacks()) {
         if (this.stackMatchesFilter(stack, filterStack.getStack())) {
            return true;
         }
      }

      return this.memorySettings.matchesFilter(stack);
   }

   private void setFilterByStorage(boolean filterByStorage) {
      this.setAttributes(attributes -> attributes.setFilterByStorage(filterByStorage));
      this.save();
   }

   protected boolean shouldFilterByStorage() {
      return this.getAttributes().filterByStorage();
   }
}

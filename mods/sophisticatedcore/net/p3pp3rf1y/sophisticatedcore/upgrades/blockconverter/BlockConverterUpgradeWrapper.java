package net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter;

import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.SimpleItemContent;

public abstract class BlockConverterUpgradeWrapper<U extends BlockConverterUpgradeItem<U, W>, W extends BlockConverterUpgradeWrapper<U, W>>
   extends UpgradeWrapperBase<W, U> {
   private final IItemHandlerModifiable inputInventory;

   public BlockConverterUpgradeWrapper(IStorageWrapper storageWrapper, final ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.inputInventory = new ItemStackHandler(1) {
         protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            if (slot == 0) {
               upgrade.set(ModCoreDataComponents.INPUT_ITEM, SimpleItemContent.copyOf(this.getStackInSlot(0)));
            }

            BlockConverterUpgradeWrapper.this.save();
         }
      };
      this.inputInventory.setStackInSlot(0, ((SimpleItemContent)upgrade.getOrDefault(ModCoreDataComponents.INPUT_ITEM, SimpleItemContent.EMPTY)).copy());
   }

   public IItemHandlerModifiable getInputInventory() {
      return this.inputInventory;
   }

   public void setRecipeId(@Nullable ResourceLocation recipeId) {
      if (recipeId == null) {
         this.upgrade.remove(ModCoreDataComponents.RECIPE_ID);
      } else {
         this.upgrade.set(ModCoreDataComponents.RECIPE_ID, recipeId);
         this.save();
      }
   }

   public Optional<ResourceLocation> getRecipeId() {
      return Optional.ofNullable((ResourceLocation)this.upgrade.get(ModCoreDataComponents.RECIPE_ID));
   }

   @Override
   public boolean canBeDisabled() {
      return false;
   }

   public boolean shouldShiftClickIntoStorage() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, true);
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      this.upgrade.set(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, shiftClickIntoStorage);
      this.save();
   }
}

package net.p3pp3rf1y.sophisticatedcore.compat.chipped;

import earth.terrarium.chipped.common.recipes.ChippedRecipe;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.SimpleItemContent;

public class BlockTransformationUpgradeWrapper extends UpgradeWrapperBase<BlockTransformationUpgradeWrapper, BlockTransformationUpgradeItem> {
   private final IItemHandlerModifiable inputInventory;
   private final RecipeType<ChippedRecipe> recipeType;

   protected BlockTransformationUpgradeWrapper(IStorageWrapper storageWrapper, final ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.inputInventory = new ItemStackHandler(1) {
         protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            if (slot == 0) {
               upgrade.set(ModCoreDataComponents.INPUT_ITEM, SimpleItemContent.copyOf(this.getStackInSlot(0)));
            }

            BlockTransformationUpgradeWrapper.this.save();
         }
      };
      this.inputInventory.setStackInSlot(0, ((SimpleItemContent)upgrade.getOrDefault(ModCoreDataComponents.INPUT_ITEM, SimpleItemContent.EMPTY)).copy());
      this.recipeType = this.upgradeItem.getRecipeType();
   }

   public IItemHandlerModifiable getInputInventory() {
      return this.inputInventory;
   }

   public void setResult(ItemStack result) {
      if (result.isEmpty()) {
         this.upgrade.remove(ModCoreDataComponents.RESULT_ITEM);
      } else {
         this.upgrade.set(ModCoreDataComponents.RESULT_ITEM, SimpleItemContent.copyOf(result));
         this.save();
      }
   }

   public Optional<SimpleItemContent> getResult() {
      return Optional.ofNullable((SimpleItemContent)this.upgrade.get(ModCoreDataComponents.RESULT_ITEM));
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

   public RecipeType<ChippedRecipe> getRecipeType() {
      return this.recipeType;
   }
}

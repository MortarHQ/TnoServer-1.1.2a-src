package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;
import net.p3pp3rf1y.sophisticatedcore.util.XpHelper;

public class AutoCookingUpgradeWrapper<W extends AutoCookingUpgradeWrapper<W, U, R>, U extends UpgradeItemBase<W> & IAutoCookingUpgradeItem, R extends AbstractCookingRecipe>
   extends UpgradeWrapperBase<W, U>
   implements ITickableUpgrade,
   ICookingUpgrade<R> {
   private static final int NOTHING_TO_DO_COOLDOWN = 10;
   private static final int NO_INVENTORY_SPACE_COOLDOWN = 60;
   private final FilterLogic inputFilterLogic;
   private final FilterLogic fuelFilterLogic;
   private final CookingLogic<R> cookingLogic;
   private final Predicate<ItemStack> isValidInput;
   private final Predicate<ItemStack> isValidFuel;
   private final RecipeType<R> recipeType;
   private int outputCooldown = 0;
   private int fuelCooldown = 0;
   private int inputCooldown = 0;

   public AutoCookingUpgradeWrapper(
      IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler, RecipeType<R> recipeType, float burnTimeModifier
   ) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.recipeType = recipeType;
      AutoCookingUpgradeConfig autoCookingUpgradeConfig = this.upgradeItem.getAutoCookingUpgradeConfig();
      this.inputFilterLogic = new FilterLogic(
         upgrade,
         upgradeSaveHandler,
         (Integer)autoCookingUpgradeConfig.inputFilterSlots.get(),
         s -> RecipeHelper.getCookingRecipe(s, recipeType).isPresent(),
         ModCoreDataComponents.INPUT_FILTER_ATTRIBUTES
      );
      this.fuelFilterLogic = new FilterLogic(
         upgrade,
         upgradeSaveHandler,
         (Integer)autoCookingUpgradeConfig.fuelFilterSlots.get(),
         s -> s.getBurnTime(recipeType) > 0,
         ModCoreDataComponents.FUEL_FILTER_ATTRIBUTES
      );
      this.fuelFilterLogic.setAllowByDefault(true);
      this.fuelFilterLogic.setEmptyAllowListMatchesEverything();
      this.isValidInput = s -> RecipeHelper.getCookingRecipe(s, recipeType).isPresent() && this.inputFilterLogic.matchesFilter(s);
      this.isValidFuel = s -> s.getBurnTime(recipeType) > 0 && this.fuelFilterLogic.matchesFilter(s);
      this.cookingLogic = new CookingLogic<>(
         upgrade, upgradeSaveHandler, this.isValidFuel, this.isValidInput, autoCookingUpgradeConfig, recipeType, burnTimeModifier
      );
   }

   @Override
   public void setEnabled(boolean enabled) {
      if (!enabled) {
         this.pauseAndRemoveRenderInfo();
      }

      super.setEnabled(enabled);
   }

   private void pauseAndRemoveRenderInfo() {
      this.cookingLogic.pause();
      RenderInfo renderInfo = this.storageWrapper.getRenderInfo();
      renderInfo.removeUpgradeRenderData(CookingUpgradeRenderData.TYPE);
   }

   @Override
   public void onBeforeRemoved() {
      this.pauseAndRemoveRenderInfo();
   }

   private void tryPushingOutput() {
      if (this.outputCooldown > 0) {
         this.outputCooldown--;
      } else {
         ItemStack output = this.cookingLogic.getCookOutput();
         IItemHandlerSimpleInserter inventory = this.storageWrapper.getInventoryForUpgradeProcessing();
         if (!output.isEmpty() && inventory.insertItem(output, true).getCount() < output.getCount()) {
            ItemStack ret = inventory.insertItem(output, false);
            this.cookingLogic.getCookingInventory().extractItem(2, output.getCount() - ret.getCount(), false);
            this.tryPushingXpToTanks();
         } else {
            this.outputCooldown = 60;
         }

         ItemStack fuel = this.cookingLogic.getFuel();
         if (!fuel.isEmpty() && fuel.getBurnTime(this.recipeType) <= 0 && inventory.insertItem(fuel, true).getCount() < fuel.getCount()) {
            ItemStack ret = inventory.insertItem(fuel, false);
            this.cookingLogic.getCookingInventory().extractItem(1, fuel.getCount() - ret.getCount(), false);
         }
      }
   }

   private void tryPushingXpToTanks() {
      this.storageWrapper.getFluidHandler().ifPresent(fluidHandler -> {
         float storedExperience = this.cookingLogic.getStoredExperience();

         for (int i = 0; i < fluidHandler.getTanks(); i++) {
            FluidStack xpFluidStack = new FluidStack((Fluid)ModFluids.XP_STILL.get(), XpHelper.experienceToLiquid(storedExperience));
            int filled = fluidHandler.fill(xpFluidStack, FluidAction.SIMULATE);
            if (filled > 0) {
               fluidHandler.fill(xpFluidStack, FluidAction.EXECUTE);
               this.cookingLogic.drainStoredExperience(XpHelper.liquidToExperience(filled));
               storedExperience -= XpHelper.liquidToExperience(filled);
               if (storedExperience <= 0.0F) {
                  break;
               }
            }
         }
      });
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         this.tryPushingOutput();
         this.tryPullingFuel();
         this.tryPullingInput();
         if (!this.cookingLogic.tick(level) && this.outputCooldown <= 0 && this.fuelCooldown <= 0 && this.inputCooldown <= 0) {
            this.setCooldown(level, 10);
         }

         boolean isBurning = this.cookingLogic.isBurning(level);
         RenderInfo renderInfo = this.storageWrapper.getRenderInfo();
         if (renderInfo.getUpgradeRenderData(CookingUpgradeRenderData.TYPE).map(CookingUpgradeRenderData::isBurning).orElse(false) != isBurning) {
            if (isBurning) {
               renderInfo.setUpgradeRenderData(CookingUpgradeRenderData.TYPE, new CookingUpgradeRenderData(true));
            } else {
               renderInfo.removeUpgradeRenderData(CookingUpgradeRenderData.TYPE);
            }
         }
      }
   }

   private void tryPullingInput() {
      if (this.inputCooldown > 0) {
         this.inputCooldown--;
      } else {
         if (this.tryPullingGetUnsucessful(this.cookingLogic.getCookInput(), this.cookingLogic::setCookInput, this.isValidInput)) {
            this.inputCooldown = 60;
         }
      }
   }

   private void tryPullingFuel() {
      if (this.fuelCooldown > 0) {
         this.fuelCooldown--;
      } else {
         if (this.tryPullingGetUnsucessful(this.cookingLogic.getFuel(), this.cookingLogic::setFuel, this.isValidFuel)) {
            this.fuelCooldown = 60;
         }
      }
   }

   private boolean tryPullingGetUnsucessful(ItemStack stack, Consumer<ItemStack> setSlot, Predicate<ItemStack> isItemValid) {
      IItemHandlerModifiable inventory = this.storageWrapper.getInventoryForUpgradeProcessing();
      ItemStack toExtract;
      if (stack.isEmpty()) {
         AtomicReference<ItemStack> ret = new AtomicReference<>(ItemStack.EMPTY);
         InventoryHelper.iterate(inventory, (slot, st) -> {
            if (isItemValid.test(st)) {
               ret.set(st.copy());
            }
         }, () -> !ret.get().isEmpty());
         if (ret.get().isEmpty()) {
            return true;
         }

         toExtract = ret.get();
         toExtract.setCount(toExtract.getMaxStackSize());
      } else {
         if (stack.getCount() == stack.getMaxStackSize() || !isItemValid.test(stack)) {
            return true;
         }

         toExtract = stack.copy();
         toExtract.setCount(stack.getMaxStackSize() - stack.getCount());
      }

      if (InventoryHelper.extractFromInventory(toExtract, inventory, true).getCount() > 0) {
         ItemStack toSet = InventoryHelper.extractFromInventory(toExtract, inventory, false);
         toSet.grow(stack.getCount());
         setSlot.accept(toSet);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public CookingLogic<R> getCookingLogic() {
      return this.cookingLogic;
   }

   public FilterLogic getInputFilterLogic() {
      return this.inputFilterLogic;
   }

   public FilterLogic getFuelFilterLogic() {
      return this.fuelFilterLogic;
   }

   public static class AutoBlastingUpgradeWrapper
      extends AutoCookingUpgradeWrapper<AutoCookingUpgradeWrapper.AutoBlastingUpgradeWrapper, AutoBlastingUpgradeItem, BlastingRecipe> {
      public AutoBlastingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.BLASTING, 0.5F);
      }
   }

   public static class AutoSmeltingUpgradeWrapper
      extends AutoCookingUpgradeWrapper<AutoCookingUpgradeWrapper.AutoSmeltingUpgradeWrapper, AutoSmeltingUpgradeItem, SmeltingRecipe> {
      public AutoSmeltingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.SMELTING, 1.0F);
      }
   }

   public static class AutoSmokingUpgradeWrapper
      extends AutoCookingUpgradeWrapper<AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper, AutoSmokingUpgradeItem, SmokingRecipe> {
      public AutoSmokingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.SMOKING, 0.5F);
      }
   }
}

package net.p3pp3rf1y.sophisticatedcore.upgrades.tank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedTankUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IStackableContentsUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.XpHelper;

public class TankUpgradeWrapper
   extends UpgradeWrapperBase<TankUpgradeWrapper, TankUpgradeItem>
   implements IRenderedTankUpgrade,
   ITickableUpgrade,
   IStackableContentsUpgrade {
   public static final int INPUT_SLOT = 0;
   public static final int OUTPUT_SLOT = 1;
   public static final int INPUT_RESULT_SLOT = 2;
   public static final int OUTPUT_RESULT_SLOT = 3;
   private Consumer<IRenderedTankUpgrade.TankRenderInfo> updateTankRenderInfoCallback;
   private final TankUpgradeWrapper.TankComponentItemHandler inventory;
   private FluidStack contents;
   private long cooldownTime = 0L;
   private static final Map<ItemStack, Function<ItemStack, IFluidHandlerItem>> CUSTOM_FLUIDHANDLER_FACTORIES = Map.of(
      new ItemStack(Items.EXPERIENCE_BOTTLE),
      stack -> new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.Full(
         stack, Items.GLASS_BOTTLE, new ItemStack(Items.EXPERIENCE_BOTTLE), XpHelper.experienceToLiquid(8.0F), (Fluid)ModFluids.XP_STILL.get()
      ),
      PotionContents.createItemStack(Items.POTION, Potions.WATER),
      stack -> new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.Full(
         stack, Items.GLASS_BOTTLE, PotionContents.createItemStack(Items.POTION, Potions.WATER), 250, Fluids.WATER
      ),
      new ItemStack(Items.GLASS_BOTTLE),
      stack -> new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.Empty(
         stack,
         Items.GLASS_BOTTLE,
         new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition(
            new ItemStack(Items.EXPERIENCE_BOTTLE), XpHelper.experienceToLiquid(8.0F), (Fluid)ModFluids.XP_STILL.get()
         ),
         new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition(
            PotionContents.createItemStack(Items.POTION, Potions.WATER), 250, Fluids.WATER
         )
      )
   );

   protected TankUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.contents = getContents(upgrade).copy();
      if (upgrade.has(DataComponents.CONTAINER)) {
         upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, (ItemContainerContents)upgrade.get(DataComponents.CONTAINER));
      }

      upgrade.remove(DataComponents.CONTAINER);
      this.inventory = new TankUpgradeWrapper.TankComponentItemHandler(upgrade);
   }

   public static SimpleFluidContent getContents(ItemStack upgrade) {
      return (SimpleFluidContent)upgrade.getOrDefault(ModCoreDataComponents.FLUID_CONTENTS, SimpleFluidContent.EMPTY);
   }

   private boolean isValidFluidHandler(IFluidHandlerItem fluidHandler, boolean isOutput) {
      boolean tankEmpty = this.contents.isEmpty();
      int tank = 0;

      while (tank < fluidHandler.getTanks()) {
         FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);
         if (!isOutput
            || fluidInTank.getAmount() >= fluidHandler.getTankCapacity(tank)
            || !fluidInTank.isEmpty() && !tankEmpty && !FluidStack.isSameFluidSameComponents(fluidInTank, this.contents)) {
            if (isOutput || fluidInTank.isEmpty() || !tankEmpty && !FluidStack.isSameFluidSameComponents(this.contents, fluidInTank)) {
               tank++;
               continue;
            }

            return true;
         }

         return true;
      }

      return false;
   }

   private boolean hasNoMatchingFluid(IFluidHandlerItem fluidHandler) {
      boolean tankEmpty = this.contents.isEmpty();

      for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
         FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);
         if (!tankEmpty && FluidStack.isSameFluidSameComponents(fluidInTank, this.contents)) {
            return false;
         }

         if (!fluidInTank.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   private boolean matchingTankIsFull(IFluidHandlerItem fluidHandler) {
      boolean tankEmpty = this.contents.isEmpty();

      for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
         FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);
         int tankCapacity = fluidHandler.getTankCapacity(tank);
         if (tankEmpty && fluidInTank.getAmount() < tankCapacity) {
            return false;
         }

         if (FluidStack.isSameFluidSameComponents(fluidInTank, this.contents) && fluidInTank.getAmount() < tankCapacity) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void setTankRenderInfoUpdateCallback(Consumer<IRenderedTankUpgrade.TankRenderInfo> updateTankRenderInfoCallback) {
      this.updateTankRenderInfoCallback = updateTankRenderInfoCallback;
   }

   @Override
   public void forceUpdateTankRenderInfo() {
      IRenderedTankUpgrade.TankRenderInfo renderInfo = new IRenderedTankUpgrade.TankRenderInfo();
      if (!this.contents.isEmpty()) {
         renderInfo.setFluid(this.contents);
         renderInfo.setFillRatio(Math.round((float)this.contents.getAmount() / this.getTankCapacity() * 10.0F) / 10.0F);
      }

      this.updateTankRenderInfoCallback.accept(renderInfo);
   }

   public FluidStack getContents() {
      return this.contents;
   }

   public int getTankCapacity() {
      return this.upgradeItem.getTankCapacity(this.storageWrapper);
   }

   public TankUpgradeWrapper.TankComponentItemHandler getInventory() {
      return this.inventory;
   }

   private int getMaxInOut() {
      return (int)Math.max(
         1000.0,
         (Integer)this.upgradeItem.getTankUpgradeConfig().maxInputOutput.get() * this.storageWrapper.getNumberOfSlotRows()
            * this.upgradeItem.getAdjustedStackMultiplier(this.storageWrapper)
      );
   }

   public int fill(FluidStack resource, FluidAction action, boolean ignoreInOutLimit) {
      int capacity = this.getTankCapacity();
      if (this.contents.getAmount() < capacity && (this.contents.isEmpty() || FluidStack.isSameFluidSameComponents(resource, this.contents))) {
         int toFill = Math.min(capacity - this.contents.getAmount(), resource.getAmount());
         if (!ignoreInOutLimit) {
            toFill = Math.min(this.getMaxInOut(), toFill);
         }

         if (action == FluidAction.EXECUTE) {
            if (this.contents.isEmpty()) {
               this.contents = resource.copyWithAmount(toFill);
            } else {
               this.contents.setAmount(this.contents.getAmount() + toFill);
            }

            this.serializeContents();
         }

         return toFill;
      } else {
         return 0;
      }
   }

   private void serializeContents() {
      this.upgrade.set(ModCoreDataComponents.FLUID_CONTENTS, SimpleFluidContent.copyOf(this.contents));
      this.save();
      this.forceUpdateTankRenderInfo();
   }

   public FluidStack drain(int maxDrain, FluidAction action, boolean ignoreInOutLimit) {
      if (this.contents.isEmpty()) {
         return FluidStack.EMPTY;
      }

      int toDrain = Math.min(maxDrain, this.contents.getAmount());
      if (!ignoreInOutLimit) {
         toDrain = Math.min(this.getMaxInOut(), toDrain);
      }

      FluidStack ret = this.contents.copyWithAmount(toDrain);
      if (action == FluidAction.EXECUTE) {
         if (toDrain == this.contents.getAmount()) {
            this.contents = FluidStack.EMPTY;
         } else {
            this.contents.setAmount(this.contents.getAmount() - toDrain);
         }

         this.serializeContents();
      }

      return ret;
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (level.getGameTime() >= this.cooldownTime) {
         boolean didSomething = this.tryDraining(this.inventory.getStackInSlot(0));
         didSomething |= this.tryFilling(this.inventory.getStackInSlot(1));
         if (didSomething) {
            this.cooldownTime = level.getGameTime() + ((Integer)this.upgradeItem.getTankUpgradeConfig().autoFillDrainContainerCooldown.get()).intValue();
         }
      }
   }

   private boolean tryDraining(ItemStack inputSlotStack) {
      ItemStack stackToDrain = inputSlotStack;
      if (inputSlotStack.getCount() > 1) {
         stackToDrain = inputSlotStack.copyWithCount(1);
         return !this.drainStack(stackToDrain, true, stack -> {})
            ? false
            : this.drainStack(stackToDrain, false, stack -> this.inventory.setStackInSlot(0, inputSlotStack.copyWithCount(inputSlotStack.getCount() - 1)));
      } else {
         return this.drainStack(stackToDrain, false, stack -> this.inventory.setStackInSlot(0, stack));
      }
   }

   private boolean drainStack(ItemStack stackToDrain, boolean simulateFullDrain, Consumer<ItemStack> updateContainerStack) {
      return this.getFluidHandler(stackToDrain)
         .map(fluidHandler -> this.drainHandler(fluidHandler, updateContainerStack, true, simulateFullDrain))
         .orElse(false);
   }

   private Optional<IFluidHandlerItem> getFluidHandler(ItemStack stack) {
      IFluidHandlerItem result = (IFluidHandlerItem)stack.getCapability(FluidHandler.ITEM);
      return result != null ? Optional.of(result) : getCustomFluidHandler(stack);
   }

   private boolean tryFilling(ItemStack outputSlotStack) {
      ItemStack stackToFill = outputSlotStack;
      if (outputSlotStack.getCount() > 1) {
         stackToFill = outputSlotStack.copyWithCount(1);
         return !this.fillStack(stackToFill, true, stack -> {})
            ? false
            : this.fillStack(stackToFill, false, stack -> this.inventory.setStackInSlot(1, outputSlotStack.copyWithCount(outputSlotStack.getCount() - 1)));
      } else {
         return this.fillStack(stackToFill, false, stack -> this.inventory.setStackInSlot(1, stack));
      }
   }

   private boolean fillStack(ItemStack outputSlotStack, boolean simulateFullFill, Consumer<ItemStack> updateContainerStack) {
      return this.getFluidHandler(outputSlotStack)
         .map(fluidHandler -> this.fillHandler(fluidHandler, updateContainerStack, true, simulateFullFill))
         .orElse(false);
   }

   public void interactWithCursorStack(ItemStack cursorStack, Consumer<ItemStack> updateContainerStack) {
      this.getFluidHandler(cursorStack).ifPresent(fluidHandler -> {
         FluidStack tankContents = this.getContents();
         if (tankContents.isEmpty()) {
            this.drainHandler(fluidHandler, updateContainerStack);
         } else if (!this.fillHandler(fluidHandler, updateContainerStack, false, false)) {
            this.drainHandler(fluidHandler, updateContainerStack);
         }
      });
   }

   private static Optional<IFluidHandlerItem> getCustomFluidHandler(ItemStack stack) {
      return CUSTOM_FLUIDHANDLER_FACTORIES.entrySet()
         .stream()
         .filter(e -> ItemStack.isSameItemSameComponents(stack, e.getKey()))
         .map(e -> e.getValue().apply(stack))
         .findFirst();
   }

   public boolean fillHandler(
      IFluidHandlerItem fluidHandler, Consumer<ItemStack> updateContainerStack, boolean moveFullToResult, boolean simulateIncludingFullFill
   ) {
      if (!this.contents.isEmpty() && this.isValidFluidHandler(fluidHandler, true)) {
         int filled = fluidHandler.fill(this.contents.copyWithAmount(Math.min(1000, this.contents.getAmount())), FluidAction.SIMULATE);
         if (filled <= 0) {
            return false;
         }

         if (moveFullToResult) {
            ItemStack containerCopy = fluidHandler.getContainer().copy();
            if (this.isFullAfterFillButUnableToInsertIntoResult(containerCopy)) {
               return false;
            }
         }

         if (simulateIncludingFullFill) {
            return fluidHandler.getTanks() > 0 && this.drain(filled, FluidAction.SIMULATE, false).getAmount() == fluidHandler.getTankCapacity(0);
         }

         FluidStack drained = this.drain(filled, FluidAction.EXECUTE, false);
         fluidHandler.fill(drained, FluidAction.EXECUTE);
         if (moveFullToResult && this.getFluidHandler(fluidHandler.getContainer()).map(this::matchingTankIsFull).orElse(false)) {
            updateContainerStack.accept(ItemStack.EMPTY);
            this.inventory.insertItem(3, fluidHandler.getContainer(), false);
         } else {
            updateContainerStack.accept(fluidHandler.getContainer());
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isFullAfterFillButUnableToInsertIntoResult(ItemStack containerCopy) {
      return this.getFluidHandler(containerCopy)
         .map(
            copyFluidHandler -> {
               copyFluidHandler.fill(this.contents.copyWithAmount(Math.min(1000, this.contents.getAmount())), FluidAction.EXECUTE);
               return this.getFluidHandler(copyFluidHandler.getContainer())
                  .map(
                     fh -> {
                        int tank = this.getMatchingTank(fh, this.contents);
                        return tank < 0
                           ? true
                           : fh.getFluidInTank(tank).getAmount() == fh.getTankCapacity(tank)
                              && !this.inventory.insertItem(3, copyFluidHandler.getContainer(), true).isEmpty();
                     }
                  )
                  .orElse(true);
            }
         )
         .orElse(true);
   }

   public void drainHandler(IFluidHandlerItem fluidHandler, Consumer<ItemStack> updateContainerStack) {
      this.drainHandler(fluidHandler, updateContainerStack, false, false);
   }

   public boolean drainHandler(
      IFluidHandlerItem fluidHandler, Consumer<ItemStack> updateContainerStack, boolean moveEmptyToResult, boolean simulateIncludingFullDrain
   ) {
      if (this.isValidFluidHandler(fluidHandler, false)) {
         FluidStack extracted = this.contents.isEmpty()
            ? fluidHandler.drain(1000, FluidAction.SIMULATE)
            : fluidHandler.drain(this.contents.copyWithAmount(Math.min(1000, this.getTankCapacity() - this.contents.getAmount())), FluidAction.SIMULATE);
         if (extracted.isEmpty()) {
            return false;
         }

         if (moveEmptyToResult) {
            ItemStack containerCopy = fluidHandler.getContainer().copy();
            if (this.isEmptyAfterDrainButUnableToInsertIntoResult(containerCopy, extracted)) {
               return false;
            }
         }

         if (simulateIncludingFullDrain) {
            return fluidHandler.getTanks() > 0 && this.fill(extracted, FluidAction.SIMULATE, false) == fluidHandler.getTankCapacity(0);
         }

         int filled = this.fill(extracted, FluidAction.EXECUTE, false);
         FluidStack toExtract = filled == extracted.getAmount() ? extracted : extracted.copyWithAmount(filled);
         fluidHandler.drain(toExtract, FluidAction.EXECUTE);
         if (moveEmptyToResult && this.getFluidHandler(fluidHandler.getContainer()).map(this::hasNoMatchingFluid).orElse(true)) {
            updateContainerStack.accept(ItemStack.EMPTY);
            this.inventory.insertItem(2, fluidHandler.getContainer(), false);
         } else {
            updateContainerStack.accept(fluidHandler.getContainer());
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isEmptyAfterDrainButUnableToInsertIntoResult(ItemStack containerCopy, FluidStack extracted) {
      return this.getFluidHandler(containerCopy)
         .map(
            copyFluidHandler -> {
               int tank = this.getMatchingTank(copyFluidHandler, extracted);
               if (tank < 0) {
                  return true;
               }

               copyFluidHandler.drain(extracted, FluidAction.EXECUTE);
               return this.getFluidHandler(copyFluidHandler.getContainer()).map(fh -> fh.getTanks() <= tank || fh.getFluidInTank(tank).isEmpty()).orElse(true)
                  && !this.inventory.insertItem(2, copyFluidHandler.getContainer(), true).isEmpty();
            }
         )
         .orElse(true);
   }

   private int getMatchingTank(IFluidHandlerItem fluidHandler, FluidStack matchTo) {
      for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
         FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);
         if (FluidStack.isSameFluidSameComponents(fluidInTank, matchTo)) {
            return tank;
         }
      }

      return -1;
   }

   @Override
   public int getMinimumMultiplierRequired() {
      return (int)Math.ceil((float)this.contents.getAmount() / this.upgradeItem.getBaseCapacity(this.storageWrapper));
   }

   @Override
   public boolean canBeDisabled() {
      return false;
   }

   private abstract static class SwapEmptyFluidContainerHandler implements IFluidHandlerItem {
      private ItemStack container;
      private final Item empty;
      private final Map<FluidStack, TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition> fullContainers = new HashMap<>();
      private FluidStack contents;

      protected SwapEmptyFluidContainerHandler(
         ItemStack container, Item empty, FluidStack contents, TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition... fullContainers
      ) {
         this.container = container;
         this.empty = empty;
         Arrays.stream(fullContainers).forEach(fc -> this.fullContainers.put(fc.validFluid, fc));
         this.contents = contents;
      }

      public ItemStack getContainer() {
         return this.container;
      }

      public int getTanks() {
         return 1;
      }

      public FluidStack getFluidInTank(int tank) {
         return this.contents;
      }

      public int getTankCapacity(int tank) {
         return this.getMatchingDefinition()
            .map(TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition::capacity)
            .orElseGet(
               () -> this.fullContainers
                  .values()
                  .stream()
                  .mapToInt(TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition::capacity)
                  .max()
                  .orElse(0)
            );
      }

      private Optional<TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition> getMatchingDefinition() {
         return this.fullContainers
            .entrySet()
            .stream()
            .filter(e -> FluidStack.isSameFluidSameComponents(e.getKey(), this.contents))
            .map(Entry::getValue)
            .findFirst();
      }

      public boolean isFluidValid(int i, FluidStack fluidStack) {
         return !this.contents.isEmpty()
            ? FluidStack.isSameFluidSameComponents(this.contents, fluidStack)
            : this.fullContainers.keySet().stream().anyMatch(validFluid -> FluidStack.isSameFluidSameComponents(validFluid, fluidStack));
      }

      public int fill(FluidStack fluidStack, FluidAction fluidAction) {
         return this.isFluidValid(0, fluidStack) && this.container.getItem() == this.empty ? this.findFirstFullContainer(fluidStack).map(fullContainer -> {
            int result = 0;
            int capacity = fullContainer.capacity();
            if (fluidStack.getAmount() >= capacity) {
               result = capacity;
               if (fluidAction == FluidAction.EXECUTE) {
                  this.container = fullContainer.full().copy();
                  this.contents = fluidStack.copyWithAmount(capacity);
               }
            }

            return result;
         }).orElse(0) : 0;
      }

      private Optional<TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition> findFirstFullContainer(FluidStack fluidStack) {
         return this.fullContainers
            .entrySet()
            .stream()
            .filter(e -> FluidStack.isSameFluidSameComponents(e.getKey(), fluidStack))
            .findFirst()
            .map(Entry::getValue);
      }

      public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
         return this.findFirstFullContainer(this.contents).map(fullContainer -> {
            if (!fluidStack.isEmpty() && ItemStack.isSameItemSameComponents(this.container, fullContainer.full())) {
               FluidStack result = FluidStack.EMPTY;
               if (this.isFluidValid(0, fluidStack) && fluidStack.getAmount() >= fullContainer.capacity()) {
                  result = fluidStack.copyWithAmount(fullContainer.capacity());
                  if (fluidAction == FluidAction.EXECUTE) {
                     this.container = new ItemStack(this.empty);
                     this.contents = FluidStack.EMPTY;
                  }
               }

               return result;
            } else {
               return FluidStack.EMPTY;
            }
         }).orElse(FluidStack.EMPTY);
      }

      public FluidStack drain(int toDrain, FluidAction fluidAction) {
         return this.container.getItem() != this.empty && toDrain >= this.contents.getAmount() ? this.drain(this.contents, fluidAction) : FluidStack.EMPTY;
      }

      public static class Empty extends TankUpgradeWrapper.SwapEmptyFluidContainerHandler {
         public Empty(ItemStack container, Item empty, TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition... fullContainers) {
            super(container, empty, FluidStack.EMPTY, fullContainers);
         }
      }

      public static class Full extends TankUpgradeWrapper.SwapEmptyFluidContainerHandler {
         public Full(ItemStack container, Item empty, ItemStack full, int capacity, Fluid validFluid) {
            super(
               container,
               empty,
               new FluidStack(validFluid, capacity),
               new TankUpgradeWrapper.SwapEmptyFluidContainerHandler.FullContainerDefinition(full, capacity, new FluidStack(validFluid, capacity))
            );
         }
      }

      private record FullContainerDefinition(ItemStack full, int capacity, FluidStack validFluid) {
         public FullContainerDefinition(ItemStack full, int capacity, Fluid validFluid) {
            this(full, capacity, new FluidStack(validFluid, capacity));
         }
      }
   }

   public class TankComponentItemHandler extends ComponentItemHandler {
      public TankComponentItemHandler(ItemStack upgrade) {
         super(upgrade, ModCoreDataComponents.LENIENT_CONTAINER.get(), 4);
         this.migrateLegacyContents();
      }

      private void migrateLegacyContents() {
         ItemContainerContents currentContents = this.getContents();
         ItemStack inputStack = this.getStackFromContents(currentContents, 0);
         if (!inputStack.isEmpty() && !this.hasValidFluidHandler(inputStack, false)) {
            this.setStackInSlot(0, ItemStack.EMPTY);
            this.setStackInSlot(2, inputStack);
         }

         ItemStack outputStack = this.getStackFromContents(currentContents, 1);
         if (!outputStack.isEmpty() && !this.hasValidFluidHandler(outputStack, true)) {
            this.setStackInSlot(1, ItemStack.EMPTY);
            this.setStackInSlot(3, outputStack);
         }
      }

      protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
         super.onContentsChanged(slot, oldStack, newStack);
         TankUpgradeWrapper.this.save();
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         if (slot == 0) {
            return stack.isEmpty() || this.hasValidFluidHandler(stack, false);
         } else {
            return slot == 1 ? stack.isEmpty() || this.hasValidFluidHandler(stack, true) : slot == 2 || slot == 3;
         }
      }

      private boolean hasValidFluidHandler(ItemStack stack, boolean isOutput) {
         return TankUpgradeWrapper.this.getFluidHandler(stack)
            .map(fluidHandler -> TankUpgradeWrapper.this.isValidFluidHandler(fluidHandler, isOutput))
            .orElse(false);
      }
   }
}

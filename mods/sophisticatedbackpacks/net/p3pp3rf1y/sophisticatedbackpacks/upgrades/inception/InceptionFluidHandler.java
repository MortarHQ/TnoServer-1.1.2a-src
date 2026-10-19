package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.EmptyFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;

public class InceptionFluidHandler implements IStorageFluidHandler {
   @Nullable
   private final IStorageFluidHandler wrappedFluidHandler;
   private final InventoryOrder inventoryOrder;
   private final SubBackpacksHandler subBackpacksHandler;
   private IStorageFluidHandler[] fluidHandlers;
   protected int[] baseIndex;
   protected int tankCount;
   private final ItemStack backpack;

   public InceptionFluidHandler(
      @Nullable IStorageFluidHandler wrappedFluidHandler, ItemStack backpack, InventoryOrder inventoryOrder, SubBackpacksHandler subBackpacksHandler
   ) {
      this.wrappedFluidHandler = wrappedFluidHandler;
      this.backpack = backpack;
      this.inventoryOrder = inventoryOrder;
      this.subBackpacksHandler = subBackpacksHandler;
      subBackpacksHandler.addRefreshListener(sbs -> this.refreshHandlers());
      this.refreshHandlers();
   }

   private void refreshHandlers() {
      List<IStorageFluidHandler> handlers = new ArrayList<>();
      if (this.wrappedFluidHandler != null && this.inventoryOrder == InventoryOrder.MAIN_FIRST) {
         handlers.add(this.wrappedFluidHandler);
      }

      this.subBackpacksHandler.getSubBackpacks().forEach(sbp -> sbp.getFluidHandler().ifPresent(handlers::add));
      if (this.wrappedFluidHandler != null && this.inventoryOrder == InventoryOrder.INCEPTED_FIRST) {
         handlers.add(this.wrappedFluidHandler);
      }

      this.fluidHandlers = handlers.toArray(new IStorageFluidHandler[0]);
      this.baseIndex = new int[this.fluidHandlers.length];
      int index = 0;

      for (int i = 0; i < this.fluidHandlers.length; i++) {
         index += this.fluidHandlers[i].getTanks();
         this.baseIndex[i] = index;
      }

      this.tankCount = index;
   }

   public int getTanks() {
      return this.tankCount;
   }

   private int getHandlerIndexForTank(int slot) {
      if (slot < 0) {
         return -1;
      }

      for (int i = 0; i < this.baseIndex.length; i++) {
         if (slot - this.baseIndex[i] < 0) {
            return i;
         }
      }

      return -1;
   }

   protected IFluidHandler getHandlerFromIndex(int index) {
      return (IFluidHandler)(index >= 0 && index < this.fluidHandlers.length ? this.fluidHandlers[index] : EmptyFluidHandler.INSTANCE);
   }

   protected int getTankFromIndex(int tank, int handlerIndex) {
      return handlerIndex > 0 && handlerIndex < this.baseIndex.length ? tank - this.baseIndex[handlerIndex - 1] : tank;
   }

   private <T> T getFluidHandlerValue(int overallTank, BiFunction<IFluidHandler, Integer, T> getValue) {
      int handlerIndex = this.getHandlerIndexForTank(overallTank);
      return getValue.apply(this.getHandlerFromIndex(handlerIndex), this.getTankFromIndex(overallTank, handlerIndex));
   }

   @Nonnull
   public FluidStack getFluidInTank(int tank) {
      return this.getFluidHandlerValue(tank, IFluidHandler::getFluidInTank);
   }

   public int getTankCapacity(int tank) {
      return this.<Integer>getFluidHandlerValue(tank, IFluidHandler::getTankCapacity);
   }

   public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
      return this.getFluidHandlerValue(tank, (h, t) -> h.isFluidValid(t, stack));
   }

   public int fill(FluidStack resource, FluidAction action, boolean ignoreInOutLimit) {
      int filled = 0;
      FluidStack toFill = resource;

      for (IStorageFluidHandler fluidHandler : this.fluidHandlers) {
         filled += fluidHandler.fill(toFill, action, ignoreInOutLimit);
         if (filled == resource.getAmount()) {
            return resource.getAmount();
         }

         toFill = new FluidStack(toFill.getFluid(), resource.getAmount() - filled);
      }

      return filled;
   }

   public int fill(FluidStack resource, FluidAction action) {
      return this.fill(resource, action, false);
   }

   public FluidStack drain(TagKey<Fluid> resourceTag, int maxDrain, FluidAction action, boolean ignoreInOutLimit) {
      FluidStack drainedStack = FluidStack.EMPTY;
      FluidStack stackToDrain = FluidStack.EMPTY;

      for (IStorageFluidHandler fluidHandler : this.fluidHandlers) {
         if (drainedStack.isEmpty()) {
            drainedStack = fluidHandler.drain(resourceTag, maxDrain, action, ignoreInOutLimit);
            if (drainedStack.getAmount() == maxDrain) {
               return drainedStack;
            }

            if (!drainedStack.isEmpty()) {
               stackToDrain = new FluidStack(drainedStack.getFluid(), maxDrain - drainedStack.getAmount());
            }
         } else {
            int amountDrained = fluidHandler.drain(stackToDrain, action, ignoreInOutLimit).getAmount();
            stackToDrain.shrink(amountDrained);
            drainedStack.grow(amountDrained);
            if (drainedStack.getAmount() == maxDrain) {
               return drainedStack;
            }
         }
      }

      return drainedStack;
   }

   public FluidStack drain(FluidStack resource, FluidAction action, boolean ignoreInOutLimit) {
      int drained = 0;
      FluidStack toDrain = resource;

      for (IStorageFluidHandler fluidHandler : this.fluidHandlers) {
         drained += fluidHandler.drain(toDrain, action, ignoreInOutLimit).getAmount();
         if (drained == resource.getAmount()) {
            return resource;
         }

         toDrain = new FluidStack(toDrain.getFluid(), resource.getAmount() - drained);
      }

      return drained == 0 ? FluidStack.EMPTY : new FluidStack(resource.getFluid(), drained);
   }

   @Nonnull
   public FluidStack drain(FluidStack resource, FluidAction action) {
      return this.drain(resource, action, false);
   }

   public FluidStack drain(int maxDrain, FluidAction action, boolean ignoreInOutLimit) {
      for (IStorageFluidHandler fluidHandler : this.fluidHandlers) {
         FluidStack drained = fluidHandler.drain(maxDrain, action, ignoreInOutLimit);
         if (!drained.isEmpty()) {
            return drained;
         }
      }

      return FluidStack.EMPTY;
   }

   @Nonnull
   public FluidStack drain(int maxDrain, FluidAction action) {
      return this.drain(maxDrain, action, false);
   }

   @Nonnull
   public ItemStack getContainer() {
      return this.backpack;
   }
}

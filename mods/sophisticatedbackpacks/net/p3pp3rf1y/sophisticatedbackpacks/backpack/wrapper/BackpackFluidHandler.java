package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeWrapper;

public class BackpackFluidHandler implements IStorageFluidHandler {
   private final IStorageWrapper backpackWrapper;

   public BackpackFluidHandler(IStorageWrapper backpackWrapper) {
      this.backpackWrapper = backpackWrapper;
   }

   public int getTanks() {
      return this.getAllTanks().size();
   }

   public FluidStack getFluidInTank(int tank) {
      return this.isInvalidTank(tank) ? FluidStack.EMPTY : this.getAllTanks().get(tank).getContents();
   }

   @Nonnull
   private List<TankUpgradeWrapper> getAllTanks() {
      return this.backpackWrapper.getUpgradeHandler().getTypeWrappers(TankUpgradeItem.TYPE);
   }

   public int getTankCapacity(int tank) {
      return this.isInvalidTank(tank) ? 0 : this.getAllTanks().get(tank).getTankCapacity();
   }

   public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
      if (this.isInvalidTank(tank)) {
         return false;
      }

      FluidStack contents = this.getAllTanks().get(tank).getContents();
      return contents.isEmpty() || FluidStack.isSameFluidSameComponents(contents, stack);
   }

   public int fill(FluidStack resource, FluidAction action, boolean ignoreInOutLimit) {
      int filled = 0;
      FluidStack toFill = resource;

      for (TankUpgradeWrapper tank : this.getAllTanks()) {
         filled += tank.fill(toFill, action, ignoreInOutLimit);
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
      FluidStack drained = FluidStack.EMPTY;
      int toDrain = maxDrain;

      for (TankUpgradeWrapper tank : this.getAllTanks()) {
         if (drained.isEmpty() && tank.getContents().is(resourceTag) || FluidStack.isSameFluidSameComponents(tank.getContents(), drained)) {
            if (drained.isEmpty()) {
               drained = tank.drain(toDrain, action, ignoreInOutLimit);
            } else {
               drained.grow(tank.drain(toDrain, action, ignoreInOutLimit).getAmount());
            }

            if (drained.getAmount() == maxDrain) {
               return drained;
            }

            toDrain = maxDrain - drained.getAmount();
         }
      }

      return drained;
   }

   public FluidStack drain(FluidStack resource, FluidAction action, boolean ignoreInOutLimit) {
      int drained = 0;
      int toDrain = resource.getAmount();

      for (TankUpgradeWrapper tank : this.getAllTanks()) {
         if (FluidStack.isSameFluidSameComponents(tank.getContents(), resource)) {
            drained += tank.drain(toDrain, action, ignoreInOutLimit).getAmount();
            if (drained == resource.getAmount()) {
               return resource;
            }

            toDrain = resource.getAmount() - drained;
         }
      }

      return drained == 0 ? FluidStack.EMPTY : new FluidStack(resource.getFluid(), drained);
   }

   public FluidStack drain(FluidStack resource, FluidAction action) {
      return this.drain(resource, action, false);
   }

   public FluidStack drain(int maxDrain, FluidAction action, boolean ignoreInOutLimit) {
      for (TankUpgradeWrapper tank : this.getAllTanks()) {
         FluidStack drained = tank.drain(maxDrain, action, ignoreInOutLimit);
         if (!drained.isEmpty()) {
            return drained;
         }
      }

      return FluidStack.EMPTY;
   }

   public FluidStack drain(int maxDrain, FluidAction action) {
      return this.drain(maxDrain, action, false);
   }

   private boolean isInvalidTank(int tank) {
      return tank < 0 || tank >= this.getTanks();
   }

   public ItemStack getContainer() {
      return this.backpackWrapper.getWrappedStorageStack();
   }
}

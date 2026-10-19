package net.p3pp3rf1y.sophisticatedcore.api;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public interface IStorageFluidHandler extends IFluidHandlerItem {
   default int fill(TagKey<Fluid> fluidTag, int maxFill, Fluid fallbackFluid, FluidAction action) {
      return this.fill(fluidTag, maxFill, fallbackFluid, action, false);
   }

   default int fill(TagKey<Fluid> fluidTag, int maxFill, Fluid fallbackFluid, FluidAction action, boolean ignoreInOutLimit) {
      for (int tank = 0; tank < this.getTanks(); tank++) {
         FluidStack tankFluid = this.getFluidInTank(tank);
         if (tankFluid.is(fluidTag)) {
            return this.fill(new FluidStack(tankFluid.getFluid(), maxFill), action, ignoreInOutLimit);
         }
      }

      return this.fill(new FluidStack(fallbackFluid, maxFill), action, ignoreInOutLimit);
   }

   int fill(FluidStack var1, FluidAction var2, boolean var3);

   FluidStack drain(TagKey<Fluid> var1, int var2, FluidAction var3, boolean var4);

   FluidStack drain(FluidStack var1, FluidAction var2, boolean var3);

   FluidStack drain(int var1, FluidAction var2, boolean var3);
}

package dev.ftb.mods.ftblibrary.util.neoforge;

import net.neoforged.neoforge.fluids.FluidStack;

public final class FluidKey {
   public final FluidStack stack;

   public FluidKey(FluidStack s) {
      this.stack = s;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FluidKey fluidKey = (FluidKey)o;
         return FluidStack.isSameFluidSameComponents(this.stack, fluidKey.stack);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return FluidStack.hashFluidAndComponents(this.stack);
   }
}

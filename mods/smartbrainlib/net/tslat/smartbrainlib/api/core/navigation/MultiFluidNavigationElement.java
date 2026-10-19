package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.fluids.FluidType;

public interface MultiFluidNavigationElement {
   default boolean canSwimInFluid(Mob mob, FluidType fluidType) {
      return this.canSwimInFluid(mob, fluidType, 1.0);
   }

   default boolean canSwimInFluid(Mob mob, FluidType fluidType, double fluidHeight) {
      return fluidType.canSwim(mob);
   }
}

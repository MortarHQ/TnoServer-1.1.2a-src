package com.chronodawn.fluids.neoforge;

import com.chronodawn.fluids.DecorativeWaterFluid;
import com.chronodawn.neoforge.registry.ModFluidTypes;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidFactoryImpl {
   public static FlowingFluid createDecorativeWaterSource() {
      return new DecorativeWaterFluid.Source() {
         public FluidType getFluidType() {
            return ModFluidTypes.DECORATIVE_WATER_TYPE.get();
         }
      };
   }

   public static FlowingFluid createDecorativeWaterFlowing() {
      return new DecorativeWaterFluid.Flowing() {
         public FluidType getFluidType() {
            return ModFluidTypes.DECORATIVE_WATER_TYPE.get();
         }
      };
   }
}

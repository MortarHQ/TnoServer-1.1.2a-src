package com.chronodawn.fluids;

import com.chronodawn.fluids.neoforge.FluidFactoryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.level.material.FlowingFluid;

public class FluidFactory {
   @ExpectPlatform
   @Transformed
   public static FlowingFluid createDecorativeWaterSource() {
      return FluidFactoryImpl.createDecorativeWaterSource();
   }

   @ExpectPlatform
   @Transformed
   public static FlowingFluid createDecorativeWaterFlowing() {
      return FluidFactoryImpl.createDecorativeWaterFlowing();
   }
}

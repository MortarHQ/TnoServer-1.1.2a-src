package com.blackgear.platform.common.worldgen.placement.parameters;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;

public enum Erosion {
   EROSION_0(Parameter.span(-1.0F, -0.78F)),
   EROSION_1(Parameter.span(-0.78F, -0.375F)),
   EROSION_2(Parameter.span(-0.375F, -0.2225F)),
   EROSION_3(Parameter.span(-0.2225F, 0.05F)),
   EROSION_4(Parameter.span(0.05F, 0.45F)),
   EROSION_5(Parameter.span(0.45F, 0.55F)),
   EROSION_6(Parameter.span(0.55F, 1.0F)),
   FULL_RANGE(Parameter.span(-1.0F, 1.0F));

   private final Parameter parameter;

   Erosion(Parameter parameter) {
      this.parameter = parameter;
   }

   public Parameter parameter() {
      return this.parameter;
   }

   public static Parameter span(Erosion min, Erosion max) {
      return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
   }
}

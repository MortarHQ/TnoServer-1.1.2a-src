package net.mebahelsapi.processor;

import net.mebahelsapi.MebahelsAPI;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3828;
import net.minecraft.class_7923;

public class MyProcessors {
   public static final class_3828<WaterRemovalProcessor> WATER_REMOVAL_PROCESSOR = (class_3828<WaterRemovalProcessor>)class_2378.method_10230(
      class_7923.field_41161, class_2960.method_60655("mebahelsapi", "water_removal_processor"), (class_3828)() -> WaterRemovalProcessor.CODEC
   );

   public static void init() {
      MebahelsAPI.LOGGER.info("Register structure processors");
   }
}

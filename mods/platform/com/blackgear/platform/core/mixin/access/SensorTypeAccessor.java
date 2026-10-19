package com.blackgear.platform.core.mixin.access;

import java.util.function.Supplier;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SensorType.class)
public interface SensorTypeAccessor {
   @Invoker("<init>")
   static <T extends Sensor<?>> SensorType<T> createSensorType(Supplier<T> supplier) {
      throw new AssertionError();
   }
}

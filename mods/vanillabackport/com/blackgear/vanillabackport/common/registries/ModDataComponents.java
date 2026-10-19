package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.core.registries.ModRegistries;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

public class ModDataComponents {
   public static final CoreRegistry<DataComponentType<?>> COMPONENTS = CoreRegistry.create(BuiltInRegistries.DATA_COMPONENT_TYPE, "minecraft");
   public static final Supplier<DataComponentType<ResourceKey<ChickenVariant>>> CHICKEN_VARIANT = register(
      "chicken/variant",
      builder -> builder.persistent(ResourceKey.codec(ModRegistries.CHICKEN_VARIANT.key()))
         .networkSynchronized(ResourceKey.streamCodec(ModRegistries.CHICKEN_VARIANT.key()))
   );

   public static <T> Supplier<DataComponentType<T>> register(String key, UnaryOperator<Builder<T>> operator) {
      return COMPONENTS.register(key, () -> operator.apply(DataComponentType.builder()).build());
   }
}

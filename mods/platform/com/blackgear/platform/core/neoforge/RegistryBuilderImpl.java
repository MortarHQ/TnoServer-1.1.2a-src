package com.blackgear.platform.core.neoforge;

import com.blackgear.platform.core.RegistryBuilder;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

public class RegistryBuilderImpl {
   public static RegistryBuilder create(String modId) {
      return new RegistryBuilder(modId) {
         @Override
         public <T> Supplier<Registry<T>> registry(ResourceKey<Registry<T>> key) {
            if (BuiltInRegistries.REGISTRY instanceof MappedRegistry<? extends Registry<?>> registry) {
               registry.unfreeze();
            }

            Registry<T> registry = BuiltInRegistries.registerSimple(key, builder -> new Object());
            if (BuiltInRegistries.REGISTRY instanceof MappedRegistry<? extends Registry<?>> mappedRegistry) {
               mappedRegistry.freeze();
            }

            return () -> registry;
         }
      };
   }
}

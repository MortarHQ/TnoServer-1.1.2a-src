package com.blamejared.crafttweaker.platform.registry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public interface RegistryWrapper<T> {
   Optional<T> getOptional(ResourceLocation var1);

   ResourceLocation getKey(T var1);

   T get(ResourceLocation var1);

   Stream<T> stream();

   Set<ResourceLocation> keySet();

   boolean containsKey(ResourceLocation var1);

   default Stream<ResourceLocation> keyStream() {
      return this.keySet().stream();
   }

   default Stream<T> getForNamespace(String namespace) {
      return this.keyStream()
         .filter(resourceLocation -> resourceLocation.getNamespace().equals(namespace))
         .map(
            location -> this.getOptional(location)
               .orElseThrow(() -> new IllegalArgumentException("Cannot get registry object from name: '" + location + "'! This should never happen!"))
         );
   }
}

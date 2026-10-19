package com.blamejared.crafttweaker.platform.registry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class VanillaRegistryWrapper<T> implements RegistryWrapper<T> {
   private final Registry<T> registry;

   public VanillaRegistryWrapper(Registry<T> registry) {
      this.registry = registry;
   }

   @Override
   public Optional<T> getOptional(ResourceLocation location) {
      return this.registry.getOptional(location);
   }

   @Override
   public ResourceLocation getKey(T object) {
      return this.registry.getKey(object);
   }

   @Override
   public T get(ResourceLocation location) {
      return (T)this.registry.get(location);
   }

   @Override
   public Stream<T> stream() {
      return this.registry.stream();
   }

   @Override
   public boolean containsKey(ResourceLocation location) {
      return this.registry.containsKey(location);
   }

   @Override
   public Set<ResourceLocation> keySet() {
      return this.registry.keySet();
   }
}

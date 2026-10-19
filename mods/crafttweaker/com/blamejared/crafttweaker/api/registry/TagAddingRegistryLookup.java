package com.blamejared.crafttweaker.api.registry;

import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceKey;

public class TagAddingRegistryLookup implements Provider {
   private final RegistryAccess registryAccess;

   public TagAddingRegistryLookup(RegistryAccess delegate) {
      this.registryAccess = delegate;
   }

   public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
      return this.registryAccess.listRegistries();
   }

   public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> key) {
      return this.registryAccess.registry(key).map(Registry::asTagAddingLookup);
   }
}

package com.almostreliable.lootjs;

import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceKey;

public class LookupProvider implements Provider {
   private final RegistryAccess registryAccess;

   public LookupProvider(RegistryAccess registryAccess) {
      this.registryAccess = registryAccess;
   }

   public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
      return this.registryAccess.listRegistries();
   }

   public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> arg) {
      return this.registryAccess.registry(arg).map(Registry::asTagAddingLookup);
   }
}

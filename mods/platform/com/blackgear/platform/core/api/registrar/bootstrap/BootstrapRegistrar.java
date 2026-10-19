package com.blackgear.platform.core.api.registrar.bootstrap;

import java.util.LinkedHashMap;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class BootstrapRegistrar<T> {
   protected final LinkedHashMap<ResourceKey<T>, Function<BootstrapContext<T>, T>> entries = new LinkedHashMap<>();
   protected final ResourceKey<? extends Registry<T>> registry;
   protected final String modId;

   BootstrapRegistrar(ResourceKey<? extends Registry<T>> registry, String modId) {
      this.registry = registry;
      this.modId = modId;
   }

   public static <T> BootstrapRegistrar<T> create(ResourceKey<? extends Registry<T>> registry, String modId) {
      return new BootstrapRegistrar<>(registry, modId);
   }

   public ResourceKey<T> register(String name, Function<BootstrapContext<T>, T> factory) {
      ResourceKey<T> key = ResourceKey.create(this.registry, ResourceLocation.fromNamespaceAndPath(this.modId, name));
      this.entries.put(key, factory);
      return key;
   }

   public void bootstrap(BootstrapContext<T> context) {
      this.entries.forEach((key, factory) -> context.register(key, factory.apply(context)));
   }
}

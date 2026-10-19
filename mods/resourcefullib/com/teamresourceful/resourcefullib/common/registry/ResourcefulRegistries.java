package com.teamresourceful.resourcefullib.common.registry;

import com.teamresourceful.resourcefullib.common.registry.neoforge.ResourcefulRegistriesImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.core.Registry;

public class ResourcefulRegistries {
   public static <T> ResourcefulRegistry<T> create(ResourcefulRegistry<T> parent) {
      return new ResourcefulRegistryChild<>(parent);
   }

   @ExpectPlatform
   @Transformed
   public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
      return ResourcefulRegistriesImpl.create(registry, id);
   }

   @ExpectPlatform
   @Transformed
   public static <D, T extends ResourcefulRegistry<D>> T create(ResourcefulRegistryType<D, T> type, String id) {
      return ResourcefulRegistriesImpl.create(type, id);
   }
}

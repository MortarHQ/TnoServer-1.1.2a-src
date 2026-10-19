package fuzs.puzzleslib.api.init.v3.registry;

import fuzs.puzzleslib.impl.core.CommonFactories;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface RegistryFactory {
   RegistryFactory INSTANCE = CommonFactories.INSTANCE.getRegistryFactory();

   default <T> Registry<T> create(ResourceKey<Registry<T>> registryKey) {
      return this.create(registryKey, true);
   }

   <T> Registry<T> create(ResourceKey<Registry<T>> var1, boolean var2);

   default <T> Registry<T> create(ResourceKey<Registry<T>> registryKey, String defaultKey) {
      return this.create(registryKey, defaultKey, true);
   }

   default <T> Registry<T> create(ResourceKey<Registry<T>> registryKey, String defaultKey, boolean synced) {
      return this.create(registryKey, registryKey.location().withPath(defaultKey), synced);
   }

   <T> Registry<T> create(ResourceKey<Registry<T>> var1, ResourceLocation var2, boolean var3);

   <T> Registry<T> register(Registry<T> var1);
}

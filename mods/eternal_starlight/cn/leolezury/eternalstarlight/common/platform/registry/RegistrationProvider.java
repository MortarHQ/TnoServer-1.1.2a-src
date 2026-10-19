package cn.leolezury.eternalstarlight.common.platform.registry;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface RegistrationProvider<T> {
   static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> key, String namespace) {
      return ESPlatform.INSTANCE.createRegistrationProvider(key, namespace);
   }

   static <T> RegistrationProvider<T> newRegistry(ResourceKey<? extends Registry<T>> key, String namespace) {
      return ESPlatform.INSTANCE.createNewRegistryProvider(key, namespace);
   }

   Registry<T> registry();

   <I extends T> RegistryObject<T, I> register(String var1, Supplier<? extends I> var2);
}

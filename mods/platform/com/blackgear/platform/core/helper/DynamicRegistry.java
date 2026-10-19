package com.blackgear.platform.core.helper;

import com.blackgear.platform.core.helper.neoforge.DynamicRegistryImpl;
import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class DynamicRegistry {
   @ExpectPlatform
   @Transformed
   public static void onRegister(Consumer<DynamicRegistry.Registrar> consumer) {
      DynamicRegistryImpl.onRegister(consumer);
   }

   public interface Registrar {
      <T> void registerDynamicRegistry(ResourceKey<Registry<T>> var1, Codec<T> var2);
   }
}

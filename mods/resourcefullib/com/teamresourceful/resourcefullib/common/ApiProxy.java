package com.teamresourceful.resourcefullib.common;

import java.util.Objects;
import net.minecraft.core.RegistryAccess;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface ApiProxy {
   static boolean hasProxy() {
      return ApiProxy.Storage.instance != null;
   }

   RegistryAccess getRegistryAccess();

   static RegistryAccess getRegistry() {
      Objects.requireNonNull(ApiProxy.Storage.instance, "ApiProxy instance is null");
      return ApiProxy.Storage.instance.getRegistryAccess();
   }

   @Internal
   static void setInstance(ApiProxy instance) {
      ApiProxy.Storage.instance = instance;
   }

   @Internal
   class Storage {
      private static ApiProxy instance = null;
   }
}

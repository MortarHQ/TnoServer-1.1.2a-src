package com.natamus.collective_common_forge.services.helpers;

public interface ModLoaderHelper {
   String getModLoaderName();

   String getGameDirectory();

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();

   boolean isClientSide();

   boolean isJarJard(String var1);

   boolean isBundleModEnabled(String var1);
}

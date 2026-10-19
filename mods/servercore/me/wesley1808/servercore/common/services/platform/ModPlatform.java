package me.wesley1808.servercore.common.services.platform;

import java.nio.file.Path;

public interface ModPlatform {
   boolean isClient();

   boolean isModLoaded(String var1);

   Path getConfigDir();

   String getVersion();
}

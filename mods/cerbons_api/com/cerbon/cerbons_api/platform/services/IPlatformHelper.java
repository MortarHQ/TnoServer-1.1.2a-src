package com.cerbon.cerbons_api.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {
   String getPlatformName();

   Path getConfigDir();

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();

   boolean isClientDist();

   boolean isRunningDataGen();

   default String getEnvironmentName() {
      return this.isDevelopmentEnvironment() ? "development" : "production";
   }
}

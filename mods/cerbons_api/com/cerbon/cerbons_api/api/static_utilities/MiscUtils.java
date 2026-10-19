package com.cerbon.cerbons_api.api.static_utilities;

import com.cerbon.cerbons_api.platform.Services;
import java.nio.file.Path;

public class MiscUtils {
   public static String getPlatformName() {
      return Services.PLATFORM.getPlatformName();
   }

   public static Path getConfigDir() {
      return Services.PLATFORM.getConfigDir();
   }

   public static boolean isModLoaded(String modId) {
      return Services.PLATFORM.isModLoaded(modId);
   }

   public static boolean isDevelopmentEnvironment() {
      return Services.PLATFORM.isDevelopmentEnvironment();
   }

   public static boolean isClientDist() {
      return Services.PLATFORM.isClientDist();
   }

   public static boolean isRunningDatagen() {
      return Services.PLATFORM.isRunningDataGen();
   }

   public static String getEnvironmentName() {
      return Services.PLATFORM.getEnvironmentName();
   }
}

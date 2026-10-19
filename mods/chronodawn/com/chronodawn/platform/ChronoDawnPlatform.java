package com.chronodawn.platform;

import com.chronodawn.platform.neoforge.ChronoDawnPlatformImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;

public class ChronoDawnPlatform {
   @ExpectPlatform
   @Transformed
   public static Path getConfigDirectory() {
      return ChronoDawnPlatformImpl.getConfigDirectory();
   }

   @ExpectPlatform
   @Transformed
   public static boolean isDevelopmentEnvironment() {
      return ChronoDawnPlatformImpl.isDevelopmentEnvironment();
   }

   @ExpectPlatform
   @Transformed
   public static String getPlatformName() {
      return ChronoDawnPlatformImpl.getPlatformName();
   }
}

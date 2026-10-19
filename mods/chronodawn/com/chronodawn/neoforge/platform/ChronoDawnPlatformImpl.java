package com.chronodawn.neoforge.platform;

import java.nio.file.Path;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

public class ChronoDawnPlatformImpl {
   public static Path getConfigDirectory() {
      return FMLPaths.CONFIGDIR.get();
   }

   public static boolean isDevelopmentEnvironment() {
      return !FMLLoader.isProduction();
   }

   public static String getPlatformName() {
      return "NeoForge";
   }
}

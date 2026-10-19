package net.regions_unexplored.module.platform;

import java.nio.file.Path;
import net.neoforged.fml.loading.FMLPaths;

public class ConfigHelper {
   public static Path getConfigDirectory() {
      return FMLPaths.CONFIGDIR.get().resolve("regions_unexplored");
   }
}

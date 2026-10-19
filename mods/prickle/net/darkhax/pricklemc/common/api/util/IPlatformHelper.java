package net.darkhax.pricklemc.common.api.util;

import java.io.File;
import java.nio.file.Path;

public interface IPlatformHelper {
   Path getConfigPath();

   default File getConfigDirectory() {
      return this.getConfigPath().toFile();
   }

   String getName();
}

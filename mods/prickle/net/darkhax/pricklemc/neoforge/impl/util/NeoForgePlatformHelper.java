package net.darkhax.pricklemc.neoforge.impl.util;

import java.nio.file.Path;
import net.darkhax.pricklemc.common.api.util.IPlatformHelper;
import net.neoforged.fml.loading.FMLPaths;

public class NeoForgePlatformHelper implements IPlatformHelper {
   @Override
   public Path getConfigPath() {
      return FMLPaths.CONFIGDIR.get();
   }

   @Override
   public String getName() {
      return "NeoForge";
   }
}

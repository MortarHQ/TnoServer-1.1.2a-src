package com.chronodawn.worldgen.runtime.neoforge;

import java.nio.file.Path;
import net.neoforged.fml.loading.FMLPaths;

public final class OverlayPackPlatformImpl {
   private OverlayPackPlatformImpl() {
   }

   public static Path getConfigDirectory() {
      return FMLPaths.CONFIGDIR.get();
   }
}

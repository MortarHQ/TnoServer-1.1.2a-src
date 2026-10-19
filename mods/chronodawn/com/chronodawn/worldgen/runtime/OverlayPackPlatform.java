package com.chronodawn.worldgen.runtime;

import com.chronodawn.worldgen.runtime.neoforge.OverlayPackPlatformImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;

public final class OverlayPackPlatform {
   private OverlayPackPlatform() {
   }

   @ExpectPlatform
   @Transformed
   public static Path getConfigDirectory() {
      return OverlayPackPlatformImpl.getConfigDirectory();
   }
}

package cc.abbie.emi_ores;

import cc.abbie.emi_ores.neoforge.PlatformImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;

public class Platform {
   @ExpectPlatform
   @Transformed
   public static Path getConfigDir() {
      return PlatformImpl.getConfigDir();
   }
}

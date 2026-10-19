package cc.abbie.emi_ores.neoforge;

import java.nio.file.Path;
import net.neoforged.fml.loading.FMLPaths;

public class PlatformImpl {
   public static Path getConfigDir() {
      return FMLPaths.CONFIGDIR.get();
   }
}

package de.cristelknight.cristellib;

import de.cristelknight.cristellib.neoforge.ModLoadingUtilImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Optional;

public class ModLoadingUtil {
   @ExpectPlatform
   @Transformed
   public static boolean isModLoaded(String modID) {
      return ModLoadingUtilImpl.isModLoaded(modID);
   }

   @ExpectPlatform
   @Transformed
   public static Optional<Integer> compare(String modID, String version) {
      return ModLoadingUtilImpl.compare(modID, version);
   }
}

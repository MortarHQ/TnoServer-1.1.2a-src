package io.github.Memoires.mysticism.bridge;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import io.github.Memoires.mysticism.bridge.neoforge.GardenerFoodAPIImpl;

public class GardenerFoodAPI {
   @ExpectPlatform
   @Transformed
   public static void register() {
      GardenerFoodAPIImpl.register();
   }
}

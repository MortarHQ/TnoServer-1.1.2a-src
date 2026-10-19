package noobanidus.mods.lootr.common.integration.create;

import org.jetbrains.annotations.ApiStatus.Internal;

public class CreateIntegration {
   @Internal
   public static boolean SKIP_ITEM_FRAMES = false;

   public static boolean shouldSkipItems() {
      return SKIP_ITEM_FRAMES;
   }
}

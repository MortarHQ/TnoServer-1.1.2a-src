package fuzs.puzzleslib.api.core.v1;

import org.jetbrains.annotations.ApiStatus.Internal;

public enum ContentRegistrationFlags {
   BIOME_MODIFICATIONS,
   DYNAMIC_RENDERERS,
   COPY_RECIPES;

   @Internal
   public static void throwForFlag(ContentRegistrationFlags flag) {
      throw new IllegalStateException("%s#%s is missing".formatted(ContentRegistrationFlags.class.getSimpleName(), flag));
   }
}

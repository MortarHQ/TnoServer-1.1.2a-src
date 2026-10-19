package fuzs.puzzleslib.api.core.v1.utility;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import fuzs.puzzleslib.api.core.v1.ModLoader;
import java.util.Arrays;
import java.util.EnumSet;

public interface EnvironmentAwareBuilder<T> {
   default T whenOnFabricLike() {
      return this.whenOn(ModLoader.getFabricLike());
   }

   default T whenOnForgeLike() {
      return this.whenOn(ModLoader.getForgeLike());
   }

   default T whenNotOn(ModLoader... forbiddenModLoaders) {
      Preconditions.checkState(forbiddenModLoaders.length > 0, "mod loaders is empty");
      return this.whenOn(EnumSet.complementOf(Sets.newEnumSet(Arrays.asList(forbiddenModLoaders), ModLoader.class)).toArray(ModLoader[]::new));
   }

   T whenOn(ModLoader... var1);
}

package fuzs.puzzleslib.api.core.v1;

import fuzs.puzzleslib.impl.core.ModContext;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public interface ModLoaderEnvironment {
   ModLoaderEnvironment INSTANCE = ServiceProviderHelper.load(ModLoaderEnvironment.class);

   ModLoader getModLoader();

   boolean isClient();

   boolean isServer();

   Path getGameDirectory();

   Path getModsDirectory();

   Path getConfigDirectory();

   String getCurrentMappingsNamespace();

   boolean isDevelopmentEnvironment();

   boolean isDataGeneration();

   Map<String, ModContainer> getModList();

   default boolean isModLoaded(String modId) {
      return this.getModList().containsKey(modId);
   }

   default Optional<ModContainer> getModContainer(String modId) {
      return Optional.ofNullable(this.getModList().get(modId));
   }

   default boolean isModPresentServerside(String modId) {
      return ModContext.isPresentServerside(modId);
   }
}

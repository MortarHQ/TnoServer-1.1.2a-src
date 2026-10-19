package net.pixeldreamstudios.mythika.platform;

import java.util.ServiceLoader;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.platform.services.IPlatformHelper;
import net.pixeldreamstudios.mythika.platform.services.IRegistryHelper;

public class Services {
   public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
   public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

   public static <T> T load(Class<T> clazz) {
      T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
      Mythika.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
      return loadedService;
   }
}

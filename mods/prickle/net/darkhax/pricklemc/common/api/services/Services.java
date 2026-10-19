package net.darkhax.pricklemc.common.api.services;

import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;
import net.darkhax.pricklemc.common.api.util.IPlatformHelper;
import net.darkhax.pricklemc.common.impl.Constants;

public class Services {
   public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

   public static <T> T load(Class<T> clazz) {
      T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
      Constants.LOG.debug("Loaded {} for service {}.", loadedService, clazz);
      return loadedService;
   }

   public static <T> List<T> loadMany(Class<T> clazz) {
      List<T> entries = ServiceLoader.load(clazz).stream().map(Provider::get).toList();
      Constants.LOG
         .debug(
            "Loaded {} entries for {}. {}",
            new Object[]{entries.size(), clazz, entries.stream().map(entry -> entry.getClass().getCanonicalName()).collect(Collectors.joining())}
         );
      return entries;
   }
}

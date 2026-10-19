package com.teamresourceful.resourcefullib.common.utils.files;

import com.teamresourceful.resourcefullib.common.lib.Constants;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.Optionull;
import org.jetbrains.annotations.ApiStatus.Internal;

public class GlobalStorage {
   private static final String ID = "resourcefullib";
   private static final String README = "This is created by ResourcefulLib for usage by mods.\nThis directory is used to store data/caches for mods.\n";
   private static boolean initialized = false;
   private static final Path cache;
   private static final Path data;

   @Internal
   public static void init() {
      if (!initialized) {
         initialized = true;

         try {
            Files.createDirectories(cache);
            Files.createDirectories(data);
            Path readme = cache.resolve("README.txt");
            if (!Files.exists(readme)) {
               Files.writeString(readme, "This is created by ResourcefulLib for usage by mods.\nThis directory is used to store data/caches for mods.\n");
            }

            readme = data.resolve("README.txt");
            if (!Files.exists(readme)) {
               Files.writeString(readme, "This is created by ResourcefulLib for usage by mods.\nThis directory is used to store data/caches for mods.\n");
            }
         } catch (Exception e) {
            Constants.LOGGER.warn("Failed to create cache/data directories!", e);
         }
      }
   }

   public static Path getCacheDirectory(String modid) {
      init();
      return cache.resolve(modid);
   }

   public static Path getDataDirectory(String modid) {
      init();
      return data.resolve(modid);
   }

   static {
      String osName = System.getProperty("os.name");
      if (osName.startsWith("Windows")) {
         cache = Path.of(System.getenv("LOCALAPPDATA"), ".resourcefullib", "cache");
         data = Path.of(System.getenv("LOCALAPPDATA"), ".resourcefullib", "data");
      } else if (!osName.startsWith("Mac OS X") && !osName.startsWith("Darwin")) {
         cache = (Path)Optionull.mapOrElse(
            System.getenv("XDG_CACHE_HOME"),
            home -> Path.of(home, "resourcefullib"),
            () -> Path.of(System.getProperty("user.home"), ".cache", "resourcefullib")
         );
         data = (Path)Optionull.mapOrElse(
            System.getenv("XDG_DATA_HOME"),
            home -> Path.of(home, "resourcefullib"),
            () -> Path.of(System.getProperty("user.home"), ".local", "share", "resourcefullib")
         );
      } else {
         cache = Path.of(System.getProperty("user.home"), "Library", "Caches", "resourcefullib");
         data = Path.of(System.getProperty("user.home"), "Library", "Application Support", "resourcefullib");
      }
   }
}

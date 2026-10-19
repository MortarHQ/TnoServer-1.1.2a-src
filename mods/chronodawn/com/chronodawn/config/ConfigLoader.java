package com.chronodawn.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.CommentedConfig.Entry;
import com.electronwill.nightconfig.toml.TomlParser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigLoader {
   private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
   private static final String CONFIG_FILE_NAME = "chronodawn.toml";
   private static final String BUNDLED_DEFAULT_RESOURCE = "/chronodawn-default-config.toml";
   private static final int MIN_SPACING = 1;
   private static final int MAX_SPACING = 4096;
   private static final int MIN_SEPARATION = 0;
   private static final String K_SCHEMA_VERSION = "schema_version";
   private static final String K_WORLD = "world";
   private static final String K_STRUCTURES = "structures";
   private static final String K_ANCIENT_RUINS = "ancient_ruins";
   private static final String K_AR_ENABLED = "enabled";
   private static final String K_AR_SPACING = "spacing";
   private static final String K_AR_SEPARATION = "separation";
   private static final String K_AR_SALT = "salt";
   private static final String K_ORES = "ores";
   private static final String K_TIME_CRYSTAL = "time_crystal";
   private static final String K_ENTROPY_CRYSTAL = "entropy_crystal";
   private static final String K_TEMPORAL_AMBER = "temporal_amber";
   private static final String K_CLOCKSTONE = "clockstone";
   private static final String K_ORE_ENABLED = "enabled";
   private static final String K_ORE_COUNT = "count";
   private static final String K_ORE_Y_MIN = "y_min";
   private static final String K_ORE_Y_MAX = "y_max";
   private static final int MIN_ORE_COUNT = 0;
   private static final int MAX_ORE_COUNT = 64;
   private static final int MIN_ORE_Y = -64;
   private static final int MAX_ORE_Y = 320;

   private ConfigLoader() {
   }

   public static ChronoDawnConfig load(Path configDir) {
      Path configFile = configDir.resolve("chronodawn.toml");

      try {
         ensureFileExists(configFile);
      } catch (IOException e) {
         LOGGER.error("Failed to materialise default config at {}; using built-in defaults", configFile, e);
         ChronoDawnConfig defaults = ConfigDefaults.defaults();
         ChronoDawnConfig.set(defaults);
         return defaults;
      }

      ChronoDawnConfig config = parseOrDefaults(configFile);
      ChronoDawnConfig.set(config);
      return config;
   }

   private static void ensureFileExists(Path configFile) throws IOException {
      if (!Files.exists(configFile)) {
         Files.createDirectories(configFile.getParent());

         try (InputStream in = ConfigLoader.class.getResourceAsStream("/chronodawn-default-config.toml")) {
            if (in == null) {
               throw new IOException("Bundled default config resource not found: /chronodawn-default-config.toml");
            }

            Files.copy(in, configFile, StandardCopyOption.REPLACE_EXISTING);
         }

         LOGGER.info("Wrote default config to {}", configFile);
      }
   }

   private static ChronoDawnConfig parseOrDefaults(Path configFile) {
      CommentedConfig parsed;
      try (InputStream in = Files.newInputStream(configFile)) {
         parsed = (CommentedConfig)new TomlParser().parse(in);
      } catch (IOException e) {
         LOGGER.error("Failed to read {}; using built-in defaults", configFile, e);
         return ConfigDefaults.defaults();
      } catch (RuntimeException e) {
         LOGGER.error("Failed to parse {} (is the TOML well-formed?); using built-in defaults", configFile, e);
         return ConfigDefaults.defaults();
      }

      int schemaVersion = parsed.getOptional("schema_version").map(Number::intValue).orElse(1);
      if (schemaVersion > 1) {
         LOGGER.warn("{} declares schema_version={} but this build only knows up to {}; reading what we can", new Object[]{"chronodawn.toml", schemaVersion, 1});
      }

      ChronoDawnConfig.AncientRuins ancientRuins = parseAncientRuins(parsed);
      OresConfig ores = parseOres(parsed);

      for (Entry entry : parsed.entrySet()) {
         String key = entry.getKey();
         if (!key.equals("schema_version") && !key.equals("world")) {
            LOGGER.warn("Unknown top-level key in {}: {}", "chronodawn.toml", key);
         }
      }

      return new ChronoDawnConfig(schemaVersion, new ChronoDawnConfig.World(new ChronoDawnConfig.Structures(ancientRuins), ores));
   }

   private static ChronoDawnConfig.AncientRuins parseAncientRuins(CommentedConfig parsed) {
      String path = "world.structures.ancient_ruins";
      boolean enabled = parsed.getOptional(path + ".enabled").orElse(true);
      int spacing = parsed.getOptional(path + ".spacing").map(Number::intValue).orElse(56);
      int separation = parsed.getOptional(path + ".separation").map(Number::intValue).orElse(20);
      int salt = parsed.getOptional(path + ".salt").map(Number::intValue).orElse(20005897);
      if (spacing < 1 || spacing > 4096) {
         LOGGER.error("Invalid {}.{} = {} (must be in [{}, {}]); using default {}", new Object[]{path, "spacing", spacing, 1, 4096, 56});
         spacing = 56;
      }

      if (separation < 0 || separation >= spacing) {
         LOGGER.error("Invalid {}.{} = {} (must be in [{}, spacing={})); using default {}", new Object[]{path, "separation", separation, 0, spacing, 20});
         separation = 20;
         if (separation >= spacing) {
            separation = Math.max(0, spacing - 1);
         }
      }

      return new ChronoDawnConfig.AncientRuins(enabled, spacing, separation, salt);
   }

   private static OresConfig parseOres(CommentedConfig parsed) {
      return new OresConfig(
         parseOre(parsed, "time_crystal", ConfigDefaults.TIME_CRYSTAL_DEFAULTS),
         parseOre(parsed, "entropy_crystal", ConfigDefaults.ENTROPY_CRYSTAL_DEFAULTS),
         parseOre(parsed, "temporal_amber", ConfigDefaults.TEMPORAL_AMBER_DEFAULTS),
         parseOre(parsed, "clockstone", ConfigDefaults.CLOCKSTONE_DEFAULTS)
      );
   }

   private static OreSettings parseOre(CommentedConfig parsed, String oreKey, OreSettings defaults) {
      String path = "world.ores." + oreKey;
      boolean enabled = parsed.getOptional(path + ".enabled").orElse(defaults.enabled());
      int count = parsed.getOptional(path + ".count").map(Number::intValue).orElse(defaults.count());
      int yMin = parsed.getOptional(path + ".y_min").map(Number::intValue).orElse(defaults.yMin());
      int yMax = parsed.getOptional(path + ".y_max").map(Number::intValue).orElse(defaults.yMax());
      if (count < 0 || count > 64) {
         LOGGER.error("Invalid {}.{} = {} (must be in [{}, {}]); using default {}", new Object[]{path, "count", count, 0, 64, defaults.count()});
         count = defaults.count();
      }

      if (yMin < -64 || yMax > 320 || yMin > yMax) {
         LOGGER.error(
            "Invalid {}.{{y_min,y_max}} = ({}, {}) (must satisfy {} <= y_min <= y_max <= {}); using defaults ({}, {})",
            new Object[]{path, yMin, yMax, -64, 320, defaults.yMin(), defaults.yMax()}
         );
         yMin = defaults.yMin();
         yMax = defaults.yMax();
      }

      return new OreSettings(enabled, count, yMin, yMax);
   }
}

package com.sennecools.tablist.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sennecools.tablist.Constants;
import com.sennecools.tablist.platform.Services;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class TabListConfig {
   public static String serverName;
   public static List<String> headerFrames;
   public static List<String> footerFrames;
   public static int updateInterval;
   public static int animationInterval;
   public static String displayNameFormat;
   public static NameFormattingProvider nameFormattingProvider;
   public static String sortMode;
   public static boolean afkEnabled;
   public static int afkTimeout;
   public static boolean useSparkMetrics;

   public static void load() {
      load(Services.PLATFORM.getConfigDir().resolve("tablist.toml"));
   }

   static void load(Path configPath) {
      CommentedFileConfig config = (CommentedFileConfig)CommentedFileConfig.builder(configPath).autosave().preserveInsertionOrder().build();

      try {
         config.load();
         boolean needsSave = false;
         needsSave |= setDefaultIfMissing(config, "appearance.server_name", "Your Server", "Your server's name. Use #SERVERNAME in header/footer to insert it.");
         needsSave |= setDefaultIfMissing(
            config,
            "appearance.header",
            List.of("#N        &#FF5555&l#SERVERNAME        #N&#AAAAAA&m            #N", "#N        &#5555FF&l#SERVERNAME        #N&#AAAAAA&m            #N"),
            "Text shown above the player list. Multiple entries create animation frames."
         );
         needsSave |= setDefaultIfMissing(
            config,
            "appearance.footer",
            List.of(
               "&fOnline: &e#PLAYERCOUNT&7/&e#MAXPLAYERS #N&7TPS: #CTPS &7| MSPT: &#55FFFF#MSPT #N&7Memory: &#AA55FF#MEMORY &7| Uptime: &#FFAA00#UPTIME",
               "&fOnline: &e#PLAYERCOUNT&7/&e#MAXPLAYERS #N&7TPS: #CTPS &7| Ping: &#55FFFF#PING&7ms #N&7Memory: &#AA55FF#MEMORY &7| Uptime: &#FFAA00#UPTIME"
            ),
            "Text shown below the player list. Multiple entries create animation frames."
         );
         needsSave |= setDefaultIfMissing(
            config,
            "appearance.display_name_format",
            "{name} &7#AFK",
            "Display name format. Supports {name}, {rank}, {prefix}, {suffix}, {primary_group}, {dimension}, {health}, {world}, {ping}, {gamemode}, #AFK + & color codes."
         );
         needsSave |= setDefaultIfMissing(config, "appearance.update_interval", 500, "How often (ms) the tab list refreshes. Range: 1-10000. Default: 500.");
         needsSave |= setDefaultIfMissing(
            config, "appearance.animation_interval", 4, "Update cycles between animation frame changes. Range: 1-200. Default: 4."
         );
         needsSave |= setDefaultIfMissing(config, "sorting.sort_mode", "NONE", "How to sort players: NONE, ALPHABETICAL, or RANK.");
         String defaultProvider = config.getOrElse("ftbranks.enable_ftbranks_formatting", true) ? "FTB" : "NONE";
         needsSave |= setDefaultIfMissing(
            config,
            "appearance.name_formatting_provider",
            defaultProvider,
            "Name formatting and rank sorting provider: NONE, FTB, or LP (LuckPerms). Optional integrations must be installed separately."
         );
         needsSave |= setDefaultIfMissing(config, "afk.afk_enabled", true, "Enable AFK detection. AFK players have greyed-out names.");
         needsSave |= setDefaultIfMissing(config, "afk.afk_timeout", 300, "Seconds of inactivity before AFK. Range: 10-3600. Default: 300.");
         needsSave |= setDefaultIfMissing(
            config,
            "performance.metrics_provider",
            "VANILLA",
            "Metrics provider: VANILLA or SPARK. SPARK falls back to vanilla when unavailable. Rolling TPS and MSPT percentiles require spark."
         );
         if (needsSave) {
            config.save();
         }

         serverName = (String)config.getOrElse("appearance.server_name", "Your Server");
         headerFrames = List.copyOf((Collection<? extends String>)config.getOrElse("appearance.header", List.of("")));
         footerFrames = List.copyOf((Collection<? extends String>)config.getOrElse("appearance.footer", List.of("")));
         updateInterval = clamp((Integer)config.getOrElse("appearance.update_interval", 500), 1, 10000);
         animationInterval = clamp((Integer)config.getOrElse("appearance.animation_interval", 4), 1, 200);
         displayNameFormat = (String)config.getOrElse("appearance.display_name_format", "{name} &7#AFK");
         String provider = (String)config.getOrElse("appearance.name_formatting_provider", defaultProvider);
         nameFormattingProvider = NameFormattingProvider.fromString(provider);
         if (provider == null || !provider.trim().equalsIgnoreCase(nameFormattingProvider.name())) {
            Constants.LOGGER.warn("Unknown name_formatting_provider '{}'. Falling back to NONE.", provider);
         }

         sortMode = (String)config.getOrElse("sorting.sort_mode", "NONE");
         afkEnabled = (Boolean)config.getOrElse("afk.afk_enabled", true);
         afkTimeout = clamp((Integer)config.getOrElse("afk.afk_timeout", 300), 10, 3600);
         String metricsProvider = (String)config.getOrElse("performance.metrics_provider", "VANILLA");
         useSparkMetrics = "SPARK".equalsIgnoreCase(metricsProvider.trim());
         if (!useSparkMetrics && !"VANILLA".equalsIgnoreCase(metricsProvider.trim())) {
            Constants.LOGGER.warn("Unknown metrics_provider '{}'. Falling back to VANILLA.", metricsProvider);
         }

         Constants.LOGGER.info("TabList config loaded. Update interval: {} ms", updateInterval);
      } catch (Throwable var7) {
         if (config != null) {
            try {
               config.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }
         }

         throw var7;
      }

      if (config != null) {
         config.close();
      }
   }

   private static <T> boolean setDefaultIfMissing(CommentedConfig config, String path, T defaultValue, String comment) {
      if (!config.contains(path)) {
         config.set(path, defaultValue);
         config.setComment(path, comment);
         return true;
      } else {
         return false;
      }
   }

   private static int clamp(int value, int min, int max) {
      return Math.max(min, Math.min(max, value));
   }
}

package de.melanx.simplebackups.config;

import de.melanx.simplebackups.StorageSize;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class CommonConfig {
   private static final String JVM_PROP_DISABLE_BACKUPS = "simplebackups.disableBackups";
   public static final ModConfigSpec CONFIG = CommonConfig.BUILDER.build();
   private static final Builder BUILDER = new Builder();
   private static final String DEFAULT_DISK_SIZE = "25 GB";
   private static final String EXPERIMENTAL_NOTE = "Experimental setting available, look at 'simplebackups-common-experimental.toml' to help testing";
   private static BooleanValue enabled;
   private static EnumValue<BackupType> backupType;
   private static BooleanValue saveAll;
   private static BooleanValue captureLatestLog;
   private static IntValue fullBackupTimer;
   private static IntValue backupsToKeep;
   private static IntValue timer;
   private static IntValue compressionLevel;
   private static BooleanValue sendMessages;
   private static ConfigValue<String> maxDiskSize;
   private static ConfigValue<String> outputPath;
   private static BooleanValue noPlayerBackups;
   private static IntValue noPlayerBackupCount;
   private static IntValue noPlayerBackupTimer;
   private static BooleanValue createSubDirs;
   private static BooleanValue useTickCounter;
   private static BooleanValue collectErrors;
   private static BooleanValue deleteUnfinishedBackup;
   private static ConfigValue<List<? extends String>> ignoredPaths;
   private static ConfigValue<List<? extends String>> ignoredFiles;
   private static ConfigValue<String> ignoredFilesRegex;
   private static BooleanValue mc2discord;
   private static BooleanValue onlyFavorites;

   public static void init(Builder builder) {
      enabled = builder.comment("If set false, no backups are being made.").define("enabled", true);
      backupType = builder.comment(
            new String[]{
               "Experimental setting available, look at 'simplebackups-common-experimental.toml' to help testing",
               "Defines the backup type.",
               "- FULL_BACKUPS - always creates full backups",
               "- MODIFIED_SINCE_LAST - only saves the files which changed since last (partial) backup !!! You need to adjust fullBackupTimer properly, otherwise you may lose a lot of your backup (or use the experimental setting) !!!",
               "- MODIFIED_SINCE_FULL - saves all files which changed after the last full backup"
            }
         )
         .defineEnum("backupType", BackupType.FULL_BACKUPS);
      saveAll = builder.comment("Should a save-all be forced before backup?").define("saveAll", true);
      captureLatestLog = builder.comment("Should log lines written to logs/latest.log during backup be stored inside the backup archive as latest.log?")
         .define("captureLatestLog", true);
      fullBackupTimer = builder.comment(
            "How often should a full backup be created if only modified files should be saved? This creates a full backup when x minutes are over and the next backup needs to be done. Once a year is default."
         )
         .defineInRange("fullBackupTimer", 525960, 1, 5259600);
      backupsToKeep = builder.comment(
            new String[]{
               "Experimental setting available, look at 'simplebackups-common-experimental.toml' to help testing", "The max amount of backup files to keep."
            }
         )
         .defineInRange("backupsToKeep", 10, 1, 32767);
      timer = builder.comment(new String[]{"The time between two backups in minutes", "5 = each 5 minutes", "60 = each hour", "1440 = each day"})
         .defineInRange("timer", 120, 1, 32767);
      compressionLevel = builder.comment(
            new String[]{
               "Compression level:",
               "  0  = no compression (low CPU usage, larger files)",
               "  9  = maximum compression (high CPU usage, smaller files)",
               "  -1 = default; balances speed and compression (recommended)"
            }
         )
         .defineInRange("compressionLevel", -1, Math.min(-1, 0), 9);
      sendMessages = builder.comment("Should message be sent when backup is in the making?").define("sendMessages", true);
      maxDiskSize = builder.comment(
            new String[]{
               "The max size of storage the backup folder. If it takes more storage, old files will be deleted.",
               "Needs to be written as <number><space><storage type>",
               "Valid storage types: B, KB, MB, GB, TB"
            }
         )
         .define("maxDiskSize", "25 GB");
      outputPath = builder.comment("Used to define the output path.").define("outputPath", "simplebackups");
      noPlayerBackups = builder.comment("Create backups, even if nobody is online").define("noPlayerBackups", false);
      noPlayerBackupCount = builder.comment("How many backups should be created after the last player left? Set to 0 to disable.")
         .defineInRange("noPlayerBackupCount", 1, 0, Integer.MAX_VALUE);
      noPlayerBackupTimer = builder.comment("The time between two backups when no player is online. 0 means the same as the \"timer\" setting.")
         .defineInRange("noPlayerBackupTimer", 0, 0, Integer.MAX_VALUE);
      createSubDirs = builder.comment(
            new String[]{
               "Should sub-directories be generated for each world?",
               "Keep in mind that all configs above, including backupsToKeep and maxDiskSize, will be calculated for each sub directory."
            }
         )
         .define("createSubDirs", true);
      useTickCounter = builder.comment(
            new String[]{
               "Use an internal tick counter instead of the real world time. The value of the timer will be converted to ticks. When the timer is over, the backup will be created.",
               "Keep in mind that lagging servers will result in larger gaps between two backups, e.g. 10 FPS in average will result in double the time set between backups."
            }
         )
         .define("useTickCounter", false);
      builder.comment("WARNING This configuration should stay as default if backups are not monitored properly.").push("error_handling");
      collectErrors = builder.comment(
            "I/O Errors will be collected and print into the chat. The mod tries to complete the backup with as many files as possible. Otherwise, it will abort after the first error."
         )
         .define("collectErrors", true);
      deleteUnfinishedBackup = builder.comment(
            "If collectErrors is 'false', backups with errors will be deleted immediately to prevent deleting valid backups."
         )
         .define("deleteUnfinishedBackup", true);
      builder.pop();
      builder.comment(new String[]{"WARNING Please check your configuration before using permanently.", "The backup system will ignore these paths and files."})
         .push("to_ignore");
      ignoredPaths = builder.comment(
            new String[]{
               "All directories that should be excluded from backups",
               "Format: Enter paths relative to the world directory (e.g., 'logs', 'data/cache')",
               "All files within these directories will also be excluded"
            }
         )
         .defineList("ignored_paths", List.of(), () -> "", obj -> obj instanceof String);
      ignoredFiles = builder.comment(
            new String[]{
               "Specific files that should be excluded from backups",
               "Format: Enter complete file paths relative to the world directory (e.g., 'level.dat_old', 'stats/player.json')",
               "Use this for individual files rather than entire directories"
            }
         )
         .defineList("ignored_files", List.of(), () -> "", obj -> obj instanceof String);
      ignoredFilesRegex = builder.comment(
            new String[]{
               "Regular expression pattern to exclude matching files from backups",
               "All files with paths matching this pattern will be skipped",
               "Example: '.*\\.temp$' excludes all files ending with .temp",
               "Leave empty to disable regex-based file exclusion"
            }
         )
         .define("ignored_files_regex", "");
      builder.pop();
      builder.push("mod_compat");
      mc2discord = builder.comment("Should backup notifications be sent to Discord by using mc2discord? (needs to be installed)").define("mc2discord", true);
      onlyFavorites = builder.comment("Should only worlds be backed up that are marked as favorite by Cherished Worlds mod?").define("onlyFavorites", false);
      builder.pop();
   }

   public static boolean isEnabled() {
      return (Boolean)enabled.get();
   }

   public static boolean backupsDisabledByJvmArg() {
      return parseBoolean(System.getProperty("simplebackups.disableBackups"), false);
   }

   public static int getBackupsToKeep() {
      return ExperimentalConfig.isEnabled() ? ExperimentalConfig.backupChainsToKeep() : (Integer)backupsToKeep.get();
   }

   @Deprecated
   public static long getTimer() {
      return getTimer(true);
   }

   public static long getTimer(boolean arePlayersOnline) {
      int i = (Integer)noPlayerBackupTimer.get();
      if (i == 0) {
         i = (Integer)timer.get();
      }

      return (arePlayersOnline ? (Integer)timer.get() : i) * 60L * 1000L;
   }

   public static long getFullBackupTimer() {
      return ((Integer)fullBackupTimer.get()).intValue() * 60L * 1000L;
   }

   public static int getCompressionLevel() {
      return (Integer)compressionLevel.get();
   }

   public static long getMaxDiskSize() {
      String s = (String)maxDiskSize.get();
      if (s.split(" ").length != 2) {
         s = "25 GB";
      }

      return StorageSize.getBytes(s);
   }

   public static Path getOutputPath(@Nullable String levelId) {
      Path base = Paths.get((String)outputPath.get());
      boolean withSubDir = levelId != null && !levelId.isEmpty() && (Boolean)createSubDirs.get();

      try {
         return withSubDir ? base.toRealPath().resolve(levelId) : base.toRealPath();
      } catch (IOException e) {
         return withSubDir ? base.resolve(levelId) : base;
      }
   }

   public static boolean doNoPlayerBackups() {
      return (Boolean)noPlayerBackups.get();
   }

   public static int noPlayerBackupCount() {
      return (Integer)noPlayerBackupCount.get();
   }

   public static BackupType backupType() {
      return ExperimentalConfig.isEnabled() ? ExperimentalConfig.backupType().asLegacyType() : (BackupType)backupType.get();
   }

   public static boolean saveAll() {
      return (Boolean)saveAll.get();
   }

   public static boolean captureLatestLog() {
      return (Boolean)captureLatestLog.get();
   }

   public static boolean sendMessages() {
      return (Boolean)sendMessages.get();
   }

   public static boolean collectErrors() {
      return (Boolean)collectErrors.get();
   }

   public static boolean deleteUnfinishedBackup() {
      return (Boolean)deleteUnfinishedBackup.get();
   }

   public static List<Path> getIgnoredPaths() {
      List<Path> paths = new ArrayList<>();

      for (String path : (List)ignoredPaths.get()) {
         paths.add(Path.of(path));
      }

      return paths;
   }

   public static List<Path> getIgnoredFiles() {
      List<Path> paths = new ArrayList<>();

      for (String path : (List)ignoredFiles.get()) {
         paths.add(Path.of(path));
      }

      return paths;
   }

   public static String getIgnoredFilesRegex() {
      return (String)ignoredFilesRegex.get();
   }

   public static boolean useTickCounter() {
      return (Boolean)useTickCounter.get();
   }

   public static boolean mc2discord() {
      return (Boolean)mc2discord.get();
   }

   public static boolean onlyFavorites() {
      return (Boolean)onlyFavorites.get();
   }

   private static boolean parseBoolean(@Nullable String value, boolean defaultValue) {
      if (value == null) {
         return defaultValue;
      }

      return switch (value.trim().toLowerCase(Locale.ROOT)) {
         case "true", "1", "yes", "y", "on" -> true;
         case "false", "0", "no", "n", "off" -> false;
         default -> defaultValue;
      };
   }

   static {
      init(BUILDER);
   }
}

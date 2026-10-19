package de.melanx.simplebackups.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ExperimentalConfig {
   public static final ModConfigSpec CONFIG = ExperimentalConfig.BUILDER.build();
   private static final Builder BUILDER = new Builder();
   private static BooleanValue enabled;
   private static EnumValue<ExperimentalConfig.ExperimentalBackupType> backupType;
   private static IntValue backupChainsToKeep;

   public static void init(Builder builder) {
      enabled = builder.comment("Only enable if you want to test the experimental backup system.").define("enabled", false);
      backupType = builder.comment(
            new String[]{
               "Defines the backup type.",
               "- FULL_BACKUPS - every backup is a full backup.",
               "- INCREMENTAL  - backup only files changed since the last backup",
               "- DIFFERENTIAL - backup only files changed since the last full backup"
            }
         )
         .defineEnum("backupType", ExperimentalConfig.ExperimentalBackupType.INCREMENTAL);
      backupChainsToKeep = builder.comment("The max amount of backup chains to keep.").defineInRange("backupChainsToKeep", 10, 1, 32767);
   }

   public static boolean isEnabled() {
      return (Boolean)enabled.get();
   }

   public static ExperimentalConfig.ExperimentalBackupType backupType() {
      return (ExperimentalConfig.ExperimentalBackupType)backupType.get();
   }

   public static int backupChainsToKeep() {
      return (Integer)backupChainsToKeep.get();
   }

   static {
      init(BUILDER);
   }

   public enum ExperimentalBackupType {
      FULL_BACKUPS(BackupType.FULL_BACKUPS),
      INCREMENTAL(BackupType.MODIFIED_SINCE_LAST),
      DIFFERENTIAL(BackupType.MODIFIED_SINCE_FULL);

      private final BackupType backupType;

      ExperimentalBackupType(BackupType backupType) {
         this.backupType = backupType;
      }

      public BackupType asLegacyType() {
         return this.backupType;
      }
   }
}

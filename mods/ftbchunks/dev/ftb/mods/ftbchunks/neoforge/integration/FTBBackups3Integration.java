package dev.ftb.mods.ftbchunks.neoforge.integration;

import dev.ftb.mods.ftbbackups.api.event.BackupEvent.Pre;
import dev.ftb.mods.ftbchunks.FTBChunks;
import java.nio.file.Path;
import net.neoforged.neoforge.common.NeoForge;

public class FTBBackups3Integration {
   public static final Path LOCAL_DIR = Path.of("local", "ftbchunks");

   public static void init() {
      NeoForge.EVENT_BUS.addListener(FTBBackups3Integration::addBackupPath);
   }

   private static void addBackupPath(Pre event) {
      event.add(LOCAL_DIR);
      FTBChunks.LOGGER.info("added {} to FTB Backups 3 path list", LOCAL_DIR);
   }
}

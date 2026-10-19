package de.melanx.simplebackups;

import de.melanx.simplebackups.compat.CherishedWorldsCompat;
import de.melanx.simplebackups.compat.Mc2DiscordCompat;
import de.melanx.simplebackups.config.BackupType;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.config.ExperimentalConfig;
import de.melanx.simplebackups.config.ServerConfig;
import de.melanx.simplebackups.exception.NotEnoughDiskSpaceException;
import de.melanx.simplebackups.network.Pause;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.FileUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.neoforged.fml.i18n.FMLTranslations;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupThread extends Thread {
   private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .appendLiteral('_')
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .toFormatter();
   private static final String LATEST_LOG_ENTRY = "backup.log";
   private static final int LOG_BUFFER_SIZE = 8192;
   public static final Logger LOGGER = LoggerFactory.getLogger(BackupThread.class);
   public static final long BACKUP_BUFFER_SIZE = 134217728L;
   private final MinecraftServer server;
   private final boolean quiet;
   private final long lastSaved;
   private final boolean fullBackup;
   private final LevelStorageAccess storageSource;
   private final Path backupPath;
   private final Set<Path> errors = new HashSet<>();
   private final BackupChainManager manager;
   private boolean forceFullBackup = false;

   private BackupThread(@Nonnull MinecraftServer server, boolean quiet, BackupData backupData) {
      this.server = server;
      this.storageSource = server.storageSource;
      this.quiet = quiet;
      if (backupData == null) {
         this.lastSaved = 0L;
         this.fullBackup = true;
      } else {
         long now = CommonConfig.useTickCounter() ? server.overworld().getGameTime() : System.currentTimeMillis();
         this.lastSaved = CommonConfig.backupType() == BackupType.MODIFIED_SINCE_LAST ? backupData.getLastSaved() : backupData.getLastFullBackup();
         this.fullBackup = CommonConfig.backupType() == BackupType.FULL_BACKUPS || now - CommonConfig.getFullBackupTimer() > backupData.getLastFullBackup();
      }

      this.setName("SimpleBackups");
      this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      String levelId = this.storageSource.getLevelId();
      this.backupPath = CommonConfig.getOutputPath(levelId);
      this.manager = ExperimentalConfig.isEnabled() ? BackupChainManager.get(levelId) : null;
   }

   public static boolean tryCreateBackup(MinecraftServer server) {
      BackupData backupData = BackupData.get(server);
      if (shouldRunBackup(server)) {
         BackupThread thread = new BackupThread(server, false, backupData);
         thread.start();
         long currentTime = CommonConfig.useTickCounter() ? server.overworld().getGameTime() : System.currentTimeMillis();
         backupData.updateSaveTime(currentTime);
         if (thread.createFullBackup()) {
            backupData.updateFullBackupTime(currentTime);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean shouldRunBackup(MinecraftServer server) {
      BackupData backupData = BackupData.get(server);
      if (!CommonConfig.isEnabled() || CommonConfig.backupsDisabledByJvmArg() || backupData.isPaused()) {
         return false;
      } else if (CherishedWorldsCompat.isLoaded() && !CherishedWorldsCompat.isFavorite(server.storageSource.getLevelId())) {
         return false;
      } else {
         boolean arePlayersOnline = !server.getPlayerList().getPlayers().isEmpty();
         if (CommonConfig.useTickCounter()) {
            long gameTime = server.overworld().getGameTime();
            long lastSaved = backupData.getLastSaved();
            long timer = CommonConfig.getTimer(arePlayersOnline) * 20L * 60L;
            return gameTime - lastSaved >= timer;
         } else {
            return System.currentTimeMillis() - CommonConfig.getTimer(arePlayersOnline) > backupData.getLastSaved();
         }
      }
   }

   public static void createBackup(MinecraftServer server, boolean quiet) {
      BackupThread thread = new BackupThread(server, quiet, null);
      thread.start();
   }

   public void deleteFiles() {
      if (ExperimentalConfig.isEnabled()) {
         this.deleteFilesExperimental();
      } else {
         File backups = this.backupPath.toFile();
         if (backups.isDirectory()) {
            List<File> files = new ArrayList<>(Arrays.stream(Objects.requireNonNull(backups.listFiles())).filter(File::isFile).toList());
            if (files.size() >= CommonConfig.getBackupsToKeep()) {
               files.sort(Comparator.comparingLong(File::lastModified));

               while (files.size() >= CommonConfig.getBackupsToKeep()) {
                  boolean deleted = files.getFirst().delete();
                  String name = files.getFirst().getName();
                  if (deleted) {
                     files.removeFirst();
                     LOGGER.info("Successfully deleted \"{}\"", name);
                  }
               }
            }
         }
      }
   }

   public void saveStorageSize() {
      try {
         if (ExperimentalConfig.isEnabled()) {
            this.saveStorageSizeExperimental();
            return;
         }

         while (this.getOutputFolderSize() > CommonConfig.getMaxDiskSize()) {
            File[] files = this.backupPath.toFile().listFiles();
            if (Objects.requireNonNull(files).length == 1) {
               LOGGER.error("Cannot delete old files to save disk space. Only one backup file left!");
               return;
            }

            Arrays.sort(Objects.requireNonNull(files), Comparator.comparingLong(File::lastModified));
            File file = files[0];
            String name = file.getName();
            if (file.delete()) {
               LOGGER.info("Successfully deleted \"{}\"", name);
            }
         }
      } catch (NullPointerException e) {
         LOGGER.error("Cannot delete old files to save disk space", e);
      }
   }

   private void deleteFilesExperimental() {
      if (!this.manager.getChains().isEmpty()) {
         int maxChains = CommonConfig.getBackupsToKeep();

         while (this.manager.getChains().size() > maxChains) {
            BackupChain chain = this.manager.getFirstChain();
            LOGGER.info("Deleting backup chain directory \"{}\"", chain.getParentFolder());
            this.manager.removeChain(chain);
         }
      }
   }

   private void saveStorageSizeExperimental() {
      while (this.manager.getFileSize() > CommonConfig.getMaxDiskSize()) {
         List<BackupChain> chains = this.manager.getChains();
         if (chains.size() <= 1) {
            LOGGER.error("Cannot delete old chains to save disk space. Only one chain directory left!");
            return;
         }

         BackupChain victim = chains.getFirst();
         LOGGER.info("Deleting backup chain directory \"{}\" to save disk space", victim.getParentFolder());
         this.manager.removeChain(victim);
      }
   }

   @Override
   public void run() {
      try {
         Path backupFilePath = this.getBackupFilePath();
         Path latestLogPath = this.server.getFile("logs/latest.log");
         BackupThread.LogSnapshot latestLogSnapshot = this.getLogSnapshot(latestLogPath);
         BackupThread.BackupResult backupResult = null;
         String time = null;

         try {
            this.deleteFiles();
            Files.createDirectories(this.backupPath);
            long start = System.currentTimeMillis();
            this.broadcast("simplebackups.backup_started", Style.EMPTY.withColor(ChatFormatting.GOLD));
            backupResult = this.makeWorldBackup(backupFilePath);
            long end = System.currentTimeMillis();
            time = BackupThread.Timer.getTimer(end - start);
         } catch (NotEnoughDiskSpaceException e) {
            this.broadcast("simplebackups.not_enough_space", Style.EMPTY.withColor(ChatFormatting.RED));
            Files.deleteIfExists(backupFilePath);
         } catch (IOException e) {
            if (CommonConfig.deleteUnfinishedBackup()) {
               this.broadcast("simplebackups.backup_failed_delete", Style.EMPTY.withColor(ChatFormatting.RED));
               Files.deleteIfExists(backupFilePath);
            } else {
               this.broadcast("simplebackups.backup_failed_continue", Style.EMPTY.withColor(ChatFormatting.RED));
            }

            SimpleBackups.LOGGER.error("Error backing up", e);
         }

         if (backupResult != null) {
            long backupFileSize = this.addLogToBackup(backupFilePath, latestLogPath, latestLogSnapshot, backupResult.fileSize);
            this.saveStorageSize();
            boolean hasErrors = !this.errors.isEmpty();
            this.broadcast(
               "simplebackups.backup_finished",
               Style.EMPTY.withColor(hasErrors ? ChatFormatting.YELLOW : ChatFormatting.GOLD),
               time,
               StorageSize.getFormattedSize(backupFileSize),
               StorageSize.getFormattedSize(this.getOutputFolderSize())
            );
            if (hasErrors) {
               MutableComponent erroredFiles = Component.literal(this.errors.stream().map(file -> "- " + file.toString()).collect(Collectors.joining("\n")));
               this.broadcast(
                  "simplebackups.backup_errors",
                  Style.EMPTY.withColor(ChatFormatting.RED).withHoverEvent(new HoverEvent(Action.SHOW_TEXT, erroredFiles)),
                  this.errors.size()
               );
               LOGGER.error("Skipped {} files during backup because of errors:", this.errors.size());

               for (Path failedFile : this.errors) {
                  LOGGER.error(" - {}", failedFile);
               }
            }

            long finalBackupFileSize = this.addLogToBackup(backupFilePath, latestLogPath, latestLogSnapshot, backupFileSize);
            if (finalBackupFileSize != backupFileSize) {
               this.saveStorageSize();
            }
         } else {
            this.addLogToBackup(backupFilePath, latestLogPath, latestLogSnapshot, 0L);
         }
      } catch (IOException e) {
         SimpleBackups.LOGGER.error("Error backing up", e);
      }
   }

   private Path getBackupFilePath() throws IOException {
      if (ExperimentalConfig.isEnabled()) {
         return this.getChainBackupFilePath();
      }

      String fileName = this.storageSource.getLevelId() + "_" + LocalDateTime.now().format(FORMATTER);
      Path path = this.backupPath;
      Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
      return path.resolve(FileUtil.findAvailableName(path, fileName, ".zip"));
   }

   private Path getChainBackupFilePath() throws IOException {
      Path worldBackupDir = this.backupPath;
      Files.createDirectories(Files.exists(worldBackupDir) ? worldBackupDir.toRealPath() : worldBackupDir);
      String baseName = LocalDateTime.now().format(FORMATTER);
      BackupChain latestChain = this.manager.getLatestChain();
      if (!this.fullBackup && latestChain != null) {
         return latestChain.createChild();
      }

      this.forceFullBackup = true;
      BackupChain chain = this.manager.createChain(baseName);
      return chain.getFullBackup();
   }

   private long getOutputFolderSize() {
      if (!Files.exists(this.backupPath)) {
         return 0L;
      }

      try (Stream<Path> stream = Files.walk(this.backupPath)) {
         return stream.filter(x$0 -> Files.isRegularFile(x$0)).mapToLong(path -> {
            try {
               return Files.size(path);
            } catch (IOException e) {
               LOGGER.warn("Failed to get size of {}", path, ex);
               return 0L;
            }
         }).sum();
      } catch (IOException e) {
         LOGGER.warn("Failed to get size of backup folder", e);
         return 0L;
      }
   }

   private void broadcast(String message, Style style, Object... parameters) {
      SimpleBackups.LOGGER.info(String.format(FMLTranslations.getPattern(message, () -> message), parameters));
      if (CommonConfig.sendMessages() && !this.quiet) {
         this.server.execute(() -> this.server.getPlayerList().getPlayers().forEach(player -> {
            if (ServerConfig.messagesForEveryone() || player.hasPermissions(2)) {
               player.sendSystemMessage(component(player, message, parameters).withStyle(style));
            }
         }));
         if (Mc2DiscordCompat.isLoaded() && CommonConfig.mc2discord()) {
            Mc2DiscordCompat.announce(component(null, message, parameters));
         }
      }
   }

   public static MutableComponent component(@Nullable ServerPlayer player, String key, Object... parameters) {
      return player != null && NetworkRegistry.hasChannel(player.connection.connection, null, Pause.ID)
         ? Component.translatable(key, parameters)
         : Component.literal(String.format(FMLTranslations.getPattern(key, () -> key), parameters));
   }

   private BackupThread.BackupResult makeWorldBackup(Path outputFile) throws IOException {
      this.storageSource.checkLock();
      if (CommonConfig.saveAll()) {
         this.server.executeBlocking(() -> this.server.saveEverything(true, false, true));
      }

      try (final ZipOutputStream zipStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(outputFile)))) {
         zipStream.setLevel(CommonConfig.getCompressionLevel());
         final Path levelName = Paths.get(this.storageSource.getLevelId());
         final Path levelPath = this.storageSource.getWorldDir().resolve(this.storageSource.getLevelId()).toRealPath();
         final List<Path> ignoredPaths = CommonConfig.getIgnoredPaths();
         final List<Path> ignoredFiles = CommonConfig.getIgnoredFiles();
         final String ignoredFilesRegex = CommonConfig.getIgnoredFilesRegex();
         final boolean ignoreSomething = !ignoredPaths.isEmpty() || !ignoredFiles.isEmpty() || !ignoredFilesRegex.isEmpty();
         final FileStore fileStore = Files.getFileStore(outputFile);
         Files.walkFileTree(
            levelPath,
            new SimpleFileVisitor<Path>() {
               @Nonnull
               public FileVisitResult visitFile(@Nonnull Path file, @Nonnull BasicFileAttributes attrs) throws IOException {
                  if (file.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  }

                  if (file.endsWith("biomancy.spatial.db")) {
                     SimpleBackups.LOGGER.info("Skipping \"{}\" - see https://github.com/Elenterius/Biomancy/issues/175", levelPath.relativize(file));
                     return FileVisitResult.CONTINUE;
                  }

                  if (ignoreSomething && this.shouldSkipFile(levelPath.relativize(file))) {
                     SimpleBackups.LOGGER.debug("Skipping file: {}", file);
                     return FileVisitResult.CONTINUE;
                  }

                  long lastModified = file.toFile().lastModified();
                  if (BackupThread.this.createFullBackup() || lastModified - BackupThread.this.lastSaved > 0L) {
                     if (fileStore.getUsableSpace() - attrs.size() - 134217728L < 0L) {
                        throw new NotEnoughDiskSpaceException("Not enough space on disk to create backup");
                     }

                     String completePath = levelName.resolve(levelPath.relativize(file)).toString().replace('\\', '/');
                     ZipEntry zipentry = new ZipEntry(completePath);

                     try (InputStream inputStream = Files.newInputStream(file)) {
                        zipStream.putNextEntry(zipentry);
                        inputStream.transferTo(zipStream);
                        zipStream.closeEntry();
                     } catch (IOException e) {
                        this.visitFileFailed(file, e);
                     }
                  }

                  return FileVisitResult.CONTINUE;
               }

               @Nonnull
               public FileVisitResult visitFileFailed(@Nonnull Path file, @Nonnull IOException exc) throws IOException {
                  if (exc instanceof NoSuchFileException || exc instanceof FileNotFoundException) {
                     SimpleBackups.LOGGER.debug("Skipped vanished file: {}", file);
                     return FileVisitResult.CONTINUE;
                  } else if (CommonConfig.collectErrors()) {
                     SimpleBackups.LOGGER.error("Failed to backup file: {}", file, exc);
                     BackupThread.this.errors.add(levelPath.relativize(file));
                     return FileVisitResult.CONTINUE;
                  } else {
                     IOException detailedException = new IOException("Failed to backup file: " + file, exc);
                     return super.visitFileFailed(file, detailedException);
                  }
               }

               private boolean shouldSkipFile(Path relativePath) {
                  return ignoredPaths.contains(relativePath.getParent())
                     || ignoredFiles.contains(relativePath)
                     || !ignoredFilesRegex.isEmpty() && this.getNormalizedPath(relativePath).matches(ignoredFilesRegex);
               }

               private String getNormalizedPath(Path path) {
                  return path.toString().replace('\\', '/');
               }
            }
         );
      }

      return new BackupThread.BackupResult(outputFile, Files.size(outputFile));
   }

   private BackupThread.LogSnapshot getLogSnapshot(Path latestLogPath) {
      if (!CommonConfig.captureLatestLog()) {
         return BackupThread.LogSnapshot.disabled();
      }

      try {
         if (!Files.exists(latestLogPath)) {
            return new BackupThread.LogSnapshot(0L, null);
         }

         BasicFileAttributes attributes = Files.readAttributes(latestLogPath, BasicFileAttributes.class);
         return !attributes.isRegularFile() ? BackupThread.LogSnapshot.disabled() : new BackupThread.LogSnapshot(attributes.size(), attributes.fileKey());
      } catch (IOException e) {
         LOGGER.warn("Failed to read latest.log metadata from {}", latestLogPath, e);
         return BackupThread.LogSnapshot.disabled();
      }
   }

   private long addLogToBackup(Path outputFile, Path latestLogPath, BackupThread.LogSnapshot logSnapshot, long fallbackSize) {
      if (logSnapshot.isEnabled() && Files.isRegularFile(latestLogPath) && Files.isRegularFile(outputFile)) {
         try {
            BasicFileAttributes attributes = Files.readAttributes(latestLogPath, BasicFileAttributes.class);
            if (!attributes.isRegularFile()) {
               return this.getFileSize(outputFile, fallbackSize);
            }

            long startOffset = logSnapshot.hasRolledOver(attributes) ? 0L : logSnapshot.offset();
            long bytesToCopy = attributes.size() - startOffset;
            if (bytesToCopy <= 0L) {
               return this.getFileSize(outputFile, fallbackSize);
            }

            try (
               InputStream inputStream = new BufferedInputStream(Files.newInputStream(latestLogPath));
               FileSystem zipFileSystem = FileSystems.newFileSystem(outputFile, (ClassLoader)null);
               OutputStream outputStream = Files.newOutputStream(
                  zipFileSystem.getPath("backup.log"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE
               );
            ) {
               this.skip(inputStream, startOffset);
               this.copy(inputStream, outputStream, bytesToCopy);
            }
         } catch (IOException e) {
            LOGGER.warn("Failed to include latest.log in backup archive", e);
         }

         return this.getFileSize(outputFile, fallbackSize);
      } else {
         return this.getFileSize(outputFile, fallbackSize);
      }
   }

   private long getFileSize(Path path, long fallbackSize) {
      try {
         return Files.isRegularFile(path) ? Files.size(path) : fallbackSize;
      } catch (IOException e) {
         return fallbackSize;
      }
   }

   private void copy(InputStream inputStream, OutputStream outputStream, long bytesToCopy) throws IOException {
      long remaining = bytesToCopy;
      byte[] buffer = new byte[8192];

      while (remaining > 0L) {
         int length = inputStream.read(buffer, 0, (int)Math.min(buffer.length, remaining));
         if (length < 0) {
            return;
         }

         outputStream.write(buffer, 0, length);
         remaining -= length;
      }
   }

   private void skip(InputStream inputStream, long bytesToSkip) throws IOException {
      long remaining = bytesToSkip;

      while (remaining > 0L) {
         long skipped = inputStream.skip(remaining);
         if (skipped > 0L) {
            remaining -= skipped;
         } else {
            if (inputStream.read() < 0) {
               return;
            }

            remaining--;
         }
      }
   }

   private boolean createFullBackup() {
      return this.fullBackup || this.forceFullBackup;
   }

   private record BackupResult(Path outputFile, long fileSize) {
   }

   private record LogSnapshot(long offset, @Nullable Object fileKey) {
      private static BackupThread.LogSnapshot disabled() {
         return new BackupThread.LogSnapshot(-1L, null);
      }

      private boolean isEnabled() {
         return this.offset >= 0L;
      }

      private boolean hasRolledOver(BasicFileAttributes currentAttributes) {
         Object currentFileKey = currentAttributes.fileKey();
         return currentAttributes.size() < this.offset || (this.fileKey != null || currentFileKey != null) && !Objects.equals(this.fileKey, currentFileKey);
      }
   }

   private static class Timer {
      public static String getTimer(long milliseconds) {
         long seconds = milliseconds / 1000L;
         long ms = milliseconds % 1000L;
         if (seconds < 60L) {
            return String.format("%d.%03ds", seconds, ms);
         } else {
            return seconds < 3600L
               ? String.format("%02d:%02dmin", seconds / 60L, seconds % 60L)
               : String.format("%d:%02dh", seconds / 3600L, seconds % 3600L / 60L);
         }
      }
   }
}

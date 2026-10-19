package com.mafuyu404.oneenoughitem.client.gui.cache;

import com.mafuyu404.oneenoughitem.Oneenoughitem;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public abstract class BaseCache {
   protected final Path cacheFile;
   protected final int cacheVersion;
   protected final ReadWriteLock lock = new ReentrantReadWriteLock();
   protected volatile boolean initialized = false;

   protected BaseCache(String dirName, String fileName, int version) {
      try {
         dirName = dirName != null && !dirName.isEmpty() ? dirName.toLowerCase() : "oei";
      } catch (Throwable t) {
         dirName = "oei";
      }

      Path dir = Paths.get(dirName);
      this.cacheFile = dir.resolve(fileName);
      this.cacheVersion = version;
   }

   protected void initialize() {
      if (!this.initialized) {
         this.lock.writeLock().lock();

         try {
            if (!this.initialized) {
               this.loadFromFile();
               this.initialized = true;
               this.onInitialized();
               return;
            }
         } finally {
            this.lock.writeLock().unlock();
         }
      }
   }

   protected abstract void onInitialized();

   protected void loadFromFile() {
      if (!Files.exists(this.cacheFile)) {
         Oneenoughitem.LOGGER.debug("Cache file not found: {}, starting with empty cache", this.cacheFile);
      } else {
         try (DataInputStream dis = new DataInputStream(new BufferedInputStream(Files.newInputStream(this.cacheFile)))) {
            int version = dis.readInt();
            if (version == this.cacheVersion) {
               this.loadData(dis);
               Oneenoughitem.LOGGER.debug("Loaded cache from: {}", this.cacheFile);
            } else {
               Oneenoughitem.LOGGER.warn("Cache version mismatch for {}, expected {}, got {}", this.cacheFile, this.cacheVersion, version);
               this.onVersionMismatch(version);
            }
         } catch (IOException e) {
            Oneenoughitem.LOGGER.error("Failed to load cache from: {}", this.cacheFile, e);
            this.onLoadError(e);
         }
      }
   }

   protected abstract void onVersionMismatch(int var1);

   protected abstract void onLoadError(IOException var1);

   protected abstract void loadData(DataInputStream var1) throws IOException;

   protected void saveToFileAsync() {
      Thread saveThread = new Thread(() -> {
         try {
            this.saveToFile();
         } catch (Exception e) {
            Oneenoughitem.LOGGER.error("Failed to save cache asynchronously: {}", this.cacheFile, e);
         }
      });
      saveThread.setDaemon(true);
      saveThread.setName(this.getClass().getSimpleName() + "-Save");
      saveThread.start();
   }

   protected void saveToFile() {
      try {
         Files.createDirectories(this.cacheFile.getParent());
         Path tempFile = this.cacheFile.resolveSibling(this.cacheFile.getFileName() + ".tmp");
         if (Files.exists(tempFile)) {
            Files.delete(tempFile);
         }

         try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(tempFile)))) {
            dos.writeInt(this.cacheVersion);
            this.saveData(dos);
            dos.flush();
         }

         Files.move(tempFile, this.cacheFile, StandardCopyOption.REPLACE_EXISTING);
         Oneenoughitem.LOGGER.debug("Saved cache to: {}", this.cacheFile);
      } catch (IOException e) {
         Oneenoughitem.LOGGER.error("Failed to save cache to: {}", this.cacheFile, e);
      }
   }

   protected abstract void saveData(DataOutputStream var1) throws IOException;

   protected void clearCacheFile() {
      try {
         if (Files.exists(this.cacheFile)) {
            Files.delete(this.cacheFile);
            Oneenoughitem.LOGGER.debug("Cache file cleared: {}", this.cacheFile);
         }
      } catch (IOException e) {
         Oneenoughitem.LOGGER.error("Failed to clear cache file: {}", this.cacheFile, e);
      }
   }

   public Path getCacheFilePath() {
      return this.cacheFile;
   }

   protected <T> T withReadLock(Supplier<T> operation) {
      this.lock.readLock().lock();

      try {
         return operation.get();
      } finally {
         this.lock.readLock().unlock();
      }
   }

   protected void withWriteLock(Runnable operation) {
      this.lock.writeLock().lock();

      try {
         operation.run();
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   protected <T> T withWriteLock(Supplier<T> operation) {
      this.lock.writeLock().lock();

      try {
         return operation.get();
      } finally {
         this.lock.writeLock().unlock();
      }
   }
}

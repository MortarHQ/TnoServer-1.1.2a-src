package mezz.jei.common.config.file;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class FileWatcher {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   private final FileWatcherThread thread;

   public FileWatcher(String threadName) {
      this.thread = createThread(threadName);
   }

   @Nullable
   private static FileWatcherThread createThread(String threadName) {
      try {
         return new FileWatcherThread(threadName);
      } catch (UnsupportedOperationException | IOException e) {
         LOGGER.error("Unable to create file watcher: ", e);
         return null;
      }
   }

   public void addCallback(Path path, Runnable callback) {
      if (this.thread != null) {
         this.thread.addCallback(path, callback);
      }
   }

   public void start() {
      if (this.thread != null) {
         this.thread.start();
      }
   }
}

package org.popcraft.chunky.util;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.World;

public final class Disk {
   private static final long ESTIMATED_SPACE_PER_CHUNK = 7031L;
   private static final double PERCENT_OVERESTIMATE = 1.05;

   private Disk() {
   }

   public static long estimatedSpace(Selection selection) {
      long diameterChunksX = selection.diameterChunksX();
      long diameterChunksZ = selection.diameterChunksZ();
      return (long)(1.05 * (diameterChunksX * diameterChunksZ * 7031L));
   }

   public static long remainingSpace(World world) {
      Optional<Path> regionDirectory = world.getRegionDirectory();

      try {
         if (regionDirectory.isPresent()) {
            return regionDirectory.get().toFile().getUsableSpace();
         }

         Path currentWorkingDirectory = Paths.get("");
         return Files.exists(currentWorkingDirectory) ? currentWorkingDirectory.toFile().getUsableSpace() : 0L;
      } catch (UnsupportedOperationException | InvalidPathException | SecurityException ignored) {
         return Long.MAX_VALUE;
      }
   }
}

package org.popcraft.chunky.platform;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.popcraft.chunky.platform.util.Location;

public interface World {
   String getName();

   String getKey();

   CompletableFuture<Boolean> isChunkGenerated(int var1, int var2);

   CompletableFuture<Void> getChunkAtAsync(int var1, int var2);

   UUID getUUID();

   int getSeaLevel();

   Location getSpawn();

   Border getWorldBorder();

   int getElevation(int var1, int var2);

   int getMaxElevation();

   void playEffect(Player var1, String var2);

   void playSound(Player var1, String var2);

   Optional<Path> getDirectory(String var1);

   default Optional<Path> getEntitiesDirectory() {
      return this.getDirectory("entities");
   }

   default Optional<Path> getPOIDirectory() {
      return this.getDirectory("poi");
   }

   default Optional<Path> getRegionDirectory() {
      return this.getDirectory("region");
   }
}

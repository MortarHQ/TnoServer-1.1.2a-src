package org.popcraft.chunky.iterator;

import java.util.Iterator;
import org.popcraft.chunky.util.ChunkCoordinate;

public interface ChunkIterator extends Iterator<ChunkCoordinate> {
   long total();

   String name();

   default boolean process() {
      return true;
   }
}

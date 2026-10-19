package twilightforest.world.components.chunkblanketing;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public interface ChunkBlanketProcessor {
   HolderSet<Biome> biomesForApplication();

   void processChunk(RandomSource var1, Function<BlockPos, Holder<Biome>> var2, ChunkAccess var3);

   ChunkBlanketType getType();

   interface SimpleProcessor extends ChunkBlanketProcessor {
      @Override
      default void processChunk(RandomSource random, Function<BlockPos, Holder<Biome>> biomeGetter, ChunkAccess chunkAccess) {
         for (int dX = 0; dX < 16; dX++) {
            for (int dZ = 0; dZ < 16; dZ++) {
               BlockPos firstAvailableBlock = chunkAccess.getPos().getBlockAt(dX, chunkAccess.getHeight(this.heightmap(), dX, dZ) + 1, dZ);
               if (this.biomesForApplication().contains(biomeGetter.apply(firstAvailableBlock))) {
                  this.processColumn(random, chunkAccess, firstAvailableBlock);
               }
            }
         }
      }

      void processColumn(RandomSource var1, ChunkAccess var2, BlockPos var3);

      Types heightmap();
   }
}

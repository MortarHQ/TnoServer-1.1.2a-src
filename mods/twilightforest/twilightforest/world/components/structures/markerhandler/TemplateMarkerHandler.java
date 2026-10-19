package twilightforest.world.components.structures.markerhandler;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.StringUtils;

public interface TemplateMarkerHandler {
   boolean handleDataMarker(String var1, BlockPos var2, WorldGenLevel var3, RandomSource var4, BoundingBox var5, ChunkGenerator var6, Rotation var7);

   TemplateMarkerHandlerType getType();

   static int parseRange(String amountLabel, RandomSource random, int defaultMin, int defaultMax) {
      String[] amountParams = amountLabel.split("-");
      if (amountParams.length == 1 && StringUtils.isNumeric(amountParams[0])) {
         return Integer.parseInt(amountParams[0]);
      } else {
         return amountParams.length == 2 && StringUtils.isNumeric(amountParams[0]) && StringUtils.isNumeric(amountParams[1])
            ? random.nextIntBetweenInclusive(Integer.parseInt(amountParams[0]), Integer.parseInt(amountParams[1]))
            : random.nextIntBetweenInclusive(defaultMin, defaultMax);
      }
   }
}

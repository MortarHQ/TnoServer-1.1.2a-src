package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class ValueMapGenerator {
   public static void place(ValueMapProvider provider, ValueMapGenerator.BlockPlacer placer) {
      placeAt(provider, placer, BlockPos.ZERO, new ArrayList<>());
   }

   private static void placeAt(ValueMapProvider provider, ValueMapGenerator.BlockPlacer placer, BlockPos pos, List<BlockPos> visited) {
      visited.add(pos);
      float value = provider.getValue(pos.getX(), pos.getY(), pos.getZ());
      if (value < 1.0F && value >= 0.0F) {
         placer.place(pos, value);

         for (Direction direction : Direction.values()) {
            if (!visited.contains(pos.relative(direction))) {
               placeAt(provider, placer, pos.relative(direction), visited);
            }
         }
      }
   }

   public interface BlockPlacer {
      void place(BlockPos var1, float var2);
   }
}

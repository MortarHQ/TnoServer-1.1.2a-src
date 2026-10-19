package twilightforest.util;

import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RootPlacer {
   private final BiConsumer<BlockPos, BlockState> rootPlacer;
   private final int rootPenetrability;

   public RootPlacer(BiConsumer<BlockPos, BlockState> placer, int penetrability) {
      this.rootPlacer = placer;
      this.rootPenetrability = penetrability;
   }

   public BiConsumer<BlockPos, BlockState> getPlacer() {
      return this.rootPlacer;
   }

   public int getRootPenetrability() {
      return this.rootPenetrability;
   }
}

package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import twilightforest.init.TFBlocks;

public class UnripeTorchClusterBlock extends TrollRootBlock {
   private static final int RIPEN_THRESHOLD = 6;

   public UnripeTorchClusterBlock(Properties properties) {
      super(properties);
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.getMaxLocalRawBrightness(pos) >= 6) {
         level.setBlockAndUpdate(pos, ((Block)TFBlocks.TROLLBER.get()).defaultBlockState());
      }
   }
}

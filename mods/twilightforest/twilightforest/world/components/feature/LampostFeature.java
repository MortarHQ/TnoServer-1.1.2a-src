package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import twilightforest.init.TFBlocks;

public class LampostFeature extends Feature<BlockStateConfiguration> {
   private static final Rotation[] ROTATIONS = Rotation.values();

   public LampostFeature(Codec<BlockStateConfiguration> configIn) {
      super(configIn);
   }

   public boolean place(FeaturePlaceContext<BlockStateConfiguration> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      BlockStateConfiguration config = (BlockStateConfiguration)ctx.config();
      if (world.getBlockState(pos.below()).getBlock() != Blocks.GRASS_BLOCK) {
         return false;
      }

      int height = 1 + rand.nextInt(4);

      for (int dy = 0; dy <= height; dy++) {
         BlockState state = world.getBlockState(pos.above(dy));
         if (!state.isAir() && !state.canBeReplaced()) {
            return false;
         }
      }

      for (int dy = 0; dy < height; dy++) {
         world.setBlock(pos.above(dy), ((Block)TFBlocks.CANOPY_FENCE.get()).defaultBlockState(), 18);
      }

      world.setBlock(pos.above(height), config.state.rotate(ROTATIONS[rand.nextInt(ROTATIONS.length)]), 18);
      return true;
   }
}

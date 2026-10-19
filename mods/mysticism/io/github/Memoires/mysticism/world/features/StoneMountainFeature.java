package io.github.Memoires.mysticism.world.features;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StoneMountainFeature extends Feature<NoneFeatureConfiguration> {
   private final List<BlockState> BLOCKS = ImmutableList.of(
      Blocks.STONE.defaultBlockState(),
      Blocks.COBBLESTONE.defaultBlockState(),
      Blocks.ANDESITE.defaultBlockState(),
      Blocks.MOSSY_COBBLESTONE.defaultBlockState()
   );

   public StoneMountainFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos origin = context.origin();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();

      while (origin.getY() > level.getMinBuildHeight() + 3 && (level.isEmptyBlock(origin.below()) || !this.isValidBase(level, origin.below()))) {
         origin = origin.below();
      }

      if (origin.getY() <= level.getMinBuildHeight() + 3) {
         return false;
      }

      int height = 12 + random.nextInt(34);
      BlockPos current = origin.above();

      for (int y = 0; y < height; y++) {
         int radius = 4 - y / 8;
         BlockState block = this.BLOCKS.get(random.nextInt(this.BLOCKS.size()));

         for (BlockPos target : BlockPos.betweenClosed(current.offset(-radius, 0, -radius), current.offset(radius, 0, radius))) {
            if (target.distSqr(current) <= radius * radius + random.nextDouble()) {
               level.setBlock(target, block, 4);
            }
         }

         current = current.above();
      }

      return true;
   }

   private boolean isValidBase(WorldGenLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(Blocks.STONE);
   }
}

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

public class FireBlockBlobFeature extends Feature<NoneFeatureConfiguration> {
   private final List<BlockState> BLOCKS = ImmutableList.of(
      Blocks.LAVA.defaultBlockState(), Blocks.BASALT.defaultBlockState(), Blocks.BLACKSTONE.defaultBlockState(), Blocks.MAGMA_BLOCK.defaultBlockState()
   );

   public FireBlockBlobFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();

      while (pos.getY() > level.getMinBuildHeight() + 3 && (level.isEmptyBlock(pos.below()) || !this.isValidPosition(level, pos.below()))) {
         pos = pos.below();
      }

      if (pos.getY() <= level.getMinBuildHeight() + 3) {
         return false;
      }

      BlockState chosen = this.BLOCKS.get(random.nextInt(this.BLOCKS.size()));

      for (int i = 0; i < 3; i++) {
         int dx = random.nextInt(2);
         int dy = random.nextInt(2);
         int dz = random.nextInt(2);
         float radius = (dx + dy + dz) * 0.333F + 0.5F;

         for (BlockPos target : BlockPos.betweenClosed(pos.offset(-dx, -dy, -dz), pos.offset(dx, dy, dz))) {
            if (target.distSqr(pos) < radius * radius) {
               level.setBlock(target, chosen, 4);
            }
         }

         pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
      }

      return true;
   }

   private boolean isValidPosition(WorldGenLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(Blocks.BASALT) || state.is(Blocks.BLACKSTONE) || state.is(Blocks.NETHERRACK);
   }
}

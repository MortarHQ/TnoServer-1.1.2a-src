package io.github.Memoires.mysticism.world.features;

import com.google.common.collect.ImmutableList;
import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WaterBlobBlockFeature extends Feature<NoneFeatureConfiguration> {
   public WaterBlobBlockFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   private List<BlockState> getBlocks() {
      return ImmutableList.of(
         Blocks.ICE.defaultBlockState(),
         Blocks.SNOW_BLOCK.defaultBlockState(),
         Blocks.PACKED_ICE.defaultBlockState(),
         ((Block)MysticismBlocks.ICE_ORE.get()).defaultBlockState()
      );
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

      int blockCount = 3;
      BlockState chosen;
      if (random.nextFloat() < 0.05F) {
         chosen = ((Block)MysticismBlocks.ICE_ORE.get()).defaultBlockState();
         blockCount = 1;
      } else {
         chosen = this.getBlocks().get(random.nextInt(this.getBlocks().size() - 1));
      }

      for (int i = 0; i < blockCount; i++) {
         int dx = random.nextInt(2);
         int dy = random.nextInt(2);
         int dz = random.nextInt(2);
         float radius = (dx + dy + dz) * 0.333F + 0.5F;
         if (chosen == ((Block)MysticismBlocks.ICE_ORE.get()).defaultBlockState()) {
            level.setBlock(pos, chosen, 4);
         } else {
            for (BlockPos target : BlockPos.betweenClosed(pos.offset(-dx, -dy, -dz), pos.offset(dx, dy, dz))) {
               if (target.distSqr(pos) < radius * radius) {
                  level.setBlock(target, chosen, 4);
               }
            }
         }

         pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
      }

      return true;
   }

   private boolean isValidPosition(WorldGenLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(Blocks.DIRT) || state.is(Blocks.SAND) || state.is(Blocks.PODZOL);
   }
}

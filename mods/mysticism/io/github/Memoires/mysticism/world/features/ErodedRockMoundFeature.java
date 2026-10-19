package io.github.Memoires.mysticism.world.features;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ErodedRockMoundFeature extends Feature<NoneFeatureConfiguration> {
   private final List<BlockState> BLOCKS = ImmutableList.of(
      Blocks.RED_SAND.defaultBlockState(),
      Blocks.TERRACOTTA.defaultBlockState(),
      Blocks.ORANGE_TERRACOTTA.defaultBlockState(),
      Blocks.STONE.defaultBlockState()
   );

   public ErodedRockMoundFeature() {
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

      int radius = 3 + random.nextInt(5);
      int height = 2 + random.nextInt(3);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int dx = -radius; dx <= radius; dx++) {
         for (int dz = -radius; dz <= radius; dz++) {
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance <= radius + 0.5) {
               int columnHeight = (int)(height * (1.0 - distance / radius) + random.nextDouble());

               for (int dy = 0; dy <= columnHeight; dy++) {
                  mutable.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                  BlockState chosen = this.BLOCKS.get(random.nextInt(this.BLOCKS.size()));
                  level.setBlock(mutable, chosen, 2);
               }
            }
         }
      }

      return true;
   }

   private boolean isValidBase(WorldGenLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(Blocks.SAND) || state.is(Blocks.RED_SAND) || state.is(Blocks.TERRACOTTA);
   }
}

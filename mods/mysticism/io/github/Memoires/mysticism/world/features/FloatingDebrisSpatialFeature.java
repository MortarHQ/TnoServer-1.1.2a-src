package io.github.Memoires.mysticism.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FloatingDebrisSpatialFeature extends Feature<NoneFeatureConfiguration> {
   public FloatingDebrisSpatialFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();
      if (pos.getY() < 84 || pos.getY() > 128) {
         int newY = 84 + random.nextIntBetweenInclusive(2, 26);
         pos = new BlockPos(pos.getX(), newY, pos.getZ());
      }

      BlockState END_STONE_BRICKS = Blocks.END_STONE_BRICKS.defaultBlockState();
      BlockState END_ROD = Blocks.END_ROD.defaultBlockState();
      BlockPos previousPos = pos;

      for (int i = 0; i < 24 && (!(random.nextFloat() < 0.2F) || i <= 10); i++) {
         Direction direction = this.getDirection(level, pos, previousPos, random);
         if (direction == null) {
            break;
         }

         pos = pos.relative(direction);
         previousPos = pos;
         level.setBlock(pos, random.nextFloat() > 0.5F ? END_STONE_BRICKS : END_ROD, 4);
      }

      return false;
   }

   private Direction getDirection(WorldGenLevel level, BlockPos pos, BlockPos previousPos, RandomSource random) {
      for (int i = 0; i < 4; i++) {
         Direction direction = Direction.getRandom(random);
         boolean flag1 = pos.relative(direction) == previousPos;
         boolean flag2 = !level.isEmptyBlock(pos.relative(direction));
         if (!flag1 && !flag2) {
            return direction;
         }
      }

      return null;
   }
}

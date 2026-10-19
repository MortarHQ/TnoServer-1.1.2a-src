package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AbyssalCaveFeature extends Feature<NoneFeatureConfiguration> {
   public AbyssalCaveFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      RandomSource random = context.random();
      MutableBlockPos placePos = new MutableBlockPos();
      int xSize = 8 + random.nextInt(5) - 2;
      int ySize = 8 + random.nextInt(5) - 2;
      int zSize = 8 + random.nextInt(5) - 2;
      Set<BlockPos> hollowPositions = new HashSet<>();

      for (int x = -xSize; x <= xSize; x++) {
         for (int y = -ySize; y <= ySize; y++) {
            for (int z = -zSize; z <= zSize; z++) {
               if (ESMathUtil.isPointInEllipsoid(x, y, z, xSize, ySize, zSize)) {
                  placePos.setWithOffset(pos, x, y, z);
                  BlockState state = level.getBlockState(placePos);
                  if (!state.is(ESTags.Blocks.ABYSSAL_CAVE_REPLACEABLES)
                     && !state.is(ESTags.Blocks.STARLIGHT_CARVER_REPLACEABLES)
                     && state.getBlock() != Blocks.WATER
                     && (!state.canBeReplaced() || state.isAir())) {
                     return false;
                  }

                  hollowPositions.add(placePos.immutable());
               }
            }
         }
      }

      hollowPositions.forEach(blockPos -> this.setBlock(level, blockPos, Blocks.WATER.defaultBlockState()));
      return true;
   }
}

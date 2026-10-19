package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LeavesPileFeature extends Feature<LeavesPileFeature.Configuration> {
   public LeavesPileFeature(Codec<LeavesPileFeature.Configuration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<LeavesPileFeature.Configuration> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      RandomSource random = context.random();
      boolean canPlace = false;

      for (int x = -4; x <= 4; x++) {
         for (int y = -2; y <= 2; y++) {
            for (int z = -4; z <= 4; z++) {
               if (level.getBlockState(pos.offset(x, y, z)).is(BlockTags.LOGS)) {
                  canPlace = true;
               }
            }
         }
      }

      if (!canPlace) {
         return false;
      }

      MutableBlockPos placePos = new MutableBlockPos();

      for (int x = -4; x <= 4; x++) {
         for (int y = -2; y <= 2; y++) {
            for (int z = -4; z <= 4; z++) {
               if (x * x + z * z <= 16
                  && level.getBlockState(pos.offset(x, y, z)).is(BlockTags.DIRT)
                  && level.getBlockState(pos.offset(x, y + 1, z)).isAir()
                  && random.nextBoolean()) {
                  placePos.setWithOffset(pos, x, y + 1, z);
                  BlockState pileState = ((LeavesPileFeature.Configuration)context.config()).pile().getState(random, placePos);
                  if (pileState.hasProperty(BlockStateProperties.LAYERS)) {
                     pileState = (BlockState)pileState.setValue(BlockStateProperties.LAYERS, random.nextInt(1, 5));
                  }

                  level.setBlock(placePos, pileState, 3);
               }
            }
         }
      }

      return true;
   }

   public record Configuration(BlockStateProvider pile) implements FeatureConfiguration {
      public static final Codec<LeavesPileFeature.Configuration> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(BlockStateProvider.CODEC.fieldOf("pile").forGetter(LeavesPileFeature.Configuration::pile))
            .apply(instance, LeavesPileFeature.Configuration::new)
      );
   }
}

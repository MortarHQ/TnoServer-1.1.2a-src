package gardensofthedead.feature;

import com.mojang.serialization.Codec;
import gardensofthedead.block.SoulSporeBaseBlock;
import gardensofthedead.block.SoulSporeBlock;
import gardensofthedead.feature.configuration.SoulSporeColumnConfiguration;
import gardensofthedead.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SoulSporeColumnFeature extends Feature<SoulSporeColumnConfiguration> {
   public SoulSporeColumnFeature(Codec<SoulSporeColumnConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<SoulSporeColumnConfiguration> context) {
      WorldGenLevel level = context.level();
      SoulSporeColumnConfiguration config = (SoulSporeColumnConfiguration)context.config();
      BlockPos origin = context.origin();
      if (!isValidPlacementLocation(level, origin, config)) {
         return false;
      }

      Direction direction = config.direction();
      RandomSource randomSource = context.random();
      int length = config.length().sample(randomSource);
      float glowingChance = config.glowingChance();
      MutableBlockPos pos = new MutableBlockPos().set(origin);
      placeSoulSporeColumn(level, randomSource, pos, length, direction, glowingChance);
      return true;
   }

   public static void placeSoulSporeColumn(
      LevelAccessor level, RandomSource randomSource, MutableBlockPos pos, int length, Direction direction, float glowingChance
   ) {
      for (int y = 1; y <= length; y++) {
         if (level.isEmptyBlock(pos)) {
            if (y == length || !level.isEmptyBlock(pos.relative(direction))) {
               BlockState topState = randomSource.nextFloat() < glowingChance && y > 1
                  ? ((SoulSporeBaseBlock)ModBlocks.GLOWING_SOUL_SPORE.get()).defaultBlockState()
                  : (BlockState)((SoulSporeBlock)ModBlocks.SOUL_SPORE.get()).defaultBlockState().setValue(SoulSporeBlock.GROWING, false);
               level.setBlock(pos, (BlockState)topState.setValue(SoulSporeBaseBlock.DIRECTION, direction), 2);
               break;
            }

            BlockState state = (BlockState)((BlockState)((BlockState)((SoulSporeBlock)ModBlocks.SOUL_SPORE.get())
                     .defaultBlockState()
                     .setValue(SoulSporeBlock.TOP, false))
                  .setValue(SoulSporeBaseBlock.DIRECTION, direction))
               .setValue(SoulSporeBlock.GROWING, false);
            level.setBlock(pos, state, 2);
         }

         pos.move(direction);
      }
   }

   private static boolean isValidPlacementLocation(LevelAccessor level, BlockPos pos, SoulSporeColumnConfiguration configuration) {
      if (!level.isEmptyBlock(pos)) {
         return false;
      }

      BlockState supportingState = level.getBlockState(pos.relative(configuration.direction().getOpposite()));
      return supportingState.is(Blocks.SOUL_SAND) || supportingState.is(Blocks.SOUL_SOIL);
   }
}

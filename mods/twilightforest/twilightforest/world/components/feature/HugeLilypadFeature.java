package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.block.HugeLilyPadBlock;
import twilightforest.enums.HugeLilypadPiece;
import twilightforest.init.TFBlocks;

public class HugeLilypadFeature extends Feature<NoneFeatureConfiguration> {
   public HugeLilypadFeature(Codec<NoneFeatureConfiguration> config) {
      super(config);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource random = ctx.random();

      for (int i = 0; i < 10; i++) {
         BlockPos dPos = pos.offset(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (this.shouldPlacePadAt(world, dPos) && world.isAreaLoaded(dPos, 1)) {
            Direction horizontal = Direction.from2DDataValue(random.nextInt(4));
            BlockState lilypad = (BlockState)((HugeLilyPadBlock)TFBlocks.HUGE_LILY_PAD.get()).defaultBlockState().setValue(HugeLilyPadBlock.FACING, horizontal);
            world.setBlock(dPos, (BlockState)lilypad.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.NW), 18);
            world.setBlock(dPos.east(), (BlockState)lilypad.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.NE), 18);
            world.setBlock(dPos.east().south(), (BlockState)lilypad.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.SE), 18);
            world.setBlock(dPos.south(), (BlockState)lilypad.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.SW), 18);
         }
      }

      return true;
   }

   private boolean shouldPlacePadAt(LevelAccessor world, BlockPos pos) {
      return world.isEmptyBlock(pos)
         && world.getBlockState(pos.below()).is(Blocks.WATER)
         && world.isEmptyBlock(pos.east())
         && world.getBlockState(pos.east().below()).is(Blocks.WATER)
         && world.isEmptyBlock(pos.south())
         && world.getBlockState(pos.south().below()).is(Blocks.WATER)
         && world.isEmptyBlock(pos.east().south())
         && world.getBlockState(pos.east().south().below()).is(Blocks.WATER);
   }
}

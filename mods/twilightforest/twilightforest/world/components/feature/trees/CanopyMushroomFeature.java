package twilightforest.world.components.feature.trees;

import com.mojang.serialization.Codec;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import twilightforest.init.TFBlocks;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.iterators.VoxelBresenhamIterator;

@ParametersAreNonnullByDefault
public abstract class CanopyMushroomFeature extends AbstractHugeMushroomFeature {
   public CanopyMushroomFeature(Codec<HugeMushroomFeatureConfiguration> featureConfigurationCodec) {
      super(featureConfigurationCodec);
   }

   protected int getTreeRadiusForHeight(int i, int i1, int foliageRadius, int treeHeight) {
      return treeHeight <= 3 ? 0 : (int)(foliageRadius * 1.5F);
   }

   protected void placeTrunk(
      LevelAccessor levelAccessor,
      RandomSource random,
      BlockPos pos,
      HugeMushroomFeatureConfiguration featureConfiguration,
      int height,
      MutableBlockPos mutableBlockPos
   ) {
      int bugsLeft = Math.max(0, random.nextInt(10) - 4) / 2;

      for (int i = 0; i < height; i++) {
         mutableBlockPos.set(pos).move(Direction.UP, i);
         if (!this.isReplaceable(levelAccessor, mutableBlockPos)) {
            height = i;
            break;
         }

         this.setBlock(levelAccessor, mutableBlockPos, featureConfiguration.stemProvider.getState(random, pos));
         if (bugsLeft > 0 && i > height / 2 && random.nextInt(10) == 9 && this.addFirefly(levelAccessor, mutableBlockPos, random)) {
            bugsLeft--;
         }
      }

      int numBranches = this.getBranches(random);
      float offset = random.nextFloat();

      for (int b = 0; b < numBranches; b++) {
         bugsLeft = this.buildABranch(
            levelAccessor,
            pos,
            height - 6 + b,
            this.getLength(random),
            0.3 * b + offset,
            random,
            new HugeMushroomFeatureConfiguration(featureConfiguration.capProvider, featureConfiguration.stemProvider, featureConfiguration.foliageRadius - 1),
            bugsLeft
         );
      }
   }

   protected boolean addFirefly(LevelAccessor levelAccessor, BlockPos pos, RandomSource random) {
      Direction direction = Direction.getRandom(random);
      if (direction.getAxis() == Axis.Y) {
         return false;
      }

      MutableBlockPos bugPos = new MutableBlockPos();
      bugPos.set(pos).move(direction);
      if (!this.isReplaceable(levelAccessor, bugPos)) {
         return false;
      }

      BlockState bugState = (BlockState)((Block)TFBlocks.FIREFLY.get()).defaultBlockState().setValue(DirectionalBlock.FACING, direction);
      this.setBlock(levelAccessor, bugPos, bugState);
      return true;
   }

   protected int getTreeHeight(RandomSource random) {
      return 9 + random.nextInt(5);
   }

   protected abstract int getBranches(RandomSource var1);

   protected abstract double getLength(RandomSource var1);

   private int buildABranch(
      LevelAccessor levelAccessor,
      BlockPos pos,
      int height,
      double length,
      double angle,
      RandomSource random,
      HugeMushroomFeatureConfiguration featureConfiguration,
      int bugsLeft
   ) {
      BlockPos src = pos.above(height);
      BlockPos dest = FeatureLogic.translate(src, length, angle, 0.2);

      for (BlockPos pixel : new VoxelBresenhamIterator(src, new BlockPos(dest.getX(), src.getY(), dest.getZ()))) {
         BlockState blockstate = featureConfiguration.stemProvider.getState(random, pos);
         if (blockstate.hasProperty(HugeMushroomBlock.UP) && blockstate.hasProperty(HugeMushroomBlock.DOWN)) {
            blockstate = (BlockState)((BlockState)blockstate.setValue(HugeMushroomBlock.DOWN, true)).setValue(HugeMushroomBlock.UP, true);
         }

         if (this.isReplaceable(levelAccessor, pixel)) {
            this.setBlock(levelAccessor, pixel, blockstate);
         }
      }

      int max = Math.max(src.getY(), dest.getY());

      for (int i = Math.min(src.getY(), dest.getY()); i < max + 1; i++) {
         BlockState blockstate = featureConfiguration.stemProvider.getState(random, pos);
         if (blockstate.hasProperty(HugeMushroomBlock.DOWN) && i == Math.min(src.getY(), dest.getY())) {
            blockstate = (BlockState)blockstate.setValue(HugeMushroomBlock.DOWN, true);
         }

         BlockPos blockPos = new BlockPos(dest.getX(), i, dest.getZ());
         if (this.isReplaceable(levelAccessor, blockPos)) {
            this.setBlock(levelAccessor, blockPos, blockstate);
         }

         if (bugsLeft > 0 && i > Math.min(src.getY(), dest.getY()) / 2 && random.nextInt(20) == 0 && this.addFirefly(levelAccessor, blockPos, random)) {
            bugsLeft--;
         }
      }

      this.makeCap(levelAccessor, random, dest, 1, new MutableBlockPos(), featureConfiguration);
      return bugsLeft;
   }

   protected void makeCap(
      LevelAccessor levelAccessor,
      RandomSource random,
      BlockPos pos,
      int height,
      MutableBlockPos mutableBlockPos,
      HugeMushroomFeatureConfiguration featureConfiguration
   ) {
      int foliageRadius = featureConfiguration.foliageRadius;

      for (int x = -foliageRadius; x <= foliageRadius; x++) {
         for (int z = -foliageRadius; z <= foliageRadius; z++) {
            if (!FeatureLogic.isCornerInSquare(x, z, foliageRadius)) {
               mutableBlockPos.setWithOffset(pos, x, height, z);
               if (this.isReplaceable(levelAccessor, mutableBlockPos)) {
                  BlockState blockState = featureConfiguration.capProvider.getState(random, pos);
                  blockState = FeatureLogic.getHorizontalMushroomBlockState(blockState, x, z, foliageRadius);
                  this.setBlock(levelAccessor, mutableBlockPos, blockState);
               }
            }
         }
      }
   }

   public boolean isReplaceable(BlockGetter levelAccessor, BlockPos pos) {
      BlockState blockState = levelAccessor.getBlockState(pos);
      return FeatureLogic.isReplaceable(blockState, true) && !blockState.isSolidRender(levelAccessor, pos);
   }
}

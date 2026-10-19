package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import twilightforest.util.RootPlacer;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.features.FeaturePlacers;
import twilightforest.util.iterators.VoxelBresenhamIterator;
import twilightforest.world.components.feature.config.RootConfig;

public class WoodRootFeature extends Feature<RootConfig> {
   public WoodRootFeature(Codec<RootConfig> configIn) {
      super(configIn);
   }

   public boolean place(FeaturePlaceContext<RootConfig> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      if (world.getBlockState(pos).getBlock() != Blocks.STONE) {
         return false;
      }

      float length = rand.nextFloat() * 6.0F + rand.nextFloat() * 6.0F + 4.0F;
      if (length > pos.getY()) {
         length = pos.getY();
      }

      float tilt = 0.6F + rand.nextFloat() * 0.3F;
      return this.drawRoot(world, rand, pos, pos, length, rand.nextFloat(), tilt, ((RootConfig)ctx.config()).blockRoot(), ((RootConfig)ctx.config()).oreRoot());
   }

   private boolean drawRoot(
      LevelAccessor world,
      RandomSource rand,
      BlockPos oPos,
      BlockPos pos,
      float length,
      float angle,
      float tilt,
      BlockStateProvider rootBlock,
      BlockStateProvider oreBlock
   ) {
      BlockPos dest = FeatureLogic.translate(pos, length, angle, tilt);
      int limit = 6;
      if (oPos.getX() + limit < dest.getX()) {
         dest = new BlockPos(oPos.getX() + limit, dest.getY(), dest.getZ());
      }

      if (oPos.getX() - limit > dest.getX()) {
         dest = new BlockPos(oPos.getX() - limit, dest.getY(), dest.getZ());
      }

      if (oPos.getZ() + limit < dest.getZ()) {
         dest = new BlockPos(dest.getX(), dest.getY(), oPos.getZ() + limit);
      }

      if (oPos.getZ() - limit > dest.getZ()) {
         dest = new BlockPos(dest.getX(), dest.getY(), oPos.getZ() - limit);
      }

      if (world.getBlockState(dest).getBlock() != Blocks.STONE) {
         return false;
      }

      FeaturePlacers.traceRoot(
         world,
         new RootPlacer((checkedPos, rootPlacement) -> world.setBlock(checkedPos, rootPlacement, 3), 1),
         rand,
         rootBlock,
         new VoxelBresenhamIterator(pos, dest)
      );
      if (length > 8.0F && rand.nextInt(3) > 0) {
         BlockPos nextSrc = FeatureLogic.translate(pos, length / 2.0F, angle, tilt);
         float nextAngle = (angle + 0.25F + rand.nextFloat() * 0.5F) % 1.0F;
         float nextTilt = 0.6F + rand.nextFloat() * 0.3F;
         this.drawRoot(world, rand, oPos, nextSrc, length / 2.0F, nextAngle, nextTilt, rootBlock, oreBlock);
      }

      if (length > 6.0F && rand.nextInt(4) == 0) {
         BlockPos ballSrc = FeatureLogic.translate(pos, length / 2.0F, angle, tilt);
         BlockPos ballDest = FeatureLogic.translate(ballSrc, 1.5, (angle + 0.5F) % 1.0F, 0.75);
         this.placeRootBlock(world, ballSrc, oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballSrc.getX(), ballSrc.getY(), ballDest.getZ()), oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballDest.getX(), ballSrc.getY(), ballSrc.getZ()), oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballSrc.getX(), ballSrc.getY(), ballDest.getZ()), oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballSrc.getX(), ballDest.getY(), ballSrc.getZ()), oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballSrc.getX(), ballDest.getY(), ballDest.getZ()), oreBlock, rand);
         this.placeRootBlock(world, new BlockPos(ballDest.getX(), ballDest.getY(), ballSrc.getZ()), oreBlock, rand);
         this.placeRootBlock(world, ballDest, oreBlock, rand);
      }

      return true;
   }

   protected boolean placeRootBlock(LevelAccessor world, BlockPos pos, BlockStateProvider state, RandomSource random) {
      return FeatureLogic.canRootGrowIn(world, pos) && world.setBlock(pos, state.getState(random, pos), 3);
   }
}

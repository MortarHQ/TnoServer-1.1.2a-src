package de.ellpeck.naturesaura.gen;

import com.mojang.serialization.Codec;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LevelGenAncientTree extends Feature<NoneFeatureConfiguration> {
   public LevelGenAncientTree() {
      super(Codec.unit(FeatureConfiguration.NONE));
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel level = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      int height = rand.nextInt(3) + 5;
      BlockPos trunkTop = pos.above(height);
      this.setBlock(level, pos, Blocks.AIR.defaultBlockState());
      int rootsAmount = rand.nextInt(4) + 5;

      for (int i = 0; i < rootsAmount; i++) {
         int length = rand.nextInt(3) + 3;
         float angle = (float) (Math.PI * 2) * ((float)i / rootsAmount);
         float x = (float)Math.sin(angle) * length;
         float z = (float)Math.cos(angle) * length;
         BlockPos goal = pos.offset(Mth.floor(x), 0, Mth.floor(z));

         while (level.isStateAtPosition(goal, BlockStateBase::canBeReplaced)) {
            goal = goal.below();
            if (goal.distSqr(pos) >= 100.0) {
               break;
            }
         }

         this.makeBranch(level, pos.above(rand.nextInt(1)), goal, ModBlocks.ANCIENT_BARK.defaultBlockState(), false);
      }

      for (int x = 0; x <= 1; x++) {
         for (int z = 0; z <= 1; z++) {
            for (int i = height - (x + z) * (rand.nextInt(2) + 2); i >= 0; i--) {
               BlockPos goal = pos.offset(x, i, z);
               if (!level.isStateAtPosition(goal, s -> !TreeFeature.validTreePos(level, goal))) {
                  this.setBlock(level, goal, (BlockState)ModBlocks.ANCIENT_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.Y));
               }
            }
         }
      }

      this.makeLeaves(level, trunkTop.above(rand.nextInt(2) - 1), ModBlocks.ANCIENT_LEAVES.defaultBlockState(), rand.nextInt(2) + 3, rand);
      int branchAmount = rand.nextInt(3) + 4;

      for (int i = 0; i < branchAmount; i++) {
         int length = rand.nextInt(2) + 3;
         float angle = (float) (Math.PI * 2) * ((float)i / branchAmount);
         float x = (float)Math.sin(angle) * length;
         float z = (float)Math.cos(angle) * length;
         BlockPos goal = trunkTop.offset(Mth.floor(x), rand.nextInt(3) + 1, Mth.floor(z));
         this.makeBranch(level, trunkTop, goal, ModBlocks.ANCIENT_LOG.defaultBlockState(), true);
         this.makeLeaves(level, goal, ModBlocks.ANCIENT_LEAVES.defaultBlockState(), rand.nextInt(2) + 2, rand);
      }

      return true;
   }

   private void makeBranch(WorldGenLevel level, BlockPos first, BlockPos second, BlockState state, boolean hasAxis) {
      BlockPos pos = second.offset(-first.getX(), -first.getY(), -first.getZ());
      int length = this.getHighestCoord(pos);
      float stepX = (float)pos.getX() / length;
      float stepY = (float)pos.getY() / length;
      float stepZ = (float)pos.getZ() / length;

      for (int i = 0; i <= length; i++) {
         BlockPos goal = first.offset(Mth.floor(0.5F + i * stepX), Mth.floor(0.5F + i * stepY), Mth.floor(0.5F + i * stepZ));
         if (!level.isStateAtPosition(goal, s -> !TreeFeature.validTreePos(level, goal))) {
            if (hasAxis) {
               Axis axis = this.getLogAxis(first, goal);
               this.setBlock(level, goal, (BlockState)state.setValue(RotatedPillarBlock.AXIS, axis));
            } else {
               this.setBlock(level, goal, state);
            }
         }
      }
   }

   private void makeLeaves(WorldGenLevel level, BlockPos pos, BlockState state, int radius, RandomSource rand) {
      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               BlockPos goal = pos.offset(x, y, z);
               if (pos.distSqr(goal) <= radius * radius + rand.nextInt(3) - 1
                  && !level.isStateAtPosition(goal, s -> s.is(BlockTags.LEAVES))
                  && level.isStateAtPosition(goal, st -> !st.is(BlockTags.LOGS) && st.getBlock() != Blocks.DIRT && st.getBlock() != Blocks.GRASS_BLOCK)) {
                  this.setBlock(level, goal, state);
               }
            }
         }
      }
   }

   private int getHighestCoord(BlockPos pos) {
      return Math.max(Mth.abs(pos.getX()), Math.max(Mth.abs(pos.getY()), Mth.abs(pos.getZ())));
   }

   private Axis getLogAxis(BlockPos pos, BlockPos goal) {
      Axis axis = Axis.Y;
      int x = Math.abs(goal.getX() - pos.getX());
      int y = Math.abs(goal.getZ() - pos.getZ());
      int highest = Math.max(x, y);
      if (highest > 0) {
         if (x == highest) {
            axis = Axis.X;
         } else if (y == highest) {
            axis = Axis.Z;
         }
      }

      return axis;
   }
}

package net.regions_unexplored.worldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.regions_unexplored.block.set.NaturalSet;
import net.regions_unexplored.block.set.WoodSet;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.config.state.common.RUCommonConfig;
import net.regions_unexplored.util.RUUtils;

public class RandomBranchDecorator extends TreeDecorator {
   public static final MapCodec<RandomBranchDecorator> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(d -> d.probability),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("branch_block").forGetter(d -> d.branchBlock),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("log_block").forGetter(d -> d.logBlock),
            Codec.intRange(0, 16).fieldOf("required_empty_blocks").forGetter(d -> d.requiredEmptyBlocks),
            BlockStateProvider.CODEC.optionalFieldOf("leaves_provider").forGetter(d -> d.leavesProvider)
         )
         .apply(i, RandomBranchDecorator::new)
   );
   public static final TreeDecoratorType<RandomBranchDecorator> TYPE = new TreeDecoratorType(CODEC);
   private final float probability;
   private final Block branchBlock;
   private final Block logBlock;
   private final int requiredEmptyBlocks;
   private final Optional<BlockStateProvider> leavesProvider;

   public RandomBranchDecorator(float probability, Block branchBlock, Block logBlock, int requiredEmptyBlocks, Optional<BlockStateProvider> leavesProvider) {
      this.probability = probability;
      this.branchBlock = branchBlock;
      this.logBlock = logBlock;
      this.requiredEmptyBlocks = requiredEmptyBlocks;
      this.leavesProvider = leavesProvider;
   }

   public static RandomBranchDecorator createWithoutLeaves(float probability, NaturalSet naturalSet, WoodSet woodSet, int requiredEmptyBlocks) {
      return new RandomBranchDecorator(probability, naturalSet.getBranch(), woodSet.getLog(), requiredEmptyBlocks, Optional.empty());
   }

   public static RandomBranchDecorator create(float probability, NaturalSet naturalSet, WoodSet woodSet, int requiredEmptyBlocks) {
      return create(probability, naturalSet, woodSet, requiredEmptyBlocks, BlockStateProvider.simple(naturalSet.getLeaves()));
   }

   public static RandomBranchDecorator create(float probability, Block branch, Block log, Block leaves, int requiredEmptyBlocks) {
      return new RandomBranchDecorator(probability, branch, log, requiredEmptyBlocks, Optional.of(BlockStateProvider.simple(leaves)));
   }

   public static RandomBranchDecorator create(
      float probability, NaturalSet naturalSet, WoodSet woodSet, int requiredEmptyBlocks, BlockStateProvider leavesProvider
   ) {
      return create(probability, naturalSet, woodSet.getLog(), requiredEmptyBlocks, leavesProvider);
   }

   public static RandomBranchDecorator create(float probability, NaturalSet naturalSet, Block log, int requiredEmptyBlocks, BlockStateProvider leavesProvider) {
      return new RandomBranchDecorator(probability, naturalSet.getBranch(), log, requiredEmptyBlocks, Optional.of(leavesProvider));
   }

   protected TreeDecoratorType<?> type() {
      return TYPE;
   }

   public void place(Context context) {
      RUCommonConfig.Misc.BranchMode mode = RUConfigHandler.COMMON.getBranchMode();
      if (!mode.cannotPlace()) {
         RandomSource random = context.random();
         int topLogY = Integer.MIN_VALUE;
         ObjectListIterator var5 = context.logs().iterator();

         while (var5.hasNext()) {
            BlockPos pos = (BlockPos)var5.next();
            if (pos.getY() > topLogY) {
               topLogY = pos.getY();
            }
         }

         for (BlockPos logsPos : RUUtils.shuffledCopy(context.logs(), random)) {
            if (logsPos.getY() + 2 < topLogY) {
               Direction branchDirection = Plane.HORIZONTAL.getRandomDirection(random);
               BlockPos placementPos = logsPos.relative(branchDirection);
               if (random.nextFloat() <= this.probability && this.hasRequiredEmptyBlocks(context, placementPos)) {
                  BlockState toPlace = (BlockState)((BlockState)mode.selectBlock(this.branchBlock, this.logBlock)
                        .trySetValue(BlockStateProperties.AXIS, branchDirection.getAxis()))
                     .trySetValue(BlockStateProperties.HORIZONTAL_FACING, branchDirection);
                  context.setBlock(placementPos, toPlace);
                  if (this.leavesProvider.isPresent()) {
                     for (Direction direction : Direction.values()) {
                        if (direction != Direction.DOWN) {
                           this.placeLeaves(context, placementPos.relative(direction));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void placeLeaves(Context context, BlockPos pos) {
      if (context.isAir(pos)) {
         context.setBlock(pos, (BlockState)this.leavesProvider.get().getState(context.random(), pos).trySetValue(LeavesBlock.DISTANCE, 1));
      }
   }

   private boolean hasRequiredEmptyBlocks(Context context, BlockPos branchPos) {
      for (int i = 0; i <= this.requiredEmptyBlocks; i++) {
         BlockPos offsetPos = branchPos.below(i);
         if (!context.level().isStateAtPosition(offsetPos, state -> state.isAir() || state.is(BlockTags.LEAVES))) {
            return false;
         }
      }

      return true;
   }
}

package twilightforest.world.components.feature.trees;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import twilightforest.util.features.FeaturePlacers;

public class DarkCanopyTreeFeature extends Feature<TreeConfiguration> {
   public DarkCanopyTreeFeature(Codec<TreeConfiguration> config) {
      super(config);
   }

   public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
      final WorldGenLevel reader = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      boolean foundDirt = false;

      for (int dy = pos.getY(); dy >= reader.getMinBuildHeight(); dy--) {
         BlockState state = reader.getBlockState(new BlockPos(pos.getX(), dy - 1, pos.getZ()));
         if (state.is(BlockTags.DIRT)) {
            foundDirt = true;
            pos = new BlockPos(pos.getX(), dy, pos.getZ());
            break;
         }

         if (state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.SAND)) {
            break;
         }
      }

      if (!foundDirt) {
         return false;
      }

      for (int i = 0; i < 4; i++) {
         if (!FeaturePlacers.validTreePos(reader, pos.relative(Direction.UP, i))) {
            return false;
         }
      }

      for (Direction e : Plane.HORIZONTAL) {
         if (reader.getBlockState(pos.relative(e)).is(BlockTags.LOGS)) {
            return false;
         }
      }

      TreeConfiguration treeconfiguration = (TreeConfiguration)ctx.config();
      Set<BlockPos> set = Sets.newHashSet();
      Set<BlockPos> set1 = Sets.newHashSet();
      final Set<BlockPos> set2 = Sets.newHashSet();
      Set<BlockPos> set3 = Sets.newHashSet();
      BiConsumer<BlockPos, BlockState> biconsumer = (p_160555_, p_160556_) -> {
         set.add(p_160555_.immutable());
         reader.setBlock(p_160555_, p_160556_, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer1 = (p_160548_, p_160549_) -> {
         set1.add(p_160548_.immutable());
         reader.setBlock(p_160548_, p_160549_, 19);
      };
      FoliageSetter setter = new FoliageSetter() {
         public void set(BlockPos pos, BlockState state) {
            set2.add(pos.immutable());
            reader.setBlock(pos, state, 19);
         }

         public boolean isSet(BlockPos p_272999_) {
            return set2.contains(p_272999_);
         }
      };
      BiConsumer<BlockPos, BlockState> biconsumer3 = (p_225290_, p_225291_) -> {
         set3.add(p_225290_.immutable());
         reader.setBlock(p_225290_, p_225291_, 19);
      };
      boolean flag = this.doPlace(reader, rand, pos, biconsumer, biconsumer1, setter, treeconfiguration);
      if (flag && (!set1.isEmpty() || !set2.isEmpty())) {
         if (!treeconfiguration.decorators.isEmpty()) {
            Context treedecorator$context = new Context(reader, biconsumer3, rand, set1, set2, set);
            treeconfiguration.decorators.forEach(p_225282_ -> p_225282_.place(treedecorator$context));
         }

         return BoundingBox.encapsulatingPositions(Iterables.concat(set1, set2, set3)).map(p_160521_ -> {
            DiscreteVoxelShape shape = updateLeaves(reader, p_160521_, set1, set3);
            StructureTemplate.updateShapeAtEdge(reader, 3, shape, p_160521_.minX(), p_160521_.minY(), p_160521_.minZ());
            return true;
         }).orElse(false);
      } else {
         return false;
      }
   }

   private boolean doPlace(
      WorldGenLevel level,
      RandomSource random,
      BlockPos pos,
      BiConsumer<BlockPos, BlockState> consumer,
      BiConsumer<BlockPos, BlockState> consumer1,
      FoliageSetter foliageSetter,
      TreeConfiguration config
   ) {
      int i = config.trunkPlacer.getTreeHeight(random);
      int j = config.foliagePlacer.foliageHeight(random, i, config);
      int k = i - j;
      int l = config.foliagePlacer.foliageRadius(random, k);
      BlockPos blockpos = config.rootPlacer.<BlockPos>map(placer -> placer.getTrunkOrigin(pos, random)).orElse(pos);
      int i1 = Math.min(pos.getY(), blockpos.getY());
      int j1 = Math.max(pos.getY(), blockpos.getY()) + i + 1;
      if (i1 >= level.getMinBuildHeight() + 1 && j1 <= level.getMaxBuildHeight()) {
         OptionalInt optionalint = config.minimumSize.minClippedHeight();
         int k1 = this.getMaxFreeTreeHeight(level, i, blockpos, config);
         if (k1 >= i || optionalint.isPresent() && k1 >= optionalint.getAsInt()) {
            if (config.rootPlacer.isPresent() && !((RootPlacer)config.rootPlacer.get()).placeRoots(level, consumer, random, pos, blockpos, config)) {
               return false;
            }

            List<FoliageAttachment> list = config.trunkPlacer.placeTrunk(level, consumer1, random, k1, blockpos, config);
            list.forEach(attachment -> config.foliagePlacer.createFoliage(level, foliageSetter, random, config, k1, attachment, j, l));
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int getMaxFreeTreeHeight(LevelSimulatedReader level, int trunkHeight, BlockPos pos, TreeConfiguration config) {
      MutableBlockPos mutable = new MutableBlockPos();

      for (int i = 0; i <= trunkHeight + 1; i++) {
         int j = config.minimumSize.getSizeAtHeight(trunkHeight, i);

         for (int k = -j; k <= j; k++) {
            for (int l = -j; l <= j; l++) {
               mutable.setWithOffset(pos, k, i, l);
               if (!validTreePos(level, mutable) || !config.ignoreVines) {
                  return i - 2;
               }
            }
         }
      }

      return trunkHeight;
   }

   protected void setBlock(LevelWriter world, BlockPos pos, BlockState state) {
      setBlockKnownShape(world, pos, state);
   }

   public static void setBlockKnownShape(LevelWriter p_236408_0_, BlockPos p_236408_1_, BlockState p_236408_2_) {
      p_236408_0_.setBlock(p_236408_1_, p_236408_2_, 19);
   }

   private static DiscreteVoxelShape updateLeaves(LevelAccessor p_67203_, BoundingBox p_67204_, Set<BlockPos> p_67205_, Set<BlockPos> p_67206_) {
      List<Set<BlockPos>> list = Lists.newArrayList();
      DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(p_67204_.getXSpan(), p_67204_.getYSpan(), p_67204_.getZSpan());
      int i = 6;

      for (int j = 0; j < 6; j++) {
         list.add(Sets.newHashSet());
      }

      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (BlockPos blockpos : Lists.newArrayList(p_67206_)) {
         if (p_67204_.isInside(blockpos)) {
            discretevoxelshape.fill(blockpos.getX() - p_67204_.minX(), blockpos.getY() - p_67204_.minY(), blockpos.getZ() - p_67204_.minZ());
         }
      }

      for (BlockPos blockpos1 : Lists.newArrayList(p_67205_)) {
         if (p_67204_.isInside(blockpos1)) {
            discretevoxelshape.fill(blockpos1.getX() - p_67204_.minX(), blockpos1.getY() - p_67204_.minY(), blockpos1.getZ() - p_67204_.minZ());
         }

         for (Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(blockpos1, direction);
            if (!p_67205_.contains(blockpos$mutableblockpos)) {
               BlockState blockstate = p_67203_.getBlockState(blockpos$mutableblockpos);
               if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
                  list.get(0).add(blockpos$mutableblockpos.immutable());
                  setBlockKnownShape(p_67203_, blockpos$mutableblockpos, (BlockState)blockstate.setValue(BlockStateProperties.DISTANCE, 1));
                  if (p_67204_.isInside(blockpos$mutableblockpos)) {
                     discretevoxelshape.fill(
                        blockpos$mutableblockpos.getX() - p_67204_.minX(),
                        blockpos$mutableblockpos.getY() - p_67204_.minY(),
                        blockpos$mutableblockpos.getZ() - p_67204_.minZ()
                     );
                  }
               }
            }
         }
      }

      for (int l = 1; l < 6; l++) {
         Set<BlockPos> set = list.get(l - 1);
         Set<BlockPos> set1 = list.get(l);

         for (BlockPos blockpos2 : set) {
            if (p_67204_.isInside(blockpos2)) {
               discretevoxelshape.fill(blockpos2.getX() - p_67204_.minX(), blockpos2.getY() - p_67204_.minY(), blockpos2.getZ() - p_67204_.minZ());
            }

            for (Direction direction1 : Direction.values()) {
               blockpos$mutableblockpos.setWithOffset(blockpos2, direction1);
               if (!set.contains(blockpos$mutableblockpos) && !set1.contains(blockpos$mutableblockpos)) {
                  BlockState blockstate1 = p_67203_.getBlockState(blockpos$mutableblockpos);
                  if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
                     int k = (Integer)blockstate1.getValue(BlockStateProperties.DISTANCE);
                     if (k > l + 1) {
                        BlockState blockstate2 = (BlockState)blockstate1.setValue(BlockStateProperties.DISTANCE, l + 1);
                        setBlockKnownShape(p_67203_, blockpos$mutableblockpos, blockstate2);
                        if (p_67204_.isInside(blockpos$mutableblockpos)) {
                           discretevoxelshape.fill(
                              blockpos$mutableblockpos.getX() - p_67204_.minX(),
                              blockpos$mutableblockpos.getY() - p_67204_.minY(),
                              blockpos$mutableblockpos.getZ() - p_67204_.minZ()
                           );
                        }

                        set1.add(blockpos$mutableblockpos.immutable());
                     }
                  }
               }
            }
         }
      }

      return discretevoxelshape;
   }

   public static boolean validTreePos(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES));
   }
}

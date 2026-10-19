package twilightforest.world.components.feature.trees.treeplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import twilightforest.init.TFFeatureModifiers;
import twilightforest.util.features.FeatureLogic;
import twilightforest.util.iterators.VoxelBresenhamIterator;

public class BranchingTrunkPlacer extends TrunkPlacer {
   public static final MapCodec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
      instance -> trunkPlacerParts(instance)
         .and(
            instance.group(
               Codec.intRange(0, 24).fieldOf("branch_start_offset_down").forGetter(o -> o.branchDownwardOffset),
               BranchesConfig.CODEC.fieldOf("branch_config").forGetter(o -> o.branchesConfig),
               Codec.BOOL.fieldOf("perpendicular_branches").forGetter(o -> o.perpendicularBranches),
               Codec.BOOL.fieldOf("prevent_exposed_root").forGetter(o -> o.preventExposedRoot)
            )
         )
         .apply(instance, BranchingTrunkPlacer::new)
   );
   private final int branchDownwardOffset;
   private final BranchesConfig branchesConfig;
   private final boolean perpendicularBranches;
   private final boolean preventExposedRoot;

   public BranchingTrunkPlacer(
      int baseHeight,
      int randomHeightA,
      int randomHeightB,
      int branchDownwardOffset,
      BranchesConfig branchesConfig,
      boolean perpendicularBranches,
      boolean preventExposedRoot
   ) {
      super(baseHeight, randomHeightA, randomHeightB);
      this.branchDownwardOffset = branchDownwardOffset;
      this.branchesConfig = branchesConfig;
      this.perpendicularBranches = perpendicularBranches;
      this.preventExposedRoot = preventExposedRoot;
   }

   protected TrunkPlacerType<BranchingTrunkPlacer> type() {
      return (TrunkPlacerType<BranchingTrunkPlacer>)TFFeatureModifiers.TRUNK_BRANCHING.get();
   }

   public List<FoliageAttachment> placeTrunk(
      LevelSimulatedReader worldReader,
      BiConsumer<BlockPos, BlockState> worldPlacer,
      RandomSource random,
      int height,
      BlockPos startPos,
      TreeConfiguration treeConfig
   ) {
      List<FoliageAttachment> leafAttachments = Lists.newArrayList();
      if (this.preventExposedRoot) {
         for (Direction direction : Plane.HORIZONTAL) {
            if (worldReader.isStateAtPosition(startPos.below().relative(direction), BlockStateBase::canBeReplaced)) {
               worldPlacer.accept(startPos.below(), Function.<BlockState>identity().apply(treeConfig.trunkProvider.getState(random, startPos.below())));
               break;
            }
         }
      }

      for (int y = 0; y <= height; y++) {
         if (!this.placeLog(worldReader, worldPlacer, random, startPos.above(y), treeConfig)) {
            height = y;
            break;
         }
      }

      leafAttachments.add(new FoliageAttachment(startPos.above(height), 0, false));
      int numBranches = this.branchesConfig.branchCount() + random.nextInt(this.branchesConfig.randomAddBranches() + 1);
      float offset = random.nextFloat();

      for (int b = 0; b < numBranches; b++) {
         this.buildBranch(
            worldReader,
            worldPlacer,
            startPos,
            leafAttachments,
            height - this.branchDownwardOffset + b,
            this.branchesConfig.length(),
            this.branchesConfig.spacingYaw() * b + offset,
            this.branchesConfig.downwardsPitch(),
            random,
            this.perpendicularBranches
         );
      }

      return leafAttachments;
   }

   private void buildBranch(
      LevelSimulatedReader worldReader,
      BiConsumer<BlockPos, BlockState> worldPlacer,
      BlockPos pos,
      List<FoliageAttachment> leafBlocks,
      int height,
      double length,
      double angle,
      double tilt,
      RandomSource treeRNG,
      boolean perpendicularBranches
   ) {
      BlockPos src = pos.above(height);
      BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);
      if (perpendicularBranches) {
         this.drawBresenhamBranch(worldReader, worldPlacer, treeRNG, src, new BlockPos(dest.getX(), src.getY(), dest.getZ()));
         int max = Math.max(src.getY(), dest.getY());

         for (int i = Math.min(src.getY(), dest.getY()); i < max + 1; i++) {
            this.placeWood(worldReader, worldPlacer, treeRNG, new BlockPos(dest.getX(), i, dest.getZ()));
         }
      } else {
         this.drawBresenhamBranch(worldReader, worldPlacer, treeRNG, src, dest);
      }

      this.placeWood(worldReader, worldPlacer, treeRNG, dest.east());
      this.placeWood(worldReader, worldPlacer, treeRNG, dest.west());
      this.placeWood(worldReader, worldPlacer, treeRNG, dest.south());
      this.placeWood(worldReader, worldPlacer, treeRNG, dest.north());
      leafBlocks.add(new FoliageAttachment(dest, 0, false));
   }

   private void drawBresenhamBranch(
      LevelSimulatedReader worldReader, BiConsumer<BlockPos, BlockState> worldPlacer, RandomSource random, BlockPos from, BlockPos to
   ) {
      for (BlockPos pixel : new VoxelBresenhamIterator(from, to)) {
         this.placeWood(worldReader, worldPlacer, random, pixel);
      }
   }

   protected boolean placeWood(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos) {
      return this.placeWood(level, blockSetter, random, pos, Function.identity());
   }

   protected boolean placeWood(
      LevelSimulatedReader level,
      BiConsumer<BlockPos, BlockState> blockSetter,
      RandomSource random,
      BlockPos pos,
      Function<BlockState, BlockState> propertySetter
   ) {
      if (this.validTreePos(level, pos)) {
         blockSetter.accept(pos, propertySetter.apply(this.branchesConfig.branchProvider().getState(random, pos)));
         return true;
      } else {
         return false;
      }
   }
}

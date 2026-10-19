package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

public class SpikeCaveDecorator implements ICaveDecorator {
   private final int bottomOfWorld;
   private final RandomSource random;
   private final List<BlockPos> spikePositions = new ArrayList<>();
   private final List<Vec3> baseBlocks = MathUtils.buildBlockCircle(4.2);

   public SpikeCaveDecorator(int bottomOfWorld, RandomSource random) {
      this.bottomOfWorld = bottomOfWorld;
      this.random = random;
   }

   @Override
   public void onBlockPlaced(BlockPos pos, Block block) {
      double spikeSpacing = Math.pow(this.random.nextInt(20) + 10.0, 2.0);
      if (pos.getY() == 5 + this.bottomOfWorld && block != Blocks.AIR && this.spikePositions.stream().allMatch(it -> it.distSqr(pos) > spikeSpacing)) {
         this.spikePositions.add(pos);
      }
   }

   @Override
   public void generate(
      WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, BlockPos pos, IStructurePiece structurePiece
   ) {
      Map<Pair<Integer, Integer>, List<BlockPos>> groupedSpikePositions = this.spikePositions
         .stream()
         .collect(Collectors.groupingBy(p -> new Pair(p.getX() >> 2, p.getZ() >> 2)));

      for (BlockPos outerPos : groupedSpikePositions.values().stream().map(list -> list.get(0)).toList()) {
         Vec3 centerDirection = VecUtils.planeProject(VecUtils.asVec3(pos.subtract(outerPos)), VecUtils.yAxis).normalize();
         Vec3 tip = centerDirection.scale(5 + random.nextInt(3)).add(VecUtils.yAxis.scale(7 + random.nextInt(5)));
         this.generateSpike(outerPos, outerPos.offset(BlockPos.containing(tip)), structurePiece, level, boundingBox, centerDirection);
      }
   }

   private void generateSpike(BlockPos origin, BlockPos tip, IStructurePiece structurePiece, WorldGenLevel level, BoundingBox boundingBox, Vec3 centerDirection) {
      Vec3 centerDirectionPos = centerDirection.scale(3.0);
      Set<BlockPos> blockSet = this.baseBlocks.stream().<BlockPos>map(BlockPos::containing).collect(Collectors.toSet());
      Set<BlockPos> innerBlockSet = blockSet.stream().filter(posx -> posx.distToCenterSqr(centerDirectionPos) < Math.pow(2.0, 2.0)).collect(Collectors.toSet());
      Set<BlockPos> middleBlockSet = blockSet.stream()
         .filter(block -> !innerBlockSet.contains(block))
         .filter(block -> block.distToCenterSqr(centerDirectionPos) < Math.pow(3.7, 2.0))
         .collect(Collectors.toSet());
      Set<BlockPos> outerBlockSet = blockSet.stream()
         .filter(posx -> !middleBlockSet.contains(posx) && !innerBlockSet.contains(posx))
         .collect(Collectors.toSet());

      for (BlockPos blockPos : innerBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.offset(origin), tip)) {
            BlockState blockState = this.random.nextInt(16) == 0 ? Blocks.BUDDING_AMETHYST.defaultBlockState() : Blocks.AMETHYST_BLOCK.defaultBlockState();
            structurePiece.placeBlock(level, blockState, pos, boundingBox);
         }
      }

      for (BlockPos blockPos : middleBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.offset(origin), tip)) {
            structurePiece.placeBlock(level, Blocks.CALCITE.defaultBlockState(), pos, boundingBox);
         }
      }

      for (BlockPos blockPos : outerBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.offset(origin), tip)) {
            structurePiece.placeBlock(level, Blocks.SMOOTH_BASALT.defaultBlockState(), pos, boundingBox);
         }
      }
   }
}

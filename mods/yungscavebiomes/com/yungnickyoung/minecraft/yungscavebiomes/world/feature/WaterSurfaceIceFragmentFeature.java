package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.CellularNoise;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.joml.Vector3f;

public class WaterSurfaceIceFragmentFeature extends Feature<NoneFeatureConfiguration> {
   private static final int MIN_MAX_FLOOD_RADIUS = 8;
   private static final int MAX_MAX_FLOOD_RADIUS = 12;
   private static final int EFFECTIVE_MAX_FLOOD_RADIUS = 11;
   private static final int EFFECTIVE_MAX_FLOOD_DIAMETER = 23;
   private static final int MAX_FLOOD_RADIUS_RANGE = 4;
   private static final float FLOOD_BOUNDARY_NOISE_FREQUENCY = 0.1F;
   private static final long FLOOD_BOUNDARY_NOISE_SEED_FLIP = -3496757360140900525L;
   private static final float ICE_FRAGMENT_SEPARATION = 0.15F;
   private static final float ICE_FRAGMENT_NOISE_FREQUENCY = 0.083333336F;
   private static final float DOMAIN_WARP_FREQUENCY = 0.083333336F;
   private static final float DOMAIN_WARP_AMPLITUDE = 4.0F;
   private static final long WARP_NOISE_SEED_FLIP = 3811143571666535007L;
   private static final long CELL_NOISE_SEED_FLIP = 6534415494888897672L;
   private static final ThreadLocal<WaterSurfaceIceFragmentFeature.IceFragmentFiller> iceFragmentFillerThreadLocal = ThreadLocal.withInitial(
      WaterSurfaceIceFragmentFeature.IceFragmentFiller::new
   );

   public WaterSurfaceIceFragmentFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      iceFragmentFillerThreadLocal.get().execute(origin, level);
      return false;
   }

   private static class IceFragmentFiller {
      private final Queue<BlockPos> floodQueue = new ArrayDeque<>(529);
      private final boolean[] visited = new boolean[529];
      private final Vector3f domainWarpVector = new Vector3f();

      public IceFragmentFiller() {
      }

      public void execute(BlockPos origin, WorldGenLevel level) {
         long seed = level.getSeed();
         long noiseSeedWarp = seed ^ 3811143571666535007L;
         long noiseSeedCell = seed ^ 6534415494888897672L;
         long noiseSeedFlood = seed ^ -3496757360140900525L;
         this.floodQueue.clear();
         Arrays.fill(this.visited, false);
         this.floodQueue.add(origin);

         while (!this.floodQueue.isEmpty()) {
            BlockPos currentPos = this.floodQueue.poll();
            BlockState currentState = level.getBlockState(currentPos);
            if (currentState.is(Blocks.WATER) || currentState.is(Blocks.ICE)) {
               OpenSimplex2S.vec3Noise3_ImproveXZ(
                  noiseSeedWarp, currentPos.getX() * 0.083333336F, currentPos.getY() * 0.083333336F, currentPos.getZ() * 0.083333336F, this.domainWarpVector
               );
               float iceShardNoiseValue = CellularNoise.sampleDistance2Sub_ImproveXZ(
                  noiseSeedCell,
                  currentPos.getX() * 0.083333336F + this.domainWarpVector.x() * 0.33333334F,
                  currentPos.getY() * 0.083333336F + this.domainWarpVector.y() * 0.33333334F,
                  currentPos.getZ() * 0.083333336F + this.domainWarpVector.z() * 0.33333334F
               );
               if (!(iceShardNoiseValue <= 0.15F)) {
                  level.setBlock(currentPos, Blocks.ICE.defaultBlockState(), 2);
                  this.considerNeighbor(noiseSeedFlood, origin, currentPos.east());
                  this.considerNeighbor(noiseSeedFlood, origin, currentPos.west());
                  this.considerNeighbor(noiseSeedFlood, origin, currentPos.south());
                  this.considerNeighbor(noiseSeedFlood, origin, currentPos.north());
               }
            }
         }
      }

      private void considerNeighbor(long noiseSeedFlood, BlockPos origin, BlockPos neighborPos) {
         int dx = neighborPos.getX() - origin.getX();
         int dz = neighborPos.getZ() - origin.getZ();
         if (dx >= -11 && dx <= 11 && dz >= -11 && dz <= 11) {
            int visitedIndex = (dz + 11) * 23 + dx + 11;
            if (!this.visited[visitedIndex]) {
               this.visited[visitedIndex] = true;
               float distSq = dx * dx + dz * dz;
               if (!(distSq > 121.0F)) {
                  if (distSq > 64.0F) {
                     float noiseValue = OpenSimplex2S.noise3_ImproveXZ(
                        noiseSeedFlood, (origin.getX() + dx) * 0.1F, origin.getY() * 0.1F, (origin.getZ() + dz) * 0.1F
                     );
                     float noisyRadius = noiseValue * 2.0F + 10.0F;
                     if (distSq > noisyRadius * noisyRadius) {
                        return;
                     }
                  }

                  this.floodQueue.add(neighborPos);
               }
            }
         }
      }
   }
}

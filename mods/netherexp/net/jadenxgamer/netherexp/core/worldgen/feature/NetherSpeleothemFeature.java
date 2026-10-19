package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NetherSpeleothemFeature extends Feature<NoneFeatureConfiguration> {
   private static final int MAX_VERTICAL_DEVIATION = 8;

   public NetherSpeleothemFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      int maxScan = 64;
      BlockPos centerCeiling = this.findSurface(level, origin, Direction.UP, maxScan);
      BlockPos centerFloor = this.findSurface(level, origin, Direction.DOWN, maxScan);
      if (centerCeiling != null && centerFloor != null) {
         int minY = centerFloor.getY();
         int maxY = centerCeiling.getY();
         int totalHeight = maxY - minY;
         if (totalHeight < 4) {
            return false;
         }

         float baseRadius = Mth.clamp(totalHeight / 10.0F + 1.0F, 1.5F, 7.0F);
         int scanRadius = Mth.ceil(baseRadius * 2.5F);
         List<NetherSpeleothemFeature.ColumnData> validColumns = new ArrayList<>();
         MutableBlockPos cursor = new MutableBlockPos();
         boolean anyColumnOutOfRange = false;

         for (int x = -scanRadius; x <= scanRadius; x++) {
            for (int z = -scanRadius; z <= scanRadius; z++) {
               double distSq = x * x + z * z;
               double dist = Math.sqrt(distSq);
               if (!(dist > baseRadius * 2.5)) {
                  cursor.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
                  BlockPos localCeiling = this.findSurface(level, cursor, Direction.UP, maxScan);
                  BlockPos localFloor = this.findSurface(level, cursor, Direction.DOWN, maxScan);
                  if (localCeiling == null || localFloor == null) {
                     anyColumnOutOfRange = true;
                     break;
                  }

                  int localMinY = localFloor.getY();
                  int localMaxY = localCeiling.getY();
                  boolean floorTooLow = localMinY < minY - 8;
                  boolean ceilingTooHigh = localMaxY > maxY + 8;
                  if (floorTooLow || ceilingTooHigh) {
                     anyColumnOutOfRange = true;
                     break;
                  }

                  validColumns.add(new NetherSpeleothemFeature.ColumnData(x, z, localMinY, localMaxY, dist));
               }
            }

            if (anyColumnOutOfRange) {
               break;
            }
         }

         if (!anyColumnOutOfRange && !validColumns.isEmpty()) {
            for (NetherSpeleothemFeature.ColumnData column : validColumns) {
               int x = column.x;
               int z = column.z;
               int localMinY = column.localMinY;
               int localMaxY = column.localMaxY;
               double distance = column.distance;

               for (int y = localMinY; y <= localMaxY; y++) {
                  float relativeY = (float)(y - minY) / totalHeight;
                  float taperFactor = 2.0F * (relativeY - 0.5F);
                  float hourglassMultiplier = 1.0F + taperFactor * taperFactor * 0.8F;
                  int distToSurface = Math.min(y - localMinY, localMaxY - y);
                  float meltStrength = 4.0F / (distToSurface + 1.0F);
                  double noise = this.getImperfectionNoise(origin.getX() + x, y, origin.getZ() + z);
                  double targetRadius = baseRadius * hourglassMultiplier - hourglassMultiplier * 0.5;
                  double blendBonus = meltStrength * 1.5;
                  double noiseBonus = noise * 1.2;
                  double maxRadiusHere = targetRadius + blendBonus + noiseBonus;
                  if (distance < maxRadiusHere) {
                     cursor.set(origin.getX() + x, y, origin.getZ() + z);
                     if (this.canReplace(level, cursor)) {
                        this.setBlock(level, cursor, Blocks.NETHERRACK.defaultBlockState());
                     }
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private BlockPos findSurface(WorldGenLevel level, BlockPos start, Direction direction, int maxDistance) {
      MutableBlockPos mPos = start.mutable();

      for (int i = 0; i < maxDistance; i++) {
         BlockState state = level.getBlockState(mPos);
         if (state.is(BlockTags.BASE_STONE_NETHER) || state.is(JNETags.Blocks.SPELEOTHEM_BASE_BLOCKS)) {
            return mPos.immutable();
         }

         mPos.move(direction);
      }

      return null;
   }

   private boolean canReplace(WorldGenLevel level, BlockPos pos) {
      return true;
   }

   private double getImperfectionNoise(int x, int y, int z) {
      double n1 = Mth.sin(x * 0.3F) * Mth.cos(y * 0.3F) * Mth.sin(z * 0.3F);
      double n2 = Mth.cos(x * 0.1F + y * 0.05F) * Mth.sin(z * 0.1F);
      return n1 + n2;
   }

   private record ColumnData(int x, int z, int localMinY, int localMaxY, double distance) {
   }
}

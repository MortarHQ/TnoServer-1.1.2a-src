package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class PillarRockFeature extends Feature<SimpleBlockConfiguration> {
   private static final int RADIUS_MIN = 5;
   private static final int RADIUS_MAX = 10;
   private static final float HEIGHT_RELATIVE_TO_RADIUS_MIN = 1.5F;
   private static final float HEIGHT_RELATIVE_TO_RADIUS_MAX = 2.0F;
   private static final float TOP_HEIGHT_PROPORTION = 0.3F;
   private static final float TOP_START_HEMISPHERE_OFFSET_MIN = 0.875F;
   private static final float TOP_START_HEMISPHERE_OFFSET_MAX = 0.9375F;
   private static final float BOTTOM_START_HEMISPHERE_OFFSET_MIN = 0.25F;
   private static final float BOTTOM_START_HEMISPHERE_OFFSET_MAX = 0.5F;
   private static final double NOISE_FREQUENCY_XZ = 0.1;
   private static final double NOISE_FREQUENCY_Y = 0.08333333333333334;
   public static final float NOISE_MODULATION_AMOUNT = 0.6F;
   private static final long NOISE_SEED_FLIP_MASK = 4187418520378946841L;

   public PillarRockFeature(Codec<SimpleBlockConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      SimpleBlockConfiguration config = (SimpleBlockConfiguration)context.config();
      BlockState state = config.toPlace().getState(random, context.origin());
      long noiseSeed = level.getSeed() ^ 4187418520378946841L;
      float radXZ = Mth.square(random.nextFloat()) * 5.0F + 5.0F;
      float height = Mth.nextFloat(random, 1.5F, 2.0F) * radXZ;
      float topHeight = radXZ * 0.3F;
      float bottomHeight = height - topHeight;
      float creasePositionY = height * 0.5F - topHeight;
      float topStartHemisphereOffset = Mth.nextFloat(random, 0.875F, 0.9375F);
      float bottomStartHemisphereOffset = Mth.nextFloat(random, 0.25F, 0.5F);
      float dyTopMultiplier = (1.0F - topStartHemisphereOffset) / topHeight;
      float dyBottomMultiplier = (bottomStartHemisphereOffset - 1.0F) / bottomHeight;
      float dyTopOffset = topStartHemisphereOffset - dyTopMultiplier * creasePositionY;
      float dyBottomOffset = bottomStartHemisphereOffset - dyBottomMultiplier * creasePositionY;
      float dySqTopAtCrease = Mth.square(creasePositionY * dyTopMultiplier + dyTopOffset);
      float dySqBottomAtCrease = Mth.square(creasePositionY * dyBottomMultiplier + dyBottomOffset);
      float topStartDistSqXZCorrection = (1.0F - dySqTopAtCrease) / (radXZ * radXZ);
      float bottomStartDistSqXZCorrection = (1.0F - dySqBottomAtCrease) / (radXZ * radXZ);
      float falloffCurveTopMultiplier = -1.0F / (1.0F - dySqTopAtCrease);
      float falloffCurveBottomMultiplier = -1.0F / (1.0F - dySqBottomAtCrease);
      float falloffCurveTopOffset = 1.0F - falloffCurveTopMultiplier * dySqTopAtCrease;
      float falloffCurveBottomOffset = 1.0F - falloffCurveBottomMultiplier * dySqBottomAtCrease;
      int radYBound = Mth.ceil(height * 0.5F);
      int radXZBound = Mth.ceil(radXZ);

      for (int dy = -radYBound; dy <= radYBound; dy++) {
         boolean isTop = dy >= creasePositionY;
         float dySq = isTop ? Mth.square(dy * dyTopMultiplier + dyTopOffset) : Mth.square(dy * dyBottomMultiplier + dyBottomOffset);

         for (int dz = -radXZBound; dz <= radXZBound; dz++) {
            for (int dx = -radXZBound; dx <= radXZBound; dx++) {
               float distSqXZ = (dx * dx + dz * dz) * (isTop ? topStartDistSqXZCorrection : bottomStartDistSqXZCorrection);
               float distSq = distSqXZ + dySq;
               float baseDensityHere = isTop
                  ? distSq * falloffCurveTopMultiplier + falloffCurveTopOffset
                  : distSq * falloffCurveBottomMultiplier + falloffCurveBottomOffset;
               boolean isInRange = baseDensityHere >= 0.6F;
               if (!isInRange) {
                  float noiseHere = OpenSimplex2S.noise3_ImproveXZ(
                           noiseSeed, (dx + origin.getX()) * 0.1, (dy + origin.getY()) * 0.08333333333333334, (dz + origin.getZ()) * 0.1
                        )
                        * 0.3F
                     + 0.3F;
                  float densityHere = baseDensityHere - noiseHere;
                  isInRange = densityHere > 0.0F;
               }

               if (isInRange) {
                  BlockPos local = origin.offset(dx, dy, dz);
                  level.setBlock(local, state, 3);
               }
            }
         }
      }

      return false;
   }
}

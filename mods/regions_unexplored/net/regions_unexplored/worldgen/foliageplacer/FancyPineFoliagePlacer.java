package net.regions_unexplored.worldgen.foliageplacer;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;

public class FancyPineFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<FancyPineFoliagePlacer> CODEC = IntProvider.CODEC.fieldOf("offset").xmap(FancyPineFoliagePlacer::new, p -> p.offset);
   public static final FoliagePlacerType<FancyPineFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public FancyPineFoliagePlacer(IntProvider offset) {
      this(ConstantInt.of(0), offset, 0);
   }

   public FancyPineFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
      super(radius, offset, height);
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }

   protected void createFoliage(
      LevelSimulatedReader level,
      FoliageSetter foliageSetter,
      RandomSource random,
      TreeConfiguration config,
      int treeHeight,
      FoliageAttachment foliageAttachment,
      int foliageHeight,
      int leafRadius,
      int offset
   ) {
      RUFoliagePlacerUtils.Context context = new RUFoliagePlacerUtils.Context(
         level, foliageSetter, random, config.foliageProvider, foliageAttachment.pos(), offset
      );
      RUFoliagePlacerUtils.placeSquare(context, 0, 2, false);
      RUFoliagePlacerUtils.placeSquare(context, 0, 1, false);
      RUFoliagePlacerUtils.placeDiamond(context, 1, 0, false);
      if (random.nextBoolean()) {
         RUFoliagePlacerUtils.placeDiamond(context, 1, -1, false);
         RUFoliagePlacerUtils.placeSquare(context, 1, -2, false);
         RUFoliagePlacerUtils.placeDiamond(context, 2, -3, false);
         RUFoliagePlacerUtils.placeDiamond(context, 1, -4, false);
         RUFoliagePlacerUtils.placeDiamond(context, 2, -5, false);
         RUFoliagePlacerUtils.placeSquare(context, 2, -6, false, 0.0F);
         if (random.nextFloat() < 0.3) {
            RUFoliagePlacerUtils.placeDiamond(context, 3, 4, -7, false);
         } else {
            RUFoliagePlacerUtils.placeSquare(context, 1, -7, false);
         }

         RUFoliagePlacerUtils.placeDiamond(context, 1, -8, false);
      } else {
         RUFoliagePlacerUtils.placeSquare(context, 0, -1, false);
         RUFoliagePlacerUtils.placeDiamond(context, 1, -2, false);
         RUFoliagePlacerUtils.placeSquare(context, 1, -3, false);
         RUFoliagePlacerUtils.placeDiamond(context, 2, -4, false);
         RUFoliagePlacerUtils.placeDiamond(context, 1, -5, false);
         RUFoliagePlacerUtils.placeSquare(context, 2, -6, false, 0.0F);
         if (random.nextFloat() < 0.3) {
            RUFoliagePlacerUtils.placeDiamond(context, 3, 4, -7, false);
         } else {
            RUFoliagePlacerUtils.placeSquare(context, 2, -7, false);
         }

         RUFoliagePlacerUtils.placeDiamond(context, 1, -8, false);
      }
   }

   protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int currentRadius, boolean doubleTrunk) {
      return dx == currentRadius && dz == currentRadius && currentRadius != 0;
   }
}

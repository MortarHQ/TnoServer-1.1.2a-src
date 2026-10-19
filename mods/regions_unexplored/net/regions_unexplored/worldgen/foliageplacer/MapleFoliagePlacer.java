package net.regions_unexplored.worldgen.foliageplacer;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;

public class MapleFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<MapleFoliagePlacer> CODEC = MapCodec.unit(MapleFoliagePlacer::new);
   public static FoliagePlacerType<MapleFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public MapleFoliagePlacer() {
      super(ConstantInt.ZERO, ConstantInt.ZERO, 0);
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
      BlockPos origin = foliageAttachment.pos();
      RUFoliagePlacerUtils.Context context = new RUFoliagePlacerUtils.Context(level, foliageSetter, random, config.foliageProvider, origin, offset);
      RUFoliagePlacerUtils.placeSquare(context, 0, 1, false);
      RUFoliagePlacerUtils.placeSquare(context, 1, 0, false, 0.4F);
      RUFoliagePlacerUtils.placeSquare(context, 1, -1, false, 0.4F);
      RUFoliagePlacerUtils.placeSquare(context, 1, -2, false);

      for (Direction direction : Plane.HORIZONTAL) {
         RUFoliagePlacerUtils.placeSingle(context, origin.below(2).relative(direction, 2), 0.5F);
      }

      RUFoliagePlacerUtils.placeSquare(context, 2, -3, false, 0.5F);
      RUFoliagePlacerUtils.placeSquare(context, 2, -4, false, 0.5F);
      RUFoliagePlacerUtils.placeDiamond(context, 1, -5, false);
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }
}

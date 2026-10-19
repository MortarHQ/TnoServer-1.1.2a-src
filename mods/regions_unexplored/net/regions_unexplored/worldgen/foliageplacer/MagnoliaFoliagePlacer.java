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

public class MagnoliaFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<MagnoliaFoliagePlacer> CODEC = MapCodec.unit(MagnoliaFoliagePlacer::new);
   public static FoliagePlacerType<MagnoliaFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public MagnoliaFoliagePlacer() {
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
      RUFoliagePlacerUtils.placeSquare(context, 3, 1, false, 0.0F);
      RUFoliagePlacerUtils.placeSquare(context, 1, 2, false);
      RUFoliagePlacerUtils.placeSquare(context, 1, 0, false);
      boolean clockwise = random.nextBoolean();

      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos pos = origin.above().relative(direction, 2);
         RUFoliagePlacerUtils.placeSingle(context, pos.below());
         RUFoliagePlacerUtils.placeSingle(context, pos.above());
         RUFoliagePlacerUtils.placeSingle(context, pos.below().relative(clockwise ? direction.getClockWise() : direction.getCounterClockWise()));
         RUFoliagePlacerUtils.placeSingle(context, pos.above().relative(clockwise ? direction.getClockWise() : direction.getCounterClockWise()));
      }
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }
}

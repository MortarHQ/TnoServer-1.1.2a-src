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

public class AspenFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<AspenFoliagePlacer> CODEC = MapCodec.unit(AspenFoliagePlacer::new);
   public static FoliagePlacerType<AspenFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public AspenFoliagePlacer() {
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
      RUFoliagePlacerUtils.placeSquare(context, 0, 2, false);

      for (Direction direction : Plane.HORIZONTAL) {
         RUFoliagePlacerUtils.placeSingle(context, origin.above(2).relative(direction), 0.5F);
      }

      RUFoliagePlacerUtils.placeDiamond(context, 1, 1, false);
      RUFoliagePlacerUtils.placeSquare(context, 1, 0, false);
      RUFoliagePlacerUtils.placeDiamond(context, 2, -1, false);
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }
}

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

public class RedwoodFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<RedwoodFoliagePlacer> CODEC = IntProvider.CODEC.fieldOf("offset").xmap(RedwoodFoliagePlacer::new, p -> p.offset);
   public static final FoliagePlacerType<RedwoodFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public RedwoodFoliagePlacer(IntProvider offset) {
      this(ConstantInt.of(1), offset, 1);
   }

   public RedwoodFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
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
      RUFoliagePlacerUtils.placeDiamond(context, 1, -1, false);
      RUFoliagePlacerUtils.placeDiamond(context, 2, 0, false);
      RUFoliagePlacerUtils.placeSquare(context, 1, 1, false, 0.33F);
      RUFoliagePlacerUtils.placeDiamond(context, 1, 2, false);
      RUFoliagePlacerUtils.placeSquare(context, 0, 3, false);
      RUFoliagePlacerUtils.placeSquare(context, 0, 4, false);
   }
}

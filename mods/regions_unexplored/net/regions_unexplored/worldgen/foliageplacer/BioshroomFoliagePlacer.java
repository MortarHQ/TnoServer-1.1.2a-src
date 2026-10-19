package net.regions_unexplored.worldgen.foliageplacer;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BioshroomFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<BioshroomFoliagePlacer> CODEC = BlockStateProvider.CODEC
      .fieldOf("secondary_provider")
      .xmap(BioshroomFoliagePlacer::new, BioshroomFoliagePlacer::secondaryProvider);
   public static final FoliagePlacerType<BioshroomFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);
   private final BlockStateProvider secondaryProvider;

   public BioshroomFoliagePlacer(BlockStateProvider secondaryProvider) {
      super(ConstantInt.ZERO, ConstantInt.ZERO, 0);
      this.secondaryProvider = secondaryProvider;
   }

   public BlockStateProvider secondaryProvider() {
      return this.secondaryProvider;
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
      BlockPos origin = foliageAttachment.pos();
      RUFoliagePlacerUtils.Context context = new RUFoliagePlacerUtils.Context(level, foliageSetter, random, config.foliageProvider, origin, offset);
      if (random.nextBoolean()) {
         RUFoliagePlacerUtils.placeSquare(context, 1, 0, false);
      } else {
         RUFoliagePlacerUtils.placeSingle(context, origin);
         RUFoliagePlacerUtils.placeSingle(context, origin.north(), this.secondaryProvider);
         RUFoliagePlacerUtils.placeSingle(context, origin.north().east());
         RUFoliagePlacerUtils.placeSingle(context, origin.east(), this.secondaryProvider);
         RUFoliagePlacerUtils.placeSingle(context, origin.east().south());
         RUFoliagePlacerUtils.placeSingle(context, origin.south(), this.secondaryProvider);
         RUFoliagePlacerUtils.placeSingle(context, origin.south().west());
         RUFoliagePlacerUtils.placeSingle(context, origin.west(), this.secondaryProvider);
         RUFoliagePlacerUtils.placeSingle(context, origin.west().north());
         RUFoliagePlacerUtils.placeDiamond(context, 1, 1, false);
      }
   }
}

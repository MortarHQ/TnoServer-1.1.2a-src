package net.regions_unexplored.worldgen.foliageplacer;

import com.mojang.serialization.Codec;
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

public class WillowFoliagePlacer extends BlobFoliagePlacer {
   public static final MapCodec<WillowFoliagePlacer> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("hanging_leaves_chance")
      .xmap(WillowFoliagePlacer::new, WillowFoliagePlacer::hangingLeavesChance);
   public static FoliagePlacerType<WillowFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);
   private final float hangingLeavesChance;

   public WillowFoliagePlacer(float hangingLeavesChance) {
      super(ConstantInt.ZERO, ConstantInt.ZERO, 0);
      this.hangingLeavesChance = hangingLeavesChance;
   }

   public float hangingLeavesChance() {
      return this.hangingLeavesChance;
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
      RUFoliagePlacerUtils.placeDiamond(context, 1, 2, false);
      RUFoliagePlacerUtils.placeDiamond(context, 2, 1, false);

      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos pos = origin.above().relative(direction, 2);
         RUFoliagePlacerUtils.placeSingle(context, pos.relative(direction.getClockWise()), 0.3F);
         RUFoliagePlacerUtils.placeSingle(context, pos.relative(direction.getCounterClockWise()), 0.3F);
      }

      RUFoliagePlacerUtils.placeSquare(context, 2, 0, false, 0.0F);

      for (int x = -2; x <= 2; x++) {
         for (int z = -2; z <= 2; z++) {
            boolean xEdge = Math.abs(x) == 2;
            boolean zEdge = Math.abs(z) == 2;
            if (xEdge != zEdge && !(this.hangingLeavesChance < random.nextFloat())) {
               BlockPos pos = origin.offset(x, -1, z);
               int length = random.nextInt(2) + 1;

               for (int i = 0; i < length; i++) {
                  RUFoliagePlacerUtils.placeSingle(context, pos.below(i));
               }
            }
         }
      }
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }
}

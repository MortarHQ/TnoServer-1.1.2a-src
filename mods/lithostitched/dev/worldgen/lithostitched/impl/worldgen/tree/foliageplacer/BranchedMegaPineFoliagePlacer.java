package dev.worldgen.lithostitched.impl.worldgen.tree.foliageplacer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;

public class BranchedMegaPineFoliagePlacer extends MegaPineFoliagePlacer {
   public static final MapCodec<BranchedMegaPineFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
      i -> foliagePlacerParts(i)
         .and(IntProvider.codec(0, 24).fieldOf("crown_height").forGetter(placer -> placer.crownHeight))
         .apply(i, BranchedMegaPineFoliagePlacer::new)
   );
   public static final FoliagePlacerType<BranchedMegaPineFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public BranchedMegaPineFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
      super(radius, offset, crownHeight);
   }

   protected FoliagePlacerType<?> type() {
      return TYPE;
   }

   protected void createFoliage(
      LevelSimulatedReader level,
      FoliageSetter blockSetter,
      RandomSource random,
      TreeConfiguration config,
      int maxFreeTreeHeight,
      FoliageAttachment attachment,
      int foliageHeight,
      int foliageRadius,
      int offset
   ) {
      BlockPos blockpos = attachment.pos();
      int i = 0;
      boolean lastPlaced = false;

      for (int j = blockpos.getY() - foliageHeight + offset; j <= blockpos.getY() + offset; j++) {
         int k = blockpos.getY() - j;
         int l = foliageRadius + attachment.radiusOffset() + Mth.floor((float)k / foliageHeight * 3.5F);
         int i1;
         if (k > 0 && l == i && (j & 1) == 0) {
            i1 = l + 1;
         } else {
            i1 = l;
         }

         if (i1 > 2 && !lastPlaced) {
            int maxLength = i1 - 2;

            for (int length = 0; length < maxLength; length++) {
               BlockPos pos = blockpos.offset(-1 - length, j - blockpos.getY(), random.nextInt(2));
               blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.X));
               pos = blockpos.offset(random.nextInt(2), j - blockpos.getY(), -1 - length);
               blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z));
               pos = blockpos.offset(2 + length, j - blockpos.getY(), random.nextInt(2));
               blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.X));
               pos = blockpos.offset(random.nextInt(2), j - blockpos.getY(), 2 + length);
               blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z));
            }

            lastPlaced = true;
         } else {
            lastPlaced = false;
         }

         this.placeLeavesRow(level, blockSetter, random, config, new BlockPos(blockpos.getX(), j, blockpos.getZ()), i1, 0, attachment.doubleTrunk());
         i = l;
      }
   }
}

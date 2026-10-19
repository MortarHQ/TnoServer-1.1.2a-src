package dev.worldgen.lithostitched.impl.worldgen.tree.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageSetter;

public class BranchedMegaJungleFoliagePlacer extends MegaJungleFoliagePlacer {
   public static final MapCodec<BranchedMegaJungleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
      i -> foliagePlacerParts(i).and(Codec.intRange(0, 16).fieldOf("height").forGetter(placer -> placer.height)).apply(i, BranchedMegaJungleFoliagePlacer::new)
   );
   public static final FoliagePlacerType<BranchedMegaJungleFoliagePlacer> TYPE = new FoliagePlacerType(CODEC);

   public BranchedMegaJungleFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
      super(radius, offset, height);
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
      int i = attachment.doubleTrunk() ? foliageHeight : 1 + random.nextInt(2);
      BlockPos blockpos = attachment.pos();
      if (foliageRadius + 3 > 2 && attachment.doubleTrunk()) {
         int heightOffset = 0;

         for (int length = 0; length < foliageRadius + 1; length++) {
            if (length == foliageRadius) {
               heightOffset = 1;
            }

            BlockPos pos = blockpos.offset(-1 - length, offset - i - 1 + heightOffset, random.nextInt(2));
            blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.X));
            pos = blockpos.offset(random.nextInt(2), offset - i - 1 + heightOffset, -1 - length);
            blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z));
            pos = blockpos.offset(2 + length, offset - i - 1 + heightOffset, random.nextInt(2));
            blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.X));
            pos = blockpos.offset(random.nextInt(2), offset - i - 1 + heightOffset, 2 + length);
            blockSetter.set(pos, (BlockState)config.trunkProvider.getState(random, pos).trySetValue(BlockStateProperties.AXIS, Axis.Z));
         }
      }

      int modFoliageRadius = attachment.doubleTrunk() ? foliageRadius : Math.min(foliageRadius, 2);

      for (int j = offset; j >= offset - i; j--) {
         int k = modFoliageRadius + attachment.radiusOffset() + 1 - j;
         this.placeLeavesRow(level, blockSetter, random, config, attachment.pos(), k, j, attachment.doubleTrunk());
      }
   }

   protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
      return localX * localX + localZ * localZ > range * range;
   }
}

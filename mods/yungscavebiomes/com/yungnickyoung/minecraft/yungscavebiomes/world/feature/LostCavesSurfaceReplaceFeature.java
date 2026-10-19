package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LostCavesSurfaceReplaceFeature extends Feature<NoneFeatureConfiguration> {
   private static final double NOISE_FREQUENCY_XZ = 0.15;
   private static final double NOISE_FREQUENCY_Y = 0.15;
   private static final long NOISE_SEED_FLIP_MASK = -7005860059682304583L;
   private static final int RADIUS_MIN = 10;
   private static final int RADIUS_MAX = 16;

   public LostCavesSurfaceReplaceFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      long noiseSeed = context.level().getSeed() ^ -7005860059682304583L;
      MutableBlockPos mutableBlockPos = new MutableBlockPos();
      List<Block> matches = ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE, (Block)BlockModule.LAYERED_ANCIENT_SANDSTONE.get());

      for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(origin, noiseSeed, 0.15, 0.15, 10, 16)) {
         if (matches.contains(level.getBlockState(here).getBlock())) {
            boolean placed = false;

            for (int yOffset = 1; yOffset <= 6; yOffset++) {
               mutableBlockPos.set(here).move(Direction.UP, yOffset);
               BlockState replacement = yOffset <= 3
                  ? ((Block)BlockModule.ANCIENT_SAND.get()).defaultBlockState()
                  : ((Block)BlockModule.ANCIENT_SANDSTONE.get()).defaultBlockState();
               if (level.getBlockState(mutableBlockPos).isAir()) {
                  level.setBlock(here, replacement, 3);
                  placed = true;
                  break;
               }
            }

            if (!placed) {
               level.setBlock(here, ((Block)BlockModule.LAYERED_ANCIENT_SANDSTONE.get()).defaultBlockState(), 3);
            }
         }
      }

      return true;
   }
}

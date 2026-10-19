package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class IceSheetFeature extends Feature<IceSheetConfiguration> {
   public IceSheetFeature(Codec<IceSheetConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<IceSheetConfiguration> featurePlaceContext) {
      WorldGenLevel worldGenLevel = featurePlaceContext.level();
      BlockPos originPos = featurePlaceContext.origin();
      IceSheetConfiguration config = (IceSheetConfiguration)featurePlaceContext.config();
      int radius = config.searchRange;
      MutableBlockPos blockPos = originPos.mutable();
      MutableBlockPos neighborPos = blockPos.mutable();

      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               double dx = (double)x / radius;
               double dy = (double)y / radius;
               double dz = (double)z / radius;
               if (!(dx * dx + dy * dy + dz * dz > 1.0)) {
                  blockPos.setWithOffset(originPos, x, y, z);
                  BlockState blockState = worldGenLevel.getBlockState(blockPos);
                  if (isAirOrWater(blockState)) {
                     for (Direction direction : config.validDirections) {
                        neighborPos.setWithOffset(blockPos, direction);
                        BlockState neighborBlockState = worldGenLevel.getBlockState(neighborPos);
                        if (!neighborBlockState.is(BlockModule.ICE_SHEET_FEATURE_AVOID)) {
                           IceSheetBlock iceSheetBlock = (IceSheetBlock)BlockModule.ICE_SHEET.get();
                           BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(blockState, worldGenLevel, blockPos, direction);
                           if (updatedBlockState != null) {
                              blockState = updatedBlockState;
                           }
                        }
                     }

                     if (blockState.is((Block)BlockModule.ICE_SHEET.get())) {
                        blockState = (BlockState)blockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 3);
                        worldGenLevel.setBlock(blockPos, blockState, 3);
                        worldGenLevel.getChunk(blockPos).markPosForPostprocessing(blockPos);
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   private static boolean isAirOrWater(BlockState blockState) {
      return blockState.isAir() || blockState.is(Blocks.WATER);
   }
}

package dev.denismasterherobrine.afterdark.features;

import com.mojang.serialization.Codec;
import dev.denismasterherobrine.afterdark.features.configuration.SpiralConfiguration;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SpiralFeature extends Feature<SpiralConfiguration> {
   public SpiralFeature(Codec<SpiralConfiguration> pContext) {
      super(pContext);
   }

   public boolean place(FeaturePlaceContext<SpiralConfiguration> pContext) {
      WorldGenLevel worldgenlevel = pContext.level();
      BlockPos blockpos = pContext.origin();
      RandomSource random = pContext.random();
      SpiralConfiguration config = (SpiralConfiguration)pContext.config();
      Set<Block> validBlocks = config.validBlocks;
      Block stemMaterial = config.stemMaterial.getBlock();
      Block leafMaterial = config.leafMaterial.getBlock();
      Integer blobMass = config.getBlobMass().sample(random);
      Integer blobWidth = config.getBlobWidth().sample(random);
      Integer blobHeight = config.getBlobHeight().sample(random);
      if (!worldgenlevel.isEmptyBlock(blockpos) && validBlocks.contains(worldgenlevel.getBlockState(blockpos.above()).getBlock().defaultBlockState())) {
         worldgenlevel.setBlock(blockpos, stemMaterial.defaultBlockState(), 2);
         BlockPos blockpos1 = blockpos;
         boolean northNegative = false;
         boolean eastNegative = false;
         int randomNumber = (int)(Math.random() * 4.0 + 1.0);
         if (randomNumber >= 4) {
            northNegative = true;
            eastNegative = true;
         } else if (randomNumber >= 3) {
            northNegative = true;
         } else if (randomNumber >= 2) {
            eastNegative = true;
         }

         int xFactor = 1;
         int zFactor = 1;
         int spiralStep = 1;
         if (northNegative) {
            xFactor = -1;
         }

         if (eastNegative) {
            zFactor = -1;
         }

         for (int i = 0; i < blobMass * 4; i += 3) {
            int randomNumber2 = (int)(Math.random() * 4.0 + 1.0);
            if (randomNumber2 >= 4 / blobHeight) {
               blockpos1 = new BlockPos(blockpos1.getX() + xFactor, blockpos1.getY() - 1, blockpos1.getZ() + zFactor);
            } else {
               blockpos1 = new BlockPos(blockpos1.getX(), blockpos1.getY() - 1, blockpos1.getZ());
            }

            worldgenlevel.setBlock(blockpos1, stemMaterial.defaultBlockState(), 2);
            worldgenlevel.setBlock(blockpos1.below(), stemMaterial.defaultBlockState(), 2);
            worldgenlevel.setBlock(blockpos1.above(), stemMaterial.defaultBlockState(), 2);
            if (spiralStep == 1) {
               worldgenlevel.setBlock(blockpos1.north(), stemMaterial.defaultBlockState(), 2);
               worldgenlevel.setBlock(blockpos1.east(), stemMaterial.defaultBlockState(), 2);
            } else if (spiralStep == 2) {
               worldgenlevel.setBlock(blockpos1.east(), stemMaterial.defaultBlockState(), 2);
               worldgenlevel.setBlock(blockpos1.south(), stemMaterial.defaultBlockState(), 2);
            } else if (spiralStep == 3) {
               worldgenlevel.setBlock(blockpos1.south(), stemMaterial.defaultBlockState(), 2);
               worldgenlevel.setBlock(blockpos1.west(), stemMaterial.defaultBlockState(), 2);
            } else if (spiralStep == 4) {
               worldgenlevel.setBlock(blockpos1.west(), stemMaterial.defaultBlockState(), 2);
               worldgenlevel.setBlock(blockpos1.north(), stemMaterial.defaultBlockState(), 2);
               spiralStep = 0;
            }

            int xDistance = blockpos1.getX() - blockpos.getX();
            int zDistance = blockpos1.getZ() - blockpos.getZ();
            if (xDistance >= blobWidth || zDistance >= blobWidth) {
               break;
            }

            if (randomNumber2 >= 4 / blobHeight && xDistance < blobWidth - 4 && zDistance < blobWidth - 4 && !leafMaterial.defaultBlockState().isAir()) {
               int b = 1;

               while (b <= 4) {
                  int randomNumber3 = (int)(Math.random() * 2.0);
                  if (randomNumber3 >= 1) {
                     this.placeBranch(worldgenlevel, blockpos1.below(b).north(randomNumber3).east(randomNumber3 - 1), leafMaterial.defaultBlockState());
                     b++;
                  } else {
                     b = 5;
                     int randomNumber4 = (int)(Math.random() * 8.0);
                     if (randomNumber4 >= 7 && Blocks.WARPED_WART_BLOCK.equals(leafMaterial)) {
                        worldgenlevel.setBlock(blockpos1.below(b), Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                     }
                  }
               }
            }

            spiralStep++;
         }

         return true;
      } else {
         return false;
      }
   }

   private void placeBranch(WorldGenLevel level, BlockPos blockPos, BlockState material) {
      this.placeLeafBlock(level, blockPos.north(), material);
      this.placeLeafBlock(level, blockPos.east(), material);
      this.placeLeafBlock(level, blockPos.south(), material);
      this.placeLeafBlock(level, blockPos.west(), material);
      this.placeLeafBlock(level, blockPos.north().east(), material);
      this.placeLeafBlock(level, blockPos.south().east(), material);
      this.placeLeafBlock(level, blockPos.north().west(), material);
      this.placeLeafBlock(level, blockPos.south().west(), material);
      blockPos = blockPos.below();
      this.placeLeafBlock(level, blockPos.north().east(), material);
      this.placeLeafBlock(level, blockPos.south().east(), material);
      this.placeLeafBlock(level, blockPos.north().west(), material);
      this.placeLeafBlock(level, blockPos.south().west(), material);
   }

   private void placeLeafBlock(WorldGenLevel level, BlockPos blockPos, BlockState material) {
      if (level.getBlockState(blockPos).isAir()) {
         level.setBlock(blockPos, material, 2);
      }
   }
}

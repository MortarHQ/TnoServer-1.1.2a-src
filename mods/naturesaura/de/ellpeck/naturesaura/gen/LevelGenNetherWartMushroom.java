package de.ellpeck.naturesaura.gen;

import com.mojang.serialization.Codec;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LevelGenNetherWartMushroom extends Feature<NoneFeatureConfiguration> {
   public LevelGenNetherWartMushroom() {
      super(Codec.unit(FeatureConfiguration.NONE));
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel levelIn = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      int height = rand.nextInt(5) + 4;
      if (rand.nextInt(10) == 0) {
         height += 5;
      }

      for (int i = 1; i < height; i++) {
         BlockPos offset = pos.above(i);
         if (levelIn.isStateAtPosition(offset, s -> !TreeFeature.validTreePos(levelIn, offset))) {
            return false;
         }
      }

      this.setBlock(levelIn, pos, Blocks.AIR.defaultBlockState());

      for (int i = 0; i < height; i++) {
         this.placeIfPossible(levelIn, pos.above(i), Blocks.NETHER_WART_BLOCK);
      }

      int rad = 3;

      for (int x = -rad; x <= rad; x++) {
         for (int z = -rad; z <= rad; z++) {
            int absX = Math.abs(x);
            int absZ = Math.abs(z);
            if (absX <= 1 && absZ <= 1) {
               this.placeIfPossible(levelIn, pos.offset(x, height, z), ModBlocks.NETHER_WART_MUSHROOM);
            } else if (absX <= 2 && absZ <= 2 && absX != absZ) {
               this.placeIfPossible(levelIn, pos.offset(x, height - 1, z), ModBlocks.NETHER_WART_MUSHROOM);
            } else if (absX < rad - 1 || absZ < rad - 1 || absX == rad - 1 && absZ == rad - 1) {
               this.placeIfPossible(levelIn, pos.offset(x, height - 2, z), ModBlocks.NETHER_WART_MUSHROOM);
            }
         }
      }

      return true;
   }

   private void placeIfPossible(WorldGenLevel level, BlockPos pos, Block block) {
      if (level.isStateAtPosition(pos, s -> TreeFeature.validTreePos(level, pos))) {
         level.setBlock(pos, block.defaultBlockState(), 19);
      }
   }
}

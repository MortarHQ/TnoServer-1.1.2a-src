package dev.denismasterherobrine.afterdark.features;

import com.mojang.serialization.Codec;
import dev.denismasterherobrine.afterdark.features.configuration.AnvilRockConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class AnvilRockFeature extends Feature<AnvilRockConfiguration> {
   public AnvilRockFeature(Codec<AnvilRockConfiguration> pContext) {
      super(pContext);
   }

   public boolean place(FeaturePlaceContext<AnvilRockConfiguration> pContext) {
      WorldGenLevel worldgenlevel = pContext.level();
      BlockPos blockpos = new BlockPos((int)pContext.origin().getCenter().x(), pContext.origin().getY(), (int)pContext.origin().getCenter().z());
      RandomSource random = pContext.random();
      AnvilRockConfiguration config = (AnvilRockConfiguration)pContext.config();
      Integer radius = config.getRadius().sample(random);
      Integer height = config.getHeight().sample(random);
      Integer stretch = config.getStretch().sample(random);
      Integer maxHeight = height - 1;
      if (worldgenlevel.isEmptyBlock(blockpos.below())) {
         return false;
      }

      int randomNumber = (int)(Math.random() * 4.0);

      for (int s = 0; s <= stretch; s++) {
         for (int h = 0; h < height; h++) {
            BlockPos pos;
            if (randomNumber == 3) {
               pos = blockpos.offset(-(h / 4 * s), 0, -(h / 6));
            } else if (randomNumber == 2) {
               pos = blockpos.offset(-(h / 6), 0, -(h / 4 * s));
            } else if (randomNumber == 1) {
               pos = blockpos.offset(h / 4 * s, 0, h / 6);
            } else {
               pos = blockpos.offset(h / 6, 0, h / 4 * s);
            }

            boolean obese = maxHeight >= 5 && h == 2 || maxHeight >= 5 && h == height - 2;
            boolean narrow = maxHeight >= 5 && h > 3 && h < height - 3;
            boolean anorexic = maxHeight >= 9 && h > 5 && h < height - 5;
            if (narrow) {
               this.genNarrowRadius1(pContext, worldgenlevel, pos, h);
            } else if (anorexic) {
               this.genAnorexicRadius1(pContext, worldgenlevel, pos, h);
            } else if (obese) {
               this.genObeseRadius1(pContext, worldgenlevel, pos, h);
            } else {
               this.genRadius1(pContext, worldgenlevel, pos, h);
            }

            if (radius >= 2 || h == 1 || h == maxHeight) {
               if (narrow) {
                  this.genNarrowRadius2(pContext, worldgenlevel, pos, h);
               } else if (!anorexic) {
                  if (obese) {
                     this.genObeseRadius2(pContext, worldgenlevel, pos, h);
                  } else {
                     this.genRadius2(pContext, worldgenlevel, pos, h);
                  }
               }
            }

            if (radius >= 3) {
               if (anorexic) {
                  this.genNarrowRadius2(pContext, worldgenlevel, pos, h);
               } else if (narrow) {
                  this.genRadius2(pContext, worldgenlevel, pos, h);
               } else if (obese) {
                  this.genObeseRadius3(pContext, worldgenlevel, pos, h);
               } else {
                  this.genRadius3(pContext, worldgenlevel, pos, h);
               }

               if (h == 1 || h == maxHeight) {
                  this.genRadius4(pContext, worldgenlevel, pos, h);
               }
            } else if (radius == 2 && h == 1) {
               this.genRadius3(pContext, worldgenlevel, pos, h);
            } else if (radius == 2 && h == maxHeight) {
               this.genRadius3(pContext, worldgenlevel, pos, h);
            }
         }
      }

      return true;
   }

   private void genAnorexicRadius1(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
   }

   private void genNarrowRadius1(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
   }

   private void genRadius1(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
   }

   private void genObeseRadius1(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 0), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -1), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -2), this.getState(pContext, blockpos.offset(0, h, 0)), 2);
   }

   private void genNarrowRadius2(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
   }

   private void genRadius2(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
   }

   private void genObeseRadius2(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(0, --h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -2), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 0), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -3), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -1), this.getState(pContext, blockpos.offset(1, h, 1)), 2);
   }

   private void genRadius3(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(2, --h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
   }

   private void genObeseRadius3(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(2, --h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h + 1, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h + 1, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h + 1, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h + 1, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h - 1, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h - 1, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h - 1, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h - 1, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -3), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 0), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, -1), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -4), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, -2), this.getState(pContext, blockpos.offset(2, h, 2)), 2);
   }

   private void genRadius4(FeaturePlaceContext<AnvilRockConfiguration> pContext, WorldGenLevel worldgenlevel, BlockPos blockpos, int h) {
      worldgenlevel.setBlock(blockpos.offset(3, --h, 3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 0), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(0, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 0), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 1), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(1, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 1), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, -1), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-1, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, -1), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 2), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(2, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 2), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, -2), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-2, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, -2), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, 3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(3, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, 3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, 4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(4, h, -3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-3, h, -4), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
      worldgenlevel.setBlock(blockpos.offset(-4, h, -3), this.getState(pContext, blockpos.offset(3, h, 3)), 2);
   }

   private BlockState getState(FeaturePlaceContext<AnvilRockConfiguration> pContext, BlockPos pos) {
      return ((AnvilRockConfiguration)pContext.config()).material.getState(pContext.level(), pContext.random(), pos);
   }
}

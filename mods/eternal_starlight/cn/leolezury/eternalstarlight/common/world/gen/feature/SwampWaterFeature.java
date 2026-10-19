package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class SwampWaterFeature extends ESFeature<NoneFeatureConfiguration> {
   private long lastSeed;
   private PerlinSimplexNoise noise;

   public SwampWaterFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
      this.noise = new PerlinSimplexNoise(RandomSource.create(this.lastSeed), List.of(0));
   }

   public void setSeed(long seed) {
      if (seed != this.lastSeed) {
         this.noise = new PerlinSimplexNoise(RandomSource.create(seed), List.of(0));
         this.lastSeed = seed;
      }
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos chunkCoord = this.getChunkCoordinate(context.origin());
      this.setSeed(level.getSeed());
      MutableBlockPos waterPos = new MutableBlockPos();

      for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
         for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
            if (this.noise.getValue(x / 20.0, z / 20.0, false) > -0.1) {
               int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
               waterPos.set(x, y, z);
               if (level.getBlockState(waterPos.offset(0, 1, 0)).isAir()
                  && this.isValid(level.getBlockState(waterPos.offset(0, -1, 0)))
                  && this.isValid(level.getBlockState(waterPos.offset(1, 0, 0)))
                  && this.isValid(level.getBlockState(waterPos.offset(-1, 0, 0)))
                  && this.isValid(level.getBlockState(waterPos.offset(0, 0, 1)))
                  && this.isValid(level.getBlockState(waterPos.offset(0, 0, -1)))
                  && level.getBlockState(waterPos).is(BlockTags.DIRT)
                  && level.getBiome(waterPos).is(ESBiomes.DARK_SWAMP)) {
                  this.setBlock(level, waterPos, Blocks.WATER.defaultBlockState());
               }
            }
         }
      }

      return true;
   }

   protected boolean isValid(BlockState state) {
      return state.is(BlockTags.DIRT) || state.is(Blocks.WATER);
   }
}

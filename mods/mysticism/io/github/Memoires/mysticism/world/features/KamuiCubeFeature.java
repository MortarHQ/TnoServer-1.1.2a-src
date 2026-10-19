package io.github.Memoires.mysticism.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class KamuiCubeFeature extends Feature<NoneFeatureConfiguration> {
   public KamuiCubeFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel level = ctx.level();
      RandomSource rand = ctx.random();
      BlockPos origin = ctx.origin();
      BlockState BLOCK = Blocks.CYAN_TERRACOTTA.defaultBlockState();
      int chunkMinX = origin.getX() & -16;
      int chunkMinZ = origin.getZ() & -16;
      int baseY = -64;
      int minHeight = 72;
      int maxHeight = 130;
      int padX = 1 + rand.nextInt(4);
      int padZ = 1 + rand.nextInt(4);
      int x0 = chunkMinX + padX;
      int z0 = chunkMinZ + padZ;
      int x1 = chunkMinX + 15 - padX;
      int z1 = chunkMinZ + 15 - padZ;
      int height = Mth.nextInt(rand, 72, 130);
      int y0 = -64;
      int y1 = Math.min(level.getMaxBuildHeight() - 1, y0 + height - 1);
      fillBox(level, x0, y0, z0, x1, y1, z1, BLOCK);
      return true;
   }

   private static void fillBox(WorldGenLevel level, int x0, int y0, int z0, int x1, int y1, int z1, BlockState state) {
      int minX = Math.min(x0, x1);
      int maxX = Math.max(x0, x1);
      int minY = Math.max(level.getMinBuildHeight(), Math.min(y0, y1));
      int maxY = Math.min(level.getMaxBuildHeight() - 1, Math.max(y0, y1));
      int minZ = Math.min(z0, z1);
      int maxZ = Math.max(z0, z1);
      MutableBlockPos pos = new MutableBlockPos();

      for (int y = minY; y <= maxY; y++) {
         for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
               pos.set(x, y, z);
               level.setBlock(pos, state, 2);
            }
         }
      }
   }
}

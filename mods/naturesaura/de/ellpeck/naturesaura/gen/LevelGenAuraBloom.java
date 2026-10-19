package de.ellpeck.naturesaura.gen;

import com.mojang.serialization.Codec;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraBloom;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LevelGenAuraBloom extends Feature<NoneFeatureConfiguration> {
   private final Block block;
   private final int chance;
   private final boolean nether;

   public LevelGenAuraBloom(Block block, int chance, boolean nether) {
      super(Codec.unit(FeatureConfiguration.NONE));
      this.block = block;
      this.chance = chance;
      this.nether = nether;
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel levelIn = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      if (rand.nextInt(this.chance) != 0) {
         return false;
      }

      int startX = pos.getX() + rand.nextInt(16);
      int startZ = pos.getZ() + rand.nextInt(16);
      boolean any = false;

      for (int i = Mth.nextInt(rand, 3, 8); i > 0; i--) {
         int offX = startX + Mth.nextInt(rand, -5, 5);
         int offZ = startZ + Mth.nextInt(rand, -5, 5);
         if (!this.nether) {
            BlockPos placePos = new BlockPos(offX, levelIn.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, offX, offZ), offZ);
            if (this.tryPlace(levelIn, placePos)) {
               any = true;
            }
         } else {
            int y = Mth.nextInt(rand, 0, 128);

            for (int off = 0; off < 64; off++) {
               if (this.tryPlace(levelIn, new BlockPos(offX, y - off, offZ)) || this.tryPlace(levelIn, new BlockPos(offX, y + off, offZ))) {
                  any = true;
                  break;
               }
            }
         }
      }

      return any;
   }

   private boolean tryPlace(WorldGenLevel level, BlockPos pos) {
      BlockState state = this.block.defaultBlockState();
      if (state.canSurvive(level, pos)) {
         level.setBlock(pos, state, 3);
         if (level.getBlockEntity(pos) instanceof BlockEntityAuraBloom bloom) {
            bloom.justGenerated = true;
         }

         return true;
      } else {
         return false;
      }
   }
}

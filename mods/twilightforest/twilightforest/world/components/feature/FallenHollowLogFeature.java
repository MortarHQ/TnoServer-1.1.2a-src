package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.init.TFBlocks;
import twilightforest.util.features.FeatureUtil;

public class FallenHollowLogFeature extends Feature<NoneFeatureConfiguration> {
   final BlockState mossPatch = ((Block)TFBlocks.MOSS_PATCH.get()).defaultBlockState();
   final BlockState oakLeaves = (BlockState)((Block)TFBlocks.TWILIGHT_OAK_LEAVES.get()).defaultBlockState().setValue(LeavesBlock.PERSISTENT, true);
   final BlockState oakLogWithZAxis = (BlockState)((RotatedPillarBlock)TFBlocks.TWILIGHT_OAK_LOG.get())
      .defaultBlockState()
      .setValue(RotatedPillarBlock.AXIS, Axis.Z);
   final BlockState oakLogWithXAxis = (BlockState)((RotatedPillarBlock)TFBlocks.TWILIGHT_OAK_LOG.get())
      .defaultBlockState()
      .setValue(RotatedPillarBlock.AXIS, Axis.X);
   final BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
   final BlockState firefly = ((Block)TFBlocks.FIREFLY.get()).defaultBlockState();

   public FallenHollowLogFeature(Codec<NoneFeatureConfiguration> configIn) {
      super(configIn);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos pos = ctx.origin();
      RandomSource rand = ctx.random();
      return rand.nextBoolean() ? this.makeLog4Z(world, rand, pos) : this.makeLog4X(world, rand, pos);
   }

   private boolean makeLog4Z(WorldGenLevel world, RandomSource rand, BlockPos pos) {
      if (!FeatureUtil.isAreaSuitable(world, pos, 4, 3, 9)) {
         return false;
      }

      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 0, 0);
      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 3, 0);
      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 0, 1);
      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 3, 1);
      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 1, 2);
      this.makeNegativeZJaggy(world, pos, rand.nextInt(3), 2, 2);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 0, 0);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 3, 0);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 0, 1);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 3, 1);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 1, 2);
      this.makePositiveZJaggy(world, pos, rand.nextInt(3), 2, 2);

      for (int dz = 0; dz < 4; dz++) {
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(1, -1, dz + 3), this.oakLogWithZAxis, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(1, 0, dz + 3), this.mossPatch, 3);
               this.markAboveForPostProcessing(world, pos.offset(1, -1, dz + 3));
            }
         } else {
            world.setBlock(pos.offset(1, -1, dz + 3), this.grass, 3);
            world.setBlock(pos.offset(1, 0, dz + 3), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(1, -1, dz + 3));
         }

         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(2, -1, dz + 3), this.oakLogWithZAxis, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(2, 0, dz + 3), this.mossPatch, 3);
               this.markAboveForPostProcessing(world, pos.offset(2, -1, dz + 3));
            }
         } else {
            world.setBlock(pos.offset(2, -1, dz + 3), this.grass, 3);
            world.setBlock(pos.offset(2, 0, dz + 3), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(2, -1, dz + 3));
         }

         world.setBlock(pos.offset(0, 0, dz + 3), this.oakLogWithZAxis, 3);
         world.setBlock(pos.offset(3, 0, dz + 3), this.oakLogWithZAxis, 3);
         world.setBlock(pos.offset(0, 1, dz + 3), this.oakLogWithZAxis, 3);
         world.setBlock(pos.offset(3, 1, dz + 3), this.oakLogWithZAxis, 3);
         world.setBlock(pos.offset(1, 2, dz + 3), this.oakLogWithZAxis, 3);
         world.setBlock(pos.offset(2, 2, dz + 3), this.oakLogWithZAxis, 3);
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(1, 3, dz + 3), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(1, 2, dz + 3));
         }

         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(2, 3, dz + 3), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(2, 2, dz + 3));
         }
      }

      int offZ = rand.nextInt(3) + 2;
      boolean plusX = rand.nextBoolean();

      for (int dz = 0; dz < 3; dz++) {
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(plusX ? 3 : 0, 2, dz + offZ), this.oakLeaves, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(plusX ? 3 : 0, 3, dz + offZ), this.oakLeaves, 3);
            }

            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(plusX ? 4 : -1, 2, dz + offZ), this.oakLeaves, 3);
            }
         }
      }

      world.setBlock(pos.offset(plusX ? 0 : 3, 2, rand.nextInt(4) + 3), this.firefly, 3);
      return true;
   }

   private void makeNegativeZJaggy(LevelAccessor world, BlockPos pos, int length, int dx, int dy) {
      for (int dz = -length; dz < 0; dz++) {
         world.setBlock(pos.offset(dx, dy, dz + 3), this.oakLogWithZAxis, 3);
      }
   }

   private void makePositiveZJaggy(LevelAccessor world, BlockPos pos, int length, int dx, int dy) {
      for (int dz = 0; dz < length; dz++) {
         world.setBlock(pos.offset(dx, dy, dz + 7), this.oakLogWithZAxis, 3);
      }
   }

   private boolean makeLog4X(WorldGenLevel world, RandomSource rand, BlockPos pos) {
      if (!FeatureUtil.isAreaSuitable(world, pos, 9, 3, 4)) {
         return false;
      }

      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 0, 0);
      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 3, 0);
      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 0, 1);
      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 3, 1);
      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 1, 2);
      this.makeNegativeXJaggy(world, pos, rand.nextInt(3), 2, 2);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 0, 0);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 3, 0);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 0, 1);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 3, 1);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 1, 2);
      this.makePositiveXJaggy(world, pos, rand.nextInt(3), 2, 2);

      for (int dx = 0; dx < 4; dx++) {
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(dx + 3, -1, 1), this.oakLogWithXAxis, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(dx + 3, 0, 1), this.mossPatch, 3);
               this.markAboveForPostProcessing(world, pos.offset(dx + 3, -1, 1));
            }
         } else {
            world.setBlock(pos.offset(dx + 3, -1, 1), this.grass, 3);
            world.setBlock(pos.offset(dx + 3, 0, 1), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(dx + 3, -1, 1));
         }

         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(dx + 3, -1, 2), this.oakLogWithXAxis, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(dx + 3, 0, 2), this.mossPatch, 3);
               this.markAboveForPostProcessing(world, pos.offset(dx + 3, -1, 2));
            }
         } else {
            world.setBlock(pos.offset(dx + 3, -1, 2), this.grass, 3);
            world.setBlock(pos.offset(dx + 3, 0, 2), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(dx + 3, -1, 2));
         }

         world.setBlock(pos.offset(dx + 3, 0, 0), this.oakLogWithXAxis, 3);
         world.setBlock(pos.offset(dx + 3, 0, 3), this.oakLogWithXAxis, 3);
         world.setBlock(pos.offset(dx + 3, 1, 0), this.oakLogWithXAxis, 3);
         world.setBlock(pos.offset(dx + 3, 1, 3), this.oakLogWithXAxis, 3);
         world.setBlock(pos.offset(dx + 3, 2, 1), this.oakLogWithXAxis, 3);
         world.setBlock(pos.offset(dx + 3, 2, 2), this.oakLogWithXAxis, 3);
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(dx + 3, 3, 1), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(dx + 3, 2, 1));
         }

         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(dx + 3, 3, 2), this.mossPatch, 3);
            this.markAboveForPostProcessing(world, pos.offset(dx + 3, 2, 2));
         }
      }

      int offX = rand.nextInt(3) + 2;
      boolean plusZ = rand.nextBoolean();

      for (int dx = 0; dx < 3; dx++) {
         if (rand.nextBoolean()) {
            world.setBlock(pos.offset(dx + offX, 2, plusZ ? 3 : 0), this.oakLeaves, 3);
            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(dx + offX, 3, plusZ ? 3 : 0), this.oakLeaves, 3);
            }

            if (rand.nextBoolean()) {
               world.setBlock(pos.offset(dx + offX, 2, plusZ ? 4 : -1), this.oakLeaves, 3);
            }
         }
      }

      world.setBlock(pos.offset(rand.nextInt(4) + 3, 2, plusZ ? 0 : 3), this.firefly, 3);
      return true;
   }

   private void makeNegativeXJaggy(LevelAccessor world, BlockPos pos, int length, int dz, int dy) {
      for (int dx = -length; dx < 0; dx++) {
         world.setBlock(pos.offset(dx + 3, dy, dz), this.oakLogWithXAxis, 3);
      }
   }

   private void makePositiveXJaggy(LevelAccessor world, BlockPos pos, int length, int dz, int dy) {
      for (int dx = 0; dx < length; dx++) {
         world.setBlock(pos.offset(dx + 7, dy, dz), this.oakLogWithXAxis, 3);
      }
   }
}

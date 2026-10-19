package com.chronodawn.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TemporalMossBlock extends Block implements BonemealableBlock {
   public TemporalMossBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F, 0.1F).sound(SoundType.MOSS_CARPET).randomTicks();
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (random.nextInt(10) == 0) {
         Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
         BlockPos targetPos = pos.relative(direction);
         BlockState targetState = level.getBlockState(targetPos);
         if (this.canSpreadTo(targetState)) {
            level.setBlockAndUpdate(targetPos, this.defaultBlockState());
         }
      }
   }

   private boolean canSpreadTo(BlockState state) {
      Block block = state.getBlock();
      String blockId = block.toString();
      return blockId.contains("dirt") || blockId.contains("stone") || blockId.contains("grass_block");
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      for (Direction direction : Plane.HORIZONTAL) {
         BlockPos targetPos = pos.relative(direction);
         BlockState targetState = level.getBlockState(targetPos);
         if (this.canSpreadTo(targetState) && random.nextBoolean()) {
            level.setBlockAndUpdate(targetPos, this.defaultBlockState());
         }
      }
   }
}

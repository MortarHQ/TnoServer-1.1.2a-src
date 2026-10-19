package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.client.level.sound.AmbientDesertBlockSoundsPlayer;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ShortDryGrassBlock extends DeadBushBlock implements BonemealableBlock {
   public ShortDryGrassBlock(Properties properties) {
      super(properties);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      AmbientDesertBlockSoundsPlayer.playAmbientDryGrassSounds(level, pos, random);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      level.setBlockAndUpdate(pos, ModBlocks.TALL_DRY_GRASS.get().defaultBlockState());
   }
}

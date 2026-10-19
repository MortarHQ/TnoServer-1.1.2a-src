package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BlackBloodConquestData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BloodFlowerPetalBlock extends Block {
   public BloodFlowerPetalBlock(Properties properties) {
      super(properties);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if ((Boolean)cfg.blockInfectionEnabled.get()) {
         BlackBloodConquestData.get(level).addInfectionPressure(pos, 10, Math.max(250, (Integer)cfg.domainChunkMaxValue.get() * 2));
      }
   }
}

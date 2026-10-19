package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BlackBloodDomainData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class InfectedBloodBlock extends Block {
   public InfectedBloodBlock(Properties properties) {
      super(properties);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if ((Boolean)cfg.blockInfectionEnabled.get()) {
         BlackBloodDomainData data = BlackBloodDomainData.get(level);
         data.addChunkValue(pos, (Integer)cfg.domainChunkValuePerBlock.get(), (Integer)cfg.domainChunkMaxValue.get());
      }
   }
}

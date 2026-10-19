package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BlackBloodDomainData;
import com.github.wal_bos.moreskills.handler.BlackBloodDomainHandler;
import com.github.wal_bos.moreskills.handler.BloodfiendHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlackBloodMireBlock extends Block {
   public BlackBloodMireBlock(Properties properties) {
      super(properties);
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      super.stepOn(level, pos, state, entity);
      if (!level.isClientSide()) {
         if (entity instanceof LivingEntity living) {
            if (level instanceof ServerLevel serverLevel) {
               MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
               if ((Boolean)cfg.blockInfectionEnabled.get()) {
                  if (!BloodfiendHandler.isBloodfiend(living) && (Boolean)cfg.domainBiomeDamagesEnemies.get() && living.tickCount % 40 == 0) {
                     BlackBloodDomainHandler.damageEnemyOnMire(serverLevel, living);
                  }
               }
            }
         }
      }
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if ((Boolean)cfg.blockInfectionEnabled.get()) {
         BlackBloodDomainData data = BlackBloodDomainData.get(level);
         data.addChunkValue(pos, (Integer)cfg.domainChunkValuePerBlock.get(), (Integer)cfg.domainChunkMaxValue.get());
      }
   }
}

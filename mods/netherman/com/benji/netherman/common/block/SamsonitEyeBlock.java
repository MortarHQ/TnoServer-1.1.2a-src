package com.benji.netherman.common.block;

import com.benji.netherman.common.entity.BellGuardianEntity;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SamsonitEyeBlock extends Block {
   public SamsonitEyeBlock(Properties properties) {
      super(properties);
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (!level.isClientSide() && entity instanceof Player) {
         level.setBlock(pos, ((Block)ModBlocks.SAMSONIT.get()).defaultBlockState(), 3);
         level.playSound(null, pos, (SoundEvent)ModSounds.BELL_BEAST_1.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         int radiusH = 100;
         int radiusV = 50;

         for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-radiusH, -radiusV, -radiusH), pos.offset(radiusH, radiusV, radiusH))) {
            BlockState checkState = level.getBlockState(checkPos);
            if (checkState.is((Block)ModBlocks.LABYRINTH_BELLSPAWN.get())) {
               BellGuardianEntity guardian = (BellGuardianEntity)((EntityType)ModEntities.BELL_GUARDIAN.get()).create(level);
               if (guardian != null) {
                  guardian.moveTo(checkPos.getX() + 0.5, checkPos.getY() + 1.0, checkPos.getZ() + 0.5, 0.0F, 0.0F);
                  level.addFreshEntity(guardian);
                  level.playSound(null, checkPos, (SoundEvent)ModSounds.BELL_BEAST_1.get(), SoundSource.HOSTILE, 1.0F, 0.5F);
                  level.setBlock(checkPos, ((Block)ModBlocks.SAMSONIT_BELL.get()).defaultBlockState(), 3);
               }
            }
         }
      }

      super.stepOn(level, pos, state, entity);
   }
}

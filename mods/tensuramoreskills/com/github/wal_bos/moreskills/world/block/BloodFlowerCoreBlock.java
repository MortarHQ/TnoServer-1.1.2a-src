package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.handler.BlackBloodConquestHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class BloodFlowerCoreBlock extends Block {
   public BloodFlowerCoreBlock(Properties properties) {
      super(properties);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.isClientSide()) {
         return InteractionResult.SUCCESS;
      } else if (player instanceof ServerPlayer serverPlayer) {
         return BlackBloodConquestHandler.drainBloomPower(serverPlayer, pos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }
}

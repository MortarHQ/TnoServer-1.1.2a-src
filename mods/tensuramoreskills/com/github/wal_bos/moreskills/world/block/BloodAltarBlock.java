package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.handler.BlackBloodDomainHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class BloodAltarBlock extends Block {
   public BloodAltarBlock(Properties properties) {
      super(properties);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.isClientSide()) {
         return InteractionResult.SUCCESS;
      }

      if (player instanceof ServerPlayer serverPlayer) {
         if (player.isShiftKeyDown()) {
            return BlackBloodDomainHandler.showAltarInfo(serverPlayer, pos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
         } else {
            return BlackBloodDomainHandler.sacrificeToAltar(serverPlayer, pos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (level.isClientSide()) {
         return ItemInteractionResult.SUCCESS;
      }

      if (player instanceof ServerPlayer serverPlayer) {
         if (player.isShiftKeyDown()) {
            return BlackBloodDomainHandler.showAltarInfo(serverPlayer, pos)
               ? ItemInteractionResult.SUCCESS
               : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         } else {
            return BlackBloodDomainHandler.sacrificeToAltar(serverPlayer, pos)
               ? ItemInteractionResult.SUCCESS
               : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         }
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }
}

package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

public class CopperButtonBlock extends ButtonBlock {
   public CopperButtonBlock(int pressTicks, Properties properties) {
      super(BlockSetType.COPPER, pressTicks, properties);
   }

   public SoundEvent getSound(boolean powered) {
      return super.getSound(powered);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      InteractionResult actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);
      return actionResult.consumesAction() ? actionResult : super.useWithoutItem(state, world, pos, player, hit);
   }
}

package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public final class OxidizableButtonBlock extends CopperButtonBlock implements FriendsAndFoesOxidizable {
   private final WeatherState oxidationLevel;

   public OxidizableButtonBlock(WeatherState oxidationLevel, int pressTicks, Properties properties) {
      super(pressTicks, properties);
      this.oxidationLevel = oxidationLevel;
   }

   public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
      this.changeOverTime(state, world, pos, random);
   }

   public boolean isRandomlyTicking(BlockState state) {
      return this.getAge().ordinal() < WeatherState.OXIDIZED.ordinal();
   }

   public WeatherState getAge() {
      return this.oxidationLevel;
   }

   @Override
   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      InteractionResult actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);
      return actionResult.consumesAction() ? actionResult : super.useWithoutItem(state, world, pos, player, hit);
   }
}

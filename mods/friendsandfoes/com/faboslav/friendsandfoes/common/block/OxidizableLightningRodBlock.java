package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public final class OxidizableLightningRodBlock extends LightningRodBlock implements WeatheringCopper {
   private final WeatherState oxidationLevel;

   public OxidizableLightningRodBlock(WeatherState oxidationLevel, Properties settings) {
      super(settings);
      this.oxidationLevel = oxidationLevel;
   }

   public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
      if (FriendsAndFoes.getConfig().enableLightningRodOxidation) {
         this.changeOverTime(state, world, pos, random);
      }
   }

   public boolean isRandomlyTicking(BlockState blockState) {
      return !FriendsAndFoes.getConfig().enableLightningRodOxidation ? false : this.getAge().ordinal() < WeatherState.OXIDIZED.ordinal();
   }

   public WeatherState getAge() {
      return this.oxidationLevel;
   }

   public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      InteractionResult actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);
      return actionResult.consumesAction() ? actionResult : super.useWithoutItem(state, world, pos, player, hit);
   }
}

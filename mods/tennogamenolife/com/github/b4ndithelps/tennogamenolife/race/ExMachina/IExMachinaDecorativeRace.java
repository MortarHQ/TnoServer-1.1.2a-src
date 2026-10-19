package com.github.b4ndithelps.tennogamenolife.race.ExMachina;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public interface IExMachinaDecorativeRace {
   default void onPlayerTick(Player player) {
      if (player.level().isClientSide) {
         ExMachinaDecorativeManager.updateDecorativeState(player);
      }
   }

   default ExMachinaDecorativeManager.ArmorAnimationState getArmorAnimationState(Player player) {
      return player.level().isClientSide ? ExMachinaDecorativeManager.getArmorAnimationState(player) : ExMachinaDecorativeManager.ArmorAnimationState.IDLE;
   }

   default int getAnimationTimer(Player player) {
      return player.level().isClientSide ? ExMachinaDecorativeManager.getAnimationTimer(player) : 0;
   }

   default boolean shouldShowDecorative(Player player) {
      return player.level().isClientSide ? ExMachinaDecorativeManager.shouldShowDecorative(player) : false;
   }

   default boolean isHoldingExMachinasWeapon(Player player) {
      return ExMachinaDecorativeManager.isHoldingExMachinasWeapon(player);
   }

   default ExMachinaDecorativeData getDecorativeData(Player player) {
      return player.level().isClientSide ? ExMachinaDecorativeManager.getDecorativeData(player) : new ExMachinaDecorativeData();
   }

   default void saveDecorativeData(Player player, CompoundTag nbt) {
      if (player.level().isClientSide) {
         ExMachinaDecorativeManager.saveDecorativeData(player, nbt);
      }
   }

   default void loadDecorativeData(Player player, CompoundTag nbt) {
      if (player.level().isClientSide) {
         ExMachinaDecorativeManager.loadDecorativeData(player, nbt);
      }
   }

   default String getDecorativeDescription() {
      return "ExMachina种族 - 具备机铠装饰显示功能";
   }

   default String getDecorativeStatusInfo(Player player) {
      if (!player.level().isClientSide) {
         return "装饰状态: 服务器端（不可用）";
      }

      ExMachinaDecorativeData data = this.getDecorativeData(player);
      return String.format(
         "装饰状态: %s | 动画: %s | 武器: %s",
         this.shouldShowDecorative(player) ? "启用" : "禁用",
         data.getAnimationStateDescription(),
         this.isHoldingExMachinasWeapon(player) ? "持有" : "未持有"
      );
   }
}

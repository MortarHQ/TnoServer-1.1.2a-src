package com.github.wal_bos.moreskills.casino;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;

public final class GreedCasinoProvider {
   private GreedCasinoProvider() {
   }

   public static void open(ServerPlayer player) {
      player.openMenu(
         new SimpleMenuProvider((id, inventory, p) -> new GreedCasinoMenu(id, inventory), Component.translatable("tensuramoreskills.gui.mammon_casino.title"))
      );
   }
}

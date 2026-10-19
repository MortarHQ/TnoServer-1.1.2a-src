package com.github.wal_bos.moreskills.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;

public final class NaberiusMenuProvider {
   private NaberiusMenuProvider() {
   }

   public static void open(ServerPlayer player, int mode) {
      player.openMenu(
         new SimpleMenuProvider(
            (containerId, inventory, p) -> new NaberiusMenu(containerId, inventory, mode), Component.translatable("tensuramoreskills.gui.naberius.title")
         ),
         buf -> NaberiusMenu.writeExtraData(player, buf, mode)
      );
   }
}
